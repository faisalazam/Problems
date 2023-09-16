package com.algorithms.ctci.normal.treesandgraph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TopologicalSortOrBuildOrder {
    /**
     * Build Order: You are given a list of projects and a list of dependencies (which is a list of pairs of projects,
     * where the second project is dependent on the first project). All of a project's dependencies must be built
     * before the project is. Find a build order that will allow the projects to be built. If there is no valid
     * build order, return an error.
     * <p>
     * By the way, this problem is called topological sort: linearly ordering the vertices in a graph such that
     * for every edge ( a , b ) , a appears before b in the linear order.
     * <p>
     * This solution takes 0(P + D) time, where P is the number of projects and D is the number of dependency pairs.
     */
    Project[] findBuildOrder(final String[] projects, final String[][] dependencies) {
        final Graph graph = new Graph(projects, dependencies);
        return orderProjects(graph.getProjects());
    }

    private Project[] orderProjects(final List<Project> projects) {
        final Project[] order = new Project[projects.size()];
        int endOfList = addNonDependent(order, projects, 0);
        int toBeProcessed = 0;
        while (toBeProcessed < order.length) {
            final Project current = order[toBeProcessed];

            /* We have a circular dependency since there are no remaining projects with * zero dependencies. */
            if (current == null) {
                return new Project[0];
            }

            /* Remove myself as a dependency. */
            final List<Project> children = current.getChildren();
            for (Project child : children) {
                child.decrementDependencies();
            }
            /* Add children that have no one depending on them. */
            endOfList = addNonDependent(order, children, endOfList);
            toBeProcessed++;
        }
        return order;
    }

    /* A helper function to insert projects with zero dependencies into the order array, starting at index offset. */
    private int addNonDependent(Project[] order, List<Project> projects, int offset) {
        for (Project project : projects) {
            if (project.hasNoDependencies()) {
                order[offset++] = project;
            }
        }
        return offset;
    }

    private static class Graph {
        private final List<Project> projects = new ArrayList<>();
        private final Map<String, Project> map = new HashMap<>();

        private Graph(String[] projects, String[][] dependencies) {
            for (String project : projects) {
                getOrCreateProjectNode(project);
            }

            for (String[] dependency : dependencies) {
                addEdge(dependency[0], dependency[1]);
            }
        }

        Project getOrCreateProjectNode(String projectName) {
            if (!map.containsKey(projectName)) {
                final Project project = new Project(projectName);
                projects.add(project);
                map.put(projectName, project);
            }
            return map.get(projectName);
        }

        void addEdge(String startName, String endName) {
            final Project start = getOrCreateProjectNode(startName);
            final Project end = getOrCreateProjectNode(endName);
            start.addNeighbor(end);
        }

        List<Project> getProjects() {
            return projects;
        }
    }

    private static class Project {
        private final String projectName;
        private int dependencies = 0;
        private final List<Project> children = new ArrayList<>();
        private final Map<String, Project> map = new HashMap<>();

        Project(String projectName) {
            this.projectName = projectName;
        }

        void addNeighbor(Project project) {
            if (!map.containsKey(project.getProjectName())) {
                children.add(project);
                map.put(project.getProjectName(), project);
                project.incrementDependencies();
            }
        }

        void incrementDependencies() {
            dependencies++;
        }

        void decrementDependencies() {
            dependencies--;
        }

        String getProjectName() {
            return projectName;
        }

        List<Project> getChildren() {
            return children;
        }

        boolean hasNoDependencies() {
            return dependencies == 0;
        }
    }
}
