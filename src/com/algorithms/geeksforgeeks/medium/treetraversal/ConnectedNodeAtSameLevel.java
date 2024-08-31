package com.algorithms.geeksforgeeks.medium.treetraversal;

import org.w3c.dom.Node;

import java.util.ArrayDeque;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Given a binary tree, connect the nodes that are at same level.
 * <p>
 * Initially, all the nextRight pointers point to garbage values. Your function should set these pointers
 * to point next right for each node.
 *        10                          10 ------> NULL
 *       / \                       /      \
 *      3   5       =>           3 ------> 5 --------> NULL
 *     / \   \                 /  \         \
 *    4   1   2              4 --> 1 ------> 2 -------> NULL
 * <p>
 * https://practice.geeksforgeeks.org/problems/connect-nodes-at-same-level/1
 */
public class ConnectedNodeAtSameLevel {
    /**
     * Time Complexity: O(N), though we are using nested loop but the number of iterations in any case will never be
     * greater than N times, where N is the number of nodes in the binary tree.
     * Auxiliary Space: O(1), no space is being utilized in this approach.
     */
    static void connect(Node root) {
        if (root == null) {
            return;
        }
        root.nextRight = null;

        while (root != null) {
            Node temp = root;

            // Setting up the nextRight pointers of one level, and that is the next level of temp/root
            // OR for loop can be used like below instead of the while loop
            // for (Node temp = root; temp != null; temp = temp.nextRight) {
            while (temp != null) {
                if (temp.left != null) {
                    temp.left.nextRight = temp.right != null
                            ? temp.right
                            : getNextRight(temp);
                }
                if (temp.right != null) {
                    temp.right.nextRight = getNextRight(temp);
                }
                temp = temp.nextRight;
            }

            // Move the root to the next level
            if (root.left != null) {
                root = root.left;
            } else if (root.right != null) {
                root = root.right;
            } else {
                root = getNextRight(root);
            }
        }
    }

    /**
     * This method will find and return the very first node pointed by the nextRight pointer in the next level
     */
    private static Node getNextRight(Node node) {
        Node temp = node.nextRight;
        while (temp != null) {
            if (temp.left != null) {
                return temp.left;
            }
            if (temp.right != null) {
                return temp.right;
            }
            temp = temp.nextRight;
        }
        return null;
    }

    /**
     * Time Complexity: O(N), for level order traversal of the binary tree
     * Auxiliary Space: O(Height of the Tree) => O(H) => where H, in best case, will be log(N); and N in worst case,
     * for storing the nodes in the queue, where N is the number of nodes present in the binary tree.
     */
    public static void connectV1(final Node root) {
        if (root == null) {
            return;
        }
        final Queue<Node> queue = new ArrayDeque<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            final int size = queue.size();
            for (int i = 0; i < size; i++) {
                final Node current = queue.poll();
                // If we don't use the 'i < size - 1' condition, then it'll set the nextRight pointer of the last node
                // in the current level to the first node in the next level, so it's an important condition
                processNode(queue, current, i < size - 1);
            }
        }
    }

    /**
     * Time Complexity: O(N), for level order traversal of the binary tree
     * Auxiliary Space: O(Height of the Tree) => O(H) => where H, in best case, will be log(N); and N in worst case,
     * for storing the nodes in the queue, where N is the number of nodes present in the binary tree.
     */
    public static void connectV0(final Node root) {
        if (root == null) {
            return;
        }
        final Queue<Node> queue = new LinkedList<>();
        queue.offer(root);
        queue.offer(null); // marks the end of level
        while (queue.size() != 1) {
            final Node curr = queue.poll();
            if (curr != null) {
                processNode(queue, curr, true);
            } else {
                queue.offer(null); // marks the end of level
            }
        }
    }

    private static void processNode(final Queue<Node> queue, final Node current, final boolean setNetRight) {
        if (setNetRight) {
            current.nextRight = queue.peek();
        }
        addNodeToQueue(queue, current.left);
        addNodeToQueue(queue, current.right);
    }

    private static void addNodeToQueue(final Queue<Node> queue, final Node node) {
        if (node != null) {
            queue.offer(node);
        }
    }

    private static class Node {
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
