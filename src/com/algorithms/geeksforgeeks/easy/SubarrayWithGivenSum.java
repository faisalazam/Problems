package com.algorithms.geeksforgeeks.easy;

import java.util.ArrayList;
import java.util.List;

public class SubarrayWithGivenSum {
    /**
     * Given an unsorted array A of size N that contains only positive integers, find a continuous sub-array that adds
     * to a given number S and return the left and right index(1-based indexing) of that subarray.
     * <p>
     * In case of multiple subarrays, return the subarray indexes which come first on moving from left to right.
     * <p>
     * Note:- You have to return an ArrayList consisting of two elements left and right. In case no such subarray
     * exists return an array consisting of only one element that is -1.
     * <p>
     * https://practice.geeksforgeeks.org/problems/subarray-with-given-sum/0
     * <p>
     * Sliding Window
     * <p>
     * Time Complexity: O(N)
     * Auxiliary Space: O(1). Since no extra space has been taken.
     */
    List<Integer> subArrayWithGivenSum(int[] arr, int n, int s) {
        final ArrayList<Integer> result = new ArrayList<>();
        if (s == 0) {
            result.add(-1);
            return result;
        }

        int sum = 0;
        int start = 0;
        for (int i = 0; i < n; i++) {
            sum += arr[i];
            while (sum > s) {
                sum -= arr[start++];
            }
            if (sum == s) {
                result.add(start + 1);
                result.add(i + 1);
                return result;
            }
        }
        result.add(-1);
        return result;
    }
}
