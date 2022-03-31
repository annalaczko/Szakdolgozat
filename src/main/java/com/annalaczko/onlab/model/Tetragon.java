package com.annalaczko.onlab.model;

import java.awt.*;

public class Tetragon extends Polygon {

    private int [] xpoints, ypoints;

    public Tetragon (){}

    public Tetragon(int[] _xpoints, int[] _ypoints) throws Exception {
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

    public Point getStartingPoint(){
        Point corner= new Point (xpoints[getStartingCornerIndex()]+Robot.getRadius(), ypoints[getStartingCornerIndex()]+Robot.getRadius());
        //TODO:szögfelezővel kiszámolni a koordinátát
        return corner;
    }
}
