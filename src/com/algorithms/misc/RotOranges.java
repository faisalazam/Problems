package com.algorithms.misc;

import java.util.LinkedList;
import java.util.Queue;

/*
 * Given a matrix of dimension r*c where each cell in the matrix can have values 0, 1 or 2 which has the following meaning:
 * 0 : Empty cell
 * 1 : Cells have fresh oranges
 * 2 : Cells have rotten oranges
 *
 * So, we have to determine what is the minimum time required to rot all oranges.
 * A rotten orange at index [i,j] can rot other fresh orange at indexes [i-1,j], [i+1,j], [i,j-1], [i,j+1]
 * (up, down, left and right) in unit time. If it is impossible to rot every orange then simply return -1.
 */
public class RotOranges {
    public static void main(String[] args) {
        int rows = 3;
        int columns = 5;
        int[][] oranges = {
                {2, 1, 0, 2, 1},
                {1, 0, 1, 2, 1},
                {1, 0, 0, 2, 1}
        };
        for (int i = 0; i < 5; ++i) {
            System.out.println();
        }
        System.out.println(minTimeToRot(rows, columns, oranges));
    }

    // Not fully Working :(
    // see also https://ide.geeksforgeeks.org/HnnRaEeQZ4
    // see https://ide.geeksforgeeks.org/exNBPqUWYe
    private static int minTimeToRot(int rows, int columns, int[][] oranges) {
        if (rows <= 0 || columns <= 0) {
            return -1;
        }
        Queue<Integer[]> queue = new LinkedList<>();
        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {
                if (oranges[row][column] == 2) {
                    queue.offer(new Integer[]{row, column});
                }
            }
        }
        int minTime = 0;
        queue.offer(new Integer[]{-1, -1});
        while (!queue.isEmpty()) {
            boolean orangesGotRotten = false;
            while (!isEndOfPass(queue.peek())) {
                Integer[] rowColumnPair = queue.peek();
                int row = rowColumnPair[0];
                int column = rowColumnPair[1];
                boolean flag = rotOrange(row - 1, column, rows, columns, oranges, queue)
                        | rotOrange(row + 1, column, rows, columns, oranges, queue)
                        | rotOrange(row, column - 1, rows, columns, oranges, queue)
                        | rotOrange(row, column + 1, rows, columns, oranges, queue);
                if (!orangesGotRotten && flag) {
                    orangesGotRotten = true;
                    minTime++;
                }
                queue.remove();
            }
            queue.remove();
            if (!queue.isEmpty()) {
                queue.add(new Integer[]{-1, -1});
            }
        }
        return isFresh(oranges, rows, columns) ? -1 : minTime;
    }

    private static boolean rotOrange(int row, int column, int rows, int columns, int[][] oranges, Queue<Integer[]> queue) {
        if (isValid(row, column, rows, columns) && oranges[row][column] == 1) {
            oranges[row][column]++;
            queue.add(new Integer[]{row, column});
            return true;
        }
        return false;
    }

    private static boolean isValid(int row, int column, int rows, int columns) {
        return row < rows && row >= 0 && column < columns && column >= 0;
    }

    private static boolean isEndOfPass(Integer[] rowColumnPair) {
        return rowColumnPair[0] == -1 && rowColumnPair[1] == -1;
    }

    private static boolean isFresh(int[][] oranges, int rows, int columns) {
        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {
                if (oranges[row][column] == 1) {
                    return true;
                }
            }
        }
        return false;
    }
}
