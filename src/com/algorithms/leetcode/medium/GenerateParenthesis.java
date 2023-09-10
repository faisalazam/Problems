package com.algorithms.leetcode.medium;

import java.util.ArrayList;
import java.util.List;

public class GenerateParenthesis {
    /**
     * Given n pairs of parentheses, write a function to generate all combinations of well-formed parentheses.
     * <p>
     * https://leetcode.com/problems/generate-parentheses/
     */
    public List<String> generateParenthesis(int n) {
        List<String> results = new ArrayList<>();
        backtrack(results, "", 0, 0, n);
        return results;
    }

    private void backtrack(List<String> results, String currentString, int open, int close, int max) {
        if (currentString.length() == max * 2) {
            results.add(currentString);
            return;
        }
        if (open < max) {
            backtrack(results, currentString + "(", open + 1, close, max);
        }
        if (close < open) { // we're doing < open instead of < max, because we can't have closing parenthesis before opening
            backtrack(results, currentString + ")", open, close + 1, max);
        }
    }

    /**
     * This one is running faster than the backtrack one
     */
    private static void addParen(List<String> list, int leftRem, int rightRem, char[] str, int count) {
        if (leftRem < 0 || rightRem < leftRem) { // invalid state
            return;
        }

        if (leftRem == 0 && rightRem == 0) {
            String s = String.copyValueOf(str);
            list.add(s);
            return;
        }
        if (leftRem > 0) {
            str[count] = '(';
            addParen(list, leftRem - 1, rightRem, str, count + 1);
        }
        if (rightRem > leftRem) {
            str[count] = ')';
            addParen(list, leftRem, rightRem - 1, str, count + 1);
        }
    }

    private static List<String> generateParens(int count) {
        char[] str = new char[count * 2];
        List<String> list = new ArrayList<>();
        addParen(list, count, count, str, 0);
        return list;
    }

    public static void main(String[] args) {
        List<String> list = generateParens(3);
        for (String s : list) {
            System.out.println(s);
        }
        System.out.println(list.size());
//        int size = 15;
//        GenerateParenthesis obj = new GenerateParenthesis();
//        long t1 = System.currentTimeMillis();
//        List<String> strings = generateParens(size);
//        long t2 = System.currentTimeMillis();
//        System.out.println(t2 - t1 + " => " + strings.size());
//        t1 = System.currentTimeMillis();
//        strings = obj.generateParenthesis(size);
//        t2 = System.currentTimeMillis();
//        System.out.println(t2 - t1 + " => " + strings.size());
    }
}
