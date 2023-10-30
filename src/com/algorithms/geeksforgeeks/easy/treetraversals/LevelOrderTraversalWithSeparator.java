package com.algorithms.geeksforgeeks.easy.treetraversals;

import java.util.LinkedList;
import java.util.Queue;

public class LevelOrderTraversalWithSeparator {
    /**
     * Given a Binary Tree, your task is to print its level order traversal such that each level is separated by $.
     * For the below tree the output will be 1 $ 2 3 $ 4 5 6 7 $ 8 $.
     *
     *           1
     *        /    \
     *      2       3
     *     / \     / \
     *    4   5   6   7
     *     \
     *      8
     * <p>
     * https://practice.geeksforgeeks.org/problems/level-order-traversal-line-by-line/1
     */
    void levelOrder(Node node) {
        if (node == null) {
            return;
        }
        final Node levelTerminationNode = new Node(Integer.MIN_VALUE);
        final Queue<Node> queue = new LinkedList<>();
        queue.offer(node);
        queue.offer(levelTerminationNode);
        while (queue.size() != 1) {
            Node curr = queue.poll();
            if (curr == levelTerminationNode) {
                System.out.print("$ ");
                queue.offer(levelTerminationNode);
                continue;
            }
            System.out.print(curr.data + " ");
            if (curr.left != null) {
                queue.offer(curr.left);
            }
            if (curr.right != null) {
                queue.offer(curr.right);
            }
        }
        System.out.print("$");
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
