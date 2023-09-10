package com.algorithms.leetcode.easy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MinimumAbsoluteDifference {
    /**
     * Given an array of distinct integers arr, find all pairs of elements with the
     * minimum absolute difference of any two elements.
     * <p>
     * https://leetcode.com/problems/minimum-absolute-difference/
     */
    public List<List<Integer>> minimumAbsDifference(int[] arr) {

        Arrays.sort(arr);

        int minDifference = getMinDifference(arr);

        List<List<Integer>> results = new ArrayList<>();
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] - arr[i - 1] == minDifference) {
                List<Integer> result = new ArrayList<>();
                result.add(arr[i - 1]);
                result.add(arr[i]);
                results.add(result);
            }
        }
        return results;
    }

    private int getMinDifference(int[] arr) {
        int minDifference = Integer.MAX_VALUE;
        for (int i = 1; i < arr.length; i++) {
            minDifference = Math.min(minDifference, arr[i] - arr[i - 1]);
        }
        return minDifference;
    }
}
