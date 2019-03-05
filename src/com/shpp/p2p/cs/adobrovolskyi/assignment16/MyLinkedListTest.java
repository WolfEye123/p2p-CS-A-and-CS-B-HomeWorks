package com.shpp.p2p.cs.adobrovolskyi.assignment16;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

class MyLinkedListTest {
    MyLinkedList<Integer> test = new MyLinkedList<>();
    LinkedList<Integer> original = new LinkedList<>();

    @BeforeEach
    void setUp() {
        for (int i = 0; i < 1000; i++) {
            original.add(i);
            test.add(i);
        }
    }

    @Test
    void addLast() {
//        for (int i = 0; i < 100; i++) {
//            assertEquals(original.addLast(i),test.addLast(i));
//        }
    }

    @Test
    void addFirst() {
    }

    @Test
    void add() {
        for (int i = 0; i < 1000; i++) {
            original.add(i);
            test.add(i);
        }
    }

    @Test
    void size() {
        assertEquals(original.size(),test.size());
    }

    @Test
    void clear() {
//        assertEquals(original.clear(),test.clear());
    }

    @Test
    void get() {
        for (int i = 0; i < 1000; i++) {
            assertEquals(original.get(i),test.get(i));
        }
    }

    @Test
    void getLast() {
        for (int i = 0; i < 500; i++) {
            assertEquals(original.getLast(),test.getLast());
        }
    }

    @Test
    void getFirst() {
        for (int i = 0; i < 500; i++) {
            assertEquals(original.getFirst(),test.getFirst());
        }
    }

    @Test
    void indexOf() {
        for (int i = 0; i < 500; i++) {
            assertEquals(original.indexOf(i),test.indexOf(i));
        }
    }

    @Test
    void contains() {
    }

    @Test
    void removeFirst() {
        for (int i = 0; i < 500; i++) {
            assertEquals(original.removeFirst(),test.removeFirst());
        }
    }

    @Test
    void removeLast() {
        for (int i = 0; i < 500; i++) {
            assertEquals(original.removeLast(),test.removeLast());
        }
    }

    @Test
    void remove() {
        for (int i = 0; i < 200; i++) {
            assertEquals(original.remove(i),test.remove(i));
        }
    }

    @Test
    void isEmpty() {
        assertEquals(original.isEmpty(),test.isEmpty());

    }
}