package com.algorithms.misc;

import java.util.HashMap;
import java.util.Map;

public class Knapsack {

    public static void main(String[] args) {
        int maxWeight = 5;
        Item[] items = {
                new Item(2, 6),
                new Item(2, 10),
                new Item(3, 12)
        };
        System.out.println("Top down without cache => " + maxKnapsack(items, maxWeight));
        System.out.println("Top down with cache => " + maxKnapsackWithCache(items, maxWeight));
        System.out.println("Bottom Up with cache => " + maxKnapsackBottomUp(items, maxWeight));
        System.out.println("Bottom Up with improved cache (better Space complexity) => " + maxKnapsackBottomUpImproved(items, maxWeight));
    }

    /**
     * Imagine that you have a knapsack which can carry a certain maximum amount of weight and you have a set of items
     * with their own weight and a monetary value. You are going to to sell your items in the market but you can only
     * carry what fits in the knapsack. How do you maximize the amount of money that you can earn?
     * Or more formally...
     * Given a list of items with values and weights, as well as a max weight, find the maximum value you can generate
     * from items, where the sum of the weights is less than or equal to the max. eg.
     * items = {(w:2, v:6), (w:2, v:10), (w:3, v:12)}
     * max weight = 5
     * knapsack(items, max weight) = 22
     * <p>
     * Each item can be included it or not included. This causes the recursion to branch in two different ways.
     * Like several other problems that have been discussed, we get 2 * 2 * ... * 2 or 2^n,
     * where n is the depth of our recursion. In this case, our recursion iterates over the items array.
     * Therefore, n is the number of items, which gives us a time complexity of O(2^n).
     * <p>
     * The code performs reasonably well in terms of space complexity.
     * The only extra space that we are using is the recursive stack, which is at most, height O(n).
     */
    private static int maxKnapsack(Item[] items, int maxWeight) {
        return maxKnapsack(items, maxWeight, 0);
    }

    private static int maxKnapsack(Item[] items, int maxWeight, int index) {
        if (index == items.length) {
            return 0;
        }
        Item item = items[index];
        int remainingWeight = maxWeight - item.getWeight();
        if (remainingWeight < 0) { // that means the current item can't be included'
            return maxKnapsack(items, maxWeight, index + 1);
        }
        return Integer.max(
                maxKnapsack(items, remainingWeight, index + 1) + item.getValue(), // include the current item
                maxKnapsack(items, maxWeight, index + 1) // do not include the current item
        );
    }

    /**
     * Time complexity is O(n * W).
     * Space complexity is O(n * W).
     * <p>
     * Where n is the number of items
     */
    private static int maxKnapsackWithCache(Item[] items, int maxWeight) {
        Map<Integer, Map<Integer, Integer>> resultsCache = new HashMap<>();
        return maxKnapsackWithCache(items, maxWeight, 0, resultsCache);
    }

    private static int maxKnapsackWithCache(Item[] items, int maxWeight, int index, Map<Integer, Map<Integer, Integer>> resultsCache) {
        if (index == items.length) {
            return 0;
        } else if (!resultsCache.containsKey(index)) {
            resultsCache.put(index, new HashMap<>());
        }
        Integer cached = resultsCache.get(index).get(maxWeight);
        if (cached != null) {
            return cached;
        }
        int currentMax;
        Item item = items[index];
        int remainingWeight = maxWeight - item.getWeight();
        if (remainingWeight < 0) {
            currentMax = maxKnapsackWithCache(items, maxWeight, index + 1, resultsCache);
        } else {
            currentMax = Integer.max(
                    maxKnapsackWithCache(items, remainingWeight, index + 1, resultsCache) + item.getValue(),
                    maxKnapsackWithCache(items, maxWeight, index + 1, resultsCache)
            );
        }
        resultsCache.get(index).put(maxWeight, currentMax);
        return currentMax;
    }

    /**
     * Time complexity is O(n * W).
     * Space complexity is O(n * W).
     * <p>
     * Where n is the number of items
     */
    private static int maxKnapsackBottomUp(Item[] items, int maxWeight) {
        int[][] resultsCache = new int[items.length + 1][maxWeight + 1];
        for (int i = 1; i <= items.length; i++) {
            for (int j = 0; j <= maxWeight; j++) {
                if (items[i - 1].getWeight() > j) {
                    resultsCache[i][j] = resultsCache[i - 1][j];
                } else {
                    resultsCache[i][j] = Integer.max(
                            resultsCache[i - 1][j],
                            resultsCache[i - 1][j - items[i - 1].getWeight()] + items[i - 1].getValue()
                    );
                }
            }
        }
        return resultsCache[items.length][maxWeight];
    }

    private static int maxKnapsackBottomUpImproved(Item[] items, int maxWeight) {
        int[] resultsCache = new int[maxWeight + 1];
        for (Item item : items) {
            int[] newResultsCache = new int[maxWeight + 1];
            for (int j = 0; j <= maxWeight; j++) {
                if (item.getWeight() > j) {
                    newResultsCache[j] = resultsCache[j];
                } else {
                    newResultsCache[j] = Math.max(
                            resultsCache[j],
                            resultsCache[j - item.getWeight()] + item.getValue()
                    );
                }
            }
            resultsCache = newResultsCache;
        }
        return resultsCache[maxWeight];
    }

    private static class Item {
        private final int value;
        private final int weight;

        Item(final int weight, final int value) {
            this.value = value;
            this.weight = weight;
        }

        int getValue() {
            return this.value;
        }

        int getWeight() {
            return this.weight;
        }
    }
}
