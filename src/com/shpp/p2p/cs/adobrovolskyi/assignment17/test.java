package com.shpp.p2p.cs.adobrovolskyi.assignment17;

import java.util.HashMap;

public class test {
    public static void main(String[] args) {

        MyHashMap<Integer, Integer> myHashMap = new MyHashMap<>();
        HashMap<Integer, Integer> hashMap = new HashMap<>();
        MyHashMap<Integer, Integer> myHashMap1 = new MyHashMap<>();
        HashMap<Integer, Integer> hashMap1 = new HashMap<>();
        for (int i = 0; i < 200; i++) {
            myHashMap.put(i, i);
            hashMap.put(i, i);
        }
        for (int i = 0; i < 200; i++) {
            myHashMap1.put(myHashMap.get(i), myHashMap.get(i));
            hashMap1.put(i, i);
        }
    }
}
