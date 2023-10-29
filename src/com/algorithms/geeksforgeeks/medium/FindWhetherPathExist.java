package com.algorithms.geeksforgeeks.medium;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Given a N X N matrix (grid) filled with 1, 0, 2, 3. The task is to find whether there is a path possible
 * from source to destination, while traversing through blank cells only. You can traverse up, down, right and left.
 * <p>
 * A value of cell 1 means Source.
 * A value of cell 2 means Destination.
 * A value of cell 3 means Blank cell.
 * A value of cell 0 means Blank Wall.
 * Note: there is only single source and single destination.
 * <p>
 * https://practice.geeksforgeeks.org/problems/find-whether-path-exist/0
 */
public class FindWhetherPathExist {
    /**
     * Time Complexity: O(n*n).In the worst case, every cell of the matrix is visited only once so the time complexity
     * is O(n²).
     * Space Complexity: O(n*n). Space is required to create the queue.
     */
    private static boolean doesPathExistBFS(int[][] grid) {
        final int size = grid.length;
        final Integer[] srcCoordinates = findCell(grid, size, 1);
        if (srcCoordinates.length == 0) {
            return false;
        }
        final boolean[][] visited = new boolean[size][size];
        final Queue<Integer[]> queue = new LinkedList<>();
        queue.offer(srcCoordinates);
        while (!queue.isEmpty()) {
            final Integer[] curr = queue.poll();
            // check if the curr value is same as the destination
            if (grid[curr[0]][curr[1]] == 2) {
                return true;
            }
            visited[curr[0]][curr[1]] = true;
            addToQueue(curr[0] - 1, curr[1], grid, size, visited, queue);
            addToQueue(curr[0] + 1, curr[1], grid, size, visited, queue);
            addToQueue(curr[0], curr[1] - 1, grid, size, visited, queue);
            addToQueue(curr[0], curr[1] + 1, grid, size, visited, queue);
        }
        return false;
    }

    private static Integer[] findCell(int[][] M, int N, int value) {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (M[i][j] == value) {
                    return new Integer[]{i, j};
                }
            }
        }
        return new Integer[]{};
    }

    private static void addToQueue(int i, int j, int[][] M, int N, boolean[][] visited, Queue<Integer[]> queue) {
        if (!isNotValid(i, j, N, visited)) {
            if (M[i][j] != 0) {
                queue.offer(new Integer[]{i, j});
            }
        }
    }

    /**
     * Time Complexity: O(n*n).In the worst case, every cell of the matrix is visited only once so the time complexity
     * is O(n²).
     * Space Complexity: O(n*n). Space is required to store the visited array
     */
    public static boolean doesPathExistDFS(int[][] grid) {
        final int size = grid.length;
        final Integer[] srcCoordinates = findCell(grid, size, 1);
        if (srcCoordinates.length == 0) {
            return false;
        }
        final boolean[][] visited = new boolean[size][size];
        return isPath(grid, size, srcCoordinates[0], srcCoordinates[1], visited);
    }

    private static boolean isPath(int[][] grid, int size, int i, int j, boolean[][] visited) {
        if (isNotValid(i, j, size, visited) || grid[i][j] == 0) {
            return false;
        }
        visited[i][j] = true;

        if (grid[i][j] == 2)
            return true;

        boolean up = isPath(grid, size, i - 1, j, visited);

        if (up)
            return true;

        boolean left = isPath(grid, size, i, j - 1, visited);

        if (left)
            return true;

        boolean down = isPath(grid, size, i + 1, j, visited);

        if (down)
            return true;

        boolean right = isPath(grid, size, i, j + 1, visited);

        return right;
    }

    private static boolean isNotValid(int i, int j, int N, boolean[][] visited) {
        return i < 0 || i >= N || j < 0 || j >= N || visited[i][j];
    }
}
