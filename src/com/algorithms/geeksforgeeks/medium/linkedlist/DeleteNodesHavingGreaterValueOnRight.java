package com.algorithms.geeksforgeeks.medium.linkedlist;

import java.util.Stack;

public class DeleteNodesHavingGreaterValueOnRight {
    /**
     * Given a singly linked list, remove all the nodes which have a greater value on its next adjacent element.
     * <p>
     * https://practice.geeksforgeeks.org/problems/delete-nodes-having-greater-value-on-right/1
     */
    void compute(LinkedList l) {
        if (l == null) {
            return;
        }
        Stack<Node> stack = new Stack<>();
        Node temp = l.head;
        stack.push(temp);
        while (temp != null && temp.next != null) {
            Node next = temp.next;
            temp.next = null;
            temp = next;
            if (temp.data < stack.peek().data) {
                stack.push(temp);
            } else {
                while (!stack.isEmpty() && temp.data > stack.peek().data) {
                    stack.pop();
                }
                stack.push(temp);
            }
        }
        Node head = new Node(-1);
        while (!stack.isEmpty()) {
            Node curr = stack.pop();
            Node next = head.next;
            head.next = curr;
            curr.next = next;
        }
        l.head = head.next;
        head = null;
    }

    private class Node {
        int data;
        Node next;

        Node(int data) {
            this.data = data;
        }
    }

    private class LinkedList {
        Node head;
    }
}
