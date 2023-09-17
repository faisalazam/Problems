package com.algorithms.geeksforgeeks.medium.treetraversal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.TreeMap;

/**
 * Given below is a binary tree. The task is to print the top view of binary tree.
 * Top view of a binary tree is the set of nodes visible when the tree is viewed from the top.
 * For the given below tree:
 *        1
 *     /     \
 *    2       3
 *  /  \    /   \
 * 4    5  6     7
 * <p>
 * Top view will be: 4 2 1 3 7
 * Note: Print from leftmost node to rightmost node.
 * <p>
 * https://practice.geeksforgeeks.org/problems/top-view-of-binary-tree/1
 * <p>
 */
public class TopViewOfBinaryTree {
    /**
     * Top View of a Binary Tree Using unordered_map O(n)
     * <p>
     * Time Complexity: O(N ), where N is the number of nodes in the binary tree. This is because we visit each
     * node once during the level-order traversal.
     * Auxiliary Space: O(N), We use a queue for level-order traversal, and in the worst case, the queue can contain all
     * nodes at one level. Additionally, the Map can contain at most N horizontal distances.
     */
    private static ArrayList<Integer> topView(Node root) {
        final ArrayList<Integer> result = new ArrayList<>();
        if (root == null) {
            return result;
        }

        int leftMostIndex = Integer.MAX_VALUE;
        // Horizontal distance from the root to node data Map
        final Map<Integer, Integer> hdDataMap = new HashMap<>();
        final Queue<NodeDistancePair> queue = new LinkedList<>();
        queue.offer(new NodeDistancePair(0, root));

        while (!queue.isEmpty()) {
            final NodeDistancePair currNodeDistancePair = queue.poll();
            processNode(currNodeDistancePair, queue, hdDataMap);
            leftMostIndex = Integer.min(leftMostIndex, currNodeDistancePair.hd);
        }

        // Traverse each value in hash from leftmost to positive side till key is available
        while (hdDataMap.containsKey(leftMostIndex)) {
            result.add(hdDataMap.get(leftMostIndex++));
        }
        return result;
    }

    /**
     * Time Complexity: O(N * log(N)), where N is the number of nodes in the binary tree. This is because we visit each
     * node once during the level-order traversal and sorting and inserting an element into the map will take O(log N) time.
     * Auxiliary Space: O(N), We use a queue for level-order traversal, and in the worst case, the queue can contain all
     * nodes at one level. Additionally, the TreeMap can contain at most N horizontal distances.
     */
    private static ArrayList<Integer> topViewVo(Node root) {
        final ArrayList<Integer> result = new ArrayList<>();
        if (root == null) {
            return result;
        }

        // Horizontal distance from the root to node data Map
        // The map is sorted according to the natural ordering of its keys.
        // Note that this implementation is not synchronized.
        // This implementation provides guaranteed log(n) time cost for the containsKey, get, put and remove operations.
        final Map<Integer, Integer> hdDataMap = new TreeMap<>();
        final Queue<NodeDistancePair> queue = new LinkedList<>();
        queue.offer(new NodeDistancePair(0, root));

        while (!queue.isEmpty()) {
            final NodeDistancePair currNodeDistancePair = queue.poll();
            processNode(currNodeDistancePair, queue, hdDataMap);
        }

        result.addAll(hdDataMap.values());
        return result;
    }

    private static void processNode(NodeDistancePair nodeDistancePair,
                                    final Queue<NodeDistancePair> queue,
                                    final Map<Integer, Integer> hdDataMap) {
        final int hd = nodeDistancePair.hd; // Horizontal distance from the root
        final Node node = nodeDistancePair.node;
        // here is the main difference between the BottomView and the TopView of binary tree.
        // In case of BottomView, we need to overwrite the value of 'hd' key in the hdDataMap as we traverse the tree
        // In case of Top, we DO NOT overwrite the value of 'hd' key in the hdDataMap as we traverse the tree
        hdDataMap.putIfAbsent(hd, node.data);
        addNode(hd - 1, node.left, queue);
        addNode(hd + 1, node.right, queue);
    }

    private static void addNode(final int hd, final Node node, final Queue<NodeDistancePair> queue) {
        if (node != null) {
            queue.offer(new NodeDistancePair(hd, node));
        }
    }

    private static class Node {
        int data;
        Node left, right;

        public Node(int key) {
            data = key;
            left = right = null;
        }
    }

    private static class NodeDistancePair {
        int hd;  // Horizontal distance from the root
        Node node;

        NodeDistancePair(int hd, Node node) {
            this.hd = hd;
            this.node = node;
        }
    }
}
