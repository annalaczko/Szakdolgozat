package com.annalaczko.onlab.model;

import com.annalaczko.onlab.model.data.Coordinate;
import com.annalaczko.onlab.model.data.RobotModel;
import com.annalaczko.onlab.model.data.Trapeze;
import com.annalaczko.onlab.viewmodel.RobotViewModel;

public class Trapezoidal extends Thread {

    public static boolean[] havebeenhere;
    ZigZag zigZag;
    int id;

    /*
    Körbe
     */

    private static void initHavebeenhere() {
        havebeenhere = new boolean[TrapezeGenerator.trapezes.size()];
        for (int i = 0; i < havebeenhere.length; i++) {
            havebeenhere[i] = false;
        }
    }

    @Override
    public void run() {
        RoadLengthMeasure roadLengthMeasure = new RoadLengthMeasure();

        roadLengthMeasure.start();

        try {
            PathFinder.Calculate();
        } catch (Exception e) {
            e.printStackTrace();
        }

        RobotModel.setLocation(PathFinder.finaltrapezes.get(0).getCornerForRobot(0));

        initHavebeenhere();

        Trapeze lasttetragon = null;

        roadLengthMeasure.writeTime();

        for (int i = 0; i < PathFinder.finaltrapezes.size(); i++) {
            if (lasttetragon != null) {
                try {
                    id = reallocate(PathFinder.finaltrapezes.get(i), lasttetragon); //következő trapézhoz megy
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            int havebeenhereID = TrapezeGenerator.trapezes.indexOf(PathFinder.finaltrapezes.get(i));

            if (!havebeenhere[havebeenhereID]) {
                havebeenhere[havebeenhereID] = true;
                zigZag = new ZigZag(PathFinder.finaltrapezes.get(i), id);
                zigZag.start();
            }

            lasttetragon = PathFinder.finaltrapezes.get(i);
        }
        roadLengthMeasure.over();


    }

    private int reallocate(Trapeze newTrapeze, Trapeze lastTrapeze) throws Exception {
        int id = 0;

        //megkeresem a legközelebbi koordinátát a következő trapézban
        double dist = distance(RobotModel.getLocation(), newTrapeze.getCornerForRobot(0));
        for (int i = 0; i < 4; i++) {
            ///TODO ha túl vékony a trapéz akkor lehet távolabb lesz a "jó" sarok mint egy másik
            if (dist > distance(RobotModel.getLocation(), newTrapeze.getCornerForRobot(i))) {
                id = i;
                dist = distance(RobotModel.getLocation(), newTrapeze.getCornerForRobot(i));
            }
        }

        double xforRobot = newTrapeze.getCornerForRobot(id).getX() - RobotModel.getRadius() * 2;
        if (id == 2 || id == 1) {
            xforRobot = newTrapeze.getCornerForRobot(id).getX() + RobotModel.getRadius() * 2;
        }

        //region trapéz szélére mozgás
        dist = distance(RobotModel.getLocation(), new Coordinate(xforRobot, newTrapeze.findCommonCoordinateY(lastTrapeze), null));

        double degree = Math.toDegrees(Math.atan2((newTrapeze.findCommonCoordinateY(lastTrapeze) - RobotModel.getLocation().getY()), (xforRobot - RobotModel.getLocation().getX())));

        while (dist > 0) {
            RobotModel.move(degree);
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            dist -= RobotModel.speed;
            RobotViewModel.update();
        }
        //endregion

        //region trapézváltás


        if (id == 2 || id == 1) {
            xforRobot = xforRobot - 2 * RobotModel.getRadius();
        } else {
            xforRobot = xforRobot + 2 * RobotModel.getRadius();
        }

        dist = distance(RobotModel.getLocation(), new Coordinate(xforRobot, newTrapeze.findCommonCoordinateY(lastTrapeze), null));
        degree = Math.toDegrees(Math.atan2((newTrapeze.findCommonCoordinateY(lastTrapeze) - RobotModel.getLocation().getY()), (xforRobot - RobotModel.getLocation().getX())));

        while (dist > 0) {
            RobotModel.move(degree);
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            dist -= RobotModel.speed;
            RobotViewModel.update();
        }
        //endregion

        dist = distance(RobotModel.getLocation(), newTrapeze.getCornerForRobot(0));
        for (int i = 0; i < 4; i++) {
            ///TODO ha túl vékony a trapéz akkor lehet távolabb lesz a "jó" sarok mint egy másik
            if (dist > distance(RobotModel.getLocation(), newTrapeze.getCornerForRobot(i))) {
                id = i;
                dist = distance(RobotModel.getLocation(), newTrapeze.getCornerForRobot(i));
            }
        }

        //region trapéz széléről sarokba mozgás
        dist = distance(RobotModel.getLocation(), newTrapeze.getCornerForRobot(id));

        degree = Math.toDegrees(Math.atan2((newTrapeze.getCornerForRobot(id).getY() - RobotModel.getLocation().getY()), (newTrapeze.getCornerForRobot(id).getX() - RobotModel.getLocation().getX())));

        while (dist > 0) {
            RobotModel.move(degree);
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            dist -= RobotModel.speed;
            RobotViewModel.update();
        }
        //endregion

        return id;
    }

    private double distance(Coordinate from, Coordinate to) {
        return Math.sqrt(Math.pow(from.getX() - to.getX(), 2) + Math.pow(from.getY() - to.getY(), 2));
    }


}
