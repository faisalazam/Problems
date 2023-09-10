package com.algorithms.leetcode.medium;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class QueensThatCanAttackTheKing {
    /**
     * On an 8x8 chessboard, there can be multiple Black Queens and one White King.
     * <p>
     * Given an array of integer coordinates queens that represents the positions of the Black Queens,
     * and a pair of coordinates king that represent the position of the White King,
     * return the coordinates of all the queens (in any order) that can attack the King.
     * <p>
     * https://leetcode.com/problems/queens-that-can-attack-the-king/
     */
    public List<List<Integer>> queensAttackTheKing(int[][] queens, int[] king) {
        List<List<Integer>> results = new ArrayList<>();

        boolean[][] seen = new boolean[8][8]; //an array to keep track where the queens are seen
        for (int[] queen : queens) {
            seen[queen[0]][queen[1]] = true;
        }

        int[] directions = {-1, 0, 1}; // the following loops will traverse through all directions
        for (int dRow : directions) {
            for (int dColumn : directions) {
                if (dRow == 0 && dColumn == 0) {
                    continue;
                }
                int row = king[0];
                int column = king[1];

                while (row + dRow >= 0 && row + dRow < 8 && column + dColumn >= 0 && column + dColumn < 8) {
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
