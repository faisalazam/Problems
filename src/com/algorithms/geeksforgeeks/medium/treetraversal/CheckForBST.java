package com.algorithms.geeksforgeeks.medium.treetraversal;

public class CheckForBST {
    /**
     * Given a binary tree. Check whether it is a BST or not.
     * <p>
     * Your task is to complete the function isBST() which takes the root of the tree as a parameter
     * and returns true if the given binary tree is BST, else returns false.
     * <p>
     * Expected Time Complexity: O(N).
     * Expected Auxiliary Space: O(Height of the BST).
     * <p>
     * https://practice.geeksforgeeks.org/problems/check-for-bst/1
     */
    boolean isBST(Node root) {
        return isBST(root, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    private boolean isBST(Node root, int min, int max) {
        if (root == null) {
            return true;
        } else if (root.data < min || root.data > max) {
            return false;
        }
        return isBST(root.left, min, root.data - 1) && isBST(root.right, root.data + 1, max);
    }

    private static Integer last_printed = null;

    /**
     * Our first thought might be to do an in-order traversal, copy the elements to an array,
     * and then check to see if the array is sorted. This solution takes up a bit of extra memory, but it works—mostly.
     * The only problem is that it can't handle duplicate values in the tree properly.
     *
     * When we examine this solution, we find that the array is not actually necessary. We never use it other
     * than to compare an element to the previous element. So why not just track the last element we saw
     * and compare it as we go (and that's what is implemented below)?
     */
    private boolean checkBST(Node n) {
        if (n == null) {
            return true;
        }

        if (!checkBST(n.left)) {
            return false;
        }

        if (last_printed != null && n.data <= last_printed) {
            return false;
        }
        last_printed = n.data;

        return checkBST(n.right);
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
