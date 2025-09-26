import java.util.*;

class Node {
    int vertex;
    int weight;
    Node(int vertex, int weight) {
        this.vertex = vertex;
        this.weight = weight;
    }
}

public class DijkstraBase {

    public static int[] dijkstra(List<List<Node>> graph, int src) {
        int V = graph.size();
        int[] dist = new int[V];
        boolean[] visited = new boolean[V];

        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[src] = 0;

        for (int count = 0; count < V - 1; count++) {
            int u = minDistance(dist, visited);
            visited[u] = true;

            for (Node neighbor : graph.get(u)) {
                int v = neighbor.vertex;
                int w = neighbor.weight;
                if (!visited[v] && dist[u] != Integer.MAX_VALUE && dist[u] + w < dist[v]) {
                    dist[v] = dist[u] + w;
                }
            }
        }

        return dist;
    }

    private static int minDistance(int[] dist, boolean[] visited) {
        int min = Integer.MAX_VALUE, idx = -1;
        for (int i = 0; i < dist.length; i++) {
            if (!visited[i] && dist[i] <= min) {
                min = dist[i];
                idx = i;
            }
        }
        return idx;
    }

    public static void main(String[] args) {
        int V = 5;
        List<List<Node>> graph = new ArrayList<>();
        for (int i = 0; i < V; i++) graph.add(new ArrayList<>());

        graph.get(0).add(new Node(1, 2));
        graph.get(0).add(new Node(2, 4));
        graph.get(1).add(new Node(2, 1));
        graph.get(1).add(new Node(3, 7));
        graph.get(2).add(new Node(4, 3));
        graph.get(3).add(new Node(4, 1));

        int[] distances = dijkstra(graph, 0);
        System.out.println("Shortest distances from source 0:");
        for (int i = 0; i < distances.length; i++) {
            System.out.println("Vertex " + i + ": " + distances[i]);
        }
    }
}
