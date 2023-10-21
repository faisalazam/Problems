package com.algorithms.leetcode.medium;

import java.util.Arrays;
import java.util.Stack;


/**
 * We are given an array asteroids of integers representing asteroids in a row.
 * <p>
 * For each asteroid, the absolute value represents its size, and the sign represents its direction
 * (positive meaning right, negative meaning left). Each asteroid moves at the same speed.
 * <p>
 * Find out the state of the asteroids after all collisions. If two asteroids meet, the smaller one will explode.
 * If both are the same size, both will explode. Two asteroids moving in the same direction will never meet.
 * <p>
 * https://leetcode.com/problems/asteroid-collision/
 */
public class AsteroidCollision {
    /**
     * Intuition
     * The intuition behind the optimized approach is to simulate the asteroid collisions while using an in-place
     * modification of the original array. By iterating through the array and updating it in a way that ensures
     * surviving asteroids are placed in the front of the array, we can avoid using an additional stack for storing the
     * final state of the asteroids.
     * <p>
     * Approach
     * Initialize a variable j to keep track of the position where the next surviving asteroid should be placed in the
     * array.
     * Iterate through the given asteroids array using a loop.
     * For each asteroid, check if it will collide with the asteroids that are already placed in the array.
     * If the current asteroid survives the collision, place it at index j and increment j by 1.
     * If it explodes during the collision, discard it and continue checking the remaining asteroids in the array.
     * The final state of the asteroids will be the elements from index 0 to j-1 in the modified array.
     * The above approach eliminates the need for an additional stack, reduces space complexity, and achieves the same
     * result as the initial approach with improved performance.
     * <p>
     * Complexity
     * Time complexity: O(n)
     * Space complexity: O(1)
     */
    public int[] asteroidCollision(final int[] asteroids) {
        int j = 0;
        final int n = asteroids.length;

        for (int i = 0; i < n; i++) {
            final int asteroid = asteroids[i];
            while (j > 0 && asteroids[j - 1] > 0 && asteroid < 0 && asteroids[j - 1] < Math.abs(asteroid)) {
                j--;
            }

            if (j == 0 || asteroid > 0 || asteroids[j - 1] < 0) {
                asteroids[j++] = asteroid;
            } else if (asteroids[j - 1] == Math.abs(asteroid)) {
                j--;
            }
        }
        return Arrays.copyOfRange(asteroids, 0, j);
    }

    public int[] asteroidCollisionV1(final int[] asteroids) {
        final int noOfAsteroidsAfterCollision = processAsteroids(asteroids, asteroids.length) + 1;
        return Arrays.copyOfRange(asteroids, 0, noOfAsteroidsAfterCollision);
    }

    private int processAsteroids(int[] asteroids, final int size) {
        if (size <= 1) {
            return size;
        }
        int currentIndex = 0;
        int nextIndex = 1;
        while (nextIndex < size) {
            final int nextAsteroid = asteroids[nextIndex];
            if (currentIndex < 0 || asteroids[currentIndex] < 0 || nextAsteroid > 0) {
                asteroids[++currentIndex] = asteroids[nextIndex++];
            } else if (asteroids[currentIndex] == -1 * nextAsteroid) {
                currentIndex--;
                nextIndex++;
            } else if (asteroids[currentIndex] < -1 * nextAsteroid) {
                currentIndex--;
            } else {
                nextIndex++;
            }
        }
        return currentIndex;
    }

    public int[] asteroidCollisionV0(final int[] asteroids) {
        final Stack<Integer> stack = processAsteroids(asteroids);
        return stackToArray(stack); // asteroidsAfterCollision
    }

    private int[] stackToArray(final Stack<Integer> stack) {
        final int noOfAsteroidsAfterCollision = stack.size();
        final int[] asteroidsAfterCollision = new int[noOfAsteroidsAfterCollision];
        for (int i = noOfAsteroidsAfterCollision - 1; i >= 0; i--) {
            asteroidsAfterCollision[i] = stack.pop();
        }
        return asteroidsAfterCollision;
    }

    private Stack<Integer> processAsteroids(final int[] asteroids) {
        final Stack<Integer> stack = new Stack<>();
        for (int asteroid : asteroids) {
            if (stack.isEmpty() || asteroid > 0) {
                stack.push(asteroid);
                continue;
            }
            manageCollisions(asteroid, stack);
        }
        return stack;
    }

    private void manageCollisions(final int asteroid, final Stack<Integer> stack) {
        while (true) {
            final int peek = stack.peek();
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
