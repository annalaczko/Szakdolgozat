package com.annalaczko.onlab.viewmodel.controllers;

import com.annalaczko.onlab.model.RobotModel;
import com.annalaczko.onlab.model.RoomModel;
import com.annalaczko.onlab.model.Tetragon;
import com.annalaczko.onlab.model.Trapezoidal;
import com.annalaczko.onlab.viewmodel.RobotViewModel;
import com.annalaczko.onlab.viewmodel.RoomViewModel;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.StrokeType;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class SceneController {

    public static Circle robot=new Circle();

    public static Pane pane=new Pane();

    public static List<Polygon> objects;

    public static void initialize() {
        initPane();
        initRobot();
    }

    public static void update(){
        robot.setCenterX(RobotViewModel.location.getX());
        robot.setCenterY(RobotViewModel.location.getY());
    }

    private static void initPane(){
        pane.setVisible(true);
        pane.setMinHeight(RoomViewModel.height);
        pane.setMinWidth(RoomViewModel.width);
        pane.setMaxHeight(RoomViewModel.height);
        pane.setMaxWidth(RoomViewModel.width);
        pane.setPrefHeight(RoomViewModel.height);
        pane.setPrefWidth(RoomViewModel.width);
        pane.setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(0), Insets.EMPTY)));
        pane.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(2))));
    }

    private static void initRobot(){
        robot.setVisible(true);
        robot.setRadius(RobotViewModel.radius);
        robot.setCenterX(RobotViewModel.location.getX());
        robot.setCenterY(RobotViewModel.location.getY());
        robot.setFill(Color.LIGHTGRAY);
        robot.setStroke(Color.DARKGRAY);
        robot.setStrokeType(StrokeType.INSIDE);
        robot.setStrokeWidth(RobotViewModel.radius/3);
        pane.getChildren().add(robot);
    }

    public static void moveRobot() throws Exception {
        double [] x={0, RoomModel.getWidth(), RoomModel.getWidth(),0};
        double [] y={0, 0, RoomModel.getHeight()/3, RoomModel.getHeight()};
        Thread thread= new Trapezoidal(new Tetragon(x,y));
        thread.start();

    }

    public static double calcConstant(){
        return Math.min(Double.valueOf(1000)/ RoomModel.getWidth(), Double.valueOf(600)/ RoomModel.getHeight());
    }


}
