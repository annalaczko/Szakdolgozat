package com.annalaczko.onlab.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Polygon extends java.awt.Polygon {

    public ArrayList<Coordinate> coordinatesInOrder=new ArrayList<>();
    public ArrayList<Coordinate> coordinatesAroundTheClock=new ArrayList<>();

    public Polygon(){}

    public Polygon (int [] x, int [] y, int db) {
        super(x,y,db);
        initCoordinatesAroundTheClock();
        initCoordinatesInOrder();
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
            coordinatesInOrder.add(new Coordinate(xpoints[i], ypoints[i],this));
        }
        coordinatesInOrder.sort(new Comparator<>() {
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
