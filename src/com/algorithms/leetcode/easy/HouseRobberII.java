package com.algorithms.leetcode.easy;

/**
 * You are a professional robber planning to rob houses along a street. Each house has a certain amount of money stashed.
 * All houses at this place are arranged in a circle. That means the first house is the neighbor of the last one.
 * Meanwhile, adjacent houses have a security system connected, and it will automatically contact the police if two
 * adjacent houses were broken into on the same night.
 * <p>
 * Given an integer array nums representing the amount of money of each house, return the maximum amount of money you
 * can rob tonight without alerting the police.
 * <p>
 * https://leetcode.com/problems/house-robber/
 */
public class HouseRobberII {
    /**
     * The solution is basically the same as the solution in {@link HouseRobber}.
     * It's just that we calculate the robbed amount twice including/excluding the first/last house to break the circle,
     * and then take the max of them.
     */
    public int rob(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        } else if (nums.length == 1) {
            return nums[0];
        }
        return Integer.max(
                rob(nums, true), // rob including the first house but excluding the last house
                rob(nums, false) // rob including the last house but excluding the first house
        );
    }

    /**
     * This method is basically the same as rob methods in {@link HouseRobber}, e.g. {@link HouseRobber#rob(int[])}
     */
    private int rob(int[] nums, boolean includeFirst) {
        int i;
        final int length;
        if (includeFirst) {
            i = 1;
            length = nums.length - 1; // skipping the last element
        } else {
            i = 2; // skipping the first element
            length = nums.length;
        }
        int prev2 = 0;
        int prev = nums[i - 1];
        for (; i < length; i++) {
            final int newSum = prev2 + nums[i];
            prev2 = prev;
            prev = Integer.max(prev, newSum);
        }
        return prev;
    }
}
