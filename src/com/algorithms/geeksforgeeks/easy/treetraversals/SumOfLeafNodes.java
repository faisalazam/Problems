package com.algorithms.geeksforgeeks.easy.treetraversals;

public class SumOfLeafNodes {
    /**
     * Given a Binary Tree of size N. The task is to complete the function sumLeaf(), that should return the sum of
     * all the leaf nodes of the given binary tree.
     * <p>
     * https://practice.geeksforgeeks.org/problems/sum-of-leaf-nodes/1
     */
    private int sumOfLeafNodes(Node root) {
        if (root == null) {
            return 0;
        } else if (root.left == null && root.right == null) {
            return root.data;
        } else if (root.left == null) {
            return sumOfLeafNodes(root.right);
        } else if (root.right == null) {
            return sumOfLeafNodes(root.left);
        }
        return sumOfLeafNodes(root.left) + sumOfLeafNodes(root.right);
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
