package com.annalaczko.onlab.viewmodel;

import com.annalaczko.onlab.model.*;
import com.annalaczko.onlab.viewmodel.controllers.SceneController;

import java.util.List;

public class RoomViewModel {

    public static double width;
    public static double height;
    private static List<Tetragon> visualCells;
    private static List<Tetragon> pathCells;

    //private double maxPixelWidth=1000, maxPixelHeight;

    private static double roomToPixelsConstant;

    public static void initialize(){
        roomToPixelsConstant= SceneController.calcConstant();
        height = RoomModel.getHeight()*roomToPixelsConstant;
        width = RoomModel.getWidth()*roomToPixelsConstant;
       }





}
