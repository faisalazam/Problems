package com.algorithms.ctci.normal.arrayandstrings;

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
 * <p>
 * https://practice.geeksforgeeks.org/problems/search-in-a-matrix-1587115621/1
 */
public class SortedMatrixSearch {
    /**
     * Brute Force Or Naive approach: To solve the problem follow the below idea:
     * <p>
     * The simple idea is to traverse the array and search elements one by one
     * <p>
     * Time Complexity: O(N²), assuming M = N, otherwise O(M + N)
     * Auxiliary Space: O(1), since no extra space has been taken
     */
    boolean findElementV0(int[][] matrix, int rows, int columns, int targetValue) {
        if (rows == 0 || columns == 0) {
            return false;
        }

        for (int[] row : matrix) {
            for (int j = 0; j < columns; j++) {
                if (row[j] == targetValue) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Search in a row-wise and column-wise sorted matrix in linear time complexity:
     * <p>
     * The simple idea is to remove a row or column in each comparison until an element is found. Start searching from
     * the top-right corner of the matrix. There are three possible cases:-
     * <p>
     * The given number is greater than the current number: This will ensure that all the elements in the current row
     * are smaller than the given number as the pointer is already at the right-most elements and the row is sorted.
     * Thus, the entire row gets eliminated and continues the search for the next row. Here, elimination means that a
     * row needs not to be searched.
     * The given number is smaller than the current number: This will ensure that all the elements in the current column
     * are greater than the given number. Thus, the entire column gets eliminated and continues the search for the
     * previous column, i.e. the column on the immediate left.
     * The given number is equal to the current number: This will end the search.
     * <p>
     * Time Complexity: O(N), Only one traversal is needed, i.e, i from 0 to n and j from n-1 to 0 with at most
     * 2*N steps. The above approach will also work for the M x N matrix (not only for N x N). Complexity would be
     * O(M + N)
     * Auxiliary Space: O(1), No extra space is required
     */
    boolean findElementV1(int[][] matrix, int rows, int columns, int targetValue) {
        int row = 0;
        int col = columns - 1;
        while (row < rows && col >= 0) {
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

    /**
     * Using binary search in 2 dimensions
     * <p>
     * Approach: We can observe that any number (say k) that we want to find, must exist within a row, including the
     * first and last elements of the row (if it exists at all). So we first find the row in which k must lie using
     * binary search ( O(log N) ) and then use binary search again to search in that row( O(log M) ).
     * <p>
     * Algorithm:
     * <p>
     * 1) first we’ll find the correct row, where k=2 might exist. To do this we will simultaneously apply binary search
     * on the first and last column.
     * *   low=0, high=n-1
     * *   i) if( k< first element of row(a[mid][0]) ) => k must exist in the row above
     * *                                            => high=mid-1;
     * *   ii) if( k> last element of row(a[mid][m-1])) => k must exist in the row below
     * *                                             => low=mid+1;
     * *   iii) if( k> first element of row(a[mid][0]) &&  k< last element of row(a[mid][m-1]))
     * *                                             => k must exist in this row
     * *                                             => apply binary search in this row like in a 1-D array
     * *   iv) i) if( k== first element of row(a[mid][0]) ||  k== last element of row(a[mid][m-1])) => found
     * <p>
     * Time Complexity: O(log n + log m).
     * Auxiliary Space: O(1)
     */
    boolean findElement(int[][] matrix, int targetValue) {
        return findElement(matrix, matrix.length, matrix[0].length, targetValue);
    }

    private static boolean findElement(int[][] matrix, int rows, int columns, int targetValue) { // Wrong Answer. !!!
//        Possibly your code does not work correctly for multiple test-cases (TCs).
        int middleRow;
        int low = 0;
        int high = rows - 1;

        while (low <= high) {
            middleRow = low + ((high - low) / 2);

            final int firstValue = matrix[middleRow][0];
            final int lastValue = matrix[middleRow][columns - 1];
            if (targetValue == firstValue // checking leftmost element
                    || targetValue == lastValue) { // checking rightmost element
                return true;
            }

            if (firstValue <= targetValue && targetValue <= lastValue) { // this means the element must be within this row
                return binarySearch(matrix, middleRow, columns, targetValue); // apply binary search as we do in 1-D array
            }

            if (targetValue < firstValue) {
                high = middleRow - 1;
            }
            if (targetValue > lastValue) {
                low = middleRow + 1;
            }
        }
        return false;
    }

    private static boolean binarySearch(int[][] matrix, int row, int columns, int targetValue) {
        int mid;
        int left = 0;
        int right = columns - 1;
        while (left <= right) {
            mid = left + ((right - left) / 2);

            if (matrix[row][mid] == targetValue) {
                return true;
            } else if (matrix[row][mid] > targetValue) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return false;
    }

    static boolean findElementUsingEuclideanDivision(int[][] matrix, int rows, int columns, int targetValue) { // Not tested
        int mid;
        int topLeft = 0;
        int bottomRight = ((rows - 1) * columns) + (columns - 1); // using Euclidean Division
        while (topLeft <= bottomRight) {
            mid = topLeft + ((bottomRight - topLeft) / 2);
            final int row;
            final int column;
            if (mid == 0) {
                row = 0;
                column = 0;
            } else {
                row = mid / columns;
                column = mid % columns;
            }

            if (matrix[row][column] == targetValue) {
                return true;
            } else if (matrix[row][column] > targetValue) {
                bottomRight = mid - 1;
            } else {
                topLeft = mid + 1;
            }
        }
        return false;
    }

    /*
     * See for more details / solutions:
     * https://www.geeksforgeeks.org/search-element-sorted-matrix/
     *
     * Another solution is "Binary Search Every Row => O(m log n)"
     */
}
