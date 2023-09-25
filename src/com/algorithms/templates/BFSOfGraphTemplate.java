package com.algorithms.templates;

import com.algorithms.geeksforgeeks.easy.BFSOfGraph;

/**
 * If we need to find the shortest path, or even just any path between two nodes, then BFS might be a better choice.
 * Why wouldn't a depth-first search work well? First, depth-first search would just find a path. It wouldn't
 * necessarily find the shortest path. Second, even if we just needed any path, it would be very inefficient.
 * Two nodes might be only one degree of separation apart, but I could search millions of nodes in their "subtrees"
 * before finding this relatively immediate connection.
 * <p>
 * Alternatively, I could do what's called a bidirectional breadth-first search. This means doing two breadth-first
 * searches, one from the source and one from the destination. When the searches collide, we know we've found a path.
 * A bidirectional BFS will generally be faster than the traditional BFS. However, it requires actually having access
 * to both the source node and the destination nodes, which is not always the case.
 * <p>
 * See {@link BFSOfGraph} for further details
 */
public class BFSOfGraphTemplate {
}
