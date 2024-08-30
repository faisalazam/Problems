package com.algorithms.leetcode.easy;

import java.util.ArrayList;
import java.util.List;

public class BinaryTreePaths {
    private static final String ARROW = "->";

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
        StringBuilder sb = new StringBuilder();
        buildPaths(root, results, sb);
        return results;
    }

    private void buildPaths(TreeNode node, List<String> results, StringBuilder stringBuilder) {
        if (node == null) {
            return;
        }
        int sbLength = stringBuilder.length();
        stringBuilder.append(node.val);
        if (node.left == null && node.right == null) {
            results.add(stringBuilder.toString());
        } else {
            stringBuilder.append(ARROW);
            buildPaths(node.left, results, stringBuilder);
            buildPaths(node.right, results, stringBuilder);
        }
        // Need to set the length of the StringBuilder to the one it was at the time of the recursive call,
        // as otherwise it'll contain the intermediate node values
        stringBuilder.setLength(sbLength);
    }

    public List<String> binaryTreePathsV0(TreeNode root) {
        List<String> results = new ArrayList<>();
        if (root == null) {
            return results;
        }
        buildPathsV0("", root, results);
        return results;
    }

    private void buildPathsV0(String currentPath, TreeNode node, List<String> results) {
        if (node == null) {
            return;
        }
        if (!currentPath.isEmpty()) {
            currentPath += ARROW;
        }
        currentPath += node.val;
        if (node.left == null && node.right == null) {
            results.add(currentPath);
            return;
        }
        buildPathsV0(currentPath, node.left, results);
        buildPathsV0(currentPath, node.right, results);
    }

    private static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
    }
}
