import java.util.*;
public class Assignment_8 {
    static class Graph {
        int V;
        List<Integer>[] adj;
        Graph(int V) {
            this.V = V;
            adj = new ArrayList[V];
            for (int i = 0; i < V; i++) adj[i] = new ArrayList<>();
        }
        void addEdge(int u, int v) {
            if (!adj[u].contains(v)) {
                adj[u].add(v);
                adj[v].add(u);
            }
        }
    }
    static int[] greedyColoring(Graph g) {
        int V = g.V;
        int[] color = new int[V];
        Arrays.fill(color, -1);
        boolean[] available = new boolean[V];
        color[0] = 0;
        for (int u = 1; u < V; u++) {
            Arrays.fill(available, true);
            for (int neighbor : g.adj[u])
                if (color[neighbor] != -1) available[color[neighbor]] = false;
            for (int c = 0; c < V; c++) {
                if (available[c]) {
                    color[u] = c;
                    break;
                }
            }
        }
        return color;
    }
    static int[] welshPowellColoring(Graph g) {
        int V = g.V;
        Integer[] vertices = new Integer[V];
        for (int i = 0; i < V; i++) vertices[i] = i;
        Arrays.sort(vertices, (a, b) -> g.adj[b].size() - g.adj[a].size());
        int[] color = new int[V];
        Arrays.fill(color, -1);
        int currColor = 0;
        for (int v : vertices) {
            if (color[v] == -1) {
                color[v] = currColor;
                for (int u : vertices) {
                    if (color[u] == -1 && !g.adj[v].contains(u)) {
                        boolean conflict = false;
                        for (int n : g.adj[u]) {
                            if (color[n] == currColor) {
                                conflict = true;
                                break;
                            }
                        }
                        if (!conflict) color[u] = currColor;
                    }
                }
                currColor++;
            }
        }
        return color;
    }
    static int[] dsaturColoring(Graph g) {
        int V = g.V;
        int[] color = new int[V];
        Arrays.fill(color, -1);
        int[] degree = new int[V];
        for (int i = 0; i < V; i++) degree[i] = g.adj[i].size();
        Set<Integer> uncolored = new HashSet<>();
        for (int i = 0; i < V; i++) uncolored.add(i);
        while (!uncolored.isEmpty()) {
            int maxSat = -1, maxDeg = -1, vertex = -1;
            for (int v : uncolored) {
                Set<Integer> neighborColors = new HashSet<>();
                for (int n : g.adj[v])
                    if (color[n] != -1) neighborColors.add(color[n]);
                int sat = neighborColors.size();
                if (sat > maxSat || (sat == maxSat && degree[v] > maxDeg)) {
                    maxSat = sat;
                    maxDeg = degree[v];
                    vertex = v;
                }
            }
            boolean[] available = new boolean[V];
            Arrays.fill(available, true);
            for (int n : g.adj[vertex])
                if (color[n] != -1) available[color[n]] = false;
            for (int c = 0; c < V; c++) {
                if (available[c]) {
                    color[vertex] = c;
                    break;
                }
            }
            uncolored.remove(vertex);
        }
        return color;
    }
    static void allocateRooms(int[] colors, int availableRooms) {
        int totalSlots = Arrays.stream(colors).max().getAsInt() + 1;
        System.out.println("\nRoom Allocation (Available: " + availableRooms + " rooms per slot)");
        for (int slot = 0; slot < totalSlots; slot++) {
            List<Integer> courses = new ArrayList<>();
            for (int i = 0; i < colors.length; i++)
                if (colors[i] == slot) courses.add(i);
            if (courses.size() > availableRooms)
                System.out.println("⚠️ Slot " + slot + " -> " + courses.size() + " courses (Not enough rooms!)");
            else
                System.out.println("Slot " + slot + " -> " + courses.size() + " courses assigned successfully.");
        }
    }
    static void displayResult(String title, int[] colors) {
        int slots = Arrays.stream(colors).max().getAsInt() + 1;
        System.out.println("\n--- " + title + " ---");
        System.out.println("Total Time Slots Needed: " + slots);
        for (int i = 0; i < colors.length; i++)
            System.out.println("Course " + i + " → Slot " + colors[i]);
    }
    public static void main(String[] args) {
        Graph g = new Graph(6);
        g.addEdge(0, 1);
        g.addEdge(0, 2);
        g.addEdge(1, 3);
        g.addEdge(2, 3);
        g.addEdge(3, 4);
        g.addEdge(4, 5);
        int[] greedy = greedyColoring(g);
        int[] wp = welshPowellColoring(g);
        int[] dsatur = dsaturColoring(g);
        displayResult("Greedy Coloring", greedy);
        displayResult("Welsh-Powell Algorithm", wp);
        displayResult("DSATUR Algorithm", dsatur);
        allocateRooms(dsatur, 3);
    }
}
