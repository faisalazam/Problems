package com.algorithms.leetcode.medium;

public class SurroundedRegions {

    private static final char O = 'O';
    private static final char X = 'X';
    private static final char MARKER = '*';

    /**
     * Given a 2D board containing 'X' and 'O' (the letter O), capture all regions surrounded by 'X'.
     * <p>
     * A region is captured by flipping all 'O's into 'X's in that surrounded region.
     * <p>
     * Explanation:
     * <p>
     * Surrounded regions shouldn’t be on the border, which means that any 'O' on the border of the board
     * are not flipped to 'X'. Any 'O' that is not on the border and it is not connected to an 'O' on the border
     * will be flipped to 'X'. Two cells are connected if they are adjacent cells connected horizontally or vertically.
     * <p>
     * https://leetcode.com/problems/surrounded-regions/
     */
    private void solve(char[][] board) {
        if (board.length == 0 || board[0].length == 0) {
            return;
        }
        int rows = board.length;
        int columns = board[0].length;
        markBoundaryColumns(board, rows, columns);
        markBoundaryRows(board, rows, columns);
        captureRegions(board, rows, columns);
    }

    private void captureRegions(char[][] board, int rows, int columns) {
        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {
                if (board[row][column] == O) {
                    board[row][column] = X;
                } else if (board[row][column] == MARKER) {
                    board[row][column] = O;
                }
            }
        }
    }

    private void markBoundaryRows(char[][] board, int rows, int columns) {
        for (int column = 0; column < columns; column++) {
            if (board[0][column] == O) {
                boundaryDFS(board, 0, column);
            }
            if (board[rows - 1][column] == O) {
                boundaryDFS(board, rows - 1, column);
            }
        }
    }

    private void markBoundaryColumns(char[][] board, int rows, int columns) {
        for (int row = 0; row < rows; row++) {
            if (board[row][0] == O) {
                boundaryDFS(board, row, 0);
            }
            if (board[row][columns - 1] == O) {
                boundaryDFS(board, row, columns - 1);
            }
        }
    }

    // Any border-cell having O, as well as any non border-cell having O and connected to the border-cell having O,
    // will be replaced with MARKER
    private void boundaryDFS(char[][] board, int row, int column) {
        if (row < 0 || row > board.length - 1 || column < 0 || column > board[0].length) {
            return;
        }
        if (board[row][column] == O) {
            board[row][column] = MARKER;
        }
        if (row > 0 && board[row - 1][column] == O) {
            boundaryDFS(board, row - 1, column);
        }
        if (row < board.length - 1 && board[row + 1][column] == O) {
            boundaryDFS(board, row + 1, column);
        }
        if (column > 0 && board[row][column - 1] == O) {
            boundaryDFS(board, row, column - 1);
        }
        if (column < board[0].length - 1 && board[row][column + 1] == O) {
            boundaryDFS(board, row, column + 1);
        }
    }

    public static void main(String[] args) {
        char[][] board = {
                {X, X, O, X, X},
                {O, X, O, X, X},
                {X, O, O, X, X},
                {X, X, X, O, X},
                {X, X, O, X, X}
        };

        new SurroundedRegions().solve(board);
        print(board);
    }

    private static void print(char[][] matrix) {
        for (char[] ints : matrix) {
            for (char column = 0; column < matrix[0].length; column++) {
                System.out.print(ints[column] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
}
