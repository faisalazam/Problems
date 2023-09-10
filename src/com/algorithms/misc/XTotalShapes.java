package com.algorithms.misc;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Given N * M string array of O's and X's. The task is to find the number of 'X' total shapes.
 * 'X' shape consists of one or more adjacent X's (diagonals not included).
 *
 * https://practice.geeksforgeeks.org/problems/x-total-shapes/0
 */
public class XTotalShapes {
    public static void main(String[] args) {
        char[][] matrix = {
                {'O', 'O', 'O', 'O', 'X', 'X', 'O'},
                {'O', 'X', 'O', 'X', 'O', 'O', 'X'},
                {'X', 'X', 'X', 'X', 'O', 'X', 'O'},
                {'O', 'X', 'X', 'X', 'O', 'O', 'O'},
        };
        System.out.println("Recursive: Total X shapes are => " + getTotalShapes(matrix));

        char[][] clone = {
                {'O', 'O', 'O', 'O', 'X', 'X', 'O'},
                {'O', 'X', 'O', 'X', 'O', 'O', 'X'},
                {'X', 'X', 'X', 'X', 'O', 'X', 'O'},
                {'O', 'X', 'X', 'X', 'O', 'O', 'O'},
        };
        System.out.println("Iterative: Total X shapes are => " + getTotalShapesIteratively(clone));
    }

    private static int getTotalShapesIteratively(char[][] matrix) {
        int totalShapes = 0;
        if (matrix == null || matrix.length == 0) {
            return totalShapes;
        }
        Queue<Pair<Integer, Integer>> queue = new LinkedList<>();
        for (int row = 0; row < matrix.length; row++) {
            for (int column = 0; column < matrix[row].length; column++) {
                if (matrix[row][column] == 'X') {
                    totalShapes++;
                    queue.offer(Pair.of(row, column));
                    handleAdjacentShapes(matrix, queue);
                }
            }
        }
        return totalShapes;
    }

    private static void handleAdjacentShapes(char[][] matrix, Queue<Pair<Integer, Integer>> queue) {
        while (!queue.isEmpty()) {
            Pair<Integer, Integer> currentCell = queue.poll();
            int row = currentCell.fst;
            int column = currentCell.snd;

            matrix[row][column] = 'O';
            if (row - 1 >= 0 && matrix[row - 1][column] == 'X') {
                queue.offer(Pair.of(row - 1, column));
            }
            if (row + 1 < matrix.length && matrix[row + 1][column] == 'X') {
                queue.offer(Pair.of(row + 1, column));
            }
            if (column - 1 >= 0 && matrix[row][column - 1] == 'X') {
                queue.offer(Pair.of(row, column - 1));
            }
            if (column + 1 < matrix[row].length && matrix[row][column + 1] == 'X') {
                queue.offer(Pair.of(row, column + 1));
            }
        }
    }

    // Time complexity: O(ROW x COL)?
    private static int getTotalShapes(char[][] matrix) {
        int totalShapes = 0;
        if (matrix == null || matrix.length == 0) {
            return totalShapes;
        }
        for (int row = 0; row < matrix.length; row++) {
            for (int column = 0; column < matrix[row].length; column++) {
                if (matrix[row][column] == 'X') {
                    updateMatrix(row, column, matrix);
                    totalShapes++;
                }
            }
        }
        return totalShapes;
    }

    private static void updateMatrix(int row, int column, char[][] matrix) {
        if (isNotValid(row, column, matrix)) {
            return;
        }
        matrix[row][column] = 'O';
        updateMatrix(row, column - 1, matrix);
        updateMatrix(row, column + 1, matrix);
        updateMatrix(row - 1, column, matrix);
        updateMatrix(row + 1, column, matrix);
    }

    private static boolean isNotValid(int row, int column, char[][] matrix) {
        return row < 0 || row >= matrix.length || column < 0 || column >= matrix[row].length || matrix[row][column] == 'O';
    }
}
