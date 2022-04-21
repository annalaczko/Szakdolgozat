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

        int  [] xpoints={100, 210, 130} ;
        int  [] ypoints={60, 90, 210 };

        objects.add(new Polygon(xpoints,ypoints,3));
        //objects.add(new Polygon(xpoints2,ypoints2,5));
    }

    public static double getWidth() {return width;}
    public static double getHeight() {return height;}


}
