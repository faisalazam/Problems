package com.algorithms.geeksforgeeks.basic;

import java.util.Arrays;

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
        final int sumOfNumbersFromOneToN = (n * (n + 1)) / 2; // there can be an overflow if N is large.
        return sumOfNumbersFromOneToN - sum;
    }

    // This solution will avoid the overflow issue which is possible in the previous solution but works only with sorted
    // numbers. For more options, check out https://www.geeksforgeeks.org/find-the-missing-number/
    // Example: {1, 2, 4, 5, 6} =>
    int missingNumberV1(int[] a, int n) {
        int total = 1;
        for (int i = 2; i < (n + 1); i++) {
            total += i;
            total -= a[i - 2];
        }
        return total;
    }

    // Time Complexity: O(N)
    // Auxiliary Space: O(N)
    int missingNumberV0(int[] a, int N) {
        int i;
        int[] temp = new int[N + 1];
        Arrays.fill(temp, 0);

        for (i = 0; i < N; i++) {
            temp[a[i] - 1] = 1; // Set 1 on the index for the number which exists
        }

        int ans = 0;
        for (i = 0; i <= N; i++) {
            if (temp[i] == 0) {
                ans = i + 1;
            }
        }
        return ans;
    }

    /*
     * Other Option: Cyclic sort
     * This pattern describes an interesting approach to deal with problems involving arrays containing numbers
     * in a given range. The Cyclic Sort pattern iterates over the array one number at a time, and if the current
     * number you are iterating is not at the correct index, you swap it with the number at its correct index.
     * You could try placing the number in its correct index, but this will produce a complexity of O(n²)
     * which is not optimal, hence the Cyclic Sort pattern.
     */
}
