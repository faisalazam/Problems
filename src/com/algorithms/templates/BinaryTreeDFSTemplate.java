package com.algorithms.templates;

import java.util.ArrayDeque;
import java.util.Deque;

public class BinaryTreeDFSTemplate {
    /**
     * Helpful video on Tree Operations:
     * https://www.youtube.com/watch?v=fAAZixBzIAI
     */
    private void template(final Node root) { // iterative DFS
        Node currentNode = root;
        // This 'ArrayDeque' class is likely to be faster than Stack when used as a stack, and faster than LinkedList
        // when used as a queue. It is also known as Array Double Ended Queue or Array Deck. This is a special kind of
        // array that grows and allows users to add or remove an element from both sides of the queue. The ArrayDeque
        // class is not thread-safe, but you can use the Collections.synchronizedDeque method to create a thread-safe
        // version of the ArrayDeque class.
        final Deque<Node> stack = new ArrayDeque<>();

        while (currentNode != null || !stack.isEmpty()) {
            pushLeft(currentNode, stack);
            currentNode = stack.pop();
            currentNode = currentNode.right;
        }
    }

    public void templateV0(final Node root) { // iterative DFS
        if (root == null) {
            return;
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
            processNode(curr, stack);
        }
    }

    private void pushLeft(final Node node, final Deque<Node> stack) {
        Node tmp = node;
        while (tmp != null) {
            stack.push(tmp);
            tmp = tmp.left;
        }
    }

    private void processNode(final Node node, final Deque<Node> stack) {
        // Do the processing somewhere here.
        pushLeft(node.right, stack);
    }

    private static class Node {
        int data;
        Node left;
        Node right;
    }
}
