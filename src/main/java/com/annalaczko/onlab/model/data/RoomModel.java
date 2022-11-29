package com.annalaczko.onlab.model.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Szoba backendje
 */

public class RoomModel {

    public static ArrayList<Polygon> objects = new ArrayList<>();
    public static int activeRoomIndex = 0;
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

        switch (activeRoomIndex) {
            case 0:
                roomRandomObject();
                break;
            case 1:
                roomFirstRNDObjects();
                break;
            case 2:
                room1();
                break;
            case 3:
                roomPanni();
                break;
            case 4:
                break;
            case 5:
                break;
            case 6:
                nappali();
                break;
            case 7:

                break;
            default:
                break;
        }


    }

    public static void roomRandomObject() {
        height = 800;
        width = 800;
        int[] xpoints = {0, 200, 600, 0};
        int[] ypoints = {400, 200, 100, 500};


        objects.add(new Polygon(xpoints, ypoints, 4));
    }

    public static void room1() {
        height = 600;
        width = 800;
        int[] xpoints1 = {50, 150, 250, 150};
        int[] ypoints1 = {150, 50, 150, 250};

        int[] xpoints2 = {450, 550, 650, 550};
        int[] ypoints2 = {150, 50, 150, 250};

        int[] xpoints3 = {250, 300, 400, 450, 450, 400, 300, 250};
        int[] ypoints3 = {300, 250, 250, 300, 350, 400, 400, 350};

        int[] xpoints4 = {150, 550, 550, 150};
        int[] ypoints4 = {450, 450, 550, 550};

        objects.add(new Polygon(xpoints1, ypoints1, 4));
        objects.add(new Polygon(xpoints2, ypoints2, 4));
        objects.add(new Polygon(xpoints3, ypoints3, 8));
        objects.add(new Polygon(xpoints4, ypoints4, 4));
    }

    public static void nappali() {
        height = 500;
        width = 1100;
        int[] xpoints0 = {0, 50, 50, 0};
        int[] ypoints0 = {0, 0, 250, 250};

        int[] xpoints1 = {50, 400, 400, 50};
        int[] ypoints1 = {0, 0, 50, 50};

        int[] xpoints2 = {400, 450, 450, 400};
        int[] ypoints2 = {0, 0, 200, 200};

        int[] xpoints3 = {600, 650, 650, 600};
        int[] ypoints3 = {0, 0, 200, 200};

        int[] xpoints4 = {650, 750, 750, 650};
        int[] ypoints4 = {50, 50, 150, 150};

        int[] xpoints5 = {800, 1100, 1100, 800};
        int[] ypoints5 = {0, 0, 50, 50};

        int[] xpoints6 = {100, 200, 300, 200};
        int[] ypoints6 = {250, 150, 250, 350};

        int[] xpoints7 = {1000, 1100, 1100, 1000};
        int[] ypoints7 = {250, 250, 350, 350};

        int[] xpoints8 = {0, 50, 100, 50};
        int[] ypoints8 = {400, 350, 400, 450};

        int[] xpoints9 = {150, 350, 350, 150};
        int[] ypoints9 = {450, 450, 500, 500};

        int[] xpoints10 = {350, 400, 500, 550, 550, 350};
        int[] ypoints10 = {400, 350, 350, 400, 500, 500};

        int[] xpoints11 = {750, 950, 950, 750};
        int[] ypoints11 = {400, 400, 500, 500};

        int[] xpoints12 = {1050, 1100, 1100, 1050};
        int[] ypoints12 = {400, 400, 500, 500};

        int[] xpoints13 = {1050, 1100, 1100, 1050};
        int[] ypoints13 = {50, 50, 200, 200};

        objects.add(new Polygon(xpoints0, ypoints0, 4));
        objects.add(new Polygon(xpoints1, ypoints1, 4));
        objects.add(new Polygon(xpoints2, ypoints2, 4));
        objects.add(new Polygon(xpoints3, ypoints3, 4));
        objects.add(new Polygon(xpoints4, ypoints4, 4));
        objects.add(new Polygon(xpoints5, ypoints5, 4));
        objects.add(new Polygon(xpoints6, ypoints6, 4));
        objects.add(new Polygon(xpoints7, ypoints7, 4));
        objects.add(new Polygon(xpoints8, ypoints8, 4));
        objects.add(new Polygon(xpoints9, ypoints9, 4));
        objects.add(new Polygon(xpoints10, ypoints10, 6));
        objects.add(new Polygon(xpoints11, ypoints11, 4));
        objects.add(new Polygon(xpoints12, ypoints12, 4));
        objects.add(new Polygon(xpoints13, ypoints13, 4));
    }

    public static void roomPanni() {
        height = 350;
        width = 350;
        int[] xpoints1 = {0, 100, 100, 0};
        int[] ypoints1 = {0, 00, 200, 200};

        int[] xpoints2 = {150, 300, 300, 150};
        int[] ypoints2 = {0, 0, 50, 50};

        int[] xpoints3 = {150, 200, 250, 200};
        int[] ypoints3 = {150, 100, 150, 200};

        int[] xpoints4 = {0, 50, 50, 0};
        int[] ypoints4 = {250, 250, 350, 350};

        objects.add(new Polygon(xpoints1, ypoints1, 4));
        objects.add(new Polygon(xpoints2, ypoints2, 4));
        objects.add(new Polygon(xpoints3, ypoints3, 4));
        objects.add(new Polygon(xpoints4, ypoints4, 4));
    }

    public static void roomFirstRNDObjects() {
        height = 1000;
        width = 2000;

        int[] xpoints1 = {100, 300, 700, 500};
        int[] ypoints1 = {300, 100, 300, 500};

        int[] xpoints2 = {900, 1200, 1200, 900};
        int[] ypoints2 = {200, 200, 400, 400};

        int[] xpoints3 = {400, 1000, 500};
        int[] ypoints3 = {600, 900, 800};

        int[] xpoints4 = {1200, 1400, 1700, 1800, 1200};
        int[] ypoints4 = {600, 400, 400, 700, 900};

        objects.add(new Polygon(xpoints1, ypoints1, 4));
        objects.add(new Polygon(xpoints2, ypoints2, 4));
        objects.add(new Polygon(xpoints3, ypoints3, 3));
        objects.add(new Polygon(xpoints4, ypoints4, 5));
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
