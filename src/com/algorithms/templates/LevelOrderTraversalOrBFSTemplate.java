package com.algorithms.templates;

import java.util.ArrayDeque;
import java.util.LinkedList;
import java.util.Queue;

public class LevelOrderTraversalOrBFSTemplate {
    public void templateWithInnerForLoop(final Node root) {
        if (root == null) {
            return;
        }
        // This 'ArrayDeque' class is likely to be faster than Stack when used as a stack, and faster than LinkedList
        // when used as a queue. It is also known as Array Double Ended Queue or Array Deck. This is a special kind of
        // array that grows and allows users to add or remove an element from both sides of the queue. The ArrayDeque
        // class is not thread-safe, but you can use the Collections.synchronizedDeque method to create a thread-safe
        // version of the ArrayDeque class.
        final Queue<Node> queue = new ArrayDeque<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            final int size = queue.size();
            for (int i = 0; i < size; i++) { //traverses one level of tree
                final Node currentNode = queue.poll();
                processNode(queue, currentNode);
            }
        }
    }

    void templateWithoutInnerForLoop(final Node node) {
        if (node == null) {
            return;
        }
        boolean isLevelProcessed = true; // you may or may not need this flag based on your requirements
        final Queue<Node> queue = new LinkedList<>(); // Using LinkedList here as ArrayDeque doesn't accept null elements
        queue.offer(node);
        queue.offer(null); // marks the end of the level
        while (queue.size() != 1) {
            final Node curr = queue.poll();
            if (curr == null) {  // check if end of the level
                // process the end of level here
                isLevelProcessed = true;
                queue.offer(null); // marks the end of the level
                continue;
            }
            if (isLevelProcessed) {
                isLevelProcessed = false;
            }
            processNode(queue, curr);
        }
    }

    private void processNode(final Queue<Node> queue, final Node current) {
        final Node left = current.left;
        final Node right = current.right;
        // process the current node somewhere here
        addNodeToQueue(queue, left);
        addNodeToQueue(queue, right);
    }

    private void addNodeToQueue(final Queue<Node> queue, final Node node) {
        if (node != null) {
            queue.offer(node);
        }
    }

    private static class Node {
        int val;
        Node left;
        Node right;
    }
}
