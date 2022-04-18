package com.annalaczko.onlab.viewmodel;

import com.annalaczko.onlab.model.*;
import com.annalaczko.onlab.view.SceneView;

public class RobotViewModel {

    public static double radius;
    public static Coordinate location;

    private static double roomToPixelsConstant;

    public static void initialize(){
        roomToPixelsConstant= SceneView.calcConstant();
        RobotModel.reset();
        radius = RobotModel.getRadius()*roomToPixelsConstant;
        location =new Coordinate((int) (RobotModel.getLocation().getX()*roomToPixelsConstant), (int)(RobotModel.getLocation().getY()*roomToPixelsConstant));

        System.out.println(radius +" "+location.getX()+" "+location.getY());
        System.out.println(RobotModel.getRadius());

    }

    public static void update(){
        location =new Coordinate ((int) (RobotModel.getLocation().getX()*roomToPixelsConstant), (int)(RobotModel.getLocation().getY()*roomToPixelsConstant));
    }

}
