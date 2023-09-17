package com.algorithms.leetcode.easy;

/**
 * Given an integer array nums, you need to find one continuous subarray such that if you only sort this subarray
 * in non-decreasing order, then the whole array will be sorted in non-decreasing order.
 * <p>
 * Return the shortest such subarray and output its length.
 * <p>
 * Example 1:
 * Input: nums = [2,6,4,8,10,9,15]
 * Output: 5
 * Explanation: You need to sort [6, 4, 8, 10, 9] in ascending order to make the whole array sorted in ascending order.
 * <p>
 * Example 2:
 * Input: nums = [1,2,3,4]
 * Output: 0
 * <p>
 * Example 3:
 * Input: nums = [1]
 * Output: 0
 * <p>
 * https://leetcode.com/problems/shortest-unsorted-continuous-subarray/
 */
public class ShortestUnsortedContinuousSubarray {
    public int findUnsortedSubarray(int[] nums) {
        int left = findLeftBoundary(nums);
        int right = findRightBoundary(nums);
        return right - left < 0 ? 0 : right - left + 1;
    }

    private int findLeftBoundary(int[] nums) {
        boolean unsorted = false;
        int min = Integer.MAX_VALUE;
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] < nums[i - 1]) {
                unsorted = true;
            }
            if (unsorted) {
                min = Math.min(min, nums[i]);
            }
        }
        int left;
        for (left = 0; left < nums.length; left++) {
            if (min < nums[left]) {
                break;
            }
        }
        return left;
    }

    private int findRightBoundary(int[] nums) {
        boolean unsorted = false;
        int max = Integer.MIN_VALUE;
        for (int i = nums.length - 2; i >= 0; i--) {
            if (nums[i] > nums[i + 1]) {
                unsorted = true;
            }
            if (unsorted) {
                max = Math.max(max, nums[i]);
            }
        }
        int right;
        for (right = nums.length - 1; right >= 0; right--) {
            if (max > nums[right]) {
                break;
            }
        }
        return right;
    }
}
