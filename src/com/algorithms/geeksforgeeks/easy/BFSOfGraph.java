package com.algorithms.geeksforgeeks.easy;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class BFSOfGraph {
    /**
     * Given a directed graph. The task is to do Breadth First Search of this graph.
     *
     * User Task:
     * You don't need to read input or print anything. Your task is to complete the function bfs()
     * takes the Graph and the number of vertices as its input and returns a list containing the BFS traversal
     * of the graph starting from the 0th vertex.
     *
     * Expected Time Complexity: O(V + E).
     * Expected Auxiliary Space: O(V).
     *
     * https://practice.geeksforgeeks.org/problems/bfs-traversal-of-graph/1
     */
    static void bfs(int s, List<List<Integer>> list, boolean[] vis) {
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(s);
        while (!queue.isEmpty()) {
            Integer currentVertex = queue.poll();
            if (!vis[currentVertex]) {
                vis[currentVertex] = true;
                System.out.print(currentVertex + " ");
            }
            List<Integer> adjacentVertices = list.get(currentVertex);
            for (Integer adjacentVertex : adjacentVertices) {
                if (!vis[adjacentVertex]) {
                    queue.offer(adjacentVertex);
                }
            }
        }
    }
}
