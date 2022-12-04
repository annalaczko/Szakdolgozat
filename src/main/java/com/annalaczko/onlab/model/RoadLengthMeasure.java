package com.annalaczko.onlab.model;

import com.annalaczko.onlab.model.data.RobotModel;

public class RoadLengthMeasure extends Thread {

    private double ms = 0;

    private boolean notOver = true;

    @Override
    public void run() {
        while (notOver) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            ms++;
        }
    }

    public void writeTime() {
        System.out.println("Time spent" + ms);
    }

    public void over() {
        notOver = false;
        System.out.println("Length" + ms * RobotModel.speed / 20 / 100);
    }
}
