package com.algorithms.geeksforgeeks.basic.linkedlist;

import java.util.ArrayList;
import java.util.List;

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
     * Time Complexity: O(N).
     * Auxiliary Space: O(1).
     * <p>
     * https://practice.geeksforgeeks.org/problems/finding-middle-element-in-a-linked-list/1
     * <p>
     * "hare and tortoise" or "tortoise and hare" or "slow and fast" pointers approach.
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
        int middleElementIndex = (count % 2 == 0) ? (count + 1) / 2 : (count / 2);
        while (middleElementIndex > 0) {
            temp = temp.next;
            middleElementIndex--;
        }
        return temp.data;
    }

    /**
     * Time Complexity: O(N).
     * Auxiliary Space: O(N).
     */
    int getMiddleV0(Node head) {
        Node temp = head;
        final List<Integer> list = new ArrayList<>();
        while (temp != null) {
            list.add(temp.data);
            temp = temp.next;
        }
        final int count = list.size();
        final int middleElementIndex = (count % 2 == 0) ? (count + 1) / 2 : (count / 2);
        return list.get(middleElementIndex);
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
