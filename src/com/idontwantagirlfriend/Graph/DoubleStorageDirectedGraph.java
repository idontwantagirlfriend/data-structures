package com.idontwantagirlfriend.Graph;

import java.util.HashMap;

public class DoubleStorageDirectedGraph {
    private final HashMap<String, Node> index;
    private final HashMap<Node, HashMap<Node, Integer>> relationships;

    public DoubleStorageDirectedGraph() {
        this.index = new HashMap<>();
        this.relationships = new HashMap<>();
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
        addNewNodeToRelationships(node);
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
        removeNodeFromRelationships(getNodeByName(name));
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
        var edgeCanBeAdded
                = nodeExistsInRelationships(fromNode)
                && nodeExistsInRelationships(toNode)
                && getEdgeLength(fromNode, toNode) == null;
        if (edgeCanBeAdded)
            addEdgeToRelationships(getNodeByName(from), getNodeByName(to), length);
        return edgeCanBeAdded;
    }

    /**
     * Remove an edge. If the edge is not found, will not make any change.
     * @param from the starting node of edge
     * @param to the destination node of edge
     * @return whether the edge has been successfully removed
     */
    public Boolean removeEdge(String from, String to) {
        if (!edgeExists(from, to)) return false;
        removeEdgeFromRelationships(getNodeByName(from), getNodeByName(to));
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
                ? getEdgeLength(getNodeByName(from), getNodeByName(to))
                : null;
    }

    private Boolean edgeExists(String from, String to) {
        var namesInIndex
                = nameExistsInIndex(from) && nameExistsInIndex(to);
        var fromNode = getNodeByName(from);
        var toNode = getNodeByName(to);
        var nodesInRelationships
                = nodeExistsInRelationships(fromNode)
                && nodeExistsInRelationships(toNode);
        return namesInIndex && nodesInRelationships && getEdgeLength(fromNode, toNode) != null;
    }

    private void addToIndex(String name, Node node) {
        index.put(name, node);
    }

    private void addNewNodeToRelationships(Node node) {
        relationships.put(node, new HashMap<>());
    }

    private void addEdgeToRelationships(Node from, Node to, int distance) {
        relationships.get(from).put(to, distance);
    }

    private void removeFromIndex(String name) {
        index.remove(name);
    }

    private void removeNodeFromRelationships(Node node) {
        relationships.remove(node);
        relationships.values().forEach(map -> map.remove(node));
    }

    private void removeEdgeFromRelationships(Node from, Node to) {
        relationships.get(from).remove(to);
    }


    private Boolean nameExistsInIndex(String name) {
        return index.containsKey(name);
    }

    private Boolean nodeExistsInRelationships(Node node) {
        return relationships.containsKey(node);
    }

    private Node getNodeByName(String name) {
        return index.get(name);
    }

    private Integer getEdgeLength(Node from, Node to) {
        return relationships.get(from).get(to);
    }

    private record Node(String name) {
    }
}