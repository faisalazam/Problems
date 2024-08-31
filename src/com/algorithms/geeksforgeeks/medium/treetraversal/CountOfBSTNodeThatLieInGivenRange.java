package com.algorithms.geeksforgeeks.medium.treetraversal;

public class CountOfBSTNodeThatLieInGivenRange {
    /**
     * Given a Binary Search Tree (BST) and a range l-h(inclusive), count the number of nodes in the BST that lie in the given range.
     * <p>
     * The values smaller than root go to the left side
     * The values greater and equal to the root go to the right side
     * <p>
     * https://practice.geeksforgeeks.org/problems/count-bst-nodes-that-lie-in-a-given-range/1
     */
    private static int getCountOfNode(Node root, int l, int h) {
        if (root == null) {
            return 0;
        } else if (root.data == h) {
            return 1 + getCountOfNode(root.left, l, h);
        } else if (root.data == l) {
            return 1 + getCountOfNode(root.right, l, h);
        } else if (root.data > l && root.data < h) {
            return 1 + getCountOfNode(root.left, l, h) + getCountOfNode(root.right, l, h);
        } else if (root.data < l) {
            return getCountOfNode(root.right, l, h);
        }
        return getCountOfNode(root.left, l, h);
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
