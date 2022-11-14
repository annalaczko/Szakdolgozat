package com.annalaczko.onlab.model;

public class Coordinate {
    private double Y;
    private double X;

    private Polygon polygon;

    public Coordinate (double x, double y, Polygon polygon1){
        X=x;
        Y=y;
        polygon=polygon1;
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

    public Polygon getObject() {
        return polygon;
    }
}
