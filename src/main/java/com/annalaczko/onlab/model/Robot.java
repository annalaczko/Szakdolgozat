package com.annalaczko.onlab.model;


public class Robot {
    private Coordinate location;
    private double speed=1; //pixel per milliseconds

    public void move (double distance, double degree ){
        int iteration= (int) Math.round(distance/speed);
        for (int i=0; i<iteration;i++){
            location.setX(location.getX()+Math.cos(Math.toRadians(degree))*speed);
            location.setY(location.getY()+Math.sin(Math.toRadians(degree))*speed);
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
