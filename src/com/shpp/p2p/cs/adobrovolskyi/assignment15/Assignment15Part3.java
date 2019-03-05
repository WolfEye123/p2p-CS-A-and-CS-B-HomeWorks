package com.shpp.p2p.cs.adobrovolskyi.assignment15;

import com.shpp.p2p.cs.adobrovolskyi.assignment16.MyArrayList;
import com.shpp.p2p.cs.adobrovolskyi.assignment17.MyPriorityQueue;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.PriorityQueue;

/**
 * A class in which the process of unzipping the file
 */
class Assignment15Part3 extends Assignment15Part4 {

    /**
     * The method that receives the name of the archive and the file in which it is unpacked.
     * Reads the file bytes and then processes them, compiles a list of unique characters and an associative table for unpacking.
     * Repeating the number of repetitions of each of the unique characters, the Huffman tree appears.
     * Based on this, rebuilds the byte array and writes them to a file.
     *
     * @param str1 the name of the unzipping file
     * @param str2 the file from which it is being unzipping
     * @return unzipping result
     */
    int unarchive(String str1, String str2) {
        try (FileInputStream inputStream = new FileInputStream(str2);
             FileOutputStream outputStream = new FileOutputStream(str1)) {

            byte[] buffer = new byte[inputStream.available()];

            /* read file into byte array */
            inputStream.read(buffer, 0, buffer.length);
            inputStream.close();

            int dataCounter = buffer.length;
            System.out.println('\n' + "Incoming file size" + ":" + '\n' + dataCounter + " byte");

            /* reads the size of the unique character table and the size of the data */
            int[] numbers = readDataCounterAndTableSizeBytesNumbers(buffer);

            /* create associative table */
            MyArrayList<Integer> associationTable = getAssociationTable(buffer, numbers[0]);

            int numbersAndTableSize = (numbers[0] * ASSOCIATION_TABLE_BYTES_NUMBERS) + TABLE_SIZE_AND_DATA_COUNTER_SIZE;

            /* overwrites the buffer with file data only */
            buffer = getOnlyData(buffer, numbersAndTableSize);

            /* overwrites the buffer after restoring the original byte order */
            buffer = getBufferWithOriginalBytesSequence(associationTable, buffer, numbers);

            dataCounter = buffer.length;

            System.out.println('\n' + "Outgoing file size" + ":" + '\n' + dataCounter + " byte");

            outputStream.write(buffer, 0, buffer.length);

        } catch (Exception e) {
            e.printStackTrace();
            return 1;
        }
        return 0;
    }

    /**
     * Processes the byte array separating the dimensions and the table from it
     *
     * @param buffer              bytes array
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
     *
     * @param buffer  bytes array
     * @param numbers number of unique characters
     * @return association table
     */
    private static MyArrayList<Integer> getAssociationTable(byte[] buffer, int numbers) {
        MyArrayList<Integer> associationTable = new MyArrayList<>();
        for (int i = 0; i < buffer.length; i += ASSOCIATION_TABLE_BYTES_NUMBERS) {
            associationTable.add((int) buffer[i + TABLE_SIZE_AND_DATA_COUNTER_SIZE]);
            StringBuilder str = new StringBuilder();
            for (int j = i + 1; j < i + ASSOCIATION_TABLE_BYTES_NUMBERS; j++) {
                if (buffer[j + TABLE_SIZE_AND_DATA_COUNTER_SIZE] < 10) {
                    str.append("0");
                    str.append(buffer[j + TABLE_SIZE_AND_DATA_COUNTER_SIZE]);
                } else {
                    str.append(buffer[j + TABLE_SIZE_AND_DATA_COUNTER_SIZE]);
                }
            }
            associationTable.add(Integer.parseInt(str.toString()));
            if (associationTable.size() == numbers * 2) {
                break;
            }
        }

        return associationTable;
    }

    /**
     * Separates from the array of bytes the number of unique characters and the amount of data
     *
     * @param buffer bytes array
     * @return an array with the number of unique characters and the amount of data
     */
    private static int[] readDataCounterAndTableSizeBytesNumbers(byte[] buffer) {
        int[] numbers = new int[2];
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < BYTES_NUMBERS; i++) {
            if (buffer[i] != 0) {
                str.append(buffer[i]);
            }
        }
        numbers[0] = Integer.parseInt(str.toString());
        str = new StringBuilder();
        for (int i = BYTES_NUMBERS; i < TABLE_SIZE_AND_DATA_COUNTER_SIZE; i++) {
            if (buffer[i] != 0) {
                str.append(buffer[i]);
            }
        }
        numbers[1] = Integer.parseInt(str.toString());

        return numbers;
    }

    /**
     * This method gets a hash map with unique characters and the number of repetitions in the file,
     * as well as a priority queue on the basis of which builds a huffman tree.
     *
     * @param associationTable a list of unique characters with the number of repetitions
     * @param priorityQueue    priority queue for building a tree
     * @return huffman tree
     */
    private InternalNode buildHuffmanTree(MyArrayList<Integer> associationTable, MyPriorityQueue<Node> priorityQueue) {
        int sum = 0;
        while (priorityQueue.size() > 1) {
            Node first = priorityQueue.poll();
            Node second = priorityQueue.poll();
            InternalNode node = new InternalNode(first, second);
            sum += node.sum;
            priorityQueue.add(node);
        }
        if (associationTable.size() == 2) {
            sum = associationTable.size() - 1;
        }
        System.out.println((associationTable.size() / 2) + " " + sum);
        InternalNode root = (InternalNode) priorityQueue.poll();
        if (associationTable.size() == 2) {
            root.code = "0";
        } else {
            root.buildCode("");
        }
        return root;
    }

    /**
     * the method takes an associative table, a buffer with bytes, and the amount of data.
     * processing the number of repetitions of unique characters, a priority queue is built on the basis of which the huffman tree is built.
     * starting from the tree recodes encoded bytes to original ones and creates an array of bytes
     * @param associationTable a list of unique characters with the number of repetitions
     * @param buffer bytes array
     * @param numbers number of unique characters
     * @return original bytes array
     */
    private byte[] getBufferWithOriginalBytesSequence(MyArrayList<Integer> associationTable, byte[] buffer, int[] numbers) {
        MyPriorityQueue<Node> priorityQueue = new MyPriorityQueue<>();
        for (int i = 0; i < associationTable.size() - 1; i += 2) {
            LeafNode node = new LeafNode((byte) (int) associationTable.get(i), associationTable.get(i + 1));
            priorityQueue.add(node);
        }
        InternalNode root = buildHuffmanTree(associationTable, priorityQueue);

        return getBuffer(getOriginalBytesSequence(buffer, numbers, root));
    }

    /**
     * the method gets the array list with bytes and overtakes it from the byte array
     * @param buffer1 array list with original bytes sequences
     * @return bytes array
     */
    private byte[] getBuffer(MyArrayList<Byte> buffer1) {
        byte[] bufferNew = new byte[buffer1.size()];
        for (int i = 0; i < buffer1.size(); i++) {
            bufferNew[i] = buffer1.get(i);
        }
        return bufferNew;
    }

    /**
     * buffer method, tree and amount of data. based on the received bytes, it receives a bit sequence,
     * starting from which it moves along the nodes of the tree and builds the original byte sequence
     * @param buffer bytes array
     * @param numbers number of unique characters
     * @param root huffman tree
     * @return ArrayList with original bytes sequences
     */
    private MyArrayList<Byte> getOriginalBytesSequence(byte[] buffer, int[] numbers, InternalNode root) {
        MyArrayList<Byte> bytes = new MyArrayList<>();
        StringBuilder str = new StringBuilder();
        StringBuilder str1 = new StringBuilder();
        InternalNode currentPosition = root;
        for (byte aBuffer : buffer) {
            getBinaryString(aBuffer, str);
            currentPosition = parseBinaryStringAndGetOriginalBytes(numbers, root, bytes, str, str1, currentPosition);
        }
        return bytes;
    }

    /**
     * a method which, based on the received bit sequences, goes through the nodes of the tree and finds the correct sequence of bytes
     * @param numbers number of unique characters
     * @param root huffman tree
     * @param bytes ArrayList with original bytes sequences
     * @param str the string from which it takes the binary sequence
     * @param str1 the string to write the binary sequence
     * @param currentPosition tree relative to the place at which the parsing of the string ended
     * @return returns a tree relative to the place at which the parsing of the string ended
     */
    private InternalNode parseBinaryStringAndGetOriginalBytes(int[] numbers, InternalNode root, MyArrayList<Byte> bytes, StringBuilder str, StringBuilder str1, InternalNode currentPosition) {
        while (str.length() != 0){
            if (str.charAt(str.length() - 1) == '0') {
                str1.append(str.charAt(str.length() - 1));
                str.deleteCharAt(str.length() - 1);
                if (currentPosition.getClass() != currentPosition.left.getClass()) {
                    LeafNode leafNode = (LeafNode) currentPosition.left;
                    bytes.add(leafNode.getByte());
                    if (bytes.size() == numbers[1]) {
                        break;
                    }
                    currentPosition = root;
                    str1.replace(0, str1.length(), "");
                    continue;
                }
                currentPosition = (InternalNode) currentPosition.left;
            } else if (str.charAt(str.length() - 1) == '1'){
                str1.append(str.charAt(str.length() - 1));
                str.deleteCharAt(str.length() - 1);
                if (currentPosition.getClass() != currentPosition.right.getClass()) {
                    LeafNode leafNode = (LeafNode) currentPosition.right;
                    bytes.add(leafNode.getByte());
                    if (bytes.size() == numbers[1]) {
                        break;
                    }
                    currentPosition = root;
                    str1.replace(0, str1.length(), "");
                    continue;
                }
                currentPosition = (InternalNode) currentPosition.right;
            }
        }
        return currentPosition;
    }

    /**
     * The method gets the byte from the buffer and an empty string. processes bytes and returns a bit sequence
     * @param Byte byte from bytes array
     * @param str empty string
     * @return string with binary sequences
     */
    private StringBuilder getBinaryString(byte Byte, StringBuilder str) {
        int tmp;
        if (Byte < 0) {
            tmp = 256 - Math.abs(Byte);
        } else {
            tmp = Byte;
        }
        while (tmp != 0) {
            str.append(tmp % 2);
            tmp = tmp / 2;
        }
        while (str.length() < 8) {
            str.append("0", 0, 1);
        }
        return str;
    }
}
