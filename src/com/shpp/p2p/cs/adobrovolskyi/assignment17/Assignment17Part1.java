package com.shpp.p2p.cs.adobrovolskyi.assignment17;

import com.shpp.p2p.cs.vchernomorets.assignment16.MyArrayList;

import java.util.HashMap;
import java.util.PriorityQueue;

public class Assignment17Part1 {
    public static void main(String[] args) {
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>();
        MyPriorityQueue<Integer> myPriorityQueue = new MyPriorityQueue<>();
//        test.Person man = new test.Person("Tom",20);
//        test.Person man1 = new test.Person("Anton",19);
//        test.Person man2 = new test.Person("Antony",25);
        for (int i = 9; i >= 0; i--) {
            myPriorityQueue.add(i);
            priorityQueue.add(i);
        }
        for (int a: priorityQueue) {
            System.out.println(a);
        }
        for (int a: myPriorityQueue) {
            System.out.println(a);
        }
        for (int i = 1; i < 6; i += 2) {
            System.out.println(priorityQueue.remove(i));
            System.out.println(myPriorityQueue.remove(i));
        }
        while (priorityQueue.size() > 0) {
            System.out.println(priorityQueue.poll());
            System.out.println(myPriorityQueue.poll());

//        }
//        System.out.println("s");
//        myPriorityQueue.add(man);
//        myPriorityQueue.add(man1);
//        myPriorityQueue.add(man2);
//        priorityQueue.add(man);
//        priorityQueue.add(man1);
//        priorityQueue.add(man2);

//        HashMap<Integer, String> hashMap = new HashMap<>();
//        MyHashMap<Integer, String> myHashMap = new MyHashMap<>();
//        hashMap.put(0,"asd");
//        hashMap.put(1,"qwe");
//        hashMap.put(2,"zxc");
//        myHashMap.put(0,"asd");
//        myHashMap.put(1,"qwe");
//        myHashMap.put(2,"zxc");
//        for (int i = 0; i < 200; i++) {
//            hashMap.put(i, i + " " + i);
//            myHashMap.put(i, i + " " + i);
//        }
//        System.out.println(myHashMap.size());
//        System.out.println(myHashMap.get(17));
//        System.out.println(myHashMap.get(18));
//        System.out.println(myHashMap.get(19));
//        System.out.println(myHashMap.get(16));
//        System.out.println(myHashMap.containsKey(16));
//        System.out.println(myHashMap.remove(16));
//        System.out.println(myHashMap.containsKey(16));

//        System.out.println(myHashMap.put(16,"00 00"));
//        System.out.println(myHashMap.containsValue("16 16"));
//        System.out.println(myHashMap.remove(16));
//        System.out.println(myHashMap.containsValue("16 16"));
//        myHashMap.put(0," ");
//        System.out.println("a");
//        for (int a : myHashMap) {
//            System.out.println(a);
//        }
//        for (int a : hashMap.keySet()) {
//            System.out.println(a);
//        }

//        hashMap.put(1,str);
//        hashMap.put(2,str1);
//        hashMap.put(1,"qwe");
//        System.out.println(hashMap.get(1));
//        hashMap.get(1);


    }}
}
