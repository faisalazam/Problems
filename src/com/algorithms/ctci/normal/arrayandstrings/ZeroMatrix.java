package com.algorithms.ctci.normal.arrayandstrings;

/**
 * Given an m x n integer matrix matrix, if an element is 0, set its entire row and column to 0's.
 * <p>
 * You must do it in place.
 * <p>
 * https://leetcode.com/problems/set-matrix-zeroes/description/
 */
public class ZeroMatrix {
    // Brute-force
    private static void setZerosV0(int[][] matrix) { // fails for the matrices which contains Integer.MIN_VALUE as cell value
        final int rows = matrix.length;
        final int columns = matrix[0].length;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (matrix[i][j] == 0) {
                    nullifyRow(matrix, i, Integer.MIN_VALUE);
                    nullifyColumn(matrix, j, Integer.MIN_VALUE);
                }
            }
        }

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (matrix[i][j] == Integer.MIN_VALUE) {
                    matrix[i][j] = 0;
                }
            }
        }
    }

    // We can avoid visiting the rows and columns again and again after they are zeroed to improve the algo just a
    // little bit by using extra space to maintain a record of already zeroed rows and columns.
    private static void setZerosV1(int[][] matrix) { // fails for the matrices which contains Integer.MIN_VALUE as cell value
        final int rows = matrix.length;
        final int columns = matrix[0].length;
        final boolean[] visitedRows = new boolean[rows];
        final boolean[] visitedColumns = new boolean[columns];

        for (int i = 0; i < rows; i++) {
            if (!visitedRows[i]) {
                final int[] row = matrix[i];
                for (int j = 0; j < columns; j++) {
                    if (!visitedColumns[j] && row[j] == 0) {
                        nullifyRow(matrix, i, Integer.MIN_VALUE);
                        nullifyColumn(matrix, j, Integer.MIN_VALUE);
                        visitedRows[i] = true;
                        visitedColumns[j] = true;
                    }
                }
            }
        }

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (matrix[i][j] == Integer.MIN_VALUE) {
                    matrix[i][j] = 0;
                }
            }
        }
    }

    // But how about if we traverse the whole matrix once to pick the rows and columns containing zero and store them in
    // extra space
    public static void setZerosV2(int[][] matrix) {
        final int rows = matrix.length;
        final int columns = matrix[0].length;
        boolean[] rowsWithZero = new boolean[rows];
        boolean[] columnsWithZero = new boolean[columns];

        // Store the row and column index with value 0
        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {
                if (matrix[row][column] == 0) {
                    rowsWithZero[row] = true;
                    columnsWithZero[column] = true;
                }
            }
        }

        // Nullify rows
        for (int row = 0; row < rows; row++) {
            if (rowsWithZero[row]) {
                nullifyRow(matrix, row);
            }
        }

        // Nullify columns
        for (int column = 0; column < columns; column++) {
            if (columnsWithZero[column]) {
                nullifyColumn(matrix, column);
            }
        }
    }


    // But how about utilising the input matrix instead of using the extra space to hold the picked rows and columns
    // containing zero
    public static void setZerosV3(int[][] matrix) {
        boolean rowHasZero = false;
        boolean colHasZero = false;

        // Check if first row has a zero
        for (int j = 0; j < matrix[0].length; j++) {
            if (matrix[0][j] == 0) {
                rowHasZero = true;
                break;
            }
        }

        // Check if first column has a zero
        for (int[] rows : matrix) {
            if (rows[0] == 0) {
                colHasZero = true;
                break;
            }
        }

        // Check for zeros in the rest of the array
        for (int i = 1; i < matrix.length; i++) {
            for (int j = 1; j < matrix[0].length; j++) {
                if (matrix[i][j] == 0) {
                    matrix[i][0] = 0; // Row at index 0 is getting set to 0 for all the rows containing zero
                    matrix[0][j] = 0; // Column at index 0 is getting set to 0 for all the columns containing zero
                }
            }
        }

        // Nullify rows based on values in first column
        for (int i = 1; i < matrix.length; i++) {
            if (matrix[i][0] == 0) {
                nullifyRow(matrix, i);
            }
        }

        // Nullify columns based on values in first row
        for (int j = 1; j < matrix[0].length; j++) {
            if (matrix[0][j] == 0) {
                nullifyColumn(matrix, j);
            }
        }

        // Nullify first row
        if (rowHasZero) {
            nullifyRow(matrix, 0);
        }

        // Nullify first column
        if (colHasZero) {
            nullifyColumn(matrix, 0);
        }
    }

    private static void nullifyRow(int[][] matrix, int row) {
        for (int j = 0; j < matrix[0].length; j++) {
            matrix[row][j] = 0;
        }
    }

    private static void nullifyColumn(int[][] matrix, int col) {
        for (int i = 0; i < matrix.length; i++) {
            matrix[i][col] = 0;
        }
    }

    private static void nullifyRow(int[][] matrix, int row, int value) {
        for (int j = 0; j < matrix[0].length; j++) {
            if (matrix[row][j] != 0) {
                matrix[row][j] = value;
            }
        }
    }

    private static void nullifyColumn(int[][] matrix, int col, int value) {
        for (int i = 0; i < matrix.length; i++) {
            if (matrix[i][col] != 0) {
                matrix[i][col] = value;
            }
        }
    }

    public static void main(String[] args) {
        final int[][] matrix = {
                {0, 1, 2, 0},
                {3, 4, 5, 2},
                {1, 3, 1, 5}
        };
        setZerosV0(matrix);
        System.out.println();
    }
}
