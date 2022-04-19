package com.annalaczko.onlab.model;

import java.awt.*;
import java.util.List;

public class PathFinder {
    public static boolean[][] neighbourMatrix;
    private static List<Tetragon> trapezes;

    private static void resetMatrix(){
        neighbourMatrix= new boolean[trapezes.size()][trapezes.size()];
        for (int i=0; i< trapezes.size(); i++){
            for (int j=0; j< trapezes.size(); j++){
                neighbourMatrix[i][j]=false;
            }
        }
    }

    public static void Calculate(List<Polygon> objects, ){

    }
}
