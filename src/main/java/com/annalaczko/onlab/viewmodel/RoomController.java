package com.annalaczko.onlab.viewmodel;

import com.annalaczko.onlab.model.*;
import com.annalaczko.onlab.model.Robot;

import java.util.List;

public class RoomController {

    public static double roomWidth;
    public static double roomHeight;
    public static double robotRadius;
    public static Coordinate robotCoordinate;
    private static List<Tetragon> visualCells;
    private static List<Tetragon> pathCells;

    //private double maxPixelWidth=1000, maxPixelHeight;

    private static double roomToPixelsConstant;

    public static void initialize(){
        calcConstant();
        Robot.reset();
        roomHeight=Room.getHeight()*roomToPixelsConstant;
        roomWidth=Room.getWidth()*roomToPixelsConstant;
        robotRadius= Robot.getRadius()*roomToPixelsConstant;
        robotCoordinate=new Coordinate((int) (Robot.getLocation().getX()*roomToPixelsConstant), (int)(Robot.getLocation().getY()*roomToPixelsConstant));
    }

    public static void update(){
        robotCoordinate=new Coordinate ((int) (Robot.getLocation().getX()*roomToPixelsConstant), (int)(Robot.getLocation().getY()*roomToPixelsConstant));
    }

    private static void calcConstant(){
        roomToPixelsConstant=Math.min(Double.valueOf(1000)/Room.getWidth(), Double.valueOf(600)/Room.getHeight());
    }

    public static void moveRobot() throws Exception {
        //double [] x={0, roomWidth, roomWidth,0};
        //double [] y={0, 0, roomHeight, roomHeight};

        double [] x={0, Room.getWidth(), Room.getWidth(),0};
        double [] y={0, 0, Room.getHeight()/3, Room.getHeight()};
        Robot.reset();
        Thread thread= new Trapezoidal(new Tetragon(x,y));
        thread.start();
    }

}
