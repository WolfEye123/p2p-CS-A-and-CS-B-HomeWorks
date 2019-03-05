package com.shpp.p2p.cs.adobrovolskyi.assignment5;

import com.shpp.cs.a.console.TextProgram;

import java.io.BufferedReader;
import java.io.FileReader;

/**
 * . Artem Dobrovolskyi @2018
 */
public class GameOnTheRoad extends TextProgram {
    public void run() {
        while (true) {
            String str = readLine("Enter three letters(0 - to exit):  ");

            /* if the user enters '0' then close the program */
            if (str.charAt(0) == '0') {
                println("Good bye");
                break;
            }
            char firstLetter = Character.toLowerCase(str.charAt(0));
            char secondLetter = Character.toLowerCase(str.charAt(1));
            char thirdLetter = Character.toLowerCase(str.charAt(2));

            findingWords(firstLetter, secondLetter, thirdLetter);
        }
    }

    /**
     * The method determines whether the words in the file contain a user-defined sequence of letters
     * and if there is, then returns all found words
     *
     * @param firstLetter  First letter entered by user
     * @param secondLetter Second letter entered by user
     * @param thirdLetter  Third letter entered by user
     */
    private void findingWords(char firstLetter, char secondLetter, char thirdLetter) {
        String str;
        String foundWord = null;
        try {
            BufferedReader words = new BufferedReader(new FileReader("E:\\Projects\\src\\com\\shpp\\p2p\\cs\\adobrovolskyi\\assignment5\\en-dictionary.txt"));
            while (true) {
                str = words.readLine();
                boolean found = false;

                /* read completion when end of file is found */
                if (str == null) {
                    words.close();
                    break;
                }

                /* searches for a given sequence of letters in words */
                for (int i = 0; i < str.length(); i++) {
                    if (firstLetter == str.charAt(i) ) {
                        for (int j = i + 1; j < str.length(); j++) {
                            if (secondLetter == str.charAt(j)) {
                                for (int k = j + 1; k < str.length(); k++) {
                                    if (thirdLetter == str.charAt(k)) {
                                        found = true;
                                        foundWord = str;
                                        break;
                                    }
                                }
                            }
                            if (found) {
                                break;
                            }
                        }
                    }
                    if (found) {
                        break;
                    }
                }
                if (found) {
                    println(foundWord);
                }
            }

        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }
}
