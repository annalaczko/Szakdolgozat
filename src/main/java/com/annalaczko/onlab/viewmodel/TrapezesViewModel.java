package com.annalaczko.onlab.viewmodel;

import com.annalaczko.onlab.model.PathFinder;
import com.annalaczko.onlab.view.SceneView;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class TrapezesViewModel {


    private static ArrayList<Polygon> trapezes=new ArrayList<>();

    private static double roomToPixelsConstant;

    public static void initialize() {
        roomToPixelsConstant = SceneView.calcConstant();
        initObjects();
    }

    public static List<Polygon> getObjects() {
        return trapezes;
    }

    private static void initObjects() {
        int[] xpoints;
        int[] ypoints;
        int npoints;
        for (Polygon object : PathFinder.trapezes) {

            npoints = object.npoints;
            xpoints = new int[npoints];
            ypoints = new int[npoints];
            for (int i = 0; i < object.npoints; i++) {
                xpoints[i] = (int) (object.xpoints[i] * SceneView.calcConstant());
                ypoints[i] = (int) (object.ypoints[i] * SceneView.calcConstant());
            }
            trapezes.add(new Polygon(xpoints, ypoints, npoints));
        }
    }
}
