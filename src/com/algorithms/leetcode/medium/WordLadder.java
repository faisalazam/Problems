package com.algorithms.leetcode.medium;

import com.algorithms.misc.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class WordLadder {
    /**
     * Given two words (beginWord and endWord), and a dictionary's word list,
     * find the length of shortest transformation sequence from beginWord to endWord, such that:
     * <p>
     * Only one letter can be changed at a time.
     * Each transformed word must exist in the word list.
     * Note:
     * <p>
     * Return 0 if there is no such transformation sequence.
     * All words have the same length.
     * All words contain only lowercase alphabetic characters.
     * You may assume no duplicates in the word list.
     * You may assume beginWord and endWord are non-empty and are not the same.
     * <p>
     * https://leetcode.com/problems/word-ladder/
     */
    public int ladderLengthV2(String beginWord, String endWord, List<String> wordList) {
        Set<String> wordsSet = new HashSet<>(wordList);
        if (!wordsSet.contains(endWord)) {
            return 0;
        }
        Queue<String> queue = new LinkedList<>();
        queue.offer(beginWord);

        int level = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                String currentWord = queue.poll();
                char[] wordChars = currentWord.toCharArray();
                for (int j = 0; j < wordChars.length; j++) {
                    char originalChar = wordChars[j];
                    for (char nextChar = 'a'; nextChar < 'z'; nextChar++) {
                        if (nextChar == wordChars[j]) {
                            continue;
                        }
                        wordChars[j] = nextChar;
                        String nextWord = String.valueOf(wordChars);
                        if (nextWord.equals(endWord)) {
                            return level + 1;
                        }
                        if (wordsSet.contains(nextWord)) {
                            queue.offer(nextWord);
                            wordsSet.remove(nextWord);
                        }
                    }
                    wordChars[j] = originalChar;
                }
            }
            level++;
        }
        return 0;
    }

    /**
     * Optimization: We can definitely reduce the space complexity of this algorithm by storing
     * the indices corresponding to each word instead of storing the word itself.
     */
    public int ladderLengthV1(String beginWord, String endWord, List<String> wordList) {

        // Since all words are of same length.
        int wordLength = beginWord.length();

        // Dictionary to hold combination of words that can be formed,
        // from any given word. By changing one letter at a time.
        Map<String, List<String>> allComboDict = allComboDict(wordLength, wordList);

        Queue<Pair<String, Integer>> queue = new LinkedList<>();
        queue.add(new Pair<>(beginWord, 1));

        Map<String, Boolean> visited = new HashMap<>();
        visited.put(beginWord, true);

        while (!queue.isEmpty()) {
            Pair<String, Integer> node = queue.remove();
            String word = node.fst;
            int level = node.snd;
            for (int i = 0; i < wordLength; i++) {
                // Intermediate words for current word
                String newWord = getGenericWord(i, word, wordLength);

                // Next states are all the words which share the same intermediate state.
                List<String> adjacentWords = allComboDict.getOrDefault(newWord, new ArrayList<>());
                for (String adjacentWord : adjacentWords) {
                    if (adjacentWord.equals(endWord)) {
                        return level + 1;
                    }
                    if (!visited.containsKey(adjacentWord)) {
                        visited.put(adjacentWord, true);
                        queue.add(new Pair<>(adjacentWord, level + 1));
                    }
                }
            }
        }
        return 0;
    }

    //Adjacency List
    private Map<String, List<String>> allComboDict(int wordLength, List<String> wordList) {
        Map<String, List<String>> allComboDict = new HashMap<>();

        wordList.forEach(
                word -> {
                    for (int i = 0; i < wordLength; i++) {
                        // Key is the generic word
                        // Value is a list of words which have the same intermediate generic word.
                        String newWord = getGenericWord(i, word, wordLength);
                        List<String> transformations = allComboDict.getOrDefault(newWord, new ArrayList<>());
                        transformations.add(word);
                        allComboDict.put(newWord, transformations);
                    }
                });
        return allComboDict;
    }

    private String getGenericWord(int index, String word, int wordLength) {
        return word.substring(0, index) + '*' + word.substring(index + 1, wordLength);
    }

    //Bidirectional BFS
    public int ladderLength(String beginWord, String endWord, List<String> wordList) {

        if (!wordList.contains(endWord)) {
            return 0;
        }

        // Since all words are of same length.
        int wordLength = beginWord.length();

        // Dictionary to hold combination of words that can be formed,
        // from any given word. By changing one letter at a time.
        Map<String, List<String>> allComboDict = allComboDict(wordLength, wordList);

        // Queues for bidirectional BFS
        // BFS starting from beginWord
        Queue<Pair<String, Integer>> Q_begin = new LinkedList<>();
        // BFS starting from endWord
        Queue<Pair<String, Integer>> Q_end = new LinkedList<>();
        Q_begin.add(new Pair<>(beginWord, 1));
        Q_end.add(new Pair<>(endWord, 1));

        // Visited to make sure we don't repeat processing same word.
        Map<String, Integer> visitedBegin = new HashMap<>();
        Map<String, Integer> visitedEnd = new HashMap<>();
        visitedBegin.put(beginWord, 1);
        visitedEnd.put(endWord, 1);

        while (!Q_begin.isEmpty() && !Q_end.isEmpty()) {

            // One hop from begin word
            int ans = visitWordNode(wordLength, Q_begin, visitedBegin, visitedEnd, allComboDict);
            if (ans > -1) {
                return ans;
            }

            // One hop from end word
            ans = visitWordNode(wordLength, Q_end, visitedEnd, visitedBegin, allComboDict);
            if (ans > -1) {
                return ans;
            }
        }

        return 0;
    }

    private int visitWordNode(int wordLength,
                              Queue<Pair<String, Integer>> Q,
                              Map<String, Integer> visited,
                              Map<String, Integer> othersVisited,
                              Map<String, List<String>> allComboDict) {

        Pair<String, Integer> node = Q.remove();
        String word = node.fst;
        int level = node.snd;

        for (int i = 0; i < wordLength; i++) {

            // Intermediate words for current word
            String newWord = getGenericWord(i, word, wordLength);

            // Next states are all the words which share the same intermediate state.
            for (String adjacentWord : allComboDict.getOrDefault(newWord, new ArrayList<>())) {
                if (othersVisited.containsKey(adjacentWord)) {
                    return level + othersVisited.get(adjacentWord);
                }

                if (!visited.containsKey(adjacentWord)) {

                    // Save the level as the value of the dictionary, to save number of hops.
                    visited.put(adjacentWord, level + 1);
                    Q.add(new Pair<>(adjacentWord, level + 1));
                }
            }
        }
        return -1;
    }
}
