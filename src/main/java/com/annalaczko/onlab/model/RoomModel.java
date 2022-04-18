package com.annalaczko.onlab.model;

import java.awt.Polygon;

import java.util.List;

public class RoomModel {

    private Polygon corners;

    private static int width;
    private static int height;

    public List<Polygon> objects;

    public static void setCorners (int _width, int _height){

        width=_width;
        height=_height;

    }

    public void addObject(Polygon newObject){
        objects.add(newObject);
    }

    public static double getWidth() {return width;}
    public static double getHeight() {return height;}



}
