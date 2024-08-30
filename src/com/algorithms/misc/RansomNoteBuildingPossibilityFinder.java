package com.algorithms.misc;

import java.util.HashMap;
import java.util.Map;

public class RansomNoteBuildingPossibilityFinder {
    public static void main(String[] args) {
        String[] magazine = {"hello", "world", "blah", "blah"};
        String[] note = {"hello", "world", "blah"};
        System.out.println("Can build ransom note => " + canBuildRansomNote(magazine, note));
        System.out.println("(BruteForce) Can build ransom note => " + canBuildRansomNoteBruteForce(magazine, note));
    }

    private static boolean canBuildRansomNote(String[] magazine, String[] note) {
        Map<String, Integer> magazineWordsFrequencies = buildWordFrequencies(magazine);
        Map<String, Integer> noteWordsFrequencies = buildWordFrequencies(note);
        return hasEnoughStrings(magazineWordsFrequencies, noteWordsFrequencies);
    }

    private static boolean hasEnoughStrings(Map<String, Integer> magazineWordsFrequencies,
                                            Map<String, Integer> noteWordsFrequencies) {
        for (Map.Entry<String, Integer> noteWordFrequencyEntry : noteWordsFrequencies.entrySet()) {
            int magazineWordFrequency = magazineWordsFrequencies.getOrDefault(noteWordFrequencyEntry.getKey(), -1);
            if (noteWordFrequencyEntry.getValue() > magazineWordFrequency) {
                return false;
            }
        }
        return true;
    }

    private static Map<String, Integer> buildWordFrequencies(String[] words) {
        Map<String, Integer> wordsFrequencies = new HashMap<>();
        for (String word : words) {
            wordsFrequencies.put(word, wordsFrequencies.getOrDefault(word, 0) + 1);
        }
        return wordsFrequencies;
    }

    private static boolean canBuildRansomNoteBruteForce(String[] magazine, String[] note) {
        int index = 0;
        for (String word : note) {
            boolean found = false;
            for (int i = index; i < magazine.length; i++) {
                if (word.equals(magazine[i])) {
                    found = true;
                    final String temp = magazine[index];
                    magazine[index] = magazine[i];
                    magazine[i] = temp;
                    index++;
                    break;
                }
            }
            if (!found) {
                return false;
            }
        }
        return true;
    }
}
