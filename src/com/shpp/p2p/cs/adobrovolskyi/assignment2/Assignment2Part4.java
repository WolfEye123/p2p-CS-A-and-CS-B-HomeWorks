package com.shpp.p2p.cs.adobrovolskyi.assignment2;

import acm.graphics.*;
import java.awt.*;

import com.shpp.cs.a.graphics.WindowProgram;


/**
 *  This program draws flags of those countries that
 *  use a three-colored flag with vertical stripes.. Artem Dobrovolskyi @2018
 */
public class Assignment2Part4 extends WindowProgram {
    public static final int APPLICATION_WIDTH = 500;
    public static final int APPLICATION_HEIGHT = 400;
    /* The rectangle width. */
    private static final double RECTWIDTH = 100;
    /* The rectangle height. */
    private static final double RECTHEIGHT = 300;

    public void run(){
        drawFlag();
    }

    /**
     * This program draws the flags of those countries that
     * use a three-colored flag with vertical stripes.
     * Sets the flag size, places it in the center of the window
     * and signs which country this flag
     */
    private void drawFlag() {
        double width = getWidth();
        double height = getHeight();
        double xCenter = width / 2.0;
        double yCenter = height / 2.0;
        drawRectangle((xCenter-RECTWIDTH / 2.0)-(RECTWIDTH), yCenter-RECTHEIGHT / 2.0, Color.BLACK);
        drawRectangle((xCenter-RECTWIDTH / 2.0), yCenter-RECTHEIGHT / 2.0, Color.YELLOW);
        drawRectangle((xCenter-RECTWIDTH / 2.0)+(RECTWIDTH), yCenter-RECTHEIGHT / 2.0, Color.RED);
        writeText(width, height);
    }

    /**
     * This method writes which country is the flag
     *
     * @param width The window width used for text positioning.
     * @param height The window height used for text positioning.
     */
    private void writeText(double width, double height) {
        GLabel label = new GLabel("Flag of Belgium.");
        label.setVisible(true);
        label.setFont("Times New Roman-16");
        label.setColor(Color.BLACK);
        double x = width - label.getWidth();
        double y = height - label.getAscent() + label.getHeight()/2.0;
        label.setLocation(x, y);
        add(label);

    }

    /**
     * This method draws a rectangle to create a country flag.
     *
     * @param x The x coordinate of the upper-left corner of the bounding box for the rectangle.
     * @param y The y coordinate of the upper-left corner of the bounding box for the rectangle.
     * @param color The rectangle color.
     */
    private void drawRectangle(double x, double y, Color color) {
        GRect rect = new GRect(x, y, RECTWIDTH, RECTHEIGHT);
        rect.setFilled(true);
        rect.setColor(color);
        add(rect);
    }
}
