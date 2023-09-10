package com.algorithms.misc;

public class SquareSubArrayFinder {
    public static void main(String[] args) {
        boolean[][] matrix = {
                {false, true, false, false},
                {true, true, true, true},
                {false, true, true, false}
        };
        System.out.println("Result from Top Down => " + largestSquareSubArrayTopDown(true, matrix));
        System.out.println("Result from Bottom Up => " + largestSquareSubArrayBottomUp(true, matrix));
    }

    /**
     * Given a 2D boolean array, find the largest square subarray of true values.
     * The return value should be the side length of the largest square subarray subarray.
     * <p>
     * e.g.
     * arr =
     * False True  False False
     * True  True  True  True
     * False True  True  False
     * <p>
     * squareSubmatrix(arr) = 2
     */
    //Better Square Submatrix solution => iterative => O(n * m) and space complexity O(n * m) due to cached result
    private static int largestSquareSubArrayBottomUp(boolean targetValue, boolean[][] matrix) {
        int maxSquareSubArray = 0;
        int noOfRows = matrix.length;
        int noOfColumns = matrix[0].length;
        int[][] resultsCache = newResultsCache(noOfRows, noOfColumns);
        for (int rowIndex = 0; rowIndex < noOfRows; rowIndex++) {
            for (int columnIndex = 0; columnIndex < noOfColumns; columnIndex++) {
                boolean isCellPartOfSquare = matrix[rowIndex][columnIndex] == targetValue;
                if (rowIndex == 0 || columnIndex == 0) {
                    resultsCache[rowIndex][columnIndex] = isCellPartOfSquare ? 1 : 0;
                } else if (isCellPartOfSquare) {
                    resultsCache[rowIndex][columnIndex] = calculateSquareValue(rowIndex, columnIndex, resultsCache);
                }
                if (resultsCache[rowIndex][columnIndex] > maxSquareSubArray) {
                    maxSquareSubArray = resultsCache[rowIndex][columnIndex];
                }
            }
        }
        return maxSquareSubArray;
    }

    private static int calculateSquareValue(int rowIndex, int columnIndex, int[][] resultsCache) {
        int leftCell = resultsCache[rowIndex][columnIndex - 1];
        int aboveCell = resultsCache[rowIndex - 1][columnIndex];
        int aboveLeftCell = resultsCache[rowIndex - 1][columnIndex - 1];
        return 1 + Math.min(
                aboveLeftCell,
                Math.min(leftCell, aboveCell)
        );
    }

    //Better Square Submatrix solution => improved by adding cache => O(n * m)
    //and space complexity O(n * m) due to cached result
    //Now we only need to recursively compute each value once
    private static int largestSquareSubArrayTopDown(boolean targetValue, boolean[][] matrix) {
        int maxSquareSubArray = 0;
        int noOfRows = matrix.length;
        int noOfColumns = matrix[0].length;
        int[][] resultsCache = newResultsCache(noOfRows, noOfColumns);
        for (int rowIndex = 0; rowIndex < noOfRows; rowIndex++) {
            for (int columnIndex = 0; columnIndex < noOfColumns; columnIndex++) {
                if (matrix[rowIndex][columnIndex] == targetValue) {
                    maxSquareSubArray = Math.max(
                            maxSquareSubArray,
                            largestSquareSubArrayTopDown(targetValue, rowIndex, columnIndex, matrix, resultsCache)
                    );
                }
            }
        }
        return maxSquareSubArray;
    }

    private static int largestSquareSubArrayTopDown(boolean targetValue, int rowIndex, int columnIndex, boolean[][] matrix, int[][] resultsCache) {
        if (isNotValid(rowIndex, columnIndex, matrix)) {
            return 0;
        } else if (targetValue != matrix[rowIndex][columnIndex]) {
            return 0;
        } else if (resultsCache[rowIndex][columnIndex] != -1) {
            return resultsCache[rowIndex][columnIndex];
        }

        int rightCellValue = largestSquareSubArrayTopDown(targetValue, rowIndex, columnIndex + 1, matrix, resultsCache);
        int belowCellValue = largestSquareSubArrayTopDown(targetValue, rowIndex + 1, columnIndex, matrix, resultsCache);
        int belowRightCellValue = largestSquareSubArrayTopDown(targetValue, rowIndex + 1, columnIndex + 1, matrix, resultsCache);
        resultsCache[rowIndex][columnIndex] = 1 + Math.min(
                belowRightCellValue,
                Math.min(rightCellValue, belowCellValue)
        );
        return resultsCache[rowIndex][columnIndex];
    }

    //Naive Square Submatrix solution => O(n * m * 3 ^ (n + m)) and space complexity O(n + m) due to recursive stack space
    private static int largestSquareSubArrayTopDown(boolean targetValue, int rowIndex, int columnIndex, boolean[][] matrix) {
        if (isNotValid(rowIndex, columnIndex, matrix)) {
            return 0;
        } else if (targetValue != matrix[rowIndex][columnIndex]) {
            return 0;
        }

        int rightCellValue = largestSquareSubArrayTopDown(targetValue, rowIndex, columnIndex + 1, matrix);
        int belowCellValue = largestSquareSubArrayTopDown(targetValue, rowIndex + 1, columnIndex, matrix);
        int belowRightCellValue = largestSquareSubArrayTopDown(targetValue, rowIndex + 1, columnIndex + 1, matrix);
        return 1 + Math.min(
                belowRightCellValue,
                Math.min(rightCellValue, belowCellValue)
        );
    }

    private static boolean isNotValid(int rowIndex, int columnIndex, boolean[][] matrix) {
        return rowIndex < 0 || rowIndex >= matrix.length || columnIndex < 0 || columnIndex >= matrix[0].length;
    }

    private static int[][] newResultsCache(int noOfRows, int noOfColumns) {
        int[][] resultsCache = new int[noOfRows][noOfColumns];
        for (int rowIndex = 0; rowIndex < noOfRows; rowIndex++) {
            for (int columnIndex = 0; columnIndex < noOfColumns; columnIndex++) {
                resultsCache[rowIndex][columnIndex] = -1;
            }
        }
        return resultsCache;
    }
}
