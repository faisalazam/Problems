package com.algorithms.leetcode.medium;


/*
 * You are given an n x n 2D matrix representing an image.
 *
 * Rotate the image by 90 degrees (clockwise).
 *
 * Note: You have to rotate the image in-place, which means you have to modify the input 2D matrix directly.
 * DO NOT allocate another 2D matrix and do the rotation.
 *
 * https://leetcode.com/problems/rotate-image/
 */
public class TwoDInPlaceMatrixRotation {
    public static void main(String[] args) {
        int[][] matrix = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };

        rotate(matrix);
        print(matrix);
//
//        rotate90Clockwise(matrix);
//        print(matrix);
//
//        rotate90AntiClockwise(matrix);
//        print(matrix);
    }

    private static void print(int[][] matrix) {
        for (int[] ints : matrix) {
            for (int column = 0; column < matrix[0].length; column++) {
                System.out.print(ints[column] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    /**
     * SOLUTION
     * Because we're rotating the matrix by 90 degrees, the easiest way to do this is to implement the rotation in layers.
     * We perform a circular rotation on each layer, moving the top edge to the right edge,
     * the right edge to the bottom edge, the bottom edge to the left edge, and the left edge to the top edge.
     */
    private static void rotate(int[][] matrix) {
        int n = matrix.length;
        for (int layer = 0; layer < n / 2; layer++) {
            int first = layer;
            int last = n - 1 - layer;
            for (int i = first; i < last; i++) {
                int offset = i - first;
                int top = matrix[first][i]; // save top

                // left -> top
                System.out.println(first + "," + i + " => " + (last - offset) + "," + first);
                matrix[first][i] = matrix[last - offset][first];

                // bottom -> left
                System.out.println((last - offset) + "," + first + " => " + last + "," + (last - offset));
                matrix[last - offset][first] = matrix[last][last - offset];

                // right -> bottom
                System.out.println(last + "," + (last - offset) + " => " + i + "," + last);
                matrix[last][last - offset] = matrix[i][last];

                // top -> right
                System.out.println(i + "," + last + " => " + first + "," + i);
                matrix[i][last] = top; // right <- saved top
                System.out.println();
            }
        }
    }

    private static void rotate90Clockwise(int[][] matrix) {
        transpose(matrix);
        for (int[] row : matrix) {
            reverseRow(row);
        }
    }

    private static void rotate90AntiClockwise(int[][] matrix) {
        transpose(matrix);
        for (int column = 0; column < matrix[0].length; column++) {
            reverseColumn(column, matrix);
        }
    }

    private static void reverseColumn(int column, int[][] matrix) {
        int start = 0;
        int end = matrix.length - 1;
        while (start < end) {
            int temp = matrix[start][column];
            matrix[start][column] = matrix[end][column];
            matrix[end][column] = temp;
            start++;
            end--;
        }
    }

    private static void reverseRow(int[] row) {
        int start = 0;
        int end = row.length - 1;
        while (start < end) {
            int temp = row[end];
            row[end] = row[start];
            row[start] = temp;
            start++;
            end--;
        }
    }

    /*
    Tower of Hanoi
          *
         ***
        *****
       *******
      *********
     ***********
    *************                4,2              3,1,




    5 0 5 2 2 3 4 0 4 1 1 3

    Your Output is:
    4 5 1 0 2 3

        5->0
        5->2
        2->3
        4->0
        4->1
        1->3

    0 has 2 incoming
    1 has 1 incoming
    2 has 1 incoming
    3 has 2 incoming
    4 has 0 incoming
    5 has 0 incoming

    Q -> {4, 5}
    remove 4
    Q -> {5, 1}

    0 has 1 incoming
    1 has 0 incoming
    2 has 1 incoming
    3 has 2 incoming
    5 has 0 incoming

    Q -> {5, 1}
    remove 5
    Q -> {1, 0, 2}

    0 has 0 incoming
    2 has 0 incoming
    3 has 2 incoming

    Q -> {1, 0, 2}
    remove 1
    Q -> {0, 2}

    3 has 0 incoming

    {3} -> {}

    4 5 1 0 2 3



     */
    private static void transpose(int[][] matrix) {
        for (int row = 0; row < matrix.length; row++) {
            for (int column = row; column < matrix[row].length; column++) {
                if (row != column) {
                    swap(row, column, matrix);
                }
            }
        }
    }

    /**
     * a = a - b;
     * b = b + a;
     * a = b - a;
     */
    private static void swap(int row, int column, int[][] matrix) {
        if (row >= 0 && row < matrix.length && column >= 0 && column < matrix[row].length) {
            int temp = matrix[column][row];
            matrix[column][row] = matrix[row][column];
            matrix[row][column] = temp;
        }
    }
}
