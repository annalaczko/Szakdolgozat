package com.annalaczko.onlab.model;

import java.awt.*;

public class Tetragon extends Polygon {

    //Sarkok jobb fentről óramutató iránya szerint sorban

     public double [] xpoints, ypoints;


    public Tetragon(double [] _xpoints, double[] _ypoints) throws Exception {
        if (_xpoints.length!=4 || _ypoints.length!=4) throw new Exception("The number of coordinates doesn't equals to four");
        xpoints=_xpoints;
        ypoints=_ypoints;
    }

    private int getStartingCornerIndex(){
        int index=0;
        for (int i=1; i <4 ;  i++){
            if (xpoints[index]<=xpoints[i] && ypoints[index]>=ypoints[i]){
                index=i;
            }
        }
        return index;
    }

    public Coordinate getCornerForRobot(int cornerIndex){
        //double a=(y1-y0),b=(x0-x1),c=x0*y1-x1*y0;

        int leftcorner, rightcorner;

        if (cornerIndex>0 && cornerIndex<3){
            leftcorner=cornerIndex+1;
            rightcorner=cornerIndex-1;
        } else if (cornerIndex==0){
            leftcorner=1;
            rightcorner=3;
        } else {
            leftcorner=0;
            rightcorner=2;
        }

        double angle1 = Math.atan2(ypoints[cornerIndex]-ypoints[leftcorner], xpoints[cornerIndex]-xpoints[leftcorner]);
        double angle2 = Math.atan2(ypoints[cornerIndex]-ypoints[rightcorner], xpoints[cornerIndex]-xpoints[rightcorner]);
        double desiredAngle = (angle1 - angle2)/2;
        double c = RobotModel.getRadius()/Math.sin(desiredAngle);

        double bisectorAngle=desiredAngle+angle2;

        double x= Math.cos(bisectorAngle)*c;
        double y= Math.sin(bisectorAngle)*c;

        //szögfelező


        return new Coordinate(x+xpoints[cornerIndex],y+ypoints[cornerIndex]);
    }
}
