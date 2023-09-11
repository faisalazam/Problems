package com.algorithms.geeksforgeeks.easy.treetraversals;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class LevelOrderTraversal {
    /**
     * You are given a tree and you need to do the level order traversal on this tree.
     * Level order traversal of a tree is breadth-first traversal for the tree.
     * For the below tree the output will be 1 2 3 4 5
     *          1
     *        /  \
     *      2     3
     *    /   \
     *   4     5
     * <p>
     * https://practice.geeksforgeeks.org/problems/level-order-traversal/1
     * <p>
     * Time Complexity: O(N), Where n is the number of nodes in the binary tree.
     * Auxiliary Space: O(N), Where n is the number of nodes in the binary tree.
     */
    static ArrayList<Integer> levelOrder(final Node node) {
        final ArrayList<Integer> result = new ArrayList<>();
        if (node == null) {
            return result;
        }
        final Queue<Node> queue = new LinkedList<>();
        queue.offer(node);

        while (!queue.isEmpty()) {
            final Node current = queue.poll();
            processNode(current, queue, result);
        }
        return result;
    }

    private static void processNode(final Node current,
                                    final Queue<Node> queue,
                                    final ArrayList<Integer> result) {
        result.add(current.data);
        addNode(current.left, queue);
        addNode(current.right, queue);
    }

    private static void addNode(final Node node, final Queue<Node> queue) {
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
