package com.algorithms.geeksforgeeks.easy.treetraversals;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class ReverseLevelOrderTraversal {
    /**
     * Given a Binary Tree of size N. You have to print the Reverse Level Order Traversal of the given tree ,
     * that is first you have to print nodes of last level of the tree , then nodes of second last and so on.
     * For Example: The Reverse Level Order Traversal of the following Tree is 4 5 2 3 1 .
     *
     *            1
     *          /   \
     *        2      3
     *      /   \
     *     4     5
     *
     * <p>
     * User task:
     * You don't need to take input. Just complete the function reversePrint() that accepts root node of the
     * tree as parameter and prints the reverse level order traversal of tree .
     * Expected Time Complexity: O(N).
     * Expected Auxiliary Space: O(N).
     * <p>
     * https://practice.geeksforgeeks.org/problems/reverse-level-order-traversal/1
     */
    public void reversePrint(Node node) {
        if (node == null) {
            return;
        }
        Stack<Integer> stack = new Stack<>();
        Queue<Node> queue = new LinkedList<>();
        queue.offer(node);
        while (!queue.isEmpty()) {
            Node curr = queue.poll();
            stack.push(curr.data);
            if (curr.right != null) {
                queue.offer(curr.right);
            }
            if (curr.left != null) {
                queue.offer(curr.left);
            }
        }
        while (!stack.isEmpty()) {
            System.out.print(stack.pop() + " ");
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
