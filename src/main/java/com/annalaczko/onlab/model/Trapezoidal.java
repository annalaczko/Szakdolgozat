package com.annalaczko.onlab.model;

import com.annalaczko.onlab.viewmodel.RobotViewModel;
import com.annalaczko.onlab.viewmodel.RoomViewModel;

import java.awt.*;
import java.util.Optional;

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
            id=reallocate(PathFinder.trapezes.get(0));
            System.out.println(tetragon.xpoints[0]+" "+tetragon.xpoints[1]+" "+tetragon.xpoints[2]+" "+tetragon.xpoints[3]+" ");
            System.out.println(tetragon.ypoints[0]+" "+tetragon.ypoints[1]+" "+tetragon.ypoints[2]+" "+tetragon.ypoints[3]+" ");
            System.out.println();
            trapeze=new Trapeze(PathFinder.trapezes.get(0), id);
            trapeze.start();
        }

    }

    private int reallocate(Tetragon trapeze){
        int id=0;
        double dist=distance(trapeze.xpoints[0], trapeze.ypoints[0]);
        for (int i=1; i<4;i++){
            if (dist>distance(trapeze.xpoints[i], trapeze.ypoints[i])) {
                dist=distance(trapeze.xpoints[i], trapeze.ypoints[i]);
                id=i;
            }
        }
        System.out.println("RX"+RobotModel.getLocation().getX()+" RY"+ RobotModel.getLocation().getY());
        System.out.println("ID"+id+ " x" + trapeze.xpoints[id]+" y "+ trapeze.ypoints[id]);
        System.out.println("DIST" + dist);

        double degree=Math.toDegrees( Math.atan2 ((RobotModel.getLocation().getY()-trapeze.ypoints[id]), (RobotModel.getLocation().getX()-trapeze.xpoints[id])));

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
