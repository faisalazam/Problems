package com.algorithms.ctci.normal.linkedlists;

public class IntersectionPointInYShappedLinkedLists {
    /**
     * Intersection: Given two (singly) linked lists, determine if the two lists intersect.
     * Return the intersecting node. Note that the intersection is defined based on reference, not value.
     * That is, if the kth node of the first linked list is the exact same node (by reference) as the jth node
     * of the second linked list, then they are intersecting.
     * <p>
     * This algorithm takes 0(A + B) time, where A and B are the lengths of the two linked lists.
     * It takes 0(1) additional space.
     *
     * https://practice.geeksforgeeks.org/problems/intersection-point-in-y-shapped-linked-lists/1/?track=md-linkedlist&batchId=144
     */
    public static Node findIntersection(Node list1, Node list2) {
        if (list1 == null || list2 == null) {
            return null;
        }
        // Get tail and sizes.
        Result result1 = getTailAndSize(list1);
        Result result2 = getTailAndSize(list2);

        // If different tail nodes, then there's no intersection.
        if (result1.tail != result2.tail) {
            return null;
        }
        Node shorter = result1.size < result2.size ? list1 : list2;
        Node longer = result1.size < result2.size ? list2 : list1;

        // Advance the pointer for the longer linked list by difference in lengths
        longer = getKthNode(longer, Math.abs(result1.size - result2.size));

        // Move both pointers until you have a collision.
        while (shorter != longer) {
            shorter = shorter.next;
            longer = longer.next;
        }
        // Return either one
        return longer;
    }

    private static Result getTailAndSize(Node list) {
        if (list == null) {
            return null;
        }
        int size = 1;
        Node current = list;

        while (current.next != null) {
            size++;
            current = current.next;
        }
        return new Result(current, size);
    }

    private static Node getKthNode(Node head, int k) {
        Node current = head;
        while (k > 0 && current != null) {
            current = current.next;
            k--;
        }
        return current;
    }

    private static Node getIntersectionNode(Node headA, Node headB) {
        if (headA == null || headB == null) {
            return null;
        }

        Node tempA = headA;
        Node tempB = headB;
        while (tempA != tempB) {
            if (tempA == null) {
                tempA = headB;
            } else {
                tempA = tempA.next;
            }
            if (tempB == null) {
                tempB = headA;
            } else {
                tempB = tempB.next;
            }
        }
        return tempA;
    }

    private static class Node {
        Node next;
    }

    private static class Result {
        int size;
        Node tail;

        Result(Node tail, int size) {
            this.tail = tail;
            this.size = size;
        }
    }
}
