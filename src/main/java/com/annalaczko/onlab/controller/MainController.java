package com.annalaczko.onlab.controller;

import com.annalaczko.onlab.model.Robot;
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

    Timeline timeline = new Timeline(new KeyFrame(Duration.millis(10), new EventHandler<ActionEvent>() {

        @Override
        public void handle(ActionEvent actionEvent) {
            Robot.move(10,270);
            update();
        }
    }));


    @FXML
    private void handleExitAction(final ActionEvent event)
    {
        Platform.exit();
    }


    @Override
    public void initialize(java.net.URL arg0, ResourceBundle arg1) {
        menuBar.setFocusTraversable(true);
        pane.setPrefWidth(1000);
        pane.setPrefHeight(600);
        robot.setCenterX(0);
        robot.setCenterY(pane.getPrefHeight()-robot.getRadius()*2);

    }

    @FXML
    private void handleNewRoomAction (final ActionEvent event) throws Exception {
        NewRoomWindow.start();
    }

    @FXML
    private void handleStartAction (final ActionEvent event) throws Exception {
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    @FXML
    public void handleLoadAction(){
        pane=new Pane();
        pane.setPrefSize(RoomController.roomWidth,RoomController.roomHeight);
        System.out.println(pane.getPrefHeight());
        robot.setRadius(RoomController.robotRadius);
        robot.setCenterX(0);
        robot.setCenterY(pane.getLayoutY());
    }

    private void update (){
        robot.setCenterX(Robot.getX());
        robot.setCenterY(Robot.getY());
    }

}
