package com.algorithms.leetcode.medium;

/**
 * Suppose an array of length n sorted in ascending order is rotated between 1 and n times. For example, the array
 * nums = [0,1,2,4,5,6,7] might become:
 * <p>
 * [4,5,6,7,0,1,2] if it was rotated 4 times.
 * [0,1,2,4,5,6,7] if it was rotated 7 times.
 * <p>
 * Notice that rotating an array [a[0], a[1], a[2], ..., a[n-1]] 1 time results in the array
 * [a[n-1], a[0], a[1], a[2], ..., a[n-2]].
 * <p>
 * Given the sorted rotated array nums of unique elements, return the minimum element of this array.
 * <p>
 * You must write an algorithm that runs in O(log n) time.
 * <p>
 * Find the minimum element.
 * <p>
 * You may assume no duplicate exists in the array.
 * <p>
 * https://leetcode.com/problems/find-minimum-in-rotated-sorted-array/
 */
public class FindMinInCircularSorterArray {
    /**
     * Time complexity : O(logN)
     * Auxiliary space : O(1)
     */
    private static int findMin(final int[] nums) {
        if (nums.length == 0) {
            return -1;
        }
        final int pivotIndex = findPivotIndex(nums);
        return nums[pivotIndex];
    }

    /**
     * This will find the index of the smallest number in the rotated sorted collection
     * <p>
     * See {@link FindMinInCircularSorterArray} for more variations
     */
    private static int findPivotIndex(final int[] nums) {
        if (nums.length == 1) {
            return 0;
        }

        int low = 0;
        int high = nums.length - 1;
        while (low < high) {
            int mid = low + ((high - low) / 2);
            if (nums[mid] > nums[high]) {
                low = mid + 1;
            } else {
                high = mid;
            }
        }
        return low;
    }

    /**
     * Second variation
     * <p>
     * See {@link FindMinInCircularSorterArray} for more variations
     */
    private static int findPivotIndexV2(final int[] nums) {
        if (nums.length == 1) {
            return 0;
        }

        int low = 0;
        int high = nums.length - 1;
        while (low < high) {
            int mid = low + ((high - low) / 2);
            final int midValue = nums[mid];
            if (mid > 0 && midValue < nums[mid - 1]) {
                return mid;
            } else if (nums[low] <= midValue && midValue > nums[high]) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return low;
    }

    public static void main(String[] args) {
        final int[] nums = new int[]{5, 6, 7, 8, 1, 2, 3, 4};
        System.out.println(findMin(nums));
    }
}
