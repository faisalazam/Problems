package com.algorithms.misc;

/**
 * Minimum Swaps required to group all 1’s together
 * <p>
 * Given an array of 0’s and 1’s, we need to write a program to find the minimum number of swaps required to
 * group all 1’s present in the array together.
 */
public class Agoda2024 {
    /**
     * Time Complexity: O(N) where N is the number of total elements in the array.
     * Space Complexity: O(1) as no additional space is used.
     */
    private static int minSwapsOptimised(int[] nums) {
        int swaps = 0;
        if (nums == null) {
            return swaps;
        }
        final int onesCount = countOnes(nums); // window size
        for (int i = 0; i < onesCount; i++) {
            if (nums[i] == 0) {
                swaps++;
            }
        }

        int minSwaps = swaps;
        for (int i = onesCount; i < nums.length; i++) {
            // Adjust 'swaps' as we include the nums[i] as the last element to the current window
            if (nums[i] == 0) {
                swaps++;
            }

            // Adjust 'swaps' as we need to exclude the first element from the previous window
            if (nums[i - onesCount] == 0) {
                swaps--;
            }
            minSwaps = Integer.min(swaps, minSwaps);
        }
        return minSwaps;
    }

    /**
     * Time Complexity: O(N * WindowSize) where N is the number of total elements
     * and WindowSize is the total number of 1's in the array.
     * Space Complexity: O(1) as no additional space is used.
     */
    private static int minSwapsBruteForce(int[] nums) {
        if (nums == null) {
            return 0;
        }
        int minSwaps = Integer.MAX_VALUE;
        final int onesCount = countOnes(nums); // window size
        for (int i = 0; i < nums.length - onesCount; i++) {
            int swaps = 0;

            // Check swaps for the window starting at the ith index
            for (int j = 0; j < onesCount; j++) {
                if (nums[i + j] == 0) {
                    swaps++;
                }
            }
            minSwaps = Integer.min(swaps, minSwaps);
        }
        return (minSwaps == Integer.MAX_VALUE) ? 0 : minSwaps;
    }

    private static int countOnes(int[] nums) {
        int count = 0;
        for (int num : nums) {
            if (num == 1) {
                count++;
            }
        }
        return count;
    }

    public static void main(String[] args) {
        System.out.println("Min Swaps (Optimised) for null => " + minSwapsOptimised(null));
        System.out.println("Min Swaps (Optimised) for [] => " + minSwapsOptimised(new int[]{}));
        System.out.println("Min Swaps (Optimised) for [0] => " + minSwapsOptimised(new int[]{0}));
        System.out.println("Min Swaps (Optimised) for [1] => " + minSwapsOptimised(new int[]{1}));
        System.out.println("Min Swaps (Optimised) for [0, 0, 0] => " + minSwapsOptimised(new int[]{0, 0, 0}));
        System.out.println("Min Swaps (Optimised) for [1, 1, 1] => " + minSwapsOptimised(new int[]{1, 1, 1}));
        System.out.println("Min Swaps (Optimised) for [1, 0, 1, 0, 1] => " + minSwapsOptimised(new int[]{1, 0, 1, 0, 1}));
        System.out.println("Min Swaps (Optimised) for [0, 0, 1, 0, 0, 0, 1, 0, 0, 1] => " + minSwapsOptimised(new int[]{0, 0, 1, 0, 0, 0, 1, 0, 0, 1}));

        System.out.println();
        System.out.println("Min Swaps (BruteForce) for null => " + minSwapsBruteForce(null));
        System.out.println("Min Swaps (BruteForce) for [] => " + minSwapsBruteForce(new int[]{}));
        System.out.println("Min Swaps (BruteForce) for [0] => " + minSwapsBruteForce(new int[]{0}));
        System.out.println("Min Swaps (BruteForce) for [1] => " + minSwapsBruteForce(new int[]{1}));
        System.out.println("Min Swaps (BruteForce) for [0, 0, 0] => " + minSwapsBruteForce(new int[]{0, 0, 0}));
        System.out.println("Min Swaps (BruteForce) for [1, 1, 1] => " + minSwapsBruteForce(new int[]{1, 1, 1}));
        System.out.println("Min Swaps (BruteForce) for [1, 0, 1, 0, 1] => " + minSwapsBruteForce(new int[]{1, 0, 1, 0, 1}));
        System.out.println("Min Swaps (BruteForce) for [0, 0, 1, 0, 0, 0, 1, 0, 0, 1] => " + minSwapsBruteForce(new int[]{0, 0, 1, 0, 0, 0, 1, 0, 0, 1}));
    }
}
