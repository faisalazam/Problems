package com.algorithms.leetcode.medium;

import java.util.Arrays;
import java.util.TreeMap;

public class HandOfStraights {
    /**
     * Alice has a hand of cards, given as an array of integers.
     * <p>
     * Now she wants to rearrange the cards into groups so that each group is size groupSize, and consists of groupSize consecutive cards.
     * <p>
     * Return true if and only if she can.
     * <p>
     * https://leetcode.com/problems/hand-of-straights/
     * <p>
     * Complexity Analysis
     * <p>
     * Time Complexity: O(N * (N/groupSize)), where N is the length of hand.
     * The (N / groupSize) factor comes from min(count).
     * In Java, the (N / groupSize) factor becomes logN due to the complexity of TreeMap.
     * <p>
     * Space Complexity: O(N)
     */
    public boolean isNStraightHand(int[] hand, int groupSize) {
        if (hand.length % groupSize != 0) { // length of array should be multiple of groupSize
            return false;
        }
        final TreeMap<Integer, Integer> cardCounts = cardCounts(hand); // it'll keep the count mappings in sorted order
        while (!cardCounts.isEmpty()) {
            int minValue = cardCounts.firstKey();
            for (int card = minValue; card < minValue + groupSize; card++) {
                if (!cardCounts.containsKey(card)) {
                    return false;
                }
                updateCardCounts(card, cardCounts);
            }
        }
        return true;
    }

    /**
     * BELOW SOLUTION WON'T WORK IF THERE ARE DUPLICATES IN THE CARDS
     * Another solution could be to sort the array of cards and proceed from there. Use of TreeMap in the above solution
     * is resulting in O(N logN) time complexity anyway.
     *
     * So we may need to stick to the TreeMap solution as the below solution won't work with duplicates because when
     * the array is sorted, then you can't just split it into smaller arrays of groupSize without some further processing
     * and that's because of the duplicates...
     */
    public static boolean isNStraightHandV0(int[] hand, int groupSize) {
        if (hand.length % groupSize != 0) { // length of array should be multiple of groupSize
            return false;
        }
        Arrays.sort(hand);

        int counter = 1;
        int minValue = hand[0];
        for (int i = 1; i < hand.length; i++) {
            if (i % groupSize == 0) {
                counter = 0;
                minValue = hand[i];
            }
            if (hand[i] != minValue + counter++) {
                return false;
            }
        }
        return true;
    }

    private void updateCardCounts(int card, TreeMap<Integer, Integer> cardCounts) {
        final int count = cardCounts.get(card);
        if (count == 1) {
            cardCounts.remove(card);
        } else {
            cardCounts.replace(card, count - 1);
        }
    }

    private TreeMap<Integer, Integer> cardCounts(int[] hand) {
        // The map is sorted according to the natural ordering of its keys.
        // Note that this implementation is not synchronized.
        // This implementation provides guaranteed log(n) time cost for the containsKey, get, put and remove operations.
        final TreeMap<Integer, Integer> cardCounts = new TreeMap<>();
        for (int card : hand) {
            cardCounts.put(card, cardCounts.getOrDefault(card, 0) + 1);
        }
        return cardCounts;
    }

    public static void main(String[] args) {
        final int[] hand = new int[]{1, 2, 3, 6, 2, 3, 4, 7, 8};
        System.out.println(isNStraightHandV0(hand, 3));
    }
}
