package demos;

import java.util.ArrayList;
import java.util.Scanner;

import components.graph.Graph;
import components.graph.Graph1L;

/**
 * This class provides a demo for creating a graph from user input and solving
 * graph theory problems. It allows the user to create a graph, modify it, and
 * solve various graph theory problems such as finding neighbors, finding the
 * weight of an edge, traversing the graph using breadth-first search and
 * depth-first search, finding the shortest path using Dijkstra's algorithm,
 * checking if the graph is connected, finding the number of connected
 * components, and finding a minimum spanning tree using Prim's algorithm.
 */
public class AutoGraph {

    /**
     * Constant for the option 1.
     */
    private static final int OPTION_1 = 1;

    /**
     * Constant for the option 2.
     */
    private static final int OPTION_2 = 2;

    /**
     * Constant for the option 3.
     */
    private static final int OPTION_3 = 3;

    /**
     * Constant for the option 4.
     */
    private static final int OPTION_4 = 4;

    /**
     * Constant for the option 5.
     */
    private static final int OPTION_5 = 5;

    /**
     * Constant for the option 6.
     */
    private static final int OPTION_6 = 6;

    /**
     * Constant for the option 7.
     */
    private static final int OPTION_7 = 7;

    /**
     * Constant for the option 8.
     */
    private static final int OPTION_8 = 8;

    /**
     * Constant for the option 9.
     */
    private static final int OPTION_9 = 9;

    /**
     * The graph object to be created. It is a representation of the graph.
     */
    private Graph graph = new Graph1L();

    /**
     * This method provides options to the user to create a graph or solve a
     * problem. It will ask the user for directed or not, the number of
     * vertices, and the edges. It will also provide options to modify the graph
     * and solve graph theory problems.
     */
    public void options() {
        System.out.println("Options:");
        System.out.println("1. Create a graph from user input.");
        System.out.println("2. Solve a graph theory problem.");
        System.out.println("3. Show current graph.");
        System.out.println("4. Modify current graph.");
        System.out.println("5. Exit.");

        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();

        if (choice == OPTION_1) {
            this.createGraph();
        } else if (choice == OPTION_2) {
            this.solveProblem();
        } else if (choice == OPTION_3) {
            System.out.println("Current graph: \n" + this.graph.toString());
            this.options();
        } else if (choice == OPTION_4) {
            this.modifyGraph();
        } else if (choice == OPTION_5) {
            System.out.println("Exiting the program.");
            System.exit(0);
        } else {
            System.out.println("Invalid choice. Please try again.");
            AutoGraph autoGraphInstance = new AutoGraph();
            autoGraphInstance.options();
        }

        // scanner.close();
    }

    /**
     * This method creates a graph from user input. It asks the user for
     * directed or not, the number of vertices, and the edges.
     */
    public void createGraph() {
        Scanner scanner = new Scanner(System.in);
        System.out
                .println("You are now creating a graph. Press Ctrl+C to exit.");
        System.out.println("If you have created a graph before, please type 1"
                + " to confirm you want to cover the previous one and"
                + " create a new graph.");

        int status = scanner.nextInt();
        if (status != 1) {
            System.out.println("Exiting the creating process.");
            this.options();
        }

        System.out.println(
                "Enter 1 for directed graph or 0 for undirected graph:");

        int directed = scanner.nextInt();
        while (directed != 0 && directed != 1) {
            System.out.println("Invalid input. Please enter 1 or 0:");
            directed = scanner.nextInt();
        }
        if (directed == 1) {
            this.graph = new Graph1L(true);
        } else {
            this.graph = new Graph1L(false);
        }

        System.out.println("Enter the number of vertices:");
        int numVertices = scanner.nextInt();
        while (numVertices <= 0) {
            System.out
                    .println("Invalid input. Please enter a positive number:");
            numVertices = scanner.nextInt();
        }
        for (int i = 1; i <= numVertices; i++) {
            this.graph.addVertex(i);
        }
        System.out.println("Vertices with numbers from 1 to " + numVertices
                + " have been created.");

        System.out.println("Enter the edges (from, to, weight) or -1 to stop:");
        int from, to, weight;
        while (true) {
            from = scanner.nextInt();
            if (from == -1) {
                break;
            }
            to = scanner.nextInt();
            weight = scanner.nextInt();
            while (weight <= 0) {
                System.out.println(
                        "Invalid input. Please enter a positive number:");
                weight = scanner.nextInt();
            }
            this.graph.addEdge(from, to, weight);
        }

        System.out.println("Graph created successfully.");
        // scanner.close();
        this.options();
    }

    /**
     * This method modifies the graph. It provides options to the user to add
     * vertices, add edges, remove a vertex, remove an edge, check if a vertex
     * exists, check if an edge exists, and exit to options.
     */
    public void modifyGraph() {
        System.out.println(
                "You are now modifying the graph. Press Ctrl+C to exit.");

        Scanner scanner = new Scanner(System.in);

        System.out.println("Modify Options: (Type -1 to exit to this page)");
        System.out.println("1. Add vertices.");
        System.out.println("2. Add edges.");
        System.out.println("3. Remove a vertex.");
        System.out.println("4. Remove an edge.");
        System.out.println("5. Check if a vertex exists.");
        System.out.println("6. Check if an edge exists.");
        System.out.println("7. Exit to options.");

        int choice = scanner.nextInt();

        while (choice != OPTION_7) {
            if (choice == OPTION_1) {
                System.out.println("Enter how many vertices you want to add:");
                int vertex = scanner.nextInt();
                if (vertex != -1) {
                    int currentVertex = this.graph.vertices().size();
                    int maxVertex = 0;
                    for (Graph.Vertex v : this.graph.vertices()) {
                        if (v.getTag() > maxVertex) {
                            maxVertex = v.getTag();
                        }
                    }
                    for (int i = 1; i <= vertex; i++) {
                        int newVertex = maxVertex + i;
                        this.graph.addVertex(newVertex);
                        System.out.println("Vertex " + newVertex + " added.");
                    }
                } else {
                    System.out.println("Exiting the adding process.");
                    this.modifyGraph();
                }
                System.out.println("Adding process completed.");
            } else if (choice == OPTION_2) {
                System.out.println("How many edges do you want to add?");
                int edge = scanner.nextInt();
                if (edge == -1) {
                    System.out.println("Exiting the adding process.");
                    this.modifyGraph();
                }
                for (int i = 0; i < edge; i++) {
                    System.out.println("Enter the edge (from, to, weight):");
                    int from = scanner.nextInt();
                    int to = scanner.nextInt();
                    int weight = scanner.nextInt();
                    if (from == -1 || to == -1 || weight == -1) {
                        System.out.println("Exiting the adding process.");
                        this.modifyGraph();
                    }
                    this.graph.addEdge(from, to, weight);
                    System.out
                            .println("Edge (" + from + ", " + to + ") added.");
                }
                System.out.println("Adding process completed.");
            } else if (choice == OPTION_3) {
                System.out.println("How many vertices do you want to remove?");
                int vertices = scanner.nextInt();
                if (vertices == -1) {
                    System.out.println("Exiting the removing process.");
                    this.modifyGraph();
                }
                for (int i = 0; i < vertices; i++) {
                    System.out.println("Enter the vertex number to remove:");
                    int vertex = scanner.nextInt();
                    if (vertex == -1) {
                        System.out.println("Exiting the removing process.");
                        this.modifyGraph();
                    }
                    this.graph.removeVertex(vertex);
                    System.out.println("Vertex " + vertex + " removed.");
                }
                System.out.println("Removing process completed.");
            } else if (choice == OPTION_4) {
                System.out.println("How many edges do you want to remove?");
                int edges = scanner.nextInt();
                if (edges == -1) {
                    System.out.println("Exiting the removing process.");
                    this.modifyGraph();
                }
                for (int i = 0; i < edges; i++) {
                    System.out.println(
                            "Enter the edge (from, to) to be removed:");
                    int from = scanner.nextInt();
                    int to = scanner.nextInt();
                    if (from == -1 || to == -1) {
                        System.out.println("Exiting the removing process.");
                        this.modifyGraph();
                    }
                    this.graph.removeEdge(from, to);
                    System.out.println(
                            "Edge (" + from + ", " + to + ") removed.");
                }
                System.out.println("Removing process completed.");
            } else if (choice == OPTION_5) {
                System.out.println("Enter the vertex number to check:");
                int vertex = scanner.nextInt();
                if (vertex == -1) {
                    System.out.println("Exiting the checking process.");
                    this.modifyGraph();
                }
                boolean exists = this.graph.containsVertex(vertex);
                if (exists) {
                    System.out.println("Vertex " + vertex + " exists.");
                } else {
                    System.out.println("Vertex " + vertex + " does not exist.");
                }
                System.out.println("Check completed.");
            } else if (choice == OPTION_6) {
                System.out.println("Enter the edge (from, to) to be checked:");
                int from = scanner.nextInt();
                int to = scanner.nextInt();
                if (from == -1 || to == -1) {
                    System.out.println("Exiting the checking process.");
                    this.modifyGraph();
                }
                boolean exists = this.graph.containsEdge(from, to);
                if (exists) {
                    System.out
                            .println("Edge (" + from + ", " + to + ") exists.");
                } else {
                    System.out.println(
                            "Edge (" + from + ", " + to + ") does not exist.");
                }
                System.out.println("Check completed.");
            } else {
                System.out.println("Invalid choice. Please try again.");
            }
            choice = scanner.nextInt();
        }

        // scanner.close();
        System.out.println("Graph modify completed.");
        this.options();
    }

    /**
     * This method solves a graph theory problem. It provides options to the
     * user to find all neighbors of a vertex, find the weight of an edge, *
     * traverse all vertices using breadth-first search, traverse all vertices
     * using depth-first search, find the shortest path using Dijkstra's
     * algorithm, check if the graph is connected, find the number of connected
     * components, and find a minimum spanning tree using Prim's algorithm.
     */
    public void solveProblem() {
        System.out.println("You are now solving a graph theory problem.");
        System.out.println("Press Ctrl+C to exit.");
        System.out.println("Options:");
        System.out.println("1. Find all neighbors of a vertex.");
        System.out.println("2. Find the weight of an edge.");
        System.out.println("3. Traverse all vertices using breadth-first"
                + " search and show the path.");
        System.out.println("4. Traverse all vertices using depth-first"
                + " search and show the path.");
        System.out
                .println("5. Find the shortest path using Dijkstra's algorithm"
                        + " and show the path.");
        System.out.println("6. Check if the graph is connected.");
        System.out.println("7. Find the number of connected components.");
        System.out.println(
                "8. Find a minimum spanning tree using Prim's algorithm "
                        + "and show the path.");
        System.out.println("9. Exit to options.");

        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();
        while (choice != OPTION_9) {
            if (choice == OPTION_1) {
                System.out.println(
                        "Enter the vertex number to find its neighbors:");
                int vertex = scanner.nextInt();
                if (vertex == -1) {
                    System.out.println("Exiting the searching process.");
                    this.solveProblem();
                }
                if (this.graph.containsVertex(vertex)) {
                    ArrayList<Integer> neighbors = new ArrayList<>(
                            this.graph.getNeighbors(vertex).keySet());
                    System.out.println("Neighbors of vertex " + vertex + ": "
                            + neighbors.toString());
                } else {
                    System.out.println("Vertex " + vertex + " does not exist.");
                }
                System.out.println("Searching process completed.");
            } else if (choice == OPTION_2) {
                System.out.println(
                        "Enter the edge (from, to) to find its weight:");
                int from = scanner.nextInt();
                int to = scanner.nextInt();
                if (from == -1 || to == -1) {
                    System.out.println("Exiting the searching process.");
                    this.solveProblem();
                }
                if (this.graph.containsEdge(from, to)
                        && this.graph.containsVertex(from)
                        && this.graph.containsVertex(to)) {
                    System.out.println("Weight of edge (" + from + ", " + to
                            + ") is: " + this.graph.getWeight(from, to));
                } else {
                    System.out.println(
                            "Edge (" + from + ", " + to + ") does not exist.");
                }
                System.out.println("Searching process completed.");
            } else if (choice == OPTION_3) {
                System.out.println(
                        "Enter the vertex number to start the search:");
                int start = scanner.nextInt();
                if (start == -1) {
                    System.out.println("Exiting the searching process.");
                    this.solveProblem();
                }
                if (!this.graph.isConnected()) {
                    System.out.println("Graph is not connected. Please "
                            + "check the graph and try again.");
                    this.solveProblem();
                }
                if (this.graph.containsVertex(start)) {
                    System.out.println("Breadth-first search path: "
                            + this.graph.bfs(start).toString());
                } else {
                    System.out.println("Vertex " + start + " does not exist.");
                }
                System.out.println("Searching process completed.");
            } else if (choice == OPTION_4) {
                System.out.println(
                        "Enter the vertex number to start the search:");
                int start = scanner.nextInt();
                if (start == -1) {
                    System.out.println("Exiting the searching process.");
                    this.solveProblem();
                }
                if (!this.graph.isConnected()) {
                    System.out.println("Graph is not connected. Please "
                            + "check the graph and try again.");
                    this.solveProblem();
                }
                if (this.graph.containsVertex(start)) {
                    System.out.println("Depth-first search path: "
                            + this.graph.dfs(start).toString());
                } else {
                    System.out.println("Vertex " + start + " does not exist.");
                }
                System.out.println("Searching process completed.");
            } else if (choice == OPTION_5) {
                System.out.println(
                        "Enter the start and end vertex numbers to find the "
                                + "shortest path:");
                int start = scanner.nextInt();
                int end = scanner.nextInt();
                if (!this.graph.isConnected()) {
                    System.out.println("Graph is not connected. Please "
                            + "check the graph and try again.");
                    this.solveProblem();
                }
                if (start == -1 || end == -1) {
                    System.out.println("Exiting the searching process.");
                    this.solveProblem();
                }
                if (this.graph.containsVertex(start)
                        && this.graph.containsVertex(end)) {
                    System.out.println("Shortest path: "
                            + this.graph.shortestPath(start, end).toString());
                } else {
                    System.out.println("Vertex " + start + " or " + end
                            + " does not exist.");
                }
                System.out.println("Searching process completed.");
            } else if (choice == OPTION_6) {
                System.out.println(
                        "Graph is connected: " + this.graph.isConnected());
            } else if (choice == OPTION_7) {
                System.out.println("Number of connected components: "
                        + this.graph.connectedComponents());
            } else if (choice == OPTION_8) {
                System.out.println("Minimum spanning tree: "
                        + this.graph.minimumSpanningTree().toString());
            } else {
                System.out.println("Invalid choice. Please try again.");
            }
            choice = scanner.nextInt();
        }

        // scanner.close();
        System.out.println("Problem solve completed.");
        this.options();
    }

    /**
     * Ask the user for directed or not. Ask the user for the number of
     * vertices. Input the vertices (numbers). Repeatedly ask the user for edges
     * until the user enters -1. Input the edges (numbers). Create the graph.
     *
     * @param args
     *            the command line arguments
     * @throws Exception
     *             if an error occurs while creating the graph
     */
    public static void main(String[] args) {
        System.out.println("--------------AutoGraph demo--------------");
        System.out.println("This demo creates a graph from user input.");
        System.out.println("It will also provide options asking if the user "
                + "wants to operate and solve graph theory problems.\n");

        AutoGraph autoGraphInstance = new AutoGraph();
        autoGraphInstance.options();
    }
}
