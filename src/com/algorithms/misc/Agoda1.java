package com.algorithms.misc;


import java.util.HashSet;
import java.util.Set;

// Main class should be named 'Solution'
class Agoda1 {
    /*
    Given an unsorted array of size n. Array elements are in the range from 1 to n. One number from set {1, 2, …n}
    is missing and one number occurs twice in the array. Find these two numbers.

    range is from lets say 3 -> 6

    Examples:

    Input: arr[] = {3, 1, 3}
    Output: Missing = 2, Repeating = 3
    Explanation: In the array,
    2 is missing and 3 occurs twice

    (sumOf1ToN - (7 - 3)) = result

    arr[] = {1, 1} or {2, 2}


    Input: arr[] = {4, 3, 6, 2, 1, 1}
    Output: Missing = 5, Repeating = 1

    ((6*7)/2) - (17 - 1) = 21 - 16 = 5
    */
    public static void main(String[] args) {

        int arr[] = {4, 3, 6, 2, 1, 1};

        final Result result = calculateResult(arr);

        System.out.println("Missing = " + result.missing + ", Repeating = " + result.repeated);
    }

    private static Result calculateResult(int arr[]) {
        int repeated = findDuplicate(arr);
        int missing = calculateMissing(arr, repeated);
        return new Result(missing, repeated);
    }

    private static int calculateMissing(int arr[], int repeated) {
        int sum = 0;
        int size = arr.length;
        for (int i = 0; i < size; i++) {
            sum += arr[i];
        }

        return ((size * (size + 1)) / 2) - (sum - repeated);
    }

    private static int findDuplicate(int arr[]) {
        final Set<Integer> values = new HashSet<>();
        for (int i = 0; i < arr.length; i++) {
            int value = arr[i];
            if (values.contains(arr[i])) {
                return value;
            }
            values.add(value);
        }
        return -1;
    }

    static class Result {
        int missing;
        int repeated;

        Result(int missing, int repeated) {
            this.missing = missing;
            this.repeated = repeated;
        }
    }
}

/*
Design Leetcode like system
seaching
Signup -- not needed
code editor -- not needed
Discussion -- not needed
Language choice -- not needed
Submit the code (predefined test case)
Running the code (containers)
Autoscaling for containers.
cache before load balancer
*/


    /**
     *
     * SquareSubArrayFinder for SRC
     *
     /*
     * Complete the 'largestArea' function below.
     *
     * The function is expected to return an INTEGER.
     * The function accepts 2D_INTEGER_ARRAY samples as parameter.
     */

//Better Square Submatrix solution => improved by adding cache => O(n * m)
//and space complexity O(n * m) due to cached result
//Now we only need to recursively compute each value once
//    public static int largestArea(List<List<Integer>> samples) {
//        int maxArea = 0;
//        int targetValue = 1;
//        int noOfRows = samples.size();
//        int noOfColumns = samples.size(); // It is a square matrix
//        int[][] resultsCache = newResultsCache(noOfRows, noOfColumns);
//        for (int rowIndex = 0; rowIndex < noOfRows; rowIndex++) {
//            for (int columnIndex = 0; columnIndex < noOfColumns; columnIndex++) {
//                if (samples.get(rowIndex).get(columnIndex) == targetValue) {
//                    maxArea = Math.max(maxArea, largestArea(targetValue, rowIndex, columnIndex, samples, resultsCache));
//                }
//            }
//        }
//
//        return maxArea;
//
//    }
//
//    private static int largestArea(int targetValue, int rowIndex, int columnIndex, List<List<Integer>> samples, int[][] resultsCache) {
//        if (isNotValid(rowIndex, columnIndex, samples)) {
//            return 0;
//        } else if (targetValue != samples.get(rowIndex).get(columnIndex)) {
//            return 0;
//        } else if (resultsCache[rowIndex][columnIndex] != -1) {
//            return resultsCache[rowIndex][columnIndex];
//        }
//
//        int rightCellValue = largestArea(targetValue, rowIndex, columnIndex + 1, samples, resultsCache);
//        int belowCellValue = largestArea(targetValue, rowIndex + 1, columnIndex, samples, resultsCache);
//        int belowRightCellValue = largestArea(targetValue, rowIndex + 1, columnIndex + 1, samples, resultsCache);
//        resultsCache[rowIndex][columnIndex] = 1 + Math.min(
//                belowRightCellValue,
//                Math.min(rightCellValue, belowCellValue)
//        );
//        return resultsCache[rowIndex][columnIndex];
//    }
//
//    private static boolean isNotValid(int rowIndex, int columnIndex, List<List<Integer>> samples) {
//        return rowIndex < 0 || rowIndex >= samples.size() || columnIndex < 0 || columnIndex >= samples.get(rowIndex).size();
//    }
//
//    private static int[][] newResultsCache(int noOfRows, int noOfColumns) {
//        int[][] resultsCache = new int[noOfRows][noOfColumns];
//        for (int rowIndex = 0; rowIndex < noOfRows; rowIndex++) {
//            for (int columnIndex = 0; columnIndex < noOfColumns; columnIndex++) {
//                resultsCache[rowIndex][columnIndex] = -1;
//            }
//        }
//        return resultsCache;
//    }


    /**
     * Tried to solve it using sliding window approach, but it could've been solved iterating the list k times and with
     * each iteration, removing the max element, diving by 2 and putting it back at the same index; and then iterating
     * the updated list again to sum all elements.
     * @param nums
     * @param k
     * @return
     */
//
//    public static int minSum(List<Integer> nums, int k) {
//        int minSum = Integer.MAX_VALUE;
//
//        // Consider all blocks starting with i.
//        for (int i = 0; i < nums.size() - k + 1; i++) {
//            int currentSum = 0;
//            for (int j = 0; j < k; j++) {
//                final int ceilValue = (int) Math.ceil(nums.remove(i + j)/2);
//                nums.add(i + j, ceilValue);
//                currentSum = currentSum + nums.get(i + j);
//            }
//            minSum = Math.min(currentSum, minSum);
//        }
//        return minSum;
//    }