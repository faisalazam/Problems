package com.algorithms.leetcode.easy;

import java.util.ArrayList;
import java.util.List;

public class BinaryTreePaths {
    /**
     * Given a binary tree, return all root-to-leaf paths.
     * <p>
     * Note: A leaf is a node with no children.
     * <p>
     * https://leetcode.com/problems/binary-tree-paths/
     */
    public List<String> binaryTreePaths(TreeNode root) {
        List<String> results = new ArrayList<>();
        if (root == null) {
            return results;
        }
        String currentPath = Integer.toString(root.val);
        buildPaths(currentPath, root, results);
        return results;
    }

    // PreOrder?
    private void buildPaths(String currentPath, TreeNode node, List<String> results) {
        if (node.left == null || node.right == null) {
            results.add(currentPath);
            return;
        }
        dfs(currentPath, node.left, results); // No need to node.left null check as it is already checked
        if (node.right != null) {
            dfs(currentPath, node.right, results);
        }
    }

    private void dfs(String currentPath, TreeNode node, List<String> results) {
        currentPath += "->" + node.val;
        buildPaths(currentPath, node, results);
    }

    private class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
    }
}
