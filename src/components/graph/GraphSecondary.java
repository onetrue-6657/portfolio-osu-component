package components.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

/**
 * Layered on top of {@code GraphKernel} with secondary methods.
 *
 * @author Zheng Ni
 */
public abstract class GraphSecondary implements Graph {

    @Override
    public int hashCode() {
        return this.vertices().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }
        GraphSecondary graph = (GraphSecondary) obj;
        return this.vertices().equals(graph.vertices());
    }

    @Override
    public String toString() {
        String rep = "{\n";
        for (int i = 0; i < this.vertices().size(); i++) {
            rep += this.vertices().get(i).getTag() + ": "
                    + this.vertices().get(i).getNeighbors() + "\n";
        }
        rep += "}";
        return rep;
    }

    /**
     * Returns the number of vertices in the graph.
     *
     * @return the number of vertices in the graph.
     */
    @Override
    public abstract ArrayList<Vertex> vertices();

    @Override
    public Map<Integer, Integer> getNeighbors(int vertex) {
        assert vertex >= 0 : "Violation of: vertex is a non-negative integer.";

        Vertex targetVertex = null;
        for (Vertex v : this.vertices()) {
            if (v.getTag() == vertex) {
                targetVertex = v;
                break;
            }
        }

        assert targetVertex != null : "Violation of: vertex exists in the graph.";
        return targetVertex.getNeighbors();
    }

    @Override
    public int getWeight(int from, int to) {
        assert from >= 0 : "Violation of: from is a non-negative integer.";
        assert to >= 0 : "Violation of: to is a non-negative integer.";
        assert from != to : "Violation of: from is not equal to to.";

        Map<Integer, Integer> neighbors = this.getNeighbors(from);
        assert neighbors.containsKey(
                to) : "Violation of: edge exists from 'from' to 'to'.";

        return neighbors.get(to);
    }

    @Override
    public ArrayList<Integer> bfs(int start) {
        assert start >= 0 : "Violation of: start is a non-negative integer.";
        assert this.containsVertex(
                start) : "Violation of: start vertex exists in graph.";

        ArrayList<Integer> result = new ArrayList<>();
        Queue<Integer> queue = new LinkedList<>();
        Set<Integer> visited = new HashSet<>();
        queue.add(start);
        visited.add(start);

        while (!queue.isEmpty()) {
            int current = queue.poll();
            result.add(current);

            Map<Integer, Integer> neighbors = this.getNeighbors(current);
            for (int neighbor : neighbors.keySet()) {
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    queue.add(neighbor);
                }
            }
        }

        return result;
    }

    @Override
    public ArrayList<Integer> dfs(int start) {
        assert start >= 0 : "Violation of: start is a non-negative integer.";
        // assert this.vertices().contains(
        // new Vertex(start)) : "Violation of: start is in the arraylist.";

        ArrayList<Integer> result = new ArrayList<>();
        Stack<Integer> stack = new Stack<>();
        Set<Integer> visited = new HashSet<>();

        stack.push(start);

        while (!stack.isEmpty()) {
            int current = stack.pop();

            if (!visited.contains(current)) {
                result.add(current);
                visited.add(current);

                Map<Integer, Integer> neighbors = this.getNeighbors(current);

                ArrayList<Integer> neighborList = new ArrayList<>(
                        neighbors.keySet());
                for (int i = neighborList.size() - 1; i >= 0; i--) {
                    int neighbor = neighborList.get(i);
                    if (!visited.contains(neighbor)) {
                        stack.push(neighbor);
                    }
                }
            }
        }

        return result;
    }

    @Override
    public ArrayList<Integer> shortestPath(int start, int end) {
        assert start >= 0 : "Violation of: start is a non-negative integer.";
        assert end >= 0 : "Violation of: end is a non-negative integer.";
        assert this.containsVertex(
                start) : "Violation of: start vertex exists in graph.";
        assert this.containsVertex(
                end) : "Violation of: end vertex exists in graph.";

        if (start == end) {
            ArrayList<Integer> result = new ArrayList<>();
            result.add(start);
            return result;
        }

        Map<Integer, Integer> distance = new HashMap<>();
        Map<Integer, Integer> previous = new HashMap<>();
        Set<Integer> unvisited = new HashSet<>();

        for (Vertex v : this.vertices()) {
            int vertexTag = v.getTag();
            distance.put(vertexTag, Integer.MAX_VALUE);
            previous.put(vertexTag, -1);
            unvisited.add(vertexTag);
        }

        distance.put(start, 0);

        while (!unvisited.isEmpty()) {
            int current = -1;
            int minDistance = Integer.MAX_VALUE;

            for (int vertexTag : unvisited) {
                if (distance.get(vertexTag) < minDistance) {
                    minDistance = distance.get(vertexTag);
                    current = vertexTag;
                }
            }

            if (current == -1 || distance.get(current) == Integer.MAX_VALUE) {
                break;
            }

            if (current == end) {
                break;
            }

            unvisited.remove(current);

            Map<Integer, Integer> neighbors = this.getNeighbors(current);
            for (Map.Entry<Integer, Integer> neighborEntry : neighbors
                    .entrySet()) {
                int neighborTag = neighborEntry.getKey();
                int weight = neighborEntry.getValue();

                if (unvisited.contains(neighborTag)) {
                    int alt = distance.get(current) + weight;
                    if (alt < distance.get(neighborTag)) {
                        distance.put(neighborTag, alt);
                        previous.put(neighborTag, current);
                    }
                }
            }
        }

        ArrayList<Integer> path = new ArrayList<>();

        if (previous.get(end) == -1 && start != end) {
            return path;
        }

        for (int at = end; at != -1; at = previous.get(at)) {
            path.add(0, at);
        }

        return path;
    }

    @Override
    public boolean isConnected() {
        if (this.vertices().isEmpty()) {
            return true;
        }

        int startVertex = this.vertices().get(0).getTag();

        ArrayList<Integer> reachableVertices = this.bfs(startVertex);

        return reachableVertices.size() == this.vertices().size();
    }

    @Override
    public boolean isDirected() {
        for (Vertex v : this.vertices()) {
            int vTag = v.getTag();
            for (int neighborTag : v.getNeighbors().keySet()) {
                Vertex neighborVertex = null;
                for (Vertex potential : this.vertices()) {
                    if (potential.getTag() == neighborTag) {
                        neighborVertex = potential;
                        break;
                    }
                }

                if (neighborVertex != null) {
                    if (!neighborVertex.getNeighbors().containsKey(vTag)) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    @Override
    public int connectedComponents() {
        assert !this.isDirected() : "Violation of: the graph is undirected.";

        int count = 0;
        Set<Integer> visited = new HashSet<>();

        for (Vertex v : this.vertices()) {
            int vertexTag = v.getTag();

            if (!visited.contains(vertexTag)) {
                ArrayList<Integer> component = this.dfs(vertexTag);
                visited.addAll(component);
                count++;
            }
        }

        return count;
    }

    @Override
    public ArrayList<Integer> minimumSpanningTree() {
        assert !this.isDirected() : "Violation of: graph is undirected.";

        if (this.vertices().isEmpty()) {
            return new ArrayList<>();
        }

        ArrayList<Vertex> allVertices = this.vertices();
        int vertexCount = allVertices.size();

        int start = allVertices.get(0).getTag();

        Map<Integer, Boolean> visited = new HashMap<>();
        Map<Integer, Integer> key = new HashMap<>();
        Map<Integer, Integer> parent = new HashMap<>();

        for (Vertex v : allVertices) {
            int vTag = v.getTag();
            visited.put(vTag, false);
            key.put(vTag, Integer.MAX_VALUE);
            parent.put(vTag, -1);
        }

        key.put(start, 0);

        for (int i = 0; i < vertexCount; i++) {
            int u = -1;
            int minKey = Integer.MAX_VALUE;

            for (Vertex v : allVertices) {
                int vTag = v.getTag();
                if (!visited.get(vTag) && key.get(vTag) < minKey) {
                    u = vTag;
                    minKey = key.get(vTag);
                }
            }

            if (u == -1) {
                break;
            }

            visited.put(u, true);

            Map<Integer, Integer> neighbors = this.getNeighbors(u);
            for (Map.Entry<Integer, Integer> entry : neighbors.entrySet()) {
                int v = entry.getKey();
                int weight = entry.getValue();

                if (!visited.get(v) && weight < key.get(v)) {
                    parent.put(v, u);
                    key.put(v, weight);
                }
            }
        }

        ArrayList<Integer> result = new ArrayList<>();

        for (Vertex v : allVertices) {
            int vTag = v.getTag();
            if (parent.get(vTag) != -1 || vTag == start) {
                if (!result.contains(vTag)) {
                    result.add(vTag);
                }
            }
        }

        return result;
    }
}
