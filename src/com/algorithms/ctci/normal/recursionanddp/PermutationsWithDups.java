package com.algorithms.ctci.normal.recursionanddp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PermutationsWithDups {
    /**
     * Permutations with Duplicates: Write a method to compute all permutations of a string whose characters
     * are not necessarily unique. The list of permutations should not have duplicates.
     * (there are over 6 billion permutations of a 13-character string)
     *
     * There will be n! permutations of a string where n is the number of characters in the string
     *
     * <p>
     * This solution will take 0 ( n ! ) time in the worst case (and, in fact, In all cases).
     */
    private static List<String> getPerms(String str) {
        if (str == null || str.isEmpty()) {
            return new ArrayList<>();
        }
        List<String> permutations = new ArrayList<>();
        Map<Character, Integer> map = buildFreqMap(str);
        getPerms(map, "", str.length(), permutations);
        return permutations;


    }

    private static void getPerms(Map<Character, Integer> freqMap, String prefix, int remaining, List<String> permutations) {
        // Permutation has been completed.
        if (remaining == 0) {
            permutations.add(prefix);
            return;
        }

        // Try remaining letters for next char, and generate remaining permutations.
        for (Character character : freqMap.keySet()) {
            int count = freqMap.get(character);
            if (count > 0) {
                freqMap.put(character, count - 1);
                getPerms(freqMap, prefix + character, remaining - 1, permutations);
                freqMap.put(character, count);
            }
        }
    }

    private static Map<Character, Integer> buildFreqMap(String str) {
        Map<Character, Integer> freqMap = new HashMap<>();
        for (char c : str.toCharArray()) {
            freqMap.put(c, freqMap.getOrDefault(c, 0) + 1);
        }
        return freqMap;
    }

    public static void main(String[] args) {
        List<String> permutations = getPerms("abcde");
        System.out.println("There are " + permutations.size() + " permutations.");
        for (String s : permutations) {
            System.out.println(s);
        }
    }
}
