package com.algorithms.leetcode.hard;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

public class FrogJump {
    /**
     * A frog is crossing a river. The river is divided into x units and at each unit there may or may not exist a stone.
     * The frog can jump on a stone, but it must not jump into the water.
     * <p>
     * Given a list of stones' positions (in units) in sorted ascending order, determine if the frog is able to
     * cross the river by landing on the last stone. Initially, the frog is on the first stone and
     * assume the first jump must be 1 unit.
     * <p>
     * If the frog's last jump was k units, then its next jump must be either k - 1, k, or k + 1 units.
     * Note that the frog can only jump in the forward direction.
     * <p>
     * Note:
     * <p>
     * The number of stones is ≥ 2 and is < 1,100.
     * Each stone's position will be a non-negative integer < 231.
     * The first stone's position is always 0.
     * <p>
     * https://leetcode.com/problems/frog-jump/
     */
    public boolean canCross(int[] stones) {
        for (int i = 3; i < stones.length; i++) {
            if (stones[i] > stones[i - 1] * 2) {
                return false;
            }
        }
        Set<Integer> stonePositions = stonePositionsInSet(stones);

        int lastStone = stones[stones.length - 1];
        Stack<Integer> jumps = new Stack<>();
        jumps.add(0);

        Stack<Integer> positions = new Stack<>();
        positions.add(0);

        while (!positions.isEmpty()) {
            int position = positions.pop();
            int jumpDistance = jumps.pop();
            for (int i = jumpDistance - 1; i <= jumpDistance + 1; i++) {
                if (i <= 0) {
                    continue;
                }
                int nextPosition = position + i;
                if (nextPosition == lastStone) {
                    return true;
                } else if (stonePositions.contains(nextPosition)) {
                    positions.add(nextPosition);
                    jumps.add(i);
                }
            }
        }
        return false;
    }

    private Set<Integer> stonePositionsInSet(int[] stones) {
        Set<Integer> stonePositions = new HashSet<>();
        for (int stonePosition : stones) {
            stonePositions.add(stonePosition);
        }
        return stonePositions;
    }
}
