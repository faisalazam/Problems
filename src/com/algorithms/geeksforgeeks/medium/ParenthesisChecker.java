package com.algorithms.geeksforgeeks.medium;

import java.util.Stack;

public class ParenthesisChecker {
    /**
     * Given an expression string exp. Examine whether the pairs and the orders of “{“,”}”,”(“,”)”,”[“,”]” are correct in exp.
     * For example, the program should print 'balanced' for exp = “[()]{}{[()()]()}” and 'not balanced' for exp = “[(])”
     * <p>
     * https://practice.geeksforgeeks.org/problems/parenthesis-checker/0
     */
    private static String isBalanced(String parenthesis) {
        String notBalanced = "not balanced";
        if (parenthesis == null || parenthesis.length() == 0 || parenthesis.length() % 2 != 0) {
            return notBalanced;
        }
        Stack<Character> stack = new Stack<>();
        for (int i = 0; i < parenthesis.length(); i++) {
            char character = parenthesis.charAt(i);
            if (character == '[' || character == '{' || character == '(') {
                stack.push(character);
            } else if (
                    !stack.isEmpty() && (
                            (stack.peek() == '[' && character == ']') ||
                                    (stack.peek() == '{' && character == '}') ||
                                    (stack.peek() == '(' && character == ')')
                    )
            ) {
                stack.pop();
            } else {
                return notBalanced;
            }
        }
        return stack.isEmpty() ? "balanced" : notBalanced;
    }
}
