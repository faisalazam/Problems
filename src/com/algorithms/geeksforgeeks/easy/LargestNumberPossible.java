package com.algorithms.geeksforgeeks.easy;

public class LargestNumberPossible {
    /**
     * Given two numbers 'N' and 'S', find the largest number that can be formed with 'N' digits and whose sum of
     * digits should be equals to 'S'.
     * <p>
     * Example 1:
     * <p>
     * Input: N = 2, S = 9
     * Output: 90
     * Explanation: It is the biggest number with sum of digits equals to 9.
     * Example 2:
     * <p>
     * Input: N = 3, S = 20
     * Output: 992
     * Explanation: It is the biggest number with sum of digits equals to 20.
     * <p>
     * https://practice.geeksforgeeks.org/problems/largest-number-possible/0
     */
    private static String largestNumberPossible(int N, int S) {
        if (N > 1 && S == 0) {
            return "-1";
        } else if (S == 0) {
            return "0";
        }
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < N; i++) {
            if (S >= 9) {
                S -= 9;
                builder.append("9");
            } else {
                builder.append(S);
                S = 0;
            }
        }
        String largestNumberPossible = builder.toString();
        return (largestNumberPossible.isEmpty() || S != 0) ? "-1" : largestNumberPossible;
    }
}
