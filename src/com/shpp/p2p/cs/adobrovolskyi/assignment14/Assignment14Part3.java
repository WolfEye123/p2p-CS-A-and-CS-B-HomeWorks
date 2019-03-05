package com.shpp.p2p.cs.adobrovolskyi.assignment14;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import static java.lang.Math.pow;

/**
 * A class in which the process of unzipping the file
 */
class Assignment14Part3 extends Assignment14Part2{

    /* the number of bytes occupied by the table size and data size */
    private static final int NUMBERS_OF_TABLE_AND_DATA = 12;

    /**
     *The method that receives the name of the archive and the file in which it is unpacked.
     * Reads the file bytes and then processes them, compiles a list of unique characters and an associative table for unpacking.
     * Counts the required number of bits for unpacking the received number of unique characters.
     * Based on this, rebuilds the byte array and writes them to a new file.
     * @param str1 the name of the unzipping file
     * @param str2 the file from which it is being unzipping
     * @return unzipping result
     */
    static int unarchive(String str1, String str2) {
        try (FileInputStream inputStream = new FileInputStream(str2);
             FileOutputStream outputStream = new FileOutputStream(str1)) {

            byte[] buffer = new byte[inputStream.available()];
            System.out.println('\n' + "Incoming file size" + ":" + '\n' + buffer.length + " byte");

            /* read file into byte array */
            inputStream.read(buffer, 0, buffer.length);
            inputStream.close();

            /* reads the size of the unique character table and the size of the data */
            int[] numbers = readDataCounterAndTableSizeBytesNumbers(buffer);

            /* create associative table */
            int[] associationTable = getAssociationTable(buffer, numbers[0]);


            int numbersAndTableSize = associationTable.length + NUMBERS_OF_TABLE_AND_DATA;


            int bitsNumber = checkForBitsSizeToUniqueCharacters(numbers[0]);

            /* creates an array of bytes with data */
            buffer = getOnlyData(buffer, numbersAndTableSize);

            /* processes an array of bytes and finds bytes for the replace */
            ArrayList<Integer> fakeBytes = findFakeBytes(buffer, numbers, bitsNumber);

            /* processes the byte array and constructs the original byte sequence. */
            buffer = changeFakeBytesForReal(fakeBytes, associationTable);

            System.out.println('\n' + "Outgoing file size" + ":" + '\n' + buffer.length + " byte");

            outputStream.write(buffer, 0, buffer.length);


        } catch (Exception e) {
            e.printStackTrace();
            return 1;
        }
        return 0;
    }

    /**
     * The method takes an array with the replaced bytes and builds the original byte sequence based on the associative table
     * @param fakeBytes a list with fake unique characters
     * @param associationTable a list with unique characters association table
     * @return array with original byte sequence
     */
    private static byte[] changeFakeBytesForReal(ArrayList<Integer> fakeBytes, int[] associationTable) {
        byte[] bufferNew = new byte[fakeBytes.size()];
        int tmp;
        for (int i = 0; i < fakeBytes.size(); i++) {
            tmp = fakeBytes.get(i);
            for (int j = 1; j < associationTable.length; j += 2) {
                if (associationTable[j] == tmp) {
                    bufferNew[i] = (byte) associationTable[j - 1];
                }
            }
        }


        return bufferNew;
    }

    /**
     * Processes an array of bytes and finds bytes for the replace
     * @param buffer an array of bytes with data
     * @param numbers  the size of the unique character table and the size of the data
     * @param bitsNumber bits size to unique characters
     * @return byte array with swapped bytes
     */
    private static ArrayList<Integer> findFakeBytes(byte[] buffer, int[] numbers, int bitsNumber) {
        ArrayList<Integer> bytes = new ArrayList<>();
        int dataCounter = 0;
        int tmp;
        StringBuilder str = new StringBuilder();
        StringBuilder str1 = new StringBuilder();

        for (int i = 0; i < buffer.length; i++) {
            if (buffer[i] < 0) {
                tmp = 256 - Math.abs(buffer[i]);
            } else {
                tmp = buffer[i];
            }
            while (tmp != 0) {
                str.append(tmp % 2);
                tmp = tmp / 2;
            }
            while (str.length() < 8) {
                str.append("0", 0, 1);
            }

            while (str.length() != 0) {
                if (str1.length() == bitsNumber) {
                    str1.replace(0, str1.length(), "");
                }
                while (str.length() != 0 && str1.length() != bitsNumber) {
                    str1.append(str.charAt(str.length() - 1));
                    str.deleteCharAt(str.length() - 1);
                }
                if (str1.length() == bitsNumber) {
                    int byteNumber = Integer.parseInt(String.valueOf(str1), 2);
                    bytes.add(byteNumber);
                    dataCounter++;
                    if (dataCounter == numbers[1]) {
                        break;
                    }
                }
            }
            if (dataCounter == numbers[1]) {
                break;
            }
        }

        return bytes;
    }

    /**
     * Processes the byte array separating the dimensions and the table from it
     * @param buffer bytes array
     * @param numbersAndTableSize the number of bytes that the table and data size and the table itself occupy
     * @return data array
     */
    private static byte[] getOnlyData(byte[] buffer, int numbersAndTableSize) {
        int bufferSize = buffer.length - numbersAndTableSize;
        byte[] bufferNew = new byte[bufferSize];
        for (int i = numbersAndTableSize, j = 0; i < buffer.length; i++, j++) {
            bufferNew[j] = buffer[i];
        }

        return bufferNew;
    }

    /**
     * Separates an associative table from the byte array
     * @param buffer bytes array
     * @param numbers number of unique characters
     * @return association table
     */
    private static int[] getAssociationTable(byte[] buffer, int numbers) {
        int[] associationTable = new int[numbers * 2];
        int tmp;
        for (int i = 0; i < numbers * 2; i++) {
            tmp = buffer[i + 12];
            if (i % 2 != 0 && buffer[i + 12] < 0) {
                associationTable[i] = 256 - Math.abs(tmp);
            } else {
                associationTable[i] = tmp;
            }
        }


        return associationTable;
    }

    /**
     * Separates from the array of bytes the number of unique characters and the amount of data
     * @param buffer bytes array
     * @return an array with the number of unique characters and the amount of data
     */
    private static int[] readDataCounterAndTableSizeBytesNumbers(byte[] buffer) {
        int[] numbers = new int[2];
        String str = "";
        for (int i = 0; i < 4; i++) {
            if (buffer[i] != 0) {
                str += buffer[i];
            }
        }
        numbers[0] = Integer.parseInt(str);
        str = "";
        for (int i = 4; i < 12; i++) {
            if (buffer[i] != 0) {
                str += buffer[i];
            }
        }
        numbers[1] = Integer.parseInt(str);

        return numbers;
    }
}
