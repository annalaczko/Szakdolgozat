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
    private AnchorPane anchorPane;

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

    @FXML
    private void handleExitAction(final ActionEvent event)
    {
        Platform.exit();
    }


    @Override
    public void initialize(java.net.URL arg0, ResourceBundle arg1) {
        menuBar.setFocusTraversable(true);
        anchorPane.setPrefWidth(1000);
        anchorPane.setPrefHeight(600);
        robot.setCenterY(0);
        robot.setCenterX(0);

    }

    @FXML
    private void handleNewRoomAction (final ActionEvent event) throws Exception {
        NewRoomWindow.start();
    }

    @FXML
    public void handleLoadAction(){
        anchorPane.setPrefHeight(RoomController.roomHeight);
        anchorPane.setPrefWidth(RoomController.roomWidth);
        robot.setRadius(RoomController.robotRadius);
        robot.setCenterX(RoomController.robotRadius);
        robot.setCenterY(RoomController.robotRadius);
    }


}
