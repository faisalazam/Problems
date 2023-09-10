package com.algorithms.misc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

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


    List<Integer> getLongestSequence(int[][] grid) {
        if (grid.length == 0) {
            return new ArrayList<Integer>();
        }

        int rows = grid.length;
        int columns = grid[0].length;

        boolean[][] visited = new boolean[rows][columns];
        int maxLength = Integer.MIN_VALUE;
        for (int row = 0; row < rows; row++) {
            int[] rowVals = grid[row];
            for (int column = 0; column < columns; column++) {
                int currentCount = getSequenceCount(grid, row, column, visited);
                maxLength = Math.max(maxLength, currentCount);
            }
        }
        return null;//maxLength;
    }

    private int getSequenceCount(int[][] grid, int row, int column, boolean[][] visited) {

        Queue<Integer> queue = new LinkedList<Integer>();
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
    public int longestIncreasingPath(int[][] matrix) {
        rows = matrix.length;
        if (rows == 0) return 0;
        columns = matrix[0].length;
        mem = new int[rows][columns];
        int max = 0;
        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {
                max = Math.max(max, DFS(matrix, row, column, Integer.MIN_VALUE));
            }
        }
        return max;
    }

    private int rows;
    private int columns;
    private int[][] mem;

    private int DFS(int[][] mat, int row, int column, int prev) {
        if (row < 0 || row >= rows || column < 0 || column >= columns || mat[row][column] <= prev) {
            return 0;
        }
        if (mem[row][column] == 0) {
            prev = mat[row][column];
            int len1 = Math.max(
                    DFS(mat, row - 1, column, prev),
                    DFS(mat, row + 1, column, prev)
            );
            int len2 = Math.max(
                    DFS(mat, row, column - 1, prev),
                    DFS(mat, row, column + 1, prev)
            );
            mem[row][column] = 1 + Math.max(len1, len2);
        }
        return mem[row][column];
    }

    private static int[][] moves = new int[][]{
            {1, 0}, {0, 1}, {-1, 0}, {0, -1}
    };

    /**
     * OR Could have done this :(
     */
    public int longestIncreasingPathAnother(int[][] matrix) {
        if (matrix.length == 0)
            return 0;

        int[][] longestPathAtPos = new int[matrix.length][matrix[0].length];
        for (int[] longestPathAtPo : longestPathAtPos) {
            Arrays.fill(longestPathAtPo, -1);
        }


        int lengthOfMaxSeq = Integer.MIN_VALUE;
        for (int i = 0; i < matrix.length; i++)
            for (int j = 0; j < matrix[0].length; j++) {
                lengthOfMaxSeq = Math.max(lengthOfMaxSeq, dfsToMaxPath(i, j, matrix, longestPathAtPos));
            }

        // for(int i = 0; i < longestPathAtPos.length; i++)
        //     System.out.println(Arrays.toString(longestPathAtPos[i]));

        return lengthOfMaxSeq;
    }

    private int dfsToMaxPath(int row, int col, int[][] matrix, int[][] longestPathAtPos) {
        if (longestPathAtPos[row][col] != -1)
            return longestPathAtPos[row][col];

        longestPathAtPos[row][col] = 1;

        for (int[] move : moves) {
            int nextRow = row + move[0];
            int nextCol = col + move[1];

            if ((nextRow >= 0 && nextRow < matrix.length)
                    && (nextCol >= 0 && nextCol < matrix[0].length)
                    && matrix[nextRow][nextCol] > matrix[row][col]) {
                longestPathAtPos[row][col] = Math.max(
                        longestPathAtPos[row][col],
                        dfsToMaxPath(nextRow, nextCol, matrix, longestPathAtPos) + 1
                );
            }
        }
        return longestPathAtPos[row][col];
    }
}
