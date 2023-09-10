package com.algorithms.leetcode.medium;

import java.util.Stack;

public class AsteroidCollision {

    /*
     * We are given an array asteroids of integers representing asteroids in a row.
     *
     * For each asteroid, the absolute value represents its size, and the sign represents its direction
     * (positive meaning right, negative meaning left). Each asteroid moves at the same speed.
     *
     * Find out the state of the asteroids after all collisions. If two asteroids meet, the smaller one will explode.
     * If both are the same size, both will explode. Two asteroids moving in the same direction will never meet.
     *
     * https://leetcode.com/problems/asteroid-collision/
     */
    public int[] asteroidCollision(int[] asteroids) {
        Stack<Integer> stack = processAsteroids(asteroids);
        int[] asteroidsAfterCollision = stackToArray(stack);
        return asteroidsAfterCollision;
    }

    private int[] stackToArray(Stack<Integer> stack) {
        int noOfAsteroidsAfterCollision = stack.size();
        int[] asteroidsAfterCollision = new int[noOfAsteroidsAfterCollision];
        for (int i = noOfAsteroidsAfterCollision - 1; i >= 0; i--) {
            asteroidsAfterCollision[i] = stack.pop();
        }
        return asteroidsAfterCollision;
    }

    private Stack<Integer> processAsteroids(int[] asteroids) {
        Stack<Integer> stack = new Stack<>();
        for (int asteroid : asteroids) {
            if (stack.isEmpty() || asteroid > 0) {
                stack.push(asteroid);
                continue;
            }
            manageCollisions(asteroid, stack);
        }
        return stack;
    }

    private void manageCollisions(int asteroid, Stack<Integer> stack) {
        while (true) {
            int peek = stack.peek();
            if (peek < 0) {
                stack.push(asteroid);
                break;
            } else if (peek == -asteroid) {
                // as peek as well as the incoming asteroid are same in size, so both the asteroids will explode
                stack.pop();
                break;
            } else if (peek > -asteroid) {
                // as peek is bigger than the incoming asteroid, so the incoming asteroid will just explode
                break;
            } else {
                // as peek is smaller than the incoming asteroid, so the peek asteroid will just explode
                stack.pop();
                if (stack.isEmpty()) {
                    stack.push(asteroid);
                    break;
                }
            }
        }
    }
}
