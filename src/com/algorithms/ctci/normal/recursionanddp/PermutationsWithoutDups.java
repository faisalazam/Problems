package com.algorithms.ctci.normal.recursionanddp;

import java.util.ArrayList;
import java.util.List;

public class PermutationsWithoutDups {
    /**
     * Permutations without Dups; Write a method to compute all permutations of a string of unique characters
     * (there are over 6 billion permutations of a 13-character string)
     *
     * There will be n! permutations of a string where n is the number of characters in the string
     *
     * Approach: Building from permutations of first n-1 characters.
     */
    private static List<String> getPerms(String str) {
        if (str == null) {
            return new ArrayList<>();
        }
        List<String> permutations = new ArrayList<>();
        if (str.isEmpty()) {
            permutations.add("");
            return permutations;
        }

        char first = str.charAt(0); // get the first character
        String remainder = str.substring(1); // remove the first character
        List<String> words = getPerms(remainder);
        for (String word : words) {
            for (int j = 0; j <= word.length(); j++) {
                String s = insertCharAt(word, first, j);
                permutations.add(s);
            }
        }
        return permutations;
    }

    private static String insertCharAt(String word, char c, int i) {
        String start = word.substring(0, i);
        String end = word.substring(i);
        return start + c + end;
    }

    public static void main(String[] args) {
        List<String> permutations = getPerms("abcde");
        System.out.println("There are " + permutations.size() + " permutations.");
        for (String s : permutations) {
            System.out.println(s);
        }
    }
}
