package com.algorithms.geeksforgeeks.easy.linkedlist;

public class DetectLoopInLinkedList {
    /**
     * Given a linked list of N nodes. The task is to check if the the linked list has a loop.
     * Linked list can contain self loop.
     * <p>
     * User Task:
     * The task is to complete the function detectloop() which contains reference to the head as only argument.
     * This function should return 1 if linked list contains loop, else return 0.
     * <p>
     * Challenge : Try to solve the problem with constant space and Linear time complexity.
     * <p>
     * https://practice.geeksforgeeks.org/problems/detect-loop-in-linked-list/1
     * <p>
     * By using Floyd’s Cycle-Finding Algorithm: This algorithm is used to find a loop in a linked list. It uses two
     * pointers one moving twice as fast as the other one. The faster one is called the faster pointer and the other
     * one is called the slow pointer, if both pointers meet then the cycle is present otherwise not.
     * <p>
     * Time Complexity: O(N)
     * Auxiliary Space: O(1)
     */
    public boolean detectLoop(Node head) {
        if (head == null || head.next == null) {
            return false;
        }
        Node slow = head;
        Node fast = head.next;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast) {
                return true;
            }
        }
        return false;
    }

    static class Node {
        int data;
        Node next;

        Node(int d) {
            data = d;
            next = null;
        }
    }
}
