package com.algorithms.geeksforgeeks.easy.linkedlist;

/**
 * Given two sorted linked lists consisting of N and M nodes respectively. The task is to merge both of the list
 * (in-place) and return head of the merged list.
 * Note: It is strongly recommended to do merging in-place using O(1) extra space.
 * <p>
 * User Task:
 * The task is to complete the function sortedMerge() which takes references to the heads of two linked lists
 * as the arguments and returns the head of merged linked list.
 * <p>
 * Expected Time Complexity : O(n+m)
 * Expected Auxiliary Space : O(1)
 * <p>
 * https://practice.geeksforgeeks.org/problems/merge-two-sorted-linked-lists/1
 */
public class MergeTwoSortedLinkedLists {
    /*
     * Brute Force Way:
     *      The Approach:
     *
     *      In this Approach we use a vector of (n+m) size where n and m are length respective linked list and then
     *      store all the element in vector and then we sort the vector and make new linked list it will be our answer.
     *
     * Time Complexity:O((n+m)+(n+m)*log(n+m)),(n+m) for traversing linked lists and (n+m)*log(n+m) for sorting vector
     * which can be further solved to O((n+m)*log(n+m)).
     * Auxiliary Space: O(n+m), for vector.
     */

    /**
     * Intuition
     * We can consider traversing and comparing the values of corresponding nodes in both the linked list and
     * continuously choosing the less value among them. Whichever value will be less, we will choose that linked list,
     * insert that value in our answer list and move the pointer in that list one node ahead. We will continue the
     * process till both the linked list gets traversed completely.
     * <p>
     * Time Complexity : O(m+n):  Since we are traversing through the two lists fully. So, the time complexity is
     * O(m+n) where m and n are the lengths of the two lists to be merged.
     * Space Complexity : O(1), Since there is no extra space used.
     */
    Node sortedMergeIterative(Node head1, Node head2) {
        final Node dummyNode = new Node(0);
        Node tail = dummyNode; // tail points to the last result node
        while (true) {
            if (head1 == null) {
                tail.next = head2;
                break;
            }
            if (head2 == null) {
                tail.next = head1;
                break;
            }
            if (head1.data <= head2.data) {
                tail.next = head1;
                head1 = head1.next;
            } else {
                tail.next = head2;
                head2 = head2.next;
            }
            tail = tail.next;
        }
        return dummyNode.next;
    }

    /**
     * Intuition
     * We can consider traversing and comparing the values of corresponding nodes in both the linked list and
     * continuously choosing the less value among them. Whichever value will be less, we will choose that linked list,
     * insert that value in our answer list and move the pointer in that list one node ahead. We will continue the
     * process till both the linked list gets traversed completely.
     * <p>
     * Time Complexity : O(m+n):  Since we are traversing through the two lists fully. So, the time complexity is
     * O(m+n) where m and n are the lengths of the two lists to be merged.
     * Space Complexity : O(1), Since there is no extra space used.
     */
    Node sortedMergeIterativeV0(Node head1, Node head2) {
        if (head1 == null) {
            return head2;
        } else if (head2 == null) {
            return head1;
        }
        Node head = null;
        Node temp = new Node(-1);
        while (head1 != null && head2 != null) {
            if (head1.data <= head2.data) {
                temp.next = head1;
                head1 = head1.next;
            } else {
                temp.next = head2;
                head2 = head2.next;
            }
            temp = temp.next;
            if (head == null) {
                head = temp;
            }
        }
        if (head1 != null) {
            temp.next = head1;
        }
        if (head2 != null) {
            temp.next = head2;
        }
        return head;
    }

    /**
     * Intuition
     * The idea is to use recursive calls on the small nodes and keep adding the results to our answer list. We will
     * firstly check the first values , add the lesser value among two into our ans list and will make the next
     * recursive call for the next node in the same linked list. We will recursively keep comparing the values of node
     * data from both the linked list, subsequently adding the lesser value to the result and make the next recursive
     * call for the next node in the chosen linked list ( that contained the latest lesser value for our answer list).
     * If some how we reach end of any particular linked list, we can add the remaining part of second linked list to
     * our answer list as we can conclude that all of its value will be greater than the last inserted value and since
     * the linked lists are already sorted hence we can insert it directly.
     * <p>
     * Time Complexity O(m+n):  Since we are traversing through the two lists fully. So, the time complexity is
     * O(m+n) where m and n are the lengths of the two lists to be merged.
     * Space Complexity O(m+n): Since stack is used for recursion.
     */
    Node sortedMergeRecursive(Node head1, Node head2) {
        if (head1 == null) {
            return head2;
        }
        if (head2 == null) {
            return head1;
        }

        if (head1.data < head2.data) {
            head1.next = sortedMergeRecursive(head1.next, head2);
            return head1;
        } else {
            head2.next = sortedMergeRecursive(head1, head2.next);
            return head2;
        }
    }

    /*
     * Merge two sorted linked lists by Reversing the Lists:
     * <p>
     * This idea involves first reversing both the given lists and after reversing, traversing both the lists till the
     * end and then comparing the nodes of both the lists and inserting the node with a larger value at the beginning
     * of the result list. And in this way, we will get the resulting list in increasing order.
     * <p>
     * Time Complexity: O(M+N) where M and N are the lengths of the two lists to be merged.
     * Auxiliary Space: O(M+N)
     * <p>
     * See this for more information: https://www.geeksforgeeks.org/merge-two-sorted-linked-lists/
     */

    private static class Node {
        int data;
        Node next;

        Node(int d) {
            this.data = d;
            this.next = null;
        }
    }
}
