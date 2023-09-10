package com.algorithms.geeksforgeeks.easy;

import java.util.List;
import java.util.Stack;

public class DFSOfGraph {
    /**
     * Given a connected undirected graph. Perform a Depth First Traversal of the graph.
     * Note: Use recursive approach.
     * <p>
     * Your task:
     * You don't need to read input or print anything. Your task is to complete the
     * function dfs() which takes the Graph and the number of vertices as inputs and returns a list
     * containing the DFS Traversal of the graph starting from the 0th node.
     * <p>
     * Expected Time Complexity: O(V + E).
     * Expected Auxiliary Space: O(V).
     * <p>
     * https://practice.geeksforgeeks.org/problems/depth-first-traversal-for-a-graph/1
     */
    private static void dfs(int src, List<List<Integer>> list, boolean[] vis) {
        vis[src] = true;
        System.out.print(src + " ");
        List<Integer> adjacentVertices = list.get(src);
        for (Integer adjacentVertex : adjacentVertices) {
            if (!vis[adjacentVertex]) {
                dfs(adjacentVertex, list, vis);
            }
        }
    }

    // Iterative solution is not working for all test cases :()
    static void dfsIterative(int src, List<List<Integer>> list, boolean[] vis) {
        Stack<Integer> stack = new Stack<>();
        stack.push(src);
        while (!stack.isEmpty()) {
            int currentVertex = stack.pop();
            if (!vis[currentVertex]) {
                System.out.print(currentVertex + " ");
                vis[currentVertex] = true;
            }
            // this implementation (as compared to the commented one) looks more like BFS
            // with the only difference of iterating the adjacentVertices in reverse order
            List<Integer> adjacentVertices = list.get(currentVertex);
            for (int adjacentVertexIndex = adjacentVertices.size() - 1; adjacentVertexIndex >= 0; adjacentVertexIndex--) {
                Integer adjacentVertex = adjacentVertices.get(adjacentVertexIndex);
                if (!vis[adjacentVertex]) {
                    stack.push(adjacentVertex);
                }
            }
        }
        System.out.println();
    }
}
