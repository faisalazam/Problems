package com.algorithms.leetcode.easy;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class MaximumDepthOfNAryTree {
    /**
     * Given a n-ary tree, find its maximum depth.
     * <p>
     * The maximum depth is the number of nodes along the longest path from the root node down to the farthest leaf node.
     * <p>
     * Nary-Tree input serialization is represented in their level order traversal,
     * each group of children is separated by the null value
     * <p>
     * https://leetcode.com/problems/maximum-depth-of-n-ary-tree/
     */
    public int maxDepth(Node root) {
        if (root == null) {
            return 0;
        }
        int depth = 0;
        Queue<Node> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                Node currentNode = queue.poll();
                for (Node child : currentNode.children) {
                    queue.offer(child);
                }
            }
            depth++;
        }
        return depth;
    }

    int maxDepth = 0;

    public int maxDepthUsingDFS(Node root) {
        if (root == null) {
            return 0;
        }
        maxDepth(root, 1);
        return maxDepth;
    }

    private void maxDepth(Node root, int depth) {
        if (root == null) {
            return;
        }
        maxDepth = Math.max(maxDepth, depth);
        for (Node child : root.children) {
            maxDepth(child, depth + 1);
        }
    }

    private class Node {
        private int val;
        private List<Node> children;

        public Node() {
        }

        public Node(int val) {
            this.val = val;
        }

        public Node(int val, List<Node> children) {
            this.val = val;
            this.children = children;
        }
    }
}
