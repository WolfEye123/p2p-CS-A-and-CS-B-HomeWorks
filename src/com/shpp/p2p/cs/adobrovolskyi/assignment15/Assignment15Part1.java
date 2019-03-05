package com.shpp.p2p.cs.adobrovolskyi.assignment15;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This program is engaged in archiving and unzipping files. Artem Dobrovolskyi @2019
 */
public class Assignment15Part1 {

    /* pattern for screening out invalid file extensions */
    private static final Pattern FILE_EXTENSIONS_PATTERN = Pattern.compile(".+\\.(txt|png|jpg|bmp|exe|mp3|mp4|avi|mkv|rar|zip|par|java|class|jar|pdf)");
    /* pattern for screening out invalid file operations */
    private static final Pattern FILE_OPERATIONS_PATTERN = Pattern.compile(".+\\.(par|uar)");
    /* pattern to separate file name from extension */
    private static final String pattern = "\\.";

    /**
     * The program archives or unzips the file based on the data transferred by the user.
     * For the program to work correctly, you can enter the following arguments:
     * *
     * *
     * *
     *     test.txt                - enter for archiving to test.par
     *     test.txt test.par       - enter for archiving to test.par
     *     test.txt test.uar       - enter for unarchiving from test.par to test(1).txt
     *     -a test.txt test.par    - enter for archiving to test.par
     *     -u test.txt test.par    - enter for unarchiving from test.par to test(1).txt
     * *
     * *
     * *
     * if you leave the argument string empty, the program will do the work with the default arguments:
     *     -a test.txt test.par
     * @param args The arguments that the user passes to the program to understand what task it needs to perform
     */
    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();

        Assignment15Part2 Assignment15Part2 = new Assignment15Part2();
        Assignment15Part3 Assignment15Part3 = new Assignment15Part3();

        int exception = 0;
        try {
            args = incomingDataProcessing(args);
        } catch (Exception e) {
            exception = 1;
        }
        if (exception != 0) {
            System.out.println('\n' + "The program is interrupted");
            args[0] = "0";
        }

        if (args[0].equals("-a")) {
            exception = Assignment15Part2.archive(args[1], args[2]);
            if (exception == 0) {
                System.out.println('\n' + "Archiving if finished.");
            } else {
                System.out.println('\n' + "Archiving failed.");
            }
        } else if (args[0].equals("-u")) {
            exception = Assignment15Part3.unarchive(args[1], args[2]);
            if (exception == 0) {
                System.out.println('\n' + "Unarchiving if finished.");
            } else {
                System.out.println('\n' + "Unarchiving failed.");
            }
        }

        long finishTime = System.currentTimeMillis();
        System.out.println('\n' + "Working time : " + (finishTime - startTime) + "ms.");
    }

    /**
     * The method processes the data entered by the user and returns them in the correct form
     * so that the program can correctly accomplish the task
     * @param args The arguments that the user passes to the program to understand what task it needs to perform
     * @return Returns the processed Arguments to the program in order to understand what task it needs to perform.
     * @throws Exception Returns an error if user input is incorrect
     */
    private static String[] incomingDataProcessing(String[] args) throws Exception {
        String[] argsNew = new String[3];
        if (args.length == 0) {                                         // if line if empty
            argsNew[0] = "-a";
            argsNew[1] = "test.txt";
            argsNew[2] = "test.par";
        } else if (args.length == 1) {                                  // if the user entered one argument like "test.txt"
            String[] str = args[0].split(pattern);
            if (match(args[0], FILE_EXTENSIONS_PATTERN)) {
                argsNew[0] = "-a";
                argsNew[1] = args[0];
                argsNew[2] = str[0] + ".par";
            } else {
                throw new Exception("Incorrect data entry. Try again.");
            }
        } else if (args.length == 2) {                                  // if the user entered two arguments like "test.txt test.par"
            String[] str = args[0].split(pattern);
            String[] str1 = args[1].split(pattern);
            switch (str1[1]) {
                case "par":
                    if (match(args[0], FILE_EXTENSIONS_PATTERN)) {
                        argsNew[0] = "-a";
                        argsNew[1] = str[0] + "." + str[1];
                        argsNew[2] = str1[0] + ".par";
                    }
                    break;
                case "uar":
                    if (match(args[0], FILE_EXTENSIONS_PATTERN)) {
                        argsNew[0] = "-u";
                        argsNew[1] = str[0] + "(1)" + "." + str[1];
                        argsNew[2] = str1[0] + ".par";
                    }
                    break;
                default:
                    throw new Exception("Incorrect data entry. Try again.");
            }
        } else if (args.length == 3) {                                  // if the user entered three arguments like "-a test.txt test.par"
            switch (args[0]) {
                case "-a":
                    if (match(args[1], FILE_EXTENSIONS_PATTERN)) {
                        if (match(args[2], FILE_OPERATIONS_PATTERN)) {
                            argsNew[0] = args[0];
                            argsNew[1] = args[1];
                            argsNew[2] = args[2];
                        } else {
                            throw new Exception("Incorrect data entry. Try again.");
                        }
                    } else {
                        throw new Exception("Incorrect data entry. Try again.");
                    }
                    break;
                case "-u":
                    if (match(args[1], FILE_EXTENSIONS_PATTERN)) {
                        if (match(args[2], FILE_OPERATIONS_PATTERN)) {
                            String[] str = args[1].split(pattern);
                            argsNew[0] = args[0];
                            argsNew[1] = str[0] + "(1)" + "." + str[1];
                            argsNew[2] = args[2];
                        } else {
                            throw new Exception("Incorrect data entry. Try again.");
                        }
                    } else {
                        throw new Exception("Incorrect data entry. Try again.");
                    }
                    break;
                default:
                    throw new Exception("Incorrect data entry. Try again.");
            }
        } else {
            throw new Exception("Incorrect data entry. Try again.");
        }

        return argsNew;
    }

    /**
     * Method that accepts a string and the pattern with which it should be compared
     * @param line string to compare
     * @param a pattern to compare
     * @return comparison result
     */
    private static boolean match(String line, Pattern a) {
        Matcher m = a.matcher(line);
        return m.matches();
    }
}
