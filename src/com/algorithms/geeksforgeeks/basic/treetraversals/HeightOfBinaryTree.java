package com.algorithms.geeksforgeeks.basic.treetraversals;

public class HeightOfBinaryTree {
    /**
     * Given a binary tree, find its height.
     * <p>
     * Your Task:
     * You don't need to read input or print anything. Your task is to complete the function height()
     * that takes root Node of the Tree as input and returns the Height of the Tree. If the Tree is empty, return 0.
     * <p>
     * Expected Time Complexity: O(N).
     * Expected Auxiliary Space: O(Height of the Tree) => O(H) => where H, in best case, will be log(N); and N in worst case.
     * <p>
     * https://practice.geeksforgeeks.org/problems/height-of-binary-tree/1
     */
    int height(Node node) {
        if (node == null) {
            return 0;
        }
        return 1 + Math.max(height(node.left), height(node.right));
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
