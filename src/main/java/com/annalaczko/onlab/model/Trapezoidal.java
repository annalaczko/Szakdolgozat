package com.annalaczko.onlab.model;

import com.annalaczko.onlab.viewmodel.RobotViewModel;

public class Trapezoidal extends Thread{

    Trapeze trapeze;
    int id;
    @Override
    public void run() {
        try {
            PathFinder.Calculate();
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (Tetragon tetragon: PathFinder.trapezes) {
            id=reallocate(tetragon);
            //System.out.println(id);
            trapeze=new Trapeze(tetragon, id);
            trapeze.start();
        }

    }

    private int reallocate(Tetragon trapeze){
        int id=0;
        double dist=distance(trapeze.getCornerForRobot(0).getX(), trapeze.getCornerForRobot(0).getY());
        for (int i=0; i<4;i++){
            ///TODO ha túl vékony a trapéz akkor lehet távolabb lesz a "jó" sarok mint egy másik
            if (dist>distance(trapeze.getCornerForRobot(i).getX(), trapeze.getCornerForRobot(i).getY())) {
                dist=distance(trapeze.getCornerForRobot(i).getX(), trapeze.getCornerForRobot(i).getY());
                id=i;
            }
        }
        System.out.println(id);

        double degree=Math.toDegrees( Math.atan2 ((trapeze.getCornerForRobot(id).getY()-RobotModel.getLocation().getY()), (trapeze.getCornerForRobot(id).getX()-RobotModel.getLocation().getX())));

        while(dist>0) {
            RobotModel.move(degree);
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            dist-=RobotModel.speed;
            RobotViewModel.update();
        }
        return id;
    }

    private double distance (double x, double y){
        return Math.sqrt(Math.pow(RobotModel.getLocation().getX()-x,2)+Math.pow(RobotModel.getLocation().getY()-y,2));
    }


}
