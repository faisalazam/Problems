package com.algorithms.leetcode.medium;

import java.util.ArrayList;
import java.util.List;

public class FinderInCircularSorterArray {
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
        System.out.println(findIndex(7, a));
    }

    /*
     * Suppose an array sorted in ascending order is rotated at some pivot unknown to you beforehand.
     * (i.e., [0,1,2,4,5,6,7] might become [4,5,6,7,0,1,2]).
     *
     * You are given a target value to search. If found in the array return its index, otherwise return -1.
     *
     * You may assume no duplicate exists in the array.
     *
     * Your algorithm's runtime complexity must be in the order of O(log n).
     *
     * https://leetcode.com/problems/search-in-rotated-sorted-array/
     */
    private static int findIndex(final int target, final List<Integer> a) {
        final int pivotIndex = findPivotIndex(a);
        if (pivotIndex == -1) {
            return -1;
        }

        int low = 0;
        int size = a.size();
        int high = size - 1;
        if (target >= a.get(pivotIndex) && target <= a.get(high)) {
            low = pivotIndex;
        } else {
            high = pivotIndex;
        }

        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (a.get(mid) == target) {
                return mid;
            } else if (a.get(mid) < target) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return -1;
    }

    // This will find the index of the smallest number in the rotated sorted collection
    private static int findPivotIndex(final List<Integer> a) {
        if (a.size() == 1) {
            return 0;
        }

        int mid = -1;
        int low = 0;
        int high = a.size() - 1;
        while (low < high) {
            mid = low + (high - low) / 2;
            if (a.get(mid) > a.get(high)) {
                low = mid + 1;
            } else {
                high = mid;
            }
        }
        return mid == -1 ? -1 : low;
    }
}
