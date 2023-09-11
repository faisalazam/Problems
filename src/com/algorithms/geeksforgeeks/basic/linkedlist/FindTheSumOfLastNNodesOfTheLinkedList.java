package com.algorithms.geeksforgeeks.basic.linkedlist;

public class FindTheSumOfLastNNodesOfTheLinkedList {
    /**
     * Given a single linked list of size M, your task is to complete the function sumOfLastN_Nodes(),
     * which should return the sum of last N nodes of the linked list.
     * <p>
     * https://practice.geeksforgeeks.org/problems/find-the-sum-of-last-n-nodes-of-the-linked-list/1
     */
    public int sum(Node head, int k) {
        int sum = 0;
        Node fast = head;
        Node slow = head;
        while (slow != null) { //may be better and more readable to write it as 3 steps/loops
            if (k-- > 0 && fast != null) {
                fast = fast.next;
                continue;
            }
            if (fast != null) {
                fast = fast.next;
            } else {
                sum += slow.data;
            }
            slow = slow.next;
        }
        return sum;
    }

    public int sumV0(Node head, int k) {
        Node fast = head;
        for (int i = 0; i < k; i++) {
            fast = fast.next;
        }

        Node slow = head;
        while (fast != null) {
            slow = slow.next;
            fast = fast.next;
        }

        int sum = 0;
        while (slow != null) {
            sum += slow.data;
            slow = slow.next;
        }

        return sum;
    }

    // Time Complexity: O(n), where n is the number of nodes in the linked list.
    // Auxiliary Space: O(1)
    private static int sumV2(Node head, int n) {
        if (n <= 0) {
            return 0;
        }

        int sum = 0;  // It'll hold the total sum of all the nodes in the linked list
        int temp = 0; // It'll hold the sum of the first (listSize - n) nodes in the linked list
        Node refPtr = head;
        Node mainPtr = head;

        // traverse 1st 'n' nodes through 'refPtr' and accumulate all node's data to 'sum'
        while (refPtr != null && (n--) > 0) {
            sum += refPtr.data;
            refPtr = refPtr.next;
        }

        // traverse to the end of the linked list
        while (refPtr != null) {
            sum += refPtr.data;
            refPtr = refPtr.next;

            temp += mainPtr.data;
            mainPtr = mainPtr.next;
        }
        return (sum - temp);
    }

    // Another approach is to reverse the linked list and then take the sum of first n nodes

    private static int n, sum;

    // utility function to find the sum of last 'n' nodes
    // Time Complexity: O(n), where n is the number of nodes in the linked list.
    // Auxiliary Space: O(n), if system call stack is being considered.
    static int sumV1(Node head, int n) {
        if (n <= 0)
            return 0;

        sum = 0;

        // find the sum of last 'n' nodes
        sumOfLastN_Nodes(head);

        // required sum
        return sum;
    }

    // function to recursively find the sum of last 'n' nodes of the given linked list
    static void sumOfLastN_Nodes(Node head) {
        if (head == null)
            return;

        // recursively traverse the remaining nodes
        sumOfLastN_Nodes(head.next);

        // if node count 'n' is greater than 0
        if (n > 0) {
            sum = sum + head.data;
            --n;
        }
    }

    private static class Node {
        int data;
        Node next;

        Node(int d) {
            this.data = d;
            this.next = null;
        }
    }
}
