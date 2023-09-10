package com.algorithms.geeksforgeeks.basic.treetraversals;

public class SizeOfBinaryTree {
    /**
     * Given a binary tree of size N, you have to count number of nodes in it. For example, count of nodes in below tree is 4.
     *
     *         1
     *      /      \
     *    10      39
     *   /
     * 5
     *
     * https://practice.geeksforgeeks.org/problems/size-of-binary-tree/1
     */
    int size(Node root) {
        if (root == null) {
            return 0;
        }
        return 1 + size(root.left) + size(root.right);
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
