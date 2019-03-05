package com.shpp.p2p.cs.adobrovolskyi.assignment6.hg;

/**
 * . Artem Dobrovolskyi @2018
 */
public class HistogramEqualizationLogic {
    private static final int MAX_LUMINANCE = 255;

    /**
     * Given the luminances of the pixels in an image, returns a histogram of the frequencies of
     * those luminances.
     * <p/>
     * You can assume that pixel luminances range from 0 to MAX_LUMINANCE, inclusive.
     *
     * @param luminances The luminances in the picture.
     * @return A histogram of those luminances.
     */
    public static int[] histogramFor(int[][] luminances) {
        /* creates an array for the histogram */
        int[] histogram = new int[256];
        int i;

        /* creating a histogram by counting the brightness of pixels */
        for (int row = 0; row < luminances.length; row++) {
            for (int col = 0; col < luminances[row].length; col++) {
                i = luminances[row][col];
                histogram[i]++;
            }
        }
        return histogram;
    }

    /**
     * Given a histogram of the luminances in an image, returns an array of the cumulative
     * frequencies of that image.  Each entry of this array should be equal to the sum of all
     * the array entries up to and including its index in the input histogram array.
     * <p/>
     * For example, given the array [1, 2, 3, 4, 5], the result should be [1, 3, 6, 10, 15].
     *
     * @param histogram The input histogram.
     * @return The cumulative frequency array.
     */
    public static int[] cumulativeSumFor(int[] histogram) {
        int[] cumulativeSumFor = new int[histogram.length];

        /* creating a cumulative histogram */
        cumulativeSumFor[0] = histogram[0];
        for (int i = 1; i < histogram.length; i++) {
            cumulativeSumFor[i] = histogram[i] + cumulativeSumFor[i-1];
        }
        return cumulativeSumFor;
    }

    /**
     * Returns the total number of pixels in the given image.
     *
     * @param luminances A matrix of the luminances within an image.
     * @return The total number of pixels in that image.
     */
    public static int totalPixelsIn(int[][] luminances) {
        return luminances.length * luminances[0].length;
    }

    /**
     * Applies the histogram equalization algorithm to the given image, represented by a matrix
     * of its luminances.
     * <p/>
     * You are strongly encouraged to use the three methods you have implemented above in order to
     * implement this method.
     *
     * @param luminances The luminances of the input image.
     * @return The luminances of the image formed by applying histogram equalization.
     */
    public static int[][] equalize(int[][] luminances) {
        /* getting return values from previously described methods */
        int[] cumulativeFor = cumulativeSumFor(histogramFor(luminances));

        int [][] equalize = new int[luminances.length][luminances[0].length];

        /* alignment of the cumulative histogram to raise the overall image contrast */
        for (int row = 0; row < luminances.length; row++){
            for (int col =0; col < luminances[row].length; col++){
                equalize[row][col] = MAX_LUMINANCE * cumulativeFor[luminances[row][col]] / (luminances.length * luminances[0].length);
            }
        }
        return equalize;
    }
}
