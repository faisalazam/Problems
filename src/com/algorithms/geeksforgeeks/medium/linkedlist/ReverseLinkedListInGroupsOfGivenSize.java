package com.algorithms.geeksforgeeks.medium.linkedlist;

/**
 * Given a linked list of size N. The task is to reverse every k nodes (where k is an input to the function) in the
 * linked list. If the number of nodes is not a multiple of k then left-out nodes, in the end, should be considered as
 * a group and must be reversed
 * <p>
 * User Task:
 * Your task is to complete the function reverse() which should reverse the linked list in group of size k and return
 * the head of the modified linked list.
 * <p>
 * Expected Time Complexity : O(n)
 * Expected Auxiliary Space : O(1)
 * <p>
 * https://practice.geeksforgeeks.org/problems/reverse-a-linked-list-in-groups-of-given-size/1
 */
public class ReverseLinkedListInGroupsOfGivenSize {
    /*
     * Brute Force Method
     * Intuition
     * The idea is to break the solution into two part, in first part we will fetch out all the elements and store them
     * into a vector since it is easy to reverse a vector as compared a linked list. After that, we will reverse the
     * elements of the vector in group of length k. once we have all the elements reversed, We can now make a linked list
     * out of it.
     * <p>
     * Time Complexity: O(N)+ O(N)= O(N), since we need to traverse the entire linked list to make an array and then
     * to prepare the a new linked list out of that array.
     * Space Complexity: O(N), we will need extra space for storing the linked list elements and then to prepare a new
     * linked list
     */

    /**
     * Intuition
     * The intuition is by using recursion we can reverse the first k nodes using reverse algorithm and subsequently
     * pass the next k nodes to be reversed to the reverse function. This is how we will keep calling the next k nodes
     * for reversal till the next node becomes null.
     * <p>
     * Time Complexity: O(n). since Traversal of list is done only once and it has 'n' elements.
     * Auxiliary Space: O(n/k). precisely O(n), For each Linked List of size n, n/k or (n/k)+1 calls will be made
     * during the recursion.
     */
    public static Node reverseInKGroups(Node node, int k) {
        int count = 0;
        Node prev = null;
        Node curr = node;
        Node next = null;

        while (curr != null && count++ < k) {
            next = curr.next;                 // marking next node
            curr.next = prev;                 // reversing link
            prev = curr;                      // updating prev
            curr = next;                      // updating current
        }

        if (next != null) { // checking if there are nodes ahead
            node.next = reverseInKGroups(next, k);     // reversing those recursively
        }
        return prev;
    }

    /**
     * Time Complexity : O(n)
     * Auxiliary Space : O(1)
     */
    public static Node reverseInKGroupsV1(Node node, int k) {
        // tail will keep pointing to the initial head (head node before the reverse) of the group of k nodes, which
        // will be used later join lists of K groups...
        Node tail = node;
        int count = getCount(node);
        Node newHead = reverseInKGroups(node, null, tail, k);
        while (newHead != null && count > 0) {
            final Node prev = tail; // point to last node of the reversed list of size k
            if (tail != null) {
                tail = tail.next; // point to the first node of the next part of the list
            }
            prev.next = reverseInKGroups(tail, prev, tail, k);
            count -= k;
        }
        return newHead;
    }

    /**
     * Solution with Dummy Node
     * <p>
     * Time Complexity: O(N) : While loop takes O(N/K) time and inner for loop takes O(K) time. So N/K * K = N.
     * Therefore TC O(N)
     * Space Complexity: O(1) : No extra space is used.
     */
    static Node reverseInKGroupsV0(Node node, int k) {
        if (node == null || node.next == null || k == 1) {
            return node;
        }
        final Node dummy = new Node(-1); // creating dummy node
        dummy.next = node;

        Node prev = dummy;
        Node curr = dummy;
        Node next = dummy;

//        int count = getCount(node); // it won't work because the below code is using curr pointer as well
        int count = 0;
        while (curr != null) {
            count++;
            curr = curr.next;
        }

        // Iterating till next is not NULL
        while (next != null) {
            curr = prev.next; // Curr position after every reverse group
            next = curr.next; // Next will always next to curr
            final int loopCount = count > k ? k : count - 1; // loopCount will set to count - 1 in case of remaining element

            for (int i = 1; i < loopCount; i++) {
                curr.next = next.next;
                next.next = prev.next;
                prev.next = next;
                next = curr.next;
            }
            prev = curr; // Setting prev to curr
            count -= k; // Update count
        }
        return dummy.next; // dummy -> next will be our new head
    }

    //1 2 3 4 5 6 7 8 9
    //3 2 1
    //      6 5 4
    //            9 8 7
    private static Node reverseInKGroups(Node currHead, Node prev, Node tail, int k) {
        if (currHead == null || currHead.next == null) {
            return currHead;
        }
        int groupSize = k;
        Node curr = currHead;
        while (curr != null && groupSize-- > 0) {
            Node next = curr.next;
            curr.next = prev;
            prev = curr;
            curr = next;
        }
        // tail is at the last node of the reversed list whereas curr is at the first node of the next part of the list,
        // so let's join them
        tail.next = curr;
        return prev; // new head of the reversed list
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

    private static class Node {
        int data;
        Node next;

        Node(int data) {
            this.data = data;
            this.next = null;
        }
    }
}
