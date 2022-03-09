package com.annalaczko.onlab.model;

import java.awt.Polygon;

import java.util.List;

public class Room {

    private static Polygon corners;

    private static int width;
    private static int height;

    private List<Polygon> objects;

    public static void setCorners (int _width, int _height){

        width=_width;
        height=_height;

        /*int [] cornerX ={0, width, width, 0};
        int [] cornerY ={0, 0, height,height};
        corners=new Polygon(cornerX,cornerY,4);*/
    }

    public static double getWidth() {return width;}
    public static double getHeight() {return height;}

}
