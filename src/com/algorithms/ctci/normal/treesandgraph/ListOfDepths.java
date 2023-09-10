package com.algorithms.ctci.normal.treesandgraph;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class ListOfDepths {
    /**
     * List of Depths: Given a binary tree, design an algorithm which creates a linked list of all the nodes at
     * each depth (e.g., if you have a tree with depth D, you'lf have D linked lists).
     *
     * O(N)
     */
    public static List<LinkedList<Node>> createLevelLinkedList(Node root) { //BFS
        List<LinkedList<Node>> result = new ArrayList<>();

        LinkedList<Node> current = new LinkedList<>();
        if (root != null) {
            current.offer(root);
        }

        while (current.size() > 0) {
            result.add(current); // Add previous level
            Queue<Node> parents = current; // Go to next level
            current = new LinkedList<>();
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
        List<LinkedList<Node>> lists = new ArrayList<>();
        createLevelLinkedList(root, lists, 0);
        return lists;
    }

    private static void createLevelLinkedList(Node root, List<LinkedList<Node>> lists, int level) {
        if (root == null) {
            return;
        }
        LinkedList<Node> list = getList(level, lists);
        list.add(root);
        createLevelLinkedList(root.left, lists, level + 1);
        createLevelLinkedList(root.right, lists, level + 1);
    }

    private static LinkedList<Node> getList(int level, List<LinkedList<Node>> lists) {
        LinkedList<Node> list;
        if (lists.size() == level) { // Level not contained in list
            list = new LinkedList<>();
            /* Levels are always traversed in order. So, if this is the first time we've visited level i,
             * we must have seen levels 0 through i - 1. We can therefore safely add the level at the end. */
            lists.add(list);
        } else {
            list = lists.get(level);
        }
        return list;
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
