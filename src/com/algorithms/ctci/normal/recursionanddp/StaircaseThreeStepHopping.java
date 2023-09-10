package com.algorithms.ctci.normal.recursionanddp;

import java.util.Arrays;

public class StaircaseThreeStepHopping {
    /**
     * Triple Step: A child is running up a staircase with n steps and can hop either 1 step, 2 steps, or 3 steps at a time.
     * Implement a method to count how many possible ways the child can run up the stairs.
     * <p>
     * Brute Force Solution:
     * Like the Fibonacci problem, the runtime of this algorithm is exponential (roughly 0(3^n)),
     * since each call branches out to three more calls.
     * <p>
     * Note that, we just need to add the number of these paths together instead of multiplying.
     */
    int countWays(int n) {
        if (n < 0) {
            return 0;
        } else if (n == 0) {
            return 1;
        } else {
            return countWays(n - 1) + countWays(n - 2) + countWays(n - 3);
        }
    }

    /**
     * Memoization Solution
     * The previous solution for countWays is called many times for the same values, which is unnecessary.
     * We can fix this through memoization.
     * <p>
     * NOTE:
     * Regardless of whether or not you use memoization, note that the number of ways will quickly overflow the bounds
     * of an integer. By the time you get to just n = 37, the result has already overflowed.
     * Using a long will delay, but not completely solve, this issue.
     */
    int countWaysV1(int n) {
        int[] memo = new int[n + 1];
        Arrays.fill(memo, -1);
        return countWays(n, memo);
    }

    private int countWays(int n, int[] memo) {
        if (n < 0) {
            return 0;
        } else if (n == 0) {
            return 1;
        } else if (memo[n] > -1) {
            return memo[n];
        } else {
            memo[n] = countWays(n - 1, memo) + countWays(n - 2, memo) + countWays(n - 3, memo);
            return memo[n];
        }
    }
}
