package com.annalaczko.onlab.model;

import java.awt.Polygon;

import java.util.List;

public class Room {

    private static Polygon corners;

    private static int width;
    private static int height;

    public static List<Polygon> objects;

    public static void setCorners (int _width, int _height){

        width=_width;
        height=_height;

    }

    public static double getWidth() {return width;}
    public static double getHeight() {return height;}

}
