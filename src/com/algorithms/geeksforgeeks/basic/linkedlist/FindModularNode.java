package com.algorithms.geeksforgeeks.basic.linkedlist;

import org.w3c.dom.Node;

public class FindModularNode {
    /**
     * Given a singly linked list and a number K, you are required to complete the function modularNode()
     * which returns the modular node of the linked list.
     * A modular node is the last node of the linked list whose Index is divisible by the number K, i.e. i%k==0.
     * <p>
     * https://practice.geeksforgeeks.org/problems/modular-node/1
     */
    public static int modularNode(Node head, int k) {
        int result = -1;
        if (k <= 0 || head == null) {
            return result;
        }
        // One solution is to count the number of nodes and then, return -1 if k > count; otherwise the target node will
        // be int x = (count / k) * k; at 'x' position, iterate till 'x' and return node.data at 'x'.
        // Below is another solution:
        int count = 1;
        Node temp = head;
        while (temp != null) {
            result = count++ % k == 0 ? temp.data : result;
            temp = temp.next;
        }
        return result;
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
