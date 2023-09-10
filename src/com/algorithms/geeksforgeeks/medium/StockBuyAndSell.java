package com.algorithms.geeksforgeeks.medium;

public class StockBuyAndSell {
    /**
     * The cost of stock on each day is given in an array A[] of size N. Find all the days on which you buy and
     * sell the stock so that in between those days your profit is maximum.
     * <p>
     * https://practice.geeksforgeeks.org/problems/stock-buy-and-sell/0
     */
    private static String calculateProfit(int[] A, int N) {
        int i = 0;
        int buyingDay;
        int sellingDay;
        StringBuilder profit = new StringBuilder();
        while (i < N - 1) {
            while (i < N - 1 && A[i] >= A[i + 1]) {
                i++;
            }
            if (i == N - 1) {
                break;
            }
            buyingDay = i++;
            while (i < N && A[i] >= A[i - 1]) {
                i++;
            }
            sellingDay = i - 1;
            profit.append("(").append(buyingDay).append(" ").append(sellingDay).append(") ");
        }
        return (profit.length() == 0) ? "No Profit" : profit.toString();
    }
}
