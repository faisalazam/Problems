package com.algorithms.leetcode.medium;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * On a 0-indexed 8 x 8 chessboard, there can be multiple black queens ad one white king.
 * <p>
 * You are given a 2D integer array queens where queens[i] = [xQueeni, yQueeni] represents the position of the ith
 * black queen on the chessboard. You are also given an integer array king of length 2 where king = [xKing, yKing]
 * represents the position of the white king.
 * <p>
 * Return the coordinates of the black queens that can directly attack the king. You may return the answer in any order.
 * <p>
 * https://leetcode.com/problems/queens-that-can-attack-the-king/
 */
public class QueensThatCanAttackTheKing {
    public List<List<Integer>> queensAttackTheKing(int[][] queens, int[] king) {
        final List<List<Integer>> results = new ArrayList<>();

        final int boardSize = 8;
        final boolean[][] seen = new boolean[boardSize][boardSize]; //an array to keep track where the queens are seen
        for (int[] queen : queens) {
            seen[queen[0]][queen[1]] = true;
        }

        final int[] directions = {-1, 0, 1}; // the following loops will traverse through all directions
        for (int dRow : directions) {
            for (int dColumn : directions) {
                if (dRow == 0 && dColumn == 0) {
                    continue;
                }
                int row = king[0];
                int column = king[1];

                while (row + dRow >= 0 && row + dRow < boardSize && column + dColumn >= 0 && column + dColumn < boardSize) {
                    row += dRow;
                    column += dColumn;
                    if (seen[row][column]) {
                        results.add(Arrays.asList(row, column));
                        break;
                    }
                }
            }
        }
        return results;
    }
}
