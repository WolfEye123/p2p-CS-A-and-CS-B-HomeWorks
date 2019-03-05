package com.shpp.p2p.cs.adobrovolskyi.assignment16;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Stack;

import static org.junit.jupiter.api.Assertions.*;

class MyStackTest {

    MyStack<Integer> test = new MyStack<>();
    Stack<Integer> original = new Stack<>();

    @BeforeEach
    void setUp() {
        for (int i = 0; i < 1000; i++) {
            original.push(i);
            test.push(i);
        }
    }

    @Test
    void push() {
        for (int i = 0; i < 1000; i++) {
            assertEquals(original.push(i), test.push(i));
        }
    }

    @Test
    void isEmpty() {
        assertEquals(original.isEmpty(), test.isEmpty());
    }

    @Test
    void peek() {
        for (int i = 0; i < 1000; i++) {
            assertEquals(original.peek(), test.peek());
        }
    }

    @Test
    void pop() {
        for (int i = 0; i < 1000; i++) {
            assertEquals(original.pop(), test.pop());
        }
    }

    @Test
    void search() {
        for (int i = 500; i > 0; i--) {
            assertEquals(original.search(i), test.search(i));
        }
    }
}