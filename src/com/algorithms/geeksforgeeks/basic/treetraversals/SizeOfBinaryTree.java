package com.algorithms.geeksforgeeks.basic.treetraversals;

import java.util.LinkedList;
import java.util.Queue;

public class SizeOfBinaryTree {
    /**
     * Given a binary tree of size N, you have to count number of nodes in it. For example, count of nodes in below tree is 4.
     *        1
     *      /   \
     *    10    39
     *   /
     *  5
     * <p>
     * https://practice.geeksforgeeks.org/problems/size-of-binary-tree/1
     * <p>
     * Time Complexity: O(N) As every node is visited once.
     * Auxiliary Space: O(N) The extra space is due to the recursion call stack and the worst case occurs when the
     * tree is either left skewed or right skewed.
     */
    int size(Node root) {
        if (root == null) {
            return 0;
        }
        return 1 + size(root.left) + size(root.right);
    }

    /**
     * Time Complexity: O(N)
     * Auxiliary Space: O(N) or O(H)? I think, O(H)
     */
    int sizeIterative(Node node) {
        if (node == null) {
            return 0;
        }
        int count = 1;
        final Queue<Node> queue = new LinkedList<>();
        queue.offer(node);

        while (!queue.isEmpty()) {
            final int size = queue.size();
            for (int i = 0; i < size; i++) { //traverses one level of tree
                final Node currentNode = queue.poll();
                count += processNode(queue, currentNode);
            }
        }
        return count;
    }

    private int processNode(final Queue<Node> queue, final Node current) {
        int count = 0;
        count += addNodeToQueue(queue, current.left);
        count += addNodeToQueue(queue, current.right);
        return count;
    }

    private int addNodeToQueue(final Queue<Node> queue, final Node node) {
        if (node != null) {
            queue.offer(node);
            return 1;
        }
        return 0;
    }

    /**
     * Using Morris Traversal:
     * <p>
     * The basic idea of Morris traversal is to use the empty right child of a node to store a temporary link to the
     * next node in the inorder traversal. By using this link, we can traverse the binary tree without using any extra
     * space for a stack or recursion.
     * <p>
     * Follow the steps to solve the problem:
     * <p>
     * 1. Initialize a counter variable to 0 and a current pointer to the root of the binary tree.
     * 2. While the current pointer is not NULL:
     * a. If the current node has no left child, increment the counter and move the current pointer to its right
     * child.
     * b. Otherwise, find the rightmost node in the left subtree of the current node.
     * i. If this node does not have a right child, set its right child to the current node and move the
     * current pointer to its left child.
     * ii. Otherwise, the right child of this node already points to the current node, so we have visited all
     * the nodes in the left subtree of the current node. Set the right child of this node to NULL,
     * increment the counter, and move the current pointer to its right child.
     * 3. When the current pointer becomes NULL, we have visited all the nodes in the binary tree. Return the counter
     * variable.
     * <p>
     * Time Complexity: O(N) , since traversing all the nodes in the tree only once.
     * Auxiliary Space: O(1) , No extra space is required.
     */
    public static int countNodes(Node root) {
        int count = 0;
        Node current = root;
        while (current != null) {
            // if the current node has no left child, increment the count and move to the right child
            if (current.left == null) {
                count++;
                current = current.right;
                continue;
            }
            // find the rightmost node in the left subtree of the current node
            Node predecessor = current.left;
            while (predecessor.right != null && predecessor.right != current) {
                predecessor = predecessor.right;
            }
            if (predecessor.right == null) {
                // set the right child of the rightmost node to the current node
                predecessor.right = current;
                // move to the left child of the current node
                current = current.left;
            } else {
                // restore the right child of the rightmost node to NULL
                predecessor.right = null;
                // increment the count for the current node
                count++;
                // move to the right child of the current node
                current = current.right;
            }
        }
        return count;
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
