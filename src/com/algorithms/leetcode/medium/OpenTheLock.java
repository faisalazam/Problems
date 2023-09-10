package com.algorithms.leetcode.medium;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

public class OpenTheLock {
    /**
     * You have a lock in front of you with 4 circular wheels. Each wheel has 10 slots:
     * '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'. The wheels can rotate freely and wrap around: f
     * or example we can turn '9' to be '0', or '0' to be '9'. Each move consists of turning one wheel one slot.
     * <p>
     * The lock initially starts at '0000', a string representing the state of the 4 wheels.
     * <p>
     * You are given a list of deadends dead ends, meaning if the lock displays any of these codes,
     * the wheels of the lock will stop turning and you will be unable to open it.
     * <p>
     * Given a target representing the value of the wheels that will unlock the lock,
     * return the minimum total number of turns required to open the lock, or -1 if it is impossible.
     * <p>
     * https://leetcode.com/problems/open-the-lock/
     */
    public int openLock(String[] deadends, String target) {
        Set<String> deadEnds = new HashSet<>(Arrays.asList(deadends));
        Set<String> visited = new HashSet<>();
        visited.add("0000");

        Queue<String> queue = new LinkedList<>();
        queue.add("0000");

        int level = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            while (size > 0) {
                String lockPosition = queue.poll();
                if (deadEnds.contains(lockPosition)) {
                    size--;
                    continue;
                }
                if (lockPosition.equals(target)) {
                    return level;
                }
                StringBuilder builder = new StringBuilder(lockPosition);
                for (int i = 0; i < 4; i++) {
                    process(i, queue, visited, deadEnds, builder);
                }
                size--;
            }
            level++;
        }
        return -1;
    }

    private void process(int index, Queue<String> queue, Set<String> visited, Set<String> deadEnds, StringBuilder builder) {
        char currentPosition = builder.charAt(index);
        String clockwisePosition = builder.substring(0, index) + (currentPosition == '9' ? 0 : currentPosition - '0' + 1);
        String counterClockwisePosition = builder.substring(0, index) + (currentPosition == '0' ? 9 : currentPosition - '0' - 1);

        visitAndAddToQueueIfPossible(clockwisePosition, queue, visited, deadEnds);
        visitAndAddToQueueIfPossible(counterClockwisePosition, queue, visited, deadEnds);
    }

    private void visitAndAddToQueueIfPossible(String position, Queue<String> queue, Set<String> visited, Set<String> deadEnds) {
        if (!visited.contains(position) && !deadEnds.contains(position)) {
            queue.offer(position);
            visited.add(position);
        }
    }
}
