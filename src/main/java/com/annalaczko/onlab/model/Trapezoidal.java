package com.annalaczko.onlab.model;

import com.annalaczko.onlab.viewmodel.RobotViewModel;
import com.annalaczko.onlab.viewmodel.RoomViewModel;

import java.awt.*;

public class Trapezoidal extends Thread{

    private Tetragon tetragon;
    private double distanceX, distanceY;
    private int corner;

    /////-90/////
    //180/////0//
    //////90/////

    public boolean isTooSmall (){
        double distanceDown=getDistanceFromWall(tetragon.xpoints[2], tetragon.ypoints[2], tetragon.xpoints[3], tetragon.ypoints[3]);
        double distanceUp=getDistanceFromWall(tetragon.xpoints[0], tetragon.ypoints[0], tetragon.xpoints[1], tetragon.ypoints[1]);
        return distanceDown<= RobotModel.getRadius() && distanceUp<= RobotModel.getRadius();
    }

    public void run(){
        double degreeX=0, degreeY=0, degree0, degree1;
        int i0,i1,i2,i3;

        switch (corner){
            case 0:
                i0=2;
                i1=3;
                i2=1;
                i3=0;
                degreeY=90;
                distanceX=tetragon.xpoints[2]- RobotModel.getLocation().getX();
                break;
            case 1:
                i0=3;
                i1=2;
                i2=0;
                i3=1;
                degreeY=90;
                distanceX= RobotModel.getLocation().getX();
                break;
            case 2:
                i0=0;
                i1=1;
                i2=3;
                i3=2;
                degreeY=-90;
                distanceX= RobotModel.getLocation().getX();
                break;
            default:
                // case 3:
                i0=1;
                i1=0;
                i2=2;
                i3=3;
                degreeY=-90;
                distanceX=tetragon.xpoints[2]- RobotModel.getLocation().getX();
                System.out.println("default");
                break;
        }

        degree0=Math.toDegrees( Math.atan2 ((tetragon.ypoints[i0]-tetragon.ypoints[i1]), (tetragon.xpoints[i0]-tetragon.xpoints[i1])));
        degree1=Math.toDegrees( Math.atan2 ((tetragon.ypoints[i2]-tetragon.ypoints[i3]), (tetragon.xpoints[i2]-tetragon.xpoints[i3])));

        //if (degree1==-180) degree1=0;
        //if (degree0==180) degree0=0;

        degreeX=degree0;

        System.out.println("0 " + degree0 + "    2 " + degree1);

        //0-val osztás!!!!


        while( distanceX> RobotModel.getRadius()*3 && !isTooSmall()){
            verticalMoving(degreeY);
            double constDistanceX=distanceX- RobotModel.getRadius()*2;
            horizontalMoving(degreeX, constDistanceX);
            degreeY*=-1;

            if (degreeX==degree1) {
                degreeX=degree0;
            }
            else degreeX=degree1;
        }
        if (!isTooSmall()){
            verticalMoving(degreeY);
            horizontalMoving(degreeX, RobotModel.getRadius());
            degreeY*=-1;
            verticalMoving(degreeY);
        }

        //MainController.isRunning=false;
        System.out.println("PUFFF");

    }

    public double getDistanceFromWall (double x0, double y0, double x1, double y1){
        double a=(y1-y0),b=(x0-x1),c=x0*y1-x1*y0;
        return (Math.abs(RobotModel.getLocation().getX()*a+ RobotModel.getLocation().getY()*b-c))/Math.sqrt(a*a+b*b);
    }

    public void verticalMoving(double degreeY){
        if (degreeY==90){
            distanceY=getDistanceFromWall(tetragon.xpoints[2], tetragon.ypoints[2], tetragon.xpoints[3], tetragon.ypoints[3]);
            while(distanceY> RobotModel.getRadius()){
                RobotModel.move(degreeY);
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                distanceY=getDistanceFromWall(tetragon.xpoints[2], tetragon.ypoints[2], tetragon.xpoints[3], tetragon.ypoints[3]); //TODO: a trapéz oldalait kell itt megadni
                RobotViewModel.update();
            }
        } else if (degreeY==-90){
            distanceY=getDistanceFromWall(tetragon.xpoints[0], tetragon.ypoints[0], tetragon.xpoints[1], tetragon.ypoints[1]);
            while(distanceY> RobotModel.getRadius()){
                RobotModel.move(degreeY);
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                distanceY=getDistanceFromWall(tetragon.xpoints[0], tetragon.ypoints[0], tetragon.xpoints[1], tetragon.ypoints[1]); //TODO: a trapéz oldalait kell itt megadni
                RobotViewModel.update();
            }
        }
    }

    public void horizontalMoving(double degreeX, double distance){
        while(distanceX>distance && !isTooSmall()){
            RobotModel.move(degreeX);
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (corner==1 || corner == 2){
                distanceX= RobotModel.getLocation().getX(); //TODO: a trapéz oldalait kell itt megadni
            }else{
                distanceX=tetragon.xpoints[2]- RobotModel.getLocation().getX(); //TODO: a trapéz oldalait kell itt megadni
            }


            RobotViewModel.update();
        }


    }

    public Trapezoidal(Tetragon _tetragon){
        tetragon=_tetragon;
        corner= RobotModel.getCorner();
    }

}
