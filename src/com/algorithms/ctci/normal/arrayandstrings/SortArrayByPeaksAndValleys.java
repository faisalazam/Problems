package com.algorithms.ctci.normal.arrayandstrings;

import java.util.Arrays;

public class SortArrayByPeaksAndValleys {
    /**
     * Peaks and Valleys: In an array of integers, a "peak" is an element which is greater than or equal to the
     * adjacent integers and a "valley" is an element which is less than or equal to the adjacent integers.
     * For example, in the array (5, 8, 6, 2, 3, 4, 6}, (8,6} are peaks and {5, 2} are valleys.
     * Given an array of integers, sort the array into an alternating sequence of peaks and valleys.
     * <p>
     * Suboptimal Solution
     * Since this problem asks us to sort the array in a particular way, one thing we can try is doing a normal sort
     * and then "fixing" the array into an alternating sequence of peaks and valleys.
     * <p>
     * Before coding, we should clarify the exact algorithm, though.
     * 1. Sort the array in ascending order.
     * 2. Iterate through the elements, starting from index 1 (not 0) and jumping two elements at a time.
     * 3. At each element, swap it with the previous element. Since every three elements appear in the order
     * small <= medium <= large , swapping these elements will always put medium as a peak: medium <= small <= large.
     * This approach will ensure that the peaks are in the right place: indexes 1, 3, 5, and so on.
     * As long as the odd- numbered elements (the peaks) are bigger than the adjacent elements,
     * then the even-numbered elements (the valleys) must be smaller than the adjacent elements.
     * <p>
     * This algorithm runs in 0(n log n) time.
     */
    void sortValleyPeak(int[] array) {
        Arrays.sort(array);
        for (int i = 1; i < array.length; i += 2) {
            swap(array, i - 1, i);
        }
    }

    /**
     * a = a - b;
     * b = b + a;
     * a = b - a;
     */
    private void swap(int[] array, int left, int right) {
        int temp = array[left];
        array[left] = array[right];
        array[right] = temp;
    }

    /**
     * Optimal Solution
     * To optimize past the prior solution, we need to cut out the sorting step. The algorithm must operate on an unsorted array.
     * <p>
     * We can fix the sequence by swapping the center element with the largest adjacent element.
     * <p>
     * This algorithm runs in 0(n) time.
     */
    void sortValleyPeakV1(int[] array) {
        for (int i = 1; i < array.length; i += 2) {
            int biggestIndex = maxIndex(array, i - 1, i, i + 1);
            if (i != biggestIndex) {
                swap(array, i, biggestIndex);
            }
        }
    }

    private int maxIndex(int[] array, int a, int b, int c) {
        int length = array.length;
        int aValue = getValue(array, a, length);
        int bValue = getValue(array, b, length);
        int cValue = getValue(array, c, length);

        int max = Math.max(aValue, Math.max(bValue, cValue));
        if (aValue == max) {
            return a;
        } else if (bValue == max) {
            return b;
        } else {
            return c;
        }
    }

    private int getValue(int[] array, int index, int length) {
        return index >= 0 && index < length ? array[index] : Integer.MIN_VALUE;
    }
}
