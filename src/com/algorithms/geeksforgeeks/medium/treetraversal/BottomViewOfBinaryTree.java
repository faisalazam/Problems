package com.algorithms.geeksforgeeks.medium.treetraversal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.TreeMap;

/**
 * Given a binary tree, print the bottom view from left to right.
 * A node is included in bottom view if it can be seen when we look at the tree from bottom.
 *                       20
 *                     /   \
 *                   8      22
 *                 /   \      \
 *               5      3      25
 *                    /   \
 *                   10    14
 * For the above tree, the bottom view is 5 10 3 14 25.
 * If there are multiple bottom-most nodes for a horizontal distance from root,
 * then print the later one in level traversal. For example, in the below diagram, 3 and 4 are both the bottom
 * most nodes at horizontal distance 0, we need to print 4.
 *                       20
 *                     /    \
 *                   8       22
 *                 /  \     /  \
 *               5     3   4    25
 *                       /  \
 *                     10   14
 * For the above tree the output should be 5 10 4 14 25.
 * <p>
 * https://practice.geeksforgeeks.org/problems/bottom-view-of-binary-tree/1
 */
public class BottomViewOfBinaryTree {
    /**
     * Bottom View of a Binary Tree Using unordered_map O(n)
     * <p>
     * Time Complexity: O(N ), where N is the number of nodes in the binary tree. This is because we visit each
     * node once during the level-order traversal.
     * Auxiliary Space: O(N), We use a queue for level-order traversal, and in the worst case, the queue can contain all
     * nodes at one level. Additionally, the Map can contain at most N horizontal distances.
     */
    public ArrayList<Integer> bottomView(Node root) {
        final ArrayList<Integer> result = new ArrayList<>();
        if (root == null) {
            return result;
        }
        int leftMostIndex = Integer.MAX_VALUE;
        // Horizontal distance from the root to node data Map
        final Map<Integer, Integer> hdDataMap = new HashMap<>();
        final Queue<Node> queue = new LinkedList<>();
        root.hd = 0;
        queue.offer(root);
        while (!queue.isEmpty()) {
            final Node curr = queue.poll();
            processNode(curr, queue, hdDataMap);
            leftMostIndex = Integer.min(leftMostIndex, curr.hd);
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
    public ArrayList<Integer> bottomViewV0(Node root) {
        final ArrayList<Integer> result = new ArrayList<>();
        if (root == null) {
            return result;
        }
        // Horizontal distance from the root to node data Map
        // The map is sorted according to the natural ordering of its keys.
        // Note that this implementation is not synchronized.
        // This implementation provides guaranteed log(n) time cost for the containsKey, get, put and remove operations.
        final Map<Integer, Integer> hdDataMap = new TreeMap<>();
        final Queue<Node> queue = new LinkedList<>();
        root.hd = 0;
        queue.offer(root);
        while (!queue.isEmpty()) {
            final Node curr = queue.poll();
            processNode(curr, queue, hdDataMap);
        }
        result.addAll(hdDataMap.values());
        return result;
    }

    private static void processNode(final Node node,
                                    final Queue<Node> queue,
                                    final Map<Integer, Integer> hdDataMap) {
        final int hd = node.hd; // Horizontal distance from the root
        // here is the main difference between the BottomView and the TopView of binary tree.
        // In case of BottomView, we need to overwrite the value of 'hd' key in the hdDataMap as we traverse the tree
        // In case of Top, we DO NOT overwrite the value of 'hd' key in the hdDataMap as we traverse the tree
        hdDataMap.put(hd, node.data);
        addNode(hd - 1, node.left, queue);
        addNode(hd + 1, node.right, queue);
    }

    private static void addNode(final int hd, final Node node, final Queue<Node> queue) {
        if (node != null) {
            node.hd = hd;
            queue.offer(node);
        }
    }

    private static class Node {
        int data;
        Node left, right;
        int hd; //horizontal distance of the node

        public Node(int key) {
            data = key;
            left = right = null;
            hd = Integer.MAX_VALUE;
        }
    }
}
