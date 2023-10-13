package com.algorithms.templates;

public class MatrixOrGridDFSTemplate {
    /**
     * The idea is to apply Depth First Search from every cell ...
     * <p>
     * Time Complexity: O(n*m), as the grid is having n*m cells, and each cell is visited at max of one time.
     * Auxiliary Space: O(n*m), as max number of stack frames in the recursion tree can go up to n*m,
     * also the visited vector is of size n*m.
     */
    // Input can be in the form of the 2D array or List<List<?>>
    public void dfsTemplate(char[][] grid) {   // or final List<List<Character>> grid
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
                    visitNeighboursDFS(row, rows, column, columns, visited, grid);
                }
            }
        }
        // you may need to return some result from here
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

        // You may not need to visit all the neighbours, so choose the ones from below according to your requirements
        // visit adjacent neighbours on left and right
        visitNeighboursDFS(row - 1, rows, column, columns, visited, grid); // Top
        visitNeighboursDFS(row + 1, rows, column, columns, visited, grid); // Down

        // visit adjacent neighbours on up and down
        visitNeighboursDFS(row, rows, column - 1, columns, visited, grid); // Left
        visitNeighboursDFS(row, rows, column + 1, columns, visited, grid); // Right

        // visit neighbours diagonally
        visitNeighboursDFS(row - 1, rows, column - 1, columns, visited, grid); // Top Left Diagonal
        visitNeighboursDFS(row - 1, rows, column + 1, columns, visited, grid); // Top Right Diagonal
        visitNeighboursDFS(row + 1, rows, column - 1, columns, visited, grid); // Down Left Diagonal
        visitNeighboursDFS(row + 1, rows, column + 1, columns, visited, grid); // Down Left Diagonal


        // visit neighbours - although we can use the following looping pattern to visit neighbors, but may be the above
        // explicit statements are more clear and easy to read and understand
//        for (int rowIndex = row - 1; rowIndex <= row + 1; rowIndex++) {
//            for (int columnIndex = column - 1; columnIndex <= column + 1; columnIndex++) {
//                // (rowIndex != row || columnIndex != column) will cover all the 8 indexes surrounding the current
//                // index/cell, i.e. 3 in the row above, 3 in the row below, and 2 on the sides of the current index/cell
//
//                // (rowIndex != row && columnIndex != column) will cover all 4 diagonal indexes around the current index/cell
//
//                // ((rowIndex == row && columnIndex != column) || (rowIndex != row && columnIndex == column)) will cover
//                // all 4 non-diagonal indexes around the current index/cell
//
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
                                      final char[][] grid) { // or final List<List<Character>> grid)
        return row < 0 || row >= rows
                || column < 0 || column >= columns
                || visited[row][column]
                // you may or may not need extra check like below, but if you need one, then it'll be according to
                // your requirement/problem
                || grid[row][column] == '?'         // grid.get(row).get(column) == '?' or whatever
                ;
    }

    // Just in case you need to use isValid(...) method...
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
}
