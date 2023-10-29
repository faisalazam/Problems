package com.algorithms.templates;

public class TwoDArrayTo1DArrayUsingEuclideanDivisionTemplate {

    /**
     * If the 2D array is of int, then Euclidean Division can be used to treat is as just one dimensional array.
     * "columns" can be used as multiplier/divisor as it'll be big enough.
     */
    public static void main(String[] args) {
        final int[][] matrix = new int[5][2];
        final int rows = matrix.length;
        final int columns = matrix[0].length;

        // the very last (bottom right) index using Euclidean Division
        int oneDimensionalIndex = ((rows - 1) * columns) + (columns - 1); // converting 2D index into 1D index
        while (oneDimensionalIndex >= 0) { // loop is just to print the row/col values. It's not part of the actual template
            final int row = oneDimensionalIndex / columns; // extracting the row index
            final int column = oneDimensionalIndex % columns; // extracting the column index

            System.out.println("index=" + oneDimensionalIndex + ", (row, column)=(" + row + ", " + column + ")");
            oneDimensionalIndex--;
        }
    }
}
