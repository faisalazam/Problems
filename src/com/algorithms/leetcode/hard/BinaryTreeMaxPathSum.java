package com.algorithms.leetcode.hard;

public class BinaryTreeMaxPathSum {
    private int maxPathSum = Integer.MIN_VALUE;

    /*
     * Given a non-empty binary tree, find the maximum path sum.
     *
     * For this problem, a path is defined as any sequence of nodes from some starting node
     * to any node in the tree along the parent-child connections. The path must contain at least one node
     * and does not need to go through the root.
     *
     * https://leetcode.com/problems/binary-tree-maximum-path-sum/
     */
    public int maxPathSum(TreeNode root) {
        pathSum(root);
        return maxPathSum;
    }

    private int pathSum(TreeNode node) {
        if (node == null) {
            return 0;
        }
        int leftSum = Math.max(0, pathSum(node.left));
        int rightSum = Math.max(0, pathSum(node.right));
        maxPathSum = Math.max(maxPathSum, leftSum + rightSum + node.val);
        return Math.max(leftSum, rightSum) + node.val;
    }

    private class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode() {
        }

        TreeNode(int val) {
            this.val = val;
        }

        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }
}
