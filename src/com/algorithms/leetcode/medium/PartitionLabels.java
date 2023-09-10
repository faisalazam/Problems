package com.algorithms.leetcode.medium;

import java.util.ArrayList;
import java.util.List;

public class PartitionLabels {
    /*
     * https://leetcode.com/problems/partition-labels/
     *
     * A string ShortestUnsortedContinuousSubarray of lowercase letters is given.
     * We want to partition this string into as many parts as possible
     * so that each letter appears in at most one part, and return a list of integers representing the size of these parts.
     */
    public List<Integer> partitionLabels(String S) {
        if (S == null || S.isEmpty()) {
            return new ArrayList<>();
        }
        int[] charLastIndices = getCharLastIndices(S);
        return getPartitions(S, charLastIndices);
    }

    private List<Integer> getPartitions(String S, int[] charLastIndices) {
        List<Integer> partitionLabels = new ArrayList<>();
        int start = 0;
        int end = 0;
        for (int i = 0; i < S.length(); i++) {
            end = Math.max(end, charLastIndices[S.charAt(i) - 'a']);
            if (i == end) {
                partitionLabels.add(end - start + 1);
                start = end + 1;
            }
        }
        return partitionLabels;
    }

    private int[] getCharLastIndices(String S) {
        int[] charLastIndices = new int[26];
        for (int i = 0; i < S.length(); i++) {
            charLastIndices[S.charAt(i) - 'a'] = i;
        }
        return charLastIndices;
    }
}
