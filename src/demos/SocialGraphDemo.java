package demos;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import components.graph.Graph;
import components.graph.Graph1L;

/**
 * Demo of a social graph using the {@code Graph} component.
 *
 * This program allows users to create a social graph, add people and their
 * connections, and recommend friends based on the friends of their friends.
 *
 * @author Zheng Ni
 */
public final class SocialGraphDemo {

    /**
     * The folder where the data files are stored.
     */
    private static final String DATA_FOLDER = "src\\demos\\social-data";

    /**
     * The file name for storing people data.
     */
    private static final String PEOPLE_FILE = "people.txt";

    /**
     * The file name for storing connections data.
     */
    private static final String CONNECTIONS_FILE = "connections.txt";

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
     * Private constructor to prevent instantiation.
     */
    private SocialGraphDemo() {
        throw new UnsupportedOperationException("Utility class");
    }

    /**
     * Main method to run the social graph demo.
     *
     * @param args
     */
    public static void main(String[] args) {
        Graph g = new Graph1L(false);
        Scanner in = new Scanner(System.in);
        Map<String, Integer> people = new HashMap<>();

        createDataFolder();

        boolean createNew = false;
        boolean dataExists = checkDataExists();

        if (dataExists) {
            System.out.println("Existing social network data found.");
            System.out.println("Do you want to create a new network?"
                    + " This will overwrite existing data. (yes/no)");
            String choice = in.next();
            createNew = choice.equalsIgnoreCase("yes");
        } else {
            createNew = true;
        }

        if (createNew) {
            System.out.println("Creating a new social network...");
            System.out.println("Enter the number of people you want to add:");
            int numberOfPeople = in.nextInt();
            System.out.println("Enter the names of the people:");
            for (int i = 1; i <= numberOfPeople; i++) {
                String name = in.next();
                g.addVertex(i);
                people.put(name, i);
            }

            System.out.println("Enter the number of connections:");
            int numberOfConnections = in.nextInt();
            System.out.println("Enter the connections (person1 person2):");
            for (int i = 0; i < numberOfConnections; i++) {
                String person1 = in.next();
                String person2 = in.next();
                int person1Tag = people.get(person1);
                int person2Tag = people.get(person2);
                g.addEdge(person1Tag, person2Tag, 1);
            }

            saveData(g, people);
            System.out.println("Data saved to " + DATA_FOLDER + " folder.");
        } else {
            boolean dataLoaded = loadData(g, people);
            if (dataLoaded) {
                System.out.println("Loaded data from " + DATA_FOLDER + ".");
            } else {
                System.err.println(
                        "Failed to load existing data. Creating a new network instead.");
                System.exit(1);
            }
        }

        displayNetwork(g, people);

        System.out.println("Do you want to modify the network? (yes/no)");
        String modifyChoice = in.next();

        if (modifyChoice.equalsIgnoreCase("yes")) {
            modifyNetwork(g, people, in);
            saveData(g, people);
            displayNetwork(g, people);
        }

        System.out.println(
                "Enter the name of the person to recommend friends to him/her:");
        String personToRecommend = in.next();

        if (!people.containsKey(personToRecommend)) {
            System.out.println("Person not found in the graph.");
        } else {
            int personToRecommendTag = people.get(personToRecommend);

            List<Integer> recommendedFriendTags = recommendFriends(g,
                    personToRecommendTag);

            Map<Integer, String> tagToPeople = new HashMap<>();
            for (Map.Entry<String, Integer> entry : people.entrySet()) {
                tagToPeople.put(entry.getValue(), entry.getKey());
            }

            System.out.println(
                    personToRecommend + " has these recommended friends:");
            if (recommendedFriendTags.isEmpty()) {
                System.out.println("Non recommendation available.");
            } else {
                for (Integer tag : recommendedFriendTags) {
                    System.out.println("- " + tagToPeople.get(tag));
                }
            }
        }

        in.close();

    }

    /**
     * Creates the data folder if it does not exist.
     */
    private static void createDataFolder() {
        File folder = new File(DATA_FOLDER);
        if (!folder.exists()) {
            folder.mkdir();
        }
    }

    /**
     * Loads data from files into the graph and people map.
     *
     * @param g
     *            the graph to load data into
     * @param people
     *            the map to load people data into
     * @return true if data is loaded successfully, false otherwise
     */
    private static boolean loadData(Graph g, Map<String, Integer> people) {
        File peopleFile = new File(DATA_FOLDER, PEOPLE_FILE);
        File connectionsFile = new File(DATA_FOLDER, CONNECTIONS_FILE);

        if (!peopleFile.exists() || !connectionsFile.exists()) {
            return false;
        }

        try {
            try (BufferedReader reader = new BufferedReader(
                    new FileReader(peopleFile))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(",");
                    if (parts.length == 2) {
                        String name = parts[0];
                        int id = Integer.parseInt(parts[1]);
                        people.put(name, id);
                        g.addVertex(id);
                    }
                }
            }

            try (BufferedReader reader = new BufferedReader(
                    new FileReader(connectionsFile))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(",");
                    if (parts.length == 2) {
                        int person1 = Integer.parseInt(parts[0]);
                        int person2 = Integer.parseInt(parts[1]);
                        g.addEdge(person1, person2, 1);
                    }
                }
            }

            return true;
        } catch (IOException e) {
            System.err.println(
                    "Error occurred when loading the data: " + e.getMessage());
            return false;
        }
    }

    /**
     * Saves the graph and people data to files.
     *
     * @param g
     *            the graph to save
     * @param people
     *            the map of people to save
     */
    private static void saveData(Graph g, Map<String, Integer> people) {
        try {
            try (BufferedWriter writer = new BufferedWriter(
                    new FileWriter(new File(DATA_FOLDER, PEOPLE_FILE)))) {
                for (Map.Entry<String, Integer> entry : people.entrySet()) {
                    writer.write(entry.getKey() + "," + entry.getValue());
                    writer.newLine();
                }
            }

            try (BufferedWriter writer = new BufferedWriter(
                    new FileWriter(new File(DATA_FOLDER, CONNECTIONS_FILE)))) {
                for (Graph.Vertex v : g.vertices()) {
                    int person1 = v.getTag();
                    Map<Integer, Integer> neighbors = v.getNeighbors();

                    for (int person2 : neighbors.keySet()) {
                        if (!g.isDirected() && person1 > person2) {
                            continue;
                        }
                        writer.write(person1 + "," + person2);
                        writer.newLine();
                    }
                }
            }
        } catch (IOException e) {
            System.err.println(
                    "Error occurred when saving the data: " + e.getMessage());
        }
    }

    /**
     * Displays the current social network.
     *
     * @param g
     *            the graph representing the social network
     * @param people
     *            the map of people in the network
     */
    private static void displayNetwork(Graph g, Map<String, Integer> people) {
        Map<Integer, String> tagToPeople = new HashMap<>();
        for (Map.Entry<String, Integer> entry : people.entrySet()) {
            tagToPeople.put(entry.getValue(), entry.getKey());
        }

        System.out.println("\n=== Current Social Network ===");
        System.out.println("People in the network:");
        for (String name : people.keySet()) {
            System.out.println(" - " + name);
        }

        System.out.println("\nConnections:");
        for (Graph.Vertex v : g.vertices()) {
            int personTag = v.getTag();
            String personName = tagToPeople.get(personTag);
            Map<Integer, Integer> neighbors = v.getNeighbors();

            if (!neighbors.isEmpty()) {
                System.out.print(personName + " has these friends: ");
                for (int neighborTag : neighbors.keySet()) {
                    System.out.print(tagToPeople.get(neighborTag) + " ");
                }
                System.out.println();
            }
        }
        System.out.println();
    }

    /**
     * Recommends friends for a person based on the friends of their friends.
     *
     * @param g
     *            the graph representing the social network
     * @param personTag
     *            the tag of the person for whom to recommend friends
     * @return a list of recommended friend tags
     *
     */
    public static List<Integer> recommendFriends(Graph g, int personTag) {
        Map<Integer, Integer> friendsMap = g.getNeighbors(personTag);
        Set<Integer> friends = friendsMap.keySet();

        Map<Integer, Integer> candidateCounts = new HashMap<>();

        for (Integer friendTag : friends) {
            Map<Integer, Integer> friendOfFriendsMap = g
                    .getNeighbors(friendTag);

            for (Integer fofTag : friendOfFriendsMap.keySet()) {
                if (fofTag != personTag && !friends.contains(fofTag)) {
                    candidateCounts.put(fofTag,
                            candidateCounts.getOrDefault(fofTag, 0) + 1);
                }
            }
        }

        List<Integer> result = new ArrayList<>();

        for (Map.Entry<Integer, Integer> entry : candidateCounts.entrySet()) {
            if (entry.getValue() >= 2) {
                result.add(entry.getKey());
            }
        }

        return result;
    }

    /**
     * Checks if the data files exist.
     *
     * @return true if the data files exist, false otherwise
     */
    private static boolean checkDataExists() {
        File peopleFile = new File(PEOPLE_FILE);
        File connectionsFile = new File(CONNECTIONS_FILE);
        return peopleFile.exists() && connectionsFile.exists();
    }

    /**
     * Modifies the social network by adding or removing people and connections.
     *
     * @param g
     *            the graph representing the social network
     * @param people
     *            the map of people in the network
     * @param in
     *            the scanner for user input
     */
    private static void modifyNetwork(Graph g, Map<String, Integer> people,
            Scanner in) {
        while (true) {
            System.out.println("\n=== Modify Network ===");
            System.out.println("1. Add a person");
            System.out.println("2. Add a connection");
            System.out.println("3. Remove a connection");
            System.out.println("4. Exit modification mode");
            System.out.print("Enter your choice (1-4): ");

            int choice = in.nextInt();

            switch (choice) {
                case OPTION_1:
                    System.out.print("Enter name of the new person: ");
                    String newName = in.next();

                    if (people.containsKey(newName)) {
                        System.out.println("Person already exists!");
                    } else {
                        int maxId = 0;
                        for (int id : people.values()) {
                            if (id > maxId) {
                                maxId = id;
                            }
                        }
                        int newId = maxId + 1;

                        g.addVertex(newId);
                        people.put(newName, newId);
                        System.out.println(
                                "Added " + newName + " to the network.");
                    }
                    break;

                case OPTION_2:
                    System.out.print("Enter name of the first person: ");
                    String person1 = in.next();
                    System.out.print("Enter name of the second person: ");
                    String person2 = in.next();

                    if (!people.containsKey(person1)) {
                        System.out.println("First person not found!");
                    } else if (!people.containsKey(person2)) {
                        System.out.println("Second person not found!");
                    } else if (person1.equals(person2)) {
                        System.out.println(
                                "Cannot connect a person to themselves!");
                    } else {
                        int person1Tag = people.get(person1);
                        int person2Tag = people.get(person2);

                        if (g.containsEdge(person1Tag, person2Tag)) {
                            System.out.println("Connection already exists!");
                        } else {
                            g.addEdge(person1Tag, person2Tag, 1);
                            System.out.println("Added connection: " + person1
                                    + " - " + person2);
                        }
                    }
                    break;

                case OPTION_3:
                    System.out.print("Enter name of the first person: ");
                    String p1 = in.next();
                    System.out.print("Enter name of the second person: ");
                    String p2 = in.next();

                    if (!people.containsKey(p1)) {
                        System.out.println("First person not found!");
                    } else if (!people.containsKey(p2)) {
                        System.out.println("Second person not found!");
                    } else {
                        int p1Tag = people.get(p1);
                        int p2Tag = people.get(p2);

                        if (!g.containsEdge(p1Tag, p2Tag)) {
                            System.out.println("Connection does not exist!");
                        } else {
                            g.removeEdge(p1Tag, p2Tag);
                            System.out.println(
                                    "Removed connection: " + p1 + " - " + p2);
                        }
                    }
                    break;

                case OPTION_4:
                    return;

                default:
                    System.out.println("Invalid choice!");
            }
        }
    }
}
