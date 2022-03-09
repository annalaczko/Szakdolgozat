package com.annalaczko.onlab.model;


public class Robot {
    private static double x=0;
    private static double y=0;
    private static int radius=20; //centimeters
    private static double speed=0.01; //cm per milliseconds

    public static void move (double distance, double degree ){
        x=x+Math.cos(Math.toRadians(degree))*speed;
        y=y+Math.sin(Math.toRadians(degree))*speed;

    }

    public static double getY() {
        return y;
    }

    public static double getX() {
        return x;
    }

    public static int getRadius() {
        return radius;
    }
}
