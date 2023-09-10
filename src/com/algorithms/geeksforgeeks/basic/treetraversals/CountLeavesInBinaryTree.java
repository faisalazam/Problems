package com.algorithms.geeksforgeeks.basic.treetraversals;

import java.util.LinkedList;
import java.util.Queue;

public class CountLeavesInBinaryTree {
    /**
     * Given a Binary Tree of size N , You have to count leaves in it. For example, there are two leaves in following tree
     * <p>
     * https://practice.geeksforgeeks.org/problems/count-leaves-in-binary-tree/1
     */
    int countLeavesRecursive(Node node) {
        if (node == null) {
            return 0;
        } else if (node.left == null && node.right == null) {
            return 1;
        }
        return countLeavesRecursive(node.left) + countLeavesRecursive(node.right);
    }

    /**
     * The idea is to use level order traversal. During traversal, if we find a node whose left and right children
     * are NULL, we increment count.
     * <p>
     * Time Complexity: O(n)
     */
    int countLeavesIterative(Node node) {
        if (node == null) {
            return 0;
        }
        final Queue<Node> queue = new LinkedList<>();
        queue.offer(node);

        int count = 0;
        while (!queue.isEmpty()) {
            final Node current = queue.poll();
            count += processNode(queue, current);
        }
        return count;
    }

    private int processNode(final Queue<Node> queue, final Node current) {
        final Node left = current.left;
        final Node right = current.right;
        if (left == null && right == null) {
            return 1;
        }
        addNodeToQueue(queue, left);
        addNodeToQueue(queue, right);
        return 0;
    }

    private void addNodeToQueue(final Queue<Node> queue, final Node node) {
        if (node != null) {
            queue.offer(node);
        }
    }

    private static class Node {
        int data;
        Node left;
        Node right;

        Node(int data) {
            this.data = data;
            this.left = this.right = null;
        }
    }
}
