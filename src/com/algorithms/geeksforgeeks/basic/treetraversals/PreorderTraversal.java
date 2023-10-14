package com.algorithms.geeksforgeeks.basic.treetraversals;

import java.util.ArrayList;
import java.util.Stack;

public class PreorderTraversal {
    /**
     * Given a binary tree, print preorder traversal of it. The task is to complete the function preorder(),
     * which accept root of the tree as argument.
     * For example: preorder traversal of below tree is "1 10 5 39"
     * 1
     * /    \
     * 10     39
     * /
     * 5
     * <p>
     * https://practice.geeksforgeeks.org/problems/preorder-traversal/1
     * <p>
     * Time Complexity: O(N).
     * Auxiliary Space: O(Height of the Tree) => O(H) => where H, in best case, will be log(N); and N in worst case.
     */
    static ArrayList<Integer> preorder(final Node root) {
        final ArrayList<Integer> result = new ArrayList<>();
//        preorderIterative(root, result);
        preorderRecursive(root, result);
        return result;
    }

    private static void preorderIterative(final Node root, final ArrayList<Integer> result) {
        final Stack<Node> stack = new Stack<>();
        if (root != null) {
            stack.push(root);
        }

        while (!stack.isEmpty()) {
            final Node currentNode = stack.pop();
            processNode(currentNode, stack, result);
        }
    }

    private static void processNode(final Node current,
                                    final Stack<Node> stack,
                                    final ArrayList<Integer> result) {
        result.add(current.data);
        addNode(current.right, stack);
        addNode(current.left, stack);
    }

    private static void addNode(final Node node, final Stack<Node> stack) {
        if (node != null) {
            stack.push(node);
        }
    }

    private static void preorderIterativeV1(final Node root, final ArrayList<Integer> result) {
        Node currentNode = root;
        final Stack<Node> stack = new Stack<>();

        while (currentNode != null || !stack.isEmpty()) {
            pushLeft(currentNode, stack, result);
            currentNode = stack.pop();
            currentNode = currentNode.right;
        }
    }

    private static void preorderIterativeV0(final Node root, final ArrayList<Integer> result) {
        Stack<Node> stack = new Stack<>();

        pushLeft(root, stack, result);
        while (!stack.isEmpty()) {
            Node temp = stack.pop();
            pushLeft(temp.right, stack, result);
        }
    }

    private static void pushLeft(final Node root, final Stack<Node> stack, final ArrayList<Integer> result) {
        Node temp = root;
        while (temp != null) {
            result.add(temp.data);
            stack.push(temp);
            temp = temp.left;
        }
    }

    private static void preorderRecursive(final Node root, final ArrayList<Integer> result) {
        if (root == null) {
            return;
        }
        result.add(root.data);
        preorderRecursive(root.left, result);
        preorderRecursive(root.right, result);
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
