package com.annalaczko.onlab.controller;

import com.annalaczko.onlab.model.Robot;
import com.annalaczko.onlab.model.Room;

public class RoomController {

    public static double roomWidth;
    public static double roomHeight;
    public static double robotRadius;


    private static double roomToPixelsConstant;

    public static void update(){
        calcConstant();
        roomHeight=Room.getHeight()*roomToPixelsConstant;
        roomWidth=Room.getWidth()*roomToPixelsConstant;
        robotRadius= Robot.getRadius()*roomToPixelsConstant;
    }

    private static void calcConstant(){
        roomToPixelsConstant=Math.min(Double.valueOf(1000)/Room.getWidth(), Double.valueOf(600)/Room.getHeight());
    }

    public static double getRoomToPixelsConstant(){return roomToPixelsConstant;}
}
