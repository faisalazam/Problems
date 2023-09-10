package com.algorithms.geeksforgeeks.easy.sorting;

public class MergeSort {
    /**
     * The task is to complete merge() function which is used to implement Merge Sort.
     * <p>
     * User Task:
     * You don't need to take the input or print anything. Your task is to complete the function merge()
     * which takes the array arr[], the starting position of the first array (l),
     * the ending position of the first array (m) and the ending position of the second array (r) as
     * its inputs and modifies the array arr[] such that it is sorted from position l to position r.
     * <p>
     * Expected Auxiliary Space: O(n)
     * Expected Time Complexity: O(n)  (for the merge() function)
     * <p>
     * https://practice.geeksforgeeks.org/problems/merge-sort/1
     */
    private void mergeSort(int[] arr, int l, int r) {
        if (l < r) {
            int m = (l + r) / 2;
            mergeSort(arr, l, m);
            mergeSort(arr, m + 1, r);
            merge(arr, l, m, r);
        }
    }

    // Merges two subarrays of arr[].  First subarray is arr[l..m]
    // Second subarray is arr[m+1..r]
    private void merge(int[] arr, int l, int m, int r) {
        int i = l;
        int j = m + 1;
        int k = 0;
        int[] temp = new int[r - l + 1];
        while (i <= m && j <= r) {
            if (arr[i] <= arr[j]) {
                temp[k++] = arr[i++];
            } else {
                temp[k++] = arr[j++];
            }
        }
        while (i <= m) {
            temp[k++] = arr[i++];
        }
        while (j <= r) {
            temp[k++] = arr[j++];
        }
        k = 0;
        for (int c = l; c <= r; c++) {
            arr[c] = temp[k++];
        }
    }
}
