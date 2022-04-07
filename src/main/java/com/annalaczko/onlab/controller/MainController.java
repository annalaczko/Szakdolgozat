package com.annalaczko.onlab.controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.*;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

import java.util.ResourceBundle;

public class MainController implements Initializable
{
    @FXML
    private MenuBar menuBar;

    @FXML
    private Circle robot;

    @FXML
    private Pane pane;

    public static boolean isRunning=true;

    @FXML
    private void handleExitAction(final ActionEvent event)
    {
        thread.stop();
        Platform.exit();
    }

    private WindowUpdate thread;

    private class WindowUpdate extends Thread{
        public void run (){

            while (isRunning){
                update();
                try {
                    sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    @Override
    public void initialize(java.net.URL arg0, ResourceBundle arg1) {
        menuBar.setFocusTraversable(true);
        pane.setPrefWidth(1000);
        pane.setPrefHeight(600);
        robot.setCenterY(0);
        robot.setCenterX(0);
        robot.setVisible(false);
        pane.setVisible(false);
        }

    @FXML
    private void handleNewRoomAction (final ActionEvent event) throws Exception {
        NewRoomWindow.start();
    }

    @FXML
    public void handleLoadAction(){
        pane.setVisible(true);
        robot.setVisible(true);
        pane.setPrefHeight(RoomController.roomHeight);
        pane.setPrefWidth(RoomController.roomWidth);
        robot.setRadius(RoomController.robotRadius);
        robot.setCenterX(RoomController.robotCoordinate.getX());
        robot.setCenterY(RoomController.robotCoordinate.getY());
    }

    public void update(){
        robot.setCenterX(RoomController.robotCoordinate.getX());
        robot.setCenterY(RoomController.robotCoordinate.getY());
    }

    @FXML
    public void handleHelpAction(){
        System.out.println(robot.getCenterX()+"  "+ robot.getCenterY());
    }

    @FXML
    public void handleStartAction(){

        thread= new WindowUpdate();

        thread.start();

        RoomController.moveRobot();
    }
}
