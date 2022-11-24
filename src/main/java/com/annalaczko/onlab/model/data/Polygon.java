package com.annalaczko.onlab.model.data;

import java.util.ArrayList;
import java.util.Comparator;

public class Polygon extends java.awt.Polygon {

    public ArrayList<Coordinate> coordinatesOrderByX = new ArrayList<>();
    public ArrayList<Coordinate> coordinatesAroundTheClock = new ArrayList<>();

    private ArrayList<Coordinate[]> horizontalEdges = new ArrayList<>();

    public Polygon() {
    }

    public Polygon(int[] x, int[] y, int db) {
        super(x, y, db);
        initCoordinatesAroundTheClock();
        initCoordinatesInOrder();
        initHorizontalEdges();
    }

    private void initHorizontalEdges() {
        for (Coordinate coor1 : coordinatesOrderByX) {
            for (Coordinate coor2 : coordinatesOrderByX) {
                if (coor1 != coor2 && coor1.getX() == coor2.getX()) {
                    horizontalEdges.add(new Coordinate[]{coor1, coor2});
                }
            }
        }
    }

    private boolean edgeDetection(double x, double y) {
        for (Coordinate[] coorPairs : horizontalEdges) {
            double min = Math.min(coorPairs[0].getY(), coorPairs[1].getY());
            double max = Math.max(coorPairs[0].getY(), coorPairs[1].getY());
            if ((coorPairs[0].getX() == x) && (y >= min) && (y <= max)) return true;
            min = Math.min(coorPairs[0].getX(), coorPairs[1].getX());
            max = Math.max(coorPairs[0].getX(), coorPairs[1].getX());
            if ((coorPairs[0].getY() == y) && (x >= min) && (x <= max)) return true;
        }

        return false;
    }

    public boolean isCoordinatePosition(Coordinate coordinate, Position position) {
        switch (position) {
            case upper:
                if (!this.contains(coordinate.getX(), coordinate.getY() - 1)) return true;
                break;
            case lower:
                if (!this.contains(coordinate.getX(), coordinate.getY() + 1)) return true;
                break;
            default:
                try {
                    throw new Exception("Baj van az isPositionben!");
                } catch (Exception e) {
                    e.printStackTrace();
                }
        }
        return false;
    }

    public Coordinate getFirstCorner() {
        Coordinate coor;
        coor = coordinatesAroundTheClock.get(0);
        for (Coordinate coordinate : coordinatesAroundTheClock) {
            if (coor.getX() > coordinate.getX()) coor = coordinate;
        }
        return coor;
    }

    public Coordinate getLastCorner() {
        Coordinate coor;
        coor = coordinatesAroundTheClock.get(0);
        for (Coordinate coordinate : coordinatesAroundTheClock) {
            if (coor.getX() < coordinate.getX()) coor = coordinate;
        }
        return coor;
    }

    private void initCoordinatesAroundTheClock() {
        for (int i = 0; i < npoints; i++) {
            coordinatesAroundTheClock.add(new Coordinate(xpoints[i], ypoints[i], this));
        }


    }

    private void initCoordinatesInOrder() {
        for (int i = 0; i < npoints; i++) {
            coordinatesOrderByX.add(new Coordinate(xpoints[i], ypoints[i], this));
        }
        coordinatesOrderByX.sort(Comparator.comparingDouble(Coordinate::getX));

    }

    public int findCornerId(Coordinate cs) {
        for (int i = 0; i < npoints; i++) {
            if (xpoints[i] == cs.getX() && ypoints[i] == cs.getY()) return i;
        }
        return -69;
    }

    public int findCornerIdAroundTheClock(Coordinate cs) {
        for (int i = 0; i < coordinatesAroundTheClock.size(); i++) {
            if (cs.equalsWithMarginOfError(coordinatesAroundTheClock.get(i))) {
                return i;
            }

        }
        return -69;
    }

    public boolean findCorner(Coordinate cs) {
        for (int i = 0; i < npoints; i++) {
            if (xpoints[i] == cs.getX() && ypoints[i] == cs.getY()) return true;
        }
        return false;
    }

    public Coordinate findNeighbourCoordinate(Coordinate coordinate) throws Exception {
        Coordinate neighbourCoordinate = null;
        switch (getUnknownCoordinatePosition(coordinate)) {

            case upper:
                for (int i = coordinatesOrderByX.size() - 1; i >= 0; i--) {
                    if (coordinatesOrderByX.get(i).getX() < coordinate.getX() && isCoordinatePosition(coordinatesOrderByX.get(i), Position.upper))
                        neighbourCoordinate = coordinatesOrderByX.get(i);
                }
                break;
            case lower:
                for (int i = coordinatesOrderByX.size() - 1; i >= 0; i--) {
                    if (coordinatesOrderByX.get(i).getX() < coordinate.getX() && isCoordinatePosition(coordinatesOrderByX.get(i), Position.lower))
                        neighbourCoordinate = coordinatesOrderByX.get(i);
                }
                break;
            default:
                break;
        }

        if (neighbourCoordinate == null)
            throw new Exception("neighbourCoordinate is null in Polygon findNeighbourCoordinate" + getUnknownCoordinatePosition(coordinate).toString());

        return neighbourCoordinate;
    }

    public Position getUnknownCoordinatePosition(Coordinate coordinate) {
        if (this.contains(coordinate.getX(), coordinate.getY() + 2)) {
            return Position.upper;
        } else {
            return Position.lower;
        }
    }

    @Override
    public boolean contains(double x, double y) {

        if (horizontalEdges.size() > 2)
            return super.contains(x, y) || findCorner(new Coordinate(x, y)) || edgeDetection(x, y);

        return super.contains(x, y) || findCorner(new Coordinate(x, y));
    }

    @Override
    public boolean contains(int x, int y) {
        if (horizontalEdges.size() > 2)
            return super.contains(x, y) || findCorner(new Coordinate(x, y)) || edgeDetection(x, y);
        return super.contains(x, y) || findCorner(new Coordinate(x, y));
    }
}
