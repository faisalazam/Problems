package com.algorithms.leetcode.easy;


import java.util.ArrayDeque;
import java.util.Queue;

/**
 * Given a 2D screen, location of a pixel in the screen ie(x,y) and a color(K),
 * your task is to replace color of the given pixel and all adjacent(excluding diagonally adjacent)
 * same colored pixels with the given color K.
 * <p>
 * https://leetcode.com/problems/flood-fill/
 * <p>
 * Is this implementation same as https://practice.geeksforgeeks.org/problems/flood-fill-algorithm/0 ?
 * <p>
 * Example:
 * <p>
 * {{1, 1, 1, 1, 1, 1, 1, 1},
 * {1, 1, 1, 1, 1, 1, 0, 0},
 * {1, 0, 0, 1, 1, 0, 1, 1},
 * {1, 2, 2, 2, 2, 0, 1, 0},
 * {1, 1, 1, 2, 2, 0, 1, 0},
 * {1, 1, 1, 2, 2, 2, 2, 0},
 * {1, 1, 1, 1, 1, 2, 1, 1},
 * {1, 1, 1, 1, 1, 2, 2, 1},
 * };
 * <p>
 * x=4, y=4, color=3
 * <p>
 * {{1, 1, 1, 1, 1, 1, 1, 1},
 * {1, 1, 1, 1, 1, 1, 0, 0},
 * {1, 0, 0, 1, 1, 0, 1, 1},
 * {1, 3, 3, 3, 3, 0, 1, 0},
 * {1, 1, 1, 3, 3, 0, 1, 0},
 * {1, 1, 1, 3, 3, 3, 3, 0},
 * {1, 1, 1, 1, 1, 3, 1, 1},
 * {1, 1, 1, 1, 1, 3, 3, 1}, };
 * <p>
 * Note: Use zero based indexing.
 */
public class FloodFillAlgorithm {
    public static void main(String[] args) {
        int[][] screen = {
                {1, 1, 1, 1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1, 1, 0, 0},
                {1, 0, 0, 1, 1, 0, 1, 1},
                {1, 2, 2, 2, 2, 0, 1, 0},
                {1, 1, 1, 2, 2, 0, 1, 0},
                {1, 1, 1, 2, 2, 2, 2, 0},
                {1, 1, 1, 1, 1, 2, 1, 1},
                {1, 1, 1, 1, 1, 2, 2, 1},
        };
        int x = 4, y = 4, color = 3;
//        floodFillDFS(screen, x, y, color);
        final int rows = screen.length;
        final int columns = screen[0].length;
        final boolean[][] visited = new boolean[rows][columns]; // Time Limit Exceeded even after using visited array
        updateColorBFS(x, rows, y, columns, color, screen, visited);

        printScreen(columns, screen);
    }

    /**
     * Time complexity: O(M x N).
     * Auxiliary Space: O(M x N),
     */
    public static int[][] floodFillBFS(int[][] image, int sr, int sc, int newColor) {
        if (image[sr][sc] == newColor) { // No need to update if the target pixel already have the new color
            return image;
        }

        final int rows = image.length;
        final int columns = image[0].length;
        final boolean[][] visited = new boolean[rows][columns];
        updateColorBFS(sr, rows, sc, columns, newColor, image, visited);
        return image;
    }

    /**
     * The idea is simple, we first replace the color of the current pixel, then recur for 4 surrounding points.
     * <p>
     * Time complexity: O(M x N).
     * Auxiliary Space: O(M x N), as implicit stack is created due to recursion
     */
    public static int[][] floodFillDFS(int[][] image, int sr, int sc, int newColor) {
        final int originalColor = image[sr][sc];
        if (originalColor == newColor) { // No need to update if the target pixel already have the new color
            return image;
        }
        final int rows = image.length;
        final int columns = image[0].length;
        final boolean[][] visited = new boolean[rows][columns];
        updateColorDFS(sr, sc, originalColor, newColor, image, visited);
        return image;
    }

    private static void updateColorBFS(final int row,
                                       final int rows,
                                       final int column,
                                       final int columns,
                                       final int newColor,
                                       final int[][] image,
                                       final boolean[][] visited) {
        final int originalColor = image[row][column];
        final Queue<Integer> queue = new ArrayDeque<>();
        queue.offer((row * columns) + column); // single dimensional representation of [i][j] or [row][column]

        while (!queue.isEmpty()) {
            final int currentPixel = queue.poll();
            final int currentRow = currentPixel / columns;
            final int currentColumn = currentPixel % columns;

            visited[currentRow][currentColumn] = true;
            image[currentRow][currentColumn] = newColor;

            addCell(currentRow - 1, rows, currentColumn, columns, image, originalColor, queue, visited);
            addCell(currentRow + 1, rows, currentColumn, columns, image, originalColor, queue, visited);
            addCell(currentRow, rows, currentColumn - 1, columns, image, originalColor, queue, visited);
            addCell(currentRow, rows, currentColumn + 1, columns, image, originalColor, queue, visited);
        }
    }

    private static void addCell(final int row,
                                final int rows,
                                final int column,
                                final int columns,
                                final int[][] image,
                                final int originalColor,
                                final Queue<Integer> queue,
                                final boolean[][] visited) {
        if (isValid(row, rows, column, columns, originalColor, image, visited)) {
            visited[row][column] = true;
            queue.offer((row * columns) + column); // single dimensional representation of [i][j] or [row][column]
        }
    }

    /**
     * Does this algorithm seem familiar? It should! This is essentially depth-first search on a graph
     */
    private static void updateColorDFS(final int row,
                                       final int column,
                                       final int originalColor,
                                       final int newColor,
                                       final int[][] image,
                                       final boolean[][] visited) {
        if (isNotValid(row, column, originalColor, image, visited)) {
            return;
        }
        visited[row][column] = true;
        image[row][column] = newColor;

        updateColorDFS(row - 1, column, originalColor, newColor, image, visited);
        updateColorDFS(row + 1, column, originalColor, newColor, image, visited);
        updateColorDFS(row, column - 1, originalColor, newColor, image, visited);
        updateColorDFS(row, column + 1, originalColor, newColor, image, visited);
    }

    private static boolean isNotValid(final int row,
                                      final int column,
                                      final int originalColor,
                                      final int[][] image,
                                      final boolean[][] visited) {
        return row < 0 || row >= image.length
                || column < 0 || column >= image[row].length
                || visited[row][column]
                || image[row][column] != originalColor
                ;
    }

    private static boolean isValid(final int row,
                                   final int rows,
                                   final int column,
                                   final int columns,
                                   final int originalColor,
                                   final int[][] image,
                                   final boolean[][] visited) {
        return row >= 0 && row < rows
                && column >= 0 && column < columns
                && !visited[row][column]
                && image[row][column] == originalColor;
    }

    private static void printScreen(int columns, int[][] screen) {
        StringBuilder builder = new StringBuilder();
        for (int[] ints : screen) {
            for (int column = 0; column < columns; column++) {
                builder.append(ints[column]).append(" ");
            }
            builder.append("\n");
        }
        System.out.println(builder);
    }
}
