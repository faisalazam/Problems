package com.algorithms.geeksforgeeks.basic.linkedlist;

public class FindingMiddleElementInALinkedList {
    /**
     * Given a singly linked list of N nodes. The task is to find the middle of the linked list.
     * For example, if given linked list is 1->2->3->4->5 then the output should be 3.
     * If there are even nodes, then there would be two middle nodes, we need to print the second middle element.
     * For example, if given linked list is 1->2->3->4->5->6 then the output should be 4.
     * <p>
     * User Task:
     * The task is to complete the function getMiddle() which takes a head reference as the only argument
     * and should return the data at the middle node of the linked list.
     * <p>
     * Expected Time Complexity: O(N).
     * Expected Auxiliary Space: O(1).
     * <p>
     * https://practice.geeksforgeeks.org/problems/finding-middle-element-in-a-linked-list/1
     */
    int getMiddle(Node head) {
        if (head == null) {
            return -1;
        }

        Node slow = head;
        Node fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow.data;
    }

    int getMiddleV1(Node head) {
        int count = 0;
        Node temp = head;
        while (temp != null) {
            count++;
            temp = temp.next;
        }
        temp = head;
        int middleElementIndex = count / 2;
        while (middleElementIndex > 0) {
            temp = temp.next;
            middleElementIndex--;
        }
        return temp.data;
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
