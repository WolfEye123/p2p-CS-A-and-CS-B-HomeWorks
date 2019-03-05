package com.shpp.p2p.cs.adobrovolskyi.assignment2;

import acm.graphics.*;
import java.awt.*;
import com.shpp.cs.a.graphics.WindowProgram;

/**
 * This program creates an optical illusion.. Artem Dobrovolskyi @2018
 */
public class Assignment2Part2 extends WindowProgram {

    public static final int APPLICATION_WIDTH = 500;
    public static final int APPLICATION_HEIGHT = 500;

    /* The radius of all circles */
    private static final double RADIUS = 50;


    public void run(){
        createIllusion();
    }

    /**
     * This method draws a rectangle on top of the circles thus creating an optical illusion.
     */
    private void createIllusion() {
        double width = getWidth();
        double height = getHeight();

        drawCircle(RADIUS,RADIUS);
        drawCircle(width-RADIUS,height-RADIUS);
        drawCircle(width-RADIUS,RADIUS);
        drawCircle(RADIUS,height-RADIUS);
        drawRectangle(width-RADIUS*2.0,height-RADIUS*2.0);
    }

    /**
     * This method draws circles to create an optical illusion.
     *
     * @param xCenter The x coordinate of the upper-left corner of the bounding box for the circle.
     * @param yCenter The y coordinate of the upper-left corner of the bounding box for the circle.
     */
    private void drawCircle(double xCenter, double yCenter) {
        double x = xCenter - RADIUS;
        double y = yCenter - RADIUS;
        GOval circle = new GOval(x, y, 2.0 * RADIUS, 2.0 * RADIUS);
        circle.setColor(Color.BLACK);
        circle.setFilled(true);
        add(circle);
    }

    /**
     * This method draws a rectangle to create an optical illusion.
     *
     * @param width The rectangle width.
     * @param height The rectangle height.
     */
    private void drawRectangle(double width, double height) {
        GRect rect = new GRect(RADIUS, RADIUS, width, height);
        rect.setFilled(true);
        rect.setColor(Color.WHITE);
        add(rect);
    }
}