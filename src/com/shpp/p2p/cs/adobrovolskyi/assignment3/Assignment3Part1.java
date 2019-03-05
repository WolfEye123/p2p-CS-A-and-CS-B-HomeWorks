package com.shpp.p2p.cs.adobrovolskyi.assignment3;

import com.shpp.cs.a.console.TextProgram;

/**
 * This program calculates the user's performance of cardio loads. Artem Dobrovolskyi @2018
 */
public class Assignment3Part1 extends TextProgram {

    public void run() {
        checkHealth();
    }

    /**
     * This method calculates the user's performance
     */
    private void checkHealth() {
        int day_numbers = 7;
        int cardiovacular = 0;
        int blood_pressure = 0;

        for (int i = 0; i < day_numbers; i++){
            int minutes = readInt("How many minutes did you do on day " + (i+1) + "?");
            if (minutes >= 30){
                cardiovacular++;
            }
            if (minutes >= 40){
                blood_pressure++;
            }
        }

        println("Cardiovacular health:");
        if (cardiovacular >= 5){
            println("   Great job! You've done enough exercise for cardiovacular health.");
        } else {
            println("   You needed to train hard for at least " + (5 - cardiovacular) + " more day(s) a week!");
        }


        println("Blood pressure:");
        if (blood_pressure >= 3){
            println("   Great job! You've done enough exercise to keep a low blood pressure.");
        } else {
            println("You needed to train hard for at least " + (3 - blood_pressure) + " more day(s) a week!");
        }
    }
}
