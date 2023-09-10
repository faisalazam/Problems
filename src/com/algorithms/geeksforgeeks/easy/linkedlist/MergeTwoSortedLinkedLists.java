package com.algorithms.geeksforgeeks.easy.linkedlist;

public class MergeTwoSortedLinkedLists {
    /**
     * Given two sorted linked lists consisting of N and M nodes respectively.
     * The task is to merge both of the list (in-place) and return head of the merged list.
     * Note: It is strongly recommended to do merging in-place using O(1) extra space.
     * <p>
     * User Task:
     * The task is to complete the function sortedMerge() which takes references to the heads of two linked lists
     * as the arguments and returns the head of merged linked list.
     * <p>
     * Expected Time Complexity : O(n+m)
     * Expected Auxilliary Space : O(1)
     * <p>
     * https://practice.geeksforgeeks.org/problems/merge-two-sorted-linked-lists/1
     */
    Node sortedMerge(Node headA, Node headB) {
        if (headA == null) {
            return headB;
        } else if (headB == null) {
            return headA;
        }
        Node head = null;
        Node temp = new Node(-1);
        while (headA != null && headB != null) {
            if (headA.data <= headB.data) {
                temp.next = headA;
                headA = headA.next;
            } else {
                temp.next = headB;
                headB = headB.next;
            }
            temp = temp.next;
            if (head == null) {
                head = temp;
            }
        }
        if (headA != null) {
            temp.next = headA;
        }
        if (headB != null) {
            temp.next = headB;
        }
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
