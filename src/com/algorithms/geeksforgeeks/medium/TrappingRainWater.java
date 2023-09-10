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
 */
public class TrappingRainWater {
    public static void main(String[] args) {
        int[] heights = {0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1};
        System.out.println("Trapped water => " + trappedWaterV1(heights));

        int[] clone = {0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1};
        System.out.println("Trapped water => " + trappingWater(clone));
    }

    private static int trappingWater(int[] arr) {
        int n = arr.length;
        if (n <= 1) {
            return 0;
        }
        int[] leftMax = getLeftMax(arr, n);
        int[] rightMax = getRightMax(arr, n);
        return trappingWater(n, arr, leftMax, rightMax);
    }

    private static int trappingWater(int n, int[] arr, int[] leftMax, int[] rightMax) {
        int trappedWater = 0;
        for (int i = 1; i < n; i++) {
            trappedWater += Math.max(0, Math.min(leftMax[i], rightMax[i]) - arr[i]);
        }
        return trappedWater;
    }

    private static int[] getLeftMax(int[] arr, int n) {
        int[] leftMax = new int[n];
        leftMax[0] = arr[0];
        for (int i = 1; i < n; i++) {
            leftMax[i] = Math.max(arr[i], leftMax[i - 1]);
        }
        return leftMax;
    }

    private static int[] getRightMax(int[] arr, int n) {
        int[] rightMax = new int[n];
        rightMax[n - 1] = arr[n - 1];
        for (int i = n - 2; i >= 0; i--) {
            rightMax[i] = Math.max(arr[i], rightMax[i + 1]);
        }
        return rightMax;
    }

    private static int trappedWaterV1(int[] heights) {
        int totalTrappedWater = 0;
        int size = heights.length;
        if (size <= 1) {
            return totalTrappedWater;
        }
        int start = 0;
        int end = size - 1;

        while (start < end) {
            while (start < end) {
                if (heights[start] != 0) {
                    break;
                }
                start++;
            }
            while (end >= start) {
                if (heights[end] != 0) {
                    break;
                }
                end--;
            }
            if (isNotValid(start, end)) {
                break;
            }
            int minHeight = Math.min(heights[start], heights[end]);
            int maxTrappedWater = (end - start - 1) * minHeight;
            heights[start] = Math.max(0, heights[start] - minHeight);
            heights[end] = Math.max(0, heights[end] - minHeight);

            for (int i = start + 1; i < end; i++) {
                if (heights[i] != 0) {
                    maxTrappedWater -= minHeight;
                    heights[i] = Math.max(0, heights[i] - minHeight);
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
