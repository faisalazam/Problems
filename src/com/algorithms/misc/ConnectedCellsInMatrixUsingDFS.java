package com.algorithms.misc;

public class ConnectedCellsInMatrixUsingDFS {
    public static void main(String[] args) {
        int[][] matrix = {
                {0, 0, 0, 1, 1, 0, 0},
                {0, 1, 0, 0, 1, 1, 0},
                {1, 1, 0, 1, 0, 0, 1},
                {0, 0, 0, 0, 0, 1, 0},
                {1, 1, 0, 0, 0, 0, 0},
                {0, 0, 0, 1, 0, 0, 0},
        };
        System.out.println("Max Region is => " + getBiggestRegion(matrix));
    }

    /*
     * Returns the size of biggest region...where region is comprised of all connected 1s horizontally,
     * vertically as well as diagonally.
     */
    private static int getBiggestRegion(int[][] matrix) {
        int maxRegion = 0;
        if (matrix == null || matrix.length == 0) {
            return maxRegion;
        }

        int rows = matrix.length;
        int columns = matrix[0].length;
        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {
                if (matrix[row][column] == 1) {
                    maxRegion = Math.max(maxRegion, getRegionSize(row, column, matrix));
                }
            }
        }
        return maxRegion;
    }

    private static int getRegionSize(int row, int column, int[][] matrix) {
        if (isNotValid(row, column, matrix) || matrix[row][column] == 0) {
            return 0;
        }

        int size = 1;
        matrix[row][column] = 0;
        for (int rowIndex = row - 1; rowIndex <= row + 1; rowIndex++) {
            for (int columnIndex = column - 1; columnIndex <= column + 1; columnIndex++) {
                if (rowIndex != row || columnIndex != column) {
                    size += getRegionSize(rowIndex, columnIndex, matrix);
                }
            }
        }
        return size;
    }

    private static boolean isNotValid(int row, int column, int[][] matrix) {
        return row < 0 || row >= matrix.length || column < 0 || column >= matrix[0].length;
    }
}
