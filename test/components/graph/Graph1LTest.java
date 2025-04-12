package components.graph;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class Graph1LTest {

    private Graph constructor() {
        return new Graph1L();
    }

    private Graph constructor(boolean directed) {
        return new Graph1L(directed);
    }

    private Graph constructorWithVerticesAndEdges(boolean directed,
            int[] vertices, int[][] edges) {
        Graph g = this.constructor(directed);

        for (int v : vertices) {
            g.addVertex(v);
        }

        for (int[] edge : edges) {
            g.addEdge(edge[0], edge[1], edge[2]);
        }

        return g;
    }

    @Test
    public void testAddVertexToEmptyGraph() {
        Graph graph = this.constructor();
        graph.addVertex(1);

        assertTrue(graph.containsVertex(1));
        assertEquals(1, graph.vertices().size());
    }

    @Test
    public void testAddMultipleVertices() {
        Graph graph = this.constructor();
        graph.addVertex(1);
        graph.addVertex(2);
        graph.addVertex(3);

        assertTrue(graph.containsVertex(1));
        assertTrue(graph.containsVertex(2));
        assertTrue(graph.containsVertex(3));
        assertEquals(3, graph.vertices().size());
    }

    @Test
    public void testAddEdgeUndirected() {
        Graph graph = this.constructor(false);
        graph.addVertex(1);
        graph.addVertex(2);
        graph.addEdge(1, 2, 5);

        assertTrue(graph.containsEdge(1, 2));
        assertTrue(graph.containsEdge(2, 1));
        assertEquals(5, graph.getWeight(1, 2));
        assertEquals(5, graph.getWeight(2, 1));
    }

    @Test
    public void testAddEdgeDirected() {
        Graph graph = this.constructor(true);
        graph.addVertex(1);
        graph.addVertex(2);
        graph.addEdge(1, 2, 5);

        assertTrue(graph.containsEdge(1, 2));
        assertFalse(graph.containsEdge(2, 1));
        assertEquals(5, graph.getWeight(1, 2));
    }

    @Test
    public void testAddMultipleEdges() {
        Graph graph = this.constructor(false);
        graph.addVertex(1);
        graph.addVertex(2);
        graph.addVertex(3);

        graph.addEdge(1, 2, 5);
        graph.addEdge(1, 3, 10);
        graph.addEdge(2, 3, 15);

        assertTrue(graph.containsEdge(1, 2));
        assertTrue(graph.containsEdge(1, 3));
        assertTrue(graph.containsEdge(2, 3));

        assertEquals(5, graph.getWeight(1, 2));
        assertEquals(10, graph.getWeight(1, 3));
        assertEquals(15, graph.getWeight(2, 3));
    }

    @Test
    public void testRemoveVertex() {
        Graph graph = this.constructor();
        graph.addVertex(1);
        graph.addVertex(2);
        graph.addVertex(3);

        graph.removeVertex(2);

        assertTrue(graph.containsVertex(1));
        assertFalse(graph.containsVertex(2));
        assertTrue(graph.containsVertex(3));
        assertEquals(2, graph.vertices().size());
    }

    @Test
    public void testRemoveVertexWithEdges() {
        Graph graph = this.constructor(false);
        graph.addVertex(1);
        graph.addVertex(2);
        graph.addVertex(3);

        graph.addEdge(1, 2, 5);
        graph.addEdge(2, 3, 10);

        graph.removeVertex(2);

        assertTrue(graph.containsVertex(1));
        assertFalse(graph.containsVertex(2));
        assertTrue(graph.containsVertex(3));

        assertFalse(graph.containsEdge(1, 2));
        assertFalse(graph.containsEdge(2, 3));
    }

    @Test
    public void testRemoveEdgeUndirected() {
        Graph graph = this.constructor(false);
        graph.addVertex(1);
        graph.addVertex(2);
        graph.addEdge(1, 2, 5);

        graph.removeEdge(1, 2);

        assertFalse(graph.containsEdge(1, 2));
        assertFalse(graph.containsEdge(2, 1));
    }

    @Test
    public void testRemoveEdgeDirected() {
        Graph graph = this.constructor(true);
        graph.addVertex(1);
        graph.addVertex(2);
        graph.addEdge(1, 2, 5);
        graph.addEdge(2, 1, 10);

        graph.removeEdge(1, 2);

        assertFalse(graph.containsEdge(1, 2));
        assertTrue(graph.containsEdge(2, 1));
        assertEquals(10, graph.getWeight(2, 1));
    }

    @Test
    public void testContainsVertexEmptyGraph() {
        Graph graph = this.constructor();
        assertFalse(graph.containsVertex(1));
    }

    @Test
    public void testContainsVertexNonEmptyGraph() {
        Graph graph = this.constructor();
        graph.addVertex(1);
        graph.addVertex(2);

        assertTrue(graph.containsVertex(1));
        assertTrue(graph.containsVertex(2));
        assertFalse(graph.containsVertex(3));
    }

    @Test
    public void testContainsEdgeUndirected() {
        Graph graph = this.constructor(false);
        graph.addVertex(1);
        graph.addVertex(2);
        graph.addVertex(3);

        graph.addEdge(1, 2, 5);

        assertTrue(graph.containsEdge(1, 2));
        assertTrue(graph.containsEdge(2, 1));
        assertFalse(graph.containsEdge(1, 3));
    }

    @Test
    public void testContainsEdgeDirected() {
        Graph graph = this.constructor(true);
        graph.addVertex(1);
        graph.addVertex(2);
        graph.addVertex(3);

        graph.addEdge(1, 2, 5);

        assertTrue(graph.containsEdge(1, 2));
        assertFalse(graph.containsEdge(2, 1));
        assertFalse(graph.containsEdge(1, 3));
    }

}
