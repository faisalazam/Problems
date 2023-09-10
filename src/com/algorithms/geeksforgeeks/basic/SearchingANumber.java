package com.algorithms.geeksforgeeks.basic;

public class SearchingANumber {
    /**
     * Given an array of N elements and a integer K. Your task is to return the position of first occurrence
     * of K in the given array.
     * Note: Position of first element is considered as 1.
     * <p>
     * https://practice.geeksforgeeks.org/problems/searching-a-number/0
     */
    int search(int[] A, int N, int K) {
        for (int i = 0; i < N; i++) {
            if (A[i] == K) {
                return i + 1;
            }
        }
        return -1;
    }
}
