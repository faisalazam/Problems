package com.algorithms.leetcode.easy;

import java.util.Arrays;

public class HouseRobber {
    /**
     * You are a professional robber planning to rob houses along a street.
     * Each house has a certain amount of money stashed, the only constraint stopping you from robbing each of them is
     * that adjacent houses have security system connected and it will automatically contact the police
     * if two adjacent houses were broken into on the same night.
     * <p>
     * Given a list of non-negative integers representing the amount of money of each house,
     * determine the maximum amount of money you can rob tonight without alerting the police.
     * <p>
     * https://leetcode.com/problems/house-robber/
     */
    public int rob(int[] nums) { // We can avoid using extra variables if we can update the input array
        if (nums == null || nums.length == 0) {
            return 0;
        } else if (nums.length == 1) {
            return nums[0];
        }

        nums[1] = Math.max(nums[1], nums[0]);
        for (int i = 2; i < nums.length; i++) {
            nums[i] = Math.max(nums[i] + nums[i - 2], nums[i - 1]);
        }
        return nums[nums.length - 1];
    }

    public int robV3(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        int prev2 = 0;
        int prev = nums[0];

        for (int i = 1; i < nums.length; i++) {
            final int newSum = prev2 + nums[i];
            prev2 = prev;
            prev = Math.max(prev, newSum);
        }
        return prev;
    }

    /**
     * DP - Top Down (Optimise Memoized code using tabulation)
     */
    public int robV2(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        int[] dp = new int[nums.length + 1];
        dp[0] = 0;
        dp[1] = nums[0];

        for (int i = 1; i < nums.length; i++) {
            dp[i + 1] = Math.max(dp[i], dp[i - 1] + nums[i]);
        }
        return dp[nums.length];
    }

    /**
     * DP - Memoization
     * Time Complexity: O(N)
     * Space Complexity: O(N).
     */
    public int robV1(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        final int[] dp = new int[nums.length];
        Arrays.fill(dp, -1);
        return getMaxAmount(nums, 0, dp);
    }


    private int getMaxAmount(int[] nums, int i, int[] dp) {
        if (i >= nums.length) {
            return 0;
        }
        if (dp[i] != -1) {
            return dp[i];
        }
        dp[i] = Integer.max(
                nums[i] + getMaxAmount(nums, i + 2, dp),
                getMaxAmount(nums, i + 1, dp)
        );
        return dp[i];
    }

    /**
     * Brute Force
     * <p>
     * Time Complexity: O(2^n).
     * Space Complexity: O(n).
     */
    public int robV0(int[] nums) { // It results in Time Limit Exceeded
        if (nums == null || nums.length == 0) {
            return 0;
        }
        return getMaxAmount(nums, 0);
    }


    private int getMaxAmount(int[] nums, int i) {
        if (i >= nums.length) {
            return 0;
        }
        return Integer.max(
                nums[i] + getMaxAmount(nums, i + 2),
                getMaxAmount(nums, i + 1)
        );
    }
}
