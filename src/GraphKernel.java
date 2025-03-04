/**
 * Graph kernel component with primary methods.
 *
 * @mathmodel type Graph is modeled by a set of vertices and a set of edges
 * @initially <pre>
 * ():
 *   ensures
 *     this is an empty graph with no vertices and no edges.
 * </pre>
 */
public interface GraphKernel {

    /**
     * Adds the Vertex {@code vertex} to the graph. It will insert a new vertex
     * into the arraylist.
     *
     * @param vertex
     *            the tag of the vertex to be added to the graph
     *
     * @requires {@code vertex} is a non-negative integer and Vertex with tag
     *           {@code vertex} is not in the arraylist.
     */
    void addVertex(int vertex);

    /**
     * Adds an edge to the graph. It will add the neighbor vertex to the set of
     * the vertex's neighbors.
     *
     * @param from
     *            the tag of the vertex where the edge starts
     * @param to
     *            the tag of the vertex where the edge ends
     * @param weight
     *            the weight of the edge
     *
     * @requires {@code from} and {@code to} are non-negative integers and
     *           {@code weight} is a positive integer. Vertices with tags
     *           {@code from} and {@code to} are in the map. {@code from} is not
     *           equal to {@code to}.
     */
    void addEdge(int from, int to, int weight);

    /**
     * Removes the vertex from the graph. It will remove the vertex from the
     * arraylist. It will also remove every edge that is connected to the vertex
     *
     * @param vertex
     *            the tag of the vertex to be removed
     *
     * @requires {@code vertex} is a non-negative integer and Vertex with tag
     *           {@code vertex} is in the arraylist.
     *
     */
    void removeVertex(int vertex);

    /**
     * Removes the edge from the graph. It will remove the neighbor vertex from
     * the set of the vertex's neighbors.
     *
     * @param from
     *            the tag of the vertex where the edge starts
     * @param to
     *            the tag of the vertex where the edge ends
     *
     * @requires {@code from} and {@code to} are non-negative integers. Vertices
     *           with tags {@code from} and {@code to} are in the map.
     *           {@code from} is not equal to {@code to}.
     */
    void removeEdge(int from, int to);

    /**
     * Checks if the graph contains the vertex with a certain tag.
     *
     * @param vertex
     *            the tag of the vertex to be checked
     *
     * @requires {@code vertex} is a non-negative integer and Vertex with tag
     *           {@code vertex} is in the arraylist.
     * @return true if the graph contains the vertex with tag {@code vertex},
     *         false otherwise.
     */
    boolean containsVertex(int vertex);

    /**
     * Checks if there is an edge from a certain vertex to another certain
     * vertex.
     *
     * @param from
     *            the tag of the vertex where the edge starts
     * @param to
     *            the tag of the vertex where the edge ends
     *
     * @requires {@code from} and {@code to} are non-negative integers. Vertices
     *           with tags {@code from} and {@code to} are in the map.
     *           {@code from} is not equal to {@code to}.
     * @return true if there is an edge from vertex {@code from} to vertex
     *         {@code to}, false otherwise.
     */
    boolean containsEdge(int from, int to);
}
