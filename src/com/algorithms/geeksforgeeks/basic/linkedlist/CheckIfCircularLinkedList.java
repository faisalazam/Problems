package com.algorithms.geeksforgeeks.basic.linkedlist;

public class CheckIfCircularLinkedList {
    /**
     * Given a singly linked list, find if the linked list is circular/cyclic or not. A linked list is called circular if
     * it not NULL terminated and all nodes are connected in the form of a cycle.
     * An empty linked list is considered as circular.
     * <p>
     * https://practice.geeksforgeeks.org/problems/circular-linked-list/1
     * <p>
     * Time Complexity: O(N).
     * Auxiliary Space: O(1).
     */
    boolean isCircular(Node head) {
        if (head == null || head.next == null) {
            return false;
        }
        Node slow = head;
        Node fast = slow.next;
        while (fast != null && fast.next != null) {
            if (slow == fast) {
                return true;
            }
            slow = slow.next;
            fast = fast.next.next;
        }
        return false;
    }

    // This might be slower than the upper one as the fast pointer used above might find it in may be around n/2...
    // well, it may not be the real solution too, because the loop does not really need to start at the head of the list
    // as the loop can be anywhere in the list
    boolean isCircular1(Node head) {
        if (head == null || head.next == null) {
            return false;
        }
        Node curr = head.next;
        while (curr != null) {
            if (curr == head) {
                return true;
            }
            curr = curr.next;
        }
        return false;
    }

    private static class Node {
        int data;
        Node next;

        Node(int d) {
            this.data = d;
            this.next = null;
        }
    }
}
