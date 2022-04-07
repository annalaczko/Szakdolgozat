package com.annalaczko.onlab.controller;

import com.annalaczko.onlab.model.*;
import com.annalaczko.onlab.model.Robot;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

import java.awt.*;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

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

    public static void moveRobot(){
        Thread thread= new Trapezoidal(new Tetragon());
        thread.start();
    }

}
