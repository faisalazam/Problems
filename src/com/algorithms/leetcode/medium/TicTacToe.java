package com.algorithms.leetcode.medium;

/**
 * Design a Tic Tac Toe game which is played between two players on n*n grid
 */
public class TicTacToe {
    private int diagonalSum;
    private int antiDiagonalSum;
    private final int[] rows;
    private final int[] columns;

    public TicTacToe(int n) {
        rows = new int[n];
        columns = new int[n];
    }

    public int move(int row, int column, int player) {
        int playerValue = player == 1 ? 1 : -1;
        updateValues(row, column, playerValue);
        return calculateResult(rows[row], columns[column], player);
    }

    private void updateValues(int row, int column, int playerValue) {
        if (row == column) {
            diagonalSum += playerValue;
        }
        if (column == (columns.length - 1 - row)) {
            antiDiagonalSum += playerValue;
        }
        rows[row] += playerValue;
        columns[column] += playerValue;
    }

    private int calculateResult(int row, int column, int player) {
        int size = rows.length;
        if (
                Math.abs(diagonalSum) == size ||
                        Math.abs(antiDiagonalSum) == size ||
                        Math.abs(row) == size ||
                        Math.abs(column) == size
        ) {
            return player;
        }
        return 0;
    }
}
