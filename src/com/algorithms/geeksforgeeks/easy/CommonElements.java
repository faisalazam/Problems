package com.algorithms.geeksforgeeks.easy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class CommonElements {
    /**
     * Given two lists V1 and V2 of sizes n and m respectively. Return the list of elements common to both the lists
     * and return the list in sorted order. Duplicates may be there in the output list.
     * <p>
     * https://practice.geeksforgeeks.org/problems/common-elements5420/1
     */
    private static ArrayList<Integer> commonElements(int[] v1, int[] v2) {
        Arrays.sort(v1);
        Arrays.sort(v2);

        int i = 0, j = 0;
        final ArrayList<Integer> result = new ArrayList<>();
        while (i < v1.length && j < v2.length) {
            if (v1[i] > v2[j]) { // that means j should be incremented as smaller numbers won't be there in v1
                j++;
            } else if (v2[j] > v1[i]) {
                i++;
            } else {
                result.add(v1[i]);
                i++;
                j++;
            }
        }
        return result;
    }

    private static ArrayList<Integer> commonElementsV3(int[] v1, int[] v2) {
        final Map<Integer, Integer> frequenciesMap = new HashMap<>(); // Not using TreeMap as v2 will be sorted later
        for (int num : v1) {
            frequenciesMap.put(num, frequenciesMap.getOrDefault(num, 0) + 1);
        }
        Arrays.sort(v2);
        final ArrayList<Integer> result = new ArrayList<>();
        for (int num : v2) {
            final int frequency = frequenciesMap.getOrDefault(num, 0);
            if (frequency > 0) {
                result.add(num);
                frequenciesMap.put(num, frequency - 1);
            }
        }
        return result;
    }

    private static ArrayList<Integer> commonElementsV2(int[] v1, int[] v2) {
        // The map is sorted according to the natural ordering of its keys.
        // Note that this implementation is not synchronized.
        // This implementation provides guaranteed log(n) time cost for the containsKey, get, put and remove operations.
        final Map<Integer, Integer> frequenciesMap = new TreeMap<>();
        for (int num : v1) {
            frequenciesMap.put(num, frequenciesMap.getOrDefault(num, 0) + 1);
        }
        final ArrayList<Integer> result = new ArrayList<>();
        Arrays.sort(v2); // Doesn't harm as the use of TreeMap is already resulting in sort of  O(N * log(N)) complexity
        for (int num : v2) {
            final int frequency = frequenciesMap.getOrDefault(num, 0);
            if (frequency > 0) {
                result.add(num);
                frequenciesMap.put(num, frequency - 1);
            }
        }
        return result;
    }

    private static ArrayList<Integer> commonElementsV1(int[] v1, int[] v2) {
        // The map is sorted according to the natural ordering of its keys.
        // Note that this implementation is not synchronized.
        // This implementation provides guaranteed log(n) time cost for the containsKey, get, put and remove operations.
        final Map<Integer, Integer> v1FrequenciesMap = new TreeMap<>();
        for (int num : v1) {
            v1FrequenciesMap.put(num, v1FrequenciesMap.getOrDefault(num, 0) + 1);
        }
        // Using simple map here as ordering can be managed through the other map
        final Map<Integer, Integer> v2FrequenciesMap = new HashMap<>();
        for (int num : v2) {
            v2FrequenciesMap.put(num, v2FrequenciesMap.getOrDefault(num, 0) + 1);
        }

        final ArrayList<Integer> result = new ArrayList<>();
        for (Map.Entry<Integer, Integer> frequencyEntry : v1FrequenciesMap.entrySet()) {
            final int key = frequencyEntry.getKey();
            final int frequency = Math.min(frequencyEntry.getValue(), v2FrequenciesMap.getOrDefault(key, 0));
            for (int i = 0; i < frequency; i++) {
                result.add(key);
            }
        }
        return result;
    }

    /**
     * Given three increasingly sorted arrays A, B, C of sizes N1, N2, and N3 respectively,
     * you need to print all common elements in these arrays.
     * <p>
     * Note: Please avoid printing the same common element more than once.
     * <p>
     * https://practice.geeksforgeeks.org/problems/common-elements/0
     */
    private static ArrayList<Integer> commonElementsV0(int[] A, int[] B, int[] C) {
        // The map is sorted according to the natural ordering of its keys.
        // Note that this implementation is not synchronized.
        // This implementation provides guaranteed log(n) time cost for the containsKey, get, put and remove operations.
        final Map<Integer, Integer> frequenciesMap = new TreeMap<>();
        for (int num : A) {
            frequenciesMap.putIfAbsent(num, 1);
        }
        for (int num : B) {
            Integer frequency = frequenciesMap.get(num);
            if (frequency != null && frequency == 1) {
                frequenciesMap.put(num, 2);
            }
        }
        for (int num : C) {
            Integer frequency = frequenciesMap.get(num);
            if (frequency != null && frequency == 2) {
                frequenciesMap.put(num, 3);
            }
        }

        final ArrayList<Integer> result = new ArrayList<>();
        for (Map.Entry<Integer, Integer> frequencyEntry : frequenciesMap.entrySet()) {
            if (frequencyEntry.getValue() == 3) {
                result.add(frequencyEntry.getKey());
            }
        }
        return result;
    }
}
