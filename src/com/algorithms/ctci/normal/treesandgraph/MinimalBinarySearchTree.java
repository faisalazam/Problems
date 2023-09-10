package com.algorithms.ctci.normal.treesandgraph;

public class MinimalBinarySearchTree {
    /**
     * Minimal Tree: Given a sorted (increasing order) array with unique integer elements,
     * write an algorithm to create a binary search tree with minimal height.
     * <p>
     * To create a tree of minimal height, we need to match the number of nodes in the left subtree to the number
     * of nodes in the right subtree as much as possible.This means that we want the root to be the middle of the array,
     * since this would mean that half the elements would be less than the root and half would be greater than it.
     * We proceed with constructing our tree in a similar fashion. The middle of each subsection of the array becomes
     * the root of the node. The left half of the array will become our left subtree, and the right half of the array
     * will become the right subtree.
     * <p>
     * One way to implement this is to use a simple root, insertNode(int v) method which inserts the value v through
     * a recursive process that starts with the root node. This wilt indeed construct a tree with minimal height but
     * it will not do so very efficiently. Each insertion will require traversing the tree, giving a total cost of
     * 0(N l o g N) to the tree.
     * Alternatively, we can cut out the extra traversals by recursively using the createMinimalBST method.
     * This method is passed just a subsection of the array and returns the root of a minimal tree for that array.
     */
    Node createMinimalBST(int[] array) {
        return createMinimalBST(array, 0, array.length - 1);
    }

    private Node createMinimalBST(int[] arr, int start, int end) {
        if (end < start) {
            return null;
        }
        int mid = (start + end) / 2;
        Node node = new Node(arr[mid]);
        node.left = createMinimalBST(arr, start, mid - 1);
        node.right = createMinimalBST(arr, mid + 1, end);
        return node;
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
