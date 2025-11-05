// Scenario: Smart Traffic Management for Emergency Vehicles 
// A smart city is implementing an intelligent traffic management system to assist ambulances 
// in reaching hospitals as quickly as possible. The city’s road network is represented as a 
// graph, where: 
// ● Intersections (junctions) are nodes. 
// ● Roads between intersections are edges with weights representing travel time (in minutes) 
// considering traffic congestion. 
// An ambulance is currently at Source (S) and needs to reach the nearest hospital (Destination 
// D) in the shortest possible time. Due to dynamic traffic conditions, the weight of each road 
// segment may change in real time. 
// As a transportation engineer, you are assigned to: 
// 1. Implement Dijkstra’s algorithm to find the shortest path from the ambulance's current 
// location (S) to all possible hospitals. 
// 2. Account for dynamic weight updates as traffic conditions change. 
// 3. Optimize the system to work efficiently for a large city with thousands of intersections 
// and roads. 
// 4. Provide a visual representation of the optimal path for navigation. 
// Expected Outcome: 
// The system should suggest the quickest route for the ambulance, updating dynamically 
// based on real-time traffic conditions, ensuring minimal response time to emergencies.

//Name:Aditya Bhoye
//PRN:123B1F010
//Date:22/08/2025
import java.util.*;
class Edge {
    int to;
    double weight;
    Edge(int to, double weight) {
        this.to = to;
        this.weight = weight;
    }
}
public class Assignment_4 {
    private static Map<Integer, List<Edge>> graph = new HashMap<>();
    public static double[] dijkstra(int source, int numNodes) {
        double[] dist = new double[numNodes];
        Arrays.fill(dist, Double.POSITIVE_INFINITY);
        dist[source] = 0;
        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingDouble(a -> a[1]));
        pq.add(new int[]{source, 0});
        while (!pq.isEmpty()) {
            int[] curr = pq.poll();
            int u = curr[0];
            double d = curr[1];
            if (d > dist[u]) continue;
            for (Edge edge : graph.getOrDefault(u, new ArrayList<>())) {
                int v = edge.to;
                double weight = edge.weight;
                if (dist[u] + weight < dist[v]) {
                    dist[v] = dist[u] + weight;
                    pq.add(new int[]{v, (int) dist[v]});
                }
            }
        }
        return dist;
    }
    public static void updateEdgeWeight(int from, int to, double newWeight) {
        for (Edge edge : graph.getOrDefault(from, new ArrayList<>())) {
            if (edge.to == to) {
                edge.weight = newWeight;
                break;
            }
        }
    }
    public static void main(String[] args) {
        int numNodes = 6; 
        graph.put(0, new ArrayList<>(Arrays.asList(new Edge(1, 4), new Edge(2, 2))));
        graph.put(1, new ArrayList<>(Arrays.asList(new Edge(2, 5), new Edge(3, 10))));
        graph.put(2, new ArrayList<>(Arrays.asList(new Edge(4, 3))));
        graph.put(3, new ArrayList<>(Arrays.asList(new Edge(5, 11))));
        graph.put(4, new ArrayList<>(Arrays.asList(new Edge(3, 4))));
        graph.put(5, new ArrayList<>());
        int ambulance = 0;
        int hospital = 5;
        double[] dist = dijkstra(ambulance, numNodes);
        System.out.println("Initial shortest time to hospital: " + dist[hospital] + " minutes");
        System.out.println("\nTraffic update: road 2->4 now slower (weight 10)");
        updateEdgeWeight(2, 4, 10);
        dist = dijkstra(ambulance, numNodes);
        System.out.println("Updated shortest time to hospital: " + dist[hospital] + " minutes");
    }
}
