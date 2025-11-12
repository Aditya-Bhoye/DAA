import java.util.*;
public class FunctionalKnapsack {
    static class Item {
        int weight;
        int profit;
        double ratio;
        Item(int weight, int profit) {
            this.weight = weight;
            this.profit = profit;
            this.ratio = (double) profit / weight;
        }
    }
    public static List<Item> readItems(Scanner sc) {
        System.out.print("Enter number of items: ");
        int n = sc.nextInt();
        List<Item> items = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            System.out.println("\nItem " + (i + 1) + ":");
            System.out.print("Profit: ");
            int profit = sc.nextInt();
            System.out.print("Weight: ");
            int weight = sc.nextInt();
            items.add(new Item(weight, profit));
        }
        return items;
    }
    public static double fractionalKnapsack(List<Item> items, int capacity) {
        items.sort(Comparator.comparingDouble((Item i) -> i.ratio).reversed());
        double totalValue = 0.0;
        int currentWeight = 0;
        for (Item item : items) {
            if (currentWeight + item.weight <= capacity) {
                currentWeight += item.weight;
                totalValue += item.profit;
            } else {
                int remaining = capacity - currentWeight;
                totalValue += item.ratio * remaining;
                break;
            }
        }
        return totalValue;
    }
    public static void displayItems(List<Item> items) {
        System.out.println("\n--- Items (sorted by profit/weight ratio) ---");
        for (Item i : items) {
            System.out.printf("Profit: %d | Weight: %d | Ratio: %.2f%n", i.profit, i.weight, i.ratio);
        }
        System.out.println("--------------------------------------------");
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        List<Item> items = readItems(sc);
        System.out.print("\nEnter knapsack capacity: ");
        int capacity = sc.nextInt();
        items.sort(Comparator.comparingDouble((Item i) -> i.ratio).reversed());
        displayItems(items);
        double maxValue = fractionalKnapsack(items, capacity);
        System.out.printf("\n Maximum achievable value: %.2f%n", maxValue);
    }
}
