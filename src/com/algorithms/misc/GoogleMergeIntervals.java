package com.algorithms.misc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class GoogleMergeIntervals {
    /*
Given an ordered disjoint list of intervals and another interval, construct a union of the intervals,
so that the result is also an ordered disjoint list of intervals.

Example:

union([{10, 20}, {50, 60}],
      {23, 38})
    = [{10, 20}, {23, 38}, {50, 60}]

union([{10, 20}, {25, 28}, {30, 33}, {35, 40}, {50, 60}],
      {23, 38})
    = [{10, 20}, {23, 40}, {50, 60}]


union([{10, 20}, {50, 60}],
      {70, 78})
    = [{10, 20}, {50, 60}]


union([{10, 20}, {50, 60}],
      {5, 9})
    = [{10, 20}, {50, 60}]
*/

    public static void main(String[] str) {
        List<Interval> intervals = new ArrayList<>();
        intervals.add(new Interval(10, 20));
        intervals.add(new Interval(30, 40));
        intervals.add(new Interval(50, 60));
        intervals.add(new Interval(70, 80));

        Interval targetInterval = new Interval(83, 88);
        List<Interval> union = union(intervals, targetInterval);
        unionImproved(intervals, targetInterval);
        System.out.println();
    }

    private static final Comparator<Interval> inRangeComp = (a, b) -> (
            a.getFinish() < b.getStart())
            ? -1 // i.e. first argument is less than second argument
            : (
            (a.getStart() > b.getFinish())
                    ? 1 // i.e. first argument is greater than second argument
                    : 0 // i.e. first argument is equal to second argument
    );

    private static List<Interval> unionImproved(List<Interval> intervals, Interval newInterval) {
        final int intervalStartIndex = insertIfPossible(intervals, newInterval.getStart());
        final int intervalFinishIndex = insertIfPossible(intervals, newInterval.getFinish());
        if (intervalStartIndex != intervalFinishIndex) {
            final Interval interval = intervals.get(intervalStartIndex);
            interval.finish = intervals.get(intervalFinishIndex).getFinish();
            intervals.set(intervalStartIndex, interval);
            intervals.subList(intervalStartIndex + 1, intervalFinishIndex + 1).clear();
        }
        return intervals;
    }

    private static int insertIfPossible(List<Interval> intervals, int startFinish) {
        final Interval interval = new Interval(startFinish, startFinish);
        int index = Collections.binarySearch(intervals, interval, inRangeComp);
        if (index < 0) {
            index = -(index + 1);
            intervals.add(index, interval);
        }
        return index;
    }

    private static class Interval { // immutable - not - because finish is not final
        private final int start;
        private int finish;

        private Interval(int start, int finish) {
            this.start = start;
            this.finish = finish;
        }

        private int getStart() {
            return start;
        }

        private int getFinish() {
            return finish;
        }
    }


    private static List<Interval> union(List<Interval> intervals, Interval targetInterval) {
        //place some null/empty checks -> return emptylist
        boolean isTargetProcessed = false;
        final List<Interval> results = new ArrayList<>();

        for (int i = 0; i < intervals.size(); i++) {
            final Interval interval = intervals.get(i);
            if (isTargetProcessed || interval.getFinish() < targetInterval.getStart()) {
                results.add(interval);
            } else if (interval.getStart() > targetInterval.getFinish()) { //
                results.add(targetInterval);
                results.add(interval); // I didn't have this statement in interview
                isTargetProcessed = true;
            } else { //overlapping
                Interval tempInterval = interval;
                while (targetInterval.getFinish() > tempInterval.getStart()) {
                    i++;
                    tempInterval = intervals.get(i); // I didn't have this statement in interview
                }
                results.add(new Interval(
                        Math.min(targetInterval.getStart(), interval.getStart()),
                        Math.max(targetInterval.getFinish(), intervals.get(i - 1).getFinish())
                ));
                results.add(tempInterval); // I didn't have this statement in interview
                isTargetProcessed = true;
            }
        }
        if (!isTargetProcessed) {
            results.add(targetInterval);
        }

        return results;
    }
}
