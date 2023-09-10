package com.algorithms.geeksforgeeks.basic.treetraversals;

import java.util.Stack;

public class PostorderTraversal {
    /**
     * Given a binary tree, print postorder traversal of it. Postorder traversal of below tree is 5 10 39 1
     *
     *         1
     *      /     \
     *    10     39
     *   /
     * 5
     *
     * https://practice.geeksforgeeks.org/problems/postorder-traversal/1
     */
    void postOrder(Node root) {
        postOrderRecursive(root);
        // postOrderIterative(root);
    }

    void postOrderIterative(Node root) {
        Stack<Node> stack = new Stack<>();

    }

    void postOrderRecursive(Node root) {
        if (root == null) {
            return;
        }
        postOrderRecursive(root.left);
        postOrderRecursive(root.right);
        System.out.print(root.data + " ");
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
