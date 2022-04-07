package com.idontwantagirlfriend.Graph;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestDoubleStorageGraph {
    private DoubleStorageDirectedGraph graph;
    @BeforeEach
    public void refreshGraph() {
        graph = new DoubleStorageDirectedGraph();
    }

    @Test
    public void addNodeRemoveNode() {
        assertTrue(graph.addNode("Hello"));
        assertTrue(graph.removeNode("Hello"));
    }

    @Test
    public void removeNonExistentNode_ShouldReturnFalse() {
        assertFalse(graph.removeNode("world"));
        graph.addNode("Hello");
        assertFalse(graph.removeNode("world"));
    }

    @Test
    public void addDuplicateNode_ShouldReturnFalse() {
        graph.addNode("Hello");
        assertFalse(graph.addNode("Hello"));
    }

    @Test
    public void addEdgeAndGet() {
        graph.addNode("This");
        graph.addNode("is");
        assertTrue(graph.addEdge("This", "is", 127));
        assertEquals(127, graph.getEdge("This", "is"));
    }

    @Test
    public void addEdgeOnNonExistentNodes_ShouldReturnFalseAndMakeNoChange() {
        graph.addNode("This");
        graph.addNode("is");
        assertFalse(graph.addEdge("This", "No", 235));
        assertFalse(graph.addEdge("No", "is", 238));
    }

    @Test
    public void addDuplicateEdge_ShouldReturnFalseAndMakeNoChange() {
        graph.addNode("This");
        graph.addNode("is");
        graph.addEdge("This", "is", 255);
        assertFalse(graph.addEdge("This", "is", 25000));
        assertEquals(255, graph.getEdge("This", "is"));
    }

    @Test
    public void addEdgeAndRemoveAndGet_ShouldReturnNull() {
        graph.addNode("This");
        graph.addNode("is");
        graph.addEdge("This", "is", 255);
        assertTrue(graph.removeEdge("This", "is"));
        assertNull(graph.getEdge("This", "is"));
    }

    @Test
    public void getNonExistentEdge_ShouldReturnNull() {
        graph.addNode("This");
        graph.addNode("is");
        assertNull(graph.getEdge("This", "my"));
        assertNull(graph.getEdge("my", "is"));
        assertNull(graph.getEdge("Hello", "world"));
    }

    @Test
    public void removeNonExistentEdge_ShouldReturnFalseAndMakeNoChange() {
        graph.addNode("This");
        graph.addNode("is");
        graph.addEdge("This", "is", 255);
        assertFalse(graph.removeEdge("hello", "is"));
        assertFalse(graph.removeEdge("This", "my"));
        assertFalse(graph.removeEdge("hello", "world"));
        assertEquals(255, graph.getEdge("This", "is"));
    }
}