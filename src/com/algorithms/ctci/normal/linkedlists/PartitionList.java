package com.algorithms.ctci.normal.linkedlists;

public class PartitionList {
    /**
     * Partition; Write code to partition a linked list around a value x, such that all nodes less than x come
     * before all nodes greater than or equal to x. If x is contained within the list,
     * the values of x only need to be after the elements less than x (see below).
     * The partition element x can appear anywhere in the "right partition";
     * it does not need to appear between the left and right partitions.
     */
    public static LinkedListNode partitionV1(LinkedListNode node, int x) {
        LinkedListNode beforeStart = null;
        LinkedListNode beforeEnd = null;
        LinkedListNode afterStart = null;
        LinkedListNode afterEnd = null;

        /* Partition list */
        while (node != null) {
            LinkedListNode next = node.next;
            node.next = null;
            if (node.data < x) {
                if (beforeStart == null) {
                    beforeStart = node;
                    beforeEnd = node;
                } else {
                    beforeEnd.next = node;
                    beforeEnd = node;
                }
            } else {
                if (afterStart == null) {
                    afterStart = node;
                    afterEnd = node;
                } else {
                    afterEnd.next = node;
                    afterEnd = node;
                }
            }
            node = next;
        }

        /* Merge before list and after list */
        if (beforeStart == null) {
            return afterStart;
        }

        beforeEnd.next = afterStart;
        return beforeStart;
    }

    /**
     * Similar solution as V1 but with just two pointers
     */
    public static LinkedListNode partitionV2(LinkedListNode node, int x) {
        LinkedListNode beforeStart = null;
        LinkedListNode afterStart = null;

        /* Partition list */
        while (node != null) {
            LinkedListNode next = node.next;
            if (node.data < x) {
                /* Insert node into start of before list */
                node.next = beforeStart;
                beforeStart = node;
            } else {
                /* Insert node into front of after list */
                node.next = afterStart;
                afterStart = node;
            }
            node = next;
        }

        /* Merge before list and after list */
        if (beforeStart == null) {
            return afterStart;
        }

        LinkedListNode head = beforeStart;
        while (beforeStart.next != null) {
            beforeStart = beforeStart.next;
        }
        beforeStart.next = afterStart;
        return head;
    }

    /**
     * If we don't care about making the elements of the list "stable" (which there's no obligation to,
     * since the interviewer hasn't specified that), then we can instead rearrange the elements
     * by growing the list at the head and tail.
     */
    public static LinkedListNode partitionV3(LinkedListNode node, int x) {
        LinkedListNode head = node;
        LinkedListNode tail = node;

        /* Partition list */
        while (node != null) {
            LinkedListNode next = node.next;
            if (node.data < x) {
                /* Insert node at head. */
                node.next = head;
                head = node;
            } else {
                /* Insert node at tail. */
                tail.next = node;
                tail = node;
            }
            node = next;
        }
        tail.next = null;

        return head;
    }

    private static class LinkedListNode {
        int data;
        LinkedListNode next;
    }
}
