package com.algorithms.misc;

import java.util.ArrayList;
import java.util.List;

public class NumberOfOccurrencesFinder {
    public static void main(String[] args) {
        int B = 3;
        List<Integer> A = new ArrayList<>();
        A.add(1);
        A.add(1);
        A.add(2);
        A.add(2);
        A.add(2);
        A.add(3);
        A.add(3);
        A.add(3);
        A.add(3);
        A.add(4);
        System.out.println(findCount(A, B));
    }

    // Find number of occurrences of an int in a sorted int collection. DO NOT MODIFY THE LIST. IT IS READ ONLY
    private static int findCount(final List<Integer> A, int B) {
        final int minIndex = findIndex(A, B, true);
        if (minIndex == -1) {
            return 0;
        }
        final int maxIndex = findIndex(A, B, false);
        return maxIndex - minIndex + 1;
    }

    private static int findIndex(final List<Integer> A, int B, boolean minIndex) {
        int result = -1;
        int low = 0;
        int high = A.size() - 1;
        while (low <= high) {
            int mid = low + ((high - low) / 2);
            if (A.get(mid) == B) {
                result = mid;
                if (minIndex) {
                    high = mid - 1;
                } else {
                    low = mid + 1;
                }
            } else if (B < A.get(mid)) {
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        return result;
    }
}
