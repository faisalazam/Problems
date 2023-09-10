package com.algorithms.geeksforgeeks.medium.linkedlist;

public class ReverseLinkedListInGroupsOfGivenSize {
    /**
     * Given a linked list of size N. The task is to reverse every k nodes
     * (where k is an input to the function) in the linked list.
     * <p>
     * User Task:
     * The task is to complete the function reverse() which should reverse the linked list in group of size k.
     * <p>
     * Expected Time Complexity : O(n)
     * Expected Auxilliary Space : O(1)
     * <p>
     * https://practice.geeksforgeeks.org/problems/reverse-a-linked-list-in-groups-of-given-size/1
     */
    public static Node reverseInKGroups(Node node, int k) {
        Node prev = null;
        Node tail = node;
        int count = getCount(node);
        Node newHead = reverseInKGroups(node, prev, tail, k);
        while (newHead != null && count > 0) {
            prev = tail;
            if (tail != null) {
                tail = tail.next;
            }
            prev.next = reverseInKGroups(tail, prev, tail, k);
            count = count - k;
        }
        return newHead;
    }

    //1 2 3 4 5 6 7 8 9
    //3 2 1
    //      6 5 4
    //            9 8 7
    private static Node reverseInKGroups(Node currHead, Node prev, Node tail, int k) {
        if (currHead == null || currHead.next == null) {
            return currHead;
        }
        Node curr = currHead;
        int groupSize = k;
        while (curr != null && groupSize > 0) {
            Node next = curr.next;
            curr.next = prev;
            prev = curr;
            curr = next;
            groupSize--;
        }
        tail.next = curr;
        return prev;
    }

    private static int getCount(Node node) {
        int count = 0;
        Node temp = node;
        while (temp != null) {
            count++;
            temp = temp.next;
        }
        return count;
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
