package com.annalaczko.onlab.model;

import com.annalaczko.onlab.model.data.Coordinate;
import com.annalaczko.onlab.model.data.Trapeze;

import java.util.ArrayList;

/**
 *
 */
public class PathFinder {

    public static boolean[][] neighbourMatrix; //trapézok szomszédsági mátrixa
    public static boolean[] havebeenhere;
    public static ArrayList<Trapeze> finaltrapezes = new ArrayList<>(); //trapézok listája sorrendben ahogyan majd végig megy a robot.
    static boolean[] alltrue;

    /**
     * reseteli a szomszédsági mátrixot
     */
    private static void resetMatrix() {
        neighbourMatrix = new boolean[TrapezeGenerator.trapezes.size()][TrapezeGenerator.trapezes.size()];
        for (int i = 0; i < TrapezeGenerator.trapezes.size(); i++) {
            for (int j = 0; j < TrapezeGenerator.trapezes.size(); j++) {
                neighbourMatrix[i][j] = false;
            }
        }
    }


    public static void Calculate() throws Exception {

        TrapezeGenerator.startGenerating();
        resetMatrix();
        initMatrix();

        hamilton();
    }
    //endregion

    /**
     * Szomszédsági mátrix felépítése
     */

    private static void initMatrix() {

        for (int i = 0; i < TrapezeGenerator.trapezes.size(); i++) {
            System.out.println(TrapezeGenerator.trapezes.get(i).xpoints[0] + " " + TrapezeGenerator.trapezes.get(i).ypoints[0]);
            for (int j = 0; j < TrapezeGenerator.trapezes.size(); j++) {

                if (i != j) {
                    neighbourMatrix[i][j] = areTheyNeighbors(TrapezeGenerator.trapezes.get(i), TrapezeGenerator.trapezes.get(j));
                }
            }
        }

        for (int i = 0; i < TrapezeGenerator.trapezes.size(); i++) {
            for (int j = 0; j < TrapezeGenerator.trapezes.size(); j++) {
                System.out.print(neighbourMatrix[i][j] + " ");
            }
            System.out.println();
        }

    }

    private static boolean areTheyNeighbors(Trapeze trapeze1, Trapeze trapeze2) {
        int cornercount = 0;
        for (int icorners = 0; icorners < 4; icorners++) {
            for (int jcorners = 0; jcorners < 4; jcorners++) {
                if (trapeze1.xpoints[icorners] == trapeze2.xpoints[jcorners] && trapeze1.ypoints[icorners] == trapeze2.ypoints[jcorners]) {
                    cornercount += 1;
                }
            }
        }
        if (cornercount == 2) return true;
        if (cornercount == 0) return false;

        Coordinate same = new Coordinate(-40, -40);
        Coordinate dif1 = new Coordinate(-40, -40);
        Coordinate dif2 = new Coordinate(-40, -40);

        for (int icorners = 0; icorners < 4; icorners++) {
            for (int jcorners = 0; jcorners < 4; jcorners++) {
                if (trapeze1.xpoints[icorners] == trapeze2.xpoints[jcorners]) {
                    if (trapeze1.ypoints[icorners] == trapeze2.ypoints[jcorners]) {
                        same = new Coordinate(trapeze1.xpoints[icorners], trapeze1.ypoints[icorners]);
                    }
                }
            }
        }

        for (int i = 0; i < 4; i++) {
            if (trapeze1.xpoints[i] == same.getX() && trapeze1.ypoints[i] != same.getY())
                dif1 = new Coordinate(trapeze1.xpoints[i], trapeze1.ypoints[i]);
            if (trapeze2.xpoints[i] == same.getX() && trapeze2.ypoints[i] != same.getY())
                dif2 = new Coordinate(trapeze2.xpoints[i], trapeze2.ypoints[i]);
        }

        //System.out.println("Plus coordinates  "+same.getX()+"-"+same.getY() + "   " +dif1.getX()+"-"+dif1.getY()+ "    " +dif2.getX()+"-"+dif2.getY()  );

        if (same.getY() - dif1.getY() < 0 && same.getY() - dif2.getY() < 0 || same.getY() - dif1.getY() > 0 && same.getY() - dif2.getY() > 0) {
            return true;
        }
        return false;
    }

    /**
     * inicializáljuk az értékeket, kiszámoljuk az utat
     *
     * @throws Exception
     */


    private static void hamilton() { //ez hamilton??
        int id = 0;
        int lastID = 0;
        boolean hasNewNeighbour;
        initAlltrue();
        initHavebeenhere();

        finaltrapezes.add(TrapezeGenerator.trapezes.get(id));
        alltrue[id] = true;
        while (!allTrue(alltrue)) {
            hasNewNeighbour = false;

            /**
             * HA (aktuális trapéznak nincs még nembejárt szomszédja)
             * Lépjünk vissza egyet
             */

            for (int i = 0; i < TrapezeGenerator.trapezes.size(); i++) {
                if (neighbourMatrix[id][i] && !alltrue[i]) {
                    hasNewNeighbour = true;
                }
            }

            if (!hasNewNeighbour) {
                finaltrapezes.add(TrapezeGenerator.trapezes.get(id)); //Vízszintesben itt beleszaladunk valami nagy gebaszba és itt ragadunk :(
                System.out.println(finaltrapezes.size());
                havebeenhere[id] = true;
                id = lastID;
                continue;
            }

            for (int i = 0; i < TrapezeGenerator.trapezes.size(); i++) {
                if (neighbourMatrix[id][i] && !alltrue[i]) { //ha nem ugyanaz a trapéz kerül sorra, de szomszédosak és még nem jártunk a trapézban
                    lastID = id;
                    id = i;
                    finaltrapezes.add(TrapezeGenerator.trapezes.get(id));
                    alltrue[id] = true;
                    break;
                }
            }
        }

    }

    private static void initAlltrue() {
        alltrue = new boolean[TrapezeGenerator.trapezes.size()];
        for (int i = 0; i < alltrue.length; i++) {
            alltrue[i] = false;
        }
    }

    private static void initHavebeenhere() {
        havebeenhere = new boolean[TrapezeGenerator.trapezes.size()];
        for (int i = 0; i < havebeenhere.length; i++) {
            havebeenhere[i] = false;
        }
    }

    /**
     * Ellenőrzi hogy voltunk-e mindegyik trapéznál
     *
     * @param trap trapéz db számú boolean tömb. igaz ha voltunk már ott
     * @return
     */
    public static boolean allTrue(boolean[] trap) {
        for (int i = 0; i < TrapezeGenerator.trapezes.size(); i++) {
            if (!trap[i]) {
                return false;
            }
        }
        return true;
    }


}
