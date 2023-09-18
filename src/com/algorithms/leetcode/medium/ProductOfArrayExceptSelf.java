package com.algorithms.leetcode.medium;

/**
 * Given an integer array nums, return an array answer such that answer[i] is equal to the product of all the elements
 * of nums except nums[i].
 * <p>
 * The product of any prefix or suffix of nums is guaranteed to fit in a 32-bit integer.
 * <p>
 * You must write an algorithm that runs in O(n) time and without using the division operation.
 * <p>
 * Note: Please solve it without division and in O(n). (because, if we are allowed to divide, then we can calculate the
 * product of whole array in one pass, and then divide the whole product with nums[i] to get the product except self...)
 * <p>
 * Follow up: Could you solve it with constant space complexity?
 * (The output array does not count as extra space for the purpose of space complexity analysis.)
 * <p>
 * https://leetcode.com/problems/product-of-array-except-self/
 */
public class ProductOfArrayExceptSelf {
    /**
     * Brute Force
     * <p>
     * The first and foremost, the simplest method that comes to mind is, loop through the complete array using a
     * pointer, say j, for every index i, and multiply all the elements at index j except when i == j. This would ensure
     * that the element at index i is skipped from being multiplied.
     * <p>
     * Time Complexity: O(N²)
     * Auxiliary Space: O(1)
     */
    private static int[] productExceptSelfBruteForce(final int[] nums) { // Results in Time Limit Exceeded
        final int size = nums.length;
        final int[] productsExceptSelf = new int[size];
        for (int i = 0; i < size; i++) {
            int product = 1;
            for (int j = 0; j < size; j++) {
                if (i != j) {
                    product *= nums[j];
                }
            }
            productsExceptSelf[i] = product;
        }
        return productsExceptSelf;
    }

    /**
     * Dividing the product of array with the number
     * <p>
     * Find the product of all the numbers of our Array and then divide the product with each element of the array to
     * get the new element for that position in our final answer array.
     * <p>
     * There's one major problem in going with this method is when we have a 0 element in the array. The problem is that,
     * we can't perform a division by 0, as a result, we won't be able to get corresponding values in our final answer
     * array for the indices having 0 in our initial array at that position.
     * <p>
     * Time Complexity: O(N)
     * Auxiliary Space: O(1)
     */
    public int[] productExceptSelfUsingDivision(int[] nums) {
        final int size = nums.length;
        final int[] productsExceptSelf = new int[size];
        int pro = 1;
        for (int i : nums) {
            pro *= i;
        }

        for (int i = 0; i < size; i++) {
            productsExceptSelf[i] = pro / nums[i];
        }
        return productsExceptSelf;
    }

    /**
     * Solution for division by 0 problem - Dividing the product of array with the number
     * <p>
     * Maintain the count of zeroes in the array.
     * 1.) If count of zeroes is greater than 1 so the array will be empty (think about it)
     * 2.) If the count of zeroes is 0 then we need to just divide the product of array with every element
     * 3.) Lastly if the count of zeroes if 1, then we need to find the index of zero and product of array without zero
     * and then just place the product at index of zero and we are done
     * <p>
     * Time Complexity: O(N)
     * Auxiliary Space: O(1)
     */
    public int[] productExceptSelfUsingDivisionContainingZero(int[] nums) {
        int prod = 1;
        int zeroCount = 0;
        for (int num : nums) {
            if (num != 0) {
                prod *= num;
            } else if (++zeroCount == 2) {
                break;
            }
        }

        for (int i = 0; i < nums.length; i++) {
            if (zeroCount == 2) {
                nums[i] = 0;
            } else if (zeroCount == 1) {
                nums[i] = (nums[i] != 0) ? 0 : prod;
            } else {
                nums[i] = prod / nums[i];
            }
        }

        return nums;
    }

    /**
     * Finding Prefix Product and Suffix Product
     * <p>
     * Similar to finding Prefix Sum Array, find the Prefix Product Array and Suffix Product Array for the original
     * array, i.e. pre[i] = pre[i - 1] * a[i - 1] (yes, we multiply with a[i - 1] and not with a[i] on purpose) and
     * similarly suff[i] = suff[i + 1] * a[i + 1].
     * Now, at any index i our final answer ans[i] would be given by ans[i] = pre[i] * suff[i]. Why? Because
     * the pre[i] * suff[i] contains product of every element before i and every element after i but not the element at
     * index i (and that is the reason why we excluded a[i] in our prefix and suffix product).
     * <p>
     * Time Complexity: O(N)
     * Auxiliary Space: O(N) (excluding the final answer array).
     */
    private static int[] productExceptSelfV1(int[] nums) {
        final int size = nums.length;
        final int[] leftProducts = new int[size];
        final int[] rightProducts = new int[size];
        final int[] productsExceptSelf = new int[size];

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

    /**
     * Directly store the product of prefix and suffix into the final answer array
     * <p>
     * The logic is, we don't actually need separate array to store prefix product and suffix products, we can do all
     * the approach discussed in the last method directly onto our final answer array.
     * <p>
     * Time Complexity: O(N)
     * Auxiliary Space: O(1) - The output array 'productsExceptSelf' does not count as extra space for the purpose of
     * space complexity analysis.
     */
    private static int[] productExceptSelf(int[] nums) {
        final int size = nums.length;
        final int[] productsExceptSelf = new int[size];

        productsExceptSelf[0] = 1;
        // Fill productsExceptSelf with left products except self
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
}
