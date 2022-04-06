package com.annalaczko.onlab.model;

public class Coordinate {
    private double Y;
    private double X;

    public Coordinate (double x, double y){
        X=x;
        Y=y;
    }

    public double getX() {
        return X;
    }

    public double getY() {
        return Y;
    }

    public void setLocation(double x, double y){
        X=x;
        Y=y;
    }

    public void setX(double x) {
        X = x;
    }

    public void setY(double y) {
        Y = y;
    }
}
