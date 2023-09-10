package com.algorithms.ctci.normal.linkedlists;

public class SumLists {
    /**
     * Sum Lists: You have two numbers represented by a linked list, where each node contains a single digit.
     * The digits are stored in reverse order, such that the 1's digit is at the head of the list.
     * Write a function that adds the two numbers and returns the sum as a linked list.
     * <p>
     * EXAMPLE
     * Input; (7-> 1 -> 6) + (5 -> 9 -> 2). That is, 617 + 295.
     * Output; 2 -> 1 -> 9. That is, 912.
     * <p>
     * In implementing this code, we must be careful to handle the condition when one linked list is shorter than another.
     * We don't want to get a null pointer exception.
     *
     * https://practice.geeksforgeeks.org/problems/add-two-numbers-represented-by-linked-lists/1/?track=md-linkedlist&batchId=144
     */
//    private static LinkedListNode addLists(LinkedListNode l1, LinkedListNode l2) {
//        return addLists(l1, l2, 0);
//    }
    private static LinkedListNode addLists(LinkedListNode l1, LinkedListNode l2, int carry) {
        if (l1 == null && l2 == null && carry == 0) {
            return null;
        }

        int value = calculateValue(l1, l2, carry);
        LinkedListNode result = new LinkedListNode(value % 10);
        if (l1 != null || l2 != null) {
            result.next = addLists(
                    l1 == null ? null : l1.next,
                    l2 == null ? null : l2.next,
                    value >= 10 ? 1 : 0
            );
        }
        return result;
    }

    private static int calculateValue(LinkedListNode l1, LinkedListNode l2, int carry) {
        int value = carry;
        if (l1 != null) {
            value += l1.data;
        }
        if (l2 != null) {
            value += l2.data;
        }
        return value;
    }

    /**
     * FOLLOW UP
     * Suppose the digits are stored in forward order. Repeat the above problem.
     * Input(6 -> 1 -> 7) + (2 -> 9 -> S). That is, 617 + 295.
     * Output: 9 -> 1 -> 2. That is, 912.
     */
    private static LinkedListNode addLists(LinkedListNode l1, LinkedListNode l2) {
        int len1 = length(l1);
        int len2 = length(l2);
        if (len1 < len2) {
            l1 = padList(l1, len2 - len1);
        } else {
            l2 = padList(l2, len1 - len2);
        }
        PartialSum partialSum = addListsHelper(l1, l2);
        if (partialSum.carry == 0) {
            return partialSum.sum;
        } else {
            return insertBefore(partialSum.sum, partialSum.carry);
        }
    }

    private static int length(LinkedListNode l) {
        if (l == null) {
            return 0;
        } else {
            return 1 + length(l.next);
        }
    }

    private static PartialSum addListsHelper(LinkedListNode l1, LinkedListNode l2) {
        if (l1 == null && l2 == null) {
            return new PartialSum();
        }
        PartialSum partialSum = addListsHelper(l1.next, l2.next);
        int val = partialSum.carry + l1.data + l2.data;
        partialSum.sum = insertBefore(partialSum.sum, val % 10);
        partialSum.carry = val / 10;
        return partialSum;
    }

    private static LinkedListNode padList(LinkedListNode l, int padding) {
        LinkedListNode head = l;
        for (int i = 0; i < padding; i++) {
            LinkedListNode n = new LinkedListNode(0);
            head.prev = n;
            n.next = head;
            head = n;
        }
        return head;
    }

    private static LinkedListNode insertBefore(LinkedListNode list, int data) {
        LinkedListNode node = new LinkedListNode(data);
        if (list != null) {
            list.prev = node;
            node.next = list;
        }
        return node;
    }

    private static class LinkedListNode {
        int data;
        LinkedListNode next;
        LinkedListNode prev;

        LinkedListNode(int data) {
            this.data = data;
        }
    }

    private static class PartialSum {
        LinkedListNode sum = null;
        int carry = 0;
    }
}
