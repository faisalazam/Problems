package com.algorithms.ctci.normal.recursionanddp;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Robot in a Grid: Imagine a robot sitting in the upper left corner of grid with r rows and c columns.
 * The robot can only move in two directions, right and down, but certain cells are "off limits"
 * such that the robot cannot step on them.
 * Design an algorithm to find a path for the robot from the top left to the bottom right.
 */
public class FindPathInGridBySteppingRightOrDown {
    /**
     * Solution:
     * Starting from the last cell, we try to find a path to each of its adjacent cells.
     * The recursive code below implements this algorithm.
     * <p>
     * This solution is 0 (2^(r+c)) , since each path has r+c steps and there are two choices we can make at each step.
     */
    private static List<Point> buildPath(int[][] maze) {
        List<Point> paths = new ArrayList<>();
        if (maze == null || maze.length == 0) {
            return paths;
        }
        if (buildPath(maze, maze.length - 1, maze[0].length - 1, paths)) {
            return paths;
        }
        return Collections.emptyList();
    }

    private static boolean isNotFree(int[][] maze, int row, int column) {
        return maze[row][column] == 0;
    }

    private static boolean buildPath(int[][] maze, int row, int column, List<Point> paths) {
        if (column < 0 || row < 0 || isNotFree(maze, row, column)) {
            return false;
        }

        final boolean isAtOrigin = row == 0 && column == 0;

        if (isAtOrigin || buildPath(maze, row, column - 1, paths) || buildPath(maze, row - 1, column, paths)) {
            paths.add(new Point(row, column));
            return true;
        }
        return false;
    }

    /**
     * Optimised Solution:
     * <p>
     * The algorithm will now take O(R*C) time because we hit each cell just once.
     */
    private static boolean getPath(int[][] maze, int row, int column, List<Point> path, Map<Point, Boolean> cache) {
        if (column < 0 || row < 0 || isNotFree(maze, row, column)) {
            return false;
        }

        final Point point = new Point(row, column);
        if (cache.containsKey(point)) {
            return cache.get(point);
        }

        boolean success = false;
        final boolean isAtOrigin = (row == 0) && (column == 0);
        if (isAtOrigin || getPath(maze, row, column - 1, path, cache) || getPath(maze, row - 1, column, path, cache)) {
            path.add(point);
            success = true;
        }
        cache.put(point, success);
        return success;
    }
}
