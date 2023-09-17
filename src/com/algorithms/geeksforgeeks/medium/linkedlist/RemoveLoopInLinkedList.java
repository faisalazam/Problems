package com.algorithms.geeksforgeeks.medium.linkedlist;

import java.util.HashSet;
import java.util.Set;

/**
 * You are given a linked list of N nodes. The task is to remove the loop from the linked list, if present.
 * <p>
 * Expected time complexity : O(n)
 * Expected auxiliary space : O(1)
 * <p>
 * https://practice.geeksforgeeks.org/problems/remove-loop-in-linked-list/1/?track=md-linkedlist&batchId=144
 */
public class RemoveLoopInLinkedList {
    /**
     * Time complexity : O(n)
     * Auxiliary space : O(1)
     */
    public static void removeTheLoop(final Node head) {
        if (head == null || head.next == null) {
            return;
        }

        final Node fast = detectLoop(head);
        if (fast != null) {
            removeLoop(head, fast);
        }
    }

    private static Node detectLoop(final Node head) {
        Node slow = head.next; // For some reason, I get Time Limit Exceeded if i initialize it with just head
        Node fast = head.next.next; // For some reason, I get Time Limit Exceeded if i initialize it with just head
        while (fast != null && fast.next != null) { // Detect loop
            if (slow == fast) {
                break;
            }
            slow = slow.next;
            fast = fast.next.next;
        }

        // Error check - there is no meeting point, and therefore no loop
        if (fast == null || fast.next == null) {
            return null;
        }
        return fast;
    }

    private static void removeLoop(final Node head, Node fast) {
        // After detecting the loop, if we start the slow pointer from the head and move both slow and fast pointers at
        // the same speed until fast don’t meet, they would meet at the beginning of the loop.
        // Move slow to Head. Keep fast at Meeting Point. Each are k steps from the Loop Start. If they move at the
        // same pace, they must meet at Loop Start.
        // See this for more information: https://www.geeksforgeeks.org/detect-and-remove-loop-in-a-linked-list/
        Node slow = head;
        if (slow == fast) { // That means fast and slow pointer met at first position.
            while (fast.next != slow) {
                fast = fast.next;
            }
        } else {
            while (slow.next != fast.next) {
                slow = slow.next;
                fast = fast.next;
            }
        }
        // since fast->next is the looping point
        fast.next = null; // remove loop
    }

    /**
     * Time complexity : O(n)
     * Auxiliary space : O(1)
     */
    public static void removeTheLoopV1(Node head) {
        if (head == null || head.next == null) {
            return;
        }

        Node temp = null;
        Node slow = head.next; // For some reason, I get Time Limit Exceeded if i initialize it with just head
        Node fast = head.next.next; // For some reason, I get Time Limit Exceeded if i initialize it with just head
        while (fast != null && fast.next != null) { // Detect loop
            if (slow == fast) {
                break;
            }
            temp = slow;
            slow = slow.next;
            fast = fast.next.next;
        }

        // Error check - there is no meeting point, and therefore no loop
        if (fast == null || fast.next == null) {
            return;
        }

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

    /**
     * Time Complexity: O(N), Where N is the number of nodes in the tree.
     * Auxiliary Space: O(N), Where N is the number of nodes in the tree (due to hashing).
     */
    public static void removeLoopV0(Node head) { // Results in Time Limit Exceeded
        if (head == null || head.next == null) {
            return;
        }

        final Set<Node> nodes = new HashSet<>();
        Node temp = null;

        while (head != null) {
            if (!nodes.add(head)) {
                temp.next = null; // Loop detected, break the loop
                break;
            }
            temp = head;
            head = head.next;
        }
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
