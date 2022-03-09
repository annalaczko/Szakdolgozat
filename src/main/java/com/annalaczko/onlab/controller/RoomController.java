package com.annalaczko.onlab.controller;

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

    private static AnchorPane anchorPane=new AnchorPane();

    //private double maxPixelWidth=1000, maxPixelHeight;

    private static double roomToPixelsConstant;

    private Circle robot;

    public static void update(){
        calcConstant();
        anchorPane.setPrefHeight(Room.getHeight()*roomToPixelsConstant);
        anchorPane.setPrefWidth(Room.getWidth()*roomToPixelsConstant);
    }

    public static void calcConstant(){
        roomToPixelsConstant=Math.min(Double.valueOf(1000)/Room.getWidth(), Double.valueOf(600)/Room.getHeight());
        System.out.println(roomToPixelsConstant);
    }

    Timeline timeline = new Timeline(new KeyFrame(Duration.millis(10), new EventHandler<ActionEvent>() {

        double deltaX = 2;
        double deltaY = 2;

        @Override
        public void handle(ActionEvent actionEvent) {
            robot.setLayoutX(robot.getLayoutX() - deltaX);
            robot.setLayoutY(robot.getLayoutY() - deltaY);

            Bounds bounds = anchorPane.getBoundsInLocal();
            boolean rightBorder = robot.getLayoutX() >= (bounds.getMaxX() - robot.getRadius());
            boolean leftBorder = robot.getLayoutX() <= (bounds.getMinX() + robot.getRadius());
            boolean bottomBorder = robot.getLayoutY() >= (bounds.getMaxY() - robot.getRadius());
            boolean topBorder = robot.getLayoutY() <= (bounds.getMinY() + robot.getRadius());

            if (rightBorder || leftBorder) {
                deltaX *= -1;
            }
            if (bottomBorder || topBorder) {
                deltaY *= -1;
            }
        }
    }));


   /*@Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }*/



    public static AnchorPane getAnchorPane(){
        return anchorPane;
    }
}
