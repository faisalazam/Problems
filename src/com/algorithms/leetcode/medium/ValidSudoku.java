package com.algorithms.leetcode.medium;

import java.util.HashSet;
import java.util.Set;

public class ValidSudoku {
    /**
     * Determine if a 9x9 Sudoku board is valid. Only the filled cells need to be validated according to the following rules:
     * <p>
     * Each row must contain the digits 1-9 without repetition.
     * Each column must contain the digits 1-9 without repetition.
     * Each of the 9 3x3 sub-boxes of the grid must contain the digits 1-9 without repetition.
     * <p>
     * A partially filled sudoku which is valid.
     * <p>
     * The Sudoku board could be partially filled, where empty cells are filled with the character '.'.
     * <p>
     * https://leetcode.com/problems/valid-sudoku/
     */
    public boolean isValidSudoku(char[][] board) {
        int N = 9;
        Set<String> seen = new HashSet<>();
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                char currentValue = board[i][j];
                if (currentValue != '.') {
                    //Note the use of add method inside if, as the add method will return false if the set already contains the value
                    if (!seen.add(currentValue + " found in row " + i) ||
                            !seen.add(currentValue + " found in column " + j) ||
                            !seen.add(currentValue + " found in sub box " + i / 3 + "-" + j / 3)
                    ) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
}
