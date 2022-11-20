package com.annalaczko.onlab.viewmodel;

import com.annalaczko.onlab.model.data.Coordinate;
import com.annalaczko.onlab.model.data.RobotModel;
import com.annalaczko.onlab.view.SceneView;

public class RobotViewModel {

    public static double radius;
    public static Coordinate location;

    private static double roomToPixelsConstant;

    public static void initialize(){
        roomToPixelsConstant= SceneView.calcConstant();
        RobotModel.reset();
        radius = RobotModel.getRadius()*roomToPixelsConstant;
        location =new Coordinate((int) (RobotModel.getLocation().getX()*roomToPixelsConstant), (int)(RobotModel.getLocation().getY()*roomToPixelsConstant), null);

    }

    public static void update(){
        location =new Coordinate ((int) (RobotModel.getLocation().getX()*roomToPixelsConstant), (int)(RobotModel.getLocation().getY()*roomToPixelsConstant), null);
    }

}
