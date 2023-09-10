package com.algorithms.misc;

/*
 * Giving a dictionary and a string ‘str’, your task is to find the longest string in dictionary of size x,
 * which can be formed by deleting some characters of the given ‘str’.
 */
public class LargestWordInDictionaryAfterReplacement {
    private static final int CHARACTERS = 26;

    public static void main(String[] args) {
        String[] dict1 = {"ale", "apple", "monkey", "plea"};
        String str1 = "abpcplea";
        int largestWord = findLargestWordV1(dict1, str1.trim().toLowerCase());
        System.out.println("V1 -> Largest word => " + (largestWord == -1 ? "NOT_POSSIBLE" : dict1[largestWord]));

        largestWord = findLargestWordV2(dict1, str1.trim().toLowerCase());
        System.out.println("V2 -> Largest word => " + (largestWord == -1 ? "NOT_POSSIBLE" : dict1[largestWord]));

        String[] dict2 = {"pintu", "geeksfor", "geeksgeeks", " forgeek"};
        String str2 = "geeksforgeeks";
        largestWord = findLargestWordV1(dict2, str2.trim().toLowerCase());
        System.out.println("V1 -> Largest word => " + (largestWord == -1 ? "NOT_POSSIBLE" : dict2[largestWord]));

        largestWord = findLargestWordV1(dict2, str2.trim().toLowerCase());
        System.out.println("V2 -> Largest word => " + (largestWord == -1 ? "NOT_POSSIBLE" : dict2[largestWord]));
    }

    private static int findLargestWordV2(String[] dict, String str) {
        int index = -1;
        int maxLength = 0;

        for (int i = 0; i < dict.length; i++) {
            String word = dict[i].trim().toLowerCase();
            if (isWordFormationPossible(word, str) && word.length() > maxLength) {
                index = i;
                maxLength = word.length();
            }
        }
        return index;
    }

    private static boolean isWordFormationPossible(String source, String target) {
        if (target.length() < source.length()) {
            return false;
        }
        int index = 0;
        for (int i = 0; i < target.length(); i++) {
            if (index == source.length()) {
                return true;
            } else if (source.charAt(index) == target.charAt(i)) {
                index++;
            }
        }
        return false;
    }

    private static int findLargestWordV1(String[] dict, String str) {
        int index = -1;
        int maxLength = 0;
        int[] strCharFrequencies = buildFrequencies(str.toLowerCase());
        for (int i = 0; i < dict.length; i++) {
            String s = dict[i];
            String wordOrBlank = getWordOrBlank(s, strCharFrequencies.clone());
            if (wordOrBlank.length() > maxLength) {
                index = i;
                maxLength = wordOrBlank.length();
            }
        }
        return index;
    }

    private static String getWordOrBlank(String str, int[] charFrequencies) {
        String lowerCased = (str == null || str.trim().isEmpty()) ? "" : str.trim().toLowerCase();
        for (int i = 0; i < lowerCased.length(); i++) {
            int charIndex = getCharIndex(lowerCased.charAt(i));
            if (charFrequencies[charIndex] == 0) {
                return "";
            }
            charFrequencies[charIndex]--;
        }
        return lowerCased;
    }

    private static int[] buildFrequencies(String str) {
        int[] charFrequencies = new int[CHARACTERS];
        for (int i = 0; i < str.length(); i++) {
            int charIndex = getCharIndex(str.charAt(i));
            charFrequencies[charIndex]++;
        }
        return charFrequencies;
    }

    private static int getCharIndex(char character) {
        return character - 'a';
    }
}
