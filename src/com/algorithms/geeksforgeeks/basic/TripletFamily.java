package com.algorithms.geeksforgeeks.basic;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TripletFamily {
    /**
     * Given an array A of integers. Find three numbers such that sum of two elements equals the third element
     * and return the triplet in a container result, if no such triplet is found return the container as empty.
     * <p>
     * https://practice.geeksforgeeks.org/problems/triplet-family/1
     */
    public static List<Integer> findTriplet(int[] arr, int n) {
        Set<Integer> nums = new HashSet<>();
        for (int i = 0; i < n; i++) {
            nums.add(arr[i]);
        }

        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) { // may j should be j = i + 1
                int sum = arr[i] + arr[j];
                if (i != j && nums.contains(sum)) {
                    result.add(arr[i]);
                    result.add(arr[j]);
                    result.add(sum);
                    return result;
                }
            }
        }
        return result;
    }
}
