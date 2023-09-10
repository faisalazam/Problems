package com.algorithms.geeksforgeeks.easy.linkedlist;

public class NthNodeFromEndOfLinkedList {
    /**
     * Given a linked list consisting of L nodes and given a number N. The task is to find
     * the Nth node from the end of the linked list.
     * <p>
     * User Task:
     * The task is to complete the function getNthFromLast() which takes two arguments:
     * reference to head and N and you need to return Nth from the end.
     * <p>
     * Expected Time Complexity: O(N).
     * Expected Auxiliary Space: O(1).
     * <p>
     * https://practice.geeksforgeeks.org/problems/nth-node-from-end-of-linked-list/1
     */
    int getNthFromLast(Node head, int n) {
        if (head == null) {
            return -1;
        }
        Node start = head;
        Node end = head;
        for (int i = 0; i < n; i++) {
            if (end == null) {
                return -1;
            }
            end = end.next;
        }
        while (end != null) {
            start = start.next;
            end = end.next;
        }
        return start.data;
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
