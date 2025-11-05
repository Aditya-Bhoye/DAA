// Scenario: University Timetable Scheduling
// A university is facing challenges in scheduling exam timetables due to overlapping student 
// enrollments in multiple courses. To prevent clashes, the university needs to assign exam 
// slots efficiently, ensuring that no two exams taken by the same student are scheduled at the 
// same time. 
// To solve this, the university decides to model the problem as a Graph Coloring Problem, 
// where: 
// ● Each course is represented as a vertex. 
// ● An edge exists between two vertices if a student is enrolled in both courses. 
// ● Each vertex (course) must be assigned a color (time slot) such that no two adjacent 
// vertices share the same color (no two exams with common students are scheduled in the 
// same slot). 
// As a scheduling system developer, you must: 
// 1. Model the problem as a graph and implement a graph coloring algorithm (e.g., Greedy 
// Coloring or Backtracking). 
// 2. Minimize the number of colors (exam slots) needed while ensuring conflict-free 
// scheduling. 
// 3. Handle large datasets with thousands of courses and students, optimizing performance. 
// 4. Compare the efficiency of Greedy Coloring, DSATUR, and Welsh-Powell algorithms 
// for better scheduling. 
// Extend the solution to include room allocation constraints where exams in the same slot should 
// fit within available classrooms. 

//Name:Aditya Bhoye
//PRN:123B1F010
//Date:03/10/2025
import java.util.*;
public class Assignment_7 {
    static class Graph {
        int V;
        List<Integer>[] adj;
        Graph(int V) {
            this.V = V;
            adj = new ArrayList[V];
            for (int i = 0; i < V; i++) adj[i] = new ArrayList<>();
        }
        void addEdge(int u, int v) {
            adj[u].add(v);
            adj[v].add(u);
        }
    }
    static int[] greedyColoring(Graph g) {
        int V = g.V;
        int[] result = new int[V];
        Arrays.fill(result, -1);
        result[0] = 0;
        boolean[] available = new boolean[V];
        for (int u = 1; u < V; u++) {
            Arrays.fill(available, true);
            for (int neighbor : g.adj[u]) {
                if (result[neighbor] != -1)
                    available[result[neighbor]] = false;
            }
            int color;
            for (color = 0; color < V; color++)
                if (available[color]) break;
            result[u] = color;
        }
        return result;
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
                        for (int n : g.adj[u])
                            if (color[n] == currColor) { conflict = true; break; }
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
                for (int n : g.adj[v]) if (color[n] != -1) neighborColors.add(color[n]);
                int sat = neighborColors.size();
                if (sat > maxSat || (sat == maxSat && degree[v] > maxDeg)) {
                    maxSat = sat;
                    maxDeg = degree[v];
                    vertex = v;
                }
            }
            boolean[] available = new boolean[V];
            Arrays.fill(available, true);
            for (int n : g.adj[vertex]) if (color[n] != -1) available[color[n]] = false;
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
    static void allocateRooms(int[] colors, int totalRooms) {
        int maxSlot = Arrays.stream(colors).max().getAsInt() + 1;
        System.out.println("\nRoom Allocation per Time Slot:");
        for (int slot = 0; slot < maxSlot; slot++) {
            System.out.print("Slot " + slot + ": ");
            List<Integer> courses = new ArrayList<>();
            for (int i = 0; i < colors.length; i++)
                if (colors[i] == slot) courses.add(i);
            if (courses.size() > totalRooms)
                System.out.println(" Not enough rooms! Need " + courses.size() + ", available " + totalRooms);
            else
                System.out.println("Courses " + courses + " assigned to " + courses.size() + " rooms.");
        }
    }
    static void displayResult(String method, int[] color) {
        System.out.println("\n--- " + method + " ---");
        int slots = Arrays.stream(color).max().getAsInt() + 1;
        System.out.println("Total Slots Required: " + slots);
        for (int i = 0; i < color.length; i++)
            System.out.println("Course " + i + " -> Slot " + color[i]);
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
