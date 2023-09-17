package com.algorithms.geeksforgeeks.easy.treetraversals;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Given a Binary Tree, print Left view of it. Left view of a Binary Tree is set of nodes visible
 * when tree is visited from Left side. The task is to complete the function leftView(),
 * which accepts root of the tree as argument.
 * <p>
 * Left view of following tree is 1 2 4 8.
 *          1
 *       /    \
 *     2       3
 *   /  \    /   \
 *  4   5   6    7
 *   \
 *    8
 * <p>
 * User Task:
 * Since this is a functional problem you don't have to worry about input, you just have to
 * complete the function leftView() that prints the left view. The newline is automatically appended by the driver code.
 * Expected Time Complexity: O(N).
 * Expected Auxiliary Space: O(Height of the Tree).
 * <p>
 * https://practice.geeksforgeeks.org/problems/left-view-of-binary-tree/1
 */
public class LeftViewOfBinaryTree {
    private int maxLevel = 0;

    ArrayList<Integer> leftView(final Node root) {
        final ArrayList<Integer> result = new ArrayList<>();
        leftViewRecursive(root, 1, result);
        return (result);
    }

    /**
     * Time Complexity: O(N) since, we have to process all the nodes , N=size of the binary tree
     * Space Complexity: O(N) auxiliary space to store all the nodes.
     */
    private void leftViewIterative(final Node root, final ArrayList<Integer> result) {
        if (root == null) {
            return;
        }
        final Queue<Node> queue = new ArrayDeque<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            final int size = queue.size();
            for (int i = 0; i < size; i++) {
                final Node current = queue.poll();
                if (i == 0) {
                    result.add(current.data);
                }
                addNodeToQueue(queue, current.left);
                addNodeToQueue(queue, current.right);
            }
        }
    }

    /**
     * Another iterative approach would  be to insert null in start/end of the queue to signify the start or end of the
     * level
     * <p>
     * Time Complexity: O(N) since, we have to process all the nodes , N=size of the binary tree
     * Space Complexity: O(N) auxiliary space to store all the nodes.
     */
    private void leftViewIterativeUsingNullMarker(final Node root, final ArrayList<Integer> result) {
        if (root == null) {
            return;
        }
        boolean isLevelProcessed = false;
        final Queue<Node> queue = new LinkedList<>(); // can't use ArrayDeque here as this queue does not permit null elements
        queue.offer(root);
        queue.offer(null);

        while (queue.size() != 1) {
            final Node current = queue.poll();
            if (current == null) {  // check if end of the level
                isLevelProcessed = false;
                queue.offer(null); // marks the end of the level
                continue;
            }
            if (!isLevelProcessed) {
                isLevelProcessed = true;
                result.add(current.data);
            }
            addNodeToQueue(queue, current.left);
            addNodeToQueue(queue, current.right);
        }
    }

    private void addNodeToQueue(final Queue<Node> queue, final Node node) {
        if (node != null) {
            queue.offer(node);
        }
    }

    /**
     * The idea is to use recursion. We can traverse the tree in a preorder manner and maintain the maximum level
     * visited till the current node. If the current level is more than the maximum level visited till now, then the
     * current node is the first node of the current level, and we print it and max_level will be changed to the current
     * level.
     * <p>
     * Time Complexity:The time complexity of the above solution is O(n), where n is the total number of nodes in the
     * binary tree.
     * Space Complexity: The program requires O(h) extra space for the call stack, where h is the height of the tree.
     */
    private void leftViewRecursive(final Node root, final int level, final ArrayList<Integer> result) {
        if (root == null) {
            return;
        }
        if (level > maxLevel) {
            maxLevel = level;
            result.add(root.data);
        }
        leftViewRecursive(root.left, level + 1, result);
        leftViewRecursive(root.right, level + 1, result);
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
