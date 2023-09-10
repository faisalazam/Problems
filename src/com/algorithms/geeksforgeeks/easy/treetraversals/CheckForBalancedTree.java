package com.algorithms.geeksforgeeks.easy.treetraversals;

public class CheckForBalancedTree {
    /**
     * Given a binary tree, find if it is height balanced or not.
     * A tree is height balanced if difference between heights of left and right subtrees is not more than
     * one for all nodes of tree.
     *
     * A height balanced tree
     *          1
     *       /     \
     *     10      39
     *    /
     *  5
     *
     *  An unbalanced tree
     *          1
     *       /
     *     10
     *    /
     *  5
     * <p>
     * Expected time complexity: O(N)
     * Expected auxiliary space: O(h) , where h = height of tree
     * <p>
     * https://practice.geeksforgeeks.org/problems/check-for-balanced-tree/1
     */
    private boolean isBalanced(Node root) {
        return checkHeight(root) != Integer.MIN_VALUE;
    }

    /**
     * If the subtree is balanced, then checkHeight will return the actual height of the subtree.
     * If the subtree is not balanced, then checkHeight will return an error code. We will immediately break
     * and return an error code from the current call. Integer.MIN_VALUE is used as error code here.
     */
    private int checkHeight(Node root) {
        if (root == null) {
            return -1;
        }
        int leftHeight = checkHeight(root.left);
        if (leftHeight == Integer.MIN_VALUE) {
            return Integer.MIN_VALUE;
        }
        int rightHeight = checkHeight(root.right);
        if (rightHeight == Integer.MIN_VALUE) {
            return Integer.MIN_VALUE;
        }
        if (Math.abs(leftHeight - rightHeight) > 1) {
            return Integer.MIN_VALUE;
        } else {
            return 1 + Math.max(leftHeight, rightHeight);
        }
    }

    private boolean balanced = true;

    /**
     * Although this works, it's not very efficient. On each node, we recurse through its entire subtree.
     * This means that getHeight is called repeatedly on the same nodes.The algorithm is 0(W log N) since each
     * node is "touched" once per node above it.
     */
    /* This function should return true if passed tree is balanced, else false. */
    private boolean isBalancedV1(Node root) {
        if (root == null) {
            return true;
        } else if (Math.abs(height(root.left) - height(root.right)) > 1) {
            balanced = false; // it'll avoid further recursion as soon as it finds that the tree isn't balanced
            return false;
        }
        if (balanced) {
            return isBalancedV1(root.left) && isBalancedV1(root.right);
        }
        return false;
    }

    private int height(Node root) {
        if (root == null) {
            return 0;
        }
        return 1 + Math.max(height(root.left), height(root.right));
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
