package com.algorithms.geeksforgeeks.easy;

public class SegregateEvenAndOddNodesInLinkedList {
    /**
     * Given a Linked List of integers, write a function to modify the linked list such that all even numbers
     * appear before all the odd numbers in the modified linked list. Also, keep the order of even and odd numbers same.
     * <p>
     * https://practice.geeksforgeeks.org/problems/segregate-even-and-odd-nodes-in-a-linked-list/0
     */
    void segregate(int[] A, int N) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < N; i++) {
            if (A[i] % 2 == 0) {
                System.out.print(A[i] + " ");
            } else {
                builder.append(A[i]).append(" ");
            }
        }
        System.out.print(builder.toString());
        System.out.println();
    }
}
