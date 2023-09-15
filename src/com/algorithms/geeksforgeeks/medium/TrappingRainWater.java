package com.algorithms.geeksforgeeks.medium;

/**
 * Given n non-negative integers representing an elevation map where the width of each bar is 1,
 * compute how much water it is able to trap after raining.
 * <p>
 * Given an array arr[] of N non-negative integers representing the height of blocks at index i as Ai
 * where the width of each block is 1. Compute how much water can be trapped in between blocks after raining.
 * The structure is like below:
 * |  |
 * |_|
 * We can trap 2 units of water in the middle gap.
 * <p>
 * https://practice.geeksforgeeks.org/problems/trapping-rain-water-1587115621/1
 * <p>
 * Time Complexity: O(N)
 * Auxiliary Space: O(N)
 * <p>
 * See for more solutions: https://www.geeksforgeeks.org/trapping-rain-water/
 */
public class TrappingRainWater {
    public static void main(String[] args) {
        int[] heights = {0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1};
        System.out.println("Trapped water => " + trappedWaterV1(heights));

        int[] clone = {0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1};
        System.out.println("Trapped water => " + trappingWater(clone, clone.length));
    }

    /**
     * Time Complexity: O(N)
     * Auxiliary Space: O(1)
     *
     * Steps:-
     *  1) Take two-pointers l and r. Initialize l to the starting index 0 and r to the last index N-1 and an answer
     *     variable to store total water trapped.
     *  2) Since l is the first element, left_max would be 0, and right_max for r would be 0.
     *  3) While l ≤ r, iterate the array. We have two possible conditions
     *      i) Condition1 : left_max <= right_max
     *          * Consider Element at index l
     *          * Since we have traversed all elements to the left of l, left_max is known
     *          * For the right max of l, We can say that the right max would  always be >= current r_max here
     *          * So, min(left_max,right_max) would always equal to left_max in this case
     *          * So, increase the answer by max(0, l_max-arr[left]) and update the left_max as l_max = max(l_max, arr[left]).
     *          * Increment l.
     *      ii) Condition2 : left_max > right_max
     *          * Consider Element at index r
     *          * Since we have traversed all elements to the right of r, right_max is known
     *          * For the left max of l, We can say that the left max would  always be >= current l_max here
     *          * So, min(left_max,right_max) would always equal to right_max in this case
     *          * So, increase the answer by max(0, r_max-arr[right]); and update the right_max as r_max = max(r_max, arr[right]);
     *          * Decrement r.
     *  4) Return the answer.
     */
    private static long trappingWater(int[] arr, int n) {
        int left = 0;
        int right = n - 1;
        int leftMax = 0;
        int rightMax = 0;
        long trappedWater = 0;

        while (left <= right) {
            // We need check for minimum of left and right max for each element
            if (leftMax <= rightMax) {
                // Add the difference between current value and left max at index left
                trappedWater += Math.max(0, leftMax - arr[left]);
                leftMax = Math.max(leftMax, arr[left]);
                left++;
            } else {
                // Add the difference between current value and right max at index right
                trappedWater += Math.max(0, rightMax - arr[right]);
                rightMax = Math.max(rightMax, arr[right]);
                right--;
            }
        }
        return trappedWater;
    }

    /**
     * Time Complexity: O(N)
     * Auxiliary Space: O(N)
     */
    private static long trappingWaterV0(int[] arr, final int n) {
        if (n <= 1) {
            return 0;
        }
        final int[] leftMax = getLeftMax(arr, n);
        final int[] rightMax = getRightMax(arr, n);
        return trappingWater(n, arr, leftMax, rightMax);
    }

    private static long trappingWater(int n, int[] arr, int[] leftMax, int[] rightMax) {
        // Storing the result by choosing the minimum of heights of tallest bar to the right and left of the bar at
        // current index and also subtracting the value of current index to get water accumulated at current index.
        long trappedWater = 0;
        for (int i = 1; i < n; i++) {
            trappedWater += Math.max(0, Math.min(leftMax[i], rightMax[i]) - arr[i]);
        }
        return trappedWater;
    }

    private static int[] getLeftMax(int[] arr, int n) {
        final int[] leftMax = new int[n];
        leftMax[0] = arr[0];
        for (int i = 1; i < n; i++) {
            leftMax[i] = Math.max(arr[i], leftMax[i - 1]);
        }
        return leftMax;
    }

    private static int[] getRightMax(int[] arr, int n) {
        final int[] rightMax = new int[n];
        rightMax[n - 1] = arr[n - 1];
        for (int i = n - 2; i >= 0; i--) {
            rightMax[i] = Math.max(arr[i], rightMax[i + 1]);
        }
        return rightMax;
    }

    private static long trappedWaterV1(int[] arr) { // Not working
        long totalTrappedWater = 0;
        int size = arr.length;
        if (size <= 1) {
            return totalTrappedWater;
        }
        int start = 0;
        int end = size - 1;

        while (start < end) {
            while (start < end && arr[start] == 0) {
                start++;
            }
            while (end >= start && arr[end] == 0) {
                end--;
            }
            if (isNotValid(start, end)) {
                break;
            }
            final int minHeight = Math.min(arr[start], arr[end]);
            int maxTrappedWater = (end - start - 1) * minHeight;
            arr[start] = Math.max(0, arr[start] - minHeight);
            arr[end] = Math.max(0, arr[end] - minHeight);

            for (int i = start + 1; i < end; i++) {
                if (arr[i] != 0) {
                    maxTrappedWater -= minHeight;
                    arr[i] = Math.max(0, arr[i] - minHeight);
                }
            }
            totalTrappedWater += maxTrappedWater;
        }

        return totalTrappedWater;
    }

    private static boolean isNotValid(int start, int end) {
        return start >= end || end < 0;
    }
}
