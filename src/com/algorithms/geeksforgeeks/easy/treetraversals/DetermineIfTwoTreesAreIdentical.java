package com.algorithms.geeksforgeeks.easy.treetraversals;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Given two binary trees, the task is to find if both of them are identical or not.
 * <p>
 * User task:
 * Since this is a functional problem you don't have to worry about input, you just have to complete the
 * function isIdentical() that takes two roots as parameters and returns true or false. The printing is done by the
 * driver code.
 * <p>
 * Expected Time Complexity: O(N).
 * Expected Auxiliary Space: O(Height of the Tree).
 * <p>
 * https://practice.geeksforgeeks.org/problems/determine-if-two-trees-are-identical/1
 */
public class DetermineIfTwoTreesAreIdentical {
    private boolean isIdentical(Node root1, Node root2) {
        // return isIdenticalRecursive(root1, root2);
        return isIdenticalIterative(root1, root2);
    }

    /**
     * Complexity
     * Time Complexity : O(min(N, M)), Where N and M are the sizes of the trees
     * Space Complexity : O(log min(N, M)), due to auxiliary stack space used by recursion calls.
     */
    private boolean isIdenticalRecursive(Node root1, Node root2) {
        if (root1 == null && root2 == null) {
            return true;
        } else if (root1 == null || root2 == null) {
            return false;
        }
        return root1.data == root2.data
                && isIdenticalRecursive(root1.left, root2.left)
                && isIdenticalRecursive(root1.right, root2.right);
    }

    private boolean isIdenticalIterative(Node root1, Node root2) {
        final Queue<Node> queue1 = new LinkedList<>();
        queue1.offer(root1);

        final Queue<Node> queue2 = new LinkedList<>();
        queue2.offer(root2);

        while (!queue1.isEmpty() && !queue2.isEmpty()) {
            final Node temp1 = queue1.poll();
            final Node temp2 = queue2.poll();
            if (temp1 == null && temp2 == null) {
                if (queue1.isEmpty() && queue2.isEmpty()) {
                    return true;
                }
            } else if (temp1 == null || temp2 == null || temp1.data != temp2.data) {
                return false;
            } else {
                queue1.offer(temp1.left);
                queue2.offer(temp2.left);
                queue1.offer(temp1.right);
                queue2.offer(temp2.right);
            }
        }
        return queue1.isEmpty() && queue2.isEmpty();
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

    /**
     * Brute Force Approach
     * Intuition
     * Two binary search trees are identical if they have same inorder, preorder and postorder traversal (all).
     *
     * Note : We can't find one traversal, say inorder, and if it is the same for both the trees, can we say the given
     * trees are identical, because we can have two trees with the same inorder traversal, still they can be non-identical.
     *
     * Implementation
     * Create two vectors to store the traversal for both the trees.
     * Use them to store the traversal for both the trees and compare them.
     * If all of the inorder, preorder and postorder are same then return true else return false.
     *
     * Complexity
     * Time Complexity : O(N)
     * Space Complexity : O(N), since using  ArrayList and call stack
     */
}
