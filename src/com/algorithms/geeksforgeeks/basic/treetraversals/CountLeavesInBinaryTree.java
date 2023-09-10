package com.algorithms.geeksforgeeks.basic.treetraversals;

public class CountLeavesInBinaryTree {
    /**
     * Given a Binary Tree of size N , You have to count leaves in it. For example, there are two leaves in following tree
     * <p>
     * https://practice.geeksforgeeks.org/problems/count-leaves-in-binary-tree/1
     */
    int countLeaves(Node node) {
        if (node == null) {
            return 0;
        } else if (node.left == null && node.right == null) {
            return 1;
        }
        return countLeaves(node.left) + countLeaves(node.right);
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
