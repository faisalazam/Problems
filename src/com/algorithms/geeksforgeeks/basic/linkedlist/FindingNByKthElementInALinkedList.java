package com.algorithms.geeksforgeeks.basic.linkedlist;

public class FindingNByKthElementInALinkedList {
    /**
     * Given a singly linked list and a number k. Write a function to find the (N/k)th element,
     * where N is the number of elements in the list. We need to consider ceil value in case of decimals.
     * <p>
     * https://practice.geeksforgeeks.org/problems/find-nk-th-node-in-linked-list/1
     */
    public static int nkNode(Node head, int k) {
        if (head == null) {
            return -1;
        }
        int counter = 1;
        Node slow = head;
        Node fast = head;
        while (fast.next != null) {
            fast = fast.next;
            // The variable fast updates the value on every iteration while variable slow updates when the position of
            // the node (stored and updated in ctr) is a multiple of k. This way slow contains the last node at a
            // position divisible by k when fast reaches the end of the linked list.
            slow = counter++ % k == 0 ? slow.next : slow;
        }
        return slow.data;
    }

    public static int nkNodeV0(Node head, int k) {
        Node temp = head;
        int count = getCount(head);
        int kth = (int) Math.ceil((double) count / k);
        for (int i = 1; i < kth; i++) {
            temp = temp.next;
        }
        return temp.data;
    }

    private static int getCount(Node head) {
        int count = 1;
        Node temp = head;
        while (temp != null) {
            count++;
            temp = temp.next;
        }
        return count - 1;
    }

    private class Node {
        int data;
        Node next;

        Node(int data) {
            this.data = data;
            this.next = null;
        }
    }
}
