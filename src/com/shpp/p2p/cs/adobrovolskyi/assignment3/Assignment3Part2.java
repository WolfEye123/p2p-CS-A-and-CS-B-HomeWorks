package com.shpp.p2p.cs.adobrovolskyi.assignment3;

import com.shpp.cs.a.console.TextProgram;

/**
 * This program calculates hailstones numbers. Artem Dobrovolskyi @2018
 */
public class Assignment3Part2 extends TextProgram {

    public void run() {
        hailstonesNumbers();
    }

    /**
     * This method calculates hailstones numbers
     */
    private void hailstonesNumbers() {
        int n = readInt("Enter a number: ");
        while (n != 1){
            if (n % 2 == 0){
                println(n + " is even, so I take half: " + (n / 2));
                n /= 2;
                if (n == 1){
                    break;
                }
            } else {
                println(n + " is odd, so I make 3n + 1: " + (n * 3 + 1));
                n = n * 3 + 1;
            }
        }
        println("The end.");
    }
}