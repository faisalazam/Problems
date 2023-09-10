package com.algorithms.geeksforgeeks.basic.binarysearch;

public class NumberOfOccurrence {
    /**
     * Given a sorted array A of size N and a number X, you need to find the number of occurrences of X in A.
     * <p>
     * https://practice.geeksforgeeks.org/problems/number-of-occurrence/0
     */
    public static int noOfOccurrences(int K, int N, int[] A) {
        int firstIndex = findIndex(K, N, A, true);
        if (firstIndex != -1) {
            int lastIndex = findIndex(K, N, A, false);
            return (lastIndex - firstIndex) + 1;
        }
        return 0;
    }

    private static int findIndex(int K, int N, int[] A, boolean findFirst) {
        int index = -1;
        int start = 0;
        int end = N - 1;
        while (start <= end) {
            int mid = start + ((end - start) / 2);
            if (K == A[mid]) {
                index = mid;
                if (findFirst) {
                    end = mid - 1;
                } else {
                    start = mid + 1;
                }
            } else if (K < A[mid]) {
                end = mid - 1;
            } else {
                start = mid + 1;
            }
        }
        return index;
    }
}
