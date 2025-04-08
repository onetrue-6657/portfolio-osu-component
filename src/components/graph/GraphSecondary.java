package components.graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;

/**
 * Layered on top of {@code GraphKernel} with secondary methods.
 *
 * @author Zheng Ni
 */
public abstract class GraphSecondary implements Graph {

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
        // assert this.vertices().contains(new Vertex(
        // vertex)) : "Violation of: vertex is in the arraylist.";

        return this.vertices().get(vertex).getNeighbors();
    }

    @Override
    public int getWeight(int from, int to) {
        assert from >= 0 : "Violation of: from is a non-negative integer.";
        assert to >= 0 : "Violation of: to is a non-negative integer.";
        // assert this.vertices().contains(
        // new Vertex(from)) : "Violation of: from is in the arraylist.";
        // assert this.vertices().contains(
        // new Vertex(to)) : "Violation of: to is in the arraylist.";
        assert from != to : "Violation of: from is not equal to to.";

        return this.getNeighbors(from).get(to);
    }

    @Override
    public ArrayList<Integer> bfs(int start) {
        assert start >= 0 : "Violation of: start is a non-negative integer.";
        // assert this.vertices().contains(
        // new Vertex(start)) : "Violation of: start is in the arraylist.";

        ArrayList<Integer> result = new ArrayList<>();
        Queue<Integer> queue = new LinkedList<>();
        boolean[] visited = new boolean[this.vertices().size()];
        Arrays.fill(visited, false);

        queue.add(start);
        visited[start] = true;

        while (!queue.isEmpty()) {
            int current = queue.poll();
            result.add(current);

            for (int neighbor : this.vertices().get(current).getNeighbors()
                    .keySet()) {
                if (!visited[neighbor]) {
                    queue.add(neighbor);
                    visited[neighbor] = true;
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
        stack.push(start);
        boolean[] visited = new boolean[this.vertices().size()];
        Arrays.fill(visited, false);

        while (!stack.isEmpty()) {
            int current = stack.pop();

            if (!visited[current]) {
                result.add(current);
                visited[current] = true;
            }

            for (int neighbor : this.vertices().get(current).getNeighbors()
                    .keySet()) {
                if (!visited[neighbor]) {
                    stack.push(neighbor);
                }
            }
        }

        return result;
    }

    @Override
    public ArrayList<Integer> shortestPath(int start, int end) {
        assert start >= 0 : "Violation of: start is a non-negative integer.";
        assert end >= 0 : "Violation of: end is a non-negative integer.";
        // assert this.vertices().contains(
        // new Vertex(start)) : "Violation of: start is in the arraylist.";
        // assert this.vertices().contains(
        // new Vertex(end)) : "Violation of: end is in the arraylist.";
        assert start != end : "Violation of: start is not equal to end.";

        int[] distance = new int[this.vertices().size()];
        int[] previous = new int[this.vertices().size()];
        boolean[] visited = new boolean[this.vertices().size()];
        Arrays.fill(distance, Integer.MAX_VALUE);
        Arrays.fill(previous, -1);

        distance[start] = 0;

        for (int i = 1; i <= this.vertices().size(); i++) {
            int current = -1;
            int min = Integer.MAX_VALUE;

            for (int j = 0; j < this.vertices().size(); j++) {
                if (!visited[j] && distance[j] < min) {
                    current = j;
                    min = distance[j];
                }
            }

            if (current == -1) {
                break;
            }

            visited[current] = true;

            for (int neighbor : this.vertices().get(current).getNeighbors()
                    .keySet()) {
                int alt = distance[current] + this.vertices().get(current)
                        .getNeighbors().get(neighbor);

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

    @Override
    public boolean isConnected() {
        return this.bfs(0).size() == this.vertices().size();
    }

    @Override
    public boolean isDirected() {
        for (Vertex v : this.vertices()) {
            for (int neighbor : v.getNeighbors().keySet()) {
                if (!this.vertices().get(neighbor).getNeighbors()
                        .containsKey(v.getTag())) {
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public int connectedComponents() {
        assert !this.isDirected() : "Violation of: the graph is undirected.";

        int count = 0;
        boolean[] visited = new boolean[this.vertices().size()];
        Arrays.fill(visited, false);

        for (int i = 0; i < this.vertices().size(); i++) {
            if (!visited[i]) {
                this.dfs(i);
                count++;
            }
        }

        return count;
    }

    @Override
    public ArrayList<Integer> minimumSpanningTree() {
        int[] distance = new int[this.vertices().size()];
        int[] previous = new int[this.vertices().size()];
        boolean[] visited = new boolean[this.vertices().size()];
        Arrays.fill(distance, Integer.MAX_VALUE);
        Arrays.fill(previous, -1);

        distance[0] = 0;

        for (int i = 1; i <= this.vertices().size(); i++) {
            int current = -1;
            int min = Integer.MAX_VALUE;

            for (int j = 0; j < this.vertices().size(); j++) {
                if (!visited[j] && distance[j] < min) {
                    current = j;
                    min = distance[j];
                }
            }

            if (current == -1) {
                break;
            }

            visited[current] = true;

            for (int neighbor : this.vertices().get(current).getNeighbors()
                    .keySet()) {
                int alt = this.vertices().get(current).getNeighbors()
                        .get(neighbor);

                if (alt < distance[neighbor]) {
                    distance[neighbor] = alt;
                    previous[neighbor] = current;
                }
            }
        }

        ArrayList<Integer> result = new ArrayList<>();
        for (int at = 0; at < this.vertices().size(); at++) {
            if (previous[at] != -1) {
                result.add(at);
            }
        }

        return result;
    }
}
