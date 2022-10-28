package com.annalaczko.onlab.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Polygon extends java.awt.Polygon {

    public ArrayList<Coordinate> coordinatesInOrder=new ArrayList<>();

    public Polygon(){}

    public Polygon (int [] x, int [] y, int db) {
        super(x,y,db);
        initCoordinatesInOrder();
    }

    public Coordinate getFirstCorner(){
        Coordinate coor;
        coor = new Coordinate(super.xpoints[0], super.ypoints[0]);
        for (int i = 0; i < super.npoints; i++) {
            if (coor.getX()>super.xpoints[i]) coor=new Coordinate(super.xpoints[i], super.ypoints[i]);
        }
        return coor;
    }

    public Coordinate getLastCorner(){
        Coordinate coor;
        coor = new Coordinate(super.xpoints[0], super.ypoints[0]);
        for (int i = 0; i < super.npoints; i++) {
            if (coor.getX()<super.xpoints[i]) coor=new Coordinate(super.xpoints[i], super.ypoints[i]);
        }
        return coor;
    }

    private void initCoordinatesInOrder(){
        for (int i=0; i<npoints; i++) {
            coordinatesInOrder.add(new Coordinate(xpoints[i], ypoints[i]));
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
