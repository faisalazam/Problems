package com.algorithms.templates;

public class DynamicProgrammingBasicTemplate {
    /**
     * Optimisation: based on your problem, see if you need the dp array or you can replace it by having couple of extra
     * variables to maintain the states...
     */
    public int basicDPTemplate(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0; // or whatever
        }
        int[] dp = new int[nums.length + 1];
        dp[0] = 0; // or whatever is more relevant in your context
        dp[1] = nums[0];

        for (int i = 1; i < nums.length; i++) {
            // this is the statement which might usually change based on the problem
            dp[i + 1] = Math.max(dp[i], dp[i - 1] + nums[i]); // or whatever is more relevant in your context
        }

        return dp[nums.length];
    }

    public int basicDPTemplateWithoutDPArray(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0; // or whatever
        }

        int prev2 = 0; // or whatever is more relevant in your context
        int prev = nums[0];
        for (int i = 1; i < nums.length; i++) {
            final int curr = prev2 + nums[i]; // or whatever is more relevant in your context
            prev2 = prev;
            // this is the statement which might usually change based on the problem
            prev = Math.max(prev, curr);
        }
        return prev;
    }
}
