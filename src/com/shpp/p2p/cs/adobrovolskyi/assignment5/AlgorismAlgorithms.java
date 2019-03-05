package com.shpp.p2p.cs.adobrovolskyi.assignment5;

import com.shpp.cs.a.console.TextProgram;

/**
 * . Artem Dobrovolskyi @2018
 */
public class AlgorismAlgorithms extends TextProgram {

    /* range of numbers from character to number */
    private static final int NUMBER_RANGE = 10;
    /* number transfer factor */
    private static final int TRANSFER_FACTOR = 10;

    public void run() {
        /* Sit in a loop, reading numbers and adding them. */
        while (true) {
            String n1 = readLine("Enter first number:  ");
            if (n1.equals("0")) {
                break;
            }
            String n2 = readLine("Enter second number: ");
            println(n1 + " + " + n2 + " = " + addNumericStrings(n1, n2));
            println();
        }
    }

    /**
     * Given two string representations of non-negative integers, adds the
     * numbers represented by those strings and returns the result.
     *
     * @param n1 The first number.
     * @param n2 The second number.
     * @return A String representation of n1 + n2
     */
    private StringBuffer addNumericStrings(String n1, String n2) {
        int sizeStr1 = n1.length();
        int sizeStr2 = n2.length();
        int portableNumber = 0;
        int firstAdditionPartCounter;
        int secondAdditionPartCounter;


        StringBuffer n3 = new StringBuffer();

        /* string length comparison */
        if (sizeStr1 > sizeStr2) {
            firstAdditionPartCounter = sizeStr2;
            secondAdditionPartCounter = sizeStr1 - sizeStr2;
        } else {
            firstAdditionPartCounter = sizeStr1;
            secondAdditionPartCounter = sizeStr2 - sizeStr1;
        }

        /* catches the returned portable number */
        portableNumber = additionFromTheStringEnd(n1, n2, n3, sizeStr1, sizeStr2, portableNumber, firstAdditionPartCounter);

        /* addition of the remaining numbers depending on which string is longer */
        if (sizeStr1 > sizeStr2) {
            additionFromLargestLine(n1, n3, portableNumber, secondAdditionPartCounter);
        } else {
            additionFromLargestLine(n2, n3, portableNumber, secondAdditionPartCounter);
        }

        return n3;
    }

    /**
     * Add numbers from strings entered by the user, starting at the end of the strings.
     * Addition is made by adding the numbers in the column.
     * The cycle length is the length of the smaller string.
     * @param n1 First user-entered string
     * @param n2 Second user-entered string
     * @param n3 String with answer
     * @param sizeStr1 First string length
     * @param sizeStr2 Second string length
     * @param portableNumber The portable number
     * @param firstAdditionPartCounter Loop counter which is equal to the length of the smallest string
     * @return The portable number
     */
    private int additionFromTheStringEnd(String n1, String n2, StringBuffer n3, int sizeStr1, int sizeStr2, int portableNumber, int firstAdditionPartCounter) {
        int result1;

        /* addition numbers from the strings */
        for (int i = firstAdditionPartCounter; i > 0; i--) {
            int i1 = Character.digit(n1.charAt(sizeStr1 - 1), NUMBER_RANGE);
            int i2 = Character.digit(n2.charAt(sizeStr2 - 1), NUMBER_RANGE);

            if (portableNumber != 0) {
                result1 = i1 + i2 + portableNumber;
                portableNumber = 0;
            } else {
                result1 = i1 + i2;
            }

            if (result1 >= TRANSFER_FACTOR) {
                result1 -= TRANSFER_FACTOR;
                portableNumber = 1;
            }

            /* adding numbers to the new string */
            n3.insert(0, result1);

            sizeStr1--;
            sizeStr2--;
        }


        return portableNumber;
    }

    /**
     * Adds long string numbers that were not added,
     * to the already added part of the response strings to get a full answer
     * @param n1 First user-entered string
     * @param n3 String with answer
     * @param portableNumber The portable number
     * @param secondAdditionPartCounter Loop counter that is equal to the residual length of a long string.
     */
    private void additionFromLargestLine(String n1, StringBuffer n3, int portableNumber, int secondAdditionPartCounter) {
        for (int i = secondAdditionPartCounter; i > 0; i--) {
            if (portableNumber == 0) {
                n3.insert(0, n1.charAt(secondAdditionPartCounter - 1));
                secondAdditionPartCounter--;
            } else {
                int result3 = Character.digit(n1.charAt(secondAdditionPartCounter - 1), NUMBER_RANGE) + portableNumber;

                if (result3 >= TRANSFER_FACTOR) {
                    result3 -= TRANSFER_FACTOR;
                    portableNumber = 1;
                } else {
                    portableNumber = 0;
                }

                n3.insert(0, result3);
                secondAdditionPartCounter--;
            }

            /* adding portable number to the front of the string with answer */
            if (portableNumber != 0 && i == 1) {
                n3.insert(0, portableNumber);
            }
        }
    }
}
