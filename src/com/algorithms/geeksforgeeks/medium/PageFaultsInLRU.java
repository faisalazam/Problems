package com.algorithms.geeksforgeeks.medium;

import java.util.LinkedList;
import java.util.Queue;

public class PageFaultsInLRU {
    /**
     * In operating systems that use paging for memory management, page replacement algorithm are needed to decide
     * which page needs to be replaced when the new page comes in. Whenever a new page is referred
     * and is not present in memory, the page fault occurs and Operating System replaces one of the existing pages
     * with a newly needed page. Given a sequence of pages and memory capacity, your task is to find the
     * number of page faults using Least Recently Used (LRU) Algorithm.
     * <p>
     * https://practice.geeksforgeeks.org/problems/page-faults-in-lru/0
     */
    private static int countPageFaults(int[] pages, int N, int capacity) {
        int pageFaults = 0;
        Queue<Integer> pageMemory = new LinkedList<>();

        for (int i = 0; i < N; i++) {
            int page = pages[i];
            if (!pageMemory.contains(page)) {
                pageFaults++;
                if (pageMemory.size() == capacity) {
                    pageMemory.poll();
                }
            } else {
                pageMemory.remove(page);
            }
            pageMemory.offer(page);
        }
        return pageFaults;
    }
}
