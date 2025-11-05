// Scenario: Disaster Relief Resource Allocation 
// A massive earthquake has struck a remote region, and a relief organization is transporting 
// essential supplies to the affected area. The organization has a limited-capacity relief truck that 
// can carry a maximum weight of W kg. They have N different types of essential items, each 
// with a specific weight and an associated utility value (importance in saving lives and meeting 
// urgent needs). 
// Since the truck has limited capacity, you must decide which items to include to maximize the 
// total utility value while ensuring the total weight does not exceed the truck's limit. 
// Your Task as a Logistics Coordinator: 
// 1. Model this problem using the 0/1 Knapsack approach, where each item can either be 
// included in the truck (1) or not (0). 
// 2. Implement an algorithm to find the optimal set of items that maximizes utility while 
// staying within the weight constraint. 
// 3. Analyze the performance of different approaches (e.g., Brute Force, Dynamic 
// Programming, and Greedy Algorithms) for solving this problem efficiently. 
// 4. Optimize for real-world constraints, such as perishable items (medicines, food) having 
// priority over less critical supplies. 
// Extend the model to consider multiple trucks or real-time decision-making for dynamic supply 
// chain management.

//Name:Aditya Bhoye
//PRN:123B1F010
//Date:19/09/2025
import java.util.*;
class Item {
    int weight;
    int value;
    boolean isPerishable;
    Item(int weight, int value, boolean isPerishable) {
        this.weight = weight;
        this.value = value;
        this.isPerishable = isPerishable;
    }
}
public class Assignment_6 {
    public static int knapsackDP(Item[] items, int W) {
        int n = items.length;
        int[][] dp = new int[n + 1][W + 1];

        for (int i = 1; i <= n; i++) {
            for (int w = 1; w <= W; w++) {
                if (items[i - 1].weight <= w) {
                    dp[i][w] = Math.max(
                            items[i - 1].value + dp[i - 1][w - items[i - 1].weight],
                            dp[i - 1][w]
                    );
                } else {
                    dp[i][w] = dp[i - 1][w];
                }
            }
        }
        return dp[n][W];
    }
    public static List<Item> getSelectedItems(Item[] items, int W) {
        int n = items.length;
        int[][] dp = new int[n + 1][W + 1];

        for (int i = 1; i <= n; i++) {
            for (int w = 1; w <= W; w++) {
                if (items[i - 1].weight <= w) {
                    dp[i][w] = Math.max(
                            items[i - 1].value + dp[i - 1][w - items[i - 1].weight],
                            dp[i - 1][w]
                    );
                } else {
                    dp[i][w] = dp[i - 1][w];
                }
            }
        }
        List<Item> selected = new ArrayList<>();
        int w = W;
        for (int i = n; i > 0 && w > 0; i--) {
            if (dp[i][w] != dp[i - 1][w]) {
                selected.add(items[i - 1]);
                w -= items[i - 1].weight;
            }
        }
        return selected;
    }
    public static int knapsackBruteForce(Item[] items, int W, int n) {
        if (n == 0 || W == 0) return 0;
        if (items[n - 1].weight > W)
            return knapsackBruteForce(items, W, n - 1);
        else
            return Math.max(
                    items[n - 1].value + knapsackBruteForce(items, W - items[n - 1].weight, n - 1),
                    knapsackBruteForce(items, W, n - 1)
            );
    }
    public static double greedyKnapsack(Item[] items, int W) {
        Arrays.sort(items, (a, b) -> Double.compare(
                (double) b.value / b.weight, (double) a.value / a.weight
        ));
        double totalValue = 0;
        int currWeight = 0;
        for (Item item : items) {
            if (currWeight + item.weight <= W) {
                currWeight += item.weight;
                totalValue += item.value;
            }
        }
        return totalValue;
    }
    public static void main(String[] args) {
        Item[] items = {
                new Item(10, 60, true),   // Medicines
                new Item(20, 100, true),  // Food
                new Item(30, 120, false), // Tents
                new Item(15, 90, false)   // Tools
        };
        int W = 50;
        for (Item i : items) {
            if (i.isPerishable) i.value *= 1.2;
        }
        System.out.println("=== Disaster Relief Resource Allocation ===");
        System.out.println("Truck Capacity: " + W + " kg");
        System.out.println("\n>> Brute Force Result: " + knapsackBruteForce(items, W, items.length));
        System.out.println(">> DP Optimal Result: " + knapsackDP(items, W));
        System.out.println(">> Greedy (approx): " + greedyKnapsack(items, W));
        List<Item> selected = getSelectedItems(items, W);
        System.out.println("\nItems Selected (DP Optimal):");
        for (Item it : selected) {
            System.out.println("Weight: " + it.weight + "  Value: " + it.value + "  Perishable: " + it.isPerishable);
        }
    }
}
