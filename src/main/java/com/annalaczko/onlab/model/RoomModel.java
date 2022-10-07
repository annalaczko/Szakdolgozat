package com.annalaczko.onlab.model;

import java.awt.Polygon;

import java.util.ArrayList;

/**
 * Szoba backendje
 */

public class RoomModel {

    private Polygon corners;

    private static int width;
    private static int height;

    public static ArrayList<Polygon> objects=new ArrayList<>();

    /**
     * Szoba inicializálása
     * @param _width
     * @param _height
     */

    public static void setCorners (int _width, int _height){

        width=_width;
        height=_height;

    }

    /**
     * TODO: ez mi
     */

    public static void addObject(/*Polygon newObject*/){
       // objects.add(newObject);

        int  [] xpoints={0, 0, 0} ;
        int  [] ypoints={0, 0, 0 };

        objects.add(new Polygon(xpoints,ypoints,3));
        //objects.add(new Polygon(xpoints2,ypoints2,5));
    }

    public static double getWidth() {return width;}
    public static double getHeight() {return height;}

    /**
     * Visszaadja Coordinate osztályban a sarkait a szobának.
     * @param id sarok sorszáma. bal felül kezd
     * @return
     */

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
