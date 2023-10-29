package com.algorithms.misc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Given a matrix with n rows and m columns. Your task is to find the length of the longest increasing path in matrix,
 * here increasing path means that the value in the specified path increases. For example if a path of length k has
 * values a1, a2, a3, .... ak  , then for every i from [2,k] this condition must hold ai > ai-1.  No cell should be
 * revisited in the path.
 * <p>
 * From each cell, you can either move in four directions: left, right, up, or down. You are not allowed to move
 * diagonally or move outside the boundary.
 * <p>
 * https://practice.geeksforgeeks.org/problems/longest-increasing-path-in-a-matrix/1
 * <p>
 * Expected Time Complexity: O(n*m)
 * Expected Auxiliary Space: O(n*m)
 */
public class IncreasingSequenceInGrid {
//    e.g. Given:
//
//            21 36 42 55
//            11 73 19 28
//            82 52 13 12
//
//    Return:
//
//            12 13 19 42 55
//
//    Which is the run in bold here:
//
//            21 36 42 55
//            11 73 19 28
//            82 52 13 12
//
//
//
//    Write a function that takes a grid of distinct numbers and returns a longest sequence of
//    adjacent increasing numbers in the grid.  Note that this question is not completely defined.


    List<Integer> getLongestSequence(int[][] matrix) { // Not Working
        if (matrix.length == 0) {
            return new ArrayList<>();
        }

        final int rows = matrix.length;
        final int columns = matrix[0].length;

        final boolean[][] visited = new boolean[rows][columns];
        int maxLength = Integer.MIN_VALUE;
        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {
                int currentCount = getSequenceCount(matrix, row, column, visited);
                maxLength = Integer.max(maxLength, currentCount);
            }
        }
        return null;//maxLength;
    }

    private int getSequenceCount(int[][] grid, int row, int column, boolean[][] visited) {

        final Queue<Integer> queue = new LinkedList<>();
        queue.offer(grid[row][column]);
        visited[row][column] = true;

        int count = 0;
        while (!queue.isEmpty()) {
            int currentVal = queue.poll();

            if (column + 1 < grid[row].length && currentVal < grid[row][column + 1] && !visited[row][column + 1]) {
                count += 1;
                queue.offer(grid[row][column + 1]);
                visited[row][column + 1] = true;
            }
            if (column - 1 > 0 && currentVal < grid[row][column - 1] && !visited[row][column - 1]) {
                count += 1;
                queue.offer(grid[row][column - 1]);
                visited[row][column - 1] = true;
            }

            if (row + 1 < grid.length && currentVal < grid[row + 1][column] && !visited[row + 1][column]) {
                count += 1;
                queue.offer(grid[row + 1][column]);
                visited[row + 1][column] = true;
            }
            if (row - 1 > 0 && currentVal < grid[row - 1][column] && !visited[row - 1][column]) {
                count += 1;
                queue.offer(grid[row - 1][column]);
                visited[row][column - 1] = true;
            }

        }
        return count;
    }


    /**
     * Could have done this :(
     */
    public int longestIncreasingPath(int[][] matrix, int rows, int columns) {
        if (rows == 0 || columns == 0) {
            return 0;
        }
        int max = 0;
        final int[][] mem = new int[rows][columns];
        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {
                max = Integer.max(max, DFS(matrix, mem, row, rows, column, columns, Integer.MIN_VALUE));
            }
        }
        return max;
    }

    private int DFS(int[][] mat, int[][] mem, int row, int rows, int column, int columns, int prev) {
        if (row < 0 || row >= rows || column < 0 || column >= columns || mat[row][column] <= prev) {
            return 0;
        }
        if (mem[row][column] == 0) {
            prev = mat[row][column];
            final int len1 = Integer.max(
                    DFS(mat, mem, row - 1, rows, column, columns, prev),
                    DFS(mat, mem, row + 1, rows, column, columns, prev)
            );
            final int len2 = Integer.max(
                    DFS(mat, mem, row, rows, column - 1, columns, prev),
                    DFS(mat, mem, row, rows, column + 1, columns, prev)
            );
            mem[row][column] = 1 + Integer.max(len1, len2);
        }
        return mem[row][column];
    }

    /**
     * OR Could have done this :(
     */
    public int longestIncreasingPathAnother(int[][] matrix, int rows, int columns) {
        if (rows == 0 || columns == 0) {
            return 0;
        }

        final int[][] longestPathAtPos = new int[rows][columns];
        for (int[] longestPathAtPo : longestPathAtPos) {
            Arrays.fill(longestPathAtPo, -1);
        }

        final int[][] moves = new int[][]{
                {1, 0}, {0, 1}, {-1, 0}, {0, -1}
        };

        int lengthOfMaxSeq = Integer.MIN_VALUE;
        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {
                lengthOfMaxSeq = Math.max(
                        lengthOfMaxSeq,
                        dfsToMaxPath(row, rows, column, columns, matrix, moves, longestPathAtPos)
                );
            }
        }

        // for(int i = 0; i < longestPathAtPos.length; i++)
        //     System.out.println(Arrays.toString(longestPathAtPos[i]));

        return lengthOfMaxSeq;
    }

    private int dfsToMaxPath(int row,
                             int rows,
                             int col,
                             int cols,
                             int[][] matrix,
                             int[][] moves,
                             int[][] longestPathAtPos) {
        if (longestPathAtPos[row][col] != -1) {
            return longestPathAtPos[row][col];
        }

        longestPathAtPos[row][col] = 1;

        for (int[] move : moves) {
            final int nextRow = row + move[0];
            final int nextCol = col + move[1];

            if ((nextRow >= 0 && nextRow < rows)
                    && (nextCol >= 0 && nextCol < cols)
                    && matrix[nextRow][nextCol] > matrix[row][col]) {
                longestPathAtPos[row][col] = Math.max(
                        longestPathAtPos[row][col],
                        1 + dfsToMaxPath(nextRow, rows, nextCol, cols, matrix, moves, longestPathAtPos)
                );
            }
        }
        return longestPathAtPos[row][col];
    }
}
