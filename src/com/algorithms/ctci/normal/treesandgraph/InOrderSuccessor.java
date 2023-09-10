package com.algorithms.ctci.normal.treesandgraph;

public class InOrderSuccessor {
    /**
     * Successor: Write an algorithm to find the "next" node (i.e., in-order successor) of a given node in a
     * binary search tree. You may assume that each node has a link to its parent.
     */
    public Node inorderSucc(Node n) {
        if (n == null) {
            return null;
        }

        // Found right children -> return left most node of right subtree
        if (n.parent == null || n.right != null) {
            return leftMostChild(n.right);
        } else {
            Node q = n;
            Node x = q.parent;
            // Go up until we’re on left instead of right
            while (x != null && x.left != q) {
                q = x;
                x = x.parent;
            }
            return x;
        }
    }

    private Node leftMostChild(Node n) {
        if (n == null) {
            return null;
        }
        while (n.left != null) {
            n = n.left;
        }
        return n;
    }

    private class Node {
        int data;
        Node left;
        Node right;
        Node parent;

        Node(int data) {
            this.data = data;
            this.left = this.right = this.parent = null;
        }
    }
}
