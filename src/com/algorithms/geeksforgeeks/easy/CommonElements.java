package com.algorithms.geeksforgeeks.easy;

import java.util.Map;
import java.util.TreeMap;

public class CommonElements {
    /**
     * Given three increasingly sorted arrays A, B, C of sizes N1, N2, and N3 respectively,
     * you need to print all common elements in these arrays.
     * <p>
     * Note: Please avoid printing the same common element more than once.
     * <p>
     * https://practice.geeksforgeeks.org/problems/common-elements/0
     */
    private static String commonElements(int[] A, int[] B, int[] C, int N1, int N2, int N3) {
        Map<Integer, Integer> frequenciesMap = new TreeMap<>();
        for (int i = 0; i < N1; i++) {
            if (!frequenciesMap.containsKey(A[i])) {
                frequenciesMap.put(A[i], 1);
            }
        }
        for (int i = 0; i < N2; i++) {
            Integer frequency = frequenciesMap.get(B[i]);
            if (frequency != null && frequency == 1) {
                frequenciesMap.put(B[i], 2);
            }
        }
        for (int i = 0; i < N3; i++) {
            Integer frequency = frequenciesMap.get(C[i]);
            if (frequency != null && frequency == 2) {
                frequenciesMap.put(C[i], 3);
            }
        }
        StringBuilder commonElementsBuilder = new StringBuilder();
        for (Map.Entry<Integer, Integer> frequencyEntry : frequenciesMap.entrySet()) {
            if (frequencyEntry.getValue() == 3) {
                commonElementsBuilder.append(frequencyEntry.getKey()).append(" ");
            }
        }
        String commonElements = commonElementsBuilder.toString();
        return commonElements.isEmpty() ? "-1" : commonElements;
    }
}
