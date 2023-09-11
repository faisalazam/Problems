package com.algorithms.geeksforgeeks.easy.linkedlist;

public class OccurrenceOfAnIntegerInLinkedList {
    /**
     * Given a singly linked list and a key, count number of occurrences of given key in linked list.
     * For example, if given linked list is 1->2->1->2->1->3->1 and given key is 1, then output should be 4.
     * <p>
     * https://practice.geeksforgeeks.org/problems/occurence-of-an-integer-in-a-linked-list/1
     * <p>
     * Time Complexity: O(n)
     * Auxiliary Space: O(1)
     */
    public static int frequency(Node head, int searchFor) {
        int count = 0;
        Node temp = head;
        while (temp != null) {
            if (temp.data == searchFor) {
                count++;
            }
            temp = temp.next;
        }
        return count;
    }

    private static int frequency = 0;

    // Time complexity: O(n) where n is size of linked list
    // Auxiliary Space: O(n) for call stack since using recursion
    // Tail Recursion: If a recursive function calling itself and that recursive call is the last statement in the
    // function then it’s known as Tail Recursion. Otherwise, it’s known as head-recursion. After that call the
    // recursive function performs nothing. The function has to process or perform any operation at the time of calling
    // and it does nothing at returning time. With tail recursion, the recursive call is the last thing the method does,
    // so there is nothing left to execute within the current function. Thus, logically there is no need to store current
    // function’s stack frame. Although the compiler can utilize this point to optimize memory, it should be noted that
    // the Java compiler doesn’t optimize for tail-recursion for now (May 2, 2023) but some modern compilers can
    // optimize utilising tail call termination.
    static int frequencyRecursive(Node head, int key) {
        if (head == null)
            return frequency;
        if (head.data == key) {
            frequency++;
        }
        return frequencyRecursive(head.next, key);
    }

    // Time complexity: O(n) where n is size of linked list
    // Auxiliary Space: O(n) for call stack since using recursion
    static int frequencyRecursiveAvoidGlobalVariable(Node head, int key) {
        if (head == null) {
            return 0;
        }
        return (head.data == key ? 1 : 0) + frequencyRecursive(head.next, key);
    }

    // Time complexity: O(n) where n is size of linked list
    // Auxiliary Space: O(n) for call stack since using recursion
    // Head Recursion: If a recursive function calling itself and that recursive call is the first statement in the
    // function then it’s known as Head Recursion. There’s no statement, no operation before the call. The function
    // doesn’t have to process or perform any operation at the time of calling and all operations are done at returning time.
    static int frequencyHeadRecursiveImpl(Node head, int key) {
        if (head == null) {
            return 0;
        }
        final int frequency = frequencyRecursive(head.next, key);
        return (head.data == key ? 1 : 0) + frequency;
    }

    // Tree Recursion: To understand Tree Recursion let’s first understand Linear Recursion. If a recursive function
    // calling itself for one time then it’s known as Linear Recursion. Otherwise, if a recursive function calling
    // itself for more than one time then it’s known as Tree Recursion.
    // e.g. stepWays(n - 1) + stepWays(n - 2), Time Complexity For Tree Recursion usually is something like:
    // O(no-of-calls^n), which in this case is: O(2^n) and for:
    // stepWays(n - 1) + stepWays(n - 2) + stepWays(n - 3), it'll be O(3^n)

    private static class Node {
        int data;
        Node next;

        Node(int d) {
            data = d;
            next = null;
        }
    }
}
