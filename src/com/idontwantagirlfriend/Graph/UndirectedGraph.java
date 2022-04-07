package com.idontwantagirlfriend.Graph;

import java.util.Collection;
import java.util.HashMap;

public class UndirectedGraph {
    private final HashMap<String, Node> index;

    public UndirectedGraph() {
        this.index = new HashMap<>();
    }

    /**
     * Add a new node by name. If a node with the same name already exists,
     * will not make any change.
     * @param name The given name of that new node
     * @return whether the new node has been successfully added
     */
    public Boolean addNode(String name) {
        if (nameExistsInIndex(name)) return false;
        var node = new Node(name);
        addToIndex(name, node);
        return true;
    }

    /**
     * Remove a node by its name. If a node with the given name doesn't exist,
     * will not change the graph.
     * @param name Name of the node to be removed
     * @return whether the node has been successfully removed
     */
    public Boolean removeNode(String name) {
        if (!nameExistsInIndex(name)) return false;
        getAllNodes().forEach(eachNode -> eachNode.removeEdgeTo(getNodeByName(name)));
        removeFromIndex(name);
        return true;
    }

    /**
     * Add an edge. If an edge with the same source and destination already exists,
     * will not make any change.
     * @param from the starting node of edge
     * @param to the destination node of edge
     * @param length length of edge
     * @return whether the edge has been successfully added
     */
    public Boolean addEdge(String from, String to, int length) {
        var namesInIndex
                = nameExistsInIndex(from) && nameExistsInIndex(to);
        if (!namesInIndex) return false;
        var fromNode = getNodeByName(from);
        var toNode = getNodeByName(to);
        if (fromNode.getEdgeLength(toNode) != null) return false;
        getNodeByName(from).addEdgeTo(getNodeByName(to), length);
        return true;
    }

    /**
     * Remove an edge. If the edge is not found, will not make any change.
     * @param from the starting node of edge
     * @param to the destination node of edge
     * @return whether the edge has been successfully removed
     */
    public Boolean removeEdge(String from, String to) {
        if (!edgeExists(from, to)) return false;
        getNodeByName(from).removeEdgeTo(getNodeByName(to));
        return true;
    }

    /**
     * The edges are directed, which means an edge from point A to point B
     * is not the same as one from point B to point A. If no existing edge
     * can be found, return -1.<br/>
     *
     * @param from the starting node of edge
     * @param to the destination node of edge
     * @return length of edge
     */
    public Integer getEdge(String from, String to) {
        return edgeExists(from, to)
                ? getNodeByName(from).getEdgeLength(getNodeByName(to))
                : null;
    }

    private Boolean edgeExists(String from, String to) {
        var namesInIndex
                = nameExistsInIndex(from) && nameExistsInIndex(to);
        return namesInIndex && getNodeByName(from).getEdgeLength(getNodeByName(to)) != null;
    }

    private void addToIndex(String name, Node node) {
        index.put(name, node);
    }

    private void removeFromIndex(String name) {
        index.remove(name);
    }

    private Boolean nameExistsInIndex(String name) {
        return index.containsKey(name);
    }

    private Node getNodeByName(String name) {
        return index.get(name);
    }

    private Collection<Node> getAllNodes() {
        return index.values();
    }

    private static final class Node {
        private final String name;
        private final HashMap<Node, Integer> edges;

        private Node(String name) {
            this.name = name;
            this.edges = new HashMap<>();
        }

        public String getName() {
            return this.name;
        }

        public void addEdgeTo(Node to, int distance) {
            edges.put(to, distance);
        }

        public void removeEdgeTo(Node node) {
            edges.remove(node);
        }

        public Integer getEdgeLength(Node to) {
            return edges.get(to);
        }
    }
}