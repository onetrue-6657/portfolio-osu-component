# Graph Component

## Idea

This project is from Dr. Jeremy Grifski ([jrg94])'s course at the Ohio State University College of Engineering [CSE 2231 - Software II]. This is a portfolio project that is similar to the [OSU Component] used for the software sequence (which I think is really bad for learning Java) created by Professor Paolo Bucci.

In the process of Brainstorming, I referred to [SoftwareComponents] repository by Dr. Grifski to find inspirations. Finally among the three ideas, I chose Graph as the topic of the component, which involves a new data structure called `Graph` and involved methods to easily solve graph theory problems such as depth-first search, breadth-first search, and minimum spanning tree.

## Design

- **Description**:
  - This component implements a graph data structure. It supports operations on vertices and edges, as well as various graph algorithms such as depth-first search (DFS), breadth-first search (BFS), shortest path, and minimum spanning tree (MST). The component supports both directed and undirected graphs but does not allow negative edge weights.

- **Kernel Methods**:
  - `void addVertex(int vertex)`: Adds the vertex `vertex` to the graph.
  - `void addEdge(int from, int to, int weight)`: Adds an edge from vertex `from` to vertex `to` with a weight `weight`.
  - `void removeVertex(int vertex)`: Removes the vertex `vertex` from the graph, along with all edges connected to it.
  - `void removeEdge(int from, int to)`: Removes the edge from vertex `from` to vertex `to`.
  - `boolean containsVertex(int vertex)`: Checks if the vertex `vertex` exists in the graph.
  - `boolean containsEdge(int from, int to)`: Checks if there is an edge from vertex `from` to vertex `to`.
  - `boolean isDirected()`: Returns whether the graph is directed.
  - `Map<Integer, Integer> getNeighbors(int vertex)`: Returns a map of all neighbors of the vertex `vertex` and their corresponding edge weights.

- **Secondary Methods**:
  - `ArrayList<Integer> bfs(int start)`: Performs a breadth-first search starting from vertex `start` and returns the order of visited vertices.
  - `ArrayList<Integer> dfs(int start)`: Performs a depth-first search starting from vertex `start` and returns the order of visited vertices.
  - `ArrayList<Integer> shortestPath(int start, int end)`: Finds the shortest path from vertex `start` to vertex `end` using Dijkstra's algorithm.
  - `boolean isConnected()`: Checks if the graph is connected.
  - `int connectedComponents()`: Returns the number of connected components in the graph (only for undirected graphs).
  - `ArrayList<Integer> minimumSpanningTree()`: Computes the minimum spanning tree (MST) using Prim's algorithm and returns the vertices in the MST.
  - `int getWeight(int from, int to)`: Returns the weight of the edge from vertex `from` to vertex `to`. If no such edge exists, returns `-1`.
  - `Set<Graph.Vertex> vertices()`: Returns a set of all vertices in the graph.

- **Internal Classes**:
  - `Graph.Vertex`: Represents a vertex in the graph, storing its label and providing access to its neighbors.
  - `Graph1L.Vertex`: A concrete implementation of `Graph.Vertex`, storing the vertex label and its adjacency list.

- **Additional Considerations**:
  - **Mutability**: This component is mutable, as it allows adding and removing vertices and edges.
  - **Internal Classes**: The component relies on internal classes such as `Vertex` to store vertex data and manage adjacency lists.
  - **Enums or Constants**: The component uses constants to distinguish between directed and undirected graphs.
  - **Secondary Methods Implementation**: Secondary methods are implemented using kernel methods. For example, `bfs` and `dfs` use the `getNeighbors` method to traverse the graph efficiently.

- **Applications**:
  - The `Graph` component has been successfully used in various demo projects:
    - **SocialGraphDemo**: A social network analysis tool that allows users to create and analyze social graphs, recommend friends based on mutual connections, and modify the network dynamically.
    - **Graph Automaton**: A demo that simulates state transitions in a finite state machine using graph structures.
  - Other ideas:
    - **Path Finder**: A tool to calculate the shortest path between locations in a graph-based map.
    - **Course Dependency Manager**: A tool to manage and visualize course prerequisites, helping students plan their learning paths using topological sorting.
    - **Network Reliability Analyzer**: A tool to analyze the reliability of a network by identifying critical nodes and edges (e.g., routers or connections) whose removal would disconnect the network.
    - **Ecosystem Simulation**: A food web simulator where nodes represent species and edges represent predator-prey relationships, allowing users to analyze ecosystem stability.
    - **Task Scheduler**: A project management tool that uses directed acyclic graphs (DAGs) to model task dependencies and compute the critical path for project completion.
    - **Recommendation System**: A graph-based recommendation engine that suggests items (e.g., movies, products) based on user-item interactions and collaborative filtering.
    - **Game Level Designer**: A tool to design game levels where nodes represent rooms or areas and edges represent connections between them, ensuring all areas are reachable.
    - **Transportation Network Planner**: A tool to model and optimize transportation networks, such as roads, railways, or flight routes, using minimum spanning tree and shortest path algorithms.

## How to Use

This section explains how to integrate and use the `Graph` component in other Java projects.

### 1. Clone or Download the Repository

First, clone or download this repository to your local machine:

```bash
git clone https://github.com/onetrue-6657/portfolio-osu-component.git
```

### 2. Add Component to Your Project

#### Option 1: Copy the Component Source Files

1. Navigate to the `src/components/graph` directory in this repository.
2. Copy the `Graph.java` and `Graph1L.java` files into your project's src directory.
3. Ensure the package structure matches your project's structure.

#### Option 2: Add as a Library (JAR File)

1. Compile the `Graph` component into a JAR file:

    ```bash
    javac -d out src/components/graph/*.java
    jar cf graph-component.jar -C out .
    ```

2. Add the graph-component.jar file to your project's classpath:
    - If using an IDE like IntelliJ IDEA or Eclipse, add the JAR file to your project's library dependencies.
    - If using the command line, include the JAR file in the -cp option when compiling and running your project:

    ```bash
    javac -cp graph-component.jar YourProjectFile.java
    java -cp graph-component.jar;. YourProjectFile
    ```

3. Import the Component

    ```java
    import components.graph.Graph;
    import components.graph.Graph1L;
    ```

4. Requirements
    - **Java Version**: Java 8 or higher.
    - **Dependencies**: This component does not require any external libraries. You may need JUnit 4 if you want to run the test cases.

[jrg94]: https://github.com/jrg94
[CSE 2231 - Software II]: https://cse22x1.engineering.osu.edu/2231/web-sw2/index.html
[OSU Component]: https://cse22x1.engineering.osu.edu/common/doc/
[SoftwareComponents]: https://github.com/jrg94/SoftwareComponents?tab=readme-ov-file
