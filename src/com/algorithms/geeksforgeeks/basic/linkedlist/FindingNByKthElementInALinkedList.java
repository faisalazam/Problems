package com.algorithms.geeksforgeeks.basic.linkedlist;

public class FindingNByKthElementInALinkedList {
    /**
     * Given a singly linked list and a number k. Write a function to find the (N/k)th element,
     * where N is the number of elements in the list. We need to consider ceil value in case of decimals.
     * <p>
     * https://practice.geeksforgeeks.org/problems/find-nk-th-node-in-linked-list/1
     */
    public static int nkNode(Node head, int k) {
        if (k <= 0 || head == null) {
            return -1;
        }
        int counter = 1;
        Node slow = head;
        Node fast = head;
        while (fast.next != null) {
            fast = fast.next;
            // The fast pointer gets updated on every iteration while the slow pointer get updated only when the position
            // of the node (stored and updated in ctr) is a multiple of k. This way slow contains the last node at a
            // position divisible by k when fast reaches the end of the linked list.
            slow = counter++ % k == 0 ? slow.next : slow;
        }
        return slow.data;
    }

    /**
     * Same as the {@link #nkNode(Node, int)} but just a different style of writing
     * Time Complexity: O(n)
     * Space complexity: O(1)
     */
    public static int nkNodeV1(Node head, int k) {
        if (k <= 0 || head == null) {
            return -1;
        }
        Node fractionalNode = null;

        int counter = 0;
        for (Node temp = head; temp != null; temp = temp.next) {
            // For every k nodes, we move fractionalNode one step ahead.
            if (counter % k == 0) {
                // First time we see a multiple of k
                if (fractionalNode == null) {
                    fractionalNode = head;
                } else {
                    fractionalNode = fractionalNode.next;
                }
            }
            counter++;
        }
        return fractionalNode.data;
    }

    public static int nkNodeV0(Node head, int k) {
        if (k <= 0 || head == null) {
            return -1;
        }
        Node temp = head;
        int count = getCount(head);
        int kth = (int) Math.ceil((double) count / k);
        for (int i = 1; i < kth; i++) {
            temp = temp.next;
        }
        return temp.data;
    }

    private static int getCount(Node head) {
        int count = 0;
        Node temp = head;
        while (temp != null) {
            count++;
            temp = temp.next;
        }
        return count;
    }

    private static class Node {
        int data;
        Node next;

        Node(int data) {
            this.data = data;
            this.next = null;
        }
    }
}
