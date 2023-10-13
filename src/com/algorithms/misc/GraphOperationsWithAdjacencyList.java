package com.algorithms.misc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;

public class GraphOperationsWithAdjacencyList {
    private final int vertices;
    // Array  of lists for Adjacency List Representation or we could use Map<Integer, List<Integer>>
    private LinkedList<Integer>[] adjacentVertices;

    private GraphOperationsWithAdjacencyList(final int vertices) {
        this.vertices = vertices;
        initialiseAdjacencyList(vertices);
    }

    @SuppressWarnings("unchecked")
    private void initialiseAdjacencyList(int vertices) {
        adjacentVertices = new LinkedList[vertices];
        for (int vertex = 0; vertex < vertices; vertex++) {
            adjacentVertices[vertex] = new LinkedList<>();
        }
    }

    private void addEdge(int vertex, int adjacentVertex) {
        adjacentVertices[vertex].add(adjacentVertex);
    }

    /*
     * Let's say you are provided with a 2D array of undirected edges where the inner array is of size 2 to hold an edge,
     * then it'll be better to convert it to AdjacencyList before solving the actual problem
     */
    private void convertEdgesToAdjacencyList(int[][] edges) {
        final Map<Integer, List<Integer>> adjList = new HashMap<>();
        for (int[] edge : edges) {
            final int vertexA = edge[0];
            final int vertexB = edge[1];
            if (!adjList.containsKey(vertexA)) {
                adjList.put(vertexA, new ArrayList<>());
            }
            if (!adjList.containsKey(vertexB)) {
                adjList.put(vertexB, new ArrayList<>());
            }
            adjList.get(vertexA).add(vertexB);
            adjList.get(vertexB).add(vertexA);
        }
    }

    /*
     * The above code traverses only the vertices reachable from a given source vertex.
     * All the vertices may not be reachable from a given vertex (example Disconnected graph).
     */
    private void depthFirstSearch(final int startingVertex) {
        System.out.print("Recursive Depth First Traversal (starting from vertex " + startingVertex + ") => ");
        final boolean[] visited = new boolean[vertices];
        depthFirstSearch(startingVertex, visited);  //TODO what if startingIndex does not exist in the graph?
        System.out.println();
    }

    /*
     * To do complete DFS traversal of such graphs, we must call depthFirstSearch() for every vertex.
     * This will work even for disconnect graphs
     */
    private void depthFirstSearch() {
        System.out.print("Recursive Depth First Traversal => ");
        final boolean[] visited = new boolean[vertices];

        for (int vertex = 0; vertex < vertices; vertex++) {
            if (!visited[vertex]) {
                depthFirstSearch(vertex, visited);
            }
        }
        System.out.println();
    }

    private void depthFirstSearch(final int startingVertex, final boolean[] visited) {
        // visited array will avoid visiting the same vertex multiple times as well as avoid getting into cycles
        visited[startingVertex] = true;
        System.out.print(startingVertex + " ");

        for (int adjacentVertex : adjacentVertices[startingVertex]) {
            if (!visited[adjacentVertex]) {
                depthFirstSearch(adjacentVertex, visited);
            }
        }
    }

    private void depthFirstSearchIteratively(final int startingVertex) {
        System.out.print("Iterative Depth First Traversal (starting from vertex " + startingVertex + ") => ");

        final boolean[] visited = new boolean[vertices];
        final Stack<Integer> stack = new Stack<>();
        stack.push(startingVertex);

        while (!stack.isEmpty()) {
            final int currentVertex = stack.pop();
            if (!visited[currentVertex]) {
                System.out.print(currentVertex + " ");
                visited[currentVertex] = true;
            }
            // this implementation (as compared to the commented one) looks more like BFS with the only difference of iterating the adjacentVertices in reverse order
            List<Integer> adjacentVerticesToCurrentVertex = adjacentVertices[currentVertex];
            for (int adjacentVertexIndex = adjacentVerticesToCurrentVertex.size() - 1; adjacentVertexIndex >= 0; adjacentVertexIndex--) {
                final int adjacentVertex = adjacentVerticesToCurrentVertex.get(adjacentVertexIndex);
                if (!visited[adjacentVertex]) {
                    stack.push(adjacentVertex);
                }
            }

// This commented piece of code is also a working one, but I guess the above one inside the while is relatively simpler
// and looks more like BFS with the only difference of iterating the adjacentVertices in reverse order
//            int currentVertex = stack.peek();
//            if (!visited[currentVertex]) {
//                System.out.print(currentVertex + " ");
//            }
//            visited[currentVertex] = true;
//            boolean hasPushedAnyAdjacentVertex = false;
//            for (Integer adjacentVertex : adjacentVertices[currentVertex]) {
//                if (!visited[adjacentVertex]) {
//                    hasPushedAnyAdjacentVertex = true;
//                    stack.push(adjacentVertex);
//                    break;
//                }
//            }
//            if (!hasPushedAnyAdjacentVertex) { //Remove the vertex from stack when it does not have any more adjacent vertices
//                stack.pop();
//            }
        }
        System.out.println();
    }

    private void breadthFirstSearch(final int startingVertex) {
        System.out.print("Iterative Breadth First Traversal (starting from vertex " + startingVertex + ") => ");

        final boolean[] visited = new boolean[vertices];
        final Queue<Integer> queue = new LinkedList<>();
        queue.offer(startingVertex);

        while (!queue.isEmpty()) {
            final int currentVertex = queue.poll();
            if (!visited[currentVertex]) {
                System.out.print(currentVertex + " ");
                visited[currentVertex] = true;
            }
            for (int adjacentVertex : adjacentVertices[currentVertex]) {
                if (!visited[adjacentVertex]) {
                    queue.offer(adjacentVertex);
                }
            }
        }
        System.out.println();
    }

    private boolean isCyclicGraph() {
        final int[] visited = new int[vertices];
        final int startingVertex = adjacentVertices[0].get(0); //check for emptiness
        return isCyclicGraph(startingVertex, visited);
    }

    private boolean isCyclicGraph(int startingVertex, int[] visited) {
        visited[startingVertex] = 1;
        for (int adjacentVertex : adjacentVertices[startingVertex]) {
            if (visited[adjacentVertex] == 1 || isCyclicGraph(adjacentVertex, visited)) {
                return true;
            }
        }
        visited[startingVertex] = 2;
        return false;
    }

    private boolean isReachableUsingDFS(int fromVertex, int toVertex) {
        final boolean[] visited = new boolean[vertices];
        return isReachableUsingDFS(fromVertex, toVertex, visited);
    }

    private boolean isReachableUsingDFS(int fromVertex, int toVertex, boolean[] visited) {
        if (fromVertex == toVertex) {
            return true;
        }
        visited[fromVertex] = true;

        for (int adjacentVertex : adjacentVertices[fromVertex]) {
            if (!visited[adjacentVertex] && isReachableUsingDFS(adjacentVertex, toVertex, visited)) {
                return true;
            }
        }
        return false;
    }

    private boolean isReachableUsingBFS(int fromVertex, int toVertex) {
        final boolean[] visited = new boolean[vertices];
        final Queue<Integer> queue = new LinkedList<>();
        queue.offer(fromVertex);
        visited[fromVertex] = true;

        while (!queue.isEmpty()) {
            final int currentVertex = queue.poll();
            for (int adjacentVertex : adjacentVertices[currentVertex]) {
                if (adjacentVertex == toVertex) {
                    return true;
                }
                if (!visited[adjacentVertex]) {
                    queue.offer(adjacentVertex);
                    visited[adjacentVertex] = true;
                }
            }
        }
        return false;
    }

    /*
     * Given an undirected graph which could consist of multiple sub-graphs, count the number of connected components or
     * sub-graphs.
     *
     * We'll complete DFS traversal for this, i.e. we must call depthFirstSearch() for every vertex so the
     * disconnected graphs can also be traversed.
     */
    private int connectedComponentsCount() {
        int count = 0;
        final boolean[] visited = new boolean[vertices];

        for (int vertex = 0; vertex < vertices; vertex++) {
            if (!visited[vertex]) {
                depthFirstSearch(vertex, visited);
                count++;
            }
        }
        return count;
    }

    /*
     * Given an undirected graph which could consist of multiple sub-graphs, return the number of vertices in the
     * largest connected components/sub-graph.
     *
     * We'll complete DFS traversal for this, i.e. we must call depthFirstSearch() for every vertex so the
     * disconnected graphs can also be traversed.
     */
    private int largestConnectedComponent() {
        int largest = Integer.MIN_VALUE;
        final boolean[] visited = new boolean[vertices];

        for (int vertex = 0; vertex < vertices; vertex++) {
            if (!visited[vertex]) {
                final int count = exploreSize(vertex, visited);
                largest = Math.max(largest, count);
            }
        }
        return largest;
    }

    private int exploreSize(final int startingVertex, final boolean[] visited) {
        // visited array will avoid visiting the same vertex multiple times as well as avoid getting into cycles
        visited[startingVertex] = true;

        int size = 1;
        for (int adjacentVertex : adjacentVertices[startingVertex]) {
            if (!visited[adjacentVertex]) {
                size += exploreSize(adjacentVertex, visited);
            }
        }
        return size;
    }

    public static void main(String[] args) {
        final GraphOperationsWithAdjacencyList graphOperations = new GraphOperationsWithAdjacencyList(6);
        graphOperations.addEdge(0, 1);
        graphOperations.addEdge(0, 2);
        graphOperations.addEdge(1, 2);
        graphOperations.addEdge(2, 0);
        graphOperations.addEdge(2, 3);
        graphOperations.addEdge(3, 3);
        graphOperations.addEdge(4, 5);

        graphOperations.depthFirstSearch(2);
        graphOperations.depthFirstSearchIteratively(2);
        graphOperations.depthFirstSearch();
        graphOperations.breadthFirstSearch(2);
        System.out.println("Is Cyclic Graph => " + graphOperations.isCyclicGraph());
        System.out.println("Connected Components Count => " + graphOperations.connectedComponentsCount());
        System.out.println("Largest Connected Component => " + graphOperations.largestConnectedComponent());
        System.out.println("DFS => Does path exists between (3, 3) => " + graphOperations.isReachableUsingDFS(3, 3));
        System.out.println("DFS => Does path exists between (0, 3) => " + graphOperations.isReachableUsingDFS(0, 3));
        System.out.println("DFS => Does path exists between (3, 1) => " + graphOperations.isReachableUsingDFS(3, 1));
        System.out.println("BFS => Does path exists between (3, 3) => " + graphOperations.isReachableUsingBFS(3, 3));
        System.out.println("BFS => Does path exists between (0, 3) => " + graphOperations.isReachableUsingBFS(0, 3));
        System.out.println("BFS => Does path exists between (3, 1) => " + graphOperations.isReachableUsingBFS(3, 1));
    }
}
