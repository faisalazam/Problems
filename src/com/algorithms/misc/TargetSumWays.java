package com.algorithms.misc;

import java.util.HashMap;
import java.util.Map;

public class TargetSumWays {
    public static void main(String[] args) {
        int T = 3;
        int[] nums = {1, 1, 1, 1, 1};
        System.out.println("Top down without cache => " + targetSum(nums, T));
        System.out.println("Top down with cache => " + targetSumWithCache(nums, T));
    }

    /**
     * Time Complexity is O(2^n), where n is the number of numbers
     * Space Complexity is O(n), where n is the number of numbers
     */
    private static int targetSum(int[] nums, int T) {
        return targetSum(nums, T, 0, 0);
    }

    private static int targetSum(int[] nums, int T, int index, int sum) {
        if (index == nums.length) {
            return sum == T ? 1 : 0;
        }
        int result1 = targetSum(nums, T, index + 1, sum + nums[index]);
        int result2 = targetSum(nums, T, index + 1, sum - nums[index]);
        return result1 + result2;
    }

    /**
     * Time Complexity is O(index * sum(nums)), where n is the number of numbers
     * Space Complexity is O(index * sum(nums)), where n is the number of numbers
     */
    private static int targetSumWithCache(int[] nums, int T) {
        //  index         sum     result
        Map<Integer, Map<Integer, Integer>> resultsCache = new HashMap<>();
        return targetSumWithCache(nums, T, 0, 0, resultsCache);
    }

    private static int targetSumWithCache(int[] nums, int T, int index, int sum, Map<Integer, Map<Integer, Integer>> resultsCache) {
        if (index == nums.length) {
            return sum == T ? 1 : 0;
        } else if (!resultsCache.containsKey(index)) {
            resultsCache.put(index, new HashMap<>());
        }
        Integer cachedResult = resultsCache.get(index).get(sum);
        if (cachedResult != null) {
            return cachedResult;
        }

        int result1 = targetSumWithCache(nums, T, index + 1, sum + nums[index], resultsCache);
        int result2 = targetSumWithCache(nums, T, index + 1, sum - nums[index], resultsCache);
        int result = result1 + result2;
        resultsCache.get(index).put(sum, result);
        return result;
    }
}
