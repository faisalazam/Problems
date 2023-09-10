package com.algorithms.geeksforgeeks.basic.linkedlist;

public class FindModularNode {
    /**
     * Given a singly linked list and a number K, you are required to complete the function modularNode()
     * which returns the modular node of the linked list.
     * A modular node is the last node of the linked list whose Index is divisible by the number K, i.e. i%k==0.
     * <p>
     * https://practice.geeksforgeeks.org/problems/modular-node/1
     */
    public static int modularNode(Node head, int k) {
        int count = 1;
        int result = -1;
        Node temp = head;
        while (temp != null) {
            result = count++ % k == 0 ? temp.data : result;
            temp = temp.next;
        }
        return result;
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
