package com.algorithms.leetcode.medium;

import java.util.ArrayList;
import java.util.List;

public class FindDuplicatesInArray {
    /**
     * Given an array of integers, 1 ≤ a[i] ≤ n (n = size of array), some elements appear twice and others appear once.
     * <p>
     * Find all the elements that appear twice in this array.
     * <p>
     * Could you do it without extra space and in O(n) runtime?
     * <p>
     * LeetCode 442. Find All Duplicates in an Array - https://leetcode.com/problems/find-all-duplicates-in-an-array/
     * <p>
     * APPROACH
     * <p>
     * If we use HashSet it will be pretty easy solution. We iterate over the array and put elements one by one in
     * our HashSet, if element already there, it means that we already have seen this element and we put it in our
     * output list
     * <p>
     * But the problem is asking us to not use extra memory other than for the output list. Given the constraints
     * (i.e all elements in the range [1, n]) of this problem, we can use nums as a HashSet. We just mark each element
     * nums[nums[i] - 1] with negative sign, i.e turning it into a negative number. What we are doing is essentially
     * mapping nums[i] value to a nums[i] - 1 index. -1 is to account for the fact that array is zero-based. We won't
     * be overflowing our array nums as we are guaranteed the elements will be in [1,n] range.
     * <p>
     * Important thing to keep in mind, whenever we read a value from nums we should always take it's absolute value,
     * as that value might have been marked negative by previous iterations.
     * <p>
     * You can also think about this technique as a boolean array, where + of each element means Not seen or False,
     * and - means Seen or True
     */
    public static List<Integer> findDuplicates(int[] nums) {
        final List<Integer> duplicates = new ArrayList<>();

        for (int i = 0; i < nums.length; i++) {
            // Get the mapped index for nums[i], using Math.abs only once => NOTE in problem description -> 1 ≤ a[i] ≤ n
            final int index = Math.abs(nums[i]) - 1;

            // -ve value means we've already seen it, i.e. its a duplicate
            if (nums[index] < 0) { // Check if nums[i] has been seen before, if true then add to the output list
                duplicates.add(index + 1); // Need to add +1 as our range is 1-based
            }

            // We mark the value at that index (where index is value at 'i-1') as -ve to signify that we've already
            // seen it, that means, if we see the same value again at some other 'i-1', the previous condition would
            // have considered it as duplicate as it'll be '< 0' because it was marked -ve earlier.
            // Unconditionally flip the sign, if it's positive, we want to mark it as negative. But if it's negative,
            // we just recover previous value so that at the end our array is not modified
            nums[index] *= -1;
        }

        return duplicates;
    }

    public static void main(String[] args) {
        System.out.println(findDuplicates(new int[]{4, 3, 2, 7, 8, 2, 3, 1}));
    }
}
