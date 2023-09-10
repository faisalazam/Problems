package com.algorithms.geeksforgeeks.easy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NMeetingsInOneRoom {
    /**
     * There is one meeting room in a firm. There are N meetings in the form of (S[i], F[i])
     * where S[i] is start time of meeting i and F[i] is finish time of meeting i.
     * <p>
     * What is the maximum number of meetings that can be accommodated in the meeting room?
     * <p>
     * https://practice.geeksforgeeks.org/problems/n-meetings-in-one-room/0
     */
    private static void meetings(int[] S, int[] F, int N) {
        List<Meeting> meetings = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            meetings.add(new Meeting(S[i], F[i], i));
        }
        Collections.sort(meetings);
        int i = 0;
        StringBuilder builder = new StringBuilder();
        builder.append(meetings.get(i).index + 1).append(" ");
        for (int j = 1; j < N; j++) {
            if (meetings.get(j).start >= meetings.get(i).finish) {
                builder.append(meetings.get(j).index + 1).append(" ");
                i = j;
            }
        }
        System.out.println(builder.toString());
    }

    private static class Meeting implements Comparable<Meeting> {
        private int start;
        private int finish;
        private int index;

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
}
