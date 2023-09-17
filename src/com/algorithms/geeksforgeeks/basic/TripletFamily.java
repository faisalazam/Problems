package com.algorithms.geeksforgeeks.basic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TripletFamily {
    /**
     * Given an array A of integers. Find three numbers such that sum of two elements equals the third element
     * and return the triplet in a container result, if no such triplet is found return the container as empty.
     * <p>
     * https://practice.geeksforgeeks.org/problems/triplet-family/1
     * <p>
     * Time Complexity: O(N²)
     * Auxiliary Space: O(1)
     */
    public static ArrayList<Integer> findTriplet(int[] arr, int n) {
        Arrays.sort(arr);

        final ArrayList<Integer> result = new ArrayList<>();
        for (int i = n - 1; i >= 2; i--) { // i >= 2 to accommodate the left and right variables below
            int left = 0;
            int right = i - 1;
            while (left < right) {
                final int targetSum = arr[i];
                final int currSum = arr[right] + arr[left];
                if (targetSum == currSum) {
                    result.add(targetSum);
                    result.add(arr[left]);
                    result.add(arr[right]);
                    return result;
                } else if (targetSum > currSum) {
                    left++;
                } else {
                    right--;
                }
            }
        }
        return result;
    }

    // O(n²)
    public static List<Integer> findTripletV2(int[] arr, int n) {
        final Set<Integer> nums = new HashSet<>();
        for (int i = 0; i < n; i++) {
            nums.add(arr[i]);
        }

        final List<Integer> result = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) { // j = 0 works too
                int sum = arr[i] + arr[j];
                if (i != j && nums.contains(sum)) {
                    result.add(arr[i]);
                    result.add(arr[j]);
                    result.add(sum);
                    return result;
                }
            }
        }
        return result;
    }

    // Time Complexity: O(N² * logN)
    // Auxiliary Space: O(1)
    public static void findTripletV1(int[] arr, int n) {

        // Sorting the array
        Arrays.sort(arr);

        // Initialising nested loops
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {

                // Finding the sum of the numbers
                if (binarySearch((arr[i] + arr[j]), j, n - 1, arr)) {

                    // Printing out the first triplet
                    System.out.print("Numbers are: " + arr[i] + " " +
                            arr[j] + " " + (arr[i] + arr[j]));
                    return;
                }
            }
        }

        // If no such triplets are found
        System.out.print("No such numbers exist");
    }

    // Time Complexity: O(N^3)
    // Auxiliary Space: O(1)
    public static void findTripletV0(int[] arr, int n) {
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                for (int k = j + 1; k < n; k++) {
                    if ((arr[i] + arr[j] == arr[k])
                            || (arr[i] + arr[k] == arr[j])
                            || (arr[j] + arr[k] == arr[i])) {

                        // printing out the first triplet
                        System.out.println("Numbers are: " + arr[i] + " " + arr[j] + " " + arr[k]);
                        return;
                    }
                }
            }
        }
        // No such triplet is found in array
        System.out.println("No such triplet exists");
    }

    private static boolean binarySearch(int sum, int start, int end, int[] arr) {
        while (start <= end) {
            int mid = start + ((end - start) / 2);
            if (arr[mid] == sum) {
                return true;
            } else if (arr[mid] > sum) {
                end = mid - 1;
            } else {
                start = mid + 1;
            }
        }
        return false;
    }
}
