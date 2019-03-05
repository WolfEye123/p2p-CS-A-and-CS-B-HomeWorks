package com.shpp.p2p.cs.adobrovolskyi.assignment7;

/*
 * File: NameSurferDataBase.java
 * -----------------------------
 * This class keeps track of the complete database of names.
 * The constructor reads in the database from a file, and
 * the only public method makes it possible to look up a
 * name and get back the corresponding NameSurferEntry.
 * Names are matched independent of case, so that "Eric"
 * and "ERIC" are the same names.
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * . Artem Dobrovolskyi @2018
 */
public class NameSurferDataBase implements NameSurferConstants {

    //creating a hash map to store objects after reading lines from a file
    private Map<String, NameSurferEntry> dataBase = new HashMap<>();

    /* Constructor: NameSurferDataBase(filename) */

    /**
     * Creates a new NameSurferDataBase and initializes it using the
     * data in the specified file.  The constructor throws an error
     * exception if the requested file does not exist or if an error
     * occurs as the file is being read.
     */
    public NameSurferDataBase(String filename){
        String line;

        try {
            BufferedReader word = new BufferedReader(new FileReader(filename));
            while (true){
                line = word.readLine();
                if (line == null) {
                    break;
                }
                NameSurferEntry nameEntry = new NameSurferEntry(line);
                dataBase.put(nameEntry.getName(),nameEntry);
            }
            word.close();
        }catch (IOException e){
            System.out.println(e);
        }

    }

    /* Method: findEntry(name) */

    /**
     * Returns the NameSurferEntry associated with this name, if one
     * exists.  If the name does not appear in the database, this
     * method returns null.
     */
    public NameSurferEntry findEntry(String name) {
        String firstLetter = name.substring(0,1).toUpperCase();
        name = firstLetter + name.substring(1).toLowerCase();

        return dataBase.getOrDefault(name, null);
    }
}

