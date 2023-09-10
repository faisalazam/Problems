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
     */
    int strstr(String str, String target) {
        int j = -1;
        int index = -1;
        int targetLength = target.length();
        for (int i = 0; i < str.length(); i++) {
            if (j + 1 < targetLength && target.charAt(j + 1) == str.charAt(i)) {
                j++;
                index = index == -1 ? i : index;
            } else if (j == targetLength - 1) {
                break;
            } else {
                j = -1;
                index = -1;
            }
        }
        if (j == targetLength - 1) {
            return index;
        }
        return -1;
    }
}
