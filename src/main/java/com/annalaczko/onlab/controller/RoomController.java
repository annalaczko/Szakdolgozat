package com.annalaczko.onlab.controller;

import com.annalaczko.onlab.model.Robot;
import com.annalaczko.onlab.model.Room;
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

import java.net.URL;
import java.util.ResourceBundle;

public class RoomController {

    public static double roomWidth;
    public static double roomHeight;
    public static double robotRadius;

    //private double maxPixelWidth=1000, maxPixelHeight;

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
