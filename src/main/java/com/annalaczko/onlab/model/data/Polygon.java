package com.annalaczko.onlab.model.data;

import java.util.ArrayList;
import java.util.Comparator;

public class Polygon extends java.awt.Polygon {

    public ArrayList<Coordinate> coordinatesOrderByX =new ArrayList<>();
    public ArrayList<Coordinate> coordinatesAroundTheClock=new ArrayList<>();

    public Polygon(){}

    public Polygon (int [] x, int [] y, int db) {
        super(x,y,db);
        initCoordinatesAroundTheClock();
        initCoordinatesInOrder();
    }

    public boolean isCoordinatePosition(Coordinate coordinate, Position position){
        switch (position){
            case upper:
                return (coordinate.getY()<this.getFirstCorner().getY() || coordinate.getY()<this.getLastCorner().getY());
            case lower:
                return (coordinate.getY()>this.getFirstCorner().getY() || coordinate.getY()>this.getLastCorner().getY());
            default:
                try {
                    throw new Exception("Baj van az isPositionben!");
                } catch (Exception e) {
                    e.printStackTrace();
                }
        }
        return false;
    }

    public Position getCoordinatePosition(Coordinate coordinate){
            if (coordinate.getY()<this.getFirstCorner().getY() || coordinate.getY()<this.getLastCorner().getY()) return Position.upper;
            if (coordinate.getY()>this.getFirstCorner().getY() || coordinate.getY()>this.getLastCorner().getY()) return Position.lower;
        try {
            throw new Exception("GetCoordinatePositionben se alul se felül sincs");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Position.same;
    }


    public Coordinate getFirstCorner(){
        Coordinate coor;
        coor=coordinatesAroundTheClock.get(0);
        for (Coordinate coordinate: coordinatesAroundTheClock) {
            if (coor.getX()>coordinate.getX()) coor=coordinate;
        }
        return coor;
    }

    public Coordinate getLastCorner(){
        Coordinate coor;
        coor=coordinatesAroundTheClock.get(0);
        for (Coordinate coordinate: coordinatesAroundTheClock) {
            if (coor.getX()<coordinate.getX()) coor=coordinate;
        }
        return coor;
    }

    private void initCoordinatesAroundTheClock(){
        for (int i=0; i<npoints; i++) {
            coordinatesAroundTheClock.add(new Coordinate(xpoints[i], ypoints[i],this));
        }


    }

    private void initCoordinatesInOrder(){
        for (int i=0; i<npoints; i++) {
            coordinatesOrderByX.add(new Coordinate(xpoints[i], ypoints[i],this));
        }
        coordinatesOrderByX.sort(new Comparator<>() {
            @Override
            public int compare(Coordinate o1, Coordinate o2) {
                return Double.compare(o1.getX(), o2.getX());
            }
        });

    }

    public int findCornerId(Coordinate cs){
        for (int i=0; i<npoints; i++) {
            if (xpoints[i]==cs.getX() && ypoints[i]==cs.getY()) return i;
        }
        return -69;
    }
}
