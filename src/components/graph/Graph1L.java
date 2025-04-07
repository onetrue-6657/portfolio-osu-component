package components.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Layered on top of {@code GraphKernel} with secondary methods.
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
        assert !this.vertices.contains(new Vertex(
                vertex)) : "Violation of: vertex is not in the arraylist.";

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

        this.vertices.get(from).getNeighbors().put(to, weight);
        if (!this.directed) {
            this.vertices.get(to).getNeighbors().put(from, weight);
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

        this.vertices.get(from).getNeighbors().remove(to);
        if (!this.directed) {
            this.vertices.get(to).getNeighbors().remove(from);
        }
    }

    @Override
    public boolean containsVertex(int vertex) {
        assert vertex >= 0 : "Violation of: vertex is a non-negative integer.";
        assert this.vertices.contains(new Vertex(
                vertex)) : "Violation of: vertex is in the arraylist.";

        return this.vertices.contains(new Vertex(vertex));
    }

    @Override
    public boolean containsEdge(int from, int to) {
        assert from >= 0 : "Violation of: from is a non-negative integer.";
        assert to >= 0 : "Violation of: to is a non-negative integer.";
        assert this.vertices.contains(
                new Vertex(from)) : "Violation of: from is in the arraylist.";
        assert this.vertices.contains(
                new Vertex(to)) : "Violation of: to is in the arraylist.";
        assert from != to : "Violation of: from is not equal to to.";

        return this.vertices.get(from).getNeighbors().containsKey(to);
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
