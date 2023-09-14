package com.algorithms.misc;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Write an autocomplete class that returns all dictionary words with a given prefix.
 * <p>
 * dict:   {"abc", "acd", "bcd", "def", "a", "aba"}
 * <p>
 * prefix: "a" -> "abc", "acd", "a", "aba"
 * prefix: "b" -> "bcd"
 */
public class AutoCompleteWithGivenPrefix {
    // Trie node class
    private static class Node {
        final String prefix;
        boolean isEndOfWord;
        final Map<Character, Node> children;

        private Node(String prefix) {
            this.prefix = prefix;
            this.children = new HashMap<>();
        }
    }

    // The trie
    private final Node trie;

    // Construct the trie from the dictionary
    public AutoCompleteWithGivenPrefix(String[] dict) {
        trie = new Node("");
        for (String s : dict) {
            insertWord(s);
        }
    }

    private void insertWord(String s) {
        Node curr = trie;
        for (int i = 0; i < s.length(); i++) {
            if (!curr.children.containsKey(s.charAt(i))) {
                curr.children.put(s.charAt(i), new Node(s.substring(0, i + 1)));
            }
            curr = curr.children.get(s.charAt(i));
            if (i == s.length() - 1) {
                curr.isEndOfWord = true;
            }
        }
    }

    // Find all words in trie that start with prefix
    public List<String> getWordsForPrefix(String pre) {
        List<String> results = new LinkedList<>();

        // Iterate to the end of the prefix
        Node curr = trie;
        for (char c : pre.toCharArray()) {
            if (curr.children.containsKey(c)) {
                curr = curr.children.get(c);
            } else {
                return results;
            }
        }

        // At the end of the prefix, find all child words
        findAllChildWords(curr, results);
        return results;
    }

    // Recursively find every child word
    private void findAllChildWords(Node n, List<String> results) {
        if (n.isEndOfWord) {
            results.add(n.prefix);
        }
        for (Character c : n.children.keySet()) {
            findAllChildWords(n.children.get(c), results);
        }
    }
}
