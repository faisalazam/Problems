package com.algorithms.geeksforgeeks.easy;

/**
 * Given an unsorted array of size N. Find the first element in array such that all of its left elements are smaller
 * and all right elements to it are greater than it.
 * <p>
 * https://practice.geeksforgeeks.org/problems/unsorted-array/0
 */
public class ElementWithLeftSideSmallerAndRightSideGreater {
    /**
     * Time Complexity: O(n), The time complexity of this program is O(n) where n is the size of the input array.
     * This is because the program iterates through the array only once to find the element that satisfies the given
     * condition.
     * <p>
     * Auxiliary Space: O(1), The space complexity of this program is O(1) because it uses only a constant amount of
     * extra space to store some variables like element, maxx, bit, check, and idx, which are not dependent on the
     * input size. Therefore, the space used by the program does not increase with the size of the input array.
     */
    private static int findElementOptimised(int[] arr, int n) {
        if (n == 1 || n == 2) {
            return -1;
        }
        int leftMax = arr[0]; // leftMax is the value which is maximum on the left side of the array.
        int candidateElement = arr[0]; // possible candidate for the solution of the problem.

        // Traverse array from 1st to n-1 th index because Extreme elements can't be aur answer
        int i = 1;
        while (i < n - 1) {
            int currentValue = arr[i];
            // find the index of possible candidateElement with left side smaller
            if (leftMax > currentValue && i < n - 1) {
                i++;
                continue;
            }
            // Update the possible candidateElement if the currentValue is greater than the leftMax (maximum candidateElement so far).
            // In while loop we sur-pass the value which is greater than the candidateElement
            if (currentValue >= leftMax) {
                i++;
                leftMax = currentValue;
                candidateElement = currentValue;
            }

            while (arr[i] >= candidateElement && i < n - 1) {
                if (arr[i] > leftMax) {
                    leftMax = arr[i];
                }
                i++;
            }
            if (candidateElement <= arr[n - 1] && i == n - 1) {
                return candidateElement;
            }
        }
        return -1;
    }

    /**
     * Time Complexity: O(n), The program uses two loops to traverse the input array, one from right to left and another
     * from left to right. The time complexity of the first loop is O(n) and that of the second loop is also O(n),
     * so the overall time complexity of the program is O(n).
     * Auxiliary Space: O(n), The program uses an extra array of size n to store the maximum of all left elements,
     * so the space complexity of the program is O(n).
     */
    private static int findElementV2(int[] arr, int n) {
        int minSoFar = Integer.MAX_VALUE;
        final boolean[] right = new boolean[n];
        right[n - 1] = true;
        for (int i = n - 2; i > 0; i--) {
            minSoFar = Math.min(minSoFar, arr[i + 1]);
            right[i] = arr[i] <= minSoFar;
        }
        // Traverse array from 1st to n-1 th index because Extreme elements can't be aur answer
        int maxSoFar = arr[0];
        for (int i = 1; i < n - 1; i++) {
            if (arr[i] >= maxSoFar && right[i]) {
                return arr[i];
            }
            maxSoFar = Math.max(maxSoFar, arr[i - 1]);
        }
        return -1;
    }

    /**
     * Time Complexity: O(n), The program uses three loops to traverse the input array, one from left to right, then
     * from right to left, and finally left to right to find the element. The time complexity of the all loops here is
     * O(n), so the overall time complexity of the program is O(n).
     * Auxiliary Space: O(n), The program uses two extra arrays of size n, so the space complexity of the program is
     * O(n) + O(n) => O(n).
     */
    private static int findElementV1(int[] arr, int n) {
        int maxSoFar = Integer.MIN_VALUE;
        final boolean[] left = new boolean[n];
        left[0] = true;
        for (int i = 1; i < n; i++) {
            maxSoFar = Math.max(maxSoFar, arr[i - 1]);
            left[i] = arr[i] >= maxSoFar;
        }
        int minSoFar = Integer.MAX_VALUE;
        final boolean[] right = new boolean[n];
        right[n - 1] = true;
        for (int i = n - 2; i > 0; i--) {
            minSoFar = Math.min(minSoFar, arr[i + 1]);
            right[i] = arr[i] <= minSoFar;
        }
        // Traverse array from 1st to n-1 th index because Extreme elements can't be aur answer
        for (int i = 1; i < n - 1; i++) {
            if (left[i] && right[i]) {
                return arr[i];
            }
        }
        return -1;
    }

    /**
     * Brute Force Approach
     * <p>
     * A simple solution is to consider every element one by one. For every element, compare it with all elements on
     * the left and all elements on right.
     * <p>
     * Time Complexity: O(n^2), Time complexity of the given program is O(n^2) as there are two nested while loops in
     * the check function, which are iterating over at most n-2 elements each, and they are being called for each
     * element in the array except the first and last elements.
     * Auxiliary Space: O(1), Space complexity of the program is O(1) as no extra space is being used, except for the
     * input array and some integer variables used for indexing and loop control.
     */
    static int findElementV0(int[] arr, int n) {
        // Traverse array from 1st to n-1 th index because Extreme elements can't be aur answer
        for (int i = 1; i < n - 1; i++) {
            if (check(arr, n, i)) {
                return arr[i];
            }
        }
        return -1;
    }

    private static boolean check(int[] arr, int n, int index) {
        int i = index - 1;
        int j = index + 1;

        while (i >= 0) {
            if (arr[i--] > arr[index]) {
                return false;
            }
        }

        while (j < n) {
            if (arr[j++] < arr[index]) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        int[] arr = {10, 6, 3, 1, 5, 11, 6, 1, 11, 12};
        System.out.println(findElementV2(arr, arr.length));
    }
}
