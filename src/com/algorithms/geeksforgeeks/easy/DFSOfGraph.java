package com.algorithms.geeksforgeeks.easy;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

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
 * <p>
 * <p>
 * Depth First Search or DFS for a Graph
 * <p>
 * Depth First Traversal (or DFS) for a graph is similar to Depth First Traversal of a tree.
 * <p>
 * The only catch here is, that, unlike trees, graphs may contain cycles (a node may be visited twice).
 * A graph can have more than one DFS traversal.
 * To avoid processing a node more than once, use a boolean visited array to divide the vertices into two categories:
 * <p>
 * Visited and
 * Not visited.
 * <p>
 * In DFS, before switching direction, we keep moving in one direction until we cannot move any further in that direction,
 * whereas in BFS, all directions are explored quite evenly and that's why it's suited for finding the shortest paths.
 */
public class DFSOfGraph {
    public ArrayList<Integer> dfsOfGraph(int V, ArrayList<ArrayList<Integer>> adj) {
        final ArrayList<Integer> result = new ArrayList<>();
        if (adj == null || adj.isEmpty() || V == 0) {
            return result;
        }
        final boolean[] visited = new boolean[adj.size()];
        dfs(0, visited, result, adj);
        return result;
    }

    /**
     * Time Complexity: O(V + E) where V is the total number of vertex and E is the total number of edges as we are
     * exploring every vertex V and exploring all its edges E.
     * Space Complexity: O(V) V is the total number of vertex as the size of the resultant vector is V and the space
     * occupied by recursive call is max V.
     */
    private static void dfs(final int src,
                            final boolean[] visited,
                            final List<Integer> result,
                            final ArrayList<ArrayList<Integer>> adj) {
        visited[src] = true;
        result.add(src);
        final List<Integer> adjacentVertices = adj.get(src);
        for (Integer adjacentVertex : adjacentVertices) {
            if (!visited[adjacentVertex]) {
                dfs(adjacentVertex, visited, result, adj);
            }
        }
    }

    /**
     * This implementation is almost same as {@link BFSOfGraph#bfsOfGraphV1(int, ArrayList)}.
     * The difference is, in BFS, a queue is used and the adjacent vertices are added in the normal order,
     * whereas in the case of DFS, a stack is used and the adjacent vertices are added in the reverse order.
     * <p>
     * Time Complexity: O(V + E) where V is the total number of vertex and E is the total number of edges as we are
     * exploring every vertex V and exploring all its edges E.
     * Space Complexity: O(V) V is the total number of vertex as the size of the resultant vector is V and the space
     * occupied by recursive call is max V.
     */
    private static void dfsIterative(final int src,
                                     final boolean[] visited,
                                     final List<Integer> result,
                                     final ArrayList<ArrayList<Integer>> adj) {
        // Iterative solution - Sometimes it may work while other times it might end up in below TLE error
        // Test Cases Passed:
        // 1115 /1120
        // Time Limit Exceeded
        // Your program took more time than expected. Expected Time Limit
        final Stack<Integer> stack = new Stack<>();
        stack.push(src);
        while (!stack.isEmpty()) {
            final int currentVertex = stack.pop();
            if (!visited[currentVertex]) {
                result.add(currentVertex);
                visited[currentVertex] = true;
            }
            // this implementation (as compared to the commented one) looks more like BFS with the only difference of
            // iterating the adjacentVertices in reverse order.
            // I think, it'd still be valid if we traverse the adjacent vertices in normal order instead of reverse
            // order...It's just that the processing will be in little different order
            final List<Integer> adjacentVertices = adj.get(currentVertex);
            for (int adjacentVertexIndex = adjacentVertices.size() - 1; adjacentVertexIndex >= 0; adjacentVertexIndex--) {
                Integer adjacentVertex = adjacentVertices.get(adjacentVertexIndex);
                if (!visited[adjacentVertex]) {
                    stack.push(adjacentVertex);
                }
            }
        }
    }
}
