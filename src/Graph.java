import java.util.ArrayList;
import java.util.Map;

/**
 * {@code GraphKernel} enhanced with secondary methods.
 */
public interface Graph extends GraphKernel {
    /**
     * Returns all neighbors of a certain vertex.
     *
     * @param vertex
     *            the tag of the vertex
     *
     * @requires {@code vertex} is a non-negative integer and Vertex with tag
     *           {@code vertex} is in the arraylist.
     * @return a map of the neighbors of the vertex with tag {@code vertex}.
     */
    Map<Integer, Integer> getNeighbors(int vertex);

    /**
     * Returns the weight of a certain edge.
     *
     * @param from
     *            the tag of the vertex where the edge starts
     * @param to
     *            the tag of the vertex where the edge ends
     *
     * @requires {@code from} and {@code to} are non-negative integers. Vertices
     *           with tags {@code from} and {@code to} are in the map.
     *           {@code from} is not equal to {@code to}.
     * @return the weight of the edge from vertex {@code from} to vertex
     *         {@code to}.
     */
    int getWeight(int from, int to);

    /**
     * Iterates all the vertices in the graph from a start point vertex using
     * breadth-first search.
     *
     * @param start
     *            the tag of the vertex where the search starts
     *
     * @requires {@code start} is a non-negative integer and Vertex with tag
     *           {@code start} is in the arraylist.
     * @return an arraylist of the tags of the vertices in the graph.
     *         Breadth-first search is used to iterate all the vertices in the
     *         graph.
     */
    ArrayList<Integer> bfs(int start);

    /**
     * Iterates all the vertices in the graph from a start point vertex using
     * depth-first search.
     *
     * @param start
     *            the tag of the vertex where the search starts
     *
     * @requires {@code start} is a non-negative integer and Vertex with tag
     *           {@code start} is in the arraylist.
     * @return an arraylist of the tags of the vertices in the graph.
     *         Depth-first search is used to iterate all the vertices in the
     *         graph.
     */
    ArrayList<Integer> dfs(int start);

    /**
     * Returns the shortest path in the graph from a start point vertex to an
     * end point vertex using Dijkstra's algorithm.
     *
     * @param start
     *            the tag of the vertex where the path starts
     * @param end
     *            the tag of the vertex where the path ends
     *
     * @requires {@code start} and {@code end} are non-negative integers.
     *           Vertices with tags {@code start} and {@code end} are in the
     *           map. {@code start} is not equal to {@code end}.
     * @return an arraylist of the tags of the vertices in the shortest path.
     *         Dijkstra's algorithm is used to find the shortest path in the
     *         graph.
     */
    ArrayList<Integer> shortestPath(int start, int end);

    /**
     * Returns if the graph is connected.
     *
     * @return true if the graph is connected, false otherwise.
     */
    boolean isConnected();
}
