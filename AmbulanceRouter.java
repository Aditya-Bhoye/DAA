import java.util.*;

// Represents a road to another intersection with a specific travel time
class Edge {
    int targetNode;
    int weight; // Travel time in minutes

    public Edge(int targetNode, int weight) {
        this.targetNode = targetNode;
        this.weight = weight;
    }
}

// Represents a node to visit in the priority queue, prioritized by distance
class Node implements Comparable<Node> {
    int vertex;
    int distance;

    public Node(int vertex, int distance) {
        this.vertex = vertex;
        this.distance = distance;
    }

    @Override
    public int compareTo(Node other) {
        return Integer.compare(this.distance, other.distance);
    }
}

// Main class to manage the city graph and run the algorithm
public class AmbulanceRouter {

    private final int V; // Number of intersections
    private final List<List<Edge>> adj; // Adjacency list for the city map

    public AmbulanceRouter(int V) {
        this.V = V;
        adj = new ArrayList<>(V);
        for (int i = 0; i < V; i++) {
            adj.add(new ArrayList<>());
        }
    }

    /**
     * The dynamic update function.
     * Adds a new road or updates the travel time of an existing one.
     * @param u source intersection
     * @param v target intersection
     * @param weight new travel time in minutes
     */
    public void updateRoad(int u, int v, int weight) {
        // Remove existing edge if it exists to prevent duplicates
        adj.get(u).removeIf(edge -> edge.targetNode == v);
        adj.get(v).removeIf(edge -> edge.targetNode == u);
        
        // Add the new or updated road
        adj.get(u).add(new Edge(v, weight));
        adj.get(v).add(new Edge(u, weight)); // For two-way roads
        System.out.printf("--- MAP UPDATED: Road between %d and %d now takes %d minutes.\n", u, v, weight);
    }

    // Finds and prints the shortest path from a source to a destination
    public List<Integer> findShortestPath(int src, int dest) {
        PriorityQueue<Node> pq = new PriorityQueue<>();
        int[] dist = new int[V];
        int[] parent = new int[V]; // Array to store the path

        Arrays.fill(dist, Integer.MAX_VALUE);
        Arrays.fill(parent, -1);

        dist[src] = 0;
        pq.add(new Node(src, 0));

        while (!pq.isEmpty()) {
            int u = pq.poll().vertex;
            if (u == dest) break;

            for (Edge edge : adj.get(u)) {
                int v = edge.targetNode;
                int weight = edge.weight;
                if (dist[u] != Integer.MAX_VALUE && dist[u] + weight < dist[v]) {
                    dist[v] = dist[u] + weight;
                    parent[v] = u;
                    pq.add(new Node(v, dist[v]));
                }
            }
        }
        return reconstructPath(src, dest, dist, parent);
    }
    
    // Reconstructs the path and returns it as a list of intersections
    private List<Integer> reconstructPath(int src, int dest, int[] dist, int[] parent) {
        List<Integer> path = new ArrayList<>();
        if (dist[dest] == Integer.MAX_VALUE) {
            System.out.println("No path found from intersection " + src + " to hospital at " + dest);
            return path; // Return empty path
        }

        System.out.println("\nShortest travel time is: " + dist[dest] + " minutes.");
        
        int current = dest;
        while (current != -1) {
            path.add(current);
            current = parent[current];
        }
        Collections.reverse(path);
        return path;
    }

    /**
     * Main method to run the embedded example.
     */
    public static void main(String[] args) {
        int V = 6; // City has 6 intersections (0 to 5)
        AmbulanceRouter cityMap = new AmbulanceRouter(V);
        
        // --- Setup the initial road network ---
        System.out.println("--- Initializing City Map ---");
        cityMap.updateRoad(0, 1, 10);
        cityMap.updateRoad(0, 2, 5);
        cityMap.updateRoad(1, 3, 5);
        cityMap.updateRoad(2, 3, 20); // This road has a traffic jam
        cityMap.updateRoad(3, 4, 8); 
        cityMap.updateRoad(4, 5, 4); // Another hospital at intersection 5
        cityMap.updateRoad(2, 5, 12);

        int ambulanceLocation = 0;
        int hospitalLocation = 4;

        // --- Calculate the first route based on initial traffic ---
        System.out.println("\n--- Calculating Initial Route to Hospital at Intersection " + hospitalLocation + " ---");
        List<Integer> initialPath = cityMap.findShortestPath(ambulanceLocation, hospitalLocation);
        System.out.println("==> Initial optimal path: " + initialPath);

        // --- DYNAMIC EVENT: A traffic jam clears up ---
        System.out.println("\n\n--- !!! TRAFFIC UPDATE !!! ---");
        
        // The dynamic function is called here to update the road's weight
        cityMap.updateRoad(2, 3, 3);

        // --- Re-calculate the route with the new traffic data ---
        System.out.println("\n--- Re-calculating Route with Updated Traffic Data ---");
        List<Integer> newPath = cityMap.findShortestPath(ambulanceLocation, hospitalLocation);
        System.out.println("==> New optimal path: " + newPath);
    }
}
