package com.annalaczko.onlab.model;


public class Robot {
    private Coordinate location;
    private int radius=25; //centimeters
    private double speed=1; //pixel per milliseconds

    public void move (double distance, double degree ){
        location.setX(location.getX()+Math.cos(Math.toRadians(degree))*speed);
        location.setY(location.getY()+Math.sin(Math.toRadians(degree))*speed);

    }
}
