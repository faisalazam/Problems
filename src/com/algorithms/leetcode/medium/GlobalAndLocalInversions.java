package com.algorithms.leetcode.medium;

/**
 * We have some permutation nums of [0, 1, ..., N - 1], where N is the length of nums.
 * <p>
 * The number of (global) inversions is the number of i < j with 0 <= i < j < N and nums[i] > nums[j].
 * <p>
 * The number of local inversions is the number of i with 0 <= i < N and nums[i] > nums[i+1].
 * <p>
 * Return true if and only if the number of global inversions is equal to the number of local inversions.
 * <p>
 * https://leetcode.com/problems/global-and-local-inversions/
 */
public class GlobalAndLocalInversions {
    /**
     * Apparently, the local inversion is a special case of global inversion. If we can find a global inversion which
     * is not local inversion, then the permutation is not ideal. In other words, we need to check whether there exists
     * i and j where j - i > 1 and A[i] > A[j]. Traverse the array from left to right, record the max value,
     * if max > A[i + 2] then the permutation is not ideal.
     */
    public boolean isIdealPermutation(int[] nums) {
        int max = -1;
        for (int i = 0; i < nums.length - 2; i++) {
            max = Math.max(max, nums[i]);
            if (max > nums[i + 2]) { // found global inversion which is not a local inversion
                return false;
            }
        }
        return true;
    }

    /**
     * Because there are only 0 to n-1 elements in the given array, so an element x must be at index x in the sorted
     * array. if there exist an element y such that |index(y) - y| > 1, then it means a global inversion exists which is
     * not a local inversion; Meaning: to bring this element to its sorted-index we will need more adjacent swaps than 1,
     * Hence, we can say more global inversions exists than local inversion for this given permutation.
     * <p>
     * So the critical thing to understand here is that every local inversion is also, by definition, a global inversion.
     * Any number that represents part of a global inversion, however, could represent more than one global inversion.
     * <p>
     * So then we should consider that the ideal version of A without any inversions would be one which is strictly
     * increasing, which means that for all i, A[i] = i. Any deviation from this results in an inversion.
     * <p>
     * Also, the further A[i] deviates from i, the more global inversions are ultimately triggered. In fact, the only
     * possible way for the number of global inversions to be equal to the number of local inversions is if each number
     * has a maximum deviation from ideal of only 1, meaning that it represents only a single global inversion and a
     * single local inversion.
     * <p>
     * See this for more information
     * https://leetcode.com/problems/global-and-local-inversions/solutions/1143422/js-python-java-c-simple-3-line-solutions-w-explanation/
     */
    public boolean isIdealPermutationV2(int[] nums) {
        for (int i = 0; i < nums.length; i++) {
            // According to problem description, 0 <= nums[i] < n and All the integers of nums are unique.
            if (Math.abs(nums[i] - i) > 1) {
                return false;
            }
        }
        return true;
    }

    public static boolean isIdealPermutationV0(int[] nums) { // Results in Time Limit Exceeded
        int localInversions = 0;
        int globalInversions = 0;
        final int length = nums.length;
        for (int i = 0; i < length - 1; i++) {
            if (nums[i] > nums[i + 1]) {
                localInversions++;
            }
            for (int j = i + 1; j < length; j++) {
                if (nums[i] > nums[j]) {
                    globalInversions++;
                }
            }
        }
        return localInversions == globalInversions;
    }

    public static void main(String[] args) {
        final int[] nums = new int[]{1, 2, 0, 4, 5, 3};
        System.out.println(isIdealPermutationV0(nums));
    }
}
