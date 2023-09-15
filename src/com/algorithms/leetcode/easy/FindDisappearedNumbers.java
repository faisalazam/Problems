package com.algorithms.leetcode.easy;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 448. Find All Numbers Disappeared in an Array
 * <p>
 * Given an array nums of n integers where nums[i] is in the range [1, n], return an array of all the integers in the
 * range [1, n] that do not appear in nums.
 * <p>
 * Example 1:
 * Input: nums = [4,3,2,7,8,2,3,1]
 * Output: [5,6]
 * <p>
 * Example 2:
 * Input: nums = [1,1]
 * Output: [2]
 * <p>
 * Follow up: Could you do it without extra space and in O(n) runtime? You may assume the returned list does not count
 * as extra space.
 */
public class FindDisappearedNumbers {
    /**
     * Time Complexity: O(N), Where n is the number of nums in the array.
     * Auxiliary Space: O(1), Where n is the number of nums in the array.
     */
    public List<Integer> findDisappearedNumbers(int[] nums) {
        for (int i = 0; i < nums.length; i++) {
            // Get the mapped index for nums[i], using Math.abs only once => NOTE in problem description -> 1 ≤ a[i] ≤ n
            final int index = Math.abs(nums[i]) - 1;
            if (nums[index] > 0) {
                // Mark the value at the index -ve. We don't really care what the value is, as all we care about is,
                // whether its +ve or -ve. -ve value will signify that this index does exist in the array and all the
                // indexes with +ve values will actually be the missing/disappeared values/numbers
                nums[index] *= -1;
            }
        }

        final List<Integer> result = new ArrayList<>();
        for (int i = 0; i < nums.length; i++) {
            // all the indexes with +ve values are the missing/disappeared values/numbers
            if (nums[i] > 0) {
                result.add(i + 1);
            }
        }
        return result;
    }

    /**
     * Time Complexity: O(N), Where n is the number of nums in the array.
     * Auxiliary Space: O(N), Where n is the number of nums in the array.
     */
    public List<Integer> findDisappearedNumbersV0(int[] nums) {
        final Set<Integer> uniqueNums = new HashSet<>();
        for (int num : nums) {
            uniqueNums.add(num);
        }

        final List<Integer> result = new ArrayList<>();
        for (int num = 1; num <= nums.length; num++) {
            if (!uniqueNums.contains(num)) {
                result.add(num);
            }
        }
        return result;
    }
}
