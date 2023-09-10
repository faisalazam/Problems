package com.algorithms.templates;

import java.util.LinkedList;
import java.util.Queue;

public class LevelOrderTraversalOrBFSTemplate {
    public void template(TreeNode root) {
        if (root == null) {
            return;
        }
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) { //traverses one level of tree
                TreeNode currentNode = queue.poll();
                //process currentNode
                addNodeToQueue(queue, currentNode.left);
                addNodeToQueue(queue, currentNode.right);
            }
        }
    }

    private void addNodeToQueue(Queue<TreeNode> queue, TreeNode node) {
        if (node != null) {
            queue.offer(node);
        }
    }

    private class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
    }
}
