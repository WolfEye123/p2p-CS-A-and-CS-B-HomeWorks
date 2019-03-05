package com.shpp.p2p.cs.adobrovolskyi.assignment16;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class MyArrayListTest {

    MyArrayList<Integer> test = new MyArrayList();
    ArrayList<Integer> original = new ArrayList<>();

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        for (int i = 0; i < 1000; i++) {
            original.add(i);
            test.add(i);
        }
    }

    @org.junit.jupiter.api.Test
    void add() {
        for (int i = 0; i < 1000; i++) {
            assertEquals(original.add(i), test.add(i));
        }
        assertEquals(original.size(), test.size());
    }

    @org.junit.jupiter.api.Test
    void get() {
        for (int i = 0; i < 1000; i++) {
            assertEquals(original.get(i), test.get(i));
        }
    }

    @org.junit.jupiter.api.Test
    void set() {
        for (int i = 10; i < 990; i++) {
            assertEquals(original.set(i, i + 1), test.set(i, i + 1));
        }
    }

    @org.junit.jupiter.api.Test
    void remove() {
        for (int i = 0; i < 450; i++) {
            assertEquals(original.remove(i),test.remove(i));
        }
    }

    @Test
    void add1() {
        for (int i = 0; i < 1000; i++) {
            assertEquals(original.add(i), test.add(i));
        }
        assertEquals(original.size(), test.size());
    }

    @Test
    void size() {
        assertEquals(original.size(), test.size());
    }

    @Test
    void remove1() {
        for (int i = 0; i < 450; i++) {
            assertEquals(original.remove(i),test.remove(i));
        }
    }

    @Test
    void isEmpty() {
            assertEquals(original.isEmpty(),test.isEmpty());
    }

    @Test
    void clear() {
//        assertEquals(original.clear(),test.clear());
    }

    @Test
    void indexOf() {
        for (int i = 0; i < 450; i++) {
            assertEquals(original.indexOf(i),test.indexOf(i));
        }
    }

    @Test
    void contains() {
        for (int i = 0; i < 450; i++) {
            assertEquals(original.contains(i),test.contains(i));
        }
    }
}