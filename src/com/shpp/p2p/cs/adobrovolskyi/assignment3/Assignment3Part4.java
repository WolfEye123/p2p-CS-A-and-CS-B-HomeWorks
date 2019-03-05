package com.shpp.p2p.cs.adobrovolskyi.assignment3;

import acm.graphics.GRect;
import com.shpp.cs.a.graphics.WindowProgram;

/**
 * This program draws a pyramid. Artem Dobrovolskyi @2018
 */
public class Assignment3Part4 extends WindowProgram {
    private static final int BRICK_HEIGHT = 20;
    private static final int BRICK_WIDTH = 50;
    private static final int BRICKS_IN_BASE = 5;

    public static final int APPLICATION_WIDTH = 300;
    public static final int APPLICATION_HEIGHT = 300;


    public void run() {
        drawThePyramid();
    }

    /**
     * This method draws a pyramid
     */
    private void drawThePyramid() {
        double width = getWidth();
        double height = getHeight();
        double heightOfPyramid = (height - BRICKS_IN_BASE * BRICK_HEIGHT);

        for (int i = 0; i < BRICKS_IN_BASE; i++) {
            double y = heightOfPyramid + i * BRICK_HEIGHT;
            double centerWidthOfPyramid = (width - (i + 1) * BRICK_WIDTH) / 2;

            for (int j = 0; j <= i; j++) {
                double x = centerWidthOfPyramid + j * BRICK_WIDTH;
                createBrick(x, y);
            }
        }
    }

    /**
     * This method draws bricks for the pyramid
     *
     * @param x The x coordinate of the upper-left corner of the bounding box for the bricks.
     * @param y The y coordinate of the upper-left corner of the bounding box for the bricks.
     */
    private void createBrick(double x, double y) {
        GRect brick = new GRect(x, y, BRICK_WIDTH, BRICK_HEIGHT);
        add(brick);
    }
}