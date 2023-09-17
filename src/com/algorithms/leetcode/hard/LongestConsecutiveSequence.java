package com.algorithms.leetcode.hard;

import java.util.HashSet;
import java.util.Set;

public class LongestConsecutiveSequence {
    /**
     * Given an unsorted array of integers, find the length of the longest consecutive elements sequence.
     * <p>
     * Your algorithm should run in O(n) complexity.
     * <p>
     * https://leetcode.com/problems/longest-consecutive-sequence/
     * <p>
     * O(N) & O(N)
     */
    public int longestConsecutive(int[] nums) {
        Set<Integer> numsSet = new HashSet<>();
        for (int currentNum : nums) {
            numsSet.add(currentNum);
        }

        int maxSequenceLength = 0;
        for (int currentNum : nums) {
            int currentSequenceLength = 1;

            if (!numsSet.contains(currentNum - 1)) {
                while (numsSet.contains(currentNum + 1)) {
                    currentNum++;
                    currentSequenceLength++;
                }
                maxSequenceLength = Math.max(currentSequenceLength, maxSequenceLength);
            }
        }

        return maxSequenceLength;
    }


    /*
     * O(N²) & O(N)
     */
    public int longestConsecutiveV2(int[] nums) {
        Set<Integer> numsSet = new HashSet<>();
        for (int currentNum : nums) {
            numsSet.add(currentNum);
        }

        int maxSequenceLength = 0;
        for (int currentNum : nums) {
            int currentSequenceLength = 1;

            while (numsSet.contains(currentNum + 1)) {
                currentNum++;
                currentSequenceLength++;
            }
            maxSequenceLength = Math.max(currentSequenceLength, maxSequenceLength);
        }

        return maxSequenceLength;
    }

    /*
     * O(N^3) & O(1)
     */
    public int longestConsecutiveV1(int[] nums) {
        int maxSequenceLength = 0;

        for (int currentNum : nums) {
            int currentSequenceLength = 1;

            while (numExists(nums, currentNum + 1)) {
                currentNum++;
                currentSequenceLength++;
            }
            maxSequenceLength = Math.max(currentSequenceLength, maxSequenceLength);
        }

        return maxSequenceLength;
    }

    private boolean numExists(int[] nums, int numToCheck) {
        for (int currentNum : nums) {
            if (currentNum == numToCheck) {
                return true;
            }
        }
        return false;
    }
}
