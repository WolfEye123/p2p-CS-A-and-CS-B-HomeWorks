package com.shpp.p2p.cs.adobrovolskyi.assignment16;

import java.util.Iterator;

/**
 * In this class, the collection is written by itself, which should work as a standard Linked List.
 * @param <E> generic which indicates input data type
 */
public class MyLinkedList<E> implements Iterable<E> {

    /**
     * The size of the LinkedList
     */
    private int size = 0;
    /**
     * linked list starting at the end
     */
    private Node<E> last;
    /**
     * linked list starting at the beginning
     */
    private Node<E> first;

    /**
     * class that creates nodes with links to the previous and next list element and the field of the object
     * @param <E> generic which indicates input data type
     */
    class Node<E> {
        E item;
        Node<E> next;
        Node<E> prev;

        Node(Node<E> prev, E element, Node<E> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }

    /**
     * adds an object to the end of the list
     * @param element specified object
     */
    public void add(E element) {
        addLast(element);
    }

    /**
     * adds an object to the end of the list
     * @param element specified object
     */
    public void addLast(E element) {
        final Node<E> node = new Node<>(last, element, null);
        if (size == 0) {
            last = node;
            first = node;
        } else {
            last.next = node;
            last = node;
        }
        size++;
    }

    /**
     * adds an object to the beginning of the list
     * @param element specified object
     */
    public void addFirst(E element) {
        final Node<E> node = new Node<>(null, element, first);
        if (size == 0) {
            last = node;
            first = node;
        } else {
            first.prev = node;
            first = node;
        }
        size++;
    }

    /**
     * method adds the specified object to the array with index of the cell
     * @param index the index of the cell of the array in which you want to add an object
     * @param element specified object
     */
    public void add(int index, E element) {
        Node<E> tmp = first;
        final Node<E> node = new Node<>(null, element, null);
        for (int i = 0; i < size; i++) {
            if (index == i) {
                node.prev = tmp.prev;
                node.next = tmp;
                tmp.prev.next = node;
                tmp.prev = node;
            }
            tmp = tmp.next;
        }
        size++;
    }

    /**
     * @return return array size
     */
    public int size() {
        return size;
    }

    /**
     * the method removes all elements from the array
     */
    public void clear() {
        last = first = null;
        size = 0;
    }

    /**
     * @param index the index of the cell of the array in which you want to get
     * @return the method returns the object with the specified index
     */
    public E get(int index) {
        indexOutOfBoundsException(index);
        return (E) findsElementByIndex(index).item;
    }

    /**
     * @return last element from LinkedList
     */
    public E getLast() {
        return last.item;
    }

    /**
     * @return first element from LinkedList
     */
    public E getFirst() {
        return first.item;
    }

    /**
     * @param o specified object
     * @return index of specified object in the LinkedList
     */
    public int indexOf(Object o) {
        Node<E> tmp = first;
        for (int i = 0; i < size; i++) {
            if (o.equals(tmp.item)) {
                return i;
            }
            tmp = tmp.next;
        }
        return -1;
    }

    /**
     * @param index the index of the cell of the array in which you want to get
     * @return specified object
     */
    private Node<E> findsElementByIndex(int index) {
        indexOutOfBoundsException(index);
        Node<E> tmp = first;
        for (int i = 0; i < size; i++) {
            if (index == 0) {
                return tmp;
            }
            if (index == i) {
                break;
            }
            tmp = tmp.next;
        }
        return tmp;
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
     * @param o specified object
     * @return checks if there is such an object in the array
     */
    public boolean contains(Object o) {
        Node<E> tmp = first;
        for (int i = 0; i < size; i++) {
            if (o.equals(tmp.item)) {
                return true;
            }
            tmp = tmp.next;
        }
        return false;
    }

    /**
     * delete first element from LinkedList
     * @return object that was deleted
     */
    public E removeFirst() {
        E element = first.item;
        first = first.next;
        removeNode(first.prev);
        first.prev = null;
        size--;
        return element;
    }

    /**
     * delete all links of this node
     * @param node node with links and object
     */
    private void removeNode(Node<E> node) {
        node.item = null;
        node.next = null;
        node.prev = null;
    }

    /**
     * delete last element from LinkedList
     * @return object that was deleted
     */
    public E removeLast() {
        E element = last.item;
        last = last.prev;
        removeNode(last.next);
        last.next = null;
        size--;
        return element;
    }

    /**
     * the method deletes an object in the specified cell
     * @param index the index of the cell of the array in which you want to remove
     * @return object that was deleted
     */
    public E remove(int index) {
        Node<E> tmp = findsElementByIndex(index);
        E element = null;
        if (index == 0) {
            element = (E)tmp.item;
            removeFirst();
            return element;
        } else if (index == size - 1) {
            element = (E)tmp.item;
            removeLast();
            return element;
        } else if (index > 0 && index < size - 1) {
            element = (E) tmp.item;
            tmp.prev.next = tmp.next;
            tmp.next.prev = tmp.prev;
            return element;
        }
        size--;
        return element;
    }

    /**
     * @return checks if the array is empty
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * iterator is used for loop type foreach
     * @return returns the object found in the array
     */
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            Node<E> node = first;
            int counter = 0;

            @Override
            public boolean hasNext() {
                return counter < size;
            }

            @Override
            public E next() {
                E item = null;
                if (node.item != null) {
                    item = (E) node.item;
                    counter++;
                }
                node = node.next;
                return item;
            }
        };
    }

}
