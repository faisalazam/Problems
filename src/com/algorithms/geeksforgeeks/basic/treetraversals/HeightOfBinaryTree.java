package com.algorithms.geeksforgeeks.basic.treetraversals;

import com.algorithms.templates.LevelOrderTraversalOrBFSTemplate;

import java.util.ArrayDeque;
import java.util.LinkedList;
import java.util.Queue;

public class HeightOfBinaryTree {
    /**
     * Given a binary tree, find its height.
     * <p>
     * Your Task:
     * You don't need to read input or print anything. Your task is to complete the function height()
     * that takes root Node of the Tree as input and returns the Height of the Tree. If the Tree is empty, return 0.
     * <p>
     * Time Complexity: O(N).
     * Auxiliary Space: O(Height of the Tree) => O(H) => where H, in best case, will be log(N); and N in worst case.
     * <p>
     * https://practice.geeksforgeeks.org/problems/height-of-binary-tree/1
     */
    int height(Node node) {
        if (node == null) {
            return 0;
        }
        return 1 + Math.max(height(node.left), height(node.right));
    }

    /**
     * Time Complexity: O(N)
     * Auxiliary Space: O(N) or O(H)? I think, O(H)
     */
    int heightIterative(Node node) {
        int height = 0;
        if (node == null) {
            return height;
        }
        final Queue<Node> queue = new LinkedList<>();
        queue.offer(node);

        while (!queue.isEmpty()) {
            final int size = queue.size();
            for (int i = 0; i < size; i++) { //traverses one level of tree
                final Node currentNode = queue.poll();
                processNode(queue, currentNode);
            }
            height++;
        }
        return height;
    }

    /**
     * Time Complexity: O(N)
     * Auxiliary Space: O(N) or O(H)? I think, O(H)
     */
    int heightIterativeV0(Node node) {
        if (node == null) {
            return 0;
        }
        int height = 1;
        final Queue<Node> queue = new LinkedList<>(); // Using LinkedList here as ArrayDeque doesn't accept null elements
        queue.offer(node);
        queue.offer(null); // marks the end of the level
        while (queue.size() != 1) {
            final Node curr = queue.poll();
            if (curr == null) {  // check if end of the level
                height++;
                queue.offer(null); // marks the end of the level
                continue;
            }
            processNode(queue, curr);
        }
        return height;
    }

    private void processNode(final Queue<Node> queue, final Node current) {
        addNodeToQueue(queue, current.left);
        addNodeToQueue(queue, current.right);
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
