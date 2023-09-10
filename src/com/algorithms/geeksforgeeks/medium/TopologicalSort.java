package com.algorithms.geeksforgeeks.medium;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class TopologicalSort {
    /**
     * Given a Directed Graph. Find any Topological Sorting of that Graph.
     *
     * Graph doesn't contain multiple edges, self loops and cycles. Graph may be disconnected.
     *
     * Expected Time Complexity: O(V + E).
     * Expected Auxiliary Space: O(V).
     *
     * https://practice.geeksforgeeks.org/problems/topological-sort/1/?track=md-graph&batchId=144
     */
    static int[] topoSort(List<List<Integer>> list, int N) {
        int[] inDegrees = inDegrees(list, N);
        Queue<Integer> independentVertices = getIndependentVertices(inDegrees, N);
        return topologicalOrder(list, independentVertices, inDegrees, N);
    }

    private static int[] topologicalOrder(List<List<Integer>> list,
                                          Queue<Integer> independentVertices,
                                          int[] inDegrees,
                                          int N) {
        int index = 0;
        int[] topoSort = new int[N];
        while (!independentVertices.isEmpty()) {
            Integer currentVertex = independentVertices.poll();
            topoSort[index++] = currentVertex;

            List<Integer> adjacentVertices = list.get(currentVertex);
            for (Integer adjacentVertex : adjacentVertices) {
                inDegrees[adjacentVertex]--;
                if (inDegrees[adjacentVertex] == 0) {
                    independentVertices.offer(adjacentVertex);
                }
            }
        }
        return topoSort;
    }

    private static Queue<Integer> getIndependentVertices(int[] inDegrees, int N) {
        Queue<Integer> independentVertices = new LinkedList<>();
        for (int vertex = 0; vertex < N; vertex++) {
            if (inDegrees[vertex] == 0) {
                independentVertices.offer(vertex);
            }
        }
        return independentVertices;
    }

    private static int[] inDegrees(List<List<Integer>> list, int N) {
        int[] inDegree = new int[N];
        for (int vertex = 0; vertex < N; vertex++) {
            List<Integer> adjacentVertices = list.get(vertex);
            for (Integer adjacentVertex : adjacentVertices) {
                inDegree[adjacentVertex]++;
            }
        }
        return inDegree;
    }
}
