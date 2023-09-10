package com.algorithms.ctci.normal.linkedlists;

public class NodeAtBeginningOfLoop {
    /**
     * Loop Detection: Given a circular linked list, implement an algorithm that returns the node at the beginning of the loop.
     * <p>
     * DEFINITION
     * Circular linked list: A (corrupt) linked list in which a node's next pointer points to an earlier node,
     * so as to make a loop in the linked list.
     * <p>
     * SOLUTION
     * Part 1: Detect If Linked List Has A Loop
     * An easy way to detect if a linked list has a loop is through the FastRunner / SlowRunner approach.
     * Fast Runner moves two steps at a time, while SlowRunner moves one step.
     * <p>
     * Part 2: When Do They Collide?
     * Since k might be much larger than the loop length, we should actually write this as mod ( k , LOOP_SIZE) steps,
     * which we will denote as K.
     * So, when do they meet? Well, if FastRunner is L00P_SIZE - K steps behind SlowRunner,
     * and FastRunner catches up at a rate of 1 step per unit of time, then they meet after LOOP_SIZE - K steps.
     * At this point, they will be K steps before the head of the loop. Let's call this point CollisionSpot.
     * <p>
     * Part 3: How Do You Find The Start of the Loop?
     * We now know that CollisionSpot is K nodes before the start of the loop. Because K = mod(k, LOOP_ SIZE)
     * (or, in other words, k = K + M * LOOP_SIZE, for any integer M), it is also correct to say that
     * it is k nodes from the loop start. For example, if node N is 2 nodes into a 5 node loop,
     * it is also correct to say that it is 7,12, or even 397 nodes into the loop.
     * Therefore, both CollisionSpot and Linked List Head are k nodes from the start of the loop.
     * Now, if we keep one pointer at CollisionSpot and move the other one to Linked ListHead, they will each be k nodes
     * from LoopStart. Moving the two pointers at the same speed will cause them to collide again
     * this time after k steps, at which point they will both be at LoopStart. All we have to do is return this node.
     */
    public static LinkedListNode FindBeginning(LinkedListNode head) {
        LinkedListNode slow = head;
        LinkedListNode fast = head;

        // Find meeting point
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast) {
                break;
            }
        }

        // Error check - there is no meeting point, and therefore no loop
        if (fast == null || fast.next == null) {
            return null;
        }

		/* Move slow to Head. Keep fast at Meeting Point. Each are k steps
		/* from the Loop Start. If they move at the same pace, they must
		 * meet at Loop Start. */
        slow = head;
        while (slow != fast) {
            slow = slow.next;
            fast = fast.next;
        }

        // Both now point to the start of the loop.
        return fast;
    }

    private static class LinkedListNode {
        LinkedListNode next;
    }
}
