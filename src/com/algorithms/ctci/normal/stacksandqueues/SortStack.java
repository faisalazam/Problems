package com.algorithms.ctci.normal.stacksandqueues;

import java.util.Stack;

public class SortStack {
    /**
     * Sort Stack; Write a program to sort a stack such that the smallest items are on the top.
     * You can use an additional temporary stack, but you may not copy the elements into any other
     * data structure (such as an array). The stack supports the following operations: push, pop, peek, and is Empty.
     * <p>
     * This algorithm is 0( N^2) time and 0(N) space.
     */
    public static Stack<Integer> sort(Stack<Integer> originalStack) {
        Stack<Integer> sortedStack = new Stack<>();
        while (!originalStack.isEmpty()) {
            int tmp = originalStack.pop();
            while (!sortedStack.isEmpty() && sortedStack.peek() > tmp) {
                originalStack.push(sortedStack.pop());
            }
            sortedStack.push(tmp);
        }
        return sortedStack;
    }

    private static int c = 0;

    /**
     * If we were allowed to use unlimited stacks, we could implement a modified quickSort or mergeSort.
     * With the mergeSort solution, we would create two extra stacks and divide the stack into two parts.
     * We would recursively sort each stack, and then merge them back together in sorted order into the original stack.
     * Note that this would require the creation of two additional stacks per level of recursion.
     */
    private static Stack<Integer> mergeSort(Stack<Integer> inStack) {
        if (inStack.size() <= 1) {
            return inStack;
        }

        Stack<Integer> left = new Stack<>();
        Stack<Integer> right = new Stack<>();
        int count = 0;
        while (!inStack.isEmpty()) {
            count++;
            c++;
            if (count % 2 == 0) {
                left.push(inStack.pop());
            } else {
                right.push(inStack.pop());
            }
        }

        left = mergeSort(left);
        right = mergeSort(right);

        while (left.size() > 0 || right.size() > 0) {
            if (left.isEmpty()) {
                inStack.push(right.pop());
            } else if (right.isEmpty()) {
                inStack.push(left.pop());
            } else if (right.peek().compareTo(left.peek()) <= 0) {
                inStack.push(left.pop());
            } else {
                inStack.push(right.pop());
            }
        }

        Stack<Integer> reverseStack = new Stack<>();
        while (inStack.size() > 0) {
            c++;
            reverseStack.push(inStack.pop());
        }

        return reverseStack;
    }
}
