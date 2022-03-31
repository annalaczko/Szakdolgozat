package com.annalaczko.onlab.model;

import com.annalaczko.onlab.controller.Main;
import com.annalaczko.onlab.controller.MainController;
import com.annalaczko.onlab.controller.RoomController;

public class Trapezoidal extends Thread{

    private Tetragon tetragon;
    private double distanceX, distanceY;

    public void run(){
        int degreeX=0, degreeY=90;
        while(distanceX>Robot.getRadius()*3){
            System.out.println(Robot.getLocation().getX() + "Run" + Robot.getLocation().getY());
            while(distanceY>Robot.getRadius()){
                Robot.move(degreeY);
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                distanceY=Room.getHeight()-Robot.getLocation().getY(); //TODO: a trapéz oldalait kell itt megadni
            }
            RoomController.update();

            while(distanceX>distanceX-Robot.getRadius()*2){
                Robot.move(degreeX);
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                distanceX=Room.getWidth()-Robot.getLocation().getX(); //TODO: a trapéz oldalait kell itt megadni
            }

            RoomController.update();

            degreeX+=180;

            degreeY+=180;
        }
        System.out.println("Dun");

    }

    public Trapezoidal(Tetragon _tetragon){
        tetragon=_tetragon;


    }

}
