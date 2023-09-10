package com.algorithms.geeksforgeeks.basic.binarysearch;

public class BinarySearch {
    /**
     * Given a sorted array A[](0 based index) and a key "k"  you need to complete the function bin_search to
     * determine the position of the key if the key is present in the array.
     * If the key is not  present then you have to return -1. The arguments left and right denotes the left most index
     * and right most index of the array A[].
     * <p>
     * https://practice.geeksforgeeks.org/problems/binary-search-1587115620/1
     * <p>
     * Class:	Search algorithm
     * Data structure:	Array
     * Worst case performance:	 O(log n)
     * Best case performance:	 O(1)
     * Average case performance: O(log n)
     * Worst case space complexity:	O(1) iterative, O(log n) recursive (without tail call elimination)
     */
    int binarySearch(int[] A, int left, int right, int k) {
        // left + right can overflow if the values are too high for their int representation.
        // You have left < right by definition.
        // As a consequence, right - left > 0, and furthermore right can be written as:
        // left - left + right = left + (right - left) = right (follows from basic algebra).
        // And consequently left + ((right - left) / 2) <= right. So no overflow can happen since every step of the
        // operation is bounded by the value of right.
        // By contrast, consider the buggy expression, (left + right) / 2. left + right >= right, and since we don’t
        // know the values of left and right, it’s entirely possible that that value overflows.
        //
        // That is done to handle the overflow of numbers sum in languages such as C and C++.
        // You have a limit on the integer range. In
        //                int mid = (low+high)/2;
        // We calculate the sum first and divide later, which might overflow buffer for large values of low and high.
        //
        // This overflow condition is handled by calculating the difference first and then dividing the difference
        // and adding low to it. Which results in,
        //                int mid = low + (high-low)/2;
        //
        // Suppose (to make the example easier) the maximum integer value is 100, left = 50, and right = 80.
        // If you use the naive formula: int mid = (left + right)/2;
        // the addition will result in 130, which overflows as we mentioned the maximum integer value is 100.
        //
        // If you instead do:
        // int mid = left + (right - left)/2;
        // you can't overflow in (right - left) because you're subtracting a smaller number from a larger number.
        // That always results in an even smaller number, so it can't possibly go over the maximum. E.g. 80 - 50 = 30.
        while (left <= right) {
            int mid = left + ((right - left) / 2);
            if (A[mid] == k) {
                return mid;
            } else if (k < A[mid]) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return -1;
    }

    int binarySearchRecursive(int[] A, int left, int right, int k) {
        final int mid = left + ((right - left) / 2);
        final int midValue = A[mid];
        if (k == midValue) {
            return mid;
        } else if (k < midValue) {
            binarySearchRecursive(A, left, mid - 1, k);
        } else {
            binarySearchRecursive(A, mid + 1, right, k);
        }
        return -1;
    }
}
