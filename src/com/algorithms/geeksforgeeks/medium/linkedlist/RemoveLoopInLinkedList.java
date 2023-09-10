package com.algorithms.geeksforgeeks.medium.linkedlist;

public class RemoveLoopInLinkedList {
    /**
     * You are given a linked list of N nodes. The task is to remove the loop from the linked list, if present.
     * <p>
     * Expected time complexity : O(n)
     * Expected auxiliary space : O(1)
     * <p>
     * https://practice.geeksforgeeks.org/problems/remove-loop-in-linked-list/1/?track=md-linkedlist&batchId=144
     */
    public static void removeTheLoop(Node head) {
        Node slow = head;
        Node fast = head;

        // Find meeting point
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast) {
                break;
            }
        }

        // Error check - there is no meeting point, and therefore no loop
        if (fast == null || fast.next == null) {
            return;
        }

		/* Move slow to Head. Keep fast at Meeting Point. Each are k steps
		/* from the Loop Start. If they move at the same pace, they must
		 * meet at Loop Start. */
        slow = head;
        while (slow != fast.next) {
            slow = slow.next;
            fast = fast.next;
        }
        fast.next = null;
    }

    public static void removeTheLoopV1(Node head) {
        Node temp = null;
        Node slow = head;
        Node fast = head;
        while (fast != null && fast.next != null) {
            temp = slow;
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast) {
                break;
            }
        }
        if (slow == fast) {
            slow = head;
            while (slow != fast) {
                slow = slow.next;
                temp = fast;
                fast = fast.next;
            }
            if (temp != null) {
                temp.next = null;
            }
        }
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
