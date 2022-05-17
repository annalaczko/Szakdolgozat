package com.annalaczko.onlab.model;

import java.awt.Polygon;

import java.util.ArrayList;
import java.util.List;

public class RoomModel {

    private Polygon corners;

    private static int width;
    private static int height;

    public static ArrayList<Polygon> objects=new ArrayList<>();

    public static void setCorners (int _width, int _height){

        width=_width;
        height=_height;

    }

    public static void addObject(/*Polygon newObject*/){
       // objects.add(newObject);

        int  [] xpoints={50, 500, 240} ;
        int  [] ypoints={60, 90, 460 };

        objects.add(new Polygon(xpoints,ypoints,3));
        //objects.add(new Polygon(xpoints2,ypoints2,5));
    }

    public static double getWidth() {return width;}
    public static double getHeight() {return height;}

    public static Coordinate getCorner(int id){
        switch (id){
            case 0:
                return new Coordinate(0,0);
            case 1:
                return new Coordinate(width,0);
            case 2:
                return new Coordinate(width,height);
            case 3:
                return new Coordinate(0,height);
            default:
                return null;
        }
    }


}
