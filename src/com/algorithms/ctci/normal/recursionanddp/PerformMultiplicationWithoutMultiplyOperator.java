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
        int bigger = Math.max(a, b);
        int smaller = Math.min(a, b);
        return minProductHelper(smaller, bigger);
    }

    private int minProductHelper(int smaller, int bigger) {
        if (smaller == 0) {
            return 0;
        } else if (smaller == 1) { // As we are trying to multiply smaller and bigger, i.e. bigger * 1 = bigger
            return bigger;
        }

        // Shifting a number "1"bit position to the right will have the effect of dividing by 2:
        //8 >> 1 = 4    // In binary: (00001000) >> 1 = (00000100)
        //and shifting a number "2"bit positions to the right will have the effect of dividing by 4:
        //8 >> 2 = 2    // In binary: (00001000) >> 2 = (00000010)
        // a similar logic is --- left shifting 'n' times means multiplying by 2^n.
        int s = smaller >> 1; //Divide by 2 - bit-shifting right by 1 accomplishes the same thing as dividing num by two
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
