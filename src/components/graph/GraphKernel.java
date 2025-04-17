package components.graph;

/**
 * Graph kernel component with primary methods.
 *
 * @mathmodel type Graph is modeled by a set of vertices and a set of edges
 * @initially <pre>
 * ():
 *   ensures
 *     this is an empty graph with no vertices and no edges.
 * </pre>
 *
 * @author Zheng Ni
 */
public interface GraphKernel extends standard {

    /**
     * Adds the Vertex {@code vertex} to the graph. It will insert a new vertex
     * into the arraylist.
     *
     * @param vertex
     *            the tag of the vertex to be added to the graph
     *
     * @ensures after execution, the graph contains a Vertex with tag
     *          {@code vertex}.
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
     * @ensures after execution, there is an edge from vertex {@code from} to
     *          vertex {@code to} with weight {@code weight}. If the graph is
     *          undirected, an edge from vertex {@code to} to vertex
     *          {@code from} is also added.
     */
    void addEdge(int from, int to, int weight);

    /**
     * Removes the vertex from the graph. It will remove the vertex from the
     * arraylist. It will also remove every edge that is connected to the
     * vertex.
     *
     * @param vertex
     *            the tag of the vertex to be removed
     *
     * @ensures after execution, the graph no longer contains the Vertex with
     *          tag {@code vertex} and all edges incident to that vertex have
     *          been removed.
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
     * @ensures after execution, there is no edge from vertex {@code from} to
     *          vertex {@code to}. If the graph is undirected, the edge from
     *          vertex {@code to} to vertex {@code from} is also removed.
     */
    void removeEdge(int from, int to);

    /**
     * Checks if the graph contains the vertex with a certain tag.
     *
     * @param vertex
     *            the tag of the vertex to be checked
     *
     * @ensures the return value accurately reflects the presence of a Vertex
     *          with tag {@code vertex} in the graph.
     * @return true if the graph contains the vertex with tag {@code vertex},
     *         false otherwise.
     *
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
     * @ensures the return value accurately reflects the existence of an edge
     *          from vertex {@code from} to vertex {@code to} in the graph.
     * @return true if there is an edge from vertex {@code from} to vertex
     *         {@code to}, false otherwise.
     *
     */
    boolean containsEdge(int from, int to);

    /**
     * Returns if the graph is directed.
     *
     * @return true if the graph is directed, false otherwise.
     */
    boolean isDirected();
}
