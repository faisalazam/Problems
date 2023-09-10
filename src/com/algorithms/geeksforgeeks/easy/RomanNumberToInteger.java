package com.algorithms.geeksforgeeks.easy;

public class RomanNumberToInteger {
    /**
     * Given an string in roman no format (s)  your task is to convert it to integer .
     * <p>
     * https://practice.geeksforgeeks.org/problems/roman-number-to-integer/0
     */
    private static int convertToInt(String romanStr) {
        int result = 0;
        int size = romanStr.length();
        for (int i = 0; i < size; i++) {
            int currentInt = getConvertedInt(romanStr.charAt(i));
            if (i == size - 1) {
                result += currentInt;
                return result;
            }
            int nextInt = getConvertedInt(romanStr.charAt(i + 1));
            result += currentInt >= nextInt ? currentInt : -currentInt;
        }
        return result;
    }

    private static int getConvertedInt(char romanChar) {
        switch (romanChar) {
            case 'I':
                return 1;
            case 'V':
                return 5;
            case 'X':
                return 10;
            case 'L':
                return 50;
            case 'C':
                return 100;
            case 'D':
                return 500;
            case 'M':
                return 1000;
            default:
                return -1;
        }
    }
}
