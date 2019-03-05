package com.shpp.p2p.cs.adobrovolskyi.assignment3;

import acm.graphics.*;
import java.awt.*;
import com.shpp.cs.a.graphics.WindowProgram;

/**
 * This program draws a caterpillar. Artem Dobrovolskyi @2018
 */
public class Assignment3Part6 extends WindowProgram {
    public static final int APPLICATION_WIDTH = 900;
    public static final int APPLICATION_HEIGHT = 300;

    /* The radius of caterpillar segments*/
    private static final double RADIUS = 50;

    /* The number of caterpillar segments*/
    private static final double AMOUNT = 6;

    public void run() {
        drawTheCaterpillar();
    }

    /**
     * This method draws a caterpillar of a certain number of segments and gives it an appearance
     */
    private void drawTheCaterpillar() {
        double height = getHeight();
        /* 0.75 and 1.5 - The coefficient of displacement of a caterpillar segments
         *                along the y-axis so that it is located in the center of the window. */
        double yCenter1 = height / 2.0 - RADIUS * 0.75;
        double yCenter2 = height / 2.0 - RADIUS * 1.5;
        /* The caterpillar step */
        double step = 0;
        double animationFrames = 50;
        for (int a = 0; a < animationFrames; a++){
            if (a % 2 == 0){
                for (int i = 0; i < AMOUNT; i++){
                    if (i % 2 == 0){
                        /* 1.2 - The coefficient of displacement of a caterpillar segments along the x-axis */
                        drawSegment(step + i * RADIUS * 1.2, yCenter1);
                        if (i == AMOUNT - 1){
                            /* 1.2 - The coefficient of displacement of a caterpillar face along the x-axis
                             *       so that it is located in the center of the last segment to create the face
                             *0.75 - The coefficient of displacement of a caterpillar face along the y-axis
                             *       so that it is located in the center of the last segment to create the face*/
                            drawFace(step + i * RADIUS * 1.2 + RADIUS, yCenter1 + RADIUS * 0.75);
                        }
                    }
                    else {
                        /* 1.2 - The coefficient of displacement of a caterpillar segments along the x-axis */
                        drawSegment(step + i * RADIUS * 1.2, yCenter2);
                        if (i == AMOUNT - 1){
                            /* 1.2 - The coefficient of displacement of a caterpillar face along the x-axis
                             *       so that it is located in the center of the last segment to create the face*/
                            drawFace(step + i * RADIUS * 1.2 + RADIUS, yCenter1);
                        }
                    }
                }
            } else {
                for (int i = 0; i < AMOUNT; i++){
                    if (i % 2 == 0){
                        /* 1.2 - The coefficient of displacement of a caterpillar segments along the x-axis */
                        drawSegment(step + i * RADIUS * 1.2, yCenter2);
                        if (i == AMOUNT - 1){
                            /* 1.2 - The coefficient of displacement of a caterpillar face along the x-axis
                             *       so that it is located in the center of the last segment to create the face
                             *0.75 - The coefficient of displacement of a caterpillar face along the y-axis
                             *       so that it is located in the center of the last segment to create the face*/
                            drawFace(step + i * RADIUS * 1.2 + RADIUS, yCenter1);
                        }
                    }
                    else {
                        /* 1.2 - The coefficient of displacement of a caterpillar segments along the x-axis */
                        drawSegment(step + i * RADIUS * 1.2, yCenter1);
                        if (i == AMOUNT - 1){
                            /* 1.2 - The coefficient of displacement of a caterpillar face along the x-axis
                             *       so that it is located in the center of the last segment to create the face*/
                            drawFace(step + i * RADIUS * 1.2 + RADIUS, yCenter1 + RADIUS * 0.75);
                        }
                    }
                }
            }
            pause(5000 / animationFrames);
            if (a < animationFrames - 1);
                removeAll();
            step += 10;
        }
    }

    /**
     * This method draws a caterpillar segment
     *
     * @param x The x coordinate of the upper-left corner of the bounding box for the circle.
     * @param y The y coordinate of the upper-left corner of the bounding box for the circle.
     */
    private void drawSegment(double x, double y) {
        GOval segment = new GOval(x, y, 2 * RADIUS, 2 * RADIUS);
        segment.setFilled(true);
        segment.setFillColor(Color.RED);
        segment.setColor(Color.BLACK);
        add(segment);

        /* 2 - The coefficient of change to the radius of the circle
               in relation to the radius of the segment
           2 and 3 - The coefficient of displacement of a caterpillar mark
         *           along the x-axis and the y-axis*/
        GOval mark = new GOval(x + RADIUS / 3, y + RADIUS / 2, RADIUS / 2, RADIUS / 2);
        mark.setFilled(true);
        mark.setFillColor(Color.BLACK);
        add(mark);
    }

    /**
     * This method draws a caterpillar face
     *
     * @param x The x coordinate of the upper-left corner of the bounding box for the face.
     * @param y The y coordinate of the upper-left corner of the bounding box for the face.
     */
    private void drawFace(double x, double y){
        /* c - The cylinder size */
        double c = 30;
        GRect hat1 = new GRect(x - (c / 2.0), y - RADIUS - (c / 2.0), c, c);
        hat1.setFilled(true);
        hat1.setColor(Color.BLACK);
        add(hat1);

        GOval hat2 = new GOval(x - c, y - RADIUS + (c / 2.0), (c * 2.0), (c / 2.0));
        hat2.setColor(Color.BLACK);
        hat2.setFilled(true);
        add(hat2);

        /* 0.8 and 3 - The coefficient of displacement of a caterpillar eye along the x-axis and the y-axis
         * 0.7 - The coefficient of change to the radius of the circle relative to the radius of the segment */
        GOval leftEye = new GOval(x - RADIUS * 0.8, y - RADIUS / 3, RADIUS * 0.7, RADIUS * 0.7);
        leftEye.setColor(Color.BLACK);
        leftEye.setFillColor(Color.WHITE);
        leftEye.setFilled(true);
        add(leftEye);

        /* 0.8 and 3 - The coefficient of displacement of a caterpillar eye along the x-axis and the y-axis
         * 0.5 - The coefficient of change to the radius of the circle relative to the radius of the segment */
        GOval leftEye1 = new GOval(x - RADIUS * 0.8, y - RADIUS / 3, RADIUS * 0.5, RADIUS * 0.5);
        leftEye1.setColor(Color.BLACK);
        leftEye1.setFilled(true);
        add(leftEye1);

        /* 0.1 and 3 - The coefficient of displacement of a caterpillar eye along the x-axis and the y-axis
         * 0.7 - The coefficient of change to the radius of the circle relative to the radius of the segment */
        GOval rightEye = new GOval(x + RADIUS * 0.1, y - RADIUS / 3, RADIUS * 0.7, RADIUS * 0.7);
        rightEye.setColor(Color.BLACK);
        rightEye.setFillColor(Color.WHITE);
        rightEye.setFilled(true);
        add(rightEye);

        /* 0.1 and 3 - The coefficient of displacement of a caterpillar eye along the x-axis and the y-axis
         * 0.5 - The coefficient of change to the radius of the circle relative to the radius of the segment */
        GOval rightEye1 = new GOval(x + RADIUS * 0.1, y - RADIUS / 3, RADIUS * 0.5, RADIUS * 0.5);
        rightEye1.setColor(Color.BLACK);
        rightEye1.setFilled(true);
        add(rightEye1);
    }
}
