package com.shpp.p2p.cs.adobrovolskyi.assignment8;

import acm.graphics.GObject;
import acm.graphics.GPolygon;
import com.shpp.cs.a.graphics.WindowProgram;

import java.awt.*;
import java.util.ArrayList;

/**
 * creating snowflakes and setting them the trajectory of the fall of the sinusoid
 */

public class Assignment8Part1 extends WindowProgram {
    public static final int APPLICATION_WIDTH = 1200;
    public static final int APPLICATION_HEIGHT = 600;
    private ArrayList<GObject> snow;
    private Color[] COLORS;


    public void run() {
        createSnowFlakes();
        setMoveToSnowFlakes();
    }

    /**
     * setting the snowflakes the trajectory of the fall of the sinusoid and rotation
     */
    private void setMoveToSnowFlakes() {
        for (int i = 1; i < 50; i++) {
            /* sine wave offset coefficient */
            double a = (i * Math.PI) / 4;
            /* sine wave shift variation */
            if (i < 5) {
                a = a * 1;
            } else if (i >= 6 && i < 10) {
                a = a * (-1);
            } else if (i >= 10 && i < 15) {
                a = a * 1;
            } else if (i >= 15 && i < 20) {
                a = a * (-1);
            } else if (i >= 20 && i < 25) {
                a = a * 1;
            } else if (i >= 25 && i < 30) {
                a = a * (-2);
            } else if (i >= 30 && i < 35) {
                a = a * 2;
            } else if (i >= 35 && i < 40) {
                a = a * (-2);
            } else if (i >= 40 && i < 45) {
                a = a * 2;
            } else if (i >= 45 && i < 50) {
                a = a * (-2);
            }
            /* move and rotate snowflakes */
            GPolygon n = (GPolygon) snow.get(0);
            n.rotate(-5);
            snow.get(0).move(a,i);
            /* move and rotate snowflakes */
            GPolygon n1 = (GPolygon) snow.get(1);
            n1.rotate(-2.5);
            snow.get(1).move(a*1.5,i*1.5);
            /* move and rotate snowflakes */
            GPolygon n2 = (GPolygon) snow.get(2);
            n2.rotate(-1);
            snow.get(2).move(a*2.2,i*2.2);
            /* move and rotate snowflakes */
            GPolygon n3 = (GPolygon) snow.get(3);
            n3.rotate(-2);
            snow.get(3).move(a*3,i*3);
            /* move and rotate snowflakes */
            GPolygon n4 = (GPolygon) snow.get(4);
            n4.rotate(1);
            snow.get(4).move(a*1.9,i*1.9);
            /* move and rotate snowflakes */
            GPolygon n5 = (GPolygon) snow.get(5);
            n5.rotate(1.5);
            snow.get(5).move(a*4,i*4);
            /* move and rotate snowflakes */
            GPolygon n6 = (GPolygon) snow.get(6);
            n6.rotate(2);
            snow.get(6).move(a*2.5,i*2.5);
            /* move and rotate snowflakes */
            GPolygon n7 = (GPolygon) snow.get(7);
            n7.rotate(1.5);
            snow.get(7).move(a*2,i*2);
            /* move and rotate snowflakes */
            GPolygon n8 = (GPolygon) snow.get(8);
            n8.rotate(-2);
            snow.get(8).move(a*1.8,i*1.8);


            pause(180);
        }
    }

    /**
     * making snowflakes
     */
    private void createSnowFlakes() {
        /* adding colors for snowflakes */
        COLORS = new Color[]{Color.BLUE, Color.RED, Color.MAGENTA, Color.BLACK, Color.GREEN, Color.YELLOW, Color.CYAN, Color.ORANGE, Color.BLUE, Color.GREEN};

        /* placing snowflakes in erylist for convenient use */
        snow = new ArrayList<>();
        for (int i = 100, j =0; i < 1000; i += 100, j++) {
            GPolygon snowflakes = new GPolygon();
            ((GPolygon) snowflakes).addVertex(100 + i, j+10);
            ((GPolygon) snowflakes).addVertex(90 + i, j+35);
            ((GPolygon) snowflakes).addVertex(65 + i, j+45);
            ((GPolygon) snowflakes).addVertex(90 + i, j+55);
            ((GPolygon) snowflakes).addVertex(100 + i, j+80);
            ((GPolygon) snowflakes).addVertex(110 + i, j+55);
            ((GPolygon) snowflakes).addVertex(135 + i, j+45);
            ((GPolygon) snowflakes).addVertex(110 + i, j+35);
            ((GPolygon) snowflakes).addVertex(100 + i, j+10);
            ((GPolygon) snowflakes).setFilled(true);
            ((GPolygon) snowflakes).setFillColor(COLORS[j]);
            add(snowflakes);
            snow.add(snowflakes);
        }
    }
}
