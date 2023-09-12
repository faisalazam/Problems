package com.algorithms.geeksforgeeks.easy.treetraversals;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Queue;

public class SumOfLeafNodes {
    /**
     * Given a Binary Tree of size N. The task is to complete the function sumLeaf(), that should return the sum of
     * all the leaf nodes of the given binary tree.
     * <p>
     * https://practice.geeksforgeeks.org/problems/sum-of-leaf-nodes/1
     */
    private int sumOfLeafNodes(Node root) { // DFS
        int sum = 0;
        if (root == null) {
            return sum;
        }

        // This 'ArrayDeque' class is likely to be faster than Stack when used as a stack, and faster than LinkedList
        // when used as a queue. It is also known as Array Double Ended Queue or Array Deck. This is a special kind of
        // array that grows and allows users to add or remove an element from both sides of the queue. The ArrayDeque
        // class is not thread-safe, but you can use the Collections.synchronizedDeque method to create a thread-safe
        // version of the ArrayDeque class.
        final Deque<Node> stack = new ArrayDeque<>();
        pushLeft(root, stack);
        while (!stack.isEmpty()) {
            final Node curr = stack.pop();
            sum += processNode(curr, stack);
        }
        return sum;
    }

    private void pushLeft(final Node node, final Deque<Node> stack) {
        Node tmp = node;
        while (tmp != null) {
            stack.push(tmp);
            tmp = tmp.left;
        }
    }

    private int processNode(final Node node, final Deque<Node> stack) {
        if (node.left == null && node.right == null) {
            return node.data;
        }
        pushLeft(node.right, stack);
        return 0;
    }

    private int sumOfLeafNodesV1(Node root) { // BFS
        int sum = 0;
        if (root == null) {
            return sum;
        }

        // This 'ArrayDeque' class is likely to be faster than Stack when used as a stack, and faster than LinkedList
        // when used as a queue. It is also known as Array Double Ended Queue or Array Deck. This is a special kind of
        // array that grows and allows users to add or remove an element from both sides of the queue. The ArrayDeque
        // class is not thread-safe, but you can use the Collections.synchronizedDeque method to create a thread-safe
        // version of the ArrayDeque class.
        final Queue<Node> queue = new ArrayDeque<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            final Node curr = queue.poll();
            sum += processNode(curr, queue);
        }
        return sum;
    }

    private int processNode(final Node node, final Queue<Node> queue) {
        if (node.left == null && node.right == null) {
            return node.data;
        }
        addNode(node.left, queue);
        addNode(node.right, queue);
        return 0;
    }

    private void addNode(final Node node, final Queue<Node> queue) {
        if (node != null) {
            queue.offer(node);
        }
    }

    private int sumOfLeafNodesV0(Node root) {
        if (root == null) {
            return 0;
        } else if (root.left == null && root.right == null) {
            return root.data;
        } else if (root.left == null) {
            return sumOfLeafNodesV0(root.right);
        } else if (root.right == null) {
            return sumOfLeafNodesV0(root.left);
        }
        return sumOfLeafNodesV0(root.left) + sumOfLeafNodesV0(root.right);
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
