package com.algorithms.geeksforgeeks;

/*
 * m Coloring Problem | Backtracking-5
 * Given an undirected graph and a number m, determine if the graph can be colored with at most m colors
 * such that no two adjacent vertices of the graph are colored with the same color.
 * Here coloring of a graph means the assignment of colors to all vertices.
 * Input:
 *  1) A 2D array graph[V][V] where V is the number of vertices in graph,
 *      and graph[V][V] is adjacency matrix representation of the graph.
 *      A value graph[i][j] is 1 if there is a direct edge from i to j, otherwise graph[i][j] is 0.
 *  2) An integer m which is the maximum number of colors that can be used.
 *
 * Output:
 * An array color[V] that should have numbers from 1 to m.
 * color[i] should represent the color assigned to the ith vertex.
 * The code should also return false if the graph cannot be colored with m colors.
 *
 * https://www.geeksforgeeks.org/m-coloring-problem-backtracking-5/
 */
public class GraphColoringProblem {
    public static void main(String[] args) {
        GraphColoringProblem coloring = new GraphColoringProblem();
        /* Create following graph and test whether it is
           3 colorable
          (3)---(2)
           |   / |
           |  /  |
           | /   |
          (0)---(1)
        */
        int[][] graph = {
                {0, 1, 1, 1},
                {1, 0, 1, 0},
                {1, 1, 0, 1},
                {1, 0, 1, 0},
        };
        int m = 3; // Number of colors
        coloring.graphColoring(graph, m);
    }

    /**
     * This function solves the m Coloring problem using Backtracking. It mainly uses graphColoringUtil() to solve
     * the problem. It returns false if the m colors cannot be assigned, otherwise return true and  prints assignments
     * of colors to all vertices. Please note that there  may be more than one solutions, this function prints one of
     * the feasible solutions.
     *
     * Time Complexity: O(m^V).
     * There are total O(m^V) combination of colors. So time complexity is O(m^V). The upper bound time complexity
     * remains the same but the average time taken will be less.
     * Space Complexity: O(V).
     * To store the output array O(V) space is required.
     */
    private void graphColoring(int[][] graph, int m) {
        // Initialize all color values as 0. This initialization is needed correct functioning of isSafe()
        int[] colors = new int[graph.length];
        boolean canColor = graphColoringUtil(0, m, graph, colors);
        if (!canColor) {
            System.out.println("Solution does not exist");
            return;
        }

        printSolution(colors);
    }

    private void printSolution(int[] colors) {
        System.out.print("Solution Exists: Following are the assigned colors => ");
        for (int color : colors) {
            System.out.print(" " + color + " ");
        }
        System.out.println();
    }

    /* A recursive utility function to solve m coloring  problem */
    private boolean graphColoringUtil(int vertex, int m, int[][] graph, int[] colors) {
        /* base case: If all vertices are assigned a color then return true */
        if (vertex == graph.length) {
            return true;
        }
        /* Consider this vertex v and try different colors */
        for (int color = 1; color <= m; color++) {
            /* Check if assignment of color c to v is fine*/
            if (isSafe(vertex, graph, colors, color)) {
                colors[vertex] = color;
                /* recur to assign colors to rest of the vertices */
                if (graphColoringUtil(vertex + 1, m, graph, colors)) {
                    return true;
                }
                /* If assigning color c doesn't lead to a solution then remove it */
                colors[vertex] = 0;
            }
        }
        /* If no color can be assigned to this vertex then return false */
        return false;
    }

    /* A utility function to check if the current color assignment is safe for vertex v */
    private boolean isSafe(int vertex, int[][] graph, int[] colors, int colorToBeApplied) {
        for (int j = 0; j < graph.length; j++) {
            if (graph[vertex][j] == 1 && colors[j] == colorToBeApplied) {
                return false;
            }
        }
        return true;
    }
}
