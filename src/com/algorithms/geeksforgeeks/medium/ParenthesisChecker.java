package com.algorithms.geeksforgeeks.medium;

import java.util.ArrayDeque;
import java.util.Deque;

public class ParenthesisChecker {
    /**
     * Given an expression string exp. Examine whether the pairs and the orders of “{“,”}”,”(“,”)”,”[“,”]” are correct in exp.
     * For example, the program should print 'balanced' for exp = “[()]{}{[()()]()}” and 'not balanced' for exp = “[(])”
     * <p>
     * https://practice.geeksforgeeks.org/problems/parenthesis-checker/0
     * <p>
     * https://leetcode.com/problems/valid-parentheses/description/
     * <p>
     * Time Complexity: O(|x|)
     * Auxiliary Space: O(|x|)
     */
    private static boolean ispar(String x) {
        if (x == null || x.isBlank() || x.length() % 2 != 0) {
            return false;
        }

        // This 'ArrayDeque' class is likely to be faster than Stack when used as a stack, and faster than LinkedList
        // when used as a queue. It is also known as Array Double Ended Queue or Array Deck. This is a special kind of
        // array that grows and allows users to add or remove an element from both sides of the queue. The ArrayDeque
        // class is not thread-safe, but you can use the Collections.synchronizedDeque method to create a thread-safe
        // version of the ArrayDeque class.
        final Deque<Character> stack = new ArrayDeque<>();
        for (int i = 0; i < x.length(); i++) {
            final char character = x.charAt(i);
            if (isOpeningBrace(character)) {
                stack.push(character);
            } else if (isCloseBrace(character)) {
                if (stack.isEmpty() || getClosing(stack.pop()) != character) {
                    return false;
                }
            }
        }
        return stack.isEmpty();
    }

    private static boolean isOpeningBrace(final char character) {
        return character == '[' || character == '{' || character == '(';
    }

    private static boolean isCloseBrace(final char character) {
        return character == ']' || character == '}' || character == ')';
    }

    private static Character getClosing(final char opening) {
        switch (opening) {
            case '[':
                return ']';
            case '{':
                return '}';
            case '(':
                return ')';
            default:
                return '!'; // any random character
        }
    }
}
