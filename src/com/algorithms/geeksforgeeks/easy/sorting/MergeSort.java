package com.algorithms.geeksforgeeks.easy.sorting;

/**
 * Given an array arr[], its starting position l and its ending position r. Sort the array using merge sort algorithm.
 * <p>
 * User Task:
 * Your task is to complete the function merge() which takes arr[], l, m, r as its input parameters and modifies arr[]
 * in-place such that it is sorted from position l to position r, and function mergeSort() which uses merge() to sort
 * the array in ascending order using merge sort algorithm.
 * <p>
 * https://practice.geeksforgeeks.org/problems/merge-sort/1
 * <p>
 * The Merge Sort algorithm is a sorting algorithm that is considered an example of the divide and conquer strategy.
 * So, in this algorithm, the array is initially divided into two equal halves and then they are combined in a sorted
 * manner. We can think of it as a recursive algorithm that continuously splits the array in half until it cannot be
 * further divided. This means that if the array becomes empty or has only one element left, the dividing will stop,
 * i.e. it is the base case to stop the recursion. If the array has multiple elements, we split the array into halves
 * and recursively invoke the merge sort on each of the halves. Finally, when both the halves are sorted, the merge
 * operation is applied. Merge operation is the process of taking two smaller sorted arrays and combining them to
 * eventually make a larger one.
 * <p>
 * Applications of Merge Sort:
 * <p>
 * Sorting large datasets: Merge sort is particularly well-suited for sorting large datasets due to its guaranteed
 * worst-case time complexity of O(n log n).
 * External sorting: Merge sort is commonly used in external sorting, where the data to be sorted is too large to fit
 * into memory.
 * Custom sorting: Merge sort can be adapted to handle different input distributions, such as partially sorted, nearly
 * sorted, or completely unsorted data.
 * Inversion Count Problem
 * <p>
 * Advantages of Merge Sort:
 * <p>
 * Stability: Merge sort is a stable sorting algorithm, which means it maintains the relative order of equal elements in
 * the input array.
 * Guaranteed worst-case performance: Merge sort has a worst-case time complexity of O(N logN), which means it performs
 * well even on large datasets.
 * Parallelizable: Merge sort is a naturally parallelizable algorithm, which means it can be easily parallelized to take
 * advantage of multiple processors or threads.
 * <p>
 * Drawbacks of Merge Sort:
 * <p>
 * Space complexity: Merge sort requires additional memory to store the merged sub-arrays during the sorting process.
 * Not in-place: Merge sort is not an in-place sorting algorithm, which means it requires additional memory to store the
 * sorted data. This can be a disadvantage in applications where memory usage is a concern.
 * Not always optimal for small datasets: For small datasets, Merge sort has a higher time complexity than some other
 * sorting algorithms, such as insertion sort. This can result in slower performance for very small datasets.
 * <p>
 * Merge Sort with O(1) extra space merge and O(n log n) time [Unsigned Integers Only] -
 * explore Approach: (Euclidean Division)
 * <p>
 * Expected Time Complexity: O(n * log(n)) - The time complexity of Merge Sort is O(n * log(n)) in all 3 cases (worst,
 * average and best) as merge sort always divides the array into two halves and takes linear time to merge two halves.
 * Expected Auxiliary Space: O(n) - In merge sort all elements are copied into an auxiliary array, so N auxiliary space
 * is required for merge sort.
 */
public class MergeSort {
    private void mergeSort(int[] arr, int l, int r) {
        if (l < r) {
            int m = l + ((r - l) / 2);
            mergeSort(arr, l, m);
            mergeSort(arr, m + 1, r);
            merge(arr, l, m, r);
        }
    }

    /**
     * Merges two sub-arrays of arr[]. First subarray is arr[l..m], whereas the second subarray is arr[m+1..r]
     */
    private void merge(final int[] arr, final int l, final int m, final int r) {
        int i = l;
        int j = m + 1;
        int k = 0;
        final int[] temp = new int[r - l + 1];
        while (i <= m && j <= r) {
            if (arr[i] <= arr[j]) {
                temp[k++] = arr[i++];
            } else {
                temp[k++] = arr[j++];
            }
        }
        while (i <= m) { // if there are any elements remaining in the 1st half of the array
            temp[k++] = arr[i++];
        }
        while (j <= r) { // if there are any elements remaining in the 2nd half of the array
            temp[k++] = arr[j++];
        }
        k = 0;
        for (int c = l; c <= r; c++) { // overwrite the original array with the sorted values stored in the temp array
            arr[c] = temp[k++];
        }
    }
}
