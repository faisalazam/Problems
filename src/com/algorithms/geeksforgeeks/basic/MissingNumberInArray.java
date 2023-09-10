package com.algorithms.geeksforgeeks.basic;

public class MissingNumberInArray {
    /**
     * Given an array C of size N-1 and given that there are numbers from 1 to N with one element missing,
     * the missing number is to be found.
     * <p>
     * https://practice.geeksforgeeks.org/problems/missing-number-in-array/0
     */
    int missingNumber(int[] a, int n) {
//        int a[]=new int[n-1];
        int sum = 0;
        for (int i = 0; i < n - 1; i++) {
            sum += a[i];
        }
        int sumOfNumbersFromOneToN = (n * (n + 1)) / 2;
        return sumOfNumbersFromOneToN - sum;
    }

    /*
     * Other Option: Cyclic sort
     * This pattern describes an interesting approach to deal with problems involving arrays containing numbers
     * in a given range. The Cyclic Sort pattern iterates over the array one number at a time, and if the current
     * number you are iterating is not at the correct index, you swap it with the number at its correct index.
     * You could try placing the number in its correct index, but this will produce a complexity of O(n^2)
     * which is not optimal, hence the Cyclic Sort pattern.
     */
}
