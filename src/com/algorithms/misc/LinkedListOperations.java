package com.algorithms.misc;

public class LinkedListOperations {
    private static class Node {
        private int data;
        private Node next;

        Node(int data) {
            this.data = data;
            this.next = null;
        }
    }

    private static class LinkedList {
        private Node head;

        LinkedList() {
            this.head = null;
        }

        void insert(int data) {
            Node newNode = new Node(data);
            if (this.head != null) {
                newNode.next = this.head;
            }
            this.head = newNode;
        }
    }

    private static void printSinglyLinkedList(Node node, String sep) {
        Node temp = node;
        while (temp != null) {
            System.out.print(temp.data);
            temp = temp.next;
            if (temp != null) {
                System.out.print(sep);
            }
        }
        System.out.println();
    }

    private static Node reverseIteratively(Node head) {
        if (head == null || head.next == null) {
            return head;
        }
        Node prev = null;
        Node curr = head;

        while (curr != null) {
            Node next = curr.next;
            curr.next = prev;
            prev = curr;
            curr = next;
        }
        return prev;
    }

    private static Node reverseRecursively(Node head) {
        if (head == null || head.next == null) {
            return head;
        }
        Node reversedHead = reverseRecursively(head.next);
        head.next.next = head;
        head.next = null;
        return reversedHead;
    }

    private static Node reverseFirstNElements(Node head, int noOfFirstElementsToReverse) {
        if (head == null || head.next == null) {
            return head;
        }
        Node temp = head;
        int noOfTotalElements = 0;
        while (temp != null) {
            temp = temp.next;
            noOfTotalElements++;
        }
        if (noOfTotalElements == noOfFirstElementsToReverse) {
            return reverseIteratively(head);
        }
        int counter = 0;
        noOfFirstElementsToReverse = noOfFirstElementsToReverse % noOfTotalElements;

        Node prev = null;
        Node curr = head;
        while (curr != null && counter < noOfFirstElementsToReverse) {
            counter++;
            Node next = curr.next;
            curr.next = prev;
            prev = curr;
            curr = next;
        }
        head.next = curr;
        return prev;
    }

    private static Node reverseLastNElements(Node head, int noOfLastElementsToReverse) {
        if (head == null || head.next == null) {
            return head;
        }

        Node temp = head;
        int noOfTotalElements = 0;
        while (temp != null) {
            temp = temp.next;
            noOfTotalElements++;
        }
        if (noOfLastElementsToReverse == noOfTotalElements) {
            return reverseIteratively(head);
        }
        noOfLastElementsToReverse = noOfLastElementsToReverse % noOfTotalElements;

        int counter = 0;
        Node slow = new Node(Integer.MIN_VALUE);
        slow.next = head;
        while (slow != null && counter < noOfTotalElements - noOfLastElementsToReverse) {
            slow = slow.next;
            counter++;
        }
        if (counter > 0 && slow != null) {
            slow.next = reverseIteratively(slow.next);
        }
        return head;
    }



    /**
     * Delete Middle Node: Implement an algorithm to delete a node in the middle (i.e., any node but the first and last node,
     * not necessarily the exact middle) of a singly linked list, given only access to that node.
     * <p>
     * Solution
     * In this problem, you are not given access to the head of the linked list. You only have access to that node.
     * The solution is simply to copy the data from the next node over to the current node,
     * and then to delete the next node.
     */
    private static boolean deleteNode(Node nodeToBeDeleted) {
        if (nodeToBeDeleted == null || nodeToBeDeleted.next == null) {
            return false; // Failure
        }
        //Assuming that it is not the tail node
        nodeToBeDeleted.data = nodeToBeDeleted.next.data;
        nodeToBeDeleted.next = nodeToBeDeleted.next.next;
        return true;
    }

    private static Node removeElements(Node head, int targetVal) {
        //this will cover the case when head contains the target value
        while (head != null && head.data == targetVal) {
            head = head.next;
        }
        Node temp = head;
        while (temp != null && temp.next != null) {
            if (targetVal == temp.next.data) {
                temp.next = temp.next.next;
            } else {
                temp = temp.next;
            }
        }
        return head;
    }

    private static Node deleteDuplicates(Node head) {
        Node temp = head;
        while (temp != null && temp.next != null) {
            if (temp.data == temp.next.data) {
                temp.next = temp.next.next;
            } else {
                temp = temp.next;
            }
        }
        return head;
    }

    public static void main(String[] args) {
        LinkedList linkedList = new LinkedList();
        linkedList.insert(1);
        linkedList.insert(2);
        linkedList.insert(3);
        linkedList.insert(4);
        linkedList.insert(5);

        printSinglyLinkedList(linkedList.head, " => ");
        linkedList.head = reverseRecursively(linkedList.head);
        printSinglyLinkedList(linkedList.head, " <= ");
        linkedList.head = reverseIteratively(linkedList.head);
        printSinglyLinkedList(linkedList.head, " => ");
        linkedList.head = reverseLastNElements(linkedList.head, 7);
        printSinglyLinkedList(linkedList.head, " => ");
        linkedList.head = reverseFirstNElements(linkedList.head, 4);
        printSinglyLinkedList(linkedList.head, " => ");


        LinkedList headA = new LinkedList();
        headA.insert(40);
        headA.insert(15);
        headA.insert(15);
        headA.insert(10);
        headA.insert(5);

        LinkedList headB = new LinkedList();
        headB.insert(20);
        headB.insert(3);
        headB.insert(3);
        headB.insert(2);
//        Node mergedHead = sortedMerge(headA.head, headB.head); //method has moved to MergeTwoSortedLinkedLists
//        printSinglyLinkedList(mergedHead, " => ");

        int count = 6;
        int middleElementIndex = (count / 2) + 1;
        System.out.println(middleElementIndex);

        LinkedList headC = new LinkedList();
        headC.insert(8);
        headC.insert(7);
        headC.insert(6);
        headC.insert(5);
        headC.insert(4);
        headC.insert(3);
        headC.insert(2);
        headC.insert(1);
//        printSinglyLinkedList(rotate(headC.head, 12), " => "); //method has moved to RotateALinkedList
    }
}

