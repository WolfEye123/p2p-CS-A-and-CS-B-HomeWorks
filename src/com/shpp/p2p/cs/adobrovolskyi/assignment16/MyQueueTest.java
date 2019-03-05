package com.shpp.p2p.cs.adobrovolskyi.assignment16;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Queue;

import static org.junit.jupiter.api.Assertions.*;

class MyQueueTest {
    MyQueue<Integer> test = new MyQueue<>();

    @BeforeEach
    void setUp() {
        for (int i = 0; i < 1000; i++) {
            test.offer(i);
        }
    }

    @Test
    void offer() {
        for (int i = 0; i < 1000; i++) {
            assertEquals((Integer) i,test.offer(i));
        }
    }

    @Test
    void isEmpty() {
//        assertEquals(0,(Integer)(test.isEmpty()));
    }

    @Test
    void peek() {
        assertEquals((Integer)0,test.peek());

    }

    @Test
    void pop() {
        for (int i = 0; i < 1000; i++) {
            assertEquals((Integer) i,test.pop());
        }
    }
}