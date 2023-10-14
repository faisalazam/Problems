package com.algorithms.geeksforgeeks.easy.linkedlist;

import java.util.Stack;

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
public class ReverseALinkedList {
    /**
     * Brute Force Approach
     * The idea is to reach the last node of the linked list using recursion then start reversing the linked list.
     * <p>
     * Time Complexity: O(N), Visiting over every node one time
     * Auxiliary Space: O(N), Function call stack space, where N is the number of nodes in the linked list.
     * Here Auxiliary space is used because its using stack space for recursion.
     */
    private Node reverseList(Node head) {
    /*
            Original List => 1 => 2 => 3 => 4 => NULL

            head = 1 and head.next = 2 for 1st recursive call
            head = 2 and head.next = 3 for 2nd recursive call
            head = 3 and head.next = 4 for 3rd recursive call
            head = 4 and head.next = null for last (nth) recursive call

            Last recursive call finished and below will be executed (with the variables from (n - 1)th recursive call stack
            reversedHead = 4 => New list so far => 4 -> 3 -> null
            head = 3
            head.next.next = null => head.next.next = 3
            head.next = 4 => head.next = null

            reversedHead = 4 => New list so far => 4 -> 3 -> 2 -> null
            head = 2
            head.next.next = null => head.next.next = 2
            head.next = 3 => head.next = null

            reversedHead = 4 => New list so far => 4 -> 3 -> 2 -> 1 -> null
            head = 1
            head.next.next = null => head.next.next = 1
            head.next = 2 => head.next = null
     */
        if (head == null || head.next == null) {
            return head;
        }
        final Node reversedHead = reverseList(head.next);
        /*
            After the last recursive call returns, reversedHead is pointing at the very last node of the original list
            whereas head will be pointing at the 2nd last node of the original list. So, for the following list:
            1->2->3->4->NULL => reversedHead = 4 and head = 3 after the return of the last recursive call, and then:
            at that point,
            reversedHead.next = null and head.next.next = null and head.next = 4 (i.e. the reversedHead)
            but the statement head.next.next = head; will fix those links resulting in reversing the last two nodes: so
            head.next.next = 3
         */
        head.next.next = head; // make head.next.next to point to the node before the reversedHead (i.e. head)
        head.next = null; // break the old link by setting it to null
        return reversedHead;
    }

    /**
     * Reverse a linked list by Tail Recursive Method:
     * <p>
     * The idea is to maintain three pointers previous, current and next, recursively visit every node and make links
     * using these three pointers.
     * <p>
     * Time Complexity: O(N), Visiting every node of the linked list of size N.
     * Auxiliary Space: O(N), Function call stack space
     */
    Node reverseUsingTailRecursive(Node head) {
        return reverseUsingTailRecursive(head, null);
    }

    private Node reverseUsingTailRecursive(Node head, Node prev) {
        if (head == null) {
            return prev;
        }
        final Node next = head.next;
        head.next = prev;
        return reverseUsingTailRecursive(next, head);
    }

    /**
     * Intuition
     * The idea is to reverse the link between nodes to reverse the list. This is done by changing the connections
     * directions in reverse order. We just need to change the direction of the links using three pointers curr, prev,
     * and next.
     * <p>
     * Time Complexity: O(N) where N is the length of the linked list.
     * Space Complexity: O(1), As no any extra space is used.
     */
    Node reverseIteratively(final Node head) {
        if (head == null || head.next == null) {
            return head;
        }
        Node prev = null; // it'll be the new head
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
     * We can avoid using the extra variable 'curr' by simply using head in its place
     * <p>
     * Time Complexity: O(N) where N is the length of the linked list.
     * Space Complexity: O(1), As no any extra space is used.
     */
    Node reverseIterativelyV1(Node head) {
        Node prev = null; // it'll be the new head
        while (head != null) {
            final Node next = head.next;
            head.next = prev;
            prev = head;
            head = next;
        }
        return prev;
    }

    /**
     * Reverse a linked list using Stack:
     * The idea is to store the all the nodes in the stack then make a reverse linked list.
     * <p>
     * Time Complexity: O(N), Visiting every node of the linked list of size N.
     * Auxiliary Space: O(N), Space is used to store the nodes in the stack.
     */
    static Node reverseListUsingStack(Node head) { // It may result in Time Limit Exceeded
        final Stack<Node> stack = new Stack<>();
        Node temp = head;
        while (temp.next != null) {
            stack.add(temp);
            temp = temp.next;
        }
        head = temp;
        while (!stack.isEmpty()) {
            temp.next = stack.pop();
            temp = temp.next;
        }
        temp.next = null;
        return head;
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
