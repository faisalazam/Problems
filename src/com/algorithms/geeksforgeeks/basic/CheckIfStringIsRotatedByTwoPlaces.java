package com.algorithms.geeksforgeeks.basic;

public class CheckIfStringIsRotatedByTwoPlaces {
    /**
     * Given two strings a and b. The task is to find if a string 'a' can be obtained by rotating another string 'b' by 2 places.
     * <p>
     * Input:
     * a = amazon
     * b = azonam
     * Output: 1
     * Explanation: amazon can be rotated anti clockwise by two places, which will make it as azonam.
     * <p>
     * https://practice.geeksforgeeks.org/problems/check-if-string-is-rotated-by-two-places/0
     * <p>
     * Time Complexity: O(N).
     * Auxiliary Complexity: O(N).
     * Challenge: Try doing it in O(1) space complexity.
     */
    boolean isRotated(String str1, String str2) {
        final int noOfRotations = 2;
        final int length1 = str1.length();
        final int length2 = str2.length();
        if (str1.isEmpty() || length1 != length2) {
            return false;
        } else if (length1 <= noOfRotations) {
            return str1.equals(str2);
        }
//        return (str1 + str1).contains(str2); it doesn't work for all test  cases
        for (int i = 0; i < length1; i++) { // working
            final int adjustedIndex = (i + noOfRotations) % length1;
            if (str1.charAt(i) != str2.charAt(adjustedIndex) // checks clockwise rotation
                    && str1.charAt(adjustedIndex) != str2.charAt(i)) {  // checks anti-clockwise rotation
                return false;
            }
        }
        return true;
    }

    boolean isRotatedV1(String str1, String str2) {
        final int noOfRotations = 2;
        if (str1 == null
                || str1.isBlank()
                || str2 == null
                || str1.length() != str2.length()) {
            return false;
        } else if (str1.length() <= noOfRotations) {
            return str1.equals(str2);
        }

        final int length = str1.length();
        for (int i = 0; i < noOfRotations; i++) { // compare the rotated part of the string
            final int adjustedIndex = length - noOfRotations + i;
            if (str1.charAt(i) != str2.charAt(adjustedIndex)
                    && str2.charAt(i) != str1.charAt(adjustedIndex)) {
                return false;
            }
        }

        for (int i = noOfRotations; i < length; i++) { // compare the non-rotated/un-touched part of the string
            if (str1.charAt(i) != str2.charAt(i - noOfRotations)
                    && str2.charAt(i) != str1.charAt(i - noOfRotations)) {
                return false;
            }
        }
        return true;
    }

    boolean isRotatedV0(String str1, String str2) {
        final int noOfRotations = 2;
        final int length1 = str1.length();
        final int length2 = str2.length();
        if (str1.isEmpty() || length1 != length2) {
            return false;
        } else if (length1 <= noOfRotations) {
            return str1.equals(str2);
        }

        final String sr = str1.substring(length1 - noOfRotations) + str1.substring(0, length1 - noOfRotations);
        final String sl = str1.substring(noOfRotations) + str1.substring(0, noOfRotations);
        return sr.equals(str2) || sl.equals(str2);
    }
}
