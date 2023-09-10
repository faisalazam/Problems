package com.algorithms.templates;

import java.util.LinkedList;
import java.util.Queue;

public class LevelOrderTraversalOrBFSTemplate {
    public void template(final TreeNode root) {
        if (root == null) {
            return;
        }
        final Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            final int size = queue.size();
            for (int i = 0; i < size; i++) { //traverses one level of tree
                final TreeNode currentNode = queue.poll();
                processNode(queue, currentNode);
            }
        }
    }


    private void processNode(final Queue<TreeNode> queue, final TreeNode current) {
        final TreeNode left = current.left;
        final TreeNode right = current.right;
        // process the current node somewhere here
        addNodeToQueue(queue, left);
        addNodeToQueue(queue, right);
    }

    private void addNodeToQueue(final Queue<TreeNode> queue, final TreeNode node) {
        if (node != null) {
            queue.offer(node);
        }
    }

    private static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
    }
}
