package com.annalaczko.onlab.model.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

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

    public static void addObject(/*Polygon newObject*/){
       // objects.add(newObject);

        int  [] xpoints={200, 700, 600, 100} ;
        int  [] ypoints={200, 100, 500, 400 };

        objects.add(new Polygon(xpoints,ypoints,4));
        //objects.add(new Polygon(xpoints2,ypoints2,5));
    }

    public static double getWidth() {return width;}
    public static double getHeight() {return height;}

    public static void setHeight(int height) {
        RoomModel.height = height;
    }

    public static void setWidth(int width) {
        RoomModel.width = width;
    }

    /**
     * Visszaadja Coordinate osztályban a sarkait a szobának.
     * @param id sarok sorszáma. bal felül kezd
     * @return
     */

    public static Coordinate getCorner(int id){
        switch (id){
            case 0:
                return new Coordinate(0,0,null);
            case 1:
                return new Coordinate(width,0,null);
            case 2:
                return new Coordinate(width,height,null);
            case 3:
                return new Coordinate(0,height,null);
            default:
                return null;
        }
    }


    public static void sortObjects(){
        Collections.sort(objects, new Comparator<Polygon>() {
            @Override
            public int compare(Polygon o1, Polygon o2) {
                return Double.compare(o1.getFirstCorner().getY(), o2.getFirstCorner().getY());
            }
        });
    }
}
