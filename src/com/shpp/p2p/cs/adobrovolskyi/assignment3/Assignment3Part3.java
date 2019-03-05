package com.shpp.p2p.cs.adobrovolskyi.assignment3;

import com.shpp.cs.a.console.TextProgram;

/**
 * This program raises numbers. Artem Dobrovolskyi @2018
 */
public class Assignment3Part3 extends TextProgram {

    public void run() {
        println(raiseToPower(0.5, -2));
    }

    /**
     * This method raises numbers
     *
     * @param base The number to be raised
     * @param exponent The degree number
     */
    private double raiseToPower(double base, double exponent) {
        double result = 1;
        if (exponent == 0){
            return 1;
        } else if (exponent > 0){
            for (int i = 1; i <= exponent; i++){
                result = result * base;
            }
        } else {
            for (int i = 1; i <= ((-1) * exponent); i++){
                result = result * base;
            }
            result = 1 / result;
            return result;
        }
        return  result;
    }
}