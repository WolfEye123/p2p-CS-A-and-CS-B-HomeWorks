package com.shpp.p2p.cs.adobrovolskyi.assignment17;


import java.util.Iterator;

public class MyHashMap<K, V> implements Iterable<K> {

    /**
     * Default initial capacity.
     */
    private static final int DEFAULT_CAPACITY = 16;
    /**
     * The size of the ArrayList
     */
    private int size = 0;
    /**
     * Shared empty array instance used for empty instances.
     */
    private Node<K, V>[] nodes = new Node[DEFAULT_CAPACITY];

    /**
     * creates a node with a key and a value
     *
     * @param <K> key
     * @param <V> value
     */
    static class Node<K, V> {

        final K key;
        V value;
        Node<K, V> next;

        Node(K key, V value, Node<K, V> next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }

    }

    /**
     * find cell index for object using hash code
     *
     * @param key key
     * @return cell index
     */
    private int hashCode(K key) {
        int hash = 31;
        hash = hash * 17 + key.hashCode();
        return hash % nodes.length;
    }

    /**
     * adds the node with the key and value to the desired cell
     *
     * @param key   key
     * @param value value
     * @return boolean value
     */
    public V put(K key, V value) {
        if (key == null || value == null) {
            return null;
        }
        Node<K, V> node = new Node<>(key, value, null);
        int index = hashCode(key);
        while (size >= nodes.length * 0.75f) {
            resizeArray();
        }
        if (addObject(key, node, index)) return node.value;
        return null;
    }

    /**
     * adds the object according to the index and if the cell is busy,
     * it checks for the similarity of keys and either replaces the value or adds a new object
     *
     * @param key   key
     * @param node  node for adding
     * @param index cell index
     * @return boolean value
     */
    private boolean addObject(K key, Node<K, V> node, int index) {
        if (nodes[index] != null) {
            Node<K, V> prevNode = nodes[index];
            if (prevNode.next == null) {
                if (prevNode.key.equals(key)) {
                    prevNode.value = node.value;
                    return true;
                } else {
                    prevNode.next = node;
                    size++;
                    return true;
                }
            } else {
                while (prevNode.next != null) {      // check the specified cell
                    if (prevNode.key.equals(key)) {
                        break;
                    }
                    prevNode = prevNode.next;
                }
                if (!prevNode.key.equals(key)) {
                    prevNode.next = node;
                    size++;
                    return true;
                } else {
                    prevNode.value = node.value;
                    return true;
                }

            }
        } else if (nodes[index] == null && !containsKey(key)) {
            nodes[index] = node;
            size++;
            return true;
        }
        return false;
    }

    /**
     * @param key key
     * @return value corresponding to the key
     */
    public V get(K key) {
        if (key == null) {
            return null;
        }
        int index = hashCode(key);
        if (index < nodes.length && nodes[index] != null) {
            Node<K,V> node = nodes[index];
            if (node.next == null) {
                if (node.key.equals(key)) {
                    return node.value;
                }
            } else {
                while (node.next != null){
                    if (node.key.equals(key)) {
                        break;
                    }
                    node = node.next;
                }
                if (node.key.equals(key)){
                    return node.value;
                }
            }
        }
        return null;
    }

    /**
     * deletes an object by key
     *
     * @param key key
     * @return boolean value
     */
    public V remove(K key) {
        int index = hashCode(key);
        if (index < nodes.length && nodes[index] != null) {
            Node<K, V> node = nodes[index];
            if (node.next != null) {
                nodes[index] = node.next;
            } else {
                nodes[index] = null;
            }
            size--;
            return node.value;
        }

        return null;
    }

    /**
     * checks if there is a node with such a key
     *
     * @param key key
     * @return boolean value
     */
    public boolean containsKey(K key) {
        int index = hashCode(key);
        if (index < nodes.length && nodes[index] != null) {
            Node<K, V> node = nodes[index];
            if (node.key.equals(key)) {
                return true;
            } else {
                while (node.next != null) {
                    node = node.next;
                    if (node.key.equals(key)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * checks if there is a node with such a value
     *
     * @param value value
     * @return boolean value
     */
    public boolean containsValue(V value) {
        for (int i = 0; i < nodes.length; i++) {
            if (nodes[i] != null) {
                Node<K, V> node = nodes[i];
                if (node.value.equals(value)) {
                    return true;
                } else {
                    while (node.next != null) {
                        node = node.next;
                        if (node.value.equals(value)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * @return return array size
     */
    public int size() {
        return size;
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
        if (size > 0) {
            for (int i = 0; i < nodes.length; i++) {
                if (nodes[i] != null) {
                    nodes[i] = null;
                }
            }
            size = 0;
        }
    }

    /**
     * overwrites the array with increasing size and recalculation of indexes for nodes
     */
    private void resizeArray() {
        Node<K, V>[] newNodesArray = nodes;
        nodes = new Node[nodes.length * 2];
        cellIndexRecalculate(newNodesArray);
    }

    /**
     * recalculation of indexes for nodes
     *
     * @param newNodesArray node array
     */
    private void cellIndexRecalculate(Node<K, V>[] newNodesArray) {
        size = 0;
        for (int i = 0; i < newNodesArray.length; i++) {
            if (newNodesArray[i] != null) {
                Node<K,V> node = newNodesArray[i];
                if (newNodesArray[i].next == null) {
                    nodes[hashCode(node.key)] = node;
                    size++;
                } else {
                    while (node.next != null){
                        nodes[hashCode(node.key)] = node;
                        size++;
                        node = node.next;
                    }
                    nodes[hashCode(node.key)] = node;
                    size++;
                }
            }
        }
    }


    /**
     * iterator is used for loop type foreach
     *
     * @return returns the object found in the array
     */
    public Iterator<K> iterator() {
        return new Iterator<K>() {
            int counter = 0;
            int sizeCounter = 0;
            K[] keyArray = (K[]) new Object[size];

            @Override
            public boolean hasNext() {
                return sizeCounter < size;
            }

            @Override
            public K next() {
                if (counter > 0 && sizeCounter > 0) {
                    sizeCounter++;
                    return keyArray[counter++];
                } else {
                    for (int i = 0, j = 0; i < nodes.length; i++) {
                        if (nodes[i] != null) {
                            Node<K,V> node = nodes[i];
                            if (node.next == null && node.key != null) {
                                keyArray[j] = node.key;
                                j++;
                            } else {
                                while(node.next!=null){
                                    keyArray[j] = node.key;
                                    node = node.next;
                                }
                                keyArray[j] = node.key;
                                j++;
                            }
                        }
                    }
                    sizeCounter++;
                    return keyArray[counter++];
                }
            }
        };
    }
}
