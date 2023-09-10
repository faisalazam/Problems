package com.algorithms.leetcode.medium;

import java.util.LinkedList;
import java.util.Queue;

public class DeepestLeavesSum {
    /**
     * Given a binary tree, return the sum of values of its deepest leaves.
     * <p>
     * https://leetcode.com/problems/deepest-leaves-sum/
     */
    public int deepestLeavesSum(TreeNode root) {
        if (root == null) {
            return 0;
        }
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);

        int levelSum = 0;
        while (!queue.isEmpty()) {
            levelSum = 0;
            int size = queue.size();
            for (int i = 0; i < size; i++) { //traverses one level of tree
                TreeNode currentNode = queue.poll();
                levelSum += currentNode.val;
                if (currentNode.left != null) {
                    queue.offer(currentNode.left);
                }
                if (currentNode.right != null) {
                    queue.offer(currentNode.right);
                }
            }
        }
        return levelSum;
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
