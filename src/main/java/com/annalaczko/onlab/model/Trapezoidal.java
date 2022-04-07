package com.annalaczko.onlab.model;

import com.annalaczko.onlab.controller.Main;
import com.annalaczko.onlab.controller.MainController;
import com.annalaczko.onlab.controller.RoomController;

public class Trapezoidal extends Thread{

    private Tetragon tetragon;
    private double distanceX, distanceY;
    private boolean isDone;

    public void run(){
        double degreeX=0, degreeY=90, degreeXUp, degreeXDown;


        //0-val osztás!!!!
        degreeXUp=Math.toDegrees( Math.atan ( (tetragon.xpoints[1]-tetragon.xpoints[0])/(tetragon.ypoints[1]-tetragon.ypoints[0])));
        //TODO általános trapéznál más fenn és lenn a szög
        degreeXDown=Math.toDegrees( Math.atan ( (tetragon.xpoints[3]-tetragon.xpoints[2])/(tetragon.ypoints[3]-tetragon.ypoints[2])));
        System.out.println(degreeXUp +"UP is DOWN"+ degreeXDown);
        degreeX=degreeXDown;
        distanceX=Room.getWidth()-Robot.getLocation().getX();
        distanceY=Room.getHeight()-Robot.getLocation().getY();

        while( distanceX>Robot.getRadius()*3){
            verticalMoving(degreeY);
            double constDistanceX=distanceX-Robot.getRadius()*2;
            horizontalMoving(degreeX, constDistanceX);
            degreeY*=-1;

            if (degreeX==degreeXDown) {
                degreeX=degreeXUp;
            }
            else degreeX=degreeXDown;
        }
        verticalMoving(degreeY);
        horizontalMoving(degreeX, Robot.getRadius());
        degreeY*=-1;

        System.out.println("itt");
        verticalMoving(degreeY);
        MainController.isRunning=false;

    }

    public void verticalMoving(double degreeY){
        if (degreeY==90){
            distanceY=Room.getHeight()-Robot.getLocation().getY();
            while(distanceY>Robot.getRadius()){
                Robot.move(degreeY);
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                distanceY=Room.getHeight()-Robot.getLocation().getY(); //TODO: a trapéz oldalait kell itt megadni
                RoomController.update();
            }
        } else if (degreeY==-90){
            distanceY=Robot.getLocation().getY();
            while(distanceY>Robot.getRadius()){
                Robot.move(degreeY);
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                distanceY=Robot.getLocation().getY(); //TODO: a trapéz oldalait kell itt megadni
                RoomController.update();
            }
        }
    }

    public void horizontalMoving(double degreeX, double distance){
        while(distanceX>distance){
            Robot.move(degreeX);
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            distanceX=Room.getWidth()-Robot.getLocation().getX(); //TODO: a trapéz oldalait kell itt megadni
            RoomController.update();
        }


    }

    public Trapezoidal(Tetragon _tetragon){
        tetragon=_tetragon;
    }

}
