package com.shpp.p2p.cs.adobrovolskyi.assignment6.sg;

import acm.graphics.*;
import com.shpp.cs.a.console.TextProgram;

/**
 * . Artem Dobrovolskyi @2018
 */
public class SteganographyLogic extends TextProgram implements SteganographyConstants {
    /**
     * Given a GImage containing a hidden message, finds the hidden message
     * contained within it and returns a boolean array containing that message.
     * <p/>
     * A message has been hidden in the input image as follows.  For each pixel
     * in the image, if that pixel has a red component that is an even number,
     * the message value at that pixel is false.  If the red component is an odd
     * number, the message value at that pixel is true.
     *
     * @param source The image containing the hidden message.
     * @return The hidden message, expressed as a boolean array.
     */
    public static boolean[][] findMessage(GImage source) {

        /* read the image pixel by pixel and put the data in a two-dimensional array */
        int[][] pixels = source.getPixelArray();

        /* boolean array to identify the encrypted message */
        boolean[][] find = new boolean[pixels.length][pixels[0].length];

        /* passes through the elements of a two-dimensional array */
        for (int row = 0; row < pixels.length; row++) {
            for (int col = 0; col < pixels[row].length; col++) {

                /* gets the value of the red pixel component */
                int red = GImage.getRed(pixels[row][col]);

                /* assigning true or false values to array elements to detect an encrypted message */
                if (red % 2 == 0) {
                    find[row][col] = false;
                } else {
                    find[row][col] = true;
                }
            }
        }

        return find;
    }

    /**
     * Hides the given message inside the specified image.
     * <p/>
     * The image will be given to you as a GImage of some size, and the message will
     * be specified as a boolean array of pixels, where each white pixel is denoted
     * false and each black pixel is denoted true.
     * <p/>
     * The message should be hidden in the image by adjusting the red channel of all
     * the pixels in the original image.  For each pixel in the original image, you
     * should make the red channel an even number if the message color is white at
     * that position, and odd otherwise.
     * <p/>
     * You can assume that the dimensions of the message and the image are the same.
     * <p/>
     *
     * @param message The message to hide.
     * @param source  The source image.
     * @return A GImage whose pixels have the message hidden within it.
     */
    public static GImage hideMessage(boolean[][] message, GImage source) {

        /* read the image pixel by pixel and put the data in a two-dimensional array */
        int[][] pixels = source.getPixelArray();

        /* passes through the elements of a two-dimensional array */
        for (int row = 0; row < pixels.length; row++){
            for (int col = 0; col < pixels[row].length; col++) {

                /* gets the value of the pixel component */
                int red = GImage.getRed(pixels[row][col]);
                int green = GImage.getGreen(pixels[row][col]);
                int blue = GImage.getBlue(pixels[row][col]);

                /* change the value of the red component of the pixel depending
                   on the resulting value from the boolean array */
                if (message[row][col]) {
                    if(red % 2 == 0 && red != 255){
                        red++;
                    }
                }else {
                    if(red % 2 !=0 && red != 0) {
                        red--;
                    }
                }

                /* make changes to the two-dimensional array obtained from the image */
                pixels[row][col] = GImage.createRGBPixel(red,green,blue);
            }
        }

        /* creating a new picture with an encrypted message */
        GImage newImage = new GImage(pixels);
        return newImage;
    }
}
