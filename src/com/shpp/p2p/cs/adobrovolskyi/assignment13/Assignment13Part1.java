package com.shpp.p2p.cs.adobrovolskyi.assignment13;

import acm.graphics.GImage;
import com.shpp.p2p.cs.adobrovolskyi.assignment16.MyQueue;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayDeque;

/**
 * This program search a silhouette. Artem Dobrovolskyi @2018
 */
public class Assignment13Part1 {

    /*  */
    private static boolean WIDE_SILHOUETTE_SEARCH = true;
    /* mean value to determine the background */
    private static int BACKGROUND_VALUE = 100;
    /* array of silhouette pixels passed when checking */
    private static boolean[][] passedPixels;
    /* silhouette pixel array */
    private static boolean[][] entryPixels;
    /* silhouette pixel pass counter */
    private static int PIXELS_COUNTER = 0;
    /* found silhouettes counter */
    private static int SILHOUETTE_COUNTER = 0;
    /* maximum pixel color value */
    private static final int MAX_RATE = 255;
    /* Minimum number of pixels of silhouette to count */
    private static int MINIMUM_NUMBER_OF_PIXELS_OF_SILHOUETTE_TO_COUNT = 0;
    /* red pigment pixel background */
    private static int BACKGROUND_RED = 0;
    /* green pigment pixel background */
    private static int BACKGROUND_GREEN = 0;
    /* blue pigment pixel background */
    private static int BACKGROUND_BLUE = 0;
    /* image width*/
    private static int IMAGE_WIDTH;
    /* image height */
    private static int IMAGE_HEIGHT;

    /**
     * The program accepts as input the name of a picture
     * that needs to be processed and calculate the number of silhouettes using a recursive depth search.
     * <p>
     * To work correctly, you must enter in the parameters of the program -Xss256m
     *
     * @param args The name of the resolution image is passed to the arguments.
     */
    public static void main(String[] args) {
        /* if the user has not entered arguments, the standard will be written below */
        String str;
        if (args.length == 0) {
            str = "test2.png";
        } else {
            str = args[0];
        }

        /* read the picture */
        BufferedImage testingImage = null;
        try {
            testingImage = ImageIO.read(new File(str));
        } catch (IOException e) {
            System.out.println(e);
        }

        assert testingImage != null;
        IMAGE_WIDTH = testingImage.getWidth();
        IMAGE_HEIGHT = testingImage.getHeight();

        /* for different size of pictures and silhouettes you need to use a different coefficient */
//        MINIMUM_NUMBER_OF_PIXELS_OF_SILHOUETTE_TO_COUNT = (int) (IMAGE_WIDTH * IMAGE_HEIGHT * 0.035);
        MINIMUM_NUMBER_OF_PIXELS_OF_SILHOUETTE_TO_COUNT = (int) (IMAGE_WIDTH * IMAGE_HEIGHT * 0.03);
//        MINIMUM_NUMBER_OF_PIXELS_OF_SILHOUETTE_TO_COUNT = (int) (IMAGE_WIDTH * IMAGE_HEIGHT * 0.001);


        int[][] pixels = getPixelsArray(testingImage);
        passedPixels = new boolean[pixels.length][pixels[0].length];
        entryPixels = new boolean[pixels.length][pixels[0].length];


        /* translate the image in black and white */
        GImage grayScaleImage = grayScaleConversion(testingImage);

        pixels = grayScaleImage.getPixelArray();

        definitionOfBackgroundColor(pixels);

        foundEntryPoints(pixels);

        silhouetteSearch();

        System.out.println("Found " + ">> " + SILHOUETTE_COUNTER + " <<" + " silhouette.");
    }

    /**
     * Method makes image in gray scale
     *
     * @param testingImage - Pixel matrix of the input color RGB image
     * @return - Image output pixel matrix in gray scale
     */
    private static GImage grayScaleConversion(BufferedImage testingImage) {
        int[][] pixels = getPixelsArray(testingImage);
        for (int row = 0; row < pixels.length; row++) {
            for (int col = 0; col < pixels[0].length; col++) {
                Color c = new Color(pixels[row][col]);
                int red = (int) (c.getRed() * 0.299);
                int green = (int) (c.getGreen() * 0.587);
                int blue = (int) (c.getBlue() * 0.114);
                int component = red + green + blue;
                pixels[row][col] = GImage.createRGBPixel(component, component, component, MAX_RATE);
            }
        }

        return new GImage(pixels);
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
                int backgroundColor = (BACKGROUND_RED + BACKGROUND_GREEN + BACKGROUND_BLUE) / 3;
                int color = (blue + red + green) / 3;
                if (backgroundColor > BACKGROUND_VALUE) {
                    if (backgroundColor > color) {
                        passedPixels[row][col] = true;
                    } else {
                        passedPixels[row][col] = false;
                    }
                } else {
                    if (backgroundColor < color) {
                        passedPixels[row][col] = true;
                    } else {
                        passedPixels[row][col] = false;
                    }
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
        int[][] pixels = new int[IMAGE_WIDTH][IMAGE_HEIGHT];
        for (int row = 0; row < IMAGE_WIDTH; row++) {
            for (int col = 0; col < IMAGE_HEIGHT; col++) {
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
                BACKGROUND_RED += GImage.getRed(pixels[row][col]);
                BACKGROUND_GREEN += GImage.getGreen(pixels[row][col]);
                BACKGROUND_BLUE += GImage.getBlue(pixels[row][col]);
            }
        }
        for (int col = 0; col < pixels[0].length; col++) {
            for (int row = 0; row < pixels.length; row++) {
                if (row == pixels.length - 1) {
                    col = pixels[0].length - 1;
                }
                BACKGROUND_RED += GImage.getRed(pixels[row][col]);
                BACKGROUND_GREEN += GImage.getGreen(pixels[row][col]);
                BACKGROUND_BLUE += GImage.getBlue(pixels[row][col]);
            }
        }
        BACKGROUND_RED = BACKGROUND_RED / (IMAGE_WIDTH * 2 + IMAGE_HEIGHT * 2) - 4;
        BACKGROUND_GREEN = BACKGROUND_GREEN / (IMAGE_WIDTH * 2 + IMAGE_HEIGHT * 2) - 4;
        BACKGROUND_BLUE = BACKGROUND_BLUE / (IMAGE_WIDTH * 2 + IMAGE_HEIGHT * 2) - 4;
    }

    /**
     * A method that counts the number of silhouettes using the recursive method
     */
    private static void silhouetteSearch() {
        for (int row = 0; row < passedPixels.length; row++) {
            for (int col = 0; col < passedPixels[0].length; col++) {
                if (passedPixels[row][col] && !entryPixels[row][col]) {
                    if (WIDE_SILHOUETTE_SEARCH) {
                        wideSilhouetteSearch(row, col);
                    } else {
                        recursion(row, col);
                    }

                    if (PIXELS_COUNTER > MINIMUM_NUMBER_OF_PIXELS_OF_SILHOUETTE_TO_COUNT) {
                        SILHOUETTE_COUNTER++;
//                        System.out.println(PIXELS_COUNTER);
//                        System.out.println(MINIMUM_NUMBER_OF_PIXELS_OF_SILHOUETTE_TO_COUNT);
                        PIXELS_COUNTER = 0;
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
        PIXELS_COUNTER++;

    }

    /**
     * This is a method that, using a wide search, counts the number of points a silhouette consists of.
     *
     * @param entryRow x coordinate of entry point
     * @param entryCol y coordinate of entry point
     */
    private static void wideSilhouetteSearch(int entryRow, int entryCol) {
        int row, col;
        entryPixels[entryRow][entryCol] = true;
        MyQueue<String> deque = new MyQueue<>();
        String pixels;
        String[] pixel;
        pixels = entryRow + " " + entryCol;
        deque.offer(pixels);
        while (deque.size() != 0) {
            pixels = deque.pop();
            pixel = pixels.split(" ");
            row = Integer.parseInt(pixel[0]);
            col = Integer.parseInt(pixel[1]);
            if (col - 1 >= 0 && row < passedPixels.length && col - 1 < passedPixels[0].length && passedPixels[row][col - 1] && !entryPixels[row][col - 1]) {
                entryPixels[row][col - 1] = true;
                pixels = row + " " + (col - 1);
                deque.offer(pixels);
                PIXELS_COUNTER++;
            }
            if (row - 1 >= 0 && row - 1 < passedPixels.length && col < passedPixels[0].length && passedPixels[row - 1][col] && !entryPixels[row - 1][col]) {
                entryPixels[row - 1][col] = true;
                pixels = (row - 1) + " " + col;
                deque.offer(pixels);
                PIXELS_COUNTER++;
            }
            if (row + 1 < passedPixels.length && col < passedPixels[0].length && passedPixels[row + 1][col] && !entryPixels[row + 1][col]) {
                entryPixels[row + 1][col] = true;
                pixels = (row + 1) + " " + col;
                deque.offer(pixels);
                PIXELS_COUNTER++;
            }
            if (row < passedPixels.length && col + 1 < passedPixels[0].length && passedPixels[row][col + 1] && !entryPixels[row][col + 1]) {
                entryPixels[row][col + 1] = true;
                pixels = row + " " + (col + 1);
                deque.offer(pixels);
                PIXELS_COUNTER++;
            }
        }
    }
}