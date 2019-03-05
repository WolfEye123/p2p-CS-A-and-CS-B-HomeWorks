package com.shpp.p2p.cs.adobrovolskyi.assignment16;

import java.util.Iterator;

/**
 *  In this class, a collection is written on its own that should work as a standard Queue.
 * @param <E> generic which indicates input data type
 */
public class MyQueue<E> implements Iterable<E>{

    /**
     * Default initial capacity.
     */
    private static final int DEFAULT_CAPACITY = 10;
    /**
     * The size of the ArrayList
     */
    private int size = 0;
    /**
     * Shared empty array instance used for empty instances.
     */
    private E[] queue = (E[]) new Object[DEFAULT_CAPACITY];

    /**
     * method adds the specified object to the beginning of the array
     * @param element specified object
     * @return specified object
     */
    public E offer(E element){
        if (size + 1 >= queue.length) {
            queue = getNewArray(queue);
        }
        for (int i = size; i < queue.length; i++) {
            if (queue[i] == null) {
                queue[i] = element;
                size++;
                return element;
            }
        }
        return element;
    }

    /**
     * method overwrites an array increasing its size
     * @param objects array of objects
     * @return rewritten array
     */
    private E[] getNewArray(E[] objects) {
        E[] array = (E[]) new Object[objects.length + DEFAULT_CAPACITY];
        System.arraycopy(objects, 0, array, 0, objects.length);
        return array;
    }

    /**
     * @return checks if the array is empty
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * @return last element from array
     */
    public E peek() {
        return queue[0];
    }

    /**
     * @return return array size
     */
    public int size() {
        return size;
    }

    /**
     * delete and return first element from array
     * @return first element from array
     */
    public E pop() {
        E item = queue[0];
        queue[0] = null;
        System.arraycopy(queue, 1, queue, 0, size - 1);
        size--;
        return item;
    }

    /**
     * iterator is used for loop type foreach
     * @return returns the object found in the array
     */
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            int index = 0;

            @Override
            public boolean hasNext() {
                return index < size;
            }

            @Override
            public E next() {
                return hasNext()? (E) queue[index++] : null;
            }
        };
    }
}
