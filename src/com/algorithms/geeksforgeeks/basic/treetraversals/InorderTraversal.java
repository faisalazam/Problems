package com.algorithms.geeksforgeeks.basic.treetraversals;

import java.util.ArrayList;
import java.util.Stack;

public class InorderTraversal {
    /**
     * Given a Binary Tree, find the In-Order Traversal of it.
     * <p>
     * Your Task:
     * You don't need to read input or print anything. Your task is to complete the function inOrder()
     * that takes root node of the tree as input and returns a list containing the In-Order Traversal of the given Binary Tree.
     * <p>
     * Time Complexity: O(N).
     * Auxiliary Space: O(Height of the Tree) => O(H) => where H, in best case, will be log(N); and N in worst case.
     * <p>
     * https://practice.geeksforgeeks.org/problems/inorder-traversal/1
     */
    ArrayList<Integer> inOrder(Node root) {
        final ArrayList<Integer> result = new ArrayList<>();
         inOrderIterative(root, result);
//        inOrderRecursive(root, result);
        return result;
    }

    private void inOrderIterative(final Node root, final ArrayList<Integer> result) {
        Node currentNode = root;
        final Stack<Node> stack = new Stack<>();

        while (currentNode != null || !stack.isEmpty()) {
            pushLeft(currentNode, stack);
            currentNode = stack.pop();
            result.add(currentNode.data);
            currentNode = currentNode.right;
        }
    }

    private void inOrderIterativeV0(final Node root, final ArrayList<Integer> result) {
        final Stack<Node> stack = new Stack<>();

        pushLeft(root, stack);
        while (!stack.isEmpty()) {
            final Node temp = stack.pop();
            result.add(temp.data);
            pushLeft(temp.right, stack);
        }
    }

    private void pushLeft(final Node root, final Stack<Node> stack) {
        Node temp = root;
        while (temp != null) {
            stack.push(temp);
            temp = temp.left;
        }
    }

    private void inOrderRecursive(Node root, final ArrayList<Integer> result) {
        if (root == null) {
            return;
        }
        inOrderRecursive(root.left, result);
        result.add(root.data);
        inOrderRecursive(root.right, result);
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
