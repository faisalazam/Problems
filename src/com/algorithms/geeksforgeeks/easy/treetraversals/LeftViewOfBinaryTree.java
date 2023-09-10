package com.algorithms.geeksforgeeks.easy.treetraversals;

public class LeftViewOfBinaryTree {
    /**
     * Given a Binary Tree, print Left view of it. Left view of a Binary Tree is set of nodes visible
     * when tree is visited from Left side. The task is to complete the function leftView(),
     * which accepts root of the tree as argument.
     *
     * Left view of following tree is 1 2 4 8.
     *
     *           1
     *        /    \
     *      2       3
     *    /  \    /   \
     *   4   5   6    7
     *    \
     *      8
     *
     * User Task:
     * Since this is a functional problem you don't have to worry about input, you just have to
     * complete the function leftView() that prints the left view. The newline is automatically appended by the driver code.
     * Expected Time Complexity: O(N).
     * Expected Auxiliary Space: O(Height of the Tree).
     * <p>
     * https://practice.geeksforgeeks.org/problems/left-view-of-binary-tree/1
     */
    private int maxLevel = 0;

    void leftView(Node root) {
        StringBuilder builder = new StringBuilder();
        leftView(root, 1, builder);
        System.out.print(builder.toString());
    }

    private void leftView(Node root, int level, StringBuilder builder) {
        if (root == null) {
            return;
        }
        if (level > maxLevel) {
            maxLevel = level;
            builder.append(root.data).append(" ");
        }
        leftView(root.left, level + 1, builder);
        leftView(root.right, level + 1, builder);
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
