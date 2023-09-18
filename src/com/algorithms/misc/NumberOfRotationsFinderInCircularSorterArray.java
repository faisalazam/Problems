package com.algorithms.misc;

import com.algorithms.leetcode.medium.FinderInCircularSorterArray;

import java.util.ArrayList;
import java.util.List;

public class NumberOfRotationsFinderInCircularSorterArray {
    private static int findNumberOfRotations(final List<Integer> a) {
        return findMin(a);
    }

    /**
     * See {@link FinderInCircularSorterArray#findPivotIndex(...)}, that might be nums simpler implementation for finding
     * pivot
     */
    private static int findMin(final List<Integer> nums) {
        int low = 0;
        int size = nums.size();
        int high = size - 1;

        while (low <= high) {
            final int mid = low + ((high - low) / 2);
            // Look at this rotated array for example [4,5,6,7,0,1,2], the following condition will only be true when
            // low is pointing at an index which holds the minimum value in the array
            if (nums.get(low) <= nums.get(high)) {
                return low;
            }
            final int midValue = nums.get(mid);
            // Using the % just in case if mid is the last element in the list
            // Added size so that index does not become -ve number in case mid is first element in the list
            final int nextValue = nums.get((mid + 1) % size);
            final int previousValue = nums.get((mid + size - 1) % size);
            if (previousValue >= midValue && midValue <= nextValue) { // It's possible only for the min value
                return mid;
            } else if (midValue <= nums.get(high)) {
                high = mid - 1;
            } else if (midValue >= nums.get(low)) {
                low = mid + 1;
            }
        }
        return -1;
    }

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
        System.out.println(findNumberOfRotations(a));
    }
}
