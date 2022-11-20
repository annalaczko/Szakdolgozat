package com.annalaczko.onlab.model.data;

/**
 * Robot backendje
 */

public class RobotModel {
    private static int radius=20; //centimeters
    private static Coordinate location=new Coordinate(radius, radius, null);
    private static int corner=0;
    public static double speed=1.5; //centimeters per 10 milliseconds

    /**
     *
     * @param degree mozgás szöge
     * pozitív x = jobb
     * pozitív y = bal
     * /////-90/////
     * //180/////0//
     * /////90/////
     */

    public static void move (double degree ){
        double x=  location.getX()+Math.cos(Math.toRadians(degree))*speed;
        double y= location.getY()+Math.sin(Math.toRadians(degree))*speed;
        location.setLocation(x,y);
    }

    public static void setCorner(int corner) {
        RobotModel.corner = corner;
    }

    public static int getRadius() {
        return radius;
    }

    public static int getCorner() {
        return corner;
    }

    public static Coordinate getLocation() {
        return location;
    }

    public static void reset(){

        switch (corner){
            case 0:
                location=new Coordinate(radius, radius, null);
                break;
            case 1:
                location=new Coordinate(RoomModel.getWidth()-radius, radius, null);
                break;
            case 2:
                location=new Coordinate(RoomModel.getWidth()-radius, RoomModel.getHeight()-radius, null);
                break;
            case 3:
                location=new Coordinate(radius, RoomModel.getHeight()-radius, null);
                break;
            default:
                System.out.println("Wrong corner in Robot!");
                break;
        }


    }
}