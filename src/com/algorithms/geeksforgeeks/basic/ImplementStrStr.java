package com.algorithms.geeksforgeeks.basic;

public class ImplementStrStr {
    /**
     * Your task is to implement the function strstr. The function takes two strings as arguments (s,x) and
     * locates the occurrence of (sort of indexOf method) the string x in the string s. The function returns and
     * integer denoting the first occurrence of the string x in s.
     * <p>
     * Note : Try to solve the question in constant space complexity.
     * <p>
     * https://practice.geeksforgeeks.org/problems/implement-strstr/1
     * <p>
     * Time Complexity: O(N*M), where N and M are lengths of s and x respectively, Nested loops are used, outer from
     * 0 to N – M and inner from 0 to M
     * Auxiliary Space: O(1), As no extra space is required.
     * <p>
     * Note : We can do this question in a much more optimised way O(N+M) using KMP Algorithm.
     */
    static int strstr(String s, String x) {
        if (s == null || x == null || s.isBlank() || x.isBlank() || x.length() > s.length()) {
            return -1;
        }
        final int N = s.length();
        final int M = x.length();
        for (int i = 0; i <= N - M; i++) {
            if (x.charAt(0) != s.charAt(i)) {
                continue;
            }
            int j;
            for (j = 0; j < M; j++) {
                if (s.charAt(i + j) != x.charAt(j)) {
                    break;
                }
            }
            if (j == M) {
                return i;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
//        System.out.println(strstr("abaaaaaaaa", "abaa"));
        System.out.println(strstr("ababaaaaaa", "abaa"));
    }
}
