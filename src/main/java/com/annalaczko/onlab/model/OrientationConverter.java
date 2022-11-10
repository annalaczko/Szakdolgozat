package com.annalaczko.onlab.model;

import com.annalaczko.onlab.viewmodel.RoomViewModel;

import java.util.ArrayList;

public class OrientationConverter {

    public static void convert (){
        convertDimensions();
        convertObjects();
    }

    private static void convertDimensions(){
        int oldWidth=(int) RoomModel.getWidth();
        RoomModel.setWidth((int)RoomModel.getHeight());
        RoomModel.setHeight(oldWidth);
    }

    private static void convertObjects(){
        Polygon temp;
        int[] newX;
        int[] newY;
        ArrayList<Polygon> newList =new ArrayList<>();
        for (Polygon original: RoomModel.objects) {
            newX = new int[original.npoints];
            newY = new int[original.npoints];
            for (int i = 0; i < original.npoints; i++) {
                newX[i]=original.ypoints[i];
                newY[i]=(int)RoomModel.getHeight()-original.xpoints[i];
                System.out.print(newX[i] + "-" + newY[i] + " ");
            }
            System.out.print(" || ");
            temp= new Polygon(newX, newY, original.npoints);
            newList.add(temp);
        }
        System.out.println();

        for (Polygon original: RoomModel.objects) {
            for (int i = 0; i < original.npoints; i++) {
                System.out.print(original.xpoints[i] + "-" + original.ypoints [i] + " ");
            }
            System.out.print(" || ");
        }

        System.out.println();

        for (Polygon polygon: newList) {
            int id = polygon.findCornerId(polygon.coordinatesInOrder.get(0));
            int [] tempXCoors=new int[id];
            int [] tempYCoors=new int[id];
            for (int i = 0; i < id; i++) {
                tempXCoors[i] = polygon.xpoints[i];
                tempYCoors[i] = polygon.ypoints[i];
            }

            for (int i = 0; i < polygon.npoints-id; i++) {
                polygon.xpoints[i]=polygon.xpoints[i+id];
                polygon.ypoints[i]=polygon.ypoints[i+id];
            }

            for (int i = polygon.npoints-id; i < polygon.npoints; i++) {
                polygon.xpoints[i]=tempXCoors[i-polygon.npoints+id];
                polygon.ypoints[i]=tempYCoors[i-polygon.npoints+id];
            }
        }


        RoomModel.objects.clear();
        RoomModel.objects.addAll(newList);

        for (Polygon original: RoomModel.objects) {
            for (int i = 0; i < original.npoints; i++) {
                System.out.print(original.xpoints[i] + "-" + original.ypoints [i] + " ");
            }
            System.out.print(" || ");
        }
    }

    private static void rearrangeList (int [] x, int [] y){

    }

}
