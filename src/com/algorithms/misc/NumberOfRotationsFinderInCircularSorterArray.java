package com.algorithms.misc;

import java.util.ArrayList;
import java.util.List;

public class NumberOfRotationsFinderInCircularSorterArray {
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

    private static int findNumberOfRotations(final List<Integer> a) {
        return findMin(a);
    }

    /*
     * See @FinderInCircularSorterArray.findPivotIndex(...), that might be a simpler implementation for finding pivot
     */
    private static int findMin(final List<Integer> a) {
        int low = 0;
        int size = a.size();
        int high = size - 1;

        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (a.get(low) <= a.get(high)) {
                return low;
            }
            int midValue = a.get(mid);
            int nextValue = a.get((mid + 1) % size); // using the % just in case if mid is the last element in the list
            int previousValue = a.get((mid + size - 1) % size); // added size so that index does not become -ve number in case mid is first element in the list
            if (midValue <= previousValue && midValue <= nextValue) {
                return mid;
            } else if (midValue <= a.get(high)) {
                high = mid - 1;
            } else if (midValue >= a.get(low)) {
                low = mid + 1;
            }
        }
        return -1;
    }
}
