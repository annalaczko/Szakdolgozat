package com.annalaczko.onlab.model;

import com.annalaczko.onlab.viewmodel.RobotViewModel;

import java.nio.file.Path;

public class Trapezoidal extends Thread{

    ZigZag zigZag;
    int id;

    /*
    Körbe
     */

    @Override
    public void run() {
        try {
            PathFinder.Calculate();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Trapeze lasttetragon=null;

        for (int i=0; i<PathFinder.finaltrapezes.size();i++) {
            if (lasttetragon!=null)
            {
                id=reallocate(PathFinder.finaltrapezes.get(i), lasttetragon); //következő trapézhoz megy
            }

            if (!PathFinder.havebeenhere[i]) {
                zigZag =new ZigZag(PathFinder.finaltrapezes.get(i), id);
                zigZag.start();
            }
            //System.out.println(id);

            lasttetragon= PathFinder.finaltrapezes.get(i);
        }

    }

    private int reallocate(Trapeze newTrapeze, Trapeze lastTrapeze){
        int id=0;


        double dist=distance(RobotModel.getLocation(), newTrapeze.getCornerForRobot(0));
        for (int i=0; i<4;i++){
            ///TODO ha túl vékony a trapéz akkor lehet távolabb lesz a "jó" sarok mint egy másik
            if (dist>distance(RobotModel.getLocation(), newTrapeze.getCornerForRobot(i))) {
                id=i;
                dist= distance(RobotModel.getLocation(), newTrapeze.getCornerForRobot(i));
            }
        }

        double xforRobot=newTrapeze.getCornerForRobot(id).getX()-RobotModel.getRadius()*2;
        if (id==2 || id==1){
            xforRobot=newTrapeze.getCornerForRobot(id).getX()+RobotModel.getRadius()*2;
        }

        dist=distance(RobotModel.getLocation(), new Coordinate(xforRobot, newTrapeze.getCornerForRobot(id).getY()));

        double degree=Math.toDegrees( Math.atan2 ((newTrapeze.getCornerForRobot(id).getY()-RobotModel.getLocation().getY()), (xforRobot-RobotModel.getLocation().getX())));

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

        dist = distance(RobotModel.getLocation(), newTrapeze.getCornerForRobot(id));
        degree=Math.toDegrees( Math.atan2 ((newTrapeze.getCornerForRobot(id).getY()-RobotModel.getLocation().getY()), (newTrapeze.getCornerForRobot(id).getX()-RobotModel.getLocation().getX())));

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

    private double distance (Coordinate from, Coordinate to){
        return Math.sqrt(Math.pow(from.getX()-to.getX(),2)+Math.pow(from.getY()-to.getY(),2));
    }


}
