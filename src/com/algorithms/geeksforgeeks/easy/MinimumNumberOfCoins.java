package com.algorithms.geeksforgeeks.easy;

import java.util.ArrayList;
import java.util.List;

public class MinimumNumberOfCoins {
    /**
     * Given a value N, total sum you have. You have to make change for Rs. N, and there is infinite supply
     * of each of the denominations in Indian currency, i.e., you have infinite supply of
     * { 1, 2, 5, 10, 20, 50, 100, 200, 500, 2000} valued coins/notes, Find the minimum number
     * of coins and/or notes needed to make the change for Rs N.
     * <p>
     * https://practice.geeksforgeeks.org/problems/-minimum-number-of-coins/0
     */
    private static List<Integer> calculate(int N) {
        int remainingAmount = N;
        int[] availableCoins = new int[]{1, 2, 5, 10, 20, 50, 100, 200, 500, 2000};
        List<Integer> coins = new ArrayList<>();
        for (int i = availableCoins.length - 1; i >= 0; i--) {
            int coin = availableCoins[i];
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

    //Working solution
    private static List<Integer> calculateV1(int N) {
        int remainingAmount = N;
        int[] availableCoins = new int[]{1, 2, 5, 10, 20, 50, 100, 200, 500, 2000};
        List<Integer> coins = new ArrayList<>();
        int i = availableCoins.length - 1;
        while (remainingAmount > 0) {
            int coin = availableCoins[i--];
            int noOfCoins = remainingAmount / coin;
            if (noOfCoins > 0) {
                for (int j = 0; j < noOfCoins; j++) {
                    coins.add(coin);
                }
                remainingAmount -= coin * noOfCoins;
            }
        }
        return coins;
    }

    private static void printCoins(List<Integer> coins) {
        StringBuilder builder = new StringBuilder();
        for (Integer coin : coins) {
            builder.append(coin).append(" ");
        }
        System.out.println(builder.toString());
    }
}
