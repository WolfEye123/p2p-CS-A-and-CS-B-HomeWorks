package com.shpp.p2p.cs.adobrovolskyi.assignment17;

import java.util.Iterator;

/**
 * In this class, a collection is written on its own that should work as a standard ArrayList.
 *
 * @param <E> generic which indicates input data type
 */
public class MyPriorityQueue<E extends Comparable<E>> implements Iterable<E> {

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
    private Object[] priorityQueue = new Object[DEFAULT_CAPACITY];
    /**
     * add value counter
     */
    private static int valuesCounter = 0;
    /**
     * child addition counter
     */
    private static int stepCounter = 1;

    /**
     * the method gets the object and adds it to the queue by priority
     * @param element receive object
     * @return boolean value
     */
    public boolean add(E element) {
        if (element == null) {
            throw new NullPointerException();
        }
        if (addingElement(element)) {
            return true;
        }
        siftUp(size - 1, element);
        return true;
    }

    /**
     * determines the cell index into which the object should be placed according to priority
     * @param element receive object
     * @return boolean value
     */
    private boolean addingElement(E element) {
        int leftChildIndex = valuesCounter * 2 + 1;
        int rightChildIndex = valuesCounter * 2 + 2;
        if (size == 0) {
            priorityQueue[0] = element;
            size++;
            return true;
        } else {
            if (size + 1 >= priorityQueue.length) {
                priorityQueue = getNewArray(priorityQueue);
            }
            if (stepCounter == 1) {
                priorityQueue[leftChildIndex] = element;
                stepCounter++;
                size++;
            } else {
                priorityQueue[rightChildIndex] = element;
                valuesCounter++;
                stepCounter--;
                size++;
            }
        }
        return false;
    }

    /**
     * @return the object with the highest priority and delete him
     */
    public E poll() {
        E element = null;
        if (size > 0) {
            element = (E) priorityQueue[0];
            priorityQueue[0] = priorityQueue[size - 1];
            priorityQueue[size - 1] = null;
            siftDown(0, (E) priorityQueue[0], size / 2);
            if (stepCounter == 1) {
                stepCounter++;
            } else {
                valuesCounter--;
                stepCounter--;
            }
            size--;
        }

        return element;
    }

    /**
     * gets the number of objects in the array and overrides the cell for the object by moving it up the tree
     * @param index number of objects in the array
     * @param element the object to be shifted
     */
    private void siftUp(int index, E element) {
        while (index > 0) {
            int parentIndex = (index - 1) / 2;
            if (element.compareTo((E) priorityQueue[parentIndex]) >= 0) {
                break;
            }
            priorityQueue[index] = (E) priorityQueue[parentIndex];
            priorityQueue[index = parentIndex] = (E) element;
        }
    }

    /**
     * gets the number of objects in the array and overrides the cell for the object by moving it down the tree
     * @param parentIndex parent cell index
     * @param element the object to be shifted
     * @param index number of objects in the array
     */
    private void siftDown(int parentIndex, E element, int index) {
        while (parentIndex < index - 1) {
            E leftChild = (E) priorityQueue[parentIndex * 2 + 1];
            E rightChild = (E) priorityQueue[parentIndex * 2 + 2];
            if (leftChild.compareTo(rightChild) > 0) {
                if (element.compareTo(rightChild) <= 0) {
                    break;
                }
                priorityQueue[parentIndex] = rightChild;
                priorityQueue[parentIndex = parentIndex * 2 + 2] = element;
            } else {
                if (element.compareTo(leftChild) <= 0) {
                    break;
                }
                priorityQueue[parentIndex] = leftChild;
                priorityQueue[parentIndex = parentIndex * 2 + 1] = element;
            }
        }
    }

    /**
     * @return the value of the object with the highest priority
     */
    public E peek() {
        return (E) priorityQueue[0];
    }

    /**
     * @param element specified object
     * @return index of specified object
     */
    private int indexOf(Object element) {
        if (element != null) {
            for (int i = 0; i < size; i++) {
                if (priorityQueue[i].equals(element)) {
                    return i;
                }
            }
        }
        return -1;
    }

    /**
     * @return number of objects
     */
    public int size() {
        return size;
    }

    public void clear() {
        for (int i = 0; i < size; i++)
            priorityQueue[i] = null;
        size = 0;
    }

    /**
     * @param element object to check
     * @return boolean value is there such an object in the array
     */
    public boolean contains(Object element) {
        return indexOf(element) >= 0;
    }

    /**
     * delete object from array
     * @param element object to delete
     * @return boolean value
     */
    public boolean remove(Object element) {
        int index = indexOf(element);
        if (index >= 0) {
            priorityQueue[index] = priorityQueue[size - 1];
            priorityQueue[size - 1] = null;
            siftDown(index, (E) priorityQueue[index], size / 2);
            size--;
            valuesCounter--;
            return true;
        } else {
            return false;
        }
    }

    /**
     * overwrites the array increasing the size
     * @param objects old array
     * @return new array
     */
    private Object[] getNewArray(Object[] objects) {
        Object[] Array = new Object[objects.length + DEFAULT_CAPACITY];
        System.arraycopy(objects, 0, Array, 0, size);
        return Array;
    }

    /**
     * iterator is used for loop type foreach
     *
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
                return hasNext() ? (E) priorityQueue[index++] : null;
            }
        };
    }

}
