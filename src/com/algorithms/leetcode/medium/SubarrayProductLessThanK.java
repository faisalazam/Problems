package com.algorithms.leetcode.medium;

public class SubarrayProductLessThanK {
    /**
     * You are given an array of positive integers nums.
     * <p>
     * Count and print the number of (contiguous) subarrays where the product of all the elements in the subarray is less than k.
     * <p>
     * Complexity Analysis
     * Time Complexity: O(N), where N is the length of nums. left can only be incremented at most N times.
     * Space Complexity: O(1), the space used by prod, left, and ans.
     * <p>
     * https://leetcode.com/problems/subarray-product-less-than-k/
     */
    public int numSubarrayProductLessThanK(int[] nums, int k) {
        int result = 0;
        if (k <= 1) {
            return result;
        }
        int left = 0;
        int right = 0;
        int product = 1;
        while (right < nums.length) {
            product *= nums[right];

            while (product >= k) {
                product /= nums[left++];
            }
            result += right - left + 1;
            right++;
        }
        return result;
    }
}
