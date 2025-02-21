import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;

/**
 * Class representing a proof of concept implementation for a Graph component.
 *
 * @author o-v-o (Zheng Ni)
 */
public class GraphConcept {

    /**
     * The primary representation variable for the graph. It is the core of a
     * graph representation, including the tags of each vertex and a set
     * including each vertex's neighbor vertices' tags.
     */
    private ArrayList<Vertex> vertices;

    /**
     * The boolean variable to determine if the graph is directed. It is used to
     * check if the graph is directed or not.
     */
    private boolean directed;

    // /**
    //  * The boolean variable to determine if the graph is weighted. It is used to
    //  * check if the graph is weighted or not.
    //  */
    // private boolean weighted;

    /**
     * Constructs a new GraphConcept object. This method is a public constructor
     * that constructs a new graph.
     */
    public GraphConcept() {
        this.vertices = new ArrayList<Vertex>();
    }

    /**
     * Kernel Methods
     */

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
    public void addVertex(int vertex) {
        assert vertex >= 0 : "Violation of: vertex is a non-negative integer.";
        assert !this.vertices.contains(new Vertex(
                vertex)) : "Violation of: vertex is not in the arraylist.";

        this.vertices.add(new Vertex(vertex));
    }

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
    public void addEdge(int from, int to, int weight) {
        assert from >= 0 : "Violation of: from is a non-negative integer.";
        assert to >= 0 : "Violation of: to is a non-negative integer.";
        assert weight > 0 : "Violation of: weight is a positive integer.";
        assert this.vertices.contains(
                new Vertex(from)) : "Violation of: from is in the arraylist.";
        assert this.vertices.contains(
                new Vertex(to)) : "Violation of: to is in the arraylist.";
        assert from != to : "Violation of: from is not equal to to.";

        this.vertices.get(from).neighbors.put(to, weight);
        if (!this.directed) {
            this.vertices.get(to).neighbors.put(from, weight);
        }
    }

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
    public void removeVertex(int vertex) {
        assert vertex >= 0 : "Violation of: vertex is a non-negative integer.";
        assert this.vertices.contains(new Vertex(
                vertex)) : "Violation of: vertex is in the arraylist.";

        this.vertices.remove(new Vertex(vertex));
        for (Vertex v : this.vertices) {
            if (v.neighbors.containsKey(vertex)) {
                v.neighbors.remove(vertex);
            }
        }
    }

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
    public void removeEdge(int from, int to) {
        assert from >= 0 : "Violation of: from is a non-negative integer.";
        assert to >= 0 : "Violation of: to is a non-negative integer.";
        assert this.vertices.contains(
                new Vertex(from)) : "Violation of: from is in the arraylist.";
        assert this.vertices.contains(
                new Vertex(to)) : "Violation of: to is in the arraylist.";
        assert from != to : "Violation of: from is not equal to to.";

        this.vertices.get(from).neighbors.remove(to);
        if (!this.directed) {
            this.vertices.get(to).neighbors.remove(from);
        }
    }

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
    public boolean containsVertex(int vertex) {
        assert vertex >= 0 : "Violation of: vertex is a non-negative integer.";
        assert this.vertices.contains(new Vertex(
                vertex)) : "Violation of: vertex is in the arraylist.";

        return this.vertices.contains(new Vertex(vertex));
    }

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
    public boolean containsEdge(int from, int to) {
        assert from >= 0 : "Violation of: from is a non-negative integer.";
        assert to >= 0 : "Violation of: to is a non-negative integer.";
        assert this.vertices.contains(
                new Vertex(from)) : "Violation of: from is in the arraylist.";
        assert this.vertices.contains(
                new Vertex(to)) : "Violation of: to is in the arraylist.";
        assert from != to : "Violation of: from is not equal to to.";

        return this.vertices.get(from).neighbors.containsKey(to);
    }

    /**
     * Secondary Methods
     */

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
    public Map<Integer, Integer> getNeighbors(int vertex) {
        assert vertex >= 0 : "Violation of: vertex is a non-negative integer.";
        assert this.vertices.contains(new Vertex(
                vertex)) : "Violation of: vertex is in the arraylist.";

        return this.vertices.get(vertex).neighbors;
    }

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
    public int getWeight(int from, int to) {
        assert from >= 0 : "Violation of: from is a non-negative integer.";
        assert to >= 0 : "Violation of: to is a non-negative integer.";
        assert this.vertices.contains(
                new Vertex(from)) : "Violation of: from is in the arraylist.";
        assert this.vertices.contains(
                new Vertex(to)) : "Violation of: to is in the arraylist.";
        assert from != to : "Violation of: from is not equal to to.";

        return this.vertices.get(from).neighbors.get(to);
    }

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
    public ArrayList<Integer> bfs(int start) {
        assert start >= 0 : "Violation of: start is a non-negative integer.";
        assert this.vertices.contains(
                new Vertex(start)) : "Violation of: start is in the arraylist.";

        ArrayList<Integer> result = new ArrayList<>();
        Queue<Integer> queue = new LinkedList<>();
        boolean[] visited = new boolean[this.vertices.size()];
        Arrays.fill(visited, false);

        queue.add(start);
        visited[start] = true;

        while (!queue.isEmpty()) {
            int current = queue.poll();
            result.add(current);

            for (int neighbor : this.vertices.get(current).neighbors.keySet()) {
                if (!visited[neighbor]) {
                    queue.add(neighbor);
                    visited[neighbor] = true;
                }
            }
        }

        return result;
    }

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
    public ArrayList<Integer> dfs(int start) {
        assert start >= 0 : "Violation of: start is a non-negative integer.";
        assert this.vertices.contains(
                new Vertex(start)) : "Violation of: start is in the arraylist.";

        ArrayList<Integer> result = new ArrayList<>();
        Stack<Integer> stack = new Stack<>();
        stack.push(start);
        boolean[] visited = new boolean[this.vertices.size()];
        Arrays.fill(visited, true);

        while (!stack.isEmpty()) {
            int current = stack.pop();

            for (int neighbor : this.vertices.get(current).neighbors.keySet()) {
                if (!visited[neighbor]) {
                    stack.push(neighbor);
                    visited[neighbor] = true;
                }
            }
        }

        return result;
    }

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
    public ArrayList<Integer> shortestPath(int start, int end) {
        assert start >= 0 : "Violation of: start is a non-negative integer.";
        assert end >= 0 : "Violation of: end is a non-negative integer.";
        assert this.vertices.contains(
                new Vertex(start)) : "Violation of: start is in the arraylist.";
        assert this.vertices.contains(
                new Vertex(end)) : "Violation of: end is in the arraylist.";
        assert start != end : "Violation of: start is not equal to end.";

        int[] distance = new int[this.vertices.size()];
        int[] previous = new int[this.vertices.size()];
        boolean[] visited = new boolean[this.vertices.size()];
        Arrays.fill(distance, Integer.MAX_VALUE);
        Arrays.fill(previous, -1);

        distance[start] = 0;

        for (int i = 1; i <= this.vertices.size(); i++) {
            int current = -1;
            int min = Integer.MAX_VALUE;

            for (int j = 0; j < this.vertices.size(); j++) {
                if (!visited[j] && distance[j] < min) {
                    current = j;
                    min = distance[j];
                }
            }

            if (current == -1) {
                break;
            }

            visited[current] = true;

            for (int neighbor : this.vertices.get(current).neighbors.keySet()) {
                int alt = distance[current]
                        + this.vertices.get(current).neighbors.get(neighbor);

                if (alt < distance[neighbor]) {
                    distance[neighbor] = alt;
                    previous[neighbor] = current;
                }
            }
        }

        ArrayList<Integer> result = new ArrayList<>();
        for (int at = end; at != -1; at = previous[at]) {
            result.add(at);
        }

        return result;
    }

    /**
     * Returns if the graph is connected.
     *
     * @return true if the graph is connected, false otherwise.
     */
    public boolean isConnected() {
        return this.bfs(0).size() == this.vertices.size();
    }

    /**
     * Returns if the graph is directed.
     *
     * @return true if the graph is directed, false otherwise.
     */
    public boolean isDirected() {
        for (Vertex v : this.vertices) {
            for (int neighbor : v.neighbors.keySet()) {
                if (!this.vertices.get(neighbor).neighbors.containsKey(v.tag)) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Returns the number of connected components.
     *
     * @requires the graph is undirected.
     *
     * @return the number of connected components.
     */
    public int connectedComponents() {
        assert !this.isDirected() : "Violation of: the graph is undirected.";

        int count = 0;
        boolean[] visited = new boolean[this.vertices.size()];
        Arrays.fill(visited, false);

        for (int i = 0; i < this.vertices.size(); i++) {
            if (!visited[i]) {
                this.dfs(i);
                count++;
            }
        }

        return count;
    }

    /**
     * Returns the list of minimum spanning tree using Prim's algorithm.
     *
     * @return the list of minimum spanning tree.
     */
    public ArrayList<Integer> minimumSpanningTree() {
        int[] distance = new int[this.vertices.size()];
        int[] previous = new int[this.vertices.size()];
        boolean[] visited = new boolean[this.vertices.size()];
        Arrays.fill(distance, Integer.MAX_VALUE);
        Arrays.fill(previous, -1);

        distance[0] = 0;

        for (int i = 1; i <= this.vertices.size(); i++) {
            int current = -1;
            int min = Integer.MAX_VALUE;

            for (int j = 0; j < this.vertices.size(); j++) {
                if (!visited[j] && distance[j] < min) {
                    current = j;
                    min = distance[j];
                }
            }

            if (current == -1) {
                break;
            }

            visited[current] = true;

            for (int neighbor : this.vertices.get(current).neighbors.keySet()) {
                int alt = this.vertices.get(current).neighbors.get(neighbor);

                if (alt < distance[neighbor]) {
                    distance[neighbor] = alt;
                    previous[neighbor] = current;
                }
            }
        }

        ArrayList<Integer> result = new ArrayList<>();
        for (int at = 0; at < this.vertices.size(); at++) {
            if (previous[at] != -1) {
                result.add(at);
            }
        }

        return result;
    }

    /**
     * Representation of a single vertex in the graph.
     */
    public static final class Vertex {

        /**
         * The tag of the vertex.
         */
        private int tag;

        /**
         * The set of tags of the vertex's neighbors.
         */
        private Map<Integer, Integer> neighbors;

        /**
         * Get the tag of the vertex.
         *
         * @return the tag of the vertex.
         */
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