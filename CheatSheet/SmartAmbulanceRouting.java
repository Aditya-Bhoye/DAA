import java.util.*;
class Graph {
    private Map<String, List<Edge>> adjList = new HashMap<>();
    static class Edge {
        String destination;
        int weight;
        Edge(String destination, int weight) {
            this.destination = destination;
            this.weight = weight;
        }
    }
    public void addIntersection(String node) {
        adjList.putIfAbsent(node, new ArrayList<>());
    }
    public void addRoad(String src, String dest, int weight) {
        adjList.get(src).add(new Edge(dest, weight));
        adjList.get(dest).add(new Edge(src, weight)); // Assuming bidirectional road
    }
    public void updateRoadWeight(String src, String dest, int newWeight) {
        for (Edge e : adjList.get(src)) {
            if (e.destination.equals(dest)) {
                e.weight = newWeight;
            }
        }
        for (Edge e : adjList.get(dest)) {
            if (e.destination.equals(src)) {
                e.weight = newWeight;
            }
        }
        System.out.println("Traffic Updated: " + src + " ↔ " + dest + " = " + newWeight + " mins");
    }
    public void dijkstra(String source, List<String> hospitals) {
        Map<String, Integer> dist = new HashMap<>();
        Map<String, String> parent = new HashMap<>();
        PriorityQueue<String> pq = new PriorityQueue<>(Comparator.comparing(dist::get));
        for (String node : adjList.keySet())
            dist.put(node, Integer.MAX_VALUE);
        dist.put(source, 0);
        pq.add(source);
        while (!pq.isEmpty()) {
            String current = pq.poll();
            for (Edge edge : adjList.get(current)) {
                int newDist = dist.get(current) + edge.weight;
                if (newDist < dist.get(edge.destination)) {
                    dist.put(edge.destination, newDist);
                    parent.put(edge.destination, current);
                    pq.add(edge.destination);
                }
            }
        }
        String nearestHospital = null;
        int shortestTime = Integer.MAX_VALUE;
        for (String hospital : hospitals) {
            if (dist.get(hospital) < shortestTime) {
                shortestTime = dist.get(hospital);
                nearestHospital = hospital;
            }
        }
        System.out.println("\nNearest Hospital: " + nearestHospital);
        System.out.println("Shortest Travel Time: " + shortestTime + " minutes");
        System.out.println("Optimal Path:");
        printPath(nearestHospital, parent);
    }
    private void printPath(String destination, Map<String, String> parent) {
        if (!parent.containsKey(destination)) {
            System.out.print(destination);
            return;
        }
        printPath(parent.get(destination), parent);
        System.out.print(" → " + destination);
    }
}
public class SmartAmbulanceRouting {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Graph g = new Graph();
        System.out.print("Enter number of intersections: ");
        int n = sc.nextInt();
        sc.nextLine();
        for (int i = 0; i < n; i++) {
            System.out.print("Enter intersection name: ");
            g.addIntersection(sc.nextLine());
        }
        System.out.print("Enter number of roads: ");
        int r = sc.nextInt();
        sc.nextLine();
        for (int i = 0; i < r; i++) {
            System.out.print("Enter road (src dest weight): ");
            String src = sc.next();
            String dest = sc.next();
            int weight = sc.nextInt();
            g.addRoad(src, dest, weight);
        }
        System.out.print("Enter number of hospitals: ");
        int h = sc.nextInt();
        sc.nextLine();
        List<String> hospitals = new ArrayList<>();
        System.out.println("Enter hospital node names:");
        for (int i = 0; i < h; i++)
            hospitals.add(sc.nextLine());
        System.out.print("Enter ambulance location: ");
        String ambulance = sc.nextLine();
        g.dijkstra(ambulance, hospitals);
        while (true) {
            System.out.print("\nUpdate Traffic? (yes/no): ");
            String ch = sc.next();
            if (ch.equalsIgnoreCase("no")) break;
            System.out.print("Enter road to update (src dest newWeight): ");
            String src = sc.next();
            String dest = sc.next();
            int newWeight = sc.nextInt();
            g.updateRoadWeight(src, dest, newWeight);
            g.dijkstra(ambulance, hospitals);
        }
        sc.close();
    }
}
/*
6
A
B
C
D
E
F
7
A B 2
B C 2
C D 2
A D 10
D E 1
E F 1
C F 20
2
D
F
A

*/
