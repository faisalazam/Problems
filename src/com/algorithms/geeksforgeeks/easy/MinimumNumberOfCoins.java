package com.algorithms.geeksforgeeks.easy;

import java.util.ArrayList;
import java.util.List;

/**
 * Given an infinite supply of each denomination of Indian currency { 1, 2, 5, 10, 20, 50, 100, 200, 500, 2000 } and a
 * target value N.
 * <p>
 * Find the minimum number of coins and/or notes needed to make the change for Rs N. You must return the list containing
 * the value of coins required.
 * <p>
 * https://practice.geeksforgeeks.org/problems/-minimum-number-of-coins/0
 */
public class MinimumNumberOfCoins {
    /**
     * Solution: Greedy Approach.
     * The intuition would be to take coins with greater value first. This can reduce the total number of coins needed.
     * Start from the largest possible denomination and keep adding denominations while the remaining value is greater
     * than 0.
     * <p>
     * Approach: A common intuition would be to take coins with greater value first. This can reduce the total number
     * of coins needed. Start from the largest possible denomination and keep adding denominations while the remaining
     * value is greater than 0.
     * <p>
     * Time Complexity: O(V). where V is the total amount that we are trying to make with the available coins.
     * Auxiliary Space: O(1).
     * <p>
     * Note: The above approach may not work for all denominations.
     * <p>
     * For example, it doesn’t work for denominations {9, 6, 5, 1} and V = 11. The above approach would print
     * 9, 1 and 1. But we can use 2 denominations 5 and 6. The following link has solution for this problem.
     * <p>
     * See this for more solutions: https://www.geeksforgeeks.org/find-minimum-number-of-coins-that-make-a-change/
     */
    private static List<Integer> calculate(int N) {
        int remainingAmount = N;
        final List<Integer> coins = new ArrayList<>();
        final int[] availableCoins = new int[]{1, 2, 5, 10, 20, 50, 100, 200, 500, 2000};
        for (int i = availableCoins.length - 1; i >= 0; i--) {
            final int coin = availableCoins[i];
            while (remainingAmount >= coin) {
                remainingAmount -= coin;
                coins.add(coin);
            }
        }
        return coins;
    }

    /**
     * Same as {@link #calculate(int)} but just coded little differently
     * <p>
     * Time Complexity: O(V). where V is the total amount that we are trying to make with the available coins.
     * Auxiliary Space: O(1).
     */
    private static List<Integer> calculateV2(int N) {
        int remainingAmount = N;
        final List<Integer> coins = new ArrayList<>();
        final int[] availableCoins = new int[]{1, 2, 5, 10, 20, 50, 100, 200, 500, 2000};

        int index = availableCoins.length - 1;
        while (remainingAmount > 0 && index >= 0) {
            final int coin = availableCoins[index];
            if (remainingAmount >= coin) {
                coins.add(coin);
                remainingAmount -= coin;
            } else {
                index--;
            }
        }
        return coins;
    }

    /**
     * Same as {@link #calculateV2(int)} but just coded little differently
     * <p>
     * Time Complexity: O(V). where V is the total amount that we are trying to make with the available coins.
     * Auxiliary Space: O(1).
     */
    private static List<Integer> calculateV0(int N) {
        int remainingAmount = N;
        final List<Integer> coins = new ArrayList<>();
        final int[] availableCoins = new int[]{1, 2, 5, 10, 20, 50, 100, 200, 500, 2000};
        for (int i = availableCoins.length - 1; i >= 0; i--) {
            final int coin = availableCoins[i];
            if (coin <= remainingAmount) {
                coins.add(coin);
                remainingAmount -= coin;
                if (remainingAmount != 0) {
                    i++;
                } else {
                    break;
                }
            }
        }
        return coins;
    }

    /**
     * The intuition would be to take coins with greater value first. This can reduce the total number of coins needed.
     * Start from the largest possible denomination and keep adding denominations while the remaining value is greater
     * than 0.
     * <p>
     * Time Complexity: O(V). where V is the total amount that we are trying to make with the available coins.
     * Auxiliary Space: O(1).
     */
    private static List<Integer> calculateV1(int N) {
        int remainingAmount = N;
        final List<Integer> coins = new ArrayList<>();
        final int[] availableCoins = new int[]{1, 2, 5, 10, 20, 50, 100, 200, 500, 2000};
        int i = availableCoins.length - 1;
        while (remainingAmount > 0) {
            final int coin = availableCoins[i--];
            final int noOfCoins = remainingAmount / coin;
            if (noOfCoins > 0) {
                for (int j = 0; j < noOfCoins; j++) {
                    coins.add(coin);
                }
                remainingAmount -= coin * noOfCoins;
            }
        }
        return coins;
    }

    /**
     * The time complexity of the recursive solution is exponential and
     * space complexity is way greater than O(n). If we draw the complete recursion tree, we can observe that many
     * sub-problems are solved again and again. For example, when we start from N = 11, we can reach 6 by subtracting
     * one 5 times and by subtracting 5 one time. So the sub-problem for 6 is called twice.
     * <p>
     * Like other typical Dynamic Programming(DP) problems, re-computations of the same sub-problems can be avoided by
     * constructing a temporary array table[][] in a bottom-up manner. Below is Dynamic Programming based solution.
     */
    private static List<Integer> calculateRecursive(int N) {
        final List<Integer> coins = new ArrayList<>();
        final int[] availableCoins = new int[]{1, 2, 5, 10, 20, 50, 100, 200, 500, 2000};
        // Passing N as single element array as the value will get lost during recursion if using just int variable.
        calculateRecursive(coins, availableCoins, new int[]{N});
        return coins;
    }

    private static void calculateRecursive(final List<Integer> result, final int[] availableCoins, int[] N) {
        if (N[0] == 0) {
            return;
        }

        for (int i = availableCoins.length - 1; i >= 0; i--) {
            if (availableCoins[i] <= N[0]) {
                result.add(availableCoins[i]);
                N[0] = N[0] - availableCoins[i];
                calculateRecursive(result, availableCoins, N);
            }
        }
    }

    public static void main(String[] args) {
        System.out.println(calculateRecursive(9));
    }
}
