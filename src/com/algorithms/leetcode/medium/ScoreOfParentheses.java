package com.algorithms.leetcode.medium;

import java.util.Stack;

public class ScoreOfParentheses {
    /**
     * Given a balanced parentheses string S, compute the score of the string based on the following rule:
     * <p>
     * () has score 1
     * AB has score A + B, where A and B are balanced parentheses strings.
     * (A) has score 2 * A, where A is a balanced parentheses string.
     * <p>
     * https://leetcode.com/problems/score-of-parentheses/
     * <p>
     * Time Complexity: O(N)
     * Space Complexity: O(1)
     */
    public int scoreOfParenthesesV1(String s) {
        int score = 0, depth = 0;
        for (int i = 0; i < s.length(); i++) {
            depth += s.charAt(i) == '(' ? 1 : -1;

            if (s.charAt(i) == ')' && s.charAt(i - 1) == '(') {
                score += Math.pow(2, depth);
            }
        }
        return score;
    }

    public int scoreOfParenthesesV2(String S) {
        int score = 1;
        int result = 0;
        int counted = 0;
        for (char c : S.toCharArray()) {
            if (c == '(') {
                score *= 2;
                counted = 0;
            } else {
                score /= 2;
                if (counted == 0) {
                    result += score;
                    counted = 1;
                }
            }
        }
        return result;
    }

    /**
     * Time Complexity: O(N)
     * Space Complexity: O(N)
     */
    public int scoreOfParenthesesV0(String S) {
        int score = 0;
        final Stack<Integer> stack = new Stack<>();
        for (char c : S.toCharArray()) {
            if (c == '(') {
                stack.push(score);
                score = 0;
            } else {
                score = stack.pop() + Math.max(score * 2, 1);
            }
        }
        return score;
    }
}
