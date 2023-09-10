package com.algorithms.leetcode.easy;

import java.util.HashMap;
import java.util.Map;

public class DegreeOfAnArray {
    /**
     * Given a non-empty array of non-negative integers nums, the degree of this array is defined as the
     * maximum frequency of any one of its elements.
     * <p>
     * An array that has degree d, must have some element x occur d times. If some subarray has the same degree,
     * then some element x (that occurred d times), still occurs d times. The shortest such subarray would be
     * from the first occurrence of x until the last occurrence.
     * <p>
     * Your task is to find the smallest possible length of a (contiguous) subarray of nums, that has the same degree as nums.
     * <p>
     * https://leetcode.com/problems/degree-of-an-array/
     */
    public int findShortestSubArray(int[] nums) {
        int degree = 0;
        int minLength = 0;
        Map<Integer, Integer> numCountsMap = new HashMap<>();
        Map<Integer, Integer> firstSeenMap = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            int num = nums[i];
            firstSeenMap.putIfAbsent(num, i);
            numCountsMap.put(num, numCountsMap.getOrDefault(num, 0) + 1);

            int numCount = numCountsMap.get(num);
            int length = i - firstSeenMap.get(num) + 1;
            if (numCount > degree) {
                degree = numCount;
                minLength = length;
            } else if (numCount == degree) {
                minLength = Math.min(minLength, length);
            }
        }
        return minLength;
    }
}
