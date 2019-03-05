package com.shpp.p2p.cs.adobrovolskyi.assignment12;

import acm.graphics.GImage;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * This program search a silhouette. Artem Dobrovolskyi @2018
 */
public class Assignment12Part1 {

    private static boolean[][] passedPixels;
    private static boolean[][] entryPixels;
    private static int counterOfRecursion = 0;
    private static int silhouettesCounter = 0;
    private static int minNumberOfRecursionToCount = 0;
    private static int backgroundRed = 0;
    private static int backgroundGreen = 0;
    private static int backgroundBlue = 0;
    private static int width;
    private static int height;

    /**
     * The program accepts as input the name of a picture
     * that needs to be processed and calculate the number of silhouettes using a recursive depth search.
     *
     * @param args The name of the resolution image is passed to the arguments.
     * @throws Exception Еo display errors to the user
     */
    public static void main(String[] args) throws Exception {
        String str = args[0];

        BufferedImage test;
        test = ImageIO.read(new File(str));

        width = test.getWidth();
        height = test.getHeight();

        minNumberOfRecursionToCount = (int) (width * height * 0.035);
//        minNumberOfRecursionToCount = (int) (width * height * 0.01);

        int[][] pixels = getPixelsArray(test);

        passedPixels = new boolean[pixels.length][pixels[0].length];
        entryPixels = new boolean[pixels.length][pixels[0].length];

        definitionOfBackgroundColor(pixels);

        foundEntryPoints(pixels);

        silhouetteSearch();

        System.out.println("Found " + ">> " + silhouettesCounter + " <<" + " silhouette.");
    }

    /**
     * A method that takes as input a two-dimensional array of pixels and processing it,
     * initializes two more arrays with points for which you need to calculate silhouettes
     *
     * @param pixels array with pixels
     */
    private static void foundEntryPoints(int[][] pixels) {
        for (int row = 0; row < pixels.length; row++) {
            for (int col = 0; col < pixels[0].length; col++) {
                int red = GImage.getRed(pixels[row][col]);
                int green = GImage.getGreen(pixels[row][col]);
                int blue = GImage.getBlue(pixels[row][col]);
                entryPixels[row][col] = false;
                int backgroundColor = (backgroundRed + backgroundGreen + backgroundBlue) / 3;
                int color = (blue + red + green) / 3;
                if (backgroundColor >= 100) {
                    passedPixels[row][col] = backgroundColor >= color;
                } else {
                    passedPixels[row][col] = backgroundColor <= color;
                }
            }
        }
    }

    /**
     * The method takes a picture as input and processing it creates an array of pixels
     *
     * @param test accepted picture
     * @return array with pixels
     */
    private static int[][] getPixelsArray(BufferedImage test) {
        int[][] pixels = new int[width][height];
        for (int row = 0; row < width; row++) {
            for (int col = 0; col < height; col++) {
                pixels[row][col] = test.getRGB(row, col);
            }
        }
        return pixels;
    }

    /**
     * The method takes an array of pixels as input and calculates the background color
     *
     * @param pixels array with pixels
     */
    private static void definitionOfBackgroundColor(int[][] pixels) {
        for (int row = 0; row < pixels.length; row++) {
            for (int col = 0; col < pixels[0].length; col++) {
                if (col == pixels[0].length - 1) {
                    row = pixels.length - 1;
                }
                backgroundRed += GImage.getRed(pixels[row][col]);
                backgroundGreen += GImage.getGreen(pixels[row][col]);
                backgroundBlue += GImage.getBlue(pixels[row][col]);
            }
        }
        for (int col = 0; col < pixels[0].length; col++) {
            for (int row = 0; row < pixels.length; row++) {
                if (row == pixels.length - 1) {
                    col = pixels[0].length - 1;
                }
                backgroundRed += GImage.getRed(pixels[row][col]);
                backgroundGreen += GImage.getGreen(pixels[row][col]);
                backgroundBlue += GImage.getBlue(pixels[row][col]);
            }
        }
        backgroundRed = backgroundRed / (width * 2 + height * 2);
        backgroundGreen = backgroundGreen / (width * 2 + height * 2);
        backgroundBlue = backgroundBlue / (width * 2 + height * 2);
    }

    /**
     * A method that counts the number of silhouettes using the recursive method
     */
    private static void silhouetteSearch() {
        for (int row = 0; row < passedPixels.length; row++) {
            for (int col = 0; col < passedPixels[0].length; col++) {
                if (passedPixels[row][col] && !entryPixels[row][col]) {
                    recursion(row, col);
                    if (counterOfRecursion > minNumberOfRecursionToCount) {
                        silhouettesCounter++;
                        System.out.println(counterOfRecursion);
                        System.out.println(minNumberOfRecursionToCount);
                        counterOfRecursion = 0;
                    }
                }
            }
        }


    }

    /**
     * A recursive method that checks points inside a silhouette
     * to count the number of points a silhouette consists of
     *
     * @param row x coordinate of the pixel to be checked
     * @param col y coordinate of the pixel to be checked
     */
    private static void recursion(int row, int col) {
        entryPixels[row][col] = true;
        if (col - 1 >= 0 && row < passedPixels.length && col - 1 < passedPixels[0].length && passedPixels[row][col - 1] && !entryPixels[row][col - 1]) {
            recursion(row, col - 1);
        }
        if (row - 1 >= 0 && row - 1 < passedPixels.length && col < passedPixels[0].length && passedPixels[row - 1][col] && !entryPixels[row - 1][col]) {
            recursion(row - 1, col);
        }
        if (row + 1 < passedPixels.length && col < passedPixels[0].length && passedPixels[row + 1][col] && !entryPixels[row + 1][col]) {
            recursion(row + 1, col);
        }
        if (row < passedPixels.length && col + 1 < passedPixels[0].length && passedPixels[row][col + 1] && !entryPixels[row][col + 1]) {
            recursion(row, col + 1);
        }
        counterOfRecursion++;

    }

}