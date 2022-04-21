package com.annalaczko.onlab.view;

import com.annalaczko.onlab.model.*;
import com.annalaczko.onlab.viewmodel.RobotViewModel;
import com.annalaczko.onlab.viewmodel.RoomViewModel;
import javafx.geometry.Insets;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.StrokeType;

import java.util.ArrayList;
import java.util.List;


public class SceneView {

    public static Circle robot=new Circle();

    public static Pane pane=new Pane();
    public static ArrayList<Polygon> objects=new ArrayList<>();


    public static void initialize() {
        initPane();
        initRobot();
        initObjects();
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

    private static void initObjects(){

        for (java.awt.Polygon object: RoomViewModel.getObjects()) {
            double [] points= new double[object.npoints*2];
            for (int i=0; i<object.npoints; i++){
                points[2*i]= object.xpoints[i];
                points[2*i+1]= object.ypoints[i];
            }
            objects.add(new Polygon(points));
        }
        pane.getChildren().addAll(objects);
    }

    public static void moveRobot() throws Exception {
        double [] x={0, RoomModel.getWidth(), RoomModel.getWidth(),0};
        double [] y={0, 0, RoomModel.getHeight()/3, RoomModel.getHeight()};
        Thread thread= new Trapezoidal();
        thread.start();

    }

    public static double calcConstant(){
        return Math.min(Double.valueOf(1000)/ RoomModel.getWidth(), Double.valueOf(600)/ RoomModel.getHeight());
    }


}
