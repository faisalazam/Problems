package com.algorithms.geeksforgeeks.easy.treetraversals;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Given a Binary Tree, convert it into its mirror.
 *         1                       1
 *      /     \                 /     \
 *     2       3       =>      3       2
 *   /  \    /   \           /  \    /   \ 
 *  4    5  6     7         7    6  5     4
 *
 * <p>
 * Your Task:
 * You don't have to take any input. Just complete the function mirror() that takes node as
 * parameter  and convert it into its mirror. The printing is done by the driver code only.
 * Expected Time Complexity: O(N).
 * Expected Auxiliary Space: O(Height of the Tree).
 * <p>
 * https://practice.geeksforgeeks.org/problems/mirror-tree/1
 */
public class MirrorTree {
    /**
     * Intuition:
     * Mirror of a Binary Tree T is another Binary Tree M(T) with left and right children of all non-leaf nodes
     * interchanged. Its very easy to use the idea of swapping for the above purpose. We can traverse the tree and keep
     * swapping the left and right children.
     */
    void mirror(final Node node) {
        mirrorRecursive(node);
        // mirrorIterative(node);
    }

    /**
     * Time Complexity: O(N), Visiting all the nodes of the tree of size N
     * Space Complexity: O(H) , auxiliary space for call stack.
     */
    private void mirrorRecursive(final Node node) {
        if (node == null) {
            return;
        }
        swap(node);
        mirrorRecursive(node.left);
        mirrorRecursive(node.right);
    }

    /**
     * We can also use queue and Level Based approach for implementation for above problem. While traversing level
     * order, we can swap left and right nodes.
     * <p>
     * Time Complexity: O(N), Traversing over the tree of size N
     * Auxiliary Space: O(N), Using queue to store the nodes of the tree
     */
    void mirrorIterative(final Node node) {
        if (node == null) {
            return;
        }
        final Queue<Node> queue = new LinkedList<>();
        queue.offer(node);
        while (!queue.isEmpty()) {
            final Node curr = queue.poll();
            processNode(queue, curr);
        }
    }

    private void processNode(final Queue<Node> queue, final Node current) {
        swap(current);
        addNodeToQueue(queue, current.left);
        addNodeToQueue(queue, current.right);
    }

    private void addNodeToQueue(final Queue<Node> queue, final Node node) {
        if (node != null) {
            queue.offer(node);
        }
    }

    private void swap(final Node node) {
        if (node.left != null || node.right != null) {
            final Node temp = node.left;
            node.left = node.right;
            node.right = temp;
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
