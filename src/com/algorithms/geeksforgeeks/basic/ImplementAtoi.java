package com.algorithms.geeksforgeeks.basic;

public class ImplementAtoi {
    /**
     * StringToIntegerConvertor
     * <p>
     * Your task  is to implement the function atoi. The function takes a string(str) as argument and
     * converts it to an integer and returns it.
     * <p>
     * https://practice.geeksforgeeks.org/problems/implement-atoi/1
     * <p>
     * Time Complexity: O(|S|), |S| = length of string str.
     * Auxiliary Space: O(1)
     */
    int atoi(String str) {
        int i = 0;
        int converted = 0;
        int negativeMultiplier = 1;
        str = str.trim();
        // sign of number
        if (str.charAt(i) == '-' || str.charAt(i) == '+') {
            // It'll be -1 for negative numbers and 1 for positive numbers
            negativeMultiplier = 1 - (2 * (str.charAt(i++) == '-' ? 1 : 0));
        }
        while (i < str.length()) {
            final char character = str.charAt(i++);
            if (!Character.isDigit(character)) {
                return -1;
            }
            final int charIntValue = character - '0';
            converted = (converted * 10) + charIntValue;
        }
        return negativeMultiplier * converted;
    }
}
