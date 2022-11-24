package com.annalaczko.onlab.model.data;

public class Coordinate {
    private double Y;
    private double X;

    private Polygon polygon = null;

    public Coordinate(double x, double y, Polygon polygon1) {
        X = x;
        Y = y;
        polygon = polygon1;
    }

    public Coordinate(Coordinate coordinate) {
        X = coordinate.getX();
        Y = coordinate.getY();
    }

    public Coordinate(double x, double y) {
        X = x;
        Y = y;
    }

    public double getX() {
        return X;
    }

    public double getY() {
        return Y;
    }

    public void setLocation(double x, double y) {
        X = x;
        Y = y;
    }

    public Polygon getObject() {
        return polygon;
    }

    public boolean equalsWithMarginOfError(Coordinate c2) {
        boolean x, y;
        x = Math.abs(c2.getX() - X) < 2;

        y = Math.abs(c2.getY() - Y) < 2;
        return x & y;
    }
}
