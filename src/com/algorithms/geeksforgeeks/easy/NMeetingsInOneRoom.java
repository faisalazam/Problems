package com.algorithms.geeksforgeeks.easy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * There is one meeting room in a firm. There are N meetings in the form of (start[i], end[i]) where start[i] is
 * start time of meeting i and end[i] is finish time of meeting i.
 * What is the maximum number of meetings that can be accommodated in the meeting room when only one meeting can be
 * held in the meeting room at a particular time?
 * <p>
 * Note: Start time of one chosen meeting can't be equal to the end time of the other chosen meeting.
 * <p>
 * https://practice.geeksforgeeks.org/problems/n-meetings-in-one-room/0
 */
public class NMeetingsInOneRoom {
    /**
     * Greedy approach for maximum meetings in one room:
     * <p>
     * The idea is to solve the problem using the greedy approach which is the same as Activity Selection Problem
     * i.e sort the meetings by their finish time and then start selecting meetings, starting with the one with the least
     * end time and then select other meetings such that the start time of the current meeting is greater than the end
     * time of last meeting selected
     * <p>
     * Time Complexity : O(N * logN)
     * Auxiliary Space : O(N)
     */
    private static int meetings(int[] start, int[] end, int n) {
        if (start == null || end == null || n == 0) {
            return 0;
        }
        int last = 0;
        int count = 1;
        final List<Meeting> meetings = sortMeetings(start, end, n);
        for (int current = 1; current < n; current++) {
            if (meetings.get(current).start > meetings.get(last).finish) {
                last = current;
                count++;
            }
        }
        return count;
    }

    /**
     * Brute Force
     */
    public int maxMeetingsV0(int[] start, int[] end, int n) {
        if (start == null || end == null || n == 0) {
            return 0;
        }
        int max = 1;
        final int[] counts = new int[n];
        final List<Meeting> meetings = sortMeetings(start, end, n);
        for (int next = 1; next < n; next++) {
            for (int prev = next - 1; prev >= 0; prev--) {
                if (meetings.get(next).start > meetings.get(prev).finish) {
                    counts[next] = Math.max(counts[next], counts[prev] + 1); // keeping the max count for each index
                    max = Math.max(max, counts[next] + 1);
                }
            }
        }
        return max;
    }

    private static List<Meeting> sortMeetings(final int[] start, final int[] end, final int n) {
        final List<Meeting> meetings = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            meetings.add(new Meeting(start[i], end[i], i));
        }
        Collections.sort(meetings);
        return meetings;
    }

    private static class Meeting implements Comparable<Meeting> {
        private final int start;
        private final int finish;
        private final int index;

        private Meeting(int start, int finish, int index) {
            this.start = start;
            this.finish = finish;
            this.index = index;
        }

        public int compareTo(Meeting meeting) {
            return Integer.compare(finish, meeting.finish);
//            if (finish == meeting.finish) {
//                return 0;
//            } else if (finish > meeting.finish) {
//                return 1;
//            } else {
//                return -1;
//            }
        }
    }

    public static void main(String[] args) {
        final int[] start = new int[]{75250, 50074, 43659, 8931, 11273, 27545, 50879, 77924};
        final int[] end = new int[]{112960, 114515, 81825, 93424, 54316, 35533, 73383, 160252};
        System.out.println(meetings(start, end, start.length));
        System.out.println(new NMeetingsInOneRoom().maxMeetingsV0(start, end, start.length));
    }
}
