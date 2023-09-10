package com.algorithms.geeksforgeeks.basic.linkedlist;

public class InsertInMiddleOfLinkedList {
    /**
     * Given a linked list of size N and a key. The task is to insert the key in the middle of the linked list.
     * <p>
     * https://practice.geeksforgeeks.org/problems/insert-in-middle-of-linked-list/1
     */
    public Node insertInMid(Node head, int data) {
        Node newNode = new Node(data);
        if (head == null) {
            return newNode;
        } else if (head.next == null) {
            head.next = newNode;
            return head;
        }
        Node slow = head;
        Node fast = head.next;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        newNode.next = slow.next;
        slow.next = newNode;
        return head;
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
