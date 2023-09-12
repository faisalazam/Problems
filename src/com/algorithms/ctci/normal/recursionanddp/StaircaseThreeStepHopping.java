package com.algorithms.ctci.normal.recursionanddp;

import java.util.Arrays;

public class StaircaseThreeStepHopping {
    /**
     * Triple Step: A child is running up a staircase with n steps and can hop either 1 step, 2 steps, or 3 steps at a
     * time. Implement a method to count how many possible ways the child can run up the stairs.
     * <p>
     * There are n stairs, and a person is allowed to jump next stair, skip one stair or skip two stairs. So there are
     * n stairs. So if a person is standing at i-th stair, the person can move to i+1, i+2, i+3-th stair.
     * <p>
     * Brute Force Solution:
     * Like the Fibonacci problem, the runtime of this algorithm is exponential (roughly 0(3^n)), since each call
     * branches out to three more calls.
     * <p>
     * Time Complexity: O(3^n).
     * The time complexity of the above solution is exponential, a close upper bound will be O(3^n). From each state,
     * 3 recursive function are called. So the upperbound for n states is O(3^n).
     * Space Complexity: O(N).
     * Auxiliary Space required by the recursive call stack is O(depth of recursion tree).
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
     * DP using Memoization (Top-down approach)
     * <p>
     * We can avoid the repeated work done in method 1(recursion) by storing the number of ways calculated so far.
     * <p>
     * We just need to store all the values in an array.
     * <p>
     * The previous solution for countWays is called many times for the same values, which is unnecessary.
     * We can fix this through memoization.
     * <p>
     * NOTE:
     * Regardless of whether or not you use memoization, note that the number of ways will quickly overflow the bounds
     * of an integer. By the time you get to just n = 37, the result has already overflowed.
     * Using a long will delay, but not completely solve, this issue.
     * <p>
     * Time Complexity: O(n). Only one traversal of the array is needed. So Time Complexity is O(n).
     * Space Complexity: O(n). To store the values in a DP, n extra space is needed. Also, stack space for recursion is
     * needed which is again O(n)
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

    /**
     * Bottom-Up Approach: Another way is to take an extra space of size n and start computing values of states
     * from 1, 2 .. to n, i.e. compute values of i, i+1, i+2 and then use them to calculate the value of i+3.
     * <p>
     * Time Complexity: O(n). Only one traversal of the array is needed. So Time Complexity is O(n).
     * Space Complexity: O(n). To store the values in a DP, n extra space is needed.
     */
    public static int countWaysV2(int n) {
        final int[] result = new int[n + 1];
        result[0] = 1;
        result[1] = 1;
        result[2] = 2;

        for (int i = 3; i <= n; i++) {
            result[i] = result[i - 1] + result[i - 2] + result[i - 3];
        }
        return result[n];
    }

    /**
     * Using four variables
     * <p>
     * The idea is based on the Fibonacci series but here with 3 sums. we will hold the values of the first three
     * stairs in 3 variables and will use the fourth variable to find the number of ways.
     * <p>
     * Time Complexity: O(n)
     * Auxiliary Space: O(1), since no extra space has been taken.
     */
    static int countWaysV3(int n) {
        // Declaring three variables and holding the ways for first three stairs
        int a = 1, b = 2, c = 4;

        if (n == 0 || n == 1 || n == 2) {
            return n;
        }
        if (n == 3) {
            return c;
        }

        // Fourth variable
        int count = 0;
        for (int i = 4; i <= n; i++) {
            count = c + b + a;
            a = b;
            b = c;
            c = count;
        }
        return count;
    }
}
