package components.graph;

import java.util.ArrayList;
import java.util.Map;

/**
 * {@code GraphKernel} enhanced with secondary methods.
 *
 * @author Zheng Ni
 */
public interface Graph extends GraphKernel {

    /**
     * Returns the number of vertices in the graph.
     *
     * @return the number of vertices in the graph.
     */
    ArrayList<Vertex> vertices();

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
     * @ensures {@code from} and {@code to} are non-negative integers. Vertices
     *          with tags {@code from} and {@code to} are in the arraylist.
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
     * @ensures the graph is not modified and the arraylist is ordered by the
     *          order using breadth-first search.
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
     * @ensures the graph is not modified and the arraylist is ordered by the
     *          order using depth-first search.
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
     * @ensures {@code start} and {@code end} are non-negative integers.
     *          Vertices with tags {@code start} and {@code end} are in the
     *          arraylist.
     * @return an arraylist of the tags of the vertices in the shortest path.
     *         Dijkstra's algorithm is used to find the shortest path in the
     *         graph.
     */
    ArrayList<Integer> shortestPath(int start, int end);

    /**
     * Returns if the graph is connected.
     *
     * @ensures the graph is not modified.
     * @return true if the graph is connected, false otherwise.
     */
    boolean isConnected();

    /**
     * Returns if the graph is directed.
     *
     * @return true if the graph is directed, false otherwise.
     */
    boolean isDirected();

    /**
     * Returns the number of connected components.
     *
     * @requires the graph is undirected.
     *
     * @return the number of connected components.
     */
    int connectedComponents();

    /**
     * Returns the list of minimum spanning tree using Prim's algorithm.
     *
     * @return the list of minimum spanning tree.
     */
    ArrayList<Integer> minimumSpanningTree();

    /**
     * Representation of a vertex in the graph.
     */
    interface Vertex {

        /**
         * The set of tags of the vertex's neighbors.
         *
         * @ensures the return {@code Map} includes the tags of the neighbors of
         *          the vertex.
         * @return the set of tags of the vertex's neighbors.
         */
        Map<Integer, Integer> getNeighbors();

        /**
         * Get the tag of the vertex.
         *
         * @ensures the return value is the only tag of the vertex.
         * @return the tag of the vertex.
         */
        int getTag();

        /**
         * Returns if two objects are the same vertex.
         *
         * @param obj
         *            the object to be compared
         *
         * @ensures the return value accurately reflects if the two objects are
         *          the same vertex.
         * @return true if the two objects are the same vertex, false otherwise.
         */
        @Override
        boolean equals(Object obj);

        /**
         * Returns the hash code of the vertex.
         *
         * @ensures the return value is the hash code of the vertex.
         * @return the hash code of the vertex.
         */
        @Override
        int hashCode();
    }
}
