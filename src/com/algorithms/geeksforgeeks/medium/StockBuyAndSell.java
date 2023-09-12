package com.algorithms.geeksforgeeks.medium;

import java.util.ArrayList;

public class StockBuyAndSell {
    /**
     * The cost of stock on each day is given in an array A[] of size N. Find all the segments of days on which you buy
     * and sell the stock so that in between those days for which profit can be generated.
     * <p>
     * https://practice.geeksforgeeks.org/problems/stock-buy-and-sell-1587115621/1
     * <p>
     * The task is to complete the function stockBuySell() which takes an array of A[] and N as input parameters and
     * finds the days of buying and selling stock. The function must return a 2D list of integers containing all the
     * buy-sell pairs i.e. the first value of the pair will represent the day on which you buy the stock and the second
     * value represent the day on which you sell that stock. If there is No Profit, return an empty list.
     * <p>
     * Time Complexity: O(N)
     * Expected Auxiliary Space: O(N)
     */
    private static ArrayList<ArrayList<Integer>> stockBuySell(int A[], int n) {
        final ArrayList<ArrayList<Integer>> sales = new ArrayList<>();
        if (A == null || A.length == 0 || A.length != n) {
            return sales;
        }

        for (int i = 1; i < n; i++) {
            final int profit = A[i] - A[i - 1];
            if (profit > 0) {
                final ArrayList<Integer> sale = new ArrayList<>();
                sale.add(i - 1);
                sale.add(i);
                sales.add(sale);
            }
        }
        return sales;
    }

    /**
     * The cost of stock on each day is given in an array A[] of size N. Find all the days on which you buy and
     * sell the stock so that in between those days your profit is maximum.
     * <p>
     * https://practice.geeksforgeeks.org/problems/stock-buy-and-sell/0
     * <p>
     * Stock Buy Sell to Maximize Profit using Local Maximum and Local Minimum:
     * Time Complexity:  O(N), The outer loop runs till I become n-1. The inner two loops increment the value of i
     * in every iteration.
     * Auxiliary Space: O(1)
     */
    private static String calculateProfit(int[] A, int N) {
        int i = 0;
        int buyingDay;
        int sellingDay;
        final StringBuilder profit = new StringBuilder();
        while (i < N - 1) { // loop till 2nd last element as there is no point of buying on last (Nth) day
            // loop to find the best day for purchasing, i.e. keep incrementing i until we find a day on which cost is
            // lower than the cost on next day and that'll become our best day to buy so far; because it makes sense to
            // skip buying today if today's cost is higher than the cost on next day
            while (i < N - 1 && A[i] >= A[i + 1]) { // skip (increment i) if today's cost is higher than tomorrow's cost
                i++;
            }
            if (i == N - 1) {
                break;
            }
            buyingDay = i++; // cost at i is minimum so far

            // loop to find the best day for selling, i.e. keep incrementing i until we find a day on which selling
            // price is lower than the selling price on yesterday and then yesterday will become our best day to sell so
            // far; because it makes sense to sell yesterday if today's selling price is lower than the yesterday's price
            while (i < N && A[i] >= A[i - 1]) { // skip if today's selling price is higher than yesterday's selling price
                i++;
            }
            sellingDay = i - 1; // selling price at (i - 1) is maximum so far

            profit.append("(").append(buyingDay).append(" ").append(sellingDay).append(") ");
        }
        return (profit.length() == 0) ? "No Profit" : profit.toString();
    }

    /**
     * SOLUTION FOR SOME OTHER PROBLEM
     * Stock Buy Sell to Maximize Profit using Valley Peak Approach:
     * In this approach, we just need to find the next greater element and subtract it from the current element so that
     * the difference keeps increasing until we reach a minimum. If the sequence is a decreasing sequence, so the
     * maximum profit possible is 0.
     * <p>
     * Time Complexity: O(N), Traversing over the array of size N.
     * Auxiliary Space: O(1)
     */
    static int maxProfit(int[] prices, int size) {
        // maxProfit adds up the difference between adjacent elements if they are in increasing order
        int maxProfit = 0;

        // The loop starts from 1 as its comparing with the previous
        for (int i = 1; i < size; i++) {
            if (prices[i] > prices[i - 1]) {
                maxProfit += prices[i] - prices[i - 1];
            }
        }
        return maxProfit;
    }

    public static void main(String[] args) {
        final int[] arr = {7, 100, 180, 260, 310, 40, 535, 695};
        final int n = arr.length;
        final ArrayList<ArrayList<Integer>> x = stockBuySell(arr, n);
        System.out.println(x);

        final String y = calculateProfit(arr, n);
        System.out.println(y);

        final int maxProfit = maxProfit(arr, n);
        System.out.println(maxProfit);
    }
}
