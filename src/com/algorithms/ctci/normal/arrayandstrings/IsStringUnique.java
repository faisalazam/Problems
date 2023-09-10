package com.algorithms.ctci.normal.arrayandstrings;

public class IsStringUnique {

    private static final int ASCII_CHAR_COUNT = 128;

    private static boolean isUniqueChars(String str) {
        if (str.length() > ASCII_CHAR_COUNT) {
            return false;
        }
        int checker = 0;
        for (int i = 0; i < str.length(); i++) {
            int val = str.charAt(i) - 'a';
            if ((checker & (1 << val)) > 0) {
                return false;
            }
            checker |= (1 << val);
        }
        return true;
    }

    private static boolean isUniqueChars2(String str) {
        if (str.length() > ASCII_CHAR_COUNT) {
            return false;
        }
        boolean[] charSet = new boolean[ASCII_CHAR_COUNT];
        for (int i = 0; i < str.length(); i++) {
            int val = str.charAt(i);
            if (charSet[val]) {
                return false;
            }
            charSet[val] = true;
        }
        return true;
    }

    public static void main(String[] args) {
        String[] words = {"abcde", "hello", "apple", "kite", "padle"};
        for (String word : words) {
            System.out.println(word + ": " + isUniqueChars(word) + " " + isUniqueChars2(word));
        }
    }
}
