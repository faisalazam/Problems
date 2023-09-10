package com.algorithms.leetcode.easy;

public class LongestContinuousIncreasingSubsequence {
    /**
     * Given an unsorted array of integers, find the length of longest continuous increasing subsequence (subarray).
     * <p>
     * https://leetcode.com/problems/longest-continuous-increasing-subsequence/
     */
    public int findLengthOfLCIS(int[] nums) {
        int start = 0;
        int result = 0;
        for (int i = 0; i < nums.length; i++) {
            if (i > 0 && nums[i - 1] >= nums[i]) {
                start = i;
            }
            result = Math.max(result, i - start + 1); // +1 to tackle the 0 based indexing
        }
        return result;
    }
}
