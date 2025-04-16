package components.graph;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Test;

public class GraphTest {

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
     * Test of secondary methods
     */

    @Test
    public void testGetWeight() {
        Graph graph = this.constructor(false);
        graph.addVertex(1);
        graph.addVertex(2);
        graph.addVertex(3);

        graph.addEdge(1, 2, 5);
        graph.addEdge(1, 3, 10);
        graph.addEdge(2, 3, 15);

        assertEquals(5, graph.getWeight(1, 2));
        assertEquals(5, graph.getWeight(2, 1));
        assertEquals(10, graph.getWeight(1, 3));
        assertEquals(10, graph.getWeight(3, 1));
        assertEquals(15, graph.getWeight(2, 3));
        assertEquals(15, graph.getWeight(3, 2));
    }

    @Test
    public void testBfsUndirected() {
        Graph graph = this.constructor(false);
        graph.addVertex(0);
        graph.addVertex(1);
        graph.addVertex(2);
        graph.addVertex(3);

        graph.addEdge(0, 1, 1);
        graph.addEdge(0, 2, 1);
        graph.addEdge(1, 3, 1);
        graph.addEdge(2, 3, 1);

        ArrayList<Integer> bfsResult = graph.bfs(0);

        assertEquals(4, bfsResult.size());
        assertEquals(Integer.valueOf(0), bfsResult.get(0));
        assertTrue(bfsResult.contains(1));
        assertTrue(bfsResult.contains(2));
        assertTrue(bfsResult.contains(3));
    }

    @Test
    public void testBfsDirected() {
        Graph graph = this.constructor(true);
        graph.addVertex(0);
        graph.addVertex(1);
        graph.addVertex(2);
        graph.addVertex(3);

        graph.addEdge(0, 1, 1);
        graph.addEdge(0, 2, 1);
        graph.addEdge(1, 3, 1);

        ArrayList<Integer> bfsResult = graph.bfs(0);

        assertEquals(4, bfsResult.size());
        assertEquals(Integer.valueOf(0), bfsResult.get(0));
        assertTrue(bfsResult.contains(1));
        assertTrue(bfsResult.contains(2));
        assertTrue(bfsResult.contains(3));
    }

    @Test
    public void testDfsUndirected() {
        Graph graph = this.constructor(false);
        graph.addVertex(0);
        graph.addVertex(1);
        graph.addVertex(2);
        graph.addVertex(3);

        graph.addEdge(0, 1, 1);
        graph.addEdge(0, 2, 1);
        graph.addEdge(1, 3, 1);
        graph.addEdge(2, 3, 1);

        ArrayList<Integer> dfsResult = graph.dfs(0);

        assertEquals(4, dfsResult.size());
        assertEquals(Integer.valueOf(0), dfsResult.get(0));
        assertTrue(dfsResult.contains(1));
        assertTrue(dfsResult.contains(2));
        assertTrue(dfsResult.contains(3));
    }

    @Test
    public void testDfsDirected() {
        Graph graph = this.constructor(true);
        graph.addVertex(0);
        graph.addVertex(1);
        graph.addVertex(2);
        graph.addVertex(3);

        graph.addEdge(0, 1, 1);
        graph.addEdge(0, 2, 1);
        graph.addEdge(1, 3, 1);

        ArrayList<Integer> dfsResult = graph.dfs(0);

        assertEquals(4, dfsResult.size());
        assertEquals(Integer.valueOf(0), dfsResult.get(0));
        assertTrue(dfsResult.contains(1));
        assertTrue(dfsResult.contains(2));
        assertTrue(dfsResult.contains(3));
    }

    @Test
    public void testShortestPathUndirected() {
        Graph graph = this.constructor(false);
        graph.addVertex(0);
        graph.addVertex(1);
        graph.addVertex(2);
        graph.addVertex(3);

        graph.addEdge(0, 1, 1);
        graph.addEdge(0, 2, 10);
        graph.addEdge(1, 3, 1);
        graph.addEdge(2, 3, 1);

        ArrayList<Integer> path = graph.shortestPath(0, 3);

        assertEquals(3, path.size());
        assertEquals(Integer.valueOf(0), path.get(0));
        assertEquals(Integer.valueOf(1), path.get(1));
        assertEquals(Integer.valueOf(3), path.get(2));
    }

    @Test
    public void testShortestPathDirected() {
        Graph graph = this.constructor(true);
        graph.addVertex(0);
        graph.addVertex(1);
        graph.addVertex(2);
        graph.addVertex(3);

        graph.addEdge(0, 1, 10);
        graph.addEdge(0, 2, 5);
        graph.addEdge(2, 3, 2);
        graph.addEdge(1, 3, 1);

        ArrayList<Integer> path = graph.shortestPath(0, 3);

        assertEquals(3, path.size());
        assertEquals(Integer.valueOf(0), path.get(0));
        assertEquals(Integer.valueOf(2), path.get(1));
        assertEquals(Integer.valueOf(3), path.get(2));
    }

    @Test
    public void testIsConnectedTrue() {
        Graph graph = this.constructor(false);
        graph.addVertex(0);
        graph.addVertex(1);
        graph.addVertex(2);
        graph.addVertex(3);

        graph.addEdge(0, 1, 1);
        graph.addEdge(0, 2, 1);
        graph.addEdge(2, 3, 1);

        assertTrue(graph.isConnected());
    }

    @Test
    public void testIsConnectedFalse() {
        Graph graph = this.constructor(false);
        graph.addVertex(0);
        graph.addVertex(1);
        graph.addVertex(2);
        graph.addVertex(3);

        graph.addEdge(0, 1, 1);
        graph.addEdge(2, 3, 1);

        assertFalse(graph.isConnected());
    }

    @Test
    public void testIsDirectedFalse() {
        Graph graph = this.constructor(false);
        assertFalse(graph.isDirected());
    }

    @Test
    public void testIsDirectedTrue() {
        Graph graph = this.constructor(true);
        assertTrue(graph.isDirected());
    }

    @Test
    public void testConnectedComponentsOneComponent() {
        Graph graph = this.constructor(false);
        graph.addVertex(0);
        graph.addVertex(1);
        graph.addVertex(2);
        graph.addVertex(3);

        graph.addEdge(0, 1, 1);
        graph.addEdge(1, 2, 1);
        graph.addEdge(2, 3, 1);

        assertEquals(1, graph.connectedComponents());
    }

    @Test
    public void testConnectedComponentsMultipleComponents() {
        Graph graph = this.constructor(false);
        graph.addVertex(0);
        graph.addVertex(1);
        graph.addVertex(2);
        graph.addVertex(3);
        graph.addVertex(4);
        graph.addVertex(5);

        graph.addEdge(0, 1, 1);
        graph.addEdge(1, 2, 1);

        graph.addEdge(3, 4, 1);

        assertEquals(3, graph.connectedComponents());
    }

    @Test
    public void testMinimumSpanningTree() {
        Graph graph = this.constructor(false);
        graph.addVertex(0);
        graph.addVertex(1);
        graph.addVertex(2);
        graph.addVertex(3);

        graph.addEdge(0, 1, 10);
        graph.addEdge(0, 2, 6);
        graph.addEdge(0, 3, 5);
        graph.addEdge(1, 3, 15);
        graph.addEdge(2, 3, 4);

        ArrayList<Integer> mst = graph.minimumSpanningTree();

        assertEquals(4, mst.size());
        assertTrue(mst.contains(0));
        assertTrue(mst.contains(1));
        assertTrue(mst.contains(2));
        assertTrue(mst.contains(3));
    }

    @Test
    public void testEqualsTrue() {
        Graph graph1 = this.constructor(false);
        graph1.addVertex(1);
        graph1.addVertex(2);
        graph1.addEdge(1, 2, 5);

        Graph graph2 = this.constructor(false);
        graph2.addVertex(1);
        graph2.addVertex(2);
        graph2.addEdge(1, 2, 5);

        assertTrue(graph1.equals(graph2));
        assertTrue(graph2.equals(graph1));
    }

    @Test
    public void testEqualsFalse() {
        Graph graph1 = this.constructor(false);
        graph1.addVertex(1);
        graph1.addVertex(2);
        graph1.addEdge(1, 2, 5);

        Graph graph2 = this.constructor(false);
        graph2.addVertex(1);
        graph2.addVertex(2);
        graph2.addEdge(1, 2, 10);

        assertFalse(graph1.equals(graph2));
        assertFalse(graph2.equals(graph1));
    }

    @Test
    public void testHashCode() {
        Graph graph1 = this.constructor(false);
        graph1.addVertex(1);
        graph1.addVertex(2);
        graph1.addEdge(1, 2, 5);

        Graph graph2 = this.constructor(false);
        graph2.addVertex(1);
        graph2.addVertex(2);
        graph2.addEdge(1, 2, 5);

        assertEquals(graph1.hashCode(), graph2.hashCode());
    }
}