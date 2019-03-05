package com.shpp.p2p.cs.adobrovolskyi.assignment15;

import com.shpp.p2p.cs.adobrovolskyi.assignment16.MyArrayList;
import com.shpp.p2p.cs.adobrovolskyi.assignment17.MyHashMap;
import com.shpp.p2p.cs.adobrovolskyi.assignment17.MyPriorityQueue;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;

/**
 * A class in which the file is being archived
 */
class Assignment15Part2 extends Assignment15Part4 {

    /**
     * The method that receives the name of the archived file and the file into which it is archived.
     * Reads the file bytes, then processes them,
     * compiles a list of unique characters and an associative table for archiving.
     * Repeating the number of repetitions of each of the unique characters, the Huffman tree appears.
     * Based on this, rebuilds the byte array and writes them to a file.
     *
     * @param str1 the name of the archived file
     * @param str2 the file to which it is being archived
     * @return archiving result
     */
    int archive(String str1, String str2) {
        try (FileInputStream inputStream = new FileInputStream(str1);
             FileOutputStream outputStream = new FileOutputStream(str2)) {

            byte[] buffer = new byte[inputStream.available()];

            /* read file into byte array */
            inputStream.read(buffer, 0, buffer.length);
            inputStream.close();

            int dataCounter = buffer.length;
            System.out.println('\n' + "Incoming file size" + ":" + '\n' + dataCounter + " byte");

            /* creating a list of unique characters */
            MyHashMap<Byte, Integer> uniqueCharacters = searchForUniqueCharacters(buffer);

            /* create associative table */
            MyArrayList<Byte> associationTable = addAssociationTable(uniqueCharacters);

            /* creating a priority queue to create a tree */
            MyArrayList<Byte> bufferNew = createPriorityQueue(uniqueCharacters, buffer);

            /* recodes bytes according to the huffman tree */
            buffer = getNewBytesArray(associationTable, bufferNew, uniqueCharacters, dataCounter);

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
     * This method gets an associative table, a hash map with unique characters and a buffer with new bytes.
     * connects all elements to byte array to write to file
     *
     * @param associationTable a list of unique characters with the number of repetitions
     * @param bufferNew        array list with new bytes
     * @param uniqueCharacters a list of unique characters
     * @param dataCounter      amount of data to archive
     * @return byte array with new bytes
     */
    private byte[] getNewBytesArray(MyArrayList<Byte> associationTable, MyArrayList<Byte> bufferNew, MyHashMap<Byte, Integer> uniqueCharacters, int dataCounter) {
        for (int i = 0; i < bufferNew.size(); i++) {
            associationTable.add(bufferNew.get(i));
        }
        return getBytesArray(associationTable, dataCounter, uniqueCharacters.size());
    }

    /**
     * Creates a byte array, which specifies the number of unique characters and the amount of data to be archived,
     * associative table and the data itself
     *
     * @param buffer1     list of bytes for archiving
     * @param dataCounter amount of data to archive
     * @param tableSize   associative table size
     * @return number of unique characters and the amount of data to be archived
     */
    private static byte[] getBytesArray(MyArrayList<Byte> buffer1, int dataCounter, int tableSize) {
        byte[] dataCounterAndTableSizeBytesNumbers = getBytesFromCounters(dataCounter, tableSize);
        int bufferSize = buffer1.size() + dataCounterAndTableSizeBytesNumbers.length;
        byte[] bufferNew = new byte[bufferSize];
        int i = 0;
        for (; i < TABLE_SIZE_AND_DATA_COUNTER_SIZE; i++) {
            bufferNew[i] = dataCounterAndTableSizeBytesNumbers[i];
        }
        for (int j = 0; i < bufferSize; i++, j++) {
            bufferNew[i] = buffer1.get(j);
        }
        return bufferNew;
    }

    /**
     * Creates a list of bytes in which is the number of unique characters and the amount of data to be archived
     *
     * @param dataCounter amount of data to archive
     * @param tableSize   associative table size
     * @return number of unique characters and the amount of data to be archived
     */
    private static byte[] getBytesFromCounters(int dataCounter, int tableSize) {
        byte[] numbers = new byte[TABLE_SIZE_AND_DATA_COUNTER_SIZE];
        int i = TABLE_SIZE_AND_DATA_COUNTER_SIZE;
        for (; i > BYTES_NUMBERS; i--) {
            byte tmp = (byte) (dataCounter % SEPARATION_FACTOR);
            dataCounter /= SEPARATION_FACTOR;
            numbers[i - 1] = tmp;
        }
        for (; i > 0; i--) {
            byte tmp = (byte) (tableSize % SEPARATION_FACTOR);
            tableSize /= SEPARATION_FACTOR;
            numbers[i - 1] = tmp;
        }
        return numbers;
    }

    /**
     * Create a list with unique characters association table
     *
     * @param uniqueCharacters a list of unique characters
     * @return a list with unique characters association table
     */
    private static MyArrayList<Byte> addAssociationTable(MyHashMap<Byte, Integer> uniqueCharacters) {
        MyArrayList<Byte> buffer = new MyArrayList<>();
        for (Byte key : uniqueCharacters) {
            buffer.add(key);
            byte[] numbers = new byte[BYTES_NUMBERS];
            System.out.println(key);
            int tmp = uniqueCharacters.get(key);
            for (int i = BYTES_NUMBERS; i > 0; i--) {
                byte tmp1 = (byte) (tmp % SEPARATION_FACTOR);
                tmp /= SEPARATION_FACTOR;
                numbers[i - 1] = tmp1;
            }
            for (int i = 0; i < BYTES_NUMBERS; i++) {
                buffer.add(numbers[i]);
            }
        }
        return buffer;
    }

    /**
     * The method makes a list of unique characters based on an array of bytes from a file
     *
     * @param buffer an array of bytes from a file
     * @return a list of unique characters
     */
    private static MyHashMap<Byte, Integer> searchForUniqueCharacters(byte[] buffer) {
        MyHashMap<Byte, Integer> uniqueCharacters = new MyHashMap<>();
        for (int i = 0; i < buffer.length; i++) {
            if (uniqueCharacters.get(buffer[i]) != null) {
                uniqueCharacters.put(buffer[i], uniqueCharacters.get(buffer[i]) + 1);
            } else {
                uniqueCharacters.put(buffer[i], 1);
            }
        }
        return uniqueCharacters;
    }

    /**
     * a method that receives a buffer byte from a file and a hash map with unique characters.
     * creates a priority queue according to the number of repetitions of unique characters on the basis of which builds a huffman tree
     *
     * @param uniqueCharacters unique characters with the number of repetitions in the file
     * @param buffer           buffer with original byte order
     * @return byte array with new bytes
     */
    private MyArrayList<Byte> createPriorityQueue(MyHashMap<Byte, Integer> uniqueCharacters, byte[] buffer) {
        MyHashMap<Byte, Node> byteNodes = new MyHashMap<>();
        MyPriorityQueue<Node> priorityQueue = new MyPriorityQueue<>();
        for (Byte key : uniqueCharacters) {
            LeafNode node = new LeafNode(key, uniqueCharacters.get(key));
            byteNodes.put(key, node);
            priorityQueue.add(node);
        }
        buildHuffmanTree(uniqueCharacters, priorityQueue);
        return getNewBytes(buffer, byteNodes);
    }

    /**
     * the method that agrees with the bit sequences builds the original byte order
     *
     * @param buffer    buffer with encoded bytes
     * @param byteNodes hash map with bytes and their bit sequences according to the tree
     * @return ArrayList with new bytes
     */
    private MyArrayList<Byte> getNewBytes(byte[] buffer, MyHashMap<Byte, Node> byteNodes) {
        MyArrayList<Byte> bufferNew = new MyArrayList<>();
        StringBuilder result = new StringBuilder();
        StringBuilder str = new StringBuilder();
        int i = 0;
        for (byte key : buffer) {
            result.append(byteNodes.get(key).code);
            while (result.length() != 0) {
                str.append(result.charAt(0));
                result.deleteCharAt(0);
                if (i == buffer.length - 1 && str.length() < 8 && result.length() == 0) {
                    while (str.length() != 8) {
                        str.append("0");
                    }
                }
                if (str.length() == 8) {
                    bufferNew.add((byte) Integer.parseInt(String.valueOf(str), 2));
                    str.delete(0, str.length());
                }
            }
            i++;
        }
        return bufferNew;
    }

    /**
     * This method gets a hash map with unique characters and the number of repetitions in the file,
     * as well as a priority queue on the basis of which builds a huffman tree.
     *
     * @param uniqueCharacters unique characters with the number of repetitions in the file
     * @param priorityQueue    priority queue for building a tree
     */
    private void buildHuffmanTree(MyHashMap<Byte, Integer> uniqueCharacters, MyPriorityQueue<Node> priorityQueue) {
        int sum = 0;
        while (priorityQueue.size() > 1) {
            Node first = priorityQueue.poll();
            Node second = priorityQueue.poll();
            InternalNode node = new InternalNode(first, second);
            sum += node.sum;
            priorityQueue.add(node);
        }
        if (uniqueCharacters.size() == 1) {
            sum = uniqueCharacters.size();
        }
        Node root = priorityQueue.poll();
        if (uniqueCharacters.size() == 1) {
            root.code = "0";
        } else {
            root.buildCode("");
        }
    }


}
