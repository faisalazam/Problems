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

    private GraphOperationsWithAdjacencyList(int vertices) {
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
     * The above code traverses only the vertices reachable from a given source vertex.
     * All the vertices may not be reachable from a given vertex (example Disconnected graph).
     */
    private void depthFirstSearch(int startingVertex) {
        System.out.print("Recursive Depth First Traversal (starting from vertex " + startingVertex + ") => ");
        boolean[] visited = new boolean[vertices];
        depthFirstSearch(startingVertex, visited);  //TODO what if startingIndex does not exist in the graph?
        System.out.println();
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
     * To do complete DFS traversal of such graphs, we must call depthFirstSearch() for every vertex.
     * This will work even for disconnect graphs
     */
    private void depthFirstSearch() {
        System.out.print("Recursive Depth First Traversal => ");
        boolean[] visited = new boolean[vertices];

        for (int vertex = 0; vertex < vertices; vertex++) {
            if (!visited[vertex]) {
                depthFirstSearch(vertex, visited);
            }
        }
        System.out.println();
    }

    private void depthFirstSearch(int startingVertex, boolean[] visited) {
        // visited array will avoid visiting the same vertex multiple times as well as avoid getting into cycles
        visited[startingVertex] = true;
        System.out.print(startingVertex + " ");

        for (Integer adjacentVertex : adjacentVertices[startingVertex]) {
            if (!visited[adjacentVertex]) {
                depthFirstSearch(adjacentVertex, visited);
            }
        }
    }

    private void depthFirstSearchIteratively(int startingVertex) {
        System.out.print("Iterative Depth First Traversal (starting from vertex " + startingVertex + ") => ");

        boolean[] visited = new boolean[vertices];
        Stack<Integer> stack = new Stack<>();
        stack.push(startingVertex);

        while (!stack.isEmpty()) {
            int currentVertex = stack.pop();
            if (!visited[currentVertex]) {
                System.out.print(currentVertex + " ");
                visited[currentVertex] = true;
            }
            // this implementation (as compared to the commented one) looks more like BFS with the only difference of iterating the adjacentVertices in reverse order
            List<Integer> adjacentVerticesToCurrentVertex = adjacentVertices[currentVertex];
            for (int adjacentVertexIndex = adjacentVerticesToCurrentVertex.size() - 1; adjacentVertexIndex >= 0; adjacentVertexIndex--) {
                Integer adjacentVertex = adjacentVerticesToCurrentVertex.get(adjacentVertexIndex);
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

    private void breadthFirstSearch(int startingVertex) {
        System.out.print("Iterative Breadth First Traversal (starting from vertex " + startingVertex + ") => ");

        boolean[] visited = new boolean[vertices];
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(startingVertex);

        while (!queue.isEmpty()) {
            Integer currentVertex = queue.poll();
            if (!visited[currentVertex]) {
                System.out.print(currentVertex + " ");
                visited[currentVertex] = true;
            }
            for (Integer adjacentVertex : adjacentVertices[currentVertex]) {
                if (!visited[adjacentVertex]) {
                    queue.offer(adjacentVertex);
                }
            }
        }
        System.out.println();
    }

    private boolean isCyclicGraph() {
        int[] visited = new int[vertices];
        int startingVertex = adjacentVertices[0].get(0); //check for emptiness
        return isCyclicGraph(startingVertex, visited);
    }

    private boolean isCyclicGraph(int startingVertex, int[] visited) {
        visited[startingVertex] = 1;
        for (Integer adjacentVertex : adjacentVertices[startingVertex]) {
            if (visited[adjacentVertex] == 1 || isCyclicGraph(adjacentVertex, visited)) {
                return true;
            }
        }
        visited[startingVertex] = 2;
        return false;
    }

    private boolean isReachableUsingDFS(int fromVertex, int toVertex) {
        boolean[] visited = new boolean[vertices];
        return isReachableUsingDFS(fromVertex, toVertex, visited);
    }

    private boolean isReachableUsingDFS(int fromVertex, int toVertex, boolean[] visited) {
        if (fromVertex == toVertex) {
            return true;
        }
        visited[fromVertex] = true;

        for (Integer adjacentVertex : adjacentVertices[fromVertex]) {
            if (!visited[adjacentVertex] && isReachableUsingDFS(adjacentVertex, toVertex, visited)) {
                return true;
            }
        }
        return false;
    }

    private boolean isReachableUsingBFS(int fromVertex, int toVertex) {
        boolean[] visited = new boolean[vertices];
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(fromVertex);
        visited[fromVertex] = true;

        while (!queue.isEmpty()) {
            int currentVertex = queue.poll();
            for (Integer adjacentVertex : adjacentVertices[currentVertex]) {
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

    public static void main(String[] args) {
        GraphOperationsWithAdjacencyList graphOperations = new GraphOperationsWithAdjacencyList(4);
        graphOperations.addEdge(0, 1);
        graphOperations.addEdge(0, 2);
        graphOperations.addEdge(1, 2);
        graphOperations.addEdge(2, 0);
        graphOperations.addEdge(2, 3);
        graphOperations.addEdge(3, 3);

        graphOperations.depthFirstSearch(2);
        graphOperations.depthFirstSearchIteratively(2);
        graphOperations.depthFirstSearch();
        graphOperations.breadthFirstSearch(2);
        System.out.println("Is Cyclic Graph => " + graphOperations.isCyclicGraph());
        System.out.println("DFS => Does path exists between (3, 3) => " + graphOperations.isReachableUsingDFS(3, 3));
        System.out.println("DFS => Does path exists between (0, 3) => " + graphOperations.isReachableUsingDFS(0, 3));
        System.out.println("DFS => Does path exists between (3, 1) => " + graphOperations.isReachableUsingDFS(3, 1));
        System.out.println("BFS => Does path exists between (3, 3) => " + graphOperations.isReachableUsingBFS(3, 3));
        System.out.println("BFS => Does path exists between (0, 3) => " + graphOperations.isReachableUsingBFS(0, 3));
        System.out.println("BFS => Does path exists between (3, 1) => " + graphOperations.isReachableUsingBFS(3, 1));
    }
}
