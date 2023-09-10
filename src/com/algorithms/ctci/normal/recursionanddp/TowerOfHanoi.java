package com.algorithms.ctci.normal.recursionanddp;

import java.util.Stack;

/**
 * Towers of Hanoi: In the classic problem of the Towers of Hanoi, you have 3 towers
 * and N disks of different sizes which can slide onto any tower.
 * The puzzle starts with disks sorted in ascending order of size from top to bottom
 * (i.e., each disk sits on top of an even larger one). You have the following constraints:
 * (1) Only one disk can be moved at a time.
 * (2) A disk is slid off the top of one tower onto another tower.
 * (3) A disk cannot be placed on top of a smaller disk.
 * <p>
 * Write a program to move the disks from the first tower to the last using Stacks.
 */
public class TowerOfHanoi {
    private Stack<Integer> disks;
    private int index;

    private TowerOfHanoi(int i) {
        disks = new Stack<>();
        index = i;
    }

    public int index() {
        return index;
    }

    public void add(int disk) {
        if (!disks.isEmpty() && disks.peek() <= disk) {
            System.out.println("Error placing disk " + disk);
        } else {
            disks.push(disk);
        }
    }

    private void moveTopTo(TowerOfHanoi tower) {
        int top = disks.pop();
        tower.add(top);
    }

    private void print() {
        System.out.println("Contents of Tower " + index() + ": " + disks.toString());
    }

    /**
     * Let's start with the smallest possible example: n = 1,
     * Case n = 1. Can we move Disk 1 from Tower 1 to Tower 3? Yes.
     * 1. We simply move Disk 1 from Tower 1 to Tower 3.
     * Case n = 2. Can we move Disk 1 and Disk 2from Tower Ho Tower 3?Yes.
     * 1. Move Disk 1 from Tower 1 to Tower 2
     * 2. Move Disk 2 from Tower 1 to Tower 3
     * 3. Move Disk 1 from Tower 2 to Tower 3
     * Note how in the above steps, Tower 2 acts as a buffer, holding a disk while we move other disks to Tower 3.
     * Case n = 3. Can we move Disk 1, 2, and 3 from Tower 1 to Tower 3? Yes.
     * 1, We know we can move the top two disks from one tower to another (as shown earlier),
     * so let's assume we've already done that. But instead, let's move them to Tower 2.
     * 2, Move Disk 3 to Tower 3.
     * 3, Move Disk 1 and Disk 2 to Tower 3.
     * We already know how to do this—just repeat what we did in Step 1,
     * Case n = 4. Can we move Disk 1, 2, 3 and 4 from Tower 1 to Tower 3? Yes.
     * 1. Move Disks 1, 2, and 3 to Tower 2. We know how to do that from the earlier examples.
     * 2. Move Disk 4 to Tower 3.
     * 3. Move Disks 1, 2 and 3 back to Tower 3
     * <p>
     * Remember that the labels of Tower 2 and Tower 3 aren't important.
     * They're equivalent towers. So, moving disks to Tower 3 with Tower 2 serving as a buffer is equivalent to
     * moving disks to Tower 2 with Tower 3 serving as a buffer.
     */
    private void moveDisks(int n, TowerOfHanoi destination, TowerOfHanoi buffer) {
        if (n <= 0) {
            return;
        }

        // move top n - 1 disks from origin to buffer, using destination as a buffer.
        moveDisks(n - 1, buffer, destination);

        // move top from origin to destination moveTop(origin, destination);
        moveTopTo(destination);

        // move top n - 1 disks from buffer to destination, using origin as a buffer.
        buffer.moveDisks(n - 1, destination, this);
    }

    private void moveDisksWithPrintStatements(int n, TowerOfHanoi destination, TowerOfHanoi buffer) {
        if (n <= 0) {
            return;
        }
        String tag = "move_" + n + "_disks_from_" + this.index + "_to_" + destination.index + "_with_buffer_" + buffer.index;
        System.out.println("<" + tag + ">");

        moveDisksWithPrintStatements(n - 1, buffer, destination);

        System.out.println("<move_top_from_" + this.index + "_to_" + destination.index + ">");
        System.out.println("<before>");
        System.out.println("<source_print>");

        this.print();

        System.out.println("</source_print>");
        System.out.println("<destination_print>");

        destination.print();

        System.out.println("</destination_print>");
        System.out.println("</before>");

        moveTopTo(destination);

        System.out.println("<after>");
        System.out.println("<source_print>");

        this.print();

        System.out.println("</source_print>");
        System.out.println("<destination_print>");

        destination.print();

        System.out.println("</destination_print>");
        System.out.println("</after>");
        System.out.println("</move_top_from_" + this.index + "_to_" + destination.index + ">");

        buffer.moveDisksWithPrintStatements(n - 1, destination, this);

        System.out.println("</" + tag + ">");
    }

    public static void main(String[] args) {
        int n = 5;
        TowerOfHanoi[] towers = new TowerOfHanoi[3];
        for (int i = 0; i < 3; i++) {
            towers[i] = new TowerOfHanoi(i);
        }
        for (int i = n - 1; i >= 0; i--) {
            towers[0].add(i);
        }

        // Copy and paste output into a .XML file and open it with internet explorer.
        //towers[0].print();
        towers[0].moveDisksWithPrintStatements(n, towers[2], towers[1]);
        //towers[2].print();
    }
}
