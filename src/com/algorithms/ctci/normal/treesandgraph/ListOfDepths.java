package com.algorithms.ctci.normal.treesandgraph;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class ListOfDepths {
    /**
     * List of Depths: Given a binary tree, design an algorithm which creates a linked list of all the nodes at
     * each depth (e.g., if you have a tree with depth D, you'll have D linked lists).
     * <p>
     * O(N)
     */
    public static List<LinkedList<Node>> createLevelLinkedList(Node root) { //BFS
        final List<LinkedList<Node>> result = new ArrayList<>();

        LinkedList<Node> current = new LinkedList<>(); // It'll contain nodes at one level at a time
        if (root != null) {
            current.offer(root);
        }

        while (current.size() > 0) {
            result.add(current); // Add previous level
            final Queue<Node> parents = current; // Go to next level
            current = new LinkedList<>();
            // traverse parents to populate 'current' with their children which will form the next level
            for (Node parent : parents) {
                if (parent.left != null) {
                    current.offer(parent.left);
                }
                if (parent.right != null) {
                    current.offer(parent.right);
                }
            }
        }
        return result;
    }

    /**
     * O(N)
     */
    public static List<LinkedList<Node>> createLevelLinkedListDFS(Node root) {
        final List<LinkedList<Node>> result = new ArrayList<>();
        createLevelLinkedList(root, result, 0);
        return result;
    }

    private static void createLevelLinkedList(final Node root, final List<LinkedList<Node>> result, final int level) {
        if (root == null) {
            return;
        }
        final LinkedList<Node> list = getList(level, result);
        list.add(root);
        createLevelLinkedList(root.left, result, level + 1);
        createLevelLinkedList(root.right, result, level + 1);
    }

    private static LinkedList<Node> getList(final int level, final List<LinkedList<Node>> result) {
        final LinkedList<Node> list;
        if (result.size() == level) { // Level not contained in list
            list = new LinkedList<>();
            /* Levels are always traversed in order. So, if this is the first time we've visited level i,
             * we must have seen levels 0 through i - 1. We can therefore safely add the level at the end. */
            result.add(list);
        } else {
            list = result.get(level);
        }
        return list;
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
