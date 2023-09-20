package com.algorithms.leetcode.medium;

import java.util.List;

public class MinimumTimeDifference {
    private static final int NUMBER_OF_MINS_IN_24_HOURS = 24 * 60;

    /*
     * Given a list of 24-hour clock time points in "Hour:Minutes" format,
     * find the minimum minutes difference between any two time points in the list.
     *
     * https://leetcode.com/problems/minimum-time-difference/
     */
    public int findMinDifference(List<String> timePoints) {
        boolean[] timeSeen = new boolean[NUMBER_OF_MINS_IN_24_HOURS];

        for (String timePoint : timePoints) {
            int timeInMinutes = getTimeInMinutes(timePoint);
            if (timeSeen[timeInMinutes]) {
                // as we've seen the exact same time twice, so they won't have any difference as no other difference can
                // be smaller than 0 (i.e. difference between the same times is 0).
                return 0;
            }
            timeSeen[timeInMinutes] = true;
        }

        Integer firstTimeSeen = null;
        Integer prevTimeSeen = null;
        int minDifference = Integer.MAX_VALUE;
        for (int currentTimeInMinutes = 0; currentTimeInMinutes < NUMBER_OF_MINS_IN_24_HOURS; currentTimeInMinutes++) {
            if (timeSeen[currentTimeInMinutes]) {
                if (firstTimeSeen == null) {
                    firstTimeSeen = currentTimeInMinutes;
                    prevTimeSeen = currentTimeInMinutes;
                } else {
                    minDifference = calculateMinDifference(minDifference, currentTimeInMinutes, prevTimeSeen);
                    prevTimeSeen = currentTimeInMinutes;
                }
            }
        }
        // minDifference should have already been calculated by the previous for loop, unless the first time is closer
        // to prev time, i.e. (23:59, 00:00)
        minDifference = calculateMinDifference(minDifference, firstTimeSeen, prevTimeSeen);
        return minDifference;
    }

    private int calculateMinDifference(int minDifference, int currentTimeInMinutes, Integer prevTimeSeen) {
        int clockWiseDifference = currentTimeInMinutes - prevTimeSeen;
        int counterClockWiseDifference = NUMBER_OF_MINS_IN_24_HOURS - currentTimeInMinutes + prevTimeSeen;
        return Math.min(
                minDifference, Math.min(
                        clockWiseDifference, counterClockWiseDifference
                )
        );
    }

    private int getTimeInMinutes(String timePoint) {
        String[] timeSplit = timePoint.split(":");
        int hour = Integer.parseInt(timeSplit[0]);
        int minutes = Integer.parseInt(timeSplit[1]);
        return (hour * 60) + minutes;
    }
}
