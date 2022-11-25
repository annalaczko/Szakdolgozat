package com.annalaczko.onlab.model;

import com.annalaczko.onlab.model.data.RobotModel;
import com.annalaczko.onlab.model.data.Trapeze;
import com.annalaczko.onlab.viewmodel.RobotViewModel;

public class ZigZag {

    private Trapeze trapeze;
    private double distanceX, distanceY;
    private int corner;

    /////-90/////
    //180/////0//
    //////90/////

    public ZigZag(Trapeze _trapeze, int _corner) {
        trapeze = _trapeze;
        corner = _corner;
    }

    public boolean isTooSmall() {
        double distanceDown = getDistanceFromWall(trapeze.xpoints[2], trapeze.ypoints[2], trapeze.xpoints[3], trapeze.ypoints[3], RobotModel.getLocation().getX(), RobotModel.getLocation().getY());
        double distanceUp = getDistanceFromWall(trapeze.xpoints[0], trapeze.ypoints[0], trapeze.xpoints[1], trapeze.ypoints[1], RobotModel.getLocation().getX(), RobotModel.getLocation().getY());
        return distanceDown <= RobotModel.getRadius() && distanceUp <= RobotModel.getRadius();
    }

    public void start() {
        double degreeX = 0, degreeY = 0, degree0, degree1;
        int i0, i1, i2, i3;

        switch (corner) {
            case 0:
                i0 = 2;
                i1 = 3;
                i2 = 1;
                i3 = 0;
                degreeY = 90;
                distanceX = trapeze.xpoints[2] - RobotModel.getLocation().getX();
                break;
            case 1:
                i0 = 3;
                i1 = 2;
                i2 = 0;
                i3 = 1;
                degreeY = 90;
                distanceX = RobotModel.getLocation().getX() - trapeze.xpoints[0];
                break;
            case 2:
                i0 = 0;
                i1 = 1;
                i2 = 3;
                i3 = 2;
                degreeY = -90;
                distanceX = RobotModel.getLocation().getX() - trapeze.xpoints[0];
                break;
            default:
                // case 3:
                i0 = 1;
                i1 = 0;
                i2 = 2;
                i3 = 3;
                degreeY = -90;
                distanceX = trapeze.xpoints[2] - RobotModel.getLocation().getX();
                break;
        }

        degree0 = Math.toDegrees(Math.atan2((trapeze.ypoints[i0] - trapeze.ypoints[i1]), (trapeze.xpoints[i0] - trapeze.xpoints[i1])));
        degree1 = Math.toDegrees(Math.atan2((trapeze.ypoints[i2] - trapeze.ypoints[i3]), (trapeze.xpoints[i2] - trapeze.xpoints[i3])));

        //if (degree1==-180) degree1=0;
        //if (degree0==180) degree0=0;

        degreeX = degree0;


        //0-val osztás!!!!


        while (distanceX > RobotModel.getRadius() * 3 && !isTooSmall()) {
            verticalMoving(degreeY);
            double constDistanceX = distanceX - RobotModel.getRadius() * 2;
            horizontalMoving(degreeX, constDistanceX);
            degreeY *= -1;

            if (degreeX == degree1) {
                degreeX = degree0;
            } else degreeX = degree1;
        }
        if (!isTooSmall()) {
            verticalMoving(degreeY);
            horizontalMoving(degreeX, RobotModel.getRadius());
            degreeY *= -1;
            verticalMoving(degreeY);
        }

        //MainController.isRunning=false;
        System.out.println("PUFFF");

    }

    public double getDistanceFromWall(double x0, double y0, double x1, double y1, double px, double py) {
        double a = (y1 - y0), b = (x0 - x1), c = x0 * y1 - x1 * y0;
        return (Math.abs(px * a + py * b - c)) / Math.sqrt(a * a + b * b);
    }

    public void verticalMoving(double degreeY) {
        if (degreeY == 90) {
            distanceY = getDistanceFromWall(trapeze.xpoints[2], trapeze.ypoints[2], trapeze.xpoints[3], trapeze.ypoints[3], RobotModel.getLocation().getX(), RobotModel.getLocation().getY());
            while (distanceY > RobotModel.getRadius()) {
                RobotModel.move(degreeY);
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                distanceY = getDistanceFromWall(trapeze.xpoints[2], trapeze.ypoints[2], trapeze.xpoints[3], trapeze.ypoints[3], RobotModel.getLocation().getX(), RobotModel.getLocation().getY());
                RobotViewModel.update();
            }
        } else if (degreeY == -90) {
            distanceY = getDistanceFromWall(trapeze.xpoints[0], trapeze.ypoints[0], trapeze.xpoints[1], trapeze.ypoints[1], RobotModel.getLocation().getX(), RobotModel.getLocation().getY());
            while (distanceY > RobotModel.getRadius()) {
                RobotModel.move(degreeY);
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                distanceY = getDistanceFromWall(trapeze.xpoints[0], trapeze.ypoints[0], trapeze.xpoints[1], trapeze.ypoints[1], RobotModel.getLocation().getX(), RobotModel.getLocation().getY());
                RobotViewModel.update();
            }
        }
    }

    public void horizontalMoving(double degreeX, double distance) {
        while (distanceX > distance && !isTooSmall()) {
            RobotModel.move(degreeX);
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (corner == 1 || corner == 2) {
                distanceX = RobotModel.getLocation().getX();
            } else {
                distanceX = trapeze.xpoints[2] - RobotModel.getLocation().getX();
            }


            RobotViewModel.update();
        }


    }


}
