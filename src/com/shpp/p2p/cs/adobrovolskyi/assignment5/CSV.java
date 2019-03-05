package com.shpp.p2p.cs.adobrovolskyi.assignment5;

import com.shpp.cs.a.console.TextProgram;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

/**
 * . Artem Dobrovolskyi @2018
 */
public class CSV extends TextProgram {
    public void run() {
        try {
            while (true) {
                String fileName = "E:\\Projects\\src\\com\\shpp\\p2p\\cs\\adobrovolskyi\\assignment5\\food-origins.csv";
                int columnIndex = readInt("Enter column number(999 - to exit): ");
                if (columnIndex == 999) {
                    break;
                }
                println(extractColumn(fileName, columnIndex));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Reading the file, it breaks the information up to a comma and places it in the ArrayList
     * for output by the user request as a column with data
     *
     * @param filename    Path to the file being processed
     * @param columnIndex The column index that indicates which ArrayList should be called
     * @return Return ArrayList with columnIndex
     */
    private ArrayList<String> extractColumn(String filename, int columnIndex) {
        ArrayList<String> lists = new ArrayList<String>();
        String s;

        try {
            BufferedReader words = new BufferedReader(new FileReader(filename));
            while (true) {
                s = words.readLine();
                if (s == null) {
                    break;
                }
                ArrayList<String> list = fieldsIn(s);
                //lists.add(list);
                lists.add(list.get(columnIndex));
            }
            words.close();
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
        return lists;
    }

    /**
     * Get a string from a file, processes it, and places each element in the
     * ArrayList cells for output to the user
     * @param line Read string from file
     * @return ArrayList with all strings from file
     */
    private ArrayList<String> fieldsIn(String line) {
        String name;
        ArrayList<String> list = new ArrayList<String>();

        for (int i = 0; i < line.length() + 1; i++) {
            // Find the comma index
            int index = line.indexOf(",");

            if (line.length() == 0) {
                break;
            } else if (line.length() == 1){
                name = "";
                line = "";
                list.add(name);
                list.add(name);
            }

            // if the cell is empty
            if (index == 0 && line.length() > 1) {
                name = "";
                line = line.substring(1, line.length());
                list.add(name);
                i = 0;
                continue;
            }

            if (index == i) {
                if (line.charAt(0) == '"') {                                    // if cell starts '"'
                    if (line.charAt(i - 1) == '"') {                            // if cell ends '"'
                        name = line.substring(1, i - 1);
                        line = line.substring(i + 1, line.length());
                        list.add(name);
                    } else if (line.charAt(i - 1) != '"' && line.charAt(i + 1) == ' ') {            //if comma in cell
                        index = line.indexOf('"', i);
                        if (index == line.length() - 1) {                       // if cell ends '"' and it is last cell
                            name = line.substring(1, line.length() - 1);
                            list.add(name);
                            line = "";
                            break;
                        } else {                                                // if cell ends '"'
                            name = line.substring(1, index);
                            line = line.substring(index + 2, line.length());
                            list.add(name);
                        }
                    }
                    i = 0;
                } else {                                                        // if cell starts without '"'
                    if (line.charAt(i - 1) == '"') {                            // if cell ends '"'
                        name = line.substring(0, i - 1);
                    } else {                                                    // if cell ends without '"'
                        name = line.substring(0, i);
                    }
                    line = line.substring(i + 1, line.length());
                    list.add(name);
                    i = 0;
                }
            } else if (index == -1) {                                        // if no comma in cell and it is last cell
                if (line.charAt(0) == '"') {                                 // if cell starts '"'
                    if (line.charAt(line.length() - 1) == '"') {             // if cell ends '"'
                        name = line.substring(1, line.length() - 1);
                        line = "";
                        list.add(name);
                    }
                } else {                                                     // if cell starts without '"'
                    if (line.charAt(line.length() - 1) == '"') {             // if cell ends '"'
                        name = line.substring(0, line.length() - 1);
                        line = "";
                        list.add(name);
                    } else {                                                 //if cell ends without '"'
                        name = line.substring(0, line.length());
                        line = "";
                        list.add(name);
                    }
                }
            }
        }
        return list;
    }
}
