package com.algorithms.geeksforgeeks.medium.treetraversal;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.TreeMap;

public class TopViewOfBinaryTree {
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
     * Time Complexity: O(NlogN)
     * Auxiliary Space: O(N).
     */
    private static ArrayList<Integer> topView(Node root) {
        final ArrayList<Integer> result = new ArrayList<>();
        if (root == null) {
            return result;
        }

        // Horizontal distance from the root to node data Map
        // The map is sorted according to the natural ordering of its keys.
        // Note that this implementation is not synchronized.
        // This implementation provides guaranteed log(n) time cost for the containsKey, get, put and remove operations.
        final Map<Integer, Integer> hdDataMap = new TreeMap<>();
        final Queue<Object[]> queue = new LinkedList<>();
        queue.offer(new Object[]{0, root});

        while (!queue.isEmpty()) {
            final Object[] currNodeWithHD = queue.poll();
            processNode(currNodeWithHD, queue, hdDataMap);
        }

        result.addAll(hdDataMap.values());
        return result;
    }

    private static void processNode(Object[] nodeWithHD,
                                    final Queue<Object[]> queue,
                                    final Map<Integer, Integer> hdDataMap) {
        final Integer hd = (Integer) nodeWithHD[0]; // Horizontal distance from the root
        final Node node = (Node) nodeWithHD[1];
        hdDataMap.putIfAbsent(hd, node.data);
        addNode(hd - 1, node.left, queue);
        addNode(hd + 1, node.right, queue);
    }

    private static void addNode(final Integer hd, final Node node, final Queue<Object[]> queue) {
        if (node != null) {
            queue.offer(new Object[]{hd, node});
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
}
