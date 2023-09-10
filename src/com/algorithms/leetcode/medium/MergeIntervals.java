package com.algorithms.leetcode.medium;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class MergeIntervals {
    /**
     * Given a collection of intervals, merge all overlapping intervals.
     * <p>
     * https://leetcode.com/problems/merge-intervals/
     */
    public int[][] merge(int[][] intervals) {
        if (intervals.length <= 1) {
            return intervals;
        }

        Arrays.sort(intervals, Comparator.comparingInt(arr -> arr[0]));

        List<int[]> results = new ArrayList<>();
        int[] currentInterval = intervals[0];
        results.add(currentInterval);

        for (int[] interval : intervals) {
            int currentEnd = currentInterval[1];
            int nextStart = interval[0];
            int nextEnd = interval[1];
            if (currentEnd >= nextStart) {
                currentInterval[1] = Math.max(currentEnd, nextEnd);
            } else {
                currentInterval = interval;
                results.add(currentInterval);
            }
        }
        return results.toArray(new int[results.size()][]);
    }
}
