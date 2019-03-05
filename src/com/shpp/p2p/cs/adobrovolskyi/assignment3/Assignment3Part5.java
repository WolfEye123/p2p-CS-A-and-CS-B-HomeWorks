package com.shpp.p2p.cs.adobrovolskyi.assignment3;

import com.shpp.cs.a.console.TextProgram;

/**
 * This program creates the St. Petersburg game. Artem Dobrovolskyi @2018
 */
public class Assignment3Part5 extends TextProgram {

    public void run() {
        game();
    }

    /**
     * This method creates the St. Petersburg game
     */
    private void game() {
        int number = 1;
        int gamesCounter = 0;
        int cash = 0;

        while (cash < 20){
            int luck = (int) (Math.random()*2);
            if (luck == 0){
                cash += number;
                println("This game, you earned $" + number);
                println("Your total is $" + cash);
                gamesCounter++;
                number = 1;
            } else {
                number += number;
            }
        }
        println("It took " + gamesCounter + " games to earn $20");
    }
}
