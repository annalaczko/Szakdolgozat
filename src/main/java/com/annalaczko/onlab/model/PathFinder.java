package com.annalaczko.onlab.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 *
 */
public class PathFinder {

    public static boolean[][] neighbourMatrix; //trapézok szomszédsági mátrixa
    public static ArrayList<Trapeze> trapezes=new ArrayList<>(); //trapézok listája
    private static ArrayList<Coordinate> coordinates=new ArrayList<>();
    public static ArrayList<Trapeze> finaltrapezes=new ArrayList<>(); //trapézok listája sorrendben ahogyan majd végig megy a robot.
    static boolean [] alltrue;
    public static boolean [] havebeenhere;

    /**
     * reseteli a szomszédsági mátrixot
     */
    private static void resetMatrix(){
        neighbourMatrix= new boolean[trapezes.size()][trapezes.size()];
        for (int i=0; i< trapezes.size(); i++){
            for (int j=0; j< trapezes.size(); j++){
                neighbourMatrix[i][j]=false;
            }
        }
    }

    public static  void findTrapeze(int i, int j, Polygon object){
        double x = object.coordinatesInOrder.get(j).getX();
        double y = object.coordinatesInOrder.get(j).getY();
        Coordinate [] cs=new Coordinate[4];
        if (j == 0) {
            if (i == 0) {
                cs =new Coordinate[] {
                        RoomModel.getCorner(0),
                        new Coordinate(x, 0),
                        new Coordinate(x, RoomModel.getHeight()),
                        RoomModel.getCorner(3)
                };
            } else {
                cs = new Coordinate[]{
                        new Coordinate(RoomModel.objects.get(i - 1).getLastCorner().getX(), 0),
                        new Coordinate(x, 0),
                        new Coordinate(x, RoomModel.getHeight()),
                        new Coordinate(RoomModel.objects.get(i - 1).getLastCorner().getX(), RoomModel.getHeight())
                };
            }
        } else {
            int id=object.findCornerId(new Coordinate(x,y));
            if (y<object.getFirstCorner().getY()||j==object.npoints-1){
                cs =new Coordinate[] {
                        new Coordinate(object.xpoints[id-1], 0),
                        new Coordinate(x, 0),
                        new Coordinate(x, y),
                        new Coordinate(object.xpoints[id-1], object.ypoints[id-1])
                };
                if (j==object.npoints-1) trapezes.add(new Trapeze(cs));
            }
            if (y>=object.getFirstCorner().getY()||j==object.npoints-1){
                if (id==object.npoints-1) { //||j==object.npoints-1
                    id=-1;
                }
                cs = new Coordinate[]{
                        new Coordinate(object.xpoints[id + 1], object.ypoints[id + 1]),
                        new Coordinate(x, y),
                        new Coordinate(x, RoomModel.getHeight()),
                        new Coordinate(object.xpoints[id + 1],RoomModel.getHeight() )
                };
            }

        }
        trapezes.add(new Trapeze(cs));

    }

    public static void toTrapeze(){

        for (int i=0; i<RoomModel.objects.size(); i++) {
            Polygon object = RoomModel.objects.get(i);
            for (int j = 0; j < object.npoints; j++) {
                findTrapeze(i,j,object);
            }
        }

        Coordinate [] cs ={
                new Coordinate(RoomModel.objects.get(RoomModel.objects.size()-1).getLastCorner().getX(), 0),
                RoomModel.getCorner(1),
                RoomModel.getCorner(2),
                new Coordinate(RoomModel.objects.get(RoomModel.objects.size()-1).getLastCorner().getX(), RoomModel.getHeight())
        };

        trapezes.add(new Trapeze(cs));

        System.out.println("TRAPEZES");
        for (Trapeze trapeze: trapezes) {
            for (int i = 0; i < 4; i++) {
                System.out.println(trapeze.xpoints[i]+ "-" + trapeze.ypoints[i]);
            }
            System.out.println();

        }
        System.out.println("TRAPEZES");

        /*for (Trapeze t: trapezes) {
            for (int i = 0; i < 4 ; i++) {
                System.out.println(t.xpoints[i]+" "+t.ypoints[i]);
            }
            System.out.println();

        }*/
    }

    /**
     * Szomszédsági mátrix felépítése
     */

    public static void initMatrix(){

        for (int i=0; i<trapezes.size();i++) {
            System.out.println(trapezes.get(i).xpoints[0] +" " + trapezes.get(i).ypoints[0]);
            for (int j=0; j<trapezes.size();j++) {

                if (i!=j) {
                    neighbourMatrix[i][j]= areTheyNeighbors(trapezes.get(i), trapezes.get(j));
                }
            }
        }

        for (int i=0; i<trapezes.size();i++) {
            for (int j=0; j<trapezes.size();j++) {
                System.out.print(neighbourMatrix[i][j] + " ");
            }
            System.out.println();
        }

    }

    private static boolean areTheyNeighbors(Trapeze trapeze1, Trapeze trapeze2){
        int cornercount=0;
        for (int icorners=0; icorners<4; icorners++){
            for (int jcorners=0; jcorners<4; jcorners++){
                if (trapeze1.xpoints[icorners]==trapeze2.xpoints[jcorners]  &&    trapeze1.ypoints[icorners]==trapeze2.ypoints[jcorners]){
                    cornercount+=1;
                }
            }
        }
        if (cornercount==2) return true;
        if (cornercount==0) return false;

        Coordinate same= new Coordinate(-40,-40);
        Coordinate dif1= new Coordinate(-40,-40);
        Coordinate dif2= new Coordinate(-40,-40);

        for (int icorners=0; icorners<4; icorners++){
            for (int jcorners=0; jcorners<4; jcorners++){
                if (trapeze1.xpoints[icorners]==trapeze2.xpoints[jcorners]){
                    if (trapeze1.ypoints[icorners]==trapeze2.ypoints[jcorners]) {
                        same =new Coordinate(trapeze1.xpoints[icorners],trapeze1.ypoints[icorners]);
                    }
                }
            }
        }

        for (int i = 0; i < 4; i++) {
            if (trapeze1.xpoints[i]==same.getX() && trapeze1.ypoints[i]!=same.getY()) dif1 = new Coordinate(trapeze1.xpoints[i], trapeze1.ypoints[i]);
            if (trapeze2.xpoints[i]==same.getX() && trapeze2.ypoints[i]!=same.getY()) dif2 = new Coordinate(trapeze2.xpoints[i], trapeze2.ypoints[i]);
        }

        //System.out.println("Plus coordinates  "+same.getX()+"-"+same.getY() + "   " +dif1.getX()+"-"+dif1.getY()+ "    " +dif2.getX()+"-"+dif2.getY()  );

        if (same.getY()-dif1.getY()<0 && same.getY()-dif2.getY()<0 || same.getY()-dif1.getY()>0 && same.getY()-dif2.getY()>0) {
            return true;
        }
        return false;
    }

    /**
     * inicializáljuk az értékeket, kiszámoljuk az utat
     * @throws Exception
     */
    public static void Calculate() throws Exception {

        initCoordinates();
        toTrapeze();
        resetMatrix();
        initMatrix();

        hamilton(); //TODO: Hamiltonban végtelen ciklusba futunk :)
        System.out.println("hammm");
    }

    public static void hamilton (){ //ez hamilton??
        int id=0;
        int lastID=0;
        boolean hasNewNeighbour;
        initAlltrue();
        initHavebeenhere();

        finaltrapezes.add(trapezes.get(id));
        alltrue[id]=true;
        while (!allTrue(alltrue)){
            hasNewNeighbour=false;

            /**
             * HA (aktuális trapéznak nincs még nembejárt szomszédja)
             * Lépjünk vissza egyet
             */

            for (int i = 0; i < trapezes.size(); i++) {
                if (neighbourMatrix[id][i] && !alltrue[i]){
                    hasNewNeighbour=true;
                }
            }

            if (!hasNewNeighbour){
                finaltrapezes.add(trapezes.get(id));
                havebeenhere[id]=true;
                id=lastID;
                continue;
            }

            for (int i = 0; i < trapezes.size(); i++) {
                if (neighbourMatrix[id][i] && !alltrue[i]){ //ha nem ugyanaz a trapéz kerül sorra, de szomszédosak és még nem jártunk a trapézban
                    lastID=id;
                    id=i;
                    finaltrapezes.add(trapezes.get(id));
                    alltrue[id]=true;
                    break;
                }
            }
        }

    }

    private static void initAlltrue(){
        alltrue=new boolean[trapezes.size()];
        for (int i = 0; i < alltrue.length; i++) {
            alltrue[i]=false;
        }
    }

    private static void initHavebeenhere(){
        havebeenhere=new boolean[trapezes.size()];
        for (int i = 0; i < havebeenhere.length; i++) {
            havebeenhere[i]=false;
        }
    }

    /**
     * Ellenőrzi hogy voltunk-e mindegyik trapéznál
     * @param trap trapéz db számú boolean tömb. igaz ha voltunk már ott
     * @return
     */
    public static boolean allTrue(boolean [] trap){
        for (int i = 0; i < trapezes.size(); i++) {
            if (!trap[i]){
                return false;
            }
        }
        return true;
    }

    /**
     * az összes objektum koordinátáit felveszi majd x kooridináta alapján sorrendezi
     */
    public static void initCoordinates(){
        for ( Polygon object: RoomModel.objects) {
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
