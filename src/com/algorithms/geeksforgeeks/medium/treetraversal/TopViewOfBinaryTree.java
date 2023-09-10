package com.algorithms.geeksforgeeks.medium.treetraversal;

import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.TreeMap;

public class TopViewOfBinaryTree {
    /**
     * Given below is a binary tree. The task is to print the top view of binary tree.
     * Top view of a binary tree is the set of nodes visible when the tree is viewed from the top. For the given below tree
     * <p>
     * 1
     * /     \
     * 2       3
     * /  \    /   \
     * 4    5  6   7
     * <p>
     * Top view will be: 4 2 1 3 7
     * Note: Print from leftmost node to rightmost node.
     * <p>
     * https://practice.geeksforgeeks.org/problems/top-view-of-binary-tree/1
     */
    private static void topView(Node root) {
        if (root == null) {
            return;
        }
        Map<Integer, Integer> map = new TreeMap<>();
        Queue<Object[]> queue = new LinkedList<>();
        queue.offer(new Object[]{0, root});
        while (!queue.isEmpty()) {
            Object[] currArray = queue.poll();
            int hd = (Integer) currArray[0];
            Node curr = (Node) currArray[1];

            if (!map.containsKey(hd)) {
                map.put(hd, curr.data);
            }
            if (curr.left != null) {
                queue.offer(new Object[]{hd - 1, curr.left});
            }
            if (curr.right != null) {
                queue.offer(new Object[]{hd + 1, curr.right});
            }
        }
        StringBuilder builder = new StringBuilder();
        for (Map.Entry entry : map.entrySet()) {
            builder.append(entry.getValue()).append(" ");
        }
        System.out.print(builder.toString());
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
