package com.annalaczko.onlab.model;


import java.awt.*;

public class Robot {
    private static int radius=20; //centimeters
    private static Coordinate location= new Coordinate(radius,radius);
    private static double speed=1; //centimeters per 10 milliseconds

    public static void move (double degree ){
        double x=  location.getX()+Math.cos(Math.toRadians(degree))*speed;
        double y= location.getY()+Math.sin(Math.toRadians(degree))*speed;
        location.setLocation(x,y);
    }

    public static int getRadius() {
        return radius;
    }

    public static void walkTetragon(Polygon tetra) throws Exception {
        if (tetra.npoints!=4) throw new Exception("Polygon is not a tetragon");

    }

    public static Coordinate getLocation() {
        return location;
    }
}
