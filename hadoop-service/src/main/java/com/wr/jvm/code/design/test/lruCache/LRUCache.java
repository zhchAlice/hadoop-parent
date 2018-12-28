package com.wr.jvm.code.design.test.lruCache;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: Alice
 * @date: 2018/12/28.
 * @since: 1.0.0
 */
public class LRUCache {
    private class Node{
        int key;
        int value;
        Node prev;
        Node next;

        public Node(int key, int value) {
            this.key = key;
            this.value = value;
            this.prev = null;
            this.next = null;
        }
    }
    private int capacity;
    private Map<Integer,Node> cache;
    private Node head;
    private Node tail;

    public LRUCache(int capacity) {
        this.capacity = capacity;
        cache = new HashMap<>();
        this.head = null;
        this.tail = null;
    }

    public int get(int key){
        Node node = cache.get(key);
        if (null== node){
            return -1;
        }
        if (node==head){
            head=node.next;
        }
        if (node!=tail) {
            node.prev.next = node.next;
            node.next.prev = node.prev;
            moveToTail(node);
        }
        return node.value;
    }

    public void put(int key,int value){
        if (-1 != this.get(key)){
            cache.get(key).value=value;
            return;
        }
        if (cache.size()==capacity){
            cache.remove(head.key);
            removeFirstNode();
        }
        Node node = new Node(key,value);
        cache.put(key,node);
        moveToTail(node);
    }


    private void moveToTail(Node node){
        if (cache.size() == 1){
            head = tail = node;
            head.next=tail;
            tail.prev=head;
        } else {
            node.prev = tail;
            node.next = tail.next;
            tail.next.prev=node;
            tail.next = node;
            tail = node;
        }
    }

    private void removeFirstNode(){
        if (head.next==head){
            head=tail=null;
        }else {
            head.next.prev=head.prev;
            head.prev.next=head.next;
            head=head.next;
        }
    }

    public static void main(String[] args) {
        LRUCache cache = new LRUCache(2);
        cache.put(2, 1);
        cache.put(3, 2);
        System.out.println(cache.get(3));      // returns 1
        System.out.println(cache.get(2));
        cache.put(4, 3);    // evicts key 2
        System.out.println(cache.get(2));
        System.out.println(cache.get(3));
        System.out.println(cache.get(4)); // returns -1 (not found)
        /*cache.put(4, 4);    // evicts key 1
        System.out.println(cache.get(1));       // returns -1 (not found)
        System.out.println(cache.get(3));       // returns 3
        System.out.println(cache.get(4));*/
    }
}
