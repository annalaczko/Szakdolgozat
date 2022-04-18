package com.annalaczko.onlab.model;


public class RobotModel {
    private static int radius=20; //centimeters
    private static Coordinate location=new Coordinate(radius, radius);
    private static int corner=2;
    private static double speed=1; //centimeters per 10 milliseconds

    public static void move (double degree ){
        double x=  location.getX()+Math.cos(Math.toRadians(degree))*speed;
        double y= location.getY()+Math.sin(Math.toRadians(degree))*speed;
        location.setLocation(x,y);
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
                location=new Coordinate(radius, radius);
                break;
            case 1:
                location=new Coordinate(RoomModel.getWidth()-radius, radius);
                break;
            case 2:
                location=new Coordinate(RoomModel.getWidth()-radius, RoomModel.getHeight()-radius);
                break;
            case 3:
                location=new Coordinate(radius, RoomModel.getHeight()-radius);
                break;
            default:
                System.out.println("Wrong corner in Robot!");
                break;
        }


    }
}
