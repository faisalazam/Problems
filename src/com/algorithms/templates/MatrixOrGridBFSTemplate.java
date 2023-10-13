package com.algorithms.templates;

import java.util.ArrayDeque;
import java.util.Queue;

public class MatrixOrGridBFSTemplate {
    /**
     * The idea is to apply Breadth First Search from every cell ...
     * <p>
     * Time Complexity: O(n*m), as the grid is having n*m cells, and each cell is visited at max of one time.
     * Auxiliary Space: O(n*m), as max number of stack frames in the recursion tree can go up to n*m,
     * also the visited vector is of size n*m.
     */
    // Input can be in the form of the 2D array or List<List<?>>
    public void bfsTemplate(char[][] grid) {    // or final List<List<Character>> grid
        // The only difference between dfsTemplate and bfsTemplate methods is, dfsTemplate will invoke
        // visitNeighboursDFS(...), whereas the bfsTemplate will invoke visitNeighboursBFS(...) method.
        if (grid == null || grid.length == 0) {
            return;
        }

        final int rows = grid.length;           // grid.size();
        final int columns = grid[0].length;     // grid.get(0).size();
        // This extra space usage can be avoided by updating the input array if that's allowed
        // If the input array is of int, then think about using Euclidean Division and update the values in the input
        // array. You can use ((rows * columns) + 1) as multiplier/divisor. If updating input array is not allowed,
        // then you can iterate over the input array in the end again to put back the original values. Think about it...
        final boolean[][] visited = new boolean[rows][columns];
        for (int row = 0; row < rows; row++) {
            final char[] rowValues = grid[row]; // grid.get(row);
            for (int column = 0; column < columns; column++) {
                final char columnValue = rowValues[column]; // rowValues.get(column);
                if (/*columnValue == ?? && */!visited[row][column]) { // you may or may not need some extra condition here
                    // potential processing place according to your requirements, e.g. count++;
                    visitNeighboursBFS(row, rows, column, columns, visited, grid);
                }
            }
        }
        // you may need to return some result from here
    }

    private static void visitNeighboursBFS(final int row,
                                           final int rows,
                                           final int column,
                                           final int columns,
                                           final boolean[][] visited,
                                           final char[][] grid) {
        final Queue<Integer> queue = new ArrayDeque<>();
        queue.offer((row * columns) + column); // single dimensional representation of [i][j] or [row][column]

        visited[row][column] = true;

        while (!queue.isEmpty()) {
            final int currentPixel = queue.poll();
            final int currentRow = currentPixel / columns;
            final int currentColumn = currentPixel % columns;

            // potential processing place according to your requirements, e.g. count++; or grid[currentRow][currentColumn] = '?';

            // You may not need to visit all the neighbours, so choose the ones from below according to your requirements
            // visit adjacent neighbours on left and right
            addCell(currentRow - 1, rows, currentColumn, columns, grid, queue, visited); // Top
            addCell(currentRow + 1, rows, currentColumn, columns, grid, queue, visited); // Down

            // visit adjacent neighbours on up and down
            addCell(currentRow, rows, currentColumn - 1, columns, grid, queue, visited); // Left
            addCell(currentRow, rows, currentColumn + 1, columns, grid, queue, visited); // Right

            // visit neighbours diagonally
            addCell(currentRow - 1, rows, currentColumn - 1, columns, grid, queue, visited); // Top Left Diagonal
            addCell(currentRow - 1, rows, currentColumn + 1, columns, grid, queue, visited); // Top Right Diagonal
            addCell(currentRow + 1, rows, currentColumn - 1, columns, grid, queue, visited); // Down Left Diagonal
            addCell(currentRow + 1, rows, currentColumn + 1, columns, grid, queue, visited); // Down Left Diagonal

// The above addCell statements can be replaced with the following loop. Choose whatever is more clear and easy to understand/read
//            for (int rowIndex = currentRow - 1; rowIndex <= currentRow + 1; rowIndex++) {
//                for (int columnIndex = currentColumn - 1; columnIndex <= currentColumn + 1; columnIndex++) {
//                    addCell(rowIndex, rows, columnIndex, columns, grid, queue, visited);
//                }
//            }
        }
    }

    private static void addCell(final int row,
                                final int rows,
                                final int column,
                                final int columns,
                                final char[][] grid,
                                final Queue<Integer> queue,
                                final boolean[][] visited) {
        if (isValid(row, rows, column, columns, visited, grid)) {
            visited[row][column] = true;
            queue.offer((row * columns) + column); // single dimensional representation of [i][j] or [row][column]
        }
    }

    private static boolean isValid(final int row,
                                   final int rows,
                                   final int column,
                                   final int columns,
                                   final boolean[][] visited,
                                   final char[][] grid) { // or final List<List<Character>> grid)
        return row >= 0 && row < rows
                && column >= 0 && column < columns
                && !visited[row][column]
                // you may or may not need extra check like below, but if you need one, then it'll be according to
                // your requirement/problem
                && grid[row][column] == '?'         // grid.get(row).get(column) == '?' or whatever
                ;
    }

    // Just in case you need to use isValid(...) method...
    private static boolean isNotValid(final int row,
                                      final int rows,
                                      final int column,
                                      final int columns,
                                      final boolean[][] visited,
                                      final char[][] grid) { // or final List<List<Character>> grid)
        return row < 0 || row >= rows
                || column < 0 || column >= columns
                || visited[row][column]
                // you may or may not need extra check like below, but if you need one, then it'll be according to
                // your requirement/problem
                || grid[row][column] == '?'         // grid.get(row).get(column) == '?' or whatever
                ;
    }
}
