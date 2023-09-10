package com.algorithms.leetcode.medium;

public class ConvertSortedListToBinarySearchTree {
    /**
     * Given an array where elements are sorted in ascending order, convert it to a height balanced BST.
     * <p>
     * For this problem, a height-balanced binary tree is defined as a binary tree in which the depth of the
     * two subtrees of every node never differ by more than 1.
     * <p>
     * https://leetcode.com/problems/convert-sorted-array-to-binary-search-tree/
     */
    public TreeNode sortedArrayToBST(int[] nums) {
        if (nums.length == 0) {
            return null;
        }
        return constructBST(nums, 0, nums.length - 1);
    }

    private TreeNode constructBST(int[] nums, int left, int right) {
        if (left > right) {
            return null;
        }
        int mid = left + (right - left) / 2;
        TreeNode root = new TreeNode(nums[mid]);
        root.left = constructBST(nums, left, mid - 1);
        root.right = constructBST(nums, mid + 1, right);
        return root;
    }

    private class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

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
