package com.algorithms.leetcode.medium;

import java.util.ArrayList;
import java.util.List;

public class FindMinInCircularSorterArray {
    public static void main(String[] args) {
        List<Integer> a = new ArrayList<>();
        a.add(5);
        a.add(6);
        a.add(7);
        a.add(8);
        a.add(1);
        a.add(2);
        a.add(3);
        a.add(4);
        System.out.println(findMin(a));
    }

    /**
     * Suppose an array sorted in ascending order is rotated at some pivot unknown to you beforehand.
     * <p>
     * (i.e.,  [0,1,2,4,5,6,7] might become  [4,5,6,7,0,1,2]).
     * <p>
     * Find the minimum element.
     * <p>
     * You may assume no duplicate exists in the array.
     * <p>
     * https://leetcode.com/problems/find-minimum-in-rotated-sorted-array/
     */
    private static int findMin(final List<Integer> a) {
        if (a.isEmpty()) {
            return -1;
        }
        final int pivotIndex = findPivotIndex(a);
        return a.get(pivotIndex);
    }

    // This will find the index of the smallest number in the rotated sorted collection
    private static int findPivotIndex(final List<Integer> a) {
        if (a.size() == 1) {
            return 0;
        }

        int low = 0;
        int high = a.size() - 1;
        while (low < high) {
            int mid = low + (high - low) / 2;
            if (a.get(mid) > a.get(high)) {
                low = mid + 1;
            } else {
                high = mid;
            }
        }
        return low;
    }

    //Second variation
    private static int findPivotIndexV2(final List<Integer> a) {
        if (a.size() == 1) {
            return 0;
        }

        int low = 0;
        int high = a.size() - 1;
        while (low < high) {
            int mid = low + (high - low) / 2;
            if (mid > 0 && a.get(mid) < a.get(mid - 1)) {
                return mid;
            } else if (a.get(low) <= a.get(mid) && a.get(mid) > a.get(high)) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return low;
    }
}
