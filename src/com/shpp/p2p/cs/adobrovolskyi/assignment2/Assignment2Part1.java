package com.shpp.p2p.cs.adobrovolskyi.assignment2;

import com.shpp.cs.a.console.TextProgram;

/**
 * This program finds the roots of the quadratic equation. Artem Dobrovolskyi @2018
 */
public class Assignment2Part1 extends TextProgram {

    public void run() {
        findRoot();
    }

    /**
     * This program taking from the user 3 numbers and
     * finds the root of the quadratic equation using the discriminant
     */
    private void findRoot() {
        double D;
        double a = readDouble("Please enter a: ");
        double b = readDouble("Please enter b: ");
        double c = readDouble("Please enter c: ");

        /* D - discriminant of a quadratic equation */
        D = (b * b) - (4 * a * c);
        if (D > 0) {
            double x1, x2;
            x1 = (-b + Math.sqrt(D)) / (2.0 * a);
            x2 = (-b - Math.sqrt(D)) / (2.0 * a);
            println("There are two roots: " + x1 + " and " + x2);
        }
        else if (D == 0) {
            double x;
            x = (-b) / (2.0 * a);
            println("There is one root: " + x);
        }
        else {
            println("There are no real roots");
        }
    }
}