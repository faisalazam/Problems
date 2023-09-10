package com.algorithms.geeksforgeeks.easy.treetraversals;

import java.util.LinkedList;
import java.util.Queue;

public class RightViewOfBinaryTree {
    /**
     * Given a Binary Tree, print Right view of it. Right view of a Binary Tree is set of nodes visible
     * when tree is viewed from right side.
     *
     * Right view of following tree is 1 3 7 8.
     *
     *           1
     *        /     \
     *      2        3
     *    /   \     /    \
     *   4     5   6    7
     *     \
     *      8
     *
     * Your Task:
     * This is a function problem. You don't have to take input. Just complete the function rightView() that
     * takes node as parameter and prints the right view. The newline is automatically appended by the driver code.
     * Expected Time Complexity: O(N).
     * Expected Auxiliary Space: O(Height of the Tree).
     * <p>
     * https://practice.geeksforgeeks.org/problems/right-view-of-binary-tree/1
     */
    void rightView(Node node) {
        if (node == null) {
            return;
        }
        boolean isLevelNotPrinted = true;
        StringBuilder builder = new StringBuilder();
        Queue<Node> queue = new LinkedList<>();
        queue.offer(node);
        queue.offer(null);
        while (queue.size() != 1) {
            Node curr = queue.poll();
            if (curr == null) {
                isLevelNotPrinted = true;
                queue.offer(null);
                continue;
            }
            if (isLevelNotPrinted) {
                isLevelNotPrinted = false;
                builder.append(curr.data).append(" ");
            }
            if (curr.right != null) {
                queue.offer(curr.right);
            }
            if (curr.left != null) {
                queue.offer(curr.left);
            }
        }
        System.out.print(builder.toString());
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
