package com.algorithms.misc;

public class DeadOrAliveFinder {

    public static void main(String[] args) {
        char[][] boat = {
                {'x', 'o', '-'},
                {'o', 'x', 'x'},
                {'-', 'o', 'x'}
        };
        int row = 2;
        int column = 2;
        System.out.println(isAlive(row, column, boat));
    }

    /*
     * Will be alive only if jump is possible to an empty/blank cell
     * (and by jumping only on the same color, e.g. x can jump only on x (but not on o) or blank and vice versa.)
     */
    private static boolean isAlive(int row, int column, char[][] boat) {
        final boolean[][] visited = new boolean[boat.length][boat[0].length];
        return isAlive(row, column, boat, visited, boat[row][column]);
    }

    private static boolean isAlive(int row, int column, char[][] boat, boolean[][] visited, char value) {
        if (isNotValid(row, column, boat) || visited[row][column]) {
            return false;
        } else if (value != boat[row][column] && !isBlankCell(boat[row][column])) {
            return false;
        } else if (isBlankCell(boat[row][column])) {
            return true;
        }
        visited[row][column] = true;
        return isAlive(row - 1, column, boat, visited, value)
                || isAlive(row + 1, column, boat, visited, value)
                || isAlive(row, column - 1, boat, visited, value)
                || isAlive(row, column + 1, boat, visited, value);
    }

    private static boolean isBlankCell(char c) {
        return '-' == c;
    }

    private static boolean isNotValid(int row, int column, char[][] boat) {
        return row < 0 || row >= boat.length || column < 0 || column >= boat[0].length;
    }
}
