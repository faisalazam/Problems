package com.algorithms.leetcode.medium;

import static java.lang.Math.max;

public class JumpGame {
    /**
     * Given an array of non-negative integers, you are initially positioned at the first index of the array.
     * <p>
     * Each element in the array represents your maximum jump length at that position.
     * <p>
     * Determine if you are able to reach the last index.
     * <p>
     * https://leetcode.com/problems/jump-game/
     * <p>
     * https://leetcode.com/problems/jump-game/solutions/2375320/interview-scenario-recursion-memoization-dp-greedy/?envType=study-plan-v2&envId=top-interview-150
     */
    public boolean canJump(int[] nums) {
        int lastGoodIndexPosition = nums.length - 1;
        for (int i = nums.length - 1; i >= 0; i--) {
            if (i + nums[i] >= lastGoodIndexPosition) {
                lastGoodIndexPosition = i;
            }
        }
        return lastGoodIndexPosition == 0;
    }

    public boolean canJumpV1(int[] nums) {
        //it shows at max what index can I reach.
        //initially I can only reach index 0, hence reach = 0
        int reach = 0;

        for (int idx = 0; idx < nums.length; idx++) {
            //at every index I'll check if my reach was at least able to
            //reach that particular index.

            //reach >= idx -> great, carry on. Otherwise,
            if (reach < idx) {
                return false;
            }

            //now as you can reach this index, it's time to update your reach
            //as at every index, you're getting a new jump length.
            reach = max(reach, idx + nums[idx]);
        }

        //this means that you reached till the end of the array, wohooo!!
        return true;
    }
}
