package com.annalaczko.onlab.model;

import java.awt.*;
import java.util.*;

public class PathFinder {
    public static boolean[][] neighbourMatrix;
    public static ArrayList<Trapeze> trapezes=new ArrayList<>();
    private static ArrayList<Coordinate> coordinates=new ArrayList<>();
    public static ArrayList<Trapeze> finaltrapezes=new ArrayList<>();

    private static void resetMatrix(){
        neighbourMatrix= new boolean[trapezes.size()][trapezes.size()];
        for (int i=0; i< trapezes.size(); i++){
            for (int j=0; j< trapezes.size(); j++){
                neighbourMatrix[i][j]=false;
            }
        }
    }

    public static void toTrapezes(){
        for (int i=0; i<coordinates.size(); i++) {
            if (i==0) {
                Coordinate [] cs={RoomModel.getCorner(0), new Coordinate(coordinates.get(0).getX(),0), new Coordinate(coordinates.get(0).getX(),RoomModel.getHeight()), RoomModel.getCorner(3)};
                Trapeze t = new Trapeze(cs);
                t.isEnd=true;
                trapezes.add(t);
                System.out.println("elso");
            } else if (i==coordinates.size()-1){
                Coordinate [] cs={
                        new Coordinate(coordinates.get(i-2).getX(),0),
                        new Coordinate(coordinates.get(i).getX(),0),
                        new Coordinate(coordinates.get(i).getX(),coordinates.get(i).getY()),
                        new Coordinate(coordinates.get(i-2).getX(),coordinates.get(i-2).getY())
                };
                trapezes.add(new Trapeze(cs));
                System.out.println("utolso");
            } else if(coordinates.get(i).getY()<coordinates.get(i+1).getY()){
                Coordinate [] cs={
                        new Coordinate(coordinates.get(i-1).getX(),0),
                        new Coordinate(coordinates.get(i).getX(),0),
                        new Coordinate(coordinates.get(i).getX(),coordinates.get(i).getY()),
                        new Coordinate(coordinates.get(i-1).getX(),coordinates.get(i-1).getY())
                };
                trapezes.add(new Trapeze(cs));

                Coordinate [] cs2={
                        new Coordinate(coordinates.get(i).getX(),0),
                        new Coordinate(coordinates.get(i+1).getX(),0),
                        new Coordinate(coordinates.get(i+1).getX(),coordinates.get(i+1).getY()),
                        new Coordinate(coordinates.get(i).getX(),coordinates.get(i).getY())
                };
                trapezes.add(new Trapeze(cs2));
            } else{
                Coordinate [] cs={
                        new Coordinate(coordinates.get(i-1).getX(),coordinates.get(i-1).getY()),
                        new Coordinate(coordinates.get(i).getX(),coordinates.get(i).getY()),
                        new Coordinate(coordinates.get(i).getX(),RoomModel.getHeight()),
                        new Coordinate(coordinates.get(i-1).getX(),RoomModel.getHeight())
                };
                trapezes.add(new Trapeze(cs));

                Coordinate [] cs2={
                        new Coordinate(coordinates.get(i).getX(),coordinates.get(i).getY()),
                        new Coordinate(coordinates.get(i+1).getX(),coordinates.get(i+1).getY()),
                        new Coordinate(coordinates.get(i+1).getX(),RoomModel.getHeight()),
                        new Coordinate(coordinates.get(i).getX(),RoomModel.getHeight())
                };
                trapezes.add(new Trapeze(cs2));
            }
        }
        Coordinate [] cs={new Coordinate(coordinates.get(coordinates.size()-1).getX(),0), RoomModel.getCorner(1), RoomModel.getCorner(2), new Coordinate(coordinates.get(coordinates.size()-1).getX(),RoomModel.getHeight())};
        Trapeze t = new Trapeze(cs);
        t.isEnd=true;
        trapezes.add(t);
    }

    public static void initMatrix(){

        for (int i=0; i<trapezes.size();i++) {
           for (int j=0; j<trapezes.size();j++) {
               int nc=0;
               if(trapezes.get(i).isEnd || trapezes.get(j).isEnd) nc++;
               for (int t1i=0; t1i<4; t1i++) {

                   for (int t2i=0; t2i<4; t2i++) {
                       if (trapezes.get(i).xpoints[t1i]==trapezes.get(j).xpoints[t2i] && trapezes.get(i).ypoints[t1i] == trapezes.get(j).ypoints[t2i]) {
                           nc++;
                       }
                   }
               }
               if (nc>1){
                   neighbourMatrix [i] [j]=true;
               }
            }
        }
    }

    public static void Calculate() throws Exception {

        initCoordinates();
        toTrapezes();
        resetMatrix();
        initMatrix();

        hamilton();
    }

    public static void hamilton (){
        int id=0;
        boolean [] alltrue=new boolean[trapezes.size()];
        for (int i = 0; i < alltrue.length; i++) {
            alltrue[i]=false;
        }
        finaltrapezes.add(trapezes.get(id));alltrue[id]=true;
        while (!allTrue(alltrue)){

            for (int i = 0; i < trapezes.size(); i++) {
                if (id!=i && neighbourMatrix[id][i] && !alltrue[i]){

                    id=i;
                    finaltrapezes.add(trapezes.get(id));
                    alltrue[id]=true;
                    System.out.println(id);
                    break;
                }
            }
        }

    }

    public static boolean allTrue(boolean [] trap){
        for (int i = 0; i < trapezes.size(); i++) {
            if (!trap[i]){
                return false;
            }
        }
        return true;
    }
    //private static void

    public static void initCoordinates(){
        for (Polygon object: RoomModel.objects) {
            for (int i=0; i<object.npoints;i++){
                coordinates.add(new Coordinate(object.xpoints[i], object.ypoints[i]));
            }
        }
        Collections.sort(coordinates, new Comparator<Coordinate>() {
            @Override
            public int compare(Coordinate o1, Coordinate o2) {
                return Double.compare(o1.getX(), o2.getX());
            }
        });

    }
}
