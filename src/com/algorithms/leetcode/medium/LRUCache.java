package com.algorithms.leetcode.medium;

import java.util.HashMap;
import java.util.Map;

/**
 * Design and implement a data structure for Least Recently Used (LRU) cache.
 * It should support the following operations: get and put.
 * <p>
 * get(key) - Get the value (will always be positive) of the key if the key exists in the cache, otherwise return -1.
 * put(key, value) - Set or insert the value if the key is not already present.
 * When the cache reached its capacity, it should invalidate the least recently used item before inserting a new item.
 * <p>
 * The cache is initialized with a positive capacity.
 * <p>
 * Follow up:
 * Could you do both operations in O(1) time complexity?
 * <p>
 * https://leetcode.com/problems/lru-cache/
 */
public class LRUCache {
    private final Node head;
    private final Node tail;
    private final int capacity;
    private final Map<Integer, Node> cache;

    public LRUCache(int capacity) {
        this.capacity = capacity;
        this.cache = new HashMap<>(capacity);
        this.head = new Node();
        this.tail = new Node();

        this.head.next = tail;
        this.tail.prev = head;
    }

    public int get(int key) {
        int result = -1;
        Node node = cache.get(key);
        if (node != null) {
            result = node.val;
            remove(node);
            add(node);
        }
        return result;
    }

    public void put(int key, int value) {
        Node node = cache.get(key);
        if (node != null) {
            remove(node);
            node.val = value;
            add(node);
        } else {
            if (cache.size() == capacity) {
                cache.remove(tail.prev.key);
                remove(tail.prev);
            }
            Node newNode = new Node();
            newNode.key = key;
            newNode.val = value;
            cache.put(key, newNode);
            add(newNode);
        }
    }

    private void add(Node node) {
        node.prev = head;
        node.next = head.next;
        head.next.prev = node;
        head.next = node;
    }

    private void remove(Node node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }

    class Node {
        int key;
        int val;
        Node next;
        Node prev;
    }
}
