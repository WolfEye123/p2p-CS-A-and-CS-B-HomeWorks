package com.shpp.p2p.cs.adobrovolskyi.assignment16;

import java.util.Iterator;


/**
 * In this class, a collection is written on its own that should work as a standard ArrayList.
 * @param <E> generic which indicates input data type
 */
public class MyArrayList<E> implements Iterable<E>{


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
    private E[] arrayList = (E[]) new Object[DEFAULT_CAPACITY];


    /**
     * method adds the specified object to the array
     * @param element specified object
     * @return a boolean variable that shows whether the object is added or not
     */
    public boolean add(E element) {
        if (size + 1 >= arrayList.length) {
            arrayList = getNewArray(arrayList);
        }
        for (int i = size; i < arrayList.length; i++) {
            if (arrayList[i] == null) {
                arrayList[i] = element;
                size++;
                return true;
            }
        }
        return false;
    }

    /**
     * method adds the specified object to the array with index of the cell
     * @param index the index of the cell of the array in which you want to add an object
     * @param element specified object
     * @return a boolean variable that shows whether the object is added or not
     */
    public boolean add(int index, E element) {
        indexOutOfBoundsException(index);
        if (size <= index) {
            arrayList = getNewArrayWithIndex(index, arrayList);
            arrayList[index] = element;
            size++;
            return true;
        } else {
            System.arraycopy(arrayList, index, arrayList, index + 1, size);
            arrayList[index] = element;
            size++;
        }
        return true;
    }

    /**
     * the method overwrites the array with the offset at the specified index and increases the size of the array
     * @param index the index of the cell of the array in which you want to add an object
     * @param objects array of objects
     * @return rewritten array
     */
    private E[] getNewArrayWithIndex(int index, E[] objects) {
        E[] Array = (E[]) new Object[objects.length + DEFAULT_CAPACITY];
        System.arraycopy(objects, index, Array, index + 1, objects.length);
        return Array;
    }

    /**
     * method overwrites an array increasing its size
     * @param objects array of objects
     * @return rewritten array
     */
    private E[] getNewArray(E[] objects) {
        E[] Array = (E[]) new Object[objects.length + DEFAULT_CAPACITY];
        System.arraycopy(objects, 0, Array, 0, objects.length);
        return Array;
    }

    /**
     * @param index the index of the cell of the array in which you want to get
     * @return the method returns the object with the specified index
     */
    public E get(int index) {
        indexOutOfBoundsException(index);
        return (E)arrayList[index];
    }

    /**
     * the method replaces the object in the specified cell with the specified object
     * @param index the index of the cell of the array in which you want to set
     * @param element specified object
     * @return the method returns the object that was changed
     */
    public E set(int index, E element) {
        indexOutOfBoundsException(index);
        E oldElement = null;
        if (index < size) {
            oldElement = arrayList[index];
            arrayList[index] = element;
        }
        return oldElement;
    }

    /**
     * gives an error when going outside the array
     * @param index the index of the cell of the array
     */
    private void indexOutOfBoundsException(int index) {
        if (index < 0 || index >= size) {
            new IndexOutOfBoundsException();
        }
    }

    /**
     * @return return array size
     */
    public int size() {
        return size;
    }

    /**
     * the method deletes an object in the specified cell
     * @param index the index of the cell of the array in which you want to remove
     * @return object that was changed
     */
    public E remove(int index) {
        indexOutOfBoundsException(index);
        E oldElement = null;
        if (index < size) {
            oldElement = (E) arrayList[index];
            System.arraycopy(arrayList, 0, arrayList, 0, index);
            System.arraycopy(arrayList, index + 1, arrayList, index, size - index - 1);
            arrayList[size - 1] = null;
            size--;
        }
        return oldElement;
    }

    /**
     * the method deletes an object
     * @param o specified object
     */
    public void remove(Object o) {
        if (o != null) {
            for (int i = 0; i < size; i++) {
                if (arrayList[i].equals(o)) {
                    System.arraycopy(arrayList, i + 1, arrayList, i, size - 1);
                    size--;
                    break;
                }
            }
        }
    }

    /**
     * @return checks if the array is empty
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * the method removes all elements from the array
     */
    public void clear() {
        for (int i = 0; i < size; i++) {
            arrayList[i] = null;
        }
        size = 0;
    }

    /**
     * @param o specified object
     * @return index of specified object
     */
    public int indexOf(Object o) {
        for (int i = 0; i < size; i++) {
            if (arrayList[i].equals(o)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * @param o specified object
     * @return checks if there is such an object in the array
     */
    public boolean contains(Object o) {
        return indexOf(o) >= 0;
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
                return hasNext() ? (E)arrayList[index++] : null;
            }
        };
    }
}
