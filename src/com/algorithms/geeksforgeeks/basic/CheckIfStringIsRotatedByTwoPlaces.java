package com.algorithms.geeksforgeeks.basic;

public class CheckIfStringIsRotatedByTwoPlaces {
    /**
     * Given two strings a and b. The task is to find if a string 'a' can be obtained by rotating another string 'b' by 2 places.
     * <p>
     * https://practice.geeksforgeeks.org/problems/check-if-string-is-rotated-by-two-places/0
     */
    int isRotated(String str1, String str2) {
        return (str1 + str1).contains(str2) ? 1 : 0;
    }
}
