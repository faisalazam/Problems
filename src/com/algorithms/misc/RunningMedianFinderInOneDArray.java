package com.algorithms.misc;

import java.util.Collections;
import java.util.PriorityQueue;
import java.util.Queue;

public class RunningMedianFinderInOneDArray {
    public static void main(String[] args) {
        int[] nums = {3, 4, 1, 8, 5, 2, 9, 11};

        double[] findMedian = findMedians(nums);
        for (int i = 0; i < findMedian.length; i++) {
            System.out.println("Median " + i + " => " + findMedian[i]);
        }
    }

    private static double[] findMedians(int[] nums) {
        if (nums == null || nums.length == 0) {
            return new double[]{-1};
        }
        double[] medians = new double[nums.length];
        Queue<Integer> highers = new PriorityQueue<>(); //containing higher nums => min-heap => min value at top
        Queue<Integer> lowers = new PriorityQueue<>(Collections.reverseOrder()); //containing lower nums => max-heap => max value at top
        for (int i = 0; i < nums.length; i++) {
            addNumber(nums[i], lowers, highers);
            rebalance(lowers, highers);
            medians[i] = getMedian(lowers, highers);
        }
        return medians;
    }

    //PriorityQueue peek and offer methods => O(log(n))
    private static void addNumber(int num, Queue<Integer> lowers, Queue<Integer> highers) {
        Queue<Integer> queue = (lowers.isEmpty() || num < lowers.peek()) ? lowers : highers;
        queue.offer(num);
    }

    private static void rebalance(Queue<Integer> lowers, Queue<Integer> highers) {
        Queue<Integer> biggerHeap = lowers.size() > highers.size() ? lowers : highers;
        Queue<Integer> smallerHeap = lowers.size() > highers.size() ? highers : lowers;
        if (biggerHeap.size() - smallerHeap.size() >= 2) {
            smallerHeap.offer(biggerHeap.poll());
        }
    }

    private static double getMedian(Queue<Integer> lowers, Queue<Integer> highers) {
        Queue<Integer> biggerHeap = lowers.size() > highers.size() ? lowers : highers;
        Queue<Integer> smallerHeap = lowers.size() > highers.size() ? highers : lowers;
        if (biggerHeap.size() == smallerHeap.size()) {
            return ((double) biggerHeap.peek() + smallerHeap.peek()) / 2; // note casting to double
        } else {
            return biggerHeap.peek();
        }
    }
}
