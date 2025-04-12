package components.graph;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Test;

public class GraphTest {

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
        // 由于 BFS 在同一层级的节点顺序可能不确定，这里不测试具体顺序
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
        // 由于 BFS 在同一层级的节点顺序可能不确定，这里不测试具体顺序
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
        // DFS的顺序取决于邻居的迭代顺序，不测试具体顺序
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
        // 顶点2和3没有连接到图的其余部分
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

        // 第一个连通分量：0-1-2
        graph.addEdge(0, 1, 1);
        graph.addEdge(1, 2, 1);

        // 第二个连通分量：3-4
        graph.addEdge(3, 4, 1);

        // 第三个连通分量：单独的顶点5

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

        // MST应该包含所有顶点
        assertEquals(4, mst.size());
        assertTrue(mst.contains(0));
        assertTrue(mst.contains(1));
        assertTrue(mst.contains(2));
        assertTrue(mst.contains(3));

        // 这里无法测试确切的MST结构，但可以验证其基本性质
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
        graph2.addEdge(1, 2, 10); // 不同的边权重

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