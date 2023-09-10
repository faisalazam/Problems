package com.algorithms.leetcode.medium;

public class ProductOfArrayExceptSelf {
    /*
     * Given an array nums of n integers where n > 1,
     * return an array output such that output[i] is equal to the product of all the elements of nums except nums[i]
     *
     * Note: Please solve it without division and in O(n).
     * (because, if we are allowed to divide, then we can calculate the product of whole array in one pass,
     * and then divide the whole product with nums[i] to get the product except self...)
     *
     * Follow up: Could you solve it with constant space complexity?
     * (The output array does not count as extra space for the purpose of space complexity analysis.)
     *
     * https://leetcode.com/problems/product-of-array-except-self/
     */
    private static int[] productExceptSelf(int[] nums) {
        int size = nums.length;
        int[] productsExceptSelf = new int[size];

        productsExceptSelf[0] = 1;
        for (int i = 1; i < size; i++) {
            productsExceptSelf[i] = nums[i - 1] * productsExceptSelf[i - 1];
        }

        int rightProduct = 1;
        for (int i = size - 1; i >= 0; i--) {
            productsExceptSelf[i] = productsExceptSelf[i] * rightProduct;
            rightProduct *= nums[i];
        }
        return productsExceptSelf;
    }

    /*
     * Using extra space
     */
    private static int[] productExceptSelfV1(int[] nums) {
        int size = nums.length;
        int[] leftProducts = new int[size];
        int[] rightProducts = new int[size];
        int[] productsExceptSelf = new int[size];

        leftProducts[0] = 1;
        rightProducts[size - 1] = 1;

        for (int i = 1; i < size; i++) {
            leftProducts[i] = nums[i - 1] * leftProducts[i - 1];
        }
        for (int i = size - 2; i >= 0; i--) {
            rightProducts[i] = nums[i + 1] * rightProducts[i + 1];
        }
        for (int i = 0; i < size; i++) {
            productsExceptSelf[i] = leftProducts[i] * rightProducts[i];
        }

        return productsExceptSelf;
    }
}
