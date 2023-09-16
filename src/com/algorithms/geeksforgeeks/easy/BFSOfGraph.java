package com.algorithms.geeksforgeeks.easy;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Given a directed graph. The task is to do Breadth First Search of this graph.
 * <p>
 * User Task:
 * You don't need to read input or print anything. Your task is to complete the function bfs()
 * takes the Graph and the number of vertices as its input and returns a list containing the BFS traversal
 * of the graph starting from the 0th vertex.
 * <p>
 * Time Complexity: O(V + E).
 * Auxiliary Space: O(V).
 * <p>
 * https://practice.geeksforgeeks.org/problems/bfs-traversal-of-graph/1
 * <p>
 * <p>
 * Breadth First Search or BFS for a Graph
 * <p>
 * The Breadth First Search (BFS) algorithm is used to search a graph data structure for a node that meets a set of
 * criteria. It starts at the root of the graph and visits all nodes at the current depth level before moving on to
 * the nodes at the next depth level.
 * <p>
 * Breadth-First Traversal (or Search) for a graph is similar to the Breadth-First Traversal of a tree.
 * <p>
 * The only catch here is, that, unlike trees, graphs may contain cycles, so we may come to the same node again.
 * To avoid processing a node more than once, we divide the vertices into two categories:
 * <p>
 * Visited and
 * Not visited.
 */
public class BFSOfGraph {
    /**
     * Time Complexity: O(V + E) where V is the total number of vertices and E is the total number of edges as we are
     * traversing every vertex so V and also exploring each edge of all vertices so resultant sum is E.
     * Space Complexity: O(V) where V is the total number of vertices as the maximum size of queue and resultant vector
     * is V.
     */
    public ArrayList<Integer> bfsOfGraph(int V, ArrayList<ArrayList<Integer>> adj) {
        final ArrayList<Integer> result = new ArrayList<>();
        if (adj == null || adj.isEmpty() || V == 0) {
            return result;
        }
        final Queue<Integer> queue = new ArrayDeque<>();
        final boolean[] visited = new boolean[adj.size()];
        queue.add(0);

        while (!queue.isEmpty()) {
            int currentVertex = queue.remove();
            if (!visited[currentVertex]) {
                result.add(currentVertex);
                visited[currentVertex] = true;
                queue.addAll(adj.get(currentVertex));
            }
        }
        return result;
    }

    /**
     * This implementation is almost same as {@link DFSOfGraph#dfsIterative(int, boolean[], List, ArrayList)}.
     * The difference is, in BFS, a queue is used and the adjacent vertices are added in the normal order,
     * whereas in the case of DFS, a stack is used and the adjacent vertices are added in the reverse order.
     */
    public ArrayList<Integer> bfsOfGraphV1(int V, ArrayList<ArrayList<Integer>> adj) {
        final ArrayList<Integer> result = new ArrayList<>();
        if (adj == null || adj.isEmpty() || V == 0) {
            return result;
        }
        final boolean[] visited = new boolean[adj.size()];
        final Queue<Integer> queue = new LinkedList<>();
        queue.offer(0);
        while (!queue.isEmpty()) {
            final int currentVertex = queue.poll();
            if (!visited[currentVertex]) {
                result.add(currentVertex);
                visited[currentVertex] = true;
            }
            final List<Integer> adjacentVertices = adj.get(currentVertex);
            for (int adjacentVertex : adjacentVertices) {
                if (!visited[adjacentVertex]) {
                    queue.offer(adjacentVertex);
                }
            }
        }
        return result;
    }

    public ArrayList<Integer> bfsOfGraphV0(int V, ArrayList<ArrayList<Integer>> adj) {
        final ArrayList<Integer> result = new ArrayList<>();
        if (adj == null || adj.isEmpty() || V == 0) {
            return result;
        }

        final Queue<Integer> queue = new ArrayDeque<>();
        final boolean[] visited = new boolean[adj.size()];
        queue.add(0);
        visited[0] = true;
        while (!queue.isEmpty()) {
            final int currentVertex = queue.poll();
            result.add(currentVertex);

            final List<Integer> adjacentVertices = adj.get(currentVertex);
            for (int adjacentVertex : adjacentVertices) {
                // result.contains(...) might increase the overall time complexity, see the other solutions in this
                // class as they are not using result.contains(...).
                if (!result.contains(adjacentVertex) && !visited[adjacentVertex]) {
                    queue.add(adjacentVertex);
                    visited[adjacentVertex] = true;
                }
            }
        }
        return result;
    }
}
