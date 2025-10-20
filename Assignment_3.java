import java.util.Arrays;
import java.util.Comparator;
class Item {
    String name;
    double value, weight;
    Item(String name, double value, double weight) {
        this.name = name;
        this.value = value;
        this.weight = weight;
    }
}
public class Assignment_3 {
    public static double getMaxValue(Item[] items, double capacity) {
        Arrays.sort(items, new Comparator<Item>() {
            @Override
            public int compare(Item a, Item b) {
                double r1 = a.value / a.weight;
                double r2 = b.value / b.weight;
                return Double.compare(r2, r1);
            }
        });
        double totalValue = 0.0;
        double currentWeight = 0.0;
        System.out.println("Filling the boat with critical supplies...");
        for (Item item : items) {
            if (currentWeight + item.weight <= capacity) {
                currentWeight += item.weight;
                totalValue += item.value;
                System.out.printf("Took full %s (%.2f kg) → Value: %.2f%n",
                        item.name, item.weight, item.value);
            } else {
                double remaining = capacity - currentWeight;
                double valueTaken = (item.value / item.weight) * remaining;
                totalValue += valueTaken;
                System.out.printf("Took %.2f kg of %s (fractional) → Value: %.2f%n",
                        remaining, item.name, valueTaken);
                currentWeight = capacity;
                break; // Boat full
            }
        }
        System.out.println("\n Total utility value carried: " + totalValue);
        return totalValue;
    }
    public static void main(String[] args) {
        Item[] items = {
                new Item("Food", 120, 10),
                new Item("Water", 280, 40),
                new Item("Medicine", 150, 20),
                new Item("Clothing", 200, 30)
        };
        double boatCapacity = 50;
        getMaxValue(items, boatCapacity);
    }
}
