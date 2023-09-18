package com.algorithms.leetcode.medium;

/**
 * There is an integer array nums sorted in ascending order (with distinct values).
 * <p>
 * Prior to being passed to your function, nums is possibly rotated at an unknown pivot index k (1 <= k < nums.length)
 * such that the resulting array is [nums[k], nums[k+1], ..., nums[n-1], nums[0], nums[1], ..., nums[k-1]] (0-indexed).
 * For example, [0,1,2,4,5,6,7] might be rotated at pivot index 3 and become [4,5,6,7,0,1,2].
 * <p>
 * Given the array nums after the possible rotation and an integer target, return the index of target if it is in nums,
 * or -1 if it is not in nums.
 * <p>
 * You may assume no duplicate exists in the array.
 * <p>
 * You must write an algorithm with O(log n) runtime complexity.
 * <p>
 * https://leetcode.com/problems/search-in-rotated-sorted-array/
 */
public class FinderInCircularSorterArray {
    /**
     * Time complexity : O(logN)
     * Auxiliary space : O(1)
     */
    private static int findIndex(final int[] nums, final int target) {
        final int pivotIndex = findPivotIndex(nums); // index of the smallest number in the rotated sorted collection
        if (pivotIndex == -1) {
            return -1;
        }

        int low = 0;
        int high = nums.length - 1;
        // pivotIndex...high is basically the 2nd half in the rotated sorted collection
        final boolean isTargetIn2ndHalf = target >= nums[pivotIndex] && target <= nums[high];
        if (isTargetIn2ndHalf) { // for [4,5,6,7,0,1,2], pivotIndex = 4
            low = pivotIndex;
        } else {
            high = pivotIndex - 1;
        }

        while (low <= high) { // Binary search
            int mid = low + ((high - low) / 2);
            if (nums[mid] == target) {
                return mid;
            } else if (nums[mid] < target) { // i.e. target can possibly be in the 2nd half
                low = mid + 1;
            } else { // i.e. target can possibly be in the 1st half
                high = mid - 1;
            }
        }
        return -1;
    }

    /**
     * Direct Binary Search on Array without finding Pivot
     * Same as {@link #findIndex(int[], int)} but just written differently
     * <p>
     * Time complexity : O(logN)
     * Auxiliary space : O(1)
     */
    private static int findIndexV0(int[] nums, int target) {
        int low = 0;
        int high = nums.length - 1;
        while (low < high) {
            final int mid = low + ((high - low) / 2);
            final int midValue = nums[mid];
            if (midValue == target) {
                return mid;
            }

            if (nums[low] <= midValue) { // for [4,5,6,7,0,1,2]
                if (target >= nums[low] && target < midValue) {
                    high = mid - 1;
                } else {
                    low = mid + 1;
                }
            } else {
                if (target > midValue && target <= nums[high]) {
                    low = mid + 1;
                } else {
                    high = mid - 1;
                }
            }
        }
        return nums[low] == target ? low : -1;
    }


    /**
     * This will find the index of the smallest number in the rotated sorted collection
     * <p>
     * Time complexity : O(logN)
     * Auxiliary space : O(1)
     */
    private static int findPivotIndex(final int[] nums) {
        if (nums.length == 1) {
            return 0;
        }

        int mid = -1;
        int low = 0;
        int high = nums.length - 1;
        while (low < high) {
            mid = low + ((high - low) / 2);
            if (nums[mid] > nums[high]) { // i.e. min value will be in the 2nd half (of e.g. [4,5,6,7,0,1,2])
                low = mid + 1;
            } else {
                high = mid;
            }
        }
        return mid == -1 ? -1 : low;
    }

    /**
     * This will find the index of the smallest number in the rotated sorted collection
     * <p>
     * Same as {@link #findPivotIndex(int[])} but just written differently
     * <p>
     * Steps:
     * <p>
     * The minimum element is the only element whose previous is greater than it. If there is no previous element,
     * then there is no rotation (the first element is minimum).
     * Check this condition for the middle element by comparing it with the (mid-1)th and (mid+1)th elements.
     * If the minimum element is not at the middle (neither mid nor mid + 1), then
     * If the middle element is smaller than the last element, then the minimum element lies in the left half
     * Else minimum element lies in the right half.
     * <p>
     * Time complexity : O(logN)
     * Auxiliary space : O(1)
     */
    private static int findPivotIndexV0(final int[] nums) {
        if (nums.length == 1) {
            return 0;
        }

        int low = 0;
        int size = nums.length;
        int high = size - 1;

        while (low <= high) {
            final int mid = low + ((high - low) / 2);
            // Look at this rotated array for example [4,5,6,7,0,1,2], the following condition will only be true when
            // low is pointing at an index which holds the minimum value in the array
            if (nums[low] <= nums[high]) {
                return low;
            }
            final int midValue = nums[mid];
            // Using the % just in case if mid is the last element in the list
            // Added size so that index does not become -ve number in case mid is first element in the list
            final int nextValue = nums[((mid + 1) % size)];
            final int previousValue = nums[((mid + size - 1) % size)];
            if (previousValue >= midValue && midValue <= nextValue) { // It's possible only for the min value
                return mid;
            } else if (midValue <= nums[high]) {
                high = mid - 1;
            } else if (midValue >= nums[low]) {
                low = mid + 1;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        final int[] nums = new int[]{5, 6, 7, 8, 1, 2, 3, 4};
        System.out.println(findIndex(nums, 7));
    }
}
