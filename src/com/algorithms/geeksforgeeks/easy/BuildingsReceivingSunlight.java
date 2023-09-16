package com.algorithms.geeksforgeeks.easy;

/**
 * Given are the heights of certain Buildings which lie adjacent to each other.
 * Sunlight starts falling from left side of the buildings. If there is a building of certain Height, all the buildings
 * to the right side of it having lesser heights cannot see the sun .
 * The task is to find the total number of such buildings that receive sunlight.
 * <p>
 * https://practice.geeksforgeeks.org/problems/buildings-receiving-sunlight/0
 */
public class BuildingsReceivingSunlight {
    /**
     * Time Complexity: O(N)
     * Auxiliary Space: O(1)
     */
    private static int buildingsReceivingSunlight(int[] A, int N) {
        int count = 1; //fist building will always receive sunlight
        int currentMax = A[0];
        for (int i = 1; i < N; i++) {
            if (A[i] >= currentMax) {
                currentMax = A[i];
                count++;
            }
        }
        return count;
    }
}
