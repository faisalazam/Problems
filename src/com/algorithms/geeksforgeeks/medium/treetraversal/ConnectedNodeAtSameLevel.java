package com.algorithms.geeksforgeeks.medium.treetraversal;

import java.util.LinkedList;
import java.util.Queue;

public class ConnectedNodeAtSameLevel {
    /**
     * Given a binary tree, connect the nodes that are at same level.
     * <p>
     * Initially, all the nextRight pointers point to garbage values. Your function should set these pointers
     * to point next right for each node.
     *        10                       10 ------> NULL
     *       / \                       /      \
     *      3   5       =>           3 ------> 5 --------> NULL
     *     / \   \                 /  \         \
     *    4   1   2              4 --> 1 -----> 2 -------> NULL
     * <p>
     * https://practice.geeksforgeeks.org/problems/connect-nodes-at-same-level/1
     */
    public static void connect(Node p) {
        if (p == null) {
            return;
        }
        Queue<Node> queue = new LinkedList<>();
        queue.offer(p);
        queue.offer(null);
        while (queue.size() != 1) {
            Node curr = queue.poll();
            if (curr != null) {
                curr.nextRight = queue.peek();
                if (curr.left != null) {
                    queue.offer(curr.left);
                }
                if (curr.right != null) {
                    queue.offer(curr.right);
                }
            } else {
                queue.offer(null);
            }
        }
    }


    private class Node {
        int data;
        Node left;
        Node right;
        Node nextRight;

        Node(int data) {
            this.data = data;
            this.left = this.right = this.nextRight = null;
        }
    }
}
