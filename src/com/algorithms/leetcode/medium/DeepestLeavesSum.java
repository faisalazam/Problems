package com.algorithms.leetcode.medium;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

/**
 * Given a binary tree, return the sum of values of its deepest leaves.
 * <p>
 * https://leetcode.com/problems/deepest-leaves-sum/
 */
public class DeepestLeavesSum {
    /*
     * Brute force
     * <p>
     * Find (DFS) the deepest level first (which is basically height of the tree) and then another DFS to calculate the
     * sum of leaves at that level
     */

    /**
     * BFS
     * <p>
     * Time Complexity: O(N)
     * Auxiliary Space: O(Height of the Tree) => O(H) => where H, in best case, will be log(N); and N in worst case,
     * used by the Queue.
     */
    public int deepestLeavesSumIterative(TreeNode root) {
        if (root == null) {
            return 0;
        }
        final Queue<TreeNode> queue = new ArrayDeque<>();
        queue.offer(root);

        int levelSum = 0;
        while (!queue.isEmpty()) {
            levelSum = 0;
            int size = queue.size();
            for (int i = 0; i < size; i++) { // traverses one level of tree
                final TreeNode currentNode = queue.poll();
                // Not explicitly checking if the node is leaf or not, because the nodes at the max depth will implicitly
                // be leaves
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

    /**
     * DFS
     * <p>
     * Time Complexity: O(N)
     * Auxiliary Space: O(1), if we don't consider the recursive function call stack, otherwise
     * O(Height of the Tree) => O(H) => where H, in best case, will be log(N); and N in worst case
     */
    public int deepestLeavesSumImprovedRecursive(TreeNode root) {
        // Using single element arrays as the values will get lost during recursion if using just int variables.
        final int[] sum = new int[1]; // will store current level sum
        final int[] maxDepth = new int[1];
        calculateSumAtLevelImprovedDFS(root, maxDepth, 0, sum);
        return sum[0];
    }

    private void calculateSumAtLevelImprovedDFS(TreeNode root, int[] maxDepth, int currDepth, int[] sum) {
        if (root == null) {
            return;
        }
        // Not explicitly checking if the node is leaf or not, because the nodes at the max depth will implicitly be leaves
        if (maxDepth[0] < currDepth) { // That signifies the start of the level
            sum[0] = root.val;
            maxDepth[0] = currDepth;
        } else if (maxDepth[0] == currDepth) { // same level/depth - keep adding
            sum[0] += root.val;
        }
        calculateSumAtLevelImprovedDFS(root.left, maxDepth, currDepth + 1, sum);
        calculateSumAtLevelImprovedDFS(root.right, maxDepth, currDepth + 1, sum);
    }

    /**
     * DFS
     * <p>
     * Time Complexity: O(N)
     * Auxiliary Space: O(Height of the Tree) => O(H) => where H, in best case, will be log(N); and N in worst case;
     * whereas sums will be be taking the same extra space as well, as the number of levels will be equal to the height
     * of the tree.
     */
    public int deepestLeavesSumRecursive(TreeNode root) {
        final List<Integer> sums = new ArrayList<>();
        calculateSumAtLevelDFS(root, 0, sums);
        return sums.get(sums.size() - 1);
    }

    public void calculateSumAtLevelDFS(TreeNode node, int level, final List<Integer> sums) {
        if (level == sums.size()) { // That signifies the start of the level
            sums.add(node.val);
        } else {
            sums.set(level, sums.get(level) + node.val); // replace existing sum at index level
        }
        // Not explicitly checking if the node is leaf or not, because the nodes at the max depth will implicitly be leaves
        if (node.left != null) {
            calculateSumAtLevelDFS(node.left, level + 1, sums);
        }
        if (node.right != null) {
            calculateSumAtLevelDFS(node.right, level + 1, sums);
        }
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
