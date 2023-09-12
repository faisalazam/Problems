package com.algorithms.leetcode.medium;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * 695. Max Area of Island
 * <p>
 * You are given an m x n binary matrix grid. An island is a group of 1's (representing land) connected 4-directionally
 * (horizontal or vertical.) You may assume all four edges of the grid are surrounded by water.
 * <p>
 * The area of an island is the number of cells with a value 1 on the island.
 * <p>
 * Return the maximum area of an island in grid. If there is no island, return 0.
 * <p>
 * https://leetcode.com/problems/max-area-of-island/description/
 * <p>
 * Overall, this algorithm has a time complexity of O(m * n) and a space complexity of O(m * n),
 * where m is the number of rows and n is the number of columns in the grid.
 */
public class MaxAreaOfIslandInMatrix {
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
     * Returns the size of biggest region...where region consists of all connected 1s horizontally,
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
                    maxRegion = Math.max(maxRegion, getRegionSizeBFS(row, matrix.length, column, matrix[row].length, matrix)); // BFS
//                    maxRegion = Math.max(maxRegion, getRegionSizeDFS(row, column, matrix)); // DFS
                }
            }
        }
        return maxRegion;
    }

    private static int getRegionSizeDFS(int row, int column, int[][] matrix) {
        if (isNotValid(row, column, matrix) || matrix[row][column] == 0) {
            return 0;
        }

        int size = 1;
        matrix[row][column] = 0;

        // visit adjacent neighbours on left and right
        size += getRegionSizeDFS(row - 1, column, matrix); // Top
        size += getRegionSizeDFS(row + 1, column, matrix); // Down

        // visit adjacent neighbours on up and down
        size += getRegionSizeDFS(row, column - 1, matrix); // Left
        size += getRegionSizeDFS(row, column + 1, matrix); // Right

// The above getRegionSizeDFS statements can be replaced with the following loop. Choose whatever is more clear and easy to understand/read
//        for (int rowIndex = row - 1; rowIndex <= row + 1; rowIndex++) {
//            for (int columnIndex = column - 1; columnIndex <= column + 1; columnIndex++) {
//                if ((rowIndex == row && columnIndex != column) || (rowIndex != row && columnIndex == column)) {
//                    size += getRegionSizeDFS(rowIndex, columnIndex, matrix);
//                }
//            }
//        }
        return size;
    }

    private static int getRegionSizeBFS(final int row,
                                        final int rows,
                                        final int column,
                                        final int columns,
                                        final int[][] grid) {
        final Queue<Integer> queue = new ArrayDeque<>();
        queue.offer((row * columns) + column); // single dimensional representation of [i][j] or [row][column]

        grid[row][column] = 0; // This is serving the purpose of the visited array usually used in DFS/BFS

        int size = 1;
        while (!queue.isEmpty()) {
            final int currentPixel = queue.poll();
            final int currentRow = currentPixel / columns;
            final int currentColumn = currentPixel % columns;

            // visit adjacent neighbours on left and right
            size += addCell(currentRow - 1, rows, currentColumn, columns, grid, queue); // Top
            size += addCell(currentRow + 1, rows, currentColumn, columns, grid, queue); // Down

            // visit adjacent neighbours on up and down
            size += addCell(currentRow, rows, currentColumn - 1, columns, grid, queue); // Left
            size += addCell(currentRow, rows, currentColumn + 1, columns, grid, queue); // Right
        }
        return size;
    }

    private static int addCell(final int row,
                               final int rows,
                               final int column,
                               final int columns,
                               final int[][] grid,
                               final Queue<Integer> queue) {
        if (isValid(row, rows, column, columns, grid)) {
            grid[row][column] = 0; // This is serving the purpose of the visited array usually used in DFS/BFS
            queue.offer((row * columns) + column); // single dimensional representation of [i][j] or [row][column]
            return 1;
        }
        return 0;
    }

    private static boolean isValid(final int row,
                                   final int rows,
                                   final int column,
                                   final int columns,
                                   final int[][] grid) {
        return row >= 0 && row < rows
                && column >= 0 && column < columns
                && grid[row][column] == 1
                ;
    }

    private static boolean isNotValid(int row, int column, int[][] matrix) {
        return row < 0 || row >= matrix.length || column < 0 || column >= matrix[0].length;
    }
}
