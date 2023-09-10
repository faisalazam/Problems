package com.algorithms.geeksforgeeks.medium;

import java.util.Arrays;
import java.util.List;

public class DetectCycleInAnUndirectedGraph {
    /**
     * Given a Undirected Graph. Check whether it contains a cycle or not.
     * <p>
     * https://practice.geeksforgeeks.org/problems/detect-cycle-in-an-undirected-graph/1
     */
    static boolean isCyclic(List<List<Integer>> list, int V) {
        boolean[] visited = new boolean[V];
        for (int srcVertex = 0; srcVertex < V; srcVertex++) {
            if (!visited[srcVertex] && isCyclic(srcVertex, list, -1, visited)) {
                return true;
            }
        }
        return false;
    }

    private static boolean isCyclic(int srcVertex, List<List<Integer>> list, int parent, boolean[] visited) {
        visited[srcVertex] = true;
        List<Integer> adjacentVertices = list.get(srcVertex);
        for (int destVertex : adjacentVertices) {
            if (!visited[destVertex]) {
                if (isCyclic(destVertex, list, srcVertex, visited)) {
                    return true;
                }
            } else if (destVertex != parent) {
                return true;
            }
        }
        return false;
    }


    //Not Working
    static boolean isCyclicV1(List<List<Integer>> list, int V) {
        int[] parents = new int[V];
        Arrays.fill(parents, -1);
        /*
        0 1 1 2 2 3
        Your Output is:
        (0, 1)
        (1, 0) (1, 2)
        (2, 1) (2, 3)
        (3, 2)
        */
        for (int srcVertex = 0; srcVertex < V; srcVertex++) {
            List<Integer> adjacentVertices = list.get(srcVertex);
            for (int destVertex : adjacentVertices) {
                int srcSubset = find(parents, srcVertex);
                int destSubset = find(parents, destVertex);
                System.out.print("(" + srcVertex + ", " + destVertex + ") ");
                if (srcSubset == destSubset) {
                    return true;
                }
                union(parents, srcSubset, destSubset);
            }
            System.out.println();
        }
        return false;
    }

    private static void union(int[] parents, int srcSubset, int destSubset) {
        int subset1 = find(parents, srcSubset);
        int subset2 = find(parents, destSubset);
        parents[subset1] = subset2;
    }

    private static int find(int[] parents, int index) {
        if (parents[index] == -1) {
            return index;
        }
        return find(parents, parents[index]);
    }
}
