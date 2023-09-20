package com.algorithms.ctci.normal.recursionanddp;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Objects;

/**
 * Power Set: Write a method to return all subsets of a set.
 * <p>
 * How many Subsets are possible for an Array of size ‘N’ ?
 * Before jumping into the solution can we observe some kind of relation between the size of array i.e. N and the number
 * of subsets formed by that array? The answer is YES, there does exist a relation that is given by the following formula:
 * <p>
 * Number of Subsets of an array of size N = 2^N
 * <p>
 * Proof: For each element of the array we have 2 choices:
 * Choice 1: Include it into the subset.
 * Choice 2: Exclude it from the subset.
 * <p>
 * Since each element has 2 choice to contribute into the subset and we have total N elements,
 * therefore total subsets = 2^N
 * <p>
 * https://practice.geeksforgeeks.org/problems/subsets-1613027340/1
 */
public class PowerSetOrAllSubsetsOfASet {

    /**
     * Time Complexity: O(2^N), where N is the size of given array, which is the best we can do.
     * Auxiliary Space: O(2^N) : if we would store all the subsets we will need 2^N memory blocks to store each subset
     */
    public static ArrayList<ArrayList<Integer>> getSubsets(ArrayList<Integer> set) {
        // Recursive solution is not submitted because sorting is not as expected by geeksforgeeks
        final ArrayList<ArrayList<Integer>> powerSet = getSubsets(set, 0);
        powerSet.stream()
                .filter(subset -> !subset.isEmpty())
                .forEach(subset -> subset.sort(Comparator.naturalOrder()));
        powerSet.sort(new SetComparator());
        return powerSet;
    }

    /**
     * Time Complexity: O(2^N), where N is the size of given array, which is the best we can do.
     * Auxiliary Space: O(2^N) : if we would store all the subsets we will need 2^N memory blocks to store each subset
     */
    public static ArrayList<ArrayList<Integer>> getSubsetsIterative(ArrayList<Integer> set) {
        final ArrayList<ArrayList<Integer>> allSubsets = new ArrayList<>();
        allSubsets.add(new ArrayList<>());
        for (int item : set) {
            final ArrayList<ArrayList<Integer>> subsetsWithItem = getSubsetsWithItem(item, allSubsets);
            allSubsets.addAll(subsetsWithItem);
        }
        allSubsets.sort(new SetComparator());
        return allSubsets;
    }

    private static ArrayList<ArrayList<Integer>> getSubsets(ArrayList<Integer> set, int index) {
        final ArrayList<ArrayList<Integer>> allSubsets;
        if (set.size() == index) {
            allSubsets = new ArrayList<>();
            allSubsets.add(new ArrayList<>());
        } else {
            allSubsets = getSubsets(set, index + 1);
            final ArrayList<ArrayList<Integer>> subsetsWithItem = getSubsetsWithItem(set.get(index), allSubsets);
            allSubsets.addAll(subsetsWithItem);
        }
        return allSubsets;
    }

    private static ArrayList<ArrayList<Integer>> getSubsetsWithItem(int item, ArrayList<ArrayList<Integer>> allSubsets) {
        final ArrayList<ArrayList<Integer>> subsetsWithItem = new ArrayList<>();
        for (ArrayList<Integer> subset : allSubsets) {
            final ArrayList<Integer> newSubset = new ArrayList<>(subset);
            newSubset.add(item);
            subsetsWithItem.add(newSubset);
        }
        return subsetsWithItem;
    }

    /*
     * Another Approach
     * <p>
     * Printing all Subsets Using Bit Manipulation
     * <p>
     * This approach is easier compared to backtraking as it just requires basic knowledge of bits.
     * <p>
     * Observation: A bit can be either 0 or 1. What can we deduce from this?
     * <p>
     * Since we know that in a subset each element has only two choices i.e. either get included or get excluded. we
     * can assign these choices to a bit representation such that:
     * 0 means Excluded
     * 1 means Included
     * i’th bit represents i’th element of the array
     * <p>
     * Now let’s say we have N elements in our array, we know this array will have 2^N subsets, these subsets can be
     * uniquely expressed in form of Bit representation of number from 0 to (2^N)-1.
     * <p>
     * Suppose we have elements in an array of size 2 = {A, B}
     * We can easily calculate all the subsets of this array from the bit representation of number
     * from 0 to (2^2)-1 i.e. 0 to 3
     * <p>
     * 0 = 00 => A excluded, B excluded => { empty }
     * 1 = 01 => A excluded, B included => { B }
     * 2 = 10 => A included, B excluded => { A }
     * 3 = 11 => A included, B included=> { A, B }
     * <p>
     * https://www.geeksforgeeks.org/backtracking-to-find-all-subsets/
     * https://www.geeksforgeeks.org/print-subsets-given-size-set/
     */

    private static class SetComparator implements Comparator<ArrayList<Integer>> {
        public int compare(ArrayList<Integer> a, ArrayList<Integer> b) {
            final int size = Math.min(a.size(), b.size());
            for (int i = 0; i < size; i++) {
                if (!Objects.equals(a.get(i), b.get(i))) {
                    return Integer.compare(a.get(i), b.get(i));
                }
            }
            return Integer.compare(a.size(), b.size());
        }
    }

    public static void main(String[] args) {
        final ArrayList<Integer> input = new ArrayList<>();
//        input.add(1);
//        input.add(2);
//        input.add(3);
        input.add(5);
        input.add(6);
        input.add(6);
        input.add(3);
        System.out.println(getSubsetsIterative(input));
        System.out.println(getSubsets(input));
    }
}
