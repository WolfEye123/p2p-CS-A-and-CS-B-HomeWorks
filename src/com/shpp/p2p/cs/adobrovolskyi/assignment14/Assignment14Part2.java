package com.shpp.p2p.cs.adobrovolskyi.assignment14;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import static java.lang.Math.pow;

/**
 * A class in which the file is being archived
 */
class Assignment14Part2 {
    /**
     * The method that receives the name of the archived file and the file to which it is being archived.
     * Reads the bytes of the file and then processing them compiles a list of unique characters and an associative table for archiving.
     * Counts the required number of bits for archiving the received number of unique characters.
     * Based on this rebuilds the byte array and writes them to but
     * @param str1 the name of the archived file
     * @param str2 the file to which it is being archived
     * @return archiving result
     */
    static int archive(String str1, String str2) {
        try (FileInputStream inputStream = new FileInputStream(str1);
             FileOutputStream outputStream = new FileOutputStream(str2)) {

            byte[] buffer = new byte[inputStream.available()];

            /* read file into byte array */
            inputStream.read(buffer, 0, buffer.length);
            inputStream.close();

            int dataCounter = buffer.length;
            System.out.println('\n' + "Incoming file size" + ":" + '\n' + dataCounter + " byte");

            /* creating a list of unique characters */
            HashMap<Byte, Integer> uniqueCharacters = searchForUniqueCharacters(buffer);

            /* replacing unique characters with others */
            uniqueCharacters = changeBytesValue(uniqueCharacters);

            /* create associative table */
            ArrayList<Byte> associationTable = addAssociationTable(uniqueCharacters);

            int bitsNumber = checkForBitsSizeToUniqueCharacters(uniqueCharacters.size());

            /* changing variable characters to bit sequences */
            HashMap<Byte, String> uniqueCharactersNew = changeByteToBinary(uniqueCharacters, bitsNumber);

            /* rewrite byte array with new characters */
            buffer = changeBinaryToByte(uniqueCharactersNew, buffer, associationTable, dataCounter);

            dataCounter = buffer.length;

            System.out.println('\n' + "Outgoing file size" + ":" + '\n' + dataCounter + " byte");

            /* writing an array of bytes to a file */
            outputStream.write(buffer, 0, buffer.length);


        } catch (Exception e) {
            e.printStackTrace();
            return 1;
        }
        return 0;
    }

    /**
     * The method counts the number of bits needed to archive the received number of unique characters
     * @param uniqueCharacters number of unique characters
     * @return number of bits needed to archive
     */
    static int checkForBitsSizeToUniqueCharacters(int uniqueCharacters) {
        int count = 0;
        int number = 1;
        for (int i = 1; i < uniqueCharacters + 1; i++) {
            if (number >= uniqueCharacters) {
                if (count == 0) {
                    count++;
                }
                break;
            }
            number = (int) pow(2, i);
            count++;
        }

        return count;
    }

    /**
     * The method makes a list of unique characters based on an array of bytes from a file
     * @param buffer an array of bytes from a file
     * @return a list of unique characters
     */
    private static HashMap<Byte, Integer> searchForUniqueCharacters(byte[] buffer) {
        HashMap<Byte, Integer> uniqueCharacters = new HashMap<>();
        for (int i = 0; i < buffer.length; i++)
            if (uniqueCharacters.get(buffer[i]) != null) {
                uniqueCharacters.put(buffer[i], uniqueCharacters.get(buffer[i]) + 1);
            } else {
                uniqueCharacters.put(buffer[i], 1);
            }

        return uniqueCharacters;
    }

    /**
     * Method creates a list of unique characters with their substitutions
     * @param uniqueCharacters a list of unique characters
     * @return a list of unique characters with their substitutions
     */
    private static HashMap<Byte, Integer> changeBytesValue(HashMap<Byte, Integer> uniqueCharacters) {
        int i = 0;
        for (Byte key : uniqueCharacters.keySet()) {
            uniqueCharacters.replace(key, i);
            i++;
        }
        return uniqueCharacters;
    }

    /**
     * The method creates a list of unique characters with their substitutions in the bit sequence
     * based on the number of bits needed for encoding.
     * The task is implemented on obtaining the bit sequence through the remainder of dividing by 2.
     * @param uniqueCharacters a list of unique characters
     * @param bitsNumbers number of bits needed to archive
     * @return the method returns a list of unique characters with their substitutions in the bit sequence
     */
    private static HashMap<Byte, String> changeByteToBinary(HashMap<Byte, Integer> uniqueCharacters, int bitsNumbers) {
        HashMap<Byte, String> uniqueCharactersNew = new HashMap<>();
        int tmp;
        StringBuilder str = new StringBuilder();
        for (Byte key : uniqueCharacters.keySet()) {
            tmp = uniqueCharacters.get(key);
            while (tmp != 0) {
                str.append(tmp % 2);
                tmp = tmp / 2;
            }
            while (str.length() < bitsNumbers) {
                str.append("0", 0, 1);
            }
            String str1 = String.valueOf(str);
            str.replace(0, str.length(), "");
            uniqueCharactersNew.put(key, str1);
        }
        return uniqueCharactersNew;
    }

    /**
     * The method overwrites the byte array after processing the list with unique characters
     * based on the bit sequences of the new characters.
     * Composes new bytes from received bit sequences.
     * @param uniqueCharactersNew a list of unique characters with their substitutions in the bit sequence
     * @param buffer an array of bytes from a file
     * @param buffer1 list in which file data is stored
     * @param dataCounter amount of data to archive
     * @return A new array of bytes in which the table size and data size, the table and new data are already recorded
     */
    private static byte[] changeBinaryToByte(HashMap<Byte, String> uniqueCharactersNew, byte[] buffer, ArrayList<Byte> buffer1, int dataCounter) {
        String tmp = "", tmp1 = "";
        int counter = 0;

        for (int i = 0; i < buffer.length; i++) {
            tmp = uniqueCharactersNew.get(buffer[i]);
            while (tmp.length() != 0) {
                tmp1 += tmp.charAt(tmp.length() - 1);
                tmp = tmp.substring(0, tmp.length() - 1);
                counter++;
                if (i == buffer.length - 1 && counter < 8 && tmp.length() == 0) {
                    while (counter < 8) {
                        tmp1 += "0";
                        counter++;
                    }
                }
                if (counter == 8) {
                    buffer1.add((byte) Integer.parseInt(tmp1, 2));
                    tmp1 = "";
                    counter = 0;
                }
            }
        }

        int tableSize = uniqueCharactersNew.size();
        byte[] bufferNew = getBytesArray(buffer1, dataCounter, tableSize);

        return bufferNew;
    }

    /**
     * Creates a byte array, which specifies the number of unique characters and the amount of data to be archived,
     * associative table and the data itself
     * @param buffer1 list of bytes for archiving
     * @param dataCounter amount of data to archive
     * @param tableSize associative table size
     * @return number of unique characters and the amount of data to be archived
     */
    private static byte[] getBytesArray(ArrayList<Byte> buffer1, int dataCounter, int tableSize) {
        byte[] dataCounterAndTableSizeBytesNumbers = getBytesFromCounters(dataCounter, tableSize);
        int bufferSize = buffer1.size() + dataCounterAndTableSizeBytesNumbers.length;
        byte tableSizeAndDataCounterSize = 12;
        byte[] bufferNew = new byte[bufferSize];
        int i = 0;
        for (; i < tableSizeAndDataCounterSize; i++) {
            bufferNew[i] = dataCounterAndTableSizeBytesNumbers[i];
        }
        for (int j = 0; i < bufferSize; i++, j++) {
            bufferNew[i] = buffer1.get(j);
        }
        return bufferNew;
    }

    /**
     * Creates a list of bytes in which is the number of unique characters and the amount of data to be archived
     * @param dataCounter amount of data to archive
     * @param tableSize associative table size
     * @return number of unique characters and the amount of data to be archived
     */
    private static byte[] getBytesFromCounters(int dataCounter, int tableSize) {
        byte[] numbers = new byte[12];
        int i = 12;
        for (; i > 4; i--) {
            byte tmp = (byte) (dataCounter % 100);
            dataCounter /= 100;
            numbers[i - 1] = tmp;
        }
        for (; i > 0; i--) {
            byte tmp = (byte) (tableSize % 100);
            tableSize /= 100;
            numbers[i - 1] = tmp;
        }

        return numbers;
    }

    /**
     * Create a list with unique characters association table
     * @param uniqueCharacters a list of unique characters
     * @return a list with unique characters association table
     */
    private static ArrayList<Byte> addAssociationTable(HashMap<Byte, Integer> uniqueCharacters) {
        ArrayList<Byte> buffer = new ArrayList<>();
        for (Byte key : uniqueCharacters.keySet()) {
            buffer.add(key);
            buffer.add((byte) (int) uniqueCharacters.get(key));
        }

        return buffer;
    }

}
