package com.algorithms.geeksforgeeks.easy.linkedlist;

public class RotateALinkedList {
    /**
     * Given a singly linked list of size N. The task is to rotate the linked list counter-clockwise by k nodes,
     * where k is a given positive integer smaller than or equal to length of the linked list.
     * <p>
     * User Task:
     * The task is to complete the function rotate() which takes a head reference as the first argument and
     * k as the second argument. The printing is done automatically by the driver code.
     * <p>
     * Expected Time Complexity: O(N).
     * Expected Auxiliary Space: O(1).
     * <p>
     * After rotating the linked list by k elements (anti-clockwise), we reached to node k+1, which is (k+1)th node
     * from beginning, now becomes head and tail of the linked list is joined to the previous head.
     * <p>
     * https://practice.geeksforgeeks.org/problems/rotate-a-linked-list/1
     */
    public Node rotate(Node head, int k) {
        if (head == null || head.next == null) {
            return null;
        }
        Node tail = head;
        for (int i = 1; i < k; i++) {
            tail = tail.next;
            if (tail == null) {
                k = k % i;
                if (k == 0) {
                    return head;
                }
                i = 0;
                tail = head;
            }
        }
        if (tail == null || tail.next == null) {
            return head;
        }
        Node newHead = tail.next;
        tail.next = null;
        tail = newHead;
        while (tail.next != null) {
            tail = tail.next;
        }
        tail.next = head;
        return newHead;
    }

    private class Node {
        int data;
        Node next;

        Node(int d) {
            this.data = d;
            this.next = null;
        }
    }
}
