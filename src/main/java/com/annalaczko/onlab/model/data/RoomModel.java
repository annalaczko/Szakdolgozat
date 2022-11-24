package com.annalaczko.onlab.model.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Szoba backendje
 */

public class RoomModel {

    public static ArrayList<Polygon> objects = new ArrayList<>();
    private static int width;
    private static int height;
    private Polygon corners;

    /**
     * Szoba inicializálása
     *
     * @param _width
     * @param _height
     */

    public static void setCorners(int _width, int _height) {

        width = _width;
        height = _height;

    }

    public static void addObject(/*Polygon newObject*/) {
        // objects.add(newObject);

        int[] xpoints = {100, 200, 600, 400};
        int[] ypoints = {400, 200, 100, 500};

        int[] xpoints1 = {100, 300, 700, 500};
        int[] ypoints1 = {300, 100, 300, 500};

        int[] xpoints2 = {900, 1200, 1200, 900};
        int[] ypoints2 = {200, 200, 400, 400};

        int[] xpoints3 = {400, 1000, 500};
        int[] ypoints3 = {600, 900, 800};

        int[] xpoints4 = {1300, 1500, 1800, 1900, 1300};
        int[] ypoints4 = {600, 400, 400, 700, 900};

        objects.add(new Polygon(xpoints, ypoints, 4));
        //objects.add(new Polygon(xpoints1, ypoints1, 4));
        //objects.add(new Polygon(xpoints2, ypoints2, 4));
        //objects.add(new Polygon(xpoints3, ypoints3, 3));
        //objects.add(new Polygon(xpoints4, ypoints4, 5));

    }

    public static double getWidth() {
        return width;
    }

    public static void setWidth(int width) {
        RoomModel.width = width;
    }

    public static double getHeight() {
        return height;
    }

    public static void setHeight(int height) {
        RoomModel.height = height;
    }

    /**
     * Visszaadja Coordinate osztályban a sarkait a szobának.
     *
     * @param id sarok sorszáma. bal felül kezd
     * @return
     */

    public static Coordinate getCorner(int id) {
        switch (id) {
            case 0:
                return new Coordinate(0, 0, null);
            case 1:
                return new Coordinate(width, 0, null);
            case 2:
                return new Coordinate(width, height, null);
            case 3:
                return new Coordinate(0, height, null);
            default:
                return null;
        }
    }


    public static void sortObjects() {
        Collections.sort(objects, new Comparator<Polygon>() {
            @Override
            public int compare(Polygon o1, Polygon o2) {
                return Double.compare(o1.getFirstCorner().getY(), o2.getFirstCorner().getY());
            }
        });
    }
}
