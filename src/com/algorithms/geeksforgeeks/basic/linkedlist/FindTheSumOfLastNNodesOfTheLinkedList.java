package com.algorithms.geeksforgeeks.basic.linkedlist;

public class FindTheSumOfLastNNodesOfTheLinkedList {
    /**
     * Given a single linked list of size M, your task is to complete the function sumOfLastN_Nodes(),
     * which should return the sum of last N nodes of the linked list.
     * <p>
     * https://practice.geeksforgeeks.org/problems/find-the-sum-of-last-n-nodes-of-the-linked-list/1
     */
    public int sum(Node head, int k) {
        int sum = 0;
        Node fast = head;
        Node slow = head;
        while (slow != null) { //may be better and more readable to write it as 3 steps/loops
            if (k-- > 0 && fast != null) {
                fast = fast.next;
                continue;
            }
            if (fast != null) {
                fast = fast.next;
            } else {
                sum += slow.data;
            }
            slow = slow.next;
        }
        return sum;
    }

    public int sumV0(Node head, int k) {
        Node fast = head;
        for (int i = 0; i < k; i++) {
            fast = fast.next;
        }

        Node slow = head;
        while (fast != null) {
            slow = slow.next;
            fast = fast.next;
        }

        int sum = 0;
        while (slow != null) {
            sum += slow.data;
            slow = slow.next;
        }

        return sum;
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
