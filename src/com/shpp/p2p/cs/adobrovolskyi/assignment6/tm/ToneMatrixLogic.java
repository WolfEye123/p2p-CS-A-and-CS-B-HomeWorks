package com.shpp.p2p.cs.adobrovolskyi.assignment6.tm;

/**
 * . Artem Dobrovolskyi @2018
 */
public class ToneMatrixLogic {
    /**
     * Given the contents of the tone matrix, returns a string of notes that should be played
     * to represent that matrix.
     *
     * @param toneMatrix The contents of the tone matrix.
     * @param column     The column number that is currently being played.
     * @param samples    The sound samples associated with each row.
     * @return A sound sample corresponding to all notes currently being played.
     */
    public static double[] matrixToMusic(boolean[][] toneMatrix, int column, double[][] samples) {
        double[] result = new double[ToneMatrixConstants.sampleSize()];
        int counter = 0;

        /* find the included light bulb and add sound to it. if there are several light bulbs,
        add up all the waves related to the included lights on the current column */
        for (int row = 0; row < toneMatrix.length; row++) {
            if (toneMatrix[row][column]) {
                for (int col = 0; col < samples[0].length; col++) {
                    result[col] = result[col] + samples[row][col];
                    counter++;
                }
            }
        }
        /* if there are no lights turned on, return an array of zeros */
        if (counter == 0){
            return result;
        }

        double max = 0.0;
        double min = 0.0;

        /* finds the wave with the greatest intensity in the resulting sound */
        for (int i = 0; i < result.length; i++) {
            if (result[i] > max) {
                max = result[i];
            } else if (result[i] < min){
                min = result[i];
            }
        }

        /* "normalize" the created wave by dividing it into an element with maximum intensity */
        for (int i = 0; i < result.length; i++) {
            if (Math.abs(min) < max){
                result[i] = result[i] / max;
            } else if (Math.abs(min) > max){
                result[i] = result[i] / min;
            }
        }
        return result;
    }
}
