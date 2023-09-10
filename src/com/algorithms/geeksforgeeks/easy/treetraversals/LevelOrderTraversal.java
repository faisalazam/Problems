package com.algorithms.geeksforgeeks.easy.treetraversals;

import java.util.LinkedList;
import java.util.Queue;

public class LevelOrderTraversal {
    /**
     * You are given a tree and you need to do the level order traversal on this tree.
     * Level order traversal of a tree is breadth-first traversal for the tree.
     * For the below tree the output will be 1 2 3 4 5
     *
     *          1
     *        /   \
     *      2     3
     *    /    \
     *   4     5
     * <p>
     * https://practice.geeksforgeeks.org/problems/level-order-traversal/1
     */
    static void levelOrder(Node node) {
        if (node == null) {
            return;
        }
        Queue<Node> queue = new LinkedList<>();
        queue.offer(node);
        while (!queue.isEmpty()) {
            Node curr = queue.poll();
            System.out.print(curr.data + " ");
            if (curr.left != null) {
                queue.offer(curr.left);
            }
            if (curr.right != null) {
                queue.offer(curr.right);
            }
        }
    }

    private class Node {
        int data;
        Node left;
        Node right;

        Node(int data) {
            this.data = data;
            this.left = this.right = null;
        }
    }
}
