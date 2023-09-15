package com.algorithms.geeksforgeeks.medium;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

public class PageFaultsInLRU {
    /**
     * In operating systems that use paging for memory management, page replacement algorithm are needed to decide
     * which page needs to be replaced when the new page comes in. Whenever a new page is referred
     * and is not present in memory, the page fault occurs and Operating System replaces one of the existing pages
     * with a newly needed page. Given a sequence of pages and memory capacity, your task is to find the
     * number of page faults using Least Recently Used (LRU) Algorithm.
     * <p>
     * https://practice.geeksforgeeks.org/problems/page-faults-in-lru/0
     * <p>
     * Time Complexity: O(N*C)
     * Auxiliary Space:  O(C)
     */
    private static int countPageFaults(int N, int C, int[] pages) {
        int pageFaults = 0;
        final Queue<Integer> pageMemory = new LinkedList<>();

        for (int i = 0; i < N; i++) {
            int page = pages[i];
            if (!pageMemory.contains(page)) {
                pageFaults++;
                if (pageMemory.size() == C) {
                    pageMemory.poll();
                }
            } else {
                pageMemory.remove(page);
            }
            pageMemory.offer(page);
        }
        return pageFaults;
    }

    /**
     * Is it better to use ArrayDeque here?
     */
    private static int countPageFaultsV0(int N, int C, int[] pages) {
        int pageFaults = 0;
        final Set<Integer> cacheKeys = new HashSet<>(C);
        // The name deque is short for "double ended queue" and is usually pronounced "deck".
        // {@code LinkedList} is the Doubly-linked list implementation of the {@code List} and {@code Deque} interfaces.
        // If all you care about is the end-to-end efficiency of the level-order traversal, not the cost of any
        // individual addition or removal of elements from the queue, it will probably be faster to use an ArrayDeque
        // than a LinkedList. In a sense, the LinkedList type is generally only better if you need to have good
        // worst-case performance on each operation performed, and that isn't the case here. The ArrayDeque is generally
        // more performant when all you care about is end-to-end runtime.
//        final Deque<Integer> cache = new LinkedList<>();
        final Deque<Integer> cache = new ArrayDeque<>(C);
        for (int page : pages) { // O(N)
            if (!cacheKeys.contains(page)) { // O(1)
                if (cache.size() == C) { // O(1)
                    final int last = cache.removeLast(); // O(1)
                    cacheKeys.remove(last); // O(1)
                }
                pageFaults++;
            } else {
                cache.remove(page); // O(C) or O(1)+ (amortized time notation) - amortized constant time ?
            }
            cacheKeys.add(page); // O(1)
            cache.addFirst(page); // O(1)
        }
        return pageFaults;
    }

    /*
     * Approach using Doubly Linked List and Maps
     * We can use an unordered map and a doubly linked list to solve this problem efficiently. This is done by
     * maintaining a map of nodes in memory. For each recently used node we can push it at the back of our doubly
     * linked list, it consumes O(1) time. Searching in the map also takes O(1) time (NO HASH COLLISIONS ASSUMED).
     * When we hit full capacity in memory, shift the head of the linked list and erase its occurrence from the map.
     * This also can be done in O(1) time. Thus, giving the algorithm a runtime of O(N) in the worst case.
     */
}
