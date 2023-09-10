package com.algorithms.geeksforgeeks.medium;

import java.util.List;

public class FindNumberOfIslands {
    /**
     * Given a Matrix consisting of 0s and 1s. Find the number of islands of connected 1s present in the matrix.
     * Note: A 1 is said to be connected if it has another 1 around it (either of the 8 directions).
     * <p>
     * Expected Time Complexity: O(N*M).
     * Expected Auxiliary Space: O(N*M).
     * <p>
     * https://practice.geeksforgeeks.org/problems/find-the-number-of-islands/1
     */
    private static int findIslands(List<List<Integer>> list, int N, int M) {
        int numOfIslands = 0;
        boolean[][] visited = new boolean[N][M];
        for (int row = 0; row < N; row++) {
            List<Integer> rowVals = list.get(row);
            for (int column = 0; column < M; column++) {
                int columnVal = rowVals.get(column);
                if (columnVal == 1 && !visited[row][column]) {
                    numOfIslands++;
                    visitNeighbours(row, column, visited, list, N, M);
                }
            }
        }
        return numOfIslands;
    }

    private static void visitNeighbours(int row, int column, boolean[][] visited, List<List<Integer>> list, int N, int M) {
        if (isNotValid(row, column, visited, list, N, M)) {
            return;
        }
        visited[row][column] = true;
        for (int rowIndex = row - 1; rowIndex <= row + 1; rowIndex++) {
            for (int columnIndex = column - 1; columnIndex <= column + 1; columnIndex++) {
                if (rowIndex != row || columnIndex != column) {
                    visitNeighbours(rowIndex, columnIndex, visited, list, N, M);
                }
            }
        }
    }

    private static boolean isNotValid(int row, int column, boolean[][] visited, List<List<Integer>> list, int N, int M) {
        return row < 0 || row >= N || column < 0 || column >= M || visited[row][column] || list.get(row).get(column) == 0;
    }

}
