package com.annalaczko.onlab.model;

import com.annalaczko.onlab.model.data.Trapeze;

import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 */
public class PathFinder {

    public static boolean[][] neighbourMatrix; //trapézok szomszédsági mátrixa

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

        ///TrapezeGenerator.writeTrapezes();


        depthFirstSearch();
        //writeTrapezes();
        System.out.println("FINAL SIZE: " + finaltrapezes.size());
        System.out.println("TRAPEZE SIZE: " + TrapezeGenerator.trapezes.size());
    }
    //endregion

    /**
     * Szomszédsági mátrix felépítése
     */

    private static void initMatrix() {

        for (int i = 0; i < TrapezeGenerator.trapezes.size(); i++) {
            for (int j = 0; j < TrapezeGenerator.trapezes.size(); j++) {

                if (i != j) {
                    neighbourMatrix[i][j] = areTheyNeighbours(TrapezeGenerator.trapezes.get(i), TrapezeGenerator.trapezes.get(j));
                }
            }
        }

    }

    private static boolean areTheyNeighbours(Trapeze trapeze1, Trapeze trapeze2) {
        double t1c1, t1c2, t2c1, t2c2;

        for (int icorners = 0; icorners < 4; icorners++) {
            for (int jcorners = 0; jcorners < 4; jcorners++) {
                if (trapeze1.xpoints[icorners] == trapeze2.xpoints[jcorners]) {
                    if ((icorners == 0 || icorners == 3) && (jcorners == 1 || jcorners == 2)) {
                        t1c1 = trapeze1.ypoints[0];
                        t1c2 = trapeze1.ypoints[3];
                        t2c1 = trapeze2.ypoints[1];
                        t2c2 = trapeze2.ypoints[2];
                        if ((t1c1 <= t2c2 && t1c2 >= t2c2) || (t1c1 <= t2c1 && t1c2 >= t2c1) || (t1c1 >= t2c1 && t1c2 <= t2c2))
                            return true;
                    }
                    if ((icorners == 1 || icorners == 2) && (jcorners == 3 || jcorners == 0)) {
                        t1c1 = trapeze1.ypoints[1];
                        t1c2 = trapeze1.ypoints[2];
                        t2c1 = trapeze2.ypoints[0];
                        t2c2 = trapeze2.ypoints[3];
                        if ((t1c1 <= t2c2 && t1c2 >= t2c2) || (t1c1 <= t2c1 && t1c2 >= t2c1) || (t1c1 >= t2c1 && t1c2 <= t2c2))
                            return true;
                    }


                }
            }
        }
        return false;
    }


    /**
     * inicializáljuk az értékeket, kiszámoljuk az utat
     *
     * @throws Exception
     */


    private static void depthFirstSearch() { //ez hamilton??
        int id = 0;
        int lastID = 0;
        ArrayList<Trapeze> finaltrapezes2 = new ArrayList<>();
        boolean hasNewNeighbour;
        initAlltrue();

        finaltrapezes.add(TrapezeGenerator.trapezes.get(id));
        finaltrapezes2.add(TrapezeGenerator.trapezes.get(id));
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
                    break;
                }
            }

            if (!hasNewNeighbour) {
                finaltrapezes2.remove(TrapezeGenerator.trapezes.get(id));
                id = TrapezeGenerator.trapezes.indexOf(finaltrapezes2.get(finaltrapezes2.size() - 1));
                finaltrapezes.add(TrapezeGenerator.trapezes.get(id));

                continue;
            }

            for (int i = 0; i < TrapezeGenerator.trapezes.size(); i++) {
                if (neighbourMatrix[id][i] && !alltrue[i]) { //ha nem ugyanaz a trapéz kerül sorra, de szomszédosak és még nem jártunk a trapézban
                    lastID = id;
                    id = i;
                    finaltrapezes.add(TrapezeGenerator.trapezes.get(id));
                    finaltrapezes2.add(TrapezeGenerator.trapezes.get(id));
                    alltrue[id] = true;
                    break;
                }
            }
            //writeTrapezes();
        }

    }

    private static void initAlltrue() {
        alltrue = new boolean[TrapezeGenerator.trapezes.size()];
        Arrays.fill(alltrue, false);
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

    private static void writeTrapezes() {
        System.out.println("Finale");

        for (Trapeze trapeze : finaltrapezes) {
            System.out.print(" " + TrapezeGenerator.trapezes.indexOf(trapeze));
        }
        System.out.println();
    }

}
