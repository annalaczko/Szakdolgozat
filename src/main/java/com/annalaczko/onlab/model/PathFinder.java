package com.annalaczko.onlab.model;

import java.awt.*;
import java.util.*;
import java.util.List;

public class PathFinder {
    public static boolean[][] neighbourMatrix;
    public static ArrayList<Tetragon> trapezes=new ArrayList<>();
    private static ArrayList<Coordinate> coordinates;

    private static void resetMatrix(){
        neighbourMatrix= new boolean[trapezes.size()][trapezes.size()];
        for (int i=0; i< trapezes.size(); i++){
            for (int j=0; j< trapezes.size(); j++){
                neighbourMatrix[i][j]=false;
            }
        }
    }

    public static void Calculate() throws Exception {
        //initCoordinates(objects);
        double [] xpoints={0,RoomModel.objects.get(0).xpoints[0],RoomModel.objects.get(0).xpoints[0],0};
        double [] ypoints={0,0, RoomModel.getHeight(), RoomModel.getHeight() };
        trapezes.add(new Tetragon(xpoints,ypoints ));

        xpoints=new double[]{RoomModel.objects.get(0).xpoints[0],RoomModel.objects.get(0).xpoints[2],RoomModel.objects.get(0).xpoints[2],RoomModel.objects.get(0).xpoints[0]};
        ypoints=new double[]{RoomModel.objects.get(0).ypoints[0],RoomModel.objects.get(0).ypoints[2], RoomModel.getHeight(), RoomModel.getHeight()};
        trapezes.add(new Tetragon(xpoints,ypoints ));

        xpoints=new double[]{RoomModel.objects.get(0).xpoints[2],RoomModel.objects.get(0).xpoints[1],RoomModel.objects.get(0).xpoints[1],RoomModel.objects.get(0).xpoints[2]};
        ypoints=new double[]{RoomModel.objects.get(0).ypoints[2],RoomModel.objects.get(0).ypoints[1], RoomModel.getHeight(),RoomModel.getHeight()};
        trapezes.add(new Tetragon(xpoints,ypoints ));

        xpoints=new double[]{RoomModel.objects.get(0).xpoints[1],RoomModel.getWidth(),RoomModel.getWidth(),RoomModel.objects.get(0).xpoints[1]};
        ypoints=new double[]{0,0,RoomModel.getHeight(), RoomModel.getHeight()};
        trapezes.add(new Tetragon(xpoints,ypoints ));

        xpoints=new double[]{RoomModel.objects.get(0).xpoints[0],RoomModel.objects.get(0).xpoints[1],RoomModel.objects.get(0).xpoints[1],RoomModel.objects.get(0).xpoints[0]};
        ypoints=new double[]{0,0,RoomModel.objects.get(0).ypoints[1], RoomModel.objects.get(0).ypoints[0]};
        trapezes.add(new Tetragon(xpoints,ypoints ));
    }

    //private static void

    public static void initCoordinates(ArrayList<Polygon> objects){
        for (Polygon object: objects) {
            for (int i=0; i<object.npoints;i++){
                coordinates.add(new Coordinate(object.xpoints[i], object.ypoints[i]));
            }
        }
        Collections.sort(coordinates, new Comparator<Coordinate>() {
            @Override
            public int compare(Coordinate o1, Coordinate o2) {
                return Double.compare(o1.getX(), o2.getX());
            }
        });


    }
}
