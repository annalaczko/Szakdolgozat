package com.annalaczko.onlab.model;

import com.annalaczko.onlab.model.data.*;

import java.util.ArrayList;
import java.util.Collections;

public class TrapezeGenerator {

    public static ArrayList<Trapeze> trapezes=new ArrayList<>(); //trapézok listája
    public static ArrayList<Coordinate> coordinates=new ArrayList<>();
    private static ArrayList<Polygon> openObjects=new ArrayList<>();
    private static Polygon closestObject;

    //Todo: ezt törölni kell majd
    public static ArrayList<Trapeze> finaltrapezes=new ArrayList<>(); //trapézok listája sorrendben ahogyan majd végig megy a robot.
    private static ArrayList<Coordinate> usedCoordinates= new ArrayList<>();

    //region Private methods for generating trapezes
    private static void addFirstTrapeze (Coordinate coordinate){
        int i=0;
        openObjects.add(coordinate.getObject());
        Coordinate [] cs;

        //hozzaadni teglalapot
        cs =new Coordinate[] {
                RoomModel.getCorner(0),
                new Coordinate(coordinate.getX(), 0, null),
                new Coordinate(coordinate.getX(), RoomModel.getHeight(),null),
                RoomModel.getCorner(3)
        };
        trapezes.add(new Trapeze(cs));
        usedCoordinates.add(coordinate);

        //nyilik masik objektum is epp?
        //hozzaadni ha igen
        i++;
        while (coordinates.get(i).getX()==coordinate.getX()){
            usedCoordinates.add(coordinates.get(i));
            if (coordinates.get(i).getObject()!=coordinate.getObject()){
                openObjects.add(coordinates.get(i).getObject());
            }
            i++;
        }
    }

    private static boolean isFirstXCoordinateAcceptable(Coordinate coordinate, Polygon objectOfCoordinate, Coordinate coordinateOfOpenObject){
        if (!(coordinateOfOpenObject.getX()<=coordinate.getX())) return false;
        switch (objectOfCoordinate.getCoordinatePosition(coordinate)){
            case upper:
                    return coordinateOfOpenObject.getY()<coordinate.getY();
            case lower:
                    return coordinateOfOpenObject.getY()>coordinate.getY();
            default:
                break;
        }

        return false ;
    }

    private static boolean isSecondXCoordinateAcceptable(Coordinate coordinate, Polygon objectOfCoordinate, Coordinate coordinateOfOpenObject){
        boolean result1=false;
        boolean result2=false;

        if (!(coordinateOfOpenObject.getX()>=coordinate.getX())) return false;
        switch (objectOfCoordinate.getCoordinatePosition(coordinate)){
            case upper:
                result1= coordinateOfOpenObject.getY()<coordinate.getY();
                break;
            case lower:
                result1= coordinateOfOpenObject.getY()>coordinate.getY();
                break;
            default:
                break;
        }

        for (Coordinate coordinate1: coordinateOfOpenObject.getObject().coordinatesAroundTheClock) {
            if ((coordinate1.getX()<=coordinate.getX())) result2=true;
        }
        //TODO: ez a második if nem tökéletes, lehet, hogy az objektum másik irányból öleli a tárgyobjektumunkat
        return result1&&result2;
    }

    //TODO: ha egy koordináta már "used" ne csináljunk vele valami újat

    private static boolean isObjectOnRightSide(Coordinate coordinate){
        Polygon objectOfCoordinate = coordinate.getObject();
        //DONE lehet hogy nem lesz X koordináta az objekten belül
        for (Polygon otherOpenObject: openObjects) {
            for (Coordinate coordinateOfOpenObject: otherOpenObject.coordinatesOrderByX) {
                if (isFirstXCoordinateAcceptable(coordinate, objectOfCoordinate, coordinateOfOpenObject) || isSecondXCoordinateAcceptable(coordinate, objectOfCoordinate, coordinateOfOpenObject)){
                    closestObject=  otherOpenObject;
                    return true;
                }

            }
        }
        return false;

    }

    private static int hasClosestObjectCoordinateHereIndex(Coordinate coordinate){
        switch (coordinate.getObject().getCoordinatePosition(coordinate)){
            case upper:
                for (Coordinate coordinateOfClosestObject: closestObject.coordinatesAroundTheClock) {
                    if (coordinateOfClosestObject.getX()==coordinate.getX() && closestObject.isCoordinatePosition(coordinateOfClosestObject, Position.lower)){
                        return closestObject.coordinatesAroundTheClock.indexOf(coordinateOfClosestObject);
                    }
                }
                break;
            case lower:
                for (Coordinate coordinateOfClosestObject: closestObject.coordinatesAroundTheClock) {
                    if (coordinateOfClosestObject.getX()==coordinate.getX() && closestObject.isCoordinatePosition(coordinateOfClosestObject, Position.upper)){
                        return closestObject.coordinatesAroundTheClock.indexOf(coordinateOfClosestObject);
                    }
                }
                break;
            default:
                break;
        }
        return -1;
    }


    private static void addComplexTrapezeWithExisitingCoordinates (Coordinate coordinate, Coordinate otherCoordinate){
        Polygon objectOfOtherCoordinate=otherCoordinate.getObject();
        Polygon objectOfCoordinate = coordinate.getObject();

        int indexOfCoordinate;
        int indexOfOtherCoordinateNeighbor;
        Coordinate [] cs=new Coordinate[4];

        //TODO: mi van ha a "coordinate" az első a trapézból? lol

        //TODO nem jó a trapégenerálás mert az első két koordináta nem lesz egymás alatt/felett

        switch (objectOfCoordinate.getCoordinatePosition(coordinate)){
            case upper:
                indexOfCoordinate=objectOfCoordinate.coordinatesAroundTheClock.indexOf(otherCoordinate);
                indexOfOtherCoordinateNeighbor=objectOfOtherCoordinate.coordinatesAroundTheClock.indexOf(otherCoordinate)+1;
                if (indexOfOtherCoordinateNeighbor-1==objectOfOtherCoordinate.coordinatesAroundTheClock.size()-1) indexOfOtherCoordinateNeighbor=0;

                cs = new Coordinate[] {
                        new Coordinate(objectOfOtherCoordinate.coordinatesAroundTheClock.get(indexOfOtherCoordinateNeighbor).getX(), objectOfOtherCoordinate.coordinatesAroundTheClock.get(indexOfOtherCoordinateNeighbor).getY(),null),
                        new Coordinate(otherCoordinate.getX(), otherCoordinate.getY(),null),
                        new Coordinate(coordinate.getX(), coordinate.getY(),null),
                        new Coordinate( objectOfCoordinate.coordinatesAroundTheClock.get(indexOfCoordinate-1).getX(),
                                objectOfCoordinate.coordinatesAroundTheClock.get(indexOfCoordinate-1).getY(),null)
                };

                if (indexOfCoordinate==0){
                    cs = new Coordinate[] {
                            new Coordinate(objectOfOtherCoordinate.coordinatesAroundTheClock.get(indexOfOtherCoordinateNeighbor).getX(), objectOfOtherCoordinate.coordinatesAroundTheClock.get(indexOfOtherCoordinateNeighbor).getY(),null),
                            new Coordinate(otherCoordinate.getX(), otherCoordinate.getY(),null),
                            new Coordinate(coordinate.getX(), RoomModel.getHeight(),null),
                            new Coordinate( objectOfCoordinate.coordinatesAroundTheClock.get(indexOfCoordinate-1).getX(),
                                    objectOfCoordinate.coordinatesAroundTheClock.get(indexOfCoordinate-1).getY(),null)
                    };
                }

                break;
            case lower:
                indexOfCoordinate=objectOfCoordinate.coordinatesAroundTheClock.indexOf(otherCoordinate);
                indexOfOtherCoordinateNeighbor=objectOfOtherCoordinate.coordinatesAroundTheClock.indexOf(otherCoordinate)-1;
                if (indexOfOtherCoordinateNeighbor+1==0) indexOfOtherCoordinateNeighbor=objectOfOtherCoordinate.coordinatesAroundTheClock.size()-1;

                cs = new Coordinate[] {
                        new Coordinate(objectOfCoordinate.coordinatesAroundTheClock.get(indexOfCoordinate+1).getX(),
                                objectOfCoordinate.coordinatesAroundTheClock.get(indexOfCoordinate+1).getY(),null),
                        new Coordinate(coordinate.getX(), coordinate.getY(),null),
                        new Coordinate(otherCoordinate.getX(), otherCoordinate.getY(),null),
                        new Coordinate(objectOfOtherCoordinate.coordinatesAroundTheClock.get(indexOfOtherCoordinateNeighbor).getX(), objectOfOtherCoordinate.coordinatesAroundTheClock.get(indexOfOtherCoordinateNeighbor).getY(),null )
                };

                break;
            default:
                break;
        }
        trapezes.add(new Trapeze(cs) );

    }

    private static Coordinate findTrapezeCoordinateOnObject(Coordinate coordinate, Polygon object){
        boolean done = false;
        int yCoordinate=(int)coordinate.getY();
        while (!done){
            switch (coordinate.getObject().getCoordinatePosition(coordinate)){
                case upper:
                    yCoordinate--;
                    break;
                case lower:
                    yCoordinate++;
                    break;
                default:
                    break;
            }
            if (object.contains((int)coordinate.getX(),yCoordinate)){
                done=true;
            }
        }
        Coordinate result=new Coordinate(coordinate.getX(), yCoordinate, null);
        if (object.coordinatesAroundTheClock.indexOf(result)>=0) usedCoordinates.add(result);
        //todo: kimarad amikor utolsó koordinátája a testnek
        return result;
    }

    private static void multipleObjectState(Coordinate coordinate){
        if (isObjectOnRightSide(coordinate)) //DONE ha bármelyik másik nyitott objektum ugyanazon az oldalon van, mint a coordinate
        {
            //DONE megtalálni a legközelebbit "fentről vagy lentről" koordinátától függően
            if (hasClosestObjectCoordinateHereIndex(coordinate)>=0) //DONE van itt koordináta?
            {
                Coordinate usedCoordinate=closestObject.coordinatesAroundTheClock.get(hasClosestObjectCoordinateHereIndex(coordinate);
                usedCoordinates.add(usedCoordinate);

                if (usedCoordinate.getObject().getLastCorner().getX()==usedCoordinate.getX())//DONE koordináta lezárja az objektumot
                {
                    //DONE lezárni objektumot
                    openObjects.remove(usedCoordinate.getObject());
                }

                //TODO hozzáadni a trapézt
            }
            else {
                //TODO kiszámolni a koordinátát
                //TODO megtalálni melyik két pont között van
                //TODO mi lesz a bal szomszédja
                //TODO talált objektumot felülírni egy olyannal ahova jó helyre be van illeszte a koordináta
                //TODO talált koordináta használt lesz
            }
        }
        else
        {
            //DONE ugyanaz az eset, mint     ha nem lenne nyitott objektum

            switch (coordinate.getObject().getCoordinatePosition(coordinate)){
                case upper:
                    upperSimpleTrapeze(coordinate,coordinate.getObject());
                    break;
                case lower:
                    lowerSimpleTrapeze(coordinate,coordinate.getObject());
                    break;
                default:
                    break;
            }
        }
    }

    private static void singleObjectState(Coordinate coordinate){

        Polygon object=coordinate.getObject();

        if (object.isCoordinatePosition(coordinate, Position.upper))//ha i. koordináta feljebb van, mint első és utolsó
        {
            //akkor i-1 objektumi koordinátával és a szoba tetejével trapéz
            trapezes.add(upperSimpleTrapeze(coordinate,object));
        }

        if (object.isCoordinatePosition(coordinate,Position.lower))
        {
            //akkor i+1 (vagy 0) objektumi koordinátával és a szoba tetejével trapéz
            trapezes.add(lowerSimpleTrapeze(coordinate,object));
        }

        if (object.isCoordinatePosition(coordinate, Position.upper) && object.isCoordinatePosition(coordinate,Position.lower))
            try {
                throw  new Exception("A coordinata se alul, se felül sincs, rip: " + coordinates.indexOf(coordinate));
            } catch (Exception e) {
                e.printStackTrace();
            }

        usedCoordinates.add(coordinate);

        checkSameXCoordinates(coordinate);
    }

    private static ArrayList<Coordinate> findSameXCoordinatesFromSameSide (Coordinate coordinate){
        Position coordinatePosition= coordinate.getObject().getCoordinatePosition(coordinate);

        int index = coordinates.indexOf(coordinate);

        ArrayList<Coordinate> result=new ArrayList<>();

        while (coordinates.get(index).getX()==coordinate.getX()){
            switch (coordinatePosition){
                case upper:
                    if (coordinates.get(index).getY()<coordinate.getY()) result.add(coordinates.get(index));
                    break;
                case lower:
                    if (coordinates.get(index).getY()>coordinate.getY()) result.add(coordinates.get(index));
                    break;
                default:
                    try {
                        throw new Exception("coordinatePosition is same");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;

            }
            index++;
        }
        if (result.size()>=2) sortCoordinatesFromSameSide(result);
        return result;
    }

    private static void checkSameXCoordinates (Coordinate coordinate){
        ArrayList<Coordinate> coordinatesFromSameSide =findSameXCoordinatesFromSameSide(coordinate);

        if (coordinatesFromSameSide.size()==0) return;

        int lastIndex=coordinatesFromSameSide.size()-1;

        switch (coordinate.getObject().getCoordinatePosition(coordinate)){
            case upper:
                openObjects.add(coordinatesFromSameSide.get(lastIndex).getObject());
                usedCoordinates.add(coordinatesFromSameSide.get(lastIndex));
                if (coordinatesFromSameSide.get(lastIndex-1).getObject().equals(coordinatesFromSameSide.get(lastIndex).getObject())) usedCoordinates.add(coordinatesFromSameSide.get(lastIndex-1));
                break;
            case lower:
                openObjects.add(coordinatesFromSameSide.get(0).getObject());
                usedCoordinates.add(coordinatesFromSameSide.get(0));
                if (coordinatesFromSameSide.get(1).getObject().equals(coordinatesFromSameSide.get(0).getObject())) usedCoordinates.add(coordinatesFromSameSide.get(1));
                break;
            default:
                break;
        }


    }

    private static Trapeze upperSimpleTrapeze(Coordinate coordinate, Polygon object){
        int indexOfCoordinate =object.coordinatesAroundTheClock.indexOf(coordinate);

        Coordinate [] cs;
        cs = new Coordinate[] {
                new Coordinate(object.coordinatesAroundTheClock.get(indexOfCoordinate-1).getX(), 0,null),
                new Coordinate(coordinate.getX(), 0,null),
                new Coordinate(coordinate.getX(), coordinate.getY(),null),
                new Coordinate( object.coordinatesAroundTheClock.get(indexOfCoordinate-1).getX(),
                        object.coordinatesAroundTheClock.get(indexOfCoordinate-1).getY(),null)
        };

        return new Trapeze(cs);
    }

    private static Trapeze lowerSimpleTrapeze(Coordinate coordinate,  Polygon object){
        int indexOfCoordinate =object.coordinatesAroundTheClock.indexOf(coordinate);

        if (indexOfCoordinate == object.npoints) indexOfCoordinate=-1;
        Coordinate [] cs;

        cs = new Coordinate[] {
                new Coordinate(object.coordinatesAroundTheClock.get(indexOfCoordinate+1).getX(), object.coordinatesAroundTheClock.get(indexOfCoordinate+1).getX(),null),
                new Coordinate(coordinate.getX(), coordinate.getY(),null),
                new Coordinate(coordinate.getX(), RoomModel.getHeight(),null),
                new Coordinate( object.coordinatesAroundTheClock.get(indexOfCoordinate+1).getX(),
                        RoomModel.getHeight(),null)
        };

        return new Trapeze(cs);
    }

    private static int findTrapezeUltimate(int i){

        Coordinate coordinate = coordinates.get(i);

        //TODO: mi van ha nyílik épp egy új trapéz és annak a koordinátája kerül sorra?

        if (i==0){
            addFirstTrapeze(coordinate);
        } else {
            if (!usedCoordinates.contains(coordinate)){
                if (openObjects.size()>=1)// van másik nyitott objektum
                {
                    multipleObjectState(coordinate);
                }
                else
                {
                    singleObjectState(coordinate);
                    //TODO: mi van ha 0 opened van? akkor téglalapot kell rajzolni
                }
            }
        }


        if (coordinates.get(i).getObject().getLastCorner()==coordinates.get(i)) // ha az objektum utolsó koordinátája
        {
            openObjects.remove(coordinates.get(i).getObject());
        }
        //i Coordináta "elhasznalva"  -> mivel iterálgatunk, az megoldja
        return i;
    }

    //endregion


    //region Old methods
    public static  void findTrapeze(int i, int j, Polygon object){
        double x = object.coordinatesOrderByX.get(j).getX();
        double y = object.coordinatesOrderByX.get(j).getY();
        Coordinate [] cs=new Coordinate[4];
        if (j == 0) {
            if (i == 0) {
                cs =new Coordinate[] {
                        RoomModel.getCorner(0),
                        new Coordinate(x, 0, null),
                        new Coordinate(x, RoomModel.getHeight(),null),
                        RoomModel.getCorner(3)
                };
            } else {
                cs = new Coordinate[]{
                        new Coordinate(RoomModel.objects.get(i - 1).getLastCorner().getX(), 0,null),
                        new Coordinate(x, 0,null),
                        new Coordinate(x, RoomModel.getHeight(),null),
                        new Coordinate(RoomModel.objects.get(i - 1).getLastCorner().getX(), RoomModel.getHeight(),null)
                };
            }
        } else {
            int id=object.findCornerId(new Coordinate(x,y,null));
            if (y<object.getFirstCorner().getY()||j==object.npoints-1){
                cs =new Coordinate[] {
                        new Coordinate(object.xpoints[id-1], 0,null),
                        new Coordinate(x, 0,null),
                        new Coordinate(x, y,null),
                        new Coordinate(object.xpoints[id-1], object.ypoints[id-1],null)
                };
                if (j==object.npoints-1) trapezes.add(new Trapeze(cs));
            }
            if (y>=object.getFirstCorner().getY()||j==object.npoints-1){
                if (id==object.npoints-1) { //||j==object.npoints-1
                    id=-1;
                }
                cs = new Coordinate[]{
                        new Coordinate(object.xpoints[id + 1], object.ypoints[id + 1],null),
                        new Coordinate(x, y,null),
                        new Coordinate(x, RoomModel.getHeight(),null),
                        new Coordinate(object.xpoints[id + 1],RoomModel.getHeight(),null )
                };
            }

        }
        trapezes.add(new Trapeze(cs));

    }

    public static void toTrapeze(){

        for (int i=0; i<RoomModel.objects.size(); i++) {
            Polygon object = RoomModel.objects.get(i);
            for (int j = 0; j < object.npoints; j++) {
                findTrapeze(i,j,object);
            }
        }

        Coordinate [] cs ={
                new Coordinate(RoomModel.objects.get(RoomModel.objects.size()-1).getLastCorner().getX(), 0,null),
                RoomModel.getCorner(1),
                RoomModel.getCorner(2),
                new Coordinate(RoomModel.objects.get(RoomModel.objects.size()-1).getLastCorner().getX(), RoomModel.getHeight(),null)
        };

        trapezes.add(new Trapeze(cs));

        System.out.println("TRAPEZES");
        for (Trapeze trapeze: trapezes) {
            for (int i = 0; i < 4; i++) {
                System.out.println(trapeze.xpoints[i]+ "-" + trapeze.ypoints[i]);
            }
            System.out.println();

        }
        System.out.println("TRAPEZES");

    }
    //endregions

    /**
     * az összes objektum koordinátáit felveszi majd x kooridináta alapján sorrendezi
     */
    public static void initCoordinates(){
        for ( Polygon object: RoomModel.objects) {
            for (Coordinate coordinate: object.coordinatesAroundTheClock){
                coordinates.add(coordinate);
            }
        }
        Collections.sort(coordinates, (o1, o2) -> Double.compare(o1.getX(), o2.getX()));

    }

    private static void sortCoordinatesFromSameSide(ArrayList<Coordinate> coordinatesFromSameSide){
        Collections.sort(coordinatesFromSameSide, (o1, o2) -> Double.compare(o1.getY(), o2.getY()));

    }
}
