package com.annalaczko.onlab.model;

import com.annalaczko.onlab.viewmodel.MainController;
import com.annalaczko.onlab.viewmodel.RoomController;

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
        return distanceDown<=Robot.getRadius() && distanceUp<=Robot.getRadius();
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
                distanceX=tetragon.xpoints[2]-Robot.getLocation().getX();
                break;
            case 1:
                i0=3;
                i1=2;
                i2=0;
                i3=1;
                degreeY=90;
                distanceX=Robot.getLocation().getX();
                break;
            case 2:
                i0=0;
                i1=1;
                i2=3;
                i3=2;
                degreeY=-90;
                distanceX=Robot.getLocation().getX();
                break;
            default:
                // case 3:
                i0=1;
                i1=0;
                i2=2;
                i3=3;
                degreeY=-90;
                distanceX=tetragon.xpoints[2]-Robot.getLocation().getX();
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


        while( distanceX>Robot.getRadius()*3 && !isTooSmall()){
            verticalMoving(degreeY);
            double constDistanceX=distanceX-Robot.getRadius()*2;
            horizontalMoving(degreeX, constDistanceX);
            degreeY*=-1;

            if (degreeX==degree1) {
                degreeX=degree0;
            }
            else degreeX=degree1;
        }
        if (!isTooSmall()){
            verticalMoving(degreeY);
            horizontalMoving(degreeX, Robot.getRadius());
            degreeY*=-1;
            verticalMoving(degreeY);
        }

        //MainController.isRunning=false;
        System.out.println("PUFFF");

    }

    public double getDistanceFromWall (double x0, double y0, double x1, double y1){
        double a=(y1-y0),b=(x0-x1),c=x0*y1-x1*y0;
        return (Math.abs(Robot.getLocation().getX()*a+Robot.getLocation().getY()*b-c))/Math.sqrt(a*a+b*b);
    }

    public void verticalMoving(double degreeY){
        if (degreeY==90){
            distanceY=getDistanceFromWall(tetragon.xpoints[2], tetragon.ypoints[2], tetragon.xpoints[3], tetragon.ypoints[3]);
            while(distanceY>Robot.getRadius()){
                Robot.move(degreeY);
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                distanceY=getDistanceFromWall(tetragon.xpoints[2], tetragon.ypoints[2], tetragon.xpoints[3], tetragon.ypoints[3]); //TODO: a trapéz oldalait kell itt megadni
                RoomController.update();
            }
        } else if (degreeY==-90){
            distanceY=getDistanceFromWall(tetragon.xpoints[0], tetragon.ypoints[0], tetragon.xpoints[1], tetragon.ypoints[1]);
            while(distanceY>Robot.getRadius()){
                Robot.move(degreeY);
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                distanceY=getDistanceFromWall(tetragon.xpoints[0], tetragon.ypoints[0], tetragon.xpoints[1], tetragon.ypoints[1]); //TODO: a trapéz oldalait kell itt megadni
                RoomController.update();
            }
        }
    }

    public void horizontalMoving(double degreeX, double distance){
        while(distanceX>distance && !isTooSmall()){
            Robot.move(degreeX);
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (corner==1 || corner == 2){
                distanceX=Robot.getLocation().getX(); //TODO: a trapéz oldalait kell itt megadni
            }else{
                distanceX=tetragon.xpoints[2]-Robot.getLocation().getX(); //TODO: a trapéz oldalait kell itt megadni
            }


            RoomController.update();
        }


    }

    public Trapezoidal(Tetragon _tetragon){
        tetragon=_tetragon;
        corner=Robot.getCorner();
    }

}
