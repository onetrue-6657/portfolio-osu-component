package components.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Layered on top of {@code GraphKernel} with secondary methods.
 *
 * @convention If the graph is undirected, for any edge (u, v), both: v ∈
 *             u.neighbors and u ∈ v.neighbors. No duplicate vertices in
 *             {@code this.vertices}.
 *
 * @correspondence {@code this.vertices} corresponds to the set of vertices in
 *                 the abstract graph. For each vertex in {@code this.vertices},
 *                 its neighbors map corresponds to the outgoing edges (and
 *                 incoming, if undirected) and their weights in the abstract
 *                 graph. {@ code this.directed} corresponds to whether the
 *                 graph allows asymmetric edges.
 */
public class Graph1L extends GraphSecondary {

    @Override
    public Graph1L newInstance() {
        return new Graph1L();
    }

    @Override
    public void clear() {
        this.vertices.clear();
        this.directed = false;
    }

    @Override
    public void transferFrom(GraphSecondary source) {
        if (source instanceof Graph1L) {
            Graph1L sourceGraph = (Graph1L) source;
            this.vertices = sourceGraph.vertices;
            this.directed = sourceGraph.directed;
            sourceGraph.vertices = new ArrayList<>();
            sourceGraph.directed = false;
        } else {
            throw new IllegalArgumentException(
                    "Source must be of type Graph1L");
        }
    }

    @Override
    public ArrayList<Graph.Vertex> vertices() {
        return this.vertices;
    }

    /**
     * The primary representation variable for the graph. It is the core of a
     * graph representation, including the tags of each vertex and a set
     * including each vertex's neighbor vertices' tags.
     */
    private ArrayList<Graph.Vertex> vertices;

    /**
     * The boolean variable to determine if the graph is directed. It is used to
     * check if the graph is directed or not.
     */
    private boolean directed;

    /**
     * Constructs a new GraphConcept object. This method is a private
     * constructor that constructs a new graph. It is used to create a new
     * representation of the graph.
     *
     * @param directed
     *            the boolean variable to determine if the graph is directed
     */
    private void createNewRep(boolean directed) {
        this.vertices = new ArrayList<Graph.Vertex>();
        this.directed = directed;
    }

    /**
     * Constructs a new GraphConcept object. This method is a public constructor
     * that constructs a new graph.
     */
    public Graph1L() {
        this.vertices = new ArrayList<Graph.Vertex>();
    }

    /**
     * Constructs a new GraphConcept object. This method is a public constructor
     * that constructs a new graph. It is used to create a new representation of
     * the graph.
     *
     * @param directed
     *            the boolean variable to determine if the graph is directed
     */
    public Graph1L(boolean directed) {
        this.createNewRep(directed);
    }

    @Override
    public void addVertex(int vertex) {
        assert vertex >= 0 : "Violation of: vertex is a non-negative integer.";
        assert !this.containsVertex(
                vertex) : "Violation of: vertex is not already in the graph.";

        this.vertices.add(new Vertex(vertex));
    }

    @Override
    public void addEdge(int from, int to, int weight) {
        assert from >= 0 : "Violation of: from is a non-negative integer.";
        assert to >= 0 : "Violation of: to is a non-negative integer.";
        assert weight > 0 : "Violation of: weight is a positive integer.";
        assert this.vertices.contains(
                new Vertex(from)) : "Violation of: from is in the arraylist.";
        assert this.vertices.contains(
                new Vertex(to)) : "Violation of: to is in the arraylist.";
        assert from != to : "Violation of: from is not equal to to.";

        Vertex fromVertex = null;
        Vertex toVertex = null;

        for (Graph.Vertex v : this.vertices) {
            if (v.getTag() == from) {
                fromVertex = (Vertex) v;
            }
            if (v.getTag() == to) {
                toVertex = (Vertex) v;
            }
        }

        fromVertex.getNeighbors().put(to, weight);
        if (!this.directed) {
            toVertex.getNeighbors().put(from, weight);
        }
    }

    @Override
    public void removeVertex(int vertex) {
        assert vertex >= 0 : "Violation of: vertex is a non-negative integer.";
        assert this.vertices.contains(new Vertex(
                vertex)) : "Violation of: vertex is in the arraylist.";

        this.vertices.remove(new Vertex(vertex));
        for (Graph.Vertex v : this.vertices()) {
            if (v.getNeighbors().containsKey(vertex)) {
                v.getNeighbors().remove(vertex);
            }
        }
    }

    @Override
    public void removeEdge(int from, int to) {
        assert from >= 0 : "Violation of: from is a non-negative integer.";
        assert to >= 0 : "Violation of: to is a non-negative integer.";
        assert this.vertices.contains(
                new Vertex(from)) : "Violation of: from is in the arraylist.";
        assert this.vertices.contains(
                new Vertex(to)) : "Violation of: to is in the arraylist.";
        assert from != to : "Violation of: from is not equal to to.";

        Vertex fromVertex = null;
        for (Graph.Vertex v : this.vertices) {
            if (v.getTag() == from) {
                fromVertex = (Vertex) v;
                break;
            }
        }

        Vertex toVertex = null;
        if (!this.directed) {
            for (Graph.Vertex v : this.vertices) {
                if (v.getTag() == to) {
                    toVertex = (Vertex) v;
                    break;
                }
            }
        }

        fromVertex.getNeighbors().remove(to);

        if (!this.directed) {
            toVertex.getNeighbors().remove(from);
        }
    }

    @Override
    public boolean containsVertex(int vertex) {
        assert vertex >= 0 : "Violation of: vertex is a non-negative integer.";

        for (Graph.Vertex v : this.vertices) {
            if (v.getTag() == vertex) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean containsEdge(int from, int to) {
        assert from >= 0 : "Violation of: from is a non-negative integer.";
        assert to >= 0 : "Violation of: to is a non-negative integer.";
        assert from != to : "Violation of: from is not equal to to.";

        boolean fromExists = false;
        boolean toExists = false;
        for (Graph.Vertex v : this.vertices) {
            if (v.getTag() == from) {
                fromExists = true;
            }
            if (v.getTag() == to) {
                toExists = true;
            }
        }

        assert fromExists : "Violation of: from is in the graph.";
        assert toExists : "Violation of: to is in the graph.";

        Vertex fromVertex = null;
        for (Graph.Vertex v : this.vertices) {
            if (v.getTag() == from) {
                fromVertex = (Vertex) v;
                break;
            }
        }

        return fromVertex.getNeighbors().containsKey(to);
    }

    @Override
    public boolean isDirected() {
        // for (Vertex v : this.vertices()) {
        //     int vTag = v.getTag();
        //     for (int neighborTag : v.getNeighbors().keySet()) {
        //         Vertex neighborVertex = null;
        //         for (Vertex potential : this.vertices()) {
        //             if (potential.getTag() == neighborTag) {
        //                 neighborVertex = potential;
        //                 break;
        //             }
        //         }

        //         if (neighborVertex != null) {
        //             if (!neighborVertex.getNeighbors().containsKey(vTag)) {
        //                 return true;
        //             }
        //         }
        //     }
        // }

        // return false;
        return this.directed;
    }

    /**
     * Representation of a single vertex in the graph.
     */
    public static final class Vertex implements Graph.Vertex {

        /**
         * The tag of the vertex.
         */
        private int tag;

        /**
         * The set of tags of the vertex's neighbors.
         */
        private Map<Integer, Integer> neighbors;

        @Override
        public Map<Integer, Integer> getNeighbors() {
            return this.neighbors;
        }

        @Override
        public int getTag() {
            return this.tag;
        }

        /**
         * Constructs a new Vertex object.
         *
         * @param tag
         *            the tag of the vertex
         */
        public Vertex(int tag) {
            this.tag = tag;
            this.neighbors = new HashMap<Integer, Integer>();
        }

        /**
         * Returns if two objects are the same vertex.
         *
         * @param obj
         *            the object to be compared
         *
         * @return true if the two objects are the same vertex, false otherwise.
         */
        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || this.getClass() != obj.getClass()) {
                return false;
            }
            Vertex vertex = (Vertex) obj;
            return this.tag == vertex.tag;
        }

        /**
         * Returns the hash code of the vertex.
         *
         * @return the hash code of the vertex.
         */
        @Override
        public int hashCode() {
            return this.tag;
        }
    }
}
