package com.annalaczko.onlab.model;

public class Timer extends Thread {

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
        System.out.println("Time spent" + ms);
    }
}
