package com.algorithms.ctci.normal.recursionanddp;

import java.util.ArrayList;
import java.util.List;

public class PowerSetOrAllSubsetsOfASet {
    public static void main(String[] args) {
        final List<Integer> input = new ArrayList<>();
        input.add(1);
        input.add(2);
        input.add(3);
        System.out.println(getSubsetsIterative(input));
    }

    /**
     * Power Set: Write a method to return all subsets of a set.
     * <p>
     * This solution will be 0(2^n) in time and space, which is the best we can do.
     */
    public static List<List<Integer>> getSubsets(List<Integer> set) {
        return getSubsets(set, 0);
    }

    public static List<List<Integer>> getSubsetsIterative(List<Integer> set) {
        List<List<Integer>> allSubsets = new ArrayList<>();
        allSubsets.add(new ArrayList<>());
        for (Integer item : set) {
            List<List<Integer>> subsetsWithItem = getSubsetsWithItem(item, allSubsets);
            allSubsets.addAll(subsetsWithItem);
        }
        return allSubsets;
    }

    private static List<List<Integer>> getSubsets(List<Integer> set, int index) {
        List<List<Integer>> allSubsets;
        if (set.size() == index) {
            allSubsets = new ArrayList<>();
            allSubsets.add(new ArrayList<>());
        } else {
            allSubsets = getSubsets(set, index + 1);
            List<List<Integer>> subsetsWithItem = getSubsetsWithItem(set.get(index), allSubsets);
            allSubsets.addAll(subsetsWithItem);
        }
        return allSubsets;
    }

    private static List<List<Integer>> getSubsetsWithItem(int item, List<List<Integer>> allSubsets) {
        List<List<Integer>> subsetsWithItem = new ArrayList<>();
        for (List<Integer> subset : allSubsets) {
            List<Integer> newSubset = new ArrayList<>(subset);
            newSubset.add(item);
            subsetsWithItem.add(newSubset);
        }
        return subsetsWithItem;
    }
}
