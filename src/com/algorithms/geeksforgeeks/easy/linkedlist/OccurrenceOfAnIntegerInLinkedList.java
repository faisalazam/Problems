package com.algorithms.geeksforgeeks.easy.linkedlist;

public class OccurrenceOfAnIntegerInLinkedList {
    /**
     * Given a singly linked list and a key, count number of occurrences of given key in linked list.
     * For example, if given linked list is 1->2->1->2->1->3->1 and given key is 1, then output should be 4.
     * <p>
     * https://practice.geeksforgeeks.org/problems/occurence-of-an-integer-in-a-linked-list/1
     */
    public static int frequency(Node node, int search) {
        int count = 0;
        Node temp = node;
        while (temp != null) {
            if (temp.data == search) {
                count++;
            }
            temp = temp.next;
        }
        return count;
    }

    class Node {
        int data;
        Node next;

        Node(int d) {
            data = d;
            next = null;
        }
    }
}
