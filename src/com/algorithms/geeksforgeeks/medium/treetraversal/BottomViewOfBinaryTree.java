package com.algorithms.geeksforgeeks.medium.treetraversal;

import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.TreeMap;

public class BottomViewOfBinaryTree {
    /**
     * Given a binary tree, print the bottom view from left to right.
     * A node is included in bottom view if it can be seen when we look at the tree from bottom.
     *
     *                       20
     *                     /    \
     *                   8       22
     *                 /   \        \
     *               5      3       25
     *                     /   \
     *                   10    14
     *
     * For the above tree, the bottom view is 5 10 3 14 25.
     * If there are multiple bottom-most nodes for a horizontal distance from root,
     * then print the later one in level traversal. For example, in the below diagram, 3 and 4 are both the bottom
     * most nodes at horizontal distance 0, we need to print 4.
     *
     *                       20
     *                     /    \
     *                   8       22
     *                 /   \     /   \
     *               5      3 4     25
     *                      /    \
     *                  10       14
     *
     * For the above tree the output should be 5 10 4 14 25.
     *
     * https://practice.geeksforgeeks.org/problems/bottom-view-of-binary-tree/1
     */
    public void bottomView(Node root) {
        if (root == null) {
            return;
        }
        root.hd = 0;
        Map<Integer, Integer> map = new TreeMap<>();
        Queue<Node> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            Node curr = queue.poll();
            int hd = curr.hd;
            map.put(curr.hd, curr.data);

            Node currLeft = curr.left;
            Node currRight = curr.right;
            if (currLeft != null) {
                currLeft.hd = hd - 1;
                queue.offer(currLeft);
            }
            if (currRight != null) {
                currRight.hd = hd + 1;
                queue.offer(currRight);
            }
        }
        StringBuilder builder = new StringBuilder();
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            builder.append(entry.getValue()).append(" ");
        }
        System.out.print(builder.toString());
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
