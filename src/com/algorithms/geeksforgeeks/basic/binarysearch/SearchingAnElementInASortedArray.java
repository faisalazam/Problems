package com.algorithms.geeksforgeeks.basic.binarysearch;

public class SearchingAnElementInASortedArray {
    /**
     * Given a sorted array arr[] of N integers and a number K is given. The task is to check
     * if the element K is present in the array or not.
     * <p>
     * https://practice.geeksforgeeks.org/problems/who-will-win/0
     */
    public static int search(int K, int N, int[] A) {
        int start = 0;
        int end = N - 1;
        while (start <= end) {
            int mid = start + ((end - start) / 2);
            if (K == A[mid]) {
                return 1;
            } else if (K < A[mid]) {
                end = mid - 1;
            } else {
                start = mid + 1;
            }
        }
        return -1;
    }
}
