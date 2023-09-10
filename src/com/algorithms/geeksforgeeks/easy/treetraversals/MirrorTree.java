package com.algorithms.geeksforgeeks.easy.treetraversals;

import java.util.LinkedList;
import java.util.Queue;

public class MirrorTree {
    /**
     * Given a Binary Tree, convert it into its mirror.
     * <p>
     * Your Task:
     * You don't have to take any input. Just complete the function mirror() that takes node as
     * parameter  and convert it into its mirror. The printing is done by the driver code only.
     * Expected Time Complexity: O(N).
     * Expected Auxiliary Space: O(Height of the Tree).
     * <p>
     * https://practice.geeksforgeeks.org/problems/mirror-tree/1
     */
    void mirror(Node node) {
        mirrorRecursive(node);
        // mirrorIterative(node);
    }

    private void mirrorRecursive(Node node) {
        if (node == null) {
            return;
        }
        swap(node);
        mirrorRecursive(node.left);
        mirrorRecursive(node.right);
    }

    void mirrorIterative(Node node) {
        if (node == null) {
            return;
        }
        Queue<Node> queue = new LinkedList<>();
        queue.offer(node);
        while (!queue.isEmpty()) {
            Node curr = queue.poll();
            swap(curr);
            if (curr.left != null) {
                queue.offer(curr.left);
            }
            if (curr.right != null) {
                queue.offer(curr.right);
            }
        }
    }

    private void swap(Node node) {
        if (node.left != null || node.right != null) {
            Node temp = node.left;
            node.left = node.right;
            node.right = temp;
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
