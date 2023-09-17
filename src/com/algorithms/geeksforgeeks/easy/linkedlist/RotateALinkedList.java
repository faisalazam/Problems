package com.algorithms.geeksforgeeks.easy.linkedlist;

/**
 * Given a singly linked list of size N. The task is to rotate the linked list counter-clockwise by k nodes,
 * where k is a given positive integer smaller than or equal to length of the linked list.
 * <p>
 * Given a singly linked list of size N. The task is to left-shift the linked list by k nodes, where k is a given
 * positive integer smaller than or equal to length of the linked list.
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
public class RotateALinkedList {
    /**
     * Intuition
     * Find Kth node in the list and do necessary pointer changes in the list making kth node the last node and (k+1)
     * node the new head.
     * <p>
     * To rotate the linked list, we need to change the next pointer of kth node to NULL, the next pointer of the last
     * node should point to previous head node, and finally, change the head to (k+1)th node.
     * <p>
     * Time Complexity : O(N), N is number of nodes in the linked list.
     * Space Complexity : O(1), no extra space is used.
     */
    public Node rotate(Node head, int k) {
        if (head == null || head.next == null) {
            return null;
        }
        Node walk = head;
        while (walk != null && k-- > 1) {
            walk = walk.next;
        } // walk will point at kth node at the end of this loop
        if (walk == null) { // It'll happen in case of k >= n
            return head;
        }
        final Node kthNode = walk; // pointing at kth node of the list
        while (walk.next != null) { // traverse from kth node to the end of the list
            walk = walk.next;
        } // walk will point at last node of the original list at the end of this loop
        //connecting last node of old list to old head.
        walk.next = head; // connect the rotated part of the list at the end of the non-rotated part of the list
        head = kthNode.next; // (k+1)th node is the new head of the list
        // since kthNode points to the node placed before new head, so it is new tail or the last node of new list, so
        // we store null in its link part.
        kthNode.next = null; // break the link between first part and second part of the list
        return head;
    }

    public Node rotateV0(Node head, int k) {
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
        if (tail.next == null) {
            return head;
        }
        final Node newHead = tail.next;
        tail.next = null;
        tail = newHead;
        while (tail.next != null) {
            tail = tail.next;
        }
        tail.next = head;
        return newHead;
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
