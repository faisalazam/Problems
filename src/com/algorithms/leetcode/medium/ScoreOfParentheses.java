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
     */
    public int scoreOfParentheses(String S) {
        int score = 0;
        Stack<Integer> stack = new Stack<>();
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
