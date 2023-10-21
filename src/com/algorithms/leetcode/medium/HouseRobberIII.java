package com.algorithms.leetcode.medium;

/**
 * The thief has found himself a new place for his thievery again. There is only one entrance to this area, called root.
 * <p>
 * Besides the root, each house has one and only one parent house. After a tour, the smart thief realized that all
 * houses in this place form a binary tree. It will automatically contact the police if two directly-linked houses were
 * broken into on the same night.
 * <p>
 * Given the root of the binary tree, return the maximum amount of money the thief can rob without alerting the police.
 * <p>
 * https://leetcode.com/problems/house-robber-iii/
 * <p>
 * Explanation:
 * https://www.youtube.com/watch?v=nHR8ytpzz7c
 * https://leetcode.com/problems/house-robber-iii/solutions/79330/step-by-step-tackling-of-the-problem/
 */
public class HouseRobberIII {
    public int rob(final TreeNode root) {
        final int[] robbedAmount = dfs(root);
        return Integer.max(robbedAmount[0], robbedAmount[1]);
    }

    /**
     * @return int[] array, where value at index 0 is the robbed amount withRoot,
     * whereas value at index 1 is the robbed amount withoutRoot
     */
    private int[] dfs(final TreeNode root) {
        if (root == null) {
            return new int[]{0, 0};
        }
        final int[] leftPair = dfs(root.left);
        final int[] rightPair = dfs(root.right);

        final int robbedAmountWithRoot = root.val + leftPair[1] + rightPair[1];
        final int robbedAmountWithoutRoot = Integer.max(leftPair[0], leftPair[1]) + Integer.max(rightPair[0], rightPair[1]);
        return new int[]{robbedAmountWithRoot, robbedAmountWithoutRoot};
    }

    private static class TreeNode {
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
