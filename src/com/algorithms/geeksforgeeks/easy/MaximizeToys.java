package com.algorithms.geeksforgeeks.easy;

import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Queue;

public class MaximizeToys {
    /**
     * Given an array consisting cost of toys. Given an integer K depicting the amount with you.
     * Maximise the number of toys you can have with K amount.
     * <p>
     * https://practice.geeksforgeeks.org/problems/maximize-toys/0
     * <p>
     * Time Complexity: O(N * logN), where N is the size of the cost array.
     * Auxiliary Space: O(1) as it is using constant space for variables
     */
    private static int maxToys(int[] A, int N, int K) {
        int maxToys = 0;
        int spentAmount = 0;
        Arrays.sort(A);
        for (int i = 0; i < N; i++) {
            spentAmount += A[i];
            if (spentAmount <= K) {
                maxToys++;
            } else {
                break;
            }
        }
        return maxToys;
    }

    /**
     * Time Complexity: O(N*logN)
     * Auxiliary Space: O(N)
     */
    public static int maxToysV0(int[] A, int N, int K) {
        // Create a priority_queue and push all the array elements in it
        // Implementation note: this implementation provides
        // * O(log(n)) time for the enqueuing and dequeue-ing methods
        // * ({@code offer}, {@code poll}, {@code remove()} and {@code add});
        // * linear time for the {@code remove(Object)} and {@code contains(Object)}
        // * methods; and constant time for the retrieval methods
        // * ({@code peek}, {@code element}, and {@code size}).
        final Queue<Integer> priorityQueue = new PriorityQueue<>();
        for (int i = 0; i < N; i++) {
            priorityQueue.offer(A[i]); // O(logN)
        }

        int count = 0;
        while (!priorityQueue.isEmpty() && priorityQueue.peek() <= K) {
            K = K - priorityQueue.poll();
            count++;
        }
        return count;
    }
}
