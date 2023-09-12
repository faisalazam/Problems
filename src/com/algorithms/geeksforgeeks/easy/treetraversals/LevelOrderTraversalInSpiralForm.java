package com.algorithms.geeksforgeeks.easy.treetraversals;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Stack;

public class LevelOrderTraversalInSpiralForm {
    /**
     * Complete the function to print spiral order traversal of a tree. For below tree, function should print 1, 2, 3, 4, 5, 6, 7.
     *           1
     *        /    \
     *      2       3
     *    /  \    /  \
     *   7    6  5    4
     * Your Task:
     * The task is to complete the function printSpiral() which prints the elements in spiral form
     * of level order traversal. The newline is automatically appended by the driver code.
     * <p>
     * https://practice.geeksforgeeks.org/problems/level-order-traversal-in-spiral-form/1
     * <p>
     * <p>
     * The idea is to use two separate stacks to store the level order traversal as per their levels in adjacent order.
     * <p>
     * Time Complexity: O(N), where N is the number of nodes in the binary tree.
     * Auxiliary Space: O(N), for storing the nodes in the stacks.
     */
    ArrayList<Integer> printSpiral(Node root) {
        final ArrayList<Integer> result = new ArrayList<>();
        if (root == null) {
            return result;
        }

        // First In First Out - FIFO DataStructure - Queue won't work here due to the order of the processing of the
        // nodes, because in the initial couple of levels, Queue might be alright, but it'll fail for right to left processing
        // Last In First Out - LIFO DataStructure - Stack
        final Stack<Node> stack1 = new Stack<>();
        final Stack<Node> stack2 = new Stack<>();
        stack1.push(root);
        while (!stack1.isEmpty()) {
            // traverse stack1 until its empty and push nodes to stack2
            while (!stack1.isEmpty()) { // no need to check !stack2.isEmpty() here as it'll always get empty inside the loop
                final Node curr1 = stack1.pop();
                result.add(curr1.data);

                // As in the next level, processing from left to right is required, hence adding nodes from
                // right to left, because Stack is a LIFO data structure
                addNode(curr1.right, stack2);
                addNode(curr1.left, stack2);
            }

            // traverse stack2 until its empty and push nodes to stack1
            while (!stack2.isEmpty()) {
                final Node curr = stack2.pop();
                result.add(curr.data);

                // As in the next level, processing from right to left is required, hence adding nodes from
                // left to right, because Stack is a LIFO data structure
                addNode(curr.left, stack1);
                addNode(curr.right, stack1);
            }
        }
        return result;
    }

    private static void addNode(final Node node, final Stack<Node> stack) {
        if (node != null) {
            stack.push(node);
        }
    }

    /**
     * The idea is to use Doubly Ended Queues, then push and pop the nodes from each end in alternate order.
     * <p>
     * Time Complexity: O(N), where N is the number of nodes in the binary tree.
     * Auxiliary Space: O(N), for storing the nodes in the Deque.
     */
    ArrayList<Integer> findSpiral(Node root) {
        final ArrayList<Integer> result = new ArrayList<>();
        if (root == null) {
            return result;
        }

        // This 'ArrayDeque' class is likely to be faster than Stack when used as a stack, and faster than LinkedList
        // when used as a queue. It is also known as Array Double Ended Queue or Array Deck. This is a special kind of
        // array that grows and allows users to add or remove an element from both sides of the queue. The ArrayDeque
        // class is not thread-safe, but you can use the Collections.synchronizedDeque method to create a thread-safe
        // version of the ArrayDeque class.
        final Deque<Node> queue = new ArrayDeque<>();
        queue.offer(root);

        boolean reverse = true;
        while (!queue.isEmpty()) {
            int size = queue.size();

            if (reverse) {
                // Iterate the deque in reverse order and insert the children from the front to process from right to left
                while (size-- > 0) {
                    // Traverse from the end of the queue but insert the children from front/head of the queue
                    final Node node = queue.pollLast();
                    if (node != null) {
                        result.add(node.data);

                        // As in the next level, processing from left to right is required, hence adding nodes from
                        // right to left on the front/head of the dequeue and then queue.pollFirst() can be used while
                        // traversing next level
                        if (node.right != null) {
                            queue.offerFirst(node.right);
                        }
                        if (node.left != null) {
                            queue.offerFirst(node.left);
                        }
                    }
                }
            } else { // left to right traversal
                while (size-- > 0) { // Iterate from left to right
                    // Traverse from the front/head of the queue but insert the children from end/tail of the queue
                    final Node node = queue.pollFirst();
                    if (node != null) {
                        result.add(node.data);

                        // As in the next level, processing from right to left is required, hence adding nodes from
                        // left to right on the back/tail of the dequeue and then queue.pollLast() can be used while
                        // traversing next level
                        if (node.left != null) {
                            queue.offerLast(node.left);
                        }
                        if (node.right != null) {
                            queue.offerLast(node.right);
                        }
                    }
                }
            }
            reverse = !reverse; // Switch reverse for next traversal
        }

        return result;
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
