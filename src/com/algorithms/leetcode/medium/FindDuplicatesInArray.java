package com.algorithms.leetcode.medium;

import java.util.ArrayList;
import java.util.List;

public class FindDuplicatesInArray {
    /*
     * Given an array of integers, 1 ≤ a[i] ≤ n (n = size of array), some elements appear twice and others appear once.
     *
     * Find all the elements that appear twice in this array.
     *
     * Could you do it without extra space and in O(n) runtime?
     *
     * LeetCode 442. Find All Duplicates in an Array
     */
    public List<Integer> findDuplicates(int[] nums) {
        List<Integer> duplicates = new ArrayList<>();
        for (int i = 0; i < nums.length; i++) {
            int index = Math.abs(nums[i] - 1); // NOTE in problem description -> 1 ≤ a[i] ≤ n
            if (nums[index] < 0) { // -ve value means we've already seen it, i.e. its a duplicate
                duplicates.add(index + 1);
            }
            // We mark the value at that index (where index is value at 'i-1') as -ve to signify that we've already seen it
            // that means, if see the same value again at some other 'i-1', the previous condition would have considered
            // it as duplicate as it'll be '< 0' because it was marked -ve earlier.
            nums[index] = -nums[index];
        }
        return duplicates;
    }
}
