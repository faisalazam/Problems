package com.algorithms.geeksforgeeks.medium;

import java.util.Arrays;
import java.util.List;

public class Dijkstra {
    /**
     * Given a graph of V nodes represented in the form of the adjacency matrix. The task is to
     * find the shortest distance of all the vertex's from the source vertex.
     * <p>
     * https://practice.geeksforgeeks.org/problems/implementing-dijkstra-set-1-adjacency-matrix/1
     *
     * See another implementation with Map and priority queue here: https://ide.geeksforgeeks.org/ghF0MEehmj
     */
    static void dijkstra(List<List<Integer>> list, int src, int V) {
        boolean[] visited = new boolean[V];
        int[] distances = new int[V];
        Arrays.fill(distances, Integer.MAX_VALUE);
        distances[src] = 0;

        int[] parent = new int[V]; // To keep track of the shortest path, maintain the parents
        for (int currentVertex = 0; currentVertex < V; currentVertex++) {
            parent[currentVertex] = currentVertex;
        }

        for (int vertex = 0; vertex < V; vertex++) {
            int vertexWithMinDistance = findVertexWithMinDistance(distances, visited);
            visited[vertexWithMinDistance] = true;
            for (int adjacentVertex = 0; adjacentVertex < V; adjacentVertex++) {
                relaxIfPossible(distances, parent, visited, adjacentVertex, vertexWithMinDistance, list.get(vertexWithMinDistance));
            }
        }
        for (int distance : distances) {
            System.out.print(distance + " ");
        }
    }

    private static void relaxIfPossible(int[] distances, int[] parent, boolean[] visited,
                                        int adjacentVertex, int vertexWithMinDistance,
                                        List<Integer> verticesAdjacentToMinDistanceVertex) {
        if (verticesAdjacentToMinDistanceVertex.get(adjacentVertex) != 0
                && !visited[adjacentVertex]
                && distances[vertexWithMinDistance] != Integer.MAX_VALUE) {
            int updatedDistance = distances[vertexWithMinDistance] + verticesAdjacentToMinDistanceVertex.get(adjacentVertex);
            if (updatedDistance < distances[adjacentVertex]) {
                distances[adjacentVertex] = updatedDistance;
                parent[adjacentVertex] = vertexWithMinDistance;
            }
        }
    }

    private static int findVertexWithMinDistance(int[] distances, boolean[] visited) {
        int vertexWithMinDistance = Integer.MAX_VALUE;
        for (int currentVertex = 0; currentVertex < distances.length; currentVertex++) {
            if (!visited[currentVertex]
                    && (vertexWithMinDistance == Integer.MAX_VALUE || distances[currentVertex] < distances[vertexWithMinDistance])) {
                vertexWithMinDistance = currentVertex;
            }
        }
        return vertexWithMinDistance;
    }
}
