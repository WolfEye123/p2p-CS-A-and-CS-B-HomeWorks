package com.shpp.p2p.cs.adobrovolskyi.assignment16;

import java.util.Iterator;

/**
 *  In this class, a collection is written on its own that should work as a standard Stack.
 * @param <E> generic which indicates input data type
 */
public class MyStack<E> implements Iterable<E> {

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
    private E[] stack = (E[]) new Object[DEFAULT_CAPACITY];

    /**
     * method adds the specified object to the array
     * @param element specified object
     * @return specified object
     */
    public E push(E element) {
        if (size + 1 >= stack.length) {
            stack = getNewArray(stack);
        }
        for (int i = size; i < stack.length; i++) {
            if (stack[i] == null) {
                stack[i] = element;
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
        return stack[size - 1];
    }

    /**
     * delete and return last element from array
     * @return last element from array
     */
    public E pop() {
        E item = peek();
        stack[size - 1] = null;
        System.arraycopy(stack, 0, stack, 0, size - 1);
        size--;
        return item;
    }

    /**
     * @return return array size
     */
    public int size() {
        return size;
    }

    /**
     * search how many pops need to get that element
     * @param o specified object
     * @return how many pops need
     */
    public int search(Object o) {
        int count = 0;
        for (int i = size - 1; i > 0; i--) {
            count++;
            if (o.equals(stack[i])){
                return count;
            }
        }
        return -1;
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
                return hasNext()? (E)stack[index++] : null;
            }
        };
    }

}
