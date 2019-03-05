package com.shpp.p2p.cs.adobrovolskyi.assignment5;

import com.shpp.cs.a.console.TextProgram;

/**
 * . Artem Dobrovolskyi @2018
 */
public class SyllableCounting extends TextProgram {
    public void run() {
        /* Repeatedly prompt the user for a word and print out the estimated
         * number of syllables in that word.
         */
        try{
            while (true) {
                String word = readLine("Enter a single word: ");
                int close = syllablesIn(word);
                if (close == 0){
                    println("Good bye");
                    break;
                }
                println("  Syllable count: " + syllablesIn(word));
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Given a word, estimates the number of syllables in that word according to the
     * heuristic specified in the handout.
     *
     * @param word A string containing a single word.
     * @return An estimate of the number of syllables in that word.
     */
    private int syllablesIn(String word) {
        if (word.charAt(0) == '0' ){
            return 0;
        }
        /* vowels in english */
        char[] mass = {'a','e','y','u','i','o'};
        /* counter to count the number of syllables in a word */
        int index = 0;
        /* search for vowels */
        index = checkLetters(word, mass, index);
        /* last letter check */
        index = checkLastLetter(word, mass, index);
        /* for words that have no syllables like: me, we... */
        if (index == 0)
            index = 1;

        return index;
    }

    /**
     * Check the last letter in the word and if it "e", then do not count it as a syllable
     * @param word User word
     * @param mass Array with vowels
     * @param index Syllables counter
     * @return Return number of syllables
     */
    private int checkLastLetter(String word, char[] mass, int index) {
        boolean found;
        if (mass[1] == word.charAt(word.length()-1)){
            found = true;
            for (char mas : mass) {
                if (mas == word.charAt(word.length()-2)){
                    found = false;
                    break;
                }
            }
            if (found)
                index--;
        }
        return index;
    }

    /**
     * Looking for vowels for counting syllables in a word
     * @param word User word.
     * @param mass Array with vowels
     * @param index Syllables counter
     * @return Return number of syllables
     */
    private int checkLetters(String word, char[] mass, int index) {
        boolean found;
        for(int i = 0; i < word.length(); i++){
            for (char mas : mass) {
                found = true;
                if (mas == word.charAt(i)) {
                    if (i > 0) {
                        /* check on the letters in front */
                        for (char mas1 : mass) {
                            if (mas1 == word.charAt(i - 1)) {
                                found = false;
                                break;
                            }
                        }
                    }
                    if (found) {
                        index++;
                    }
                }

            }
        }
        return index;
    }
}
