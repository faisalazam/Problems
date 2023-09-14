package com.algorithms.geeksforgeeks.easy.linkedlist;

public class ReverseALinkedList {
    /**
     * Given a linked list of N nodes. The task is to reverse this list.
     * <p>
     * Input: Head of following linked list
     * 1->2->3->4->NULL
     * Output: Linked list should be changed to,
     * 4->3->2->1->NULL
     * <p>
     * Input: Head of following linked list
     * 1->2->3->4->5->NULL
     * Output: Linked list should be changed to,
     * 5->4->3->2->1->NULL
     * <p>
     * Input: 1->NULL
     * Output: 1->NULL
     * <p>
     * User Task:
     * The task is to complete the function reverseList() with head reference as the only argument and
     * should return new head after reversing the list.
     * <p>
     * Expected Time Complexity: O(N).
     * Expected Auxiliary Space: O(1).
     * <p>
     * https://practice.geeksforgeeks.org/problems/reverse-a-linked-list/1
     */
    private Node reverseList(Node head) {
        if (head == null || head.next == null) {
            return head;
        }
        Node newHead = reverseList(head.next);
        head.next.next = head;
        head.next = null;
        return newHead;
    }

    /**
     * Time Complexity: O(n)
     */
    //Working and submitted Iterative Solution
    Node reverseIteratively(final Node head) {
        if (head == null || head.next == null) {
            return head;
        }
        Node prev = null;
        Node curr = head;
        while (curr != null) {
            final Node next = curr.next;
            curr.next = prev;
            prev = curr;
            curr = next;
        }
        return prev;
    }

    /**
     * Time Complexity: O(n)
     */
    //Working and submitted Iterative Solution
    Node reverseIterativelyV1(Node head) {
        Node newHead = null;
        while (head != null) {
            final Node next = head.next;
            head.next = newHead;
            newHead = head;
            head = next;
        }
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
