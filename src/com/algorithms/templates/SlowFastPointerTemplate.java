package com.algorithms.templates;

public class SlowFastPointerTemplate {
    /**
     * "hare and tortoise" or "tortoise and hare" or "slow and fast" pointers approach: moving one pointer at half t
     * he speed of the other.
     * <p>
     * After the while loop, if fast is not null, then that means list size is odd.
     * That also means that slow will be in the mid, i.e. if size is 7, then there will be 3 nodes before slow,
     * as well as 3 nodes after slow
     * <p>
     * On the other hand, if fast is null, then that means list size is even and in that case, slow will at mid + 1,
     * i.e. if size is 6, then there will be 3 nodes before slow, but 2 nodes after slow
     */
    void slowFastRunnerTemplate(LinkedListNode head) {
        LinkedListNode fast = head;
        LinkedListNode slow = head;

        // Depending on your problem, processing can also be done inside this while loop;
        // after this while loop, if fast is not null, then that means list size is odd.
        // that also means that slow will be in the mid, i.e. if size is 7,
        // then there will be 3 nodes before slow, as well as 3 nodes after slow
        // on the other hand, if fast is null, then that means list size is even
        // and in that case, slow will at mid + 1, i.e. if size is 6,
        // then there will be 3 nodes before slow, but 2 nodes after slow
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        /* List has odd number of elements, skip the middle element? */
//        if (fast != null) {
//            slow = slow.next;
//        }

        while (slow != null) {
            // do the processing
            slow = slow.next;
        }
    }


    private static class LinkedListNode {
        int data;
        LinkedListNode next;
    }
}
