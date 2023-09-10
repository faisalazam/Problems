package com.algorithms.misc;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static java.lang.Integer.MAX_VALUE;

//https://www.baeldung.com/java-dijkstra
public class BaeldungDijkstra {
    public static void main(String[] args) {
        Node nodeA = new Node("A");
        Node nodeB = new Node("B");
        Node nodeC = new Node("C");
        Node nodeD = new Node("D");
        Node nodeE = new Node("E");
        Node nodeF = new Node("F");

        nodeA.addDestination(nodeB, 10);
        nodeA.addDestination(nodeC, 15);

        nodeB.addDestination(nodeD, 12);
        nodeB.addDestination(nodeF, 15);

        nodeC.addDestination(nodeE, 10);

        nodeD.addDestination(nodeE, 2);
        nodeD.addDestination(nodeF, 1);

        nodeF.addDestination(nodeE, 5);

        Graph graph = new Graph();

        graph.addNode(nodeA);
        graph.addNode(nodeB);
        graph.addNode(nodeC);
        graph.addNode(nodeD);
        graph.addNode(nodeE);
        graph.addNode(nodeF);

        graph = calculateShortestPathFromSource(graph, nodeA);

        List<Node> shortestPathForNodeB = Arrays.asList(nodeA);
        List<Node> shortestPathForNodeC = Arrays.asList(nodeA);
        List<Node> shortestPathForNodeD = Arrays.asList(nodeA, nodeB);
        List<Node> shortestPathForNodeE = Arrays.asList(nodeA, nodeB, nodeD);
        List<Node> shortestPathForNodeF = Arrays.asList(nodeA, nodeB, nodeD);

        //TODO verify like below
//        for (Node node : graph.getProjects()) {
//            switch (node.getName()) {
//                case "B":
//                    assert (node.getShortestPath().equals(shortestPathForNodeB));
//                    break;
//                case "C":
//                    assert (node.getShortestPath().equals(shortestPathForNodeC));
//                    break;
//                case "D":
//                    assert (node.getShortestPath().equals(shortestPathForNodeD));
//                    break;
//                case "E":
//                    assert (node.getShortestPath().equals(shortestPathForNodeE));
//                    break;
//                case "F":
//                    assert (node.getShortestPath().equals(shortestPathForNodeF));
//                    break;
//            }
//        }
    }

    private static Graph calculateShortestPathFromSource(Graph graph, Node source) {
        if (graph == null || source == null || !graph.getNodes().contains(source)) {
            return null;
        }
        source.setDistance(0);
        Set<Node> settledNodes = new HashSet<>(); // more like keeping track of visited/settled nodes
        Set<Node> unsettledNodes = new HashSet<>();
        unsettledNodes.add(source);
        while (!unsettledNodes.isEmpty()) {
            Node currentNode = getLowestDistanceNode(unsettledNodes);
            unsettledNodes.remove(currentNode);
            for (Map.Entry<Node, Integer> nodeDistanceEntry : currentNode.getAdjacentNodes().entrySet()) {
                Node adjacentNode = nodeDistanceEntry.getKey();
                Integer edgeWeight = nodeDistanceEntry.getValue();
                if (!settledNodes.contains(adjacentNode)) {
                    if (relaxIfPossible(adjacentNode, edgeWeight, currentNode)) {
                        setShortestPath(adjacentNode, currentNode);
                    }
                    unsettledNodes.add(adjacentNode);
                }
            }
            settledNodes.add(currentNode);
        }
        return graph;
    }

    private static boolean relaxIfPossible(Node evaluationNode, Integer edgeWeigh, Node sourceNode) {
        int evaluatedDistance = sourceNode.getDistance() + edgeWeigh;
        if (evaluatedDistance < evaluationNode.getDistance()) {
            evaluationNode.setDistance(evaluatedDistance);
            return true;
        }
        return false;
    }

    private static void setShortestPath(Node evaluationNode, Node sourceNode) {
        List<Node> shortestPath = new LinkedList<>(sourceNode.getShortestPath());
        shortestPath.add(sourceNode);
        evaluationNode.setShortestPath(shortestPath);
    }

    private static Node getLowestDistanceNode(Set<Node> unsettledNodes) {
        Node lowestDistanceNode = null;
        int lowestDistance = Integer.MAX_VALUE;
        for (Node node : unsettledNodes) {
            int nodeDistance = node.getDistance();
            if (nodeDistance < lowestDistance) {
                lowestDistance = nodeDistance;
                lowestDistanceNode = node;
            }
        }
        return lowestDistanceNode;
    }

    private static class Graph {
        private Set<Node> nodes;

        private Graph() {
            this.nodes = new HashSet<>();
        }

        private void addNode(Node node) {
            nodes.add(node);
        }

        private Set<Node> getNodes() {
            return nodes;
        }
    }

    private static class Node {
        private String name;
        private Integer distance;
        private List<Node> shortestPath;
        private Map<Node, Integer> adjacentNodes; // node -> distance mapping

        private Node(String name) {
            this.name = name;
            this.distance = MAX_VALUE;
            this.adjacentNodes = new HashMap<>();
            this.shortestPath = new LinkedList<>();
        }

        private void addDestination(Node node, int distance) {
            if (distance < 0) {
                System.out.println("-ve distances are not allowed: " + distance);
            } else if (node == null) {
                System.out.println("null nodes are not allowed");
            } else {
                adjacentNodes.put(node, distance);
            }
        }

        private String getName() {
            return name;
        }

        private Integer getDistance() {
            return distance;
        }

        private void setDistance(int distance) {
            this.distance = distance;
        }

        private List<Node> getShortestPath() {
            return shortestPath;
        }

        private void setShortestPath(List<Node> shortestPath) {
            this.shortestPath = shortestPath;
        }

        private Map<Node, Integer> getAdjacentNodes() {
            return adjacentNodes;
        }
    }
}
