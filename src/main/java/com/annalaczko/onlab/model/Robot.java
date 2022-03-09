package com.annalaczko.onlab.model;


public class Robot {
    private static Coordinate location;
    private static int radius=20; //centimeters
    private static double speed=1; //pixel per milliseconds

    public static void move (double distance, double degree ){
        location.setX(location.getX()+Math.cos(Math.toRadians(degree))*speed);
        location.setY(location.getY()+Math.sin(Math.toRadians(degree))*speed);

    }

    public static int getRadius() {
        return radius;
    }
}
