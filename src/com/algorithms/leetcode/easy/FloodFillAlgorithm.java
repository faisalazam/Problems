package com.algorithms.leetcode.easy;


import com.algorithms.misc.Pair;

import java.util.LinkedList;
import java.util.Queue;

/*
 * Given a 2D screen, location of a pixel in the screen ie(x,y) and a color(K),
 * your task is to replace color of the given pixel and all adjacent(excluding diagonally adjacent)
 * same colored pixels with the given color K.
 *
 * https://leetcode.com/problems/flood-fill/
 *
 * Is this implementation same as https://practice.geeksforgeeks.org/problems/flood-fill-algorithm/0 ?
 *
 * Example:
 *
 * {{1, 1, 1, 1, 1, 1, 1, 1},
 * {1, 1, 1, 1, 1, 1, 0, 0},
 * {1, 0, 0, 1, 1, 0, 1, 1},
 * {1, 2, 2, 2, 2, 0, 1, 0},
 * {1, 1, 1, 2, 2, 0, 1, 0},
 * {1, 1, 1, 2, 2, 2, 2, 0},
 * {1, 1, 1, 1, 1, 2, 1, 1},
 * {1, 1, 1, 1, 1, 2, 2, 1},
 *  };
 *
 *  x=4, y=4, color=3
 *
 * {{1, 1, 1, 1, 1, 1, 1, 1},
 * {1, 1, 1, 1, 1, 1, 0, 0},
 * {1, 0, 0, 1, 1, 0, 1, 1},
 * {1, 3, 3, 3, 3, 0, 1, 0},
 * {1, 1, 1, 3, 3, 0, 1, 0},
 * {1, 1, 1, 3, 3, 3, 3, 0},
 * {1, 1, 1, 1, 1, 3, 1, 1},
 * {1, 1, 1, 1, 1, 3, 3, 1}, };
 *
 * Note: Use zero based indexing.
 *
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
        int rows = screen.length;
        int columns = screen[0].length;
        boolean[][] visited = new boolean[rows][columns];
//        updateScreenColorV1(x, y, screen[x][y], color, screen, visited);
        if (screen[x][y] != color) { // No need to update if the target pixel already have the new color
            updateScreenColorV2(x, y, color, screen);
        }

        printScreen(columns, screen);
    }

    private static void printScreen(int columns, int[][] screen) {
        StringBuilder builder = new StringBuilder();
        for (int[] ints : screen) {
            for (int column = 0; column < columns; column++) {
                builder.append(ints[column]).append(" ");
            }
            builder.append("\n");
        }
        System.out.println(builder.toString());
    }

    private static void updateScreenColorV2(int x, int y, int newColor, int[][] screen) {
        int originalColor = screen[x][y];
        Queue<Pair<Integer, Integer>> queue = new LinkedList<>();
        queue.offer(Pair.of(x, y));

        while (!queue.isEmpty()) {
            Pair<Integer, Integer> currentPixel = queue.poll();
            int row = currentPixel.fst;
            int column = currentPixel.snd;
            screen[row][column] = newColor;

            if (row - 1 >= 0 && screen[row - 1][column] == originalColor) {
                queue.offer(Pair.of(row - 1, column));
            }
            if (row + 1 < screen.length && screen[row + 1][column] == originalColor) {
                queue.offer(Pair.of(row + 1, column));
            }
            if (column - 1 >= 0 && screen[row][column - 1] == originalColor) {
                queue.offer(Pair.of(row, column - 1));
            }
            if (column + 1 < screen[row].length && screen[row][column + 1] == originalColor) {
                queue.offer(Pair.of(row, column + 1));
            }
        }
    }

    /**
     * Does this algorithm seem familiar? It should! This is essentially depth-first search on a graph
     */
    private static void updateScreenColorV1(int x, int y, int originalColor, int newColor, int[][] screen, boolean[][] visited) {
        if (isNotValid(x, y, screen, visited)) {
            return;
        }
        visited[x][y] = true;
        if (screen[x][y] == originalColor) {
            screen[x][y] = newColor;
        }
        updateScreenColorV1(x - 1, y, originalColor, newColor, screen, visited);
        updateScreenColorV1(x + 1, y, originalColor, newColor, screen, visited);
        updateScreenColorV1(x, y - 1, originalColor, newColor, screen, visited);
        updateScreenColorV1(x, y + 1, originalColor, newColor, screen, visited);
    }

    private static boolean isNotValid(int x, int y, int[][] screen, boolean[][] visited) {
        return x < 0 || x >= screen.length || y < 0 || y >= screen[x].length || visited[x][y];
    }
}
