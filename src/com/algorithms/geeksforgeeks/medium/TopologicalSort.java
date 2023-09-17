package com.algorithms.geeksforgeeks.medium;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;
import java.util.Queue;

/**
 * Given a Directed Graph. Find any Topological Sorting of that Graph.
 * <p>
 * Graph doesn't contain multiple edges, self loops and cycles. Graph may be disconnected.
 * <p>
 * Expected Time Complexity: O(V + E).
 * Expected Auxiliary Space: O(V).
 * <p>
 * https://practice.geeksforgeeks.org/problems/topological-sort/1/?track=md-graph&batchId=144
 * <p>
 * Topological sorting for Directed Acyclic Graph (DAG) is a linear ordering of vertices such that for every directed
 * edge u v, vertex u comes before v in the ordering.
 * <p>
 * There can be more than one topological sorting for a graph. The first vertex in topological sorting is always a
 * vertex with an in-degree of 0 (a vertex with no incoming edges).
 * <p>
 * Topological Sorting for a graph is not possible if the graph is not a Directed Acyclic Graph (DAG).
 * <p>
 * Applications of Topological Sorting:
 * <p>
 * Topological Sorting is mainly used for scheduling jobs from the given dependencies among jobs.
 * In computer science, applications of this type arise in:
 * Instruction scheduling
 * Ordering of formula cell evaluation when recomputing formula values in spreadsheets
 * Logic synthesis
 * Determining the order of compilation tasks to perform in make files
 * Data serialization
 * Resolving symbol dependencies in linkers
 */
public class TopologicalSort {
    /**
     * Kahn's Algorithm - BFS
     * <p>
     * The main idea is to deal with indegree as we know in topological sort vertex with less indegree comes first
     * which means indegree with zero is the first element in topo sort, so we can apply the below idea to get topo sort:
     * <p>
     * 1) We will print the node which has indegree 0(They have no dependency) first.
     * 2) Then remove their adjacent edges and update the indegree of adjacent nodes which are connected with previous
     * nodes with indegree zero.
     * 3) Repeat Steps 1 and 2 until we have a node with indegree 0.
     * <p>
     * Why there always be a node with indegree 0?
     * As we are working with D.A.G So there must have always a starting point of a graph and that have indegree=0
     * <p>
     * Time Complexity: O(V+E): As we are traversing over all vertex of every edge using BFS so total time will be O(V+E).
     * Auxiliary space: O(V). Extra space is needed for the queue, indegree, and answer array.
     */
    static int[] topoSortBFS(final ArrayList<ArrayList<Integer>> adj, final int V) {
        final int[] inDegrees = inDegrees(V, adj);
        final Queue<Integer> independentVertices = getIndependentVertices(V, inDegrees);
        return topologicalOrder(V, inDegrees, adj, independentVertices);
    }

    /**
     * DFS
     * <p>
     * We can modify DFS to find the Topological Sorting of a graph. In DFS,
     * <p>
     * We start from a vertex, we first print it, and then
     * Recursively call DFS for its adjacent vertices.
     * In topological sorting,
     * <p>
     * We use a temporary stack.
     * We don’t print the vertex immediately,
     * we first recursively call topological sorting for all its adjacent vertices, then push it to a stack.
     * <p>
     * Finally, print the contents of the stack.
     * Note: A vertex is pushed to stack only when all of its adjacent vertices (and their adjacent vertices and so on)
     * are already in the stack
     * <p>
     * If we will print nodes in descending order of their finishing time of dfs that will be a topo sort. We will print
     * the node v only after it goes through to all its descendants. This ensures that anything that follows v will have
     * been added to the output vector before v.
     * <p>
     * Time Complexity: O(V+E). As we are traversing over all vertex of every edge using DFS so total time will be
     * O(V+E). It is simply DFS with an extra stack. So time complexity is the same as DFS.
     * Auxiliary space: O(V). Extra space is needed for the stack and visited array.
     */
    static int[] topoSortDFS(final ArrayList<ArrayList<Integer>> adj, final int V) {
        final boolean[] visited = new boolean[V]; // boolean array to mark visited nodes
        Arrays.fill(visited, false);
        final Deque<Integer> stack = new ArrayDeque<>();

        for (int vertex = 0; vertex < V; vertex++) { //traversing over all the vertices.
            if (!visited[vertex]) { // if the current vertex is not visited, call the topo function.
                topo(vertex, visited, stack, adj); // DFS
            }
        }
        return stack.stream().mapToInt(vertex -> vertex).toArray();
    }

    static void topo(final int currentVertex,
                     final boolean[] visited,
                     final Deque<Integer> stack,
                     final ArrayList<ArrayList<Integer>> adj) {
        visited[currentVertex] = true;

        final List<Integer> adjacentVertices = adj.get(currentVertex);
        for (int adjacentVertex : adjacentVertices) {
            // if any vertex is not visited, we call the function recursively.
            if (!visited[adjacentVertex]) {
                topo(adjacentVertex, visited, stack, adj);
            }
        }
        // pushing the current vertex into the stack.
        stack.push(currentVertex);
    }

    private static int[] topologicalOrder(final int V,
                                          final int[] inDegrees,
                                          final ArrayList<ArrayList<Integer>> adj,
                                          final Queue<Integer> independentVertices) {
        int index = 0;
        final int[] topoSort = new int[V];
        while (!independentVertices.isEmpty()) {
            final int currentVertex = independentVertices.poll();
            topoSort[index++] = currentVertex;

            final List<Integer> adjacentVertices = adj.get(currentVertex);
            for (int adjacentVertex : adjacentVertices) {
                inDegrees[adjacentVertex]--;
                addIfIndependent(inDegrees, adjacentVertex, independentVertices);
            }
        }
        return topoSort;
    }

    private static Queue<Integer> getIndependentVertices(final int V, final int[] inDegrees) {
        final Queue<Integer> independentVertices = new ArrayDeque<>();
        for (int vertex = 0; vertex < V; vertex++) {
            addIfIndependent(inDegrees, vertex, independentVertices);
        }
        return independentVertices;
    }

    /**
     * Time Complexity: The outer for loop will be executed V number of times and the inner for loop will be executed
     * E number of times, Thus overall time complexity is O(V+E).
     */
    private static int[] inDegrees(final int V, final ArrayList<ArrayList<Integer>> adj) {
        final int[] inDegrees = new int[V];
        for (int vertex = 0; vertex < V; vertex++) {
            final List<Integer> adjacentVertices = adj.get(vertex);
            for (Integer adjacentVertex : adjacentVertices) {
                inDegrees[adjacentVertex]++;
            }
        }
        return inDegrees;
    }

    private static void addIfIndependent(final int[] inDegrees,
                                         final int adjacentVertex,
                                         final Queue<Integer> independentVertices) {
        if (inDegrees[adjacentVertex] == 0) {
            independentVertices.offer(adjacentVertex);
        }
    }
}
