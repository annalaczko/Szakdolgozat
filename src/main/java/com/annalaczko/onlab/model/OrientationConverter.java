package com.annalaczko.onlab.model;

import com.annalaczko.onlab.model.data.Polygon;
import com.annalaczko.onlab.model.data.RoomModel;

import java.util.ArrayList;

public class OrientationConverter {

    public static boolean turned = false;

    public static void convert() {

        convertObjects();
        convertDimensions();
    }

    private static void convertDimensions() {
        int oldWidth = (int) RoomModel.getWidth();
        RoomModel.setWidth((int) RoomModel.getHeight());
        RoomModel.setHeight(oldWidth);
    }

    private static void convertObjects() {
        Polygon temp;
        int[] newX;
        int[] newY;
        ArrayList<Polygon> newList = new ArrayList<>();
        for (Polygon original : RoomModel.objects) {
            newX = new int[original.npoints];
            newY = new int[original.npoints];
            for (int i = 0; i < original.npoints; i++) {
                newX[i] = original.ypoints[i]; //(int)RoomModel.getHeight()-
                newY[i] = (int) RoomModel.getWidth() - original.xpoints[i];//; //
            }
            temp = new Polygon(newX, newY, original.npoints);
            newList.add(temp);
        }

        for (Polygon polygon : newList) {
            int id = polygon.findCornerId(polygon.coordinatesOrderByX.get(0));
            int[] tempXCoors = new int[id];
            int[] tempYCoors = new int[id];
            for (int i = 0; i < id; i++) {
                tempXCoors[i] = polygon.xpoints[i];
                tempYCoors[i] = polygon.ypoints[i];
            }

            for (int i = 0; i < polygon.npoints - id; i++) {
                polygon.xpoints[i] = polygon.xpoints[i + id];
                polygon.ypoints[i] = polygon.ypoints[i + id];
            }

            for (int i = polygon.npoints - id; i < polygon.npoints; i++) {
                polygon.xpoints[i] = tempXCoors[i - polygon.npoints + id];
                polygon.ypoints[i] = tempYCoors[i - polygon.npoints + id];
            }
        }


        RoomModel.objects.clear();
        RoomModel.objects.addAll(newList);

    }

}
