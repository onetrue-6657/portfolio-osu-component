package components.graph;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Test class for the Graph1L class.
 *
 * @author Zheng Ni
 */
public class Graph1LTest {

    /**
     * Create a new undirected graph.
     *
     * @return a new undirected graph.
     */
    private Graph constructor() {
        return new Graph1L();
    }

    /**
     * Create a new graph.
     *
     * @param directed
     *            whether the graph is directed or not
     * @return a new graph
     */
    private Graph constructor(boolean directed) {
        return new Graph1L(directed);
    }

    /**
     * Create a new graph with vertices and edges.
     *
     * @param directed
     *            whether the graph is directed or not
     * @param vertices
     *            the vertices to be added to the graph
     * @param edges
     *            the edges to be added to the graph
     * @return a new graph with vertices and edges
     */
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

    /**
     * Test constructors
     */

    @Test
    public void testDefaultConstructor() {
        Graph graph = this.constructor();

        assertEquals(0, graph.vertices().size());
        assertFalse(graph.isDirected());
    }

    @Test
    public void testParameterizedConstructor() {
        Graph graph = this.constructor();
        Graph directedGraph = this.constructor(true);
        Graph undirectedGraph = this.constructor(false);

        assertFalse(graph.isDirected());
        assertTrue(directedGraph.isDirected());
        assertFalse(undirectedGraph.isDirected());
    }

    @Test
    public void testConstructorWithVerticesAndEdges() {
        int[] vertices = { 1, 3, 5 };
        int[][] edges = { { 1, 3, 10 }, { 3, 5, 20 }, { 5, 1, 30 } };

        Graph undirectedGraph = this.constructorWithVerticesAndEdges(false,
                vertices, edges);

        assertEquals(3, undirectedGraph.vertices().size());
        assertTrue(undirectedGraph.containsVertex(1));
        assertTrue(undirectedGraph.containsVertex(3));
        assertTrue(undirectedGraph.containsVertex(5));

        assertTrue(undirectedGraph.containsEdge(1, 3));
        assertTrue(undirectedGraph.containsEdge(3, 5));
        assertTrue(undirectedGraph.containsEdge(5, 1));
        assertEquals(10, undirectedGraph.getWeight(1, 3));
        assertEquals(20, undirectedGraph.getWeight(3, 5));
        assertEquals(30, undirectedGraph.getWeight(5, 1));

        assertTrue(undirectedGraph.containsEdge(3, 1));
        assertTrue(undirectedGraph.containsEdge(5, 3));
        assertTrue(undirectedGraph.containsEdge(1, 5));
        assertEquals(10, undirectedGraph.getWeight(3, 1));
        assertEquals(20, undirectedGraph.getWeight(5, 3));
        assertEquals(30, undirectedGraph.getWeight(1, 5));

        Graph directedGraph = this.constructorWithVerticesAndEdges(true,
                vertices, edges);

        assertEquals(3, directedGraph.vertices().size());
        assertTrue(directedGraph.containsVertex(1));
        assertTrue(directedGraph.containsVertex(3));
        assertTrue(directedGraph.containsVertex(5));

        assertTrue(directedGraph.containsEdge(1, 3));
        assertTrue(directedGraph.containsEdge(3, 5));
        assertTrue(directedGraph.containsEdge(5, 1));
        assertEquals(10, directedGraph.getWeight(1, 3));
        assertEquals(20, directedGraph.getWeight(3, 5));
        assertEquals(30, directedGraph.getWeight(5, 1));

        assertFalse(directedGraph.containsEdge(3, 1));
        assertFalse(directedGraph.containsEdge(5, 3));
        assertFalse(directedGraph.containsEdge(1, 5));
    }

    /**
     * Test of kernel methods
     */

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

    @Test
    public void testIsDirected() {
        Graph graph = this.constructor(false);
        assertFalse(graph.isDirected());

        graph = this.constructor(true);
        assertTrue(graph.isDirected());
    }

}
