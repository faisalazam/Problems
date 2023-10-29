package com.algorithms.geeksforgeeks.medium;

import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;

public class FindNumberOfIslands {
    /**
     * Given a Matrix consisting of 0s and 1s. Find the number of islands of connected 1s present in the matrix.
     * Note: A 1 is said to be connected if it has another 1 around it (either of the 8 directions).
     * <p>
     * Time Complexity: O(n*m), as the grid is having n*m cells, and each cell is visited at max of one time.
     * Auxiliary Space: O(n*m), as max number of stack frames in the recursion tree can go up to n*m when all cells
     * of the grid are having '1', also the visited vector is of size n*m.
     * <p>
     * https://practice.geeksforgeeks.org/problems/find-the-number-of-islands/1
     * https://leetcode.com/problems/number-of-islands/description/
     * <p>
     * The idea is to apply Breadth First Search from every cell having '1' and not visited, mark all cells in their
     * connected components which is having '1' as visited and increase the answer by one.
     * <p>
     * Time Complexity: O(n*m), as the grid, is having n*m cells, and each cell is visited at max of one time.
     * Auxiliary Space: O(n*m), as visited array is of size n*m, also queue can also take up to n*m cells.
     */
    public int numIslandsBFS(char[][] grid) {
        int numOfIslands = 0;
        final char targetIslandChar = '1';
        final int rows = grid.length;
        final int columns = grid[0].length;
        // visited array will avoid visiting the same cell multiple times as well as avoid getting into cycles (as this
        // grid is essentially an undirected graph)
        final boolean[][] visited = new boolean[rows][columns];
        for (int row = 0; row < rows; row++) {
            final char[] rowValues = grid[row];
            for (int column = 0; column < columns; column++) {
                final char columnValue = rowValues[column];
                if (columnValue == targetIslandChar && !visited[row][column]) {
                    numOfIslands++;
                    visitNeighboursBFS(row, rows, column, columns, visited, grid);
                }
            }
        }
        return numOfIslands;
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
            final int currentCell = queue.poll();
            final int currentRow = currentCell / columns;
            final int currentColumn = currentCell % columns;

            // You may not need to visit all the neighbours, so choose the ones from below according to your requirements
            // visit adjacent neighbours on up and down
            addCell(currentRow - 1, rows, currentColumn, columns, grid, queue, visited); // Top
            addCell(currentRow + 1, rows, currentColumn, columns, grid, queue, visited); // Down

            // visit adjacent neighbours on left and right
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
                                   final char[][] grid) {
        return row >= 0 && row < rows
                && column >= 0 && column < columns
                && !visited[row][column]
                && grid[row][column] == '1';
    }

    /**
     * The idea is to apply Depth First Search from every cell having '1' and not visited, mark all cells in their
     * connected components which is having '1' as visited and increase the answer by one.
     * <p>
     * Time Complexity: O(n*m), as the grid is having n*m cells, and each cell is visited at max of one time.
     * Auxiliary Space: O(n*m), as max number of stack frames in the recursion tree can go up to n*m when all cells
     * of the grid are having '1', also the visited vector is of size n*m.
     */
    public int numIslandsDFS(char[][] grid) {
        int numOfIslands = 0;
        final char targetIslandChar = '1';
        final int rows = grid.length;
        final int columns = grid[0].length;
        // visited array will avoid visiting the same cell multiple times as well as avoid getting into cycles (as this
        // grid is essentially an undirected graph)
        final boolean[][] visited = new boolean[rows][columns];
        for (int row = 0; row < rows; row++) {
            final char[] rowValues = grid[row];
            for (int column = 0; column < columns; column++) {
                final char columnValue = rowValues[column];
                if (columnValue == targetIslandChar && !visited[row][column]) {
                    numOfIslands++;
                    visitNeighboursDFS(row, rows, column, columns, visited, grid);
                }
            }
        }
        return numOfIslands;
    }

    private static void visitNeighboursDFS(final int row,
                                           final int rows,
                                           final int column,
                                           final int columns,
                                           final boolean[][] visited,
                                           final char[][] grid) {
        if (isNotValid(row, rows, column, columns, visited, grid)) {
            return;
        }
        visited[row][column] = true;

        // visit adjacent neighbours on left and right
        visitNeighboursDFS(row - 1, rows, column, columns, visited, grid);
        visitNeighboursDFS(row + 1, rows, column, columns, visited, grid);

        // visit adjacent neighbours on up and down
        visitNeighboursDFS(row, rows, column - 1, columns, visited, grid);
        visitNeighboursDFS(row, rows, column + 1, columns, visited, grid);

        // visit neighbours diagonally
        visitNeighboursDFS(row - 1, rows, column - 1, columns, visited, grid);
        visitNeighboursDFS(row - 1, rows, column + 1, columns, visited, grid);
        visitNeighboursDFS(row + 1, rows, column - 1, columns, visited, grid);
        visitNeighboursDFS(row + 1, rows, column + 1, columns, visited, grid);

        // visit neighbours - although we can use the following looping pattern to visit neighbors, but may be the above
        // explicit statements are more clear and easy to read and understand
//        for (int rowIndex = row - 1; rowIndex <= row + 1; rowIndex++) {
//            for (int columnIndex = column - 1; columnIndex <= column + 1; columnIndex++) {
//                if (rowIndex != row || columnIndex != column) { // This to skip the current cell
//                    visitNeighboursDFS(rowIndex, rows, columnIndex, columns, visited, grid);
//                }
//            }
//        }
    }

    private static boolean isNotValid(final int row,
                                      final int rows,
                                      final int column,
                                      final int columns,
                                      final boolean[][] visited,
                                      final char[][] grid) {
        return row < 0 || row >= rows
                || column < 0 || column >= columns
                || visited[row][column]
                || grid[row][column] == '0';
    }

    private static int findIslands(final List<List<Integer>> list) {
        int numOfIslands = 0;
        final int rows = list.size();
        final int columns = list.get(0).size();
        // visited array will avoid visiting the same cell multiple times as well as avoid getting into cycles (as this
        // grid is essentially an undirected graph)
        final boolean[][] visited = new boolean[rows][columns];
        for (int row = 0; row < rows; row++) {
            final List<Integer> rowValues = list.get(row);
            for (int column = 0; column < columns; column++) {
                final int columnVal = rowValues.get(column);
                if (columnVal == 1 && !visited[row][column]) {
                    // columnVal == 1 means one island regardless of the surroundings, but if there are connected 1's,
                    // then they'll be part of this island. No need to count them separately.
                    numOfIslands++;
                    visitNeighboursDFS(row, rows, column, columns, visited, list);
                }
            }
        }
        return numOfIslands;
    }

    private static void visitNeighboursDFS(final int row,
                                           final int rows,
                                           final int column,
                                           final int columns,
                                           final boolean[][] visited,
                                           final List<List<Integer>> list) {
        if (isNotValid(row, rows, column, columns, visited, list)) {
            return;
        }
        visited[row][column] = true;
        for (int rowIndex = row - 1; rowIndex <= row + 1; rowIndex++) {
            for (int columnIndex = column - 1; columnIndex <= column + 1; columnIndex++) {
                if (rowIndex != row || columnIndex != column) { // This to skip the current cell
                    visitNeighboursDFS(rowIndex, rows, columnIndex, columns, visited, list);
                }
            }
        }
    }

    private static boolean isNotValid(final int row,
                                      final int rows,
                                      final int column,
                                      final int columns,
                                      final boolean[][] visited,
                                      final List<List<Integer>> list) {
        return row < 0 || row >= rows
                || column < 0 || column >= columns
                || visited[row][column]
                || list.get(row).get(column) == 0;
    }
}
