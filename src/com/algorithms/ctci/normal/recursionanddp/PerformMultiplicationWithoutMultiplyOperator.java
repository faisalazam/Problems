package com.algorithms.ctci.normal.recursionanddp;

public class PerformMultiplicationWithoutMultiplyOperator {
    /**
     * Recursive Multiply: Write a recursive function to multiply two positive integers without using
     * the * operator (or / operator}. You can use addition, subtraction, and bit shifting,
     * but you should minimize the number of those operations.
     * <p>
     * This algorithm will run in O(log s) time, where s is the smaller of the two numbers.
     */
    private int minProduct(int a, int b) {
        int bigger = a < b ? b : a;
        int smaller = a < b ? a : b;
        return minProductHelper(smaller, bigger);
    }

    private int minProductHelper(int smaller, int bigger) {
        if (smaller == 0) {
            return 0;
        } else if (smaller == 1) {
            return bigger;
        }

        int s = smaller >> 1; //Divide by 2
        int halfProd = minProductHelper(s, bigger);
        int evenResult = halfProd + halfProd;
        if (smaller % 2 == 0) {
            return evenResult;
        } else {
            return evenResult + bigger;
        }
    }

    public static void main(String[] args) {
        PerformMultiplicationWithoutMultiplyOperator multiply = new PerformMultiplicationWithoutMultiplyOperator();
        System.out.println(multiply.minProduct(3, 5));
    }
}
