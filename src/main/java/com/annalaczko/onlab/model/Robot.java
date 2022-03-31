package com.annalaczko.onlab.model;


import java.awt.*;

public class Robot {
    private static int radius=20; //centimeters
    private static Point location= new Point(radius,radius);
    private static double speed=0.2; //centimeters per 10 milliseconds

    public static void move (double degree ){
        location.setLocation(location.getX()+Math.cos(Math.toRadians(degree))*speed,location.getY()+Math.sin(Math.toRadians(degree))*speed );
    }

    public static int getRadius() {
        return radius;
    }

    public static void walkTetragon(Polygon tetra) throws Exception {
        if (tetra.npoints!=4) throw new Exception("Polygon is not a tetragon");

    }

    public static Point getLocation() {
        return location;
    }
}
