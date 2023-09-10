package com.algorithms.geeksforgeeks.basic.treetraversals;

import java.util.Stack;

public class InorderTraversal {
    /**
     * Given a Binary Tree, find the In-Order Traversal of it.
     * <p>
     * Your Task:
     * You don't need to read input or print anything. Your task is to complete the function inOrder()
     * that takes root node of the tree as input and returns a list containing the In-Order Traversal of the given Binary Tree.
     * <p>
     * Expected Time Complexity: O(N).
     * Expected Auxiliary Space: O(N).
     * <p>
     * https://practice.geeksforgeeks.org/problems/inorder-traversal/1
     */
    void inOrder(Node root) {
        inOrderIterative(root);
        // inOrderRecursive(root);
    }

    private void inOrderIterative(Node root) {
        Stack<Node> stack = new Stack<>();

        pushLeft(root, stack);
        while (!stack.isEmpty()) {
            Node temp = stack.pop();
            System.out.print(temp.data + " ");
            pushLeft(temp.right, stack);
        }
    }

    private void pushLeft(Node root, Stack<Node> stack) {
        Node temp = root;
        while (temp != null) {
            stack.push(temp);
            temp = temp.left;
        }
    }

    private void inOrderRecursive(Node root) {
        if (root == null) {
            return;
        }
        inOrderRecursive(root.left);
        System.out.print(root.data + " ");
        inOrderRecursive(root.right);
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
