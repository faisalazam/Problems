package com.algorithms.ctci.normal.recursionanddp;

public class MakeChange {
    /**
     * Coins: Given an infinite number of quarters (25 cents), dimes (10 cents), nickels (5 cents), and
     * pennies (1 cent), write code to calculate the number of ways of representing n cents.
     */
    private static int makeChange(int amount, int[] denoms, int index) {
        if (index >= denoms.length - 1) { // one denom remaining -> one way to do it
            return 1;
        }
        int ways = 0;
        int denomAmount = denoms[index];
        for (int i = 0; i * denomAmount <= amount; i++) {
            int amountRemaining = amount - i * denomAmount;
            ways += makeChange(amountRemaining, denoms, index + 1); // go to next denom
        }
        return ways;
    }

    private static int makeChange1(int n) {
        int[] denoms = {25, 10, 5, 1};
        return makeChange(n, denoms, 0);
    }

    private static int makeChangeImproved(int n) {
        int[] denoms = {25, 10, 5, 1};
        int[][] map = new int[n + 1][denoms.length];
        return makeChangeImproved(n, denoms, 0, map);
    }

    private static int makeChangeImproved(int amount, int[] denoms, int index, int[][] map) {
        if (map[amount][index] > 0) {
            return map[amount][index];
        }
        if (index >= denoms.length - 1) { // one denom remaining -> one way to do it
            return 1;
        }
        int denomAmount = denoms[index];
        int ways = 0;
        for (int i = 0; i * denomAmount <= amount; i++) {
            // go to next denom, assuming i coins of denomAmount
            int amountRemaining = amount - i * denomAmount;
            ways += makeChangeImproved(amountRemaining, denoms, index + 1, map);
        }
        map[amount][index] = ways;
        return ways;
    }

    private static int makeChange(int n) {
        int x = makeChange1(n);
        int y = makeChangeImproved(n);
        if (x != y) {
            System.out.println("Error: " + x + " " + y);
        }
        return x;
    }

    public static void main(String[] args) {
        for (int i = 0; i <= 100; i++) {
            System.out.println("makeChange(" + i + ") = " + makeChange(i));
        }
    }
}
