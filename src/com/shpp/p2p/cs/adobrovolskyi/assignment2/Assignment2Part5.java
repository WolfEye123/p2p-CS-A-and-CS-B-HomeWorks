package com.shpp.p2p.cs.adobrovolskyi.assignment2;

import acm.graphics.*;
import java.awt.*;
import com.shpp.cs.a.graphics.WindowProgram;

/**
 * This program creates an optical illusion.. Artem Dobrovolskyi @2018
 */
public class Assignment2Part5 extends WindowProgram {
    /* The number of rows and columns in the grid, respectively. */
    private static final int NUM_ROWS = 5;
    private static final int NUM_COLS = 6;

    /* The width and height of each box. */
    private static final double BOX_SIZE = 40;

    /* The horizontal and vertical spacing between the boxes. */
    private static final double BOX_SPACING = 10;

    /* The coefficient of displacement of squares
     * BOX_SIZE == 100% ,  BOX_SPACING == 25% from BOX_SIZE
     * 100% + 25% == 125% / 100 == 1.25      */
    private static final double BETWEEN_BOXES = 1.25;

    public static final int APPLICATION_WIDTH = 350;
    public static final int APPLICATION_HEIGHT = 600;


    public void run() {
        createIllusion();
    }

    /**
     * This method draws a grid of squares creating an optical illusion.
     */
    private void createIllusion() {
        double width = getWidth();
        double height = getHeight();
        double xCenter = width / 2.0 - BOX_SIZE * (NUM_COLS / 2.0) - (BOX_SPACING * 2.0 + BOX_SPACING * 0.5);
        double yCenter = height / 2.0 - (BOX_SIZE * 2.0 + BOX_SIZE * 0.5) - (BOX_SPACING * 2.0);
        for (int i = 0; i < NUM_COLS; i++){
            for (int j = 0; j < NUM_ROWS; j++){
                drawRectangle(xCenter + i * BOX_SIZE * BETWEEN_BOXES,
                              yCenter + j * BOX_SIZE * BETWEEN_BOXES);
            }
        }
    }

    /**
     * This method draws a rectangle to create an optical illusion.
     *
     * @param x The x coordinate of the upper-left corner of the bounding box for the rectangle.
     * @param y The y coordinate of the upper-left corner of the bounding box for the rectangle.
     */
    private void drawRectangle(double x, double y) {
        GRect rect = new GRect(x, y, BOX_SIZE, BOX_SIZE);
        rect.setFilled(true);
        rect.setColor(Color.BLACK);
        add(rect);
    }
}
