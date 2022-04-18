package com.annalaczko.onlab.viewmodel.controllers;

import com.annalaczko.onlab.model.RobotModel;
import com.annalaczko.onlab.model.RoomModel;
import com.annalaczko.onlab.model.Tetragon;
import com.annalaczko.onlab.model.Trapezoidal;
import com.annalaczko.onlab.viewmodel.RoomViewModel;
import javafx.scene.layout.Pane;

public class SceneController {

    public static Pane pane;

    public static RoomViewModel roomViewModel;

    public static void moveRobot() throws Exception {
        //double [] x={0, roomWidth, roomWidth,0};
        //double [] y={0, 0, roomHeight, roomHeight};

        double [] x={0, RoomModel.getWidth(), RoomModel.getWidth(),0};
        double [] y={0, 0, RoomModel.getHeight()/3, RoomModel.getHeight()};
        Thread thread= new Trapezoidal(new Tetragon(x,y));
        thread.start();

    }

    public static double calcConstant(){
        return Math.min(Double.valueOf(1000)/ RoomModel.getWidth(), Double.valueOf(600)/ RoomModel.getHeight());
    }

}
