package com.algorithms.leetcode.medium;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * Given an array of intervals where intervals[i] = [starti, endi], merge all overlapping intervals, and return an
 * array of the non-overlapping intervals that cover all the intervals in the input.
 * <p>
 * Example 1:
 * <p>
 * Input: intervals = [[1,3],[2,6],[8,10],[15,18]]
 * Output: [[1,6],[8,10],[15,18]]
 * Explanation: Since intervals [1,3] and [2,6] overlap, merge them into [1,6].
 * <p>
 * Example 2:
 * <p>
 * Input: intervals = [[1,4],[4,5]]
 * Output: [[1,5]]
 * Explanation: Intervals [1,4] and [4,5] are considered overlapping.
 * <p>
 * https://leetcode.com/problems/merge-intervals/
 */
public class MergeIntervals {
    public int[][] merge(int[][] intervals) {
        final int size = intervals.length;
        if (size <= 1) {
            return intervals;
        }

        Arrays.sort(intervals, Comparator.comparingInt(arr -> arr[0]));

        final List<int[]> results = new ArrayList<>();
        int[] currentInterval = intervals[0];
        results.add(currentInterval);

        for (int i = 1; i < size; i++) {
            final int[] interval = intervals[i];
            final int nextStart = interval[0];
            final int nextEnd = interval[1];
            final int currentEnd = currentInterval[1];
            if (currentEnd >= nextStart) {
                currentInterval[1] = Integer.max(currentEnd, nextEnd);
            } else {
                currentInterval = interval;
                results.add(currentInterval);
            }
        }
        return results.toArray(new int[results.size()][]);
    }
}
