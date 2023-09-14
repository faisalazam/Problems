package com.algorithms.geeksforgeeks.basic.treetraversals;

import java.util.ArrayList;
import java.util.Stack;

public class PostorderTraversal {
    /**
     * Given a binary tree, print postorder traversal of it. Postorder traversal of below tree is 5 10 39 1
     *         1
     *      /    \
     *    10     39
     *   /
     * 5
     * <p>
     * https://practice.geeksforgeeks.org/problems/postorder-traversal/1
     * <p>
     * Time Complexity: O(N).
     * Auxiliary Space: O(Height of the Tree) => O(H) => where H, in best case, will be log(N); and N in worst case.
     */
    ArrayList<Integer> postOrder(Node root) {
        final ArrayList<Integer> result = new ArrayList<>();
        postOrderRecursive(root, result);
        // postOrderIterative(root, result);
        return result;
    }

    void postOrderIterative(final Node root, final ArrayList<Integer> result) {
        final Stack<Node> stack = new Stack<>();
        pushLeft(root, stack);
        while (!stack.isEmpty()) {
            Node temp = stack.pop();
            if (!stack.empty() && stack.peek() == temp) {
                pushLeft(temp.right, stack);
            } else {
                result.add(temp.data);
            }
        }
    }

    private static void pushLeft(final Node root, final Stack<Node> stack) {
        Node temp = root;
        while (temp != null) {
            // Push directly root node two times while traversing to the left. While popping if you find stack top()
            // is same as root then go for root->right else print root.
            stack.push(temp);
            stack.push(temp);
            temp = temp.left;
        }
    }

    void postOrderRecursive(Node root, final ArrayList<Integer> result) {
        if (root == null) {
            return;
        }
        postOrderRecursive(root.left, result);
        postOrderRecursive(root.right, result);
        result.add(root.data);
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
