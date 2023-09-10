package com.algorithms.leetcode.easy;

import java.util.ArrayList;
import java.util.List;

public class FindModeInBinarySearchTree {
    /**
     * Given a binary search tree (BST) with duplicates, find all the mode(s)
     * (the most frequently occurred element) in the given BST.
     * <p>
     * Assume a BST is defined as follows:
     * <p>
     * The left subtree of a node contains only nodes with keys less than or equal to the node's key.
     * The right subtree of a node contains only nodes with keys greater than or equal to the node's key.
     * Both the left and right subtrees must also be binary search trees.
     * <p>
     * https://leetcode.com/problems/find-mode-in-binary-search-tree/
     */
    public int[] findMode(TreeNode root) {
        List<Integer> modes = new ArrayList<>();
        traverse(root, modes);
        return listToArray(modes);
    }

    private int max = 0;
    private int count = 1;
    private Integer prev = null;

    private void traverse(TreeNode node, List<Integer> modes) {
        if (node == null) {
            return;
        }
        traverse(node.left, modes);
        process(node, modes);
        traverse(node.right, modes);
    }

    private void process(TreeNode node, List<Integer> modes) {
        if (prev != null) {
            if (prev == node.val) {
                count++;
            } else {
                count = 1;
            }
        }
        if (count > max) {
            max = count;
            modes.clear();
            modes.add(node.val);
        } else if (count == max) {
            modes.add(node.val);
        }
        prev = node.val;
    }

    private int[] listToArray(List<Integer> modes) {
        int[] result = new int[modes.size()];
        for (int i = 0; i < modes.size(); i++) {
            result[i] = modes.get(i);
        }
        return result;
    }

    private class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
    }
}
