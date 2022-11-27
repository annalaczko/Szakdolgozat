package com.annalaczko.onlab.model;

import com.annalaczko.onlab.model.data.*;

import java.util.ArrayList;
import java.util.Comparator;

public class TrapezeGenerator {

    public static ArrayList<Trapeze> trapezes = new ArrayList<>(); //trapézok listája
    public static ArrayList<Coordinate> coordinates = new ArrayList<>();
    private static ArrayList<Polygon> openObjects = new ArrayList<>();

    private static ArrayList<Coordinate> usedCoordinates = new ArrayList<>();

    //region Private methods for generating trapezes

    private static Coordinate findCoordinate(Coordinate coordinate, Position coordinatePosition, boolean isLastColumn) throws Exception {
        boolean done = false;
        int yCoordinate = (int) coordinate.getY();
        Coordinate result = null;

        while (!done) {
            switch (coordinatePosition) {
                case upper:
                    yCoordinate--;
                    if (yCoordinate <= 0) {
                        done = true;
                        result = new Coordinate(coordinate.getX(), yCoordinate, null);
                    }
                    break;
                case lower:
                    yCoordinate++;
                    if (yCoordinate >= RoomModel.getHeight()) {
                        done = true;
                        result = new Coordinate(coordinate.getX(), yCoordinate, null);
                    }
                    break;
                default:
                    break;
            }

            for (Polygon object : RoomModel.objects) {
                if (done) break;
                if (object.contains((int) coordinate.getX(), yCoordinate)) {
                    done = true;

                    result = new Coordinate(coordinate.getX(), yCoordinate, object);
                    if (result.getX() == object.getFirstCorner().getX() && isLastColumn) {
                        done = false;
                    }
                    if (result.getX() == object.getLastCorner().getX() && !isLastColumn) {
                        done = false;
                    }
                    if (object.findCorner(result)) { //TODO: findcornerbe nem kell valszleg hibahatár
                        usedCoordinates.add(result);
                        if (!openObjects.contains(object)) openObjects.add(object);
                        if (result.getX() == object.getLastCorner().getX()) {
                            usedCoordinates.remove(result);
                        }
                    }

                }

            }
        }

        if (result == null) throw new Exception("Result in find coordinate is null!");

        return result;
    }

    private static void writeCoordinates() {
        for (int i = 0; i < coordinates.size(); i++) {
            System.out.println("ID: " + i + " Coordinates: " + coordinates.get(i).getX() + "-" + coordinates.get(i).getY());
        }
    }

    private static void findTrapezeUltimate(int i) throws Exception {

        Coordinate coordinate = coordinates.get(i);
        Coordinate[] cs;


        if (i == 0) {
            openObjects.add(coordinate.getObject());

            cs = new Coordinate[]{
                    new Coordinate(0, 0),
                    new Coordinate(findCoordinate(coordinate, Position.upper, true)),
                    new Coordinate(findCoordinate(coordinate, Position.lower, true)),
                    new Coordinate(0, RoomModel.getHeight())
            };

            trapezes.add(new Trapeze(cs));
        } else {
            if (openObjects.size() == 0) {
                openObjects.add(coordinate.getObject());
                cs = new Coordinate[]{
                        new Coordinate(coordinates.get(i - 1).getX(), 0),
                        new Coordinate(findCoordinate(coordinate, Position.upper, true)),
                        new Coordinate(findCoordinate(coordinate, Position.lower, true)),
                        new Coordinate(coordinates.get(i - 1).getX(), RoomModel.getHeight())
                };

                trapezes.add(new Trapeze(cs));
            } else {
                if (!openObjects.contains(coordinate.getObject())) {
                    openObjects.add(coordinate.getObject());
                    Coordinate c0, c1, c2, c3;
                    c0 = null;
                    c3 = null;

                    c1 = findCoordinate(coordinate, Position.upper, true);
                    c2 = findCoordinate(coordinate, Position.lower, true);


                    if (c1.getObject() != null && c2.getObject() != null) { //RIP ez bajos így, a c0 meg a c3 már overwriteolva lett
                        c0 = c1.getObject().findNeighbourCoordinate(c1, Position.lower);
                        c3 = c2.getObject().findNeighbourCoordinate(c2, Position.upper);
                        if (c0.getX() > c3.getX()) {
                            c3 = findCoordinate(c0, Position.lower, false);
                            if (findLastUsedGoodCoordinate(c1, c0, Position.lower) != null) {
                                c3 = findCoordinate(findLastUsedGoodCoordinate(c1, c0, Position.lower), Position.lower, false);
                                c0 = findCoordinate(findLastUsedGoodCoordinate(c1, c0, Position.lower), Position.upper, false);
                            }
                        } else {
                            c0 = findCoordinate(c3, Position.upper, false);
                            if (findLastUsedGoodCoordinate(c2, c3, Position.upper) != null) {
                                c0 = findCoordinate(findLastUsedGoodCoordinate(c2, c3, Position.upper), Position.upper, false);
                                c3 = findCoordinate(findLastUsedGoodCoordinate(c2, c3, Position.upper), Position.lower, false);
                            }
                        }
                    } else {
                        if (c1.getObject() != null) {
                            c0 = c1.getObject().findNeighbourCoordinate(c1, Position.lower);
                            c3 = new Coordinate(c0.getX(), RoomModel.getHeight());
                            if (findLastUsedGoodCoordinate(c1, c0, Position.lower) != null) {
                                c3 = findCoordinate(findLastUsedGoodCoordinate(c1, c0, Position.lower), Position.lower, false);
                                c0 = findCoordinate(findLastUsedGoodCoordinate(c1, c0, Position.lower), Position.upper, false);
                            }
                        }
                        if (c2.getObject() != null) {
                            c3 = c2.getObject().findNeighbourCoordinate(c2, Position.upper);
                            c0 = new Coordinate(c3.getX(), 0);
                            if (findLastUsedGoodCoordinate(c2, c3, Position.upper) != null) {
                                c0 = findCoordinate(findLastUsedGoodCoordinate(c2, c3, Position.upper), Position.upper, false);
                                c3 = findCoordinate(findLastUsedGoodCoordinate(c2, c3, Position.upper), Position.lower, false);
                            }
                        }
                    }


                    cs = new Coordinate[]{c0, c1, c2, c3};

                    trapezes.add(new Trapeze(cs));

                } else {

                    Coordinate c0, c1, c2, c3;

                    if (coordinate.getObject().isCoordinatePosition(coordinate, Position.upper)) {

                        c2 = coordinate;
                        c1 = findCoordinate(coordinate, Position.upper, true);
                        c3 = c2.getObject().coordinatesAroundTheClock.get(c2.getObject().findCornerIdAroundTheClock(c2) - 1);

                        if (c1.getObject() != null) {
                            if (c1.getObject().findNeighbourCoordinate(c1, Position.lower).getX() > c3.getX()) {
                                c0 = c1.getObject().findNeighbourCoordinate(c1, Position.lower);
                                if (findLastUsedGoodCoordinate(c2, c0, Position.upper) != null) {
                                    c3 = findCoordinate(findLastUsedGoodCoordinate(c2, c0, Position.upper), Position.lower, false);
                                    c0 = findCoordinate(findLastUsedGoodCoordinate(c2, c0, Position.upper), Position.upper, false);
                                } else {
                                    c3 = findCoordinate(c0, Position.lower, false);
                                }
                            } else {
                                if (findLastUsedGoodCoordinate(c2, c3, Position.upper) != null) {

                                    c0 = findCoordinate(findLastUsedGoodCoordinate(c2, c3, Position.upper), Position.upper, false);
                                    c3 = findCoordinate(findLastUsedGoodCoordinate(c2, c3, Position.upper), Position.lower, false);
                                } else {
                                    c0 = findCoordinate(c3, Position.upper, false);
                                }
                            }
                        } else {
                            if (findLastUsedGoodCoordinate(c2, c3, Position.upper) != null) {
                                c0 = findCoordinate(findLastUsedGoodCoordinate(c2, c3, Position.upper), Position.upper, false);
                                c3 = findCoordinate(findLastUsedGoodCoordinate(c2, c3, Position.upper), Position.lower, false);
                            } else {
                                c0 = findCoordinate(c3, Position.upper, false);
                            }
                        }

                        cs = new Coordinate[]{c0, c1, c2, c3};
                        trapezes.add(new Trapeze(cs));


                    }
                    if (coordinate.getObject().isCoordinatePosition(coordinate, Position.lower)) {

                        //System.out.println("LOWER COORDINATE: " + coordinate.getX() + "-" + coordinate.getY());

                        c1 = coordinate;
                        c2 = findCoordinate(coordinate, Position.lower, true);
                        int index = c1.getObject().findCornerIdAroundTheClock(c1);

                        if (index == c1.getObject().coordinatesOrderByX.size() - 1) {
                            index = -1;
                        }
                        c0 = c1.getObject().coordinatesAroundTheClock.get(index + 1);
                        if (c2.getObject() != null) {
                            if (c2.getObject().findNeighbourCoordinate(c2, Position.upper).getX() > c0.getX()) {
                                c3 = c2.getObject().findNeighbourCoordinate(c2, Position.upper);
                                if (findLastUsedGoodCoordinate(c1, c3, Position.lower) != null) {
                                    c0 = findCoordinate(findLastUsedGoodCoordinate(c1, c3, Position.lower), Position.upper, false);
                                    c3 = findCoordinate(findLastUsedGoodCoordinate(c1, c3, Position.lower), Position.lower, false);
                                } else {
                                    c0 = findCoordinate(c3, Position.upper, false);
                                }
                            } else {
                                if (findLastUsedGoodCoordinate(c1, c0, Position.lower) != null) {

                                    c3 = findCoordinate(findLastUsedGoodCoordinate(c1, c0, Position.lower), Position.lower, false);
                                    c0 = findCoordinate(findLastUsedGoodCoordinate(c1, c0, Position.lower), Position.upper, false);
                                } else {
                                    c3 = findCoordinate(c0, Position.lower, false);
                                }
                            }
                        } else {
                            if (findLastUsedGoodCoordinate(c1, c0, Position.lower) != null) {
                                c3 = findCoordinate(findLastUsedGoodCoordinate(c1, c0, Position.lower), Position.lower, false);
                                c0 = findCoordinate(findLastUsedGoodCoordinate(c1, c0, Position.lower), Position.upper, false);
                            } else {
                                c3 = findCoordinate(c0, Position.lower, false);
                            }
                        }
                        cs = new Coordinate[]{c0, c1, c2, c3};
                        trapezes.add(new Trapeze(cs));
                    }
                }


            }
        }
        usedCoordinates.add(coordinate);

        if (isLastCoordinateOfObject(coordinate.getObject())) // ha az objektum utolsó koordinátája
        {
            openObjects.remove(coordinate.getObject());
        }
    }

    private static Coordinate findLastUsedGoodCoordinate(Coordinate original, Coordinate neighbour, Position position) {
//biztos jó magasságban van az y?
        Coordinate result = null;
        switch (position) {
            case upper:
                for (int i = usedCoordinates.size() - 1; i >= 0; i--) {
                    if ((usedCoordinates.get(i).getY() < original.getY() || usedCoordinates.get(i).getY() < neighbour.getY())
                            && usedCoordinates.get(i).getX() < original.getX()
                            && usedCoordinates.get(i).getX() > neighbour.getX()
                            && usedCoordinates.get(i).getObject() != original.getObject()
                            && usedCoordinates.get(i).getObject() != neighbour.getObject()) {
                        result = usedCoordinates.get(i);
                        break;
                    }
                }
                break;
            case lower:
                for (int i = usedCoordinates.size() - 1; i >= 0; i--) {
                    if ((usedCoordinates.get(i).getY() > original.getY() || usedCoordinates.get(i).getY() > neighbour.getY())
                            && usedCoordinates.get(i).getX() < original.getX()
                            && usedCoordinates.get(i).getX() > neighbour.getX()
                            && usedCoordinates.get(i).getObject() != original.getObject()
                            && usedCoordinates.get(i).getObject() != neighbour.getObject()) {
                        result = usedCoordinates.get(i);
                        break;
                    }
                }
                break;
            default:
                break;
        }

        if (result != null && openObjects.contains(result.getObject())) return null;

        return result;
    }

    private static boolean isLastCoordinateOfObject(Polygon object) {
        int usedCoordinatesNumber = 0;

        for (int i = 0; i < object.coordinatesAroundTheClock.size(); i++) {
            if (isUsed(object.coordinatesAroundTheClock.get(i))) usedCoordinatesNumber++;
        }

        return usedCoordinatesNumber == object.coordinatesAroundTheClock.size();
    }

    private static boolean isUsed(Coordinate coordinate) {
        for (int i = 0; i < usedCoordinates.size(); i++) {
            if (coordinate.getX() == usedCoordinates.get(i).getX() && coordinate.getY() == usedCoordinates.get(i).getY()) {
                return true;
            }
        }
        return false;
    }

    //endregion

    public static void startGenerating() throws Exception {
        initCoordinates();
        //writeCoordinates();
        for (int i = 0; i < coordinates.size(); i++) {
            if (!isUsed(coordinates.get(i))) {
                findTrapezeUltimate(i);
            }
        }
        lastTrapeze();
        checkSameTrapezes();

    }

    private static void lastTrapeze() {
        double lastX = coordinates.get(coordinates.size() - 1).getX();
        Coordinate[] cs = new Coordinate[]{
                new Coordinate(lastX, 0),
                new Coordinate(RoomModel.getWidth(), 0),
                new Coordinate(RoomModel.getWidth(), RoomModel.getHeight()),
                new Coordinate(lastX, RoomModel.getHeight())
        };
        trapezes.add(new Trapeze(cs));
    }

    /**
     * az összes objektum koordinátáit felveszi majd x kooridináta alapján sorrendezi
     */
    private static void initCoordinates() {
        for (Polygon object : RoomModel.objects) {
            coordinates.addAll(object.coordinatesAroundTheClock);
        }
        coordinates.sort(Comparator.comparingDouble(Coordinate::getX));

    }

    public static void writeTrapezes() {
        for (Trapeze trapeze : trapezes) {
            for (int i = 0; i < 4; i++) {
                System.out.println(trapeze.xpoints[i] + "-" + trapeze.ypoints[i]);
            }
            System.out.println();
        }
    }

    private static void checkSameTrapezes() {
        for (int i = 0; i < trapezes.size(); i++) {
            for (int j = 0; j < trapezes.size(); j++) {
                deleteSameTrapezes(i, j);
            }
        }
    }

    private static void deleteSameTrapezes(int original, int copy) {
        if (original != copy) {
            for (int k = 0; k < 4; k++) {
                if (trapezes.get(original).xpoints[k] != trapezes.get(copy).xpoints[k] || trapezes.get(original).ypoints[k] != trapezes.get(copy).ypoints[k])
                    return;

            }
        } else {
            return;
        }
        trapezes.remove(copy);
    }

}
