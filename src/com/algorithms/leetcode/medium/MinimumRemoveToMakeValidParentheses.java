package com.algorithms.leetcode.medium;

public class MinimumRemoveToMakeValidParentheses {
    /**
     * Given a string s of '(' , ')' and lowercase English characters.
     * <p>
     * Your task is to remove the minimum number of parentheses ( '(' or ')', in any positions )
     * so that the resulting parentheses string is valid and return any valid string.
     * <p>
     * Formally, a parentheses string is valid if and only if:
     * <p>
     * It is the empty string, contains only lowercase characters, or
     * It can be written as AB (A concatenated with B), where A and B are valid strings, or
     * It can be written as (A), where A is a valid string.
     * <p>
     * https://leetcode.com/problems/minimum-remove-to-make-valid-parentheses/
     */
    public String minRemoveToMakeValid(String s) {
        int opening = 0;
        StringBuilder builder = new StringBuilder();
        for (char c : s.toCharArray()) { // this loop will balance the closing one
            if (c == '(') {
                opening++;
            } else if (c == ')') {
                if (opening == 0) {
                    continue;
                }
                opening--;
            }
            builder.append(c);
        }
        StringBuilder result = new StringBuilder();
        for (int i = builder.length() - 1; i >= 0; i--) {
            if (builder.charAt(i) == '(' && opening-- > 0) {
                continue;
            }
            result.append(builder.charAt(i));
        }
        return result.reverse().toString();
    }
}
