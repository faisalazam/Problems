package com.algorithms.geeksforgeeks.basic.treetraversals;

import java.util.Stack;

public class PreorderTraversal {
    /**
     * Given a binary tree, print preorder traversal of it. The task is to complete the function preorder(),
     * which accept root of the tree as argument.
     * For example: preorder traversal of below tree is "1 10 5 39"
     *         1
     *      /    \
     *    10     39
     *   /
     *  5
     * <p>
     * https://practice.geeksforgeeks.org/problems/preorder-traversal/1
     */
    static void preorder(final Node root) {
        // preorderRecursive(root);
        preorderIterative(root);
    }

    private static void preorderIterative(final Node root) {
        Stack<Node> stack = new Stack<>();

        pushLeft(root, stack);
        while (!stack.isEmpty()) {
            Node temp = stack.pop();
            pushLeft(temp.right, stack);
        }
    }

    private static void pushLeft(final Node root, final Stack<Node> stack) {
        Node temp = root;
        while (temp != null) {
            System.out.print(temp.data + " ");
            stack.push(temp);
            temp = temp.left;
        }
    }

    private static void preorderRecursive(final Node root) {
        if (root == null) {
            return;
        }
        System.out.print(root.data + " ");
        preorderRecursive(root.left);
        preorderRecursive(root.right);
    }

    private static class Node {
        int data;
        Node left;
        Node right;

        Node(int data) {
            this.data = data;
            this.left = this.right = null;
        }
    }
}
