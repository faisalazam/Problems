package com.algorithms.geeksforgeeks.easy;

public class SortAnArrayOf0sAnd1sAnd2s {
    /**
     * Given an array A of size N containing 0s, 1s, and 2s; you need to sort the array in ascending order.
     * <p>
     * https://practice.geeksforgeeks.org/problems/sort-an-array-of-0s-1s-and-2s/0
     */
    public static void sort(int N, int[] A) {
        int lo = 0;
        int mid = 0;
        int high = N - 1;
        while (mid <= high) {
            switch (A[mid]) {
                case 0:
                    A[mid] = A[lo];
                    A[lo] = 0;
                    lo++;
                    mid++;
                    break;
                case 1:
                    mid++;
                    break;
                case 2:
                    A[mid] = A[high];
                    A[high] = 2;
                    high--;
                    break;
            }
        }


        //This is also a working/submitted solution
        // int zeros = 0;
        // int ones = 0;
        // int twos = 0;
        // for (int i = 0; i < N; i++) {
        //     switch (A[i]) {
        //         case 0:
        //             zeros++;
        //             break;
        //         case 1:
        //             ones++;
        //             break;
        //         case 2:
        //             twos++;
        //             break;
        //     }
        // }
        // for (int i = 0; i < N; i++) {
        //     if (i < zeros) {
        //         A[i] = 0;
        //     } else if (i < zeros + ones) {
        //         A[i] = 1;
        //     } else {
        //         A[i] = 2;
        //     }
        // }
    }
}
