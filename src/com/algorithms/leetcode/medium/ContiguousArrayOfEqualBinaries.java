package com.algorithms.leetcode.medium;

import java.util.HashMap;
import java.util.Map;

public class ContiguousArrayOfEqualBinaries {

    /*
     * Given a binary array, find the maximum length of a contiguous subarray with equal number of 0 and 1.
     *
     * https://leetcode.com/problems/contiguous-array/
     *
     *
     * Algorithm:
     *
     * In this approach, we make use of a count variable,
     * which is used to store the relative number of ones and zeros encountered so far while traversing the array.
     * The count variable is incremented by one for every 1 encountered and the same is decremented by one
     * for every 0 encountered. We start traversing the array from the beginning.
     * If at any moment, the count becomes zero, it implies that we've encountered equal number of zeros and ones
     * from the beginning till the current index of the array(i). Not only this, another point to be noted is that
     * if we encounter the same count twice while traversing the array, it means that the number of zeros and ones
     * are equal between the indices corresponding to the equal count values.
     */
    public int findMaxLength(int[] nums) {
        int maxLength = 0;
        Map<Integer, Integer> countsMap = new HashMap<>();
        countsMap.put(0, -1);

        int count = 0;
        for (int currentIndex = 0; currentIndex < nums.length; currentIndex++) {
            count += nums[currentIndex] == 0 ? -1 : 1;
            if (countsMap.containsKey(count)) {
                int previousIndex = countsMap.get(count);
                int lengthOfContinuousBinaryArray = currentIndex - previousIndex;
                maxLength = Math.max(maxLength, lengthOfContinuousBinaryArray);
            } else {
                countsMap.put(count, currentIndex);
            }
        }
        return maxLength;
    }

    /**
     * Complexity Analysis
     * Time complexity : O(n^2). We consider every possible subarray by traversing over the complete array for every start point possible.
     * Space complexity : O(1). Only two variables zeroes and ones are required.
     */
    public int findMaxLengthV1(int[] nums) {
        int maxlen = 0;
        for (int start = 0; start < nums.length; start++) {
            int zeroes = 0, ones = 0;
            for (int end = start; end < nums.length; end++) {
                if (nums[end] == 0) {
                    zeroes++;
                } else {
                    ones++;
                }
                if (zeroes == ones) {
                    maxlen = Math.max(maxlen, end - start + 1);
                }
            }
        }
        return maxlen;
    }
}
