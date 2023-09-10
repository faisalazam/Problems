package com.algorithms.geeksforgeeks.easy;

import java.util.HashSet;
import java.util.Set;

public class CountTheTriplets {
    /**
     * Given an array of distinct integers. The task is to count all the triplets
     * such that sum of two elements equals the third element.
     * <p>
     * https://practice.geeksforgeeks.org/problems/count-the-triplets/0
     */
    private static int countTriplets(int[] A, int N) {
        int count = 0;
        Set<Integer> nums = new HashSet<>();
        for (int i = 0; i < N; i++) {
            nums.add(A[i]);
        }
        for (int i = 0; i < N; i++) {
            for (int j = i + 1; j < N; j++) {
                int sum = A[i] + A[j];
                if (nums.contains(sum)) {
                    count++;
                }
            }
        }
        return count == 0 ? -1 : count;
    }
}
