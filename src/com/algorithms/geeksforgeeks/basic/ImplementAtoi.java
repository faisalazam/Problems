package com.algorithms.geeksforgeeks.basic;

public class ImplementAtoi {
    /**
     * Your task  is to implement the function atoi. The function takes a string(str) as argument and
     * converts it to an integer and returns it.
     * <p>
     * https://practice.geeksforgeeks.org/problems/implement-atoi/1
     */
    int atoi(String str) {
        int i = 0;
        int converted = 0;
        int negativeMultiplier = 1;
        if (str.charAt(i) == '-') {
            i++;
            negativeMultiplier = -1;
        }
        while (i < str.length()) {
            int characterValue = str.charAt(i++) - '0';
            if (isDigit(characterValue)) {
                converted = (converted * 10) + characterValue;
            } else {
                return -1;
            }
        }
        return negativeMultiplier * converted;
    }

    private boolean isDigit(int characterValue) {
        return characterValue >= 0 && characterValue <= 9;
    }
}
