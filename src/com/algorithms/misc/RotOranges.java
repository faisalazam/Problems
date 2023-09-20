package com.algorithms.misc;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Given a matrix of dimension r*c where each cell in the matrix can have values 0, 1 or 2 which has the following meaning:
 * 0 : Empty cell
 * 1 : Cells have fresh oranges
 * 2 : Cells have rotten oranges
 * <p>
 * So, we have to determine what is the minimum time required to rot all oranges.
 * A rotten orange at index [i,j] can rot other fresh orange at indexes [i-1,j], [i+1,j], [i,j-1], [i,j+1]
 * (up, down, left and right) in unit time. If it is impossible to rot every orange then simply return -1.
 * <p>
 * For solution details:
 * https://www.geeksforgeeks.org/minimum-time-required-so-that-all-oranges-become-rotten/
 * https://www.geeksforgeeks.org/minimum-time-required-to-rot-all-oranges-dynamic-programming/
 * <p>
 * https://practice.geeksforgeeks.org/problems/rotten-oranges2536/1
 */
public class RotOranges {
    public static void main(String[] args) {
        final int[][] oranges = {
                {2, 1, 0, 2, 1},
                {1, 0, 1, 2, 1},
                {1, 0, 0, 2, 1}
        };
        for (int i = 0; i < 5; ++i) {
            System.out.println();
        }
        System.out.println(minTimeToRotV0(oranges));
    }

    /**
     * Minimum time required to rot all oranges using Breadth First Search:
     * The idea is to use Breadth First Search. The condition of oranges getting rotten is when they come in contact
     * with other rotten oranges. This is similar to a breadth-first search where the graph is divided into layers or
     * circles and the search is done from lower or closer layers to deeper or higher layers.
     * <p>
     * Time Complexity: O(R*C), Each element of the matrix can be inserted into the queue only once so the upper
     * bound of iteration is O(R*C)
     * Auxiliary Space: O(R*C), To store the elements in a queue.
     */
    private static int minTimeToRot(int[][] oranges) {
        final int rows = oranges.length;
        if (rows == 0 || oranges[0].length == 0) {
            return -1;
        }
        int minTime = 0;
        final int columns = oranges[0].length;
        final Queue<Integer[]> queue = new LinkedList<>();
        for (int row = 0; row < rows; row++) { // Store all the cells having rotten orange in first time frame
            for (int column = 0; column < columns; column++) {
                if (oranges[row][column] == 2) {
                    queue.offer(new Integer[]{row, column});
                }
            }
        } // queue has been filled with all the rotten oranges

        while (!queue.isEmpty()) {
            // This flag is used to determine whether even a single fresh orange gets rotten due to rotten oranges in
            // the current time frame so we can increase the count of the required time.
            boolean orangesGotRotten = false;
            final int size = queue.size();
            for (int i = 0; i < size; i++) {
                final Integer[] rowColumnPair = queue.remove();
                final boolean flag = processOrange(oranges, rows, columns, queue, rowColumnPair);
                if (!orangesGotRotten && flag) {
                    orangesGotRotten = true;
                    minTime++;
                }
            }
        }
        return isFresh(oranges, rows, columns) ? -1 : minTime;
    }

    /**
     * Minimum time required to rot all oranges using Breadth First Search: Using Delimiter
     * The idea is to use Breadth First Search. The condition of oranges getting rotten is when they come in contact
     * with other rotten oranges. This is similar to a breadth-first search where the graph is divided into layers or
     * circles and the search is done from lower or closer layers to deeper or higher layers.
     * <p>
     * Time Complexity: O(R*C), Each element of the matrix can be inserted into the queue only once so the upper
     * bound of iteration is O(R*C)
     * Auxiliary Space: O(R*C), To store the elements in a queue.
     */
    private static int minTimeToRotV0(int[][] oranges) {
        final int rows = oranges.length;
        if (rows == 0 || oranges[0].length == 0) {
            return -1;
        }
        int minTime = 0;
        final int columns = oranges[0].length;
        final Queue<Integer[]> queue = new LinkedList<>();
        for (int row = 0; row < rows; row++) { // Store all the cells having rotten orange in first time frame
            for (int column = 0; column < columns; column++) {
                if (oranges[row][column] == 2) {
                    queue.offer(new Integer[]{row, column});
                }
            }
        } // queue has been filled with all the rotten oranges

        // Separate these rotten oranges from the oranges which will rotten due the oranges in first time frame using
        // delimiter which is (-1, -1)
        queue.offer(new Integer[]{-1, -1});
        while (!queue.isEmpty()) {
            // This flag is used to determine whether even a single fresh orange gets rotten due to rotten oranges in
            // the current time frame so we can increase the count of the required time.
            boolean orangesGotRotten = false;
            while (!queue.isEmpty() && !isEndOfPass(queue.peek())) {
                final Integer[] rowColumnPair = queue.remove();
                final boolean flag = processOrange(oranges, rows, columns, queue, rowColumnPair);
                if (!orangesGotRotten && flag) {
                    orangesGotRotten = true;
                    minTime++;
                }
            }
            queue.remove(); // Pop the delimiter
            if (!queue.isEmpty()) {
                // If oranges were rotten in current frame than separate the rotten oranges using delimiter for the next
                // frame for processing.
                queue.add(new Integer[]{-1, -1});
            }
        }
        return isFresh(oranges, rows, columns) ? -1 : minTime;
    }

    private static boolean processOrange(final int[][] oranges,
                                         final int rows,
                                         final int columns,
                                         final Queue<Integer[]> queue,
                                         final Integer[] rowColumnPair) {
        final int row = rowColumnPair[0];
        final int column = rowColumnPair[1];

        // The operators && and || are short-circuiting, meaning they will not evaluate their right-hand
        // expression if the value of the left-hand expression is enough to determine the result, whereas
        // & and | are not short-circuiting, so all the conditions in the expression will get evaluated
        return rotOrange(row - 1, column, rows, columns, oranges, queue)
                | rotOrange(row + 1, column, rows, columns, oranges, queue)
                | rotOrange(row, column - 1, rows, columns, oranges, queue)
                | rotOrange(row, column + 1, rows, columns, oranges, queue);
    }

    private static boolean rotOrange(int row,
                                     int column,
                                     int rows,
                                     int columns,
                                     int[][] oranges,
                                     Queue<Integer[]> queue) {
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
