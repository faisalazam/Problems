package com.algorithms.geeksforgeeks.easy.sorting;

import java.util.Stack;

/**
 * Quick Sort is a Divide and Conquer algorithm. It picks an element as a pivot and partitions the given array
 * around the picked pivot. Given an array arr[], its starting position is low (the index of the array) and its
 * ending position is high(the index of the array).
 * <p>
 * Note: The low and high are inclusive.
 * <p>
 * Implement the partition() and quickSort() functions to sort the array.
 * <p>
 * Your Task:
 * You don't need to read input or print anything. Your task is to complete the functions partition() and
 * quickSort() which takes the array arr[], low and high as input parameters and partitions the array. Consider
 * the last element as the pivot such that all the elements less than(or equal to) the pivot lie before it and the
 * elements greater than it lie after the pivot.
 * <p>
 * QuickSort is a Divide and Conquer algorithm.
 * <p>
 * It picks an element as a pivot and partitions the given array around the picked pivot. There are many different
 * versions of quickSort that pick pivots in different ways.
 * <p>
 * Always pick the first element as a pivot.
 * Always pick the last element as a pivot (we are using this in this editorial).
 * Pick a random element as a pivot.
 * Pick the median as the pivot.
 * <p>
 * The key process in quickSort is a partition(). The target of partitions is, given an array and an element x of an
 * array as the pivot, put x at its correct position in a sorted array and put all smaller elements (smaller than x)
 * before x, and put all greater elements (greater than x) after x. All this should be done in linear time.
 * <p>
 * Advantages of Quick Sort:
 * <p>
 * It is a divide-and-conquer algorithm that makes it easier to solve problems.
 * It is efficient on large data sets.
 * It has a low overhead, as it only requires a small amount of memory to function.
 * <p>
 * Disadvantages of Quick Sort:
 * <p>
 * It has a worst-case time complexity of O(N2), which occurs when the pivot is chosen poorly.
 * It is not a good choice for small data sets.
 * It is not a stable sort, meaning that if two elements have the same key, their relative order will not be preserved
 * in the sorted output in case of quick sort, because here we are swapping elements according to the pivot’s position
 * (without considering their original positions).
 * <p>
 * Time Complexity:
 * Worst Case: O(N2), The worst case occurs when the partition process always picks the greatest or smallest element
 * as the pivot. If we consider the above partition strategy where the last element is always picked as a pivot, the
 * worst case would occur when the array is already sorted in increasing or decreasing order
 * Best Case: O(NlogN), The best case occurs when the partition process always picks the middle element as the pivot.
 * where N is the size of the array for placing the pivot element it takes N and the total number of partitions is
 * approx logN in the best case so the overall complexity is O(NlogN)
 * Average Case: O(NlogN)
 * Space Complexity: O(logN), Recursion stack takes logN space and in the worst case quicksort could make O(N)..
 * <p>
 * https://practice.geeksforgeeks.org/problems/quick-sort/1
 * https://practice.geeksforgeeks.org/problems/sort-the-array/0
 */
public class QuickSort {
    private static void quickSort(int[] arr, int low, int high) {
        if (low < high) {
            final int partitionIndex = partition(arr, low, high);
            quickSort(arr, low, partitionIndex - 1);
            quickSort(arr, partitionIndex + 1, high);
        }
    }

    static void quickSortIterative(int[] arr, int low, int high) {
        final Stack<Integer> stack = new Stack<>();

        // push initial values of low and high to stack
        stack.push(low);
        stack.push(high);

        while (!stack.isEmpty()) {
            high = stack.pop();
            low = stack.pop();

            // Set pivot element at its correct position in sorted array
            final int partitionIndex = partition(arr, low, high);

            // If there are elements on left side of pivot, then push left side to stack
            if (partitionIndex - 1 > low) {
                stack.push(low);
                stack.push(partitionIndex - 1);
            }

            // If there are elements on right side of pivot, then push right side to stack
            if (partitionIndex + 1 < high) {
                stack.push(partitionIndex + 1);
                stack.push(high);
            }
        }
    }

    /**
     * This function takes last element as pivot, places the pivot element at its correct position in sorted array,
     * and places all smaller (smaller than pivot) to left of pivot and all greater elements to right of pivot
     */
    private static int partition(int[] arr, int low, int high) {
        final int pivot = arr[high];
        int partitionIndex = low;
        for (int i = low; i < high; i++) {// 2 5 3 6 4
            if (arr[i] <= pivot) {
                swap(arr, i, partitionIndex++);
            }
        }
        swap(arr, partitionIndex, high);
        return partitionIndex;
    }

    /**
     * a = a - b;
     * b = b + a;
     * a = b - a;
     * <p>
     * if swapping array elements using this approach, then ensure that i != j, because this approach will result
     * in 0 when i == j.
     */
    private static void swap(int[] arr, int i, int j) {
        final int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}
