package com.algorithms.ctci.normal.linkedlists;

import java.util.HashSet;
import java.util.Set;

/**
 * Intersection: Given two (singly) linked lists, determine if the two lists intersect.
 * Return the intersecting node. Note that the intersection is defined based on reference, not value.
 * That is, if the kth node of the first linked list is the exact same node (by reference) as the jth node
 * of the second linked list, then they are intersecting.
 * <p>
 * This algorithm takes 0(A + B) time, where A and B are the lengths of the two linked lists.
 * It takes 0(1) additional space.
 * <p>
 * https://practice.geeksforgeeks.org/problems/intersection-point-in-y-shapped-linked-lists/1/?track=md-linkedlist&batchId=144
 */
public class IntersectionPointInYShapedLinkedLists {
    /**
     * Brute Force Approach(TLE)
     * Intuition
     * The idea is to use 2 nested for loops. The outer loop will be for each node of the 1st list and the inner loop
     * will be for the 2nd list. In the inner loop, check if any of the nodes of the 2nd list is the same as the current
     * node of the first linked list.
     * <p>
     * Time Complexity: O(n1 * n2), where n1 is the length of the first linked list (headA) and n2 is the length of the
     * second linked list (headB). Since the inner loop is nested within the outer loop, the total number of
     * comparisons made will be the product of the number of nodes in headA and headB, resulting in O(n1 * n2) time
     * complexity.
     * Space Complexity: O(1), which means it uses a constant amount of extra space.
     */
    public static int findIntersectionBruteForce(Node headA, Node headB) {
        if (headA == null || headB == null) {
            return -1;
        }

        while (headB != null) {
            Node temp = headA;
            while (temp != null) {
                if (temp == headB) { // if both Nodes are same
                    return headB.data;
                }
                temp = temp.next;
            }
            headB = headB.next;
        }
        return -1; // no intersection is not present between the lists,
    }

    /**
     * Brute Optimised Approach (TLE)
     * Intuition
     * We can use a hash set to store nodes from the first linked list (headA). It then iterates through the second
     * linked list (headB) and checks if the current node is already present in the hash set. If a match is found, it
     * indicates an intersection, and the data of the matching node is returned. The hash set efficiently allows for
     * quick membership checks and ensures that duplicates are not stored.
     * <p>
     * Time Complexity:  O(n1 + n2), where n1 and n2 are the lengths of the two linked lists. The first loop iterates
     * through the nodes of headA and adds them to the hash set. This takes O(n1) time, where n1 is the length of headA.
     * The second loop iterates through the nodes of headB and performs a constant-time lookup in the hash set for each
     * node. This takes O(n2) time, where n2 is the length of headB.
     * Space Complexity : O(n1), where n1 is the length of the first linked list (headA). The hash set stores nodes from
     * headA, and its size is proportional to the number of nodes in headA. The memory usage scales with the size of headA
     */
    public static int findIntersectionImprovedBruteForce(Node headA, Node headB) {
        if (headA == null || headB == null) {
            return -1;
        }

        final Set<Node> nodes = new HashSet<>();
        for (Node curr = headA; curr != null; curr = curr.next) {
            nodes.add(curr);
        }

        for (Node curr = headB; curr != null; curr = curr.next) {
            if (nodes.contains(curr)) {
                return curr.data;
            }
        }
        return -1; // no intersection is not present between the lists,
    }

    /**
     * Expected Approach
     * <p>
     * Traverse the bigger list from the first node to d nodes so that from here onwards both the lists have an equal
     * no of nodes
     * <p>
     * The approach first finds the lengths of both linked lists (head1 and head2) using two separate loops. Then, it
     * adjusts the starting points of the longer linked list by moving its head forward by the difference in lengths
     * between the two lists. This ensures that both linked lists have an equal number of nodes remaining to be
     * traversed before reaching the intersection point. After that, it iterates through both lists simultaneously,
     * one step at a time, until the pointers head1 and head2 point to the same node, which is the intersection point.
     * <p>
     * Time complexity : 0(A + B) time, where A and B are the lengths of the two linked lists.
     * Auxiliary space : O(1)
     */
    public static int findIntersection(Node headA, Node headB) {
        if (headA == null || headB == null) {
            return -1;
        }
        // Get tail and sizes.
        final Result result1 = getTailAndSize(headA);
        final Result result2 = getTailAndSize(headB);

        // If different tail nodes, then there's no intersection.
        if (result1.tail != result2.tail) {
            return -1;
        }
        Node shorter = result1.size < result2.size ? headA : headB;
        Node longer = result1.size < result2.size ? headB : headA;

        // Advance the pointer for the longer linked list by difference in lengths
        longer = getKthNode(longer, Math.abs(result1.size - result2.size));

        // Move both pointers until you have a collision.
        while (shorter != longer) {
            shorter = shorter.next;
            longer = longer.next;
        }
        return longer.data; // Return either one
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
        while (k-- > 0 && current != null) {
            current = current.next;
        }
        return current;
    }

    /**
     * Time complexity : 0(A + B) time, where A and B are the lengths of the two linked lists.
     * Auxiliary space : O(1)
     */
    private static int getIntersectionNode(Node headA, Node headB) {
        if (headA == null || headB == null) {
            return -1;
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
        return tempA == null ? -1 : tempA.data;
    }

    private static class Node {
        int data;
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
