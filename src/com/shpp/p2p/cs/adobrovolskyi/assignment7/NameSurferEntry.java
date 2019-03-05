package com.shpp.p2p.cs.adobrovolskyi.assignment7;

/*
 * File: NameSurferEntry.java
 * --------------------------
 * This class represents a single entry in the database.  Each
 * NameSurferEntry contains a name and a list giving the popularity
 * of that name for each decade stretching back to 1900.
 */

/**
 * . Artem Dobrovolskyi @2018
 */
public class NameSurferEntry implements NameSurferConstants {


    private String nameSurferEntry;
    //array to store items from the resulting string
    private String[] entryName;
    //array to store the ranking of names for decades
    public int[] rank;

    /* Constructor: NameSurferEntry(line) */


    /**
     * Creates a new NameSurferEntry from a data line as it appears
     * in the data file.  Each line begins with the name, which is
     * followed by integers giving the rank of that name for each
     * decade.
     */
    public NameSurferEntry(String line) {

        //divide the string into elements and put them into an array
        String pattern = "\\s+|,\\s*";
        entryName = line.split(pattern, 13);
        nameSurferEntry = entryName[0];

        //placing rating names in the array
        rank = new int[NDECADES];
        for (int i = 0; i < NDECADES; i++) {
            rank[i] = Integer.parseInt(entryName[i + 1]);
        }
    }

    /* Method: getName() */

    /**
     * Returns the name associated with this entry.
     */
    public String getName() {
        return entryName[0];
    }

    /* Method: getRank(decade) */

    /**
     * Returns the rank associated with an entry for a particular
     * decade.  The decade value is an integer indicating how many
     * decades have passed since the first year in the database,
     * which is given by the constant START_DECADE.  If a name does
     * not appear in a decade, the rank value is 0.
     */
    public int getRank(int decade) {
        return rank[decade];
    }

    /* Method: toString() */

    /**
     * Returns a string that makes it easy to see the value of a
     * NameSurferEntry.
     */
    public String toString() {
        String result = "\"" + nameSurferEntry + " [";
        for (int i = 0; i < NDECADES; i++) {
            result += getRank(i) + " ";
        }
        result += "]\"";
        return result;
    }
}

