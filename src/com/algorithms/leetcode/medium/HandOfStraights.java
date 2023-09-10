package com.algorithms.leetcode.medium;

import java.util.TreeMap;

public class HandOfStraights {
    /**
     * Alice has a hand of cards, given as an array of integers.
     * <p>
     * Now she wants to rearrange the cards into groups so that each group is size W, and consists of W consecutive cards.
     * <p>
     * Return true if and only if she can.
     * <p>
     * https://leetcode.com/problems/hand-of-straights/
     *
     *
     * Complexity Analysis
     *
     * Time Complexity: O(N * (N/W)), where N is the length of hand.
     * The (N / W) factor comes from min(count).
     * In Java, the (N / W) factor becomes logN due to the complexity of TreeMap.
     *
     * Space Complexity: O(N)
     */
    public boolean isNStraightHand(int[] hand, int W) {
        if (hand.length % W != 0) {
            return false;
        }
        TreeMap<Integer, Integer> cardCounts = cardCounts(hand); // it'll keep the count mappings in sorted order
        while (!cardCounts.isEmpty()) {
            int minValue = cardCounts.firstKey();
            for (int card = minValue; card < minValue + W; card++) {
                if (!cardCounts.containsKey(card)) {
                    return false;
                }
                updateCardCounts(card, cardCounts);
            }
        }
        return true;
    }

    private void updateCardCounts(int card, TreeMap<Integer, Integer> cardCounts) {
        int count = cardCounts.get(card);
        if (count == 1) {
            cardCounts.remove(card);
        } else {
            cardCounts.replace(card, count - 1);
        }
    }

    private TreeMap<Integer, Integer> cardCounts(int[] hand) {
        TreeMap<Integer, Integer> cardCounts = new TreeMap<>();
        for (int card : hand) {
            cardCounts.put(card, cardCounts.getOrDefault(card, 0) + 1);
        }
        return cardCounts;
    }
}
