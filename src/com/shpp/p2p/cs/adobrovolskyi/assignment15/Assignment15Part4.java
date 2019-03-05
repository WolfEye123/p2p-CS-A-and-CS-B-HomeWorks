package com.shpp.p2p.cs.adobrovolskyi.assignment15;

/**
 * class for building the structure of the huffman tree
 */
class Assignment15Part4 {

    /* number of bytes amount of data */
    static final int TABLE_SIZE_AND_DATA_COUNTER_SIZE = 12;
    /* separation factor for sediment separation */
    static final int SEPARATION_FACTOR = 100;
    /* number of bytes to put numbers */
    static final int BYTES_NUMBERS = 4;
    /* sequence step in the association table */
    static final int ASSOCIATION_TABLE_BYTES_NUMBERS = 5;


    /**
     * class for building nodes
     */
    class Node implements Comparable<Node> {
        final int sum;
        String code;

        void buildCode(String code) {
            this.code = code;
        }

        Node(int sum) {
            this.sum = sum;
        }

        @Override
        public int compareTo(Node o) {
            return Integer.compare(sum, o.sum);
        }
    }

    /**
     * class for building internal nodes
     */
    class InternalNode extends Node {
        Node left;
        Node right;

        @Override
        void buildCode(String code) {
            super.buildCode(code);
            left.buildCode(code + "0");
            right.buildCode(code + "1");
        }

        InternalNode(Node left, Node right) {
            super(left.sum + right.sum);
            this.left = left;
            this.right = right;
        }

    }

    /**
     * class for building leaf nodes
     */
    class LeafNode extends Node {
        byte Byte;

        @Override
        void buildCode(String code) {
            super.buildCode(code);
        }

        LeafNode(Byte Byte, int count) {
            super(count);
            this.Byte = Byte;
        }

        byte getByte() {return Byte;}

    }
}
