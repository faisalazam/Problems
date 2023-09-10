package com.algorithms.geeksforgeeks.medium;

import java.util.LinkedList;
import java.util.Queue;

public class FindWhetherPathExist {
    /**
     * Given a N X N matrix (M) filled with 1, 0, 2, 3. The task is to find whether there is a path possible
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
    private static int doesPathExist(int[][] M, int N) {
        Integer[] src = find(M, N, 1);
        Integer[] dest = find(M, N, 2);
        if (src.length == 0 || dest.length == 0) {
            return 0;
        }
        boolean[][] visited = new boolean[N][N];
        Queue<Integer[]> queue = new LinkedList<>();
        queue.offer(src);
        while (!queue.isEmpty()) {
            Integer[] curr = queue.poll();
            if (curr[0].equals(dest[0]) && curr[1].equals(dest[1])) {
                return 1;
            }
            visited[curr[0]][curr[1]] = true;
            addToQueue(curr[0] - 1, curr[1], M, N, visited, queue);
            addToQueue(curr[0] + 1, curr[1], M, N, visited, queue);
            addToQueue(curr[0], curr[1] - 1, M, N, visited, queue);
            addToQueue(curr[0], curr[1] + 1, M, N, visited, queue);
        }
        return 0;
    }

    private static Integer[] find(int[][] M, int N, int value) {
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

    private static boolean isNotValid(int i, int j, int N, boolean[][] visited) {
        return i < 0 || i >= N || j < 0 || j >= N || visited[i][j];
    }
}
