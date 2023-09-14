package com.algorithms.ctci.normal.arrayandstrings;

public class SortedMatrixSearch {
    /**
     * Sorted Matrix Search: Given an M x N matrix in which each row and each column is sorted in ascending order,
     * write a method to find an element.
     * <p>
     * Solution #1: Naive Solution
     * As a first approach, we can do binary search on every row to find the element.
     * This algorithm will be 0(M log(N)), since there are M rows and it takes O(log(N)) time to search each one.
     * <p>
     * Solution #2: Binary Search (Not implemented below)
     * We want to be able to leverage the sorting property to more efficiently find an element.
     * So, we might ask ourselves, what does the unique ordering property of this matrix imply about where an element
     * might be located? We are told that every row and column is sorted. This means that element a[i][j] will be greater
     * than the elements in row i between columns 0 and j - 1 and the elements in column j between rows 0 and i - 1.
     * This means that for any rectangle we draw in the matrix, the bottom right hand corner will always be the biggest.
     * Likewise, the top left hand corner will always be the smallest.
     */
    boolean findElement(int[][] matrix, int targetValue) {
        int row = 0;
        int col = matrix[0].length - 1;
        while (row < matrix.length && col >= 0) {
            if (matrix[row][col] == targetValue) {
                return true;
            } else if (matrix[row][col] > targetValue) {
                col--;
            } else {
                row++;
            }
        }
        return false;
    }
}
