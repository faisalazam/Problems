package com.algorithms.geeksforgeeks.easy.treetraversals;

import java.util.Stack;

public class LevelOrderTraversalInSpiralForm {
    /**
     * Complete the function to print spiral order traversal of a tree. For below tree, function should print 1, 2, 3, 4, 5, 6, 7.
     *
     *           1
     *        /     \
     *      2        3
     *    /    \   /   \
     *   7     6   5     4
     *
     * Your Task:
     * The task is to complete the function printSpiral() which prints the elements in spiral form
     * of level order traversal. The newline is automatically appended by the driver code.
     * Expected Time Complexity: O(N).
     * Expected Auxiliary Space: O(N).
     * <p>
     * https://practice.geeksforgeeks.org/problems/level-order-traversal-in-spiral-form/1
     */
    void printSpiral(Node node) {
        if (node == null) {
            return;
        }
        StringBuilder output = new StringBuilder();
        Stack<Node> stack1 = new Stack<>();
        Stack<Node> stack2 = new Stack<>();
        stack1.push(node);
        while (!stack1.isEmpty()) {
            while (!stack1.isEmpty()) {
                Node curr = stack1.pop();
                output.append(curr.data).append(" ");
                if (curr.right != null) {
                    stack2.push(curr.right);
                }
                if (curr.left != null) {
                    stack2.push(curr.left);
                }
            }
            while (!stack2.isEmpty()) {
                Node curr = stack2.pop();
                output.append(curr.data).append(" ");
                if (curr.left != null) {
                    stack1.push(curr.left);
                }
                if (curr.right != null) {
                    stack1.push(curr.right);
                }
            }
        }
        System.out.println(output.toString());
    }

    private class Node {
        int data;
        Node left;
        Node right;

        Node(int data) {
            this.data = data;
            this.left = this.right = null;
        }
    }
}
