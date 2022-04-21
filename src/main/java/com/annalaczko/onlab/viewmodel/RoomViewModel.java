package com.annalaczko.onlab.viewmodel;

import com.annalaczko.onlab.model.*;
import com.annalaczko.onlab.view.SceneView;
import java.awt.Polygon;

import java.util.ArrayList;
import java.util.List;

public class RoomViewModel {

    public static double width;
    public static double height;

    public static ArrayList<Polygon> objects=new ArrayList<>();

    //private double maxPixelWidth=1000, maxPixelHeight;

    private static double roomToPixelsConstant;

    public static void initialize(){
        roomToPixelsConstant= SceneView.calcConstant();
        height = RoomModel.getHeight()*roomToPixelsConstant;
        width = RoomModel.getWidth()*roomToPixelsConstant;
        initObjects();

       }

    public static List<Polygon> getObjects() {
        return objects;
    }

    private static void initObjects(){
         int [] xpoints;
         int [] ypoints;
         int npoints;
        for (Polygon object: RoomModel.objects) {
            npoints= object.npoints;
            xpoints=new int[npoints];
            ypoints= new int[npoints];
            for (int i=0; i<object.npoints;i++){
                xpoints[i]=(int)(object.xpoints[i]*SceneView.calcConstant());
                ypoints[i]=(int) (object.ypoints[i]*SceneView.calcConstant());
            }
            objects.add(new Polygon(xpoints,ypoints,npoints));
        }

       }

}
