// Scenario: Emergency Relief Supply Distribution 
// A devastating flood has hit multiple villages in a remote area, and the government, along 
// with NGOs, is organizing an emergency relief operation. A rescue team has a limited
// capacity boat that can carry a maximum weight of W kilograms. The boat must transport 
// critical supplies, including food, medicine, and drinking water, from a relief center to the 
// affected villages. 
// Each type of relief item has: 
// ● A weight (wi) in kilograms. 
// ● Utility value (vi) indicating its importance (e.g., medicine has higher value than food). 
// ● Some items can be divided into smaller portions (e.g., food and water), while others must 
// be taken as a whole (e.g., medical kits). 
// As the logistics manager, you must: 
// 1. Implement the Fractional Knapsack algorithm to maximize the total utility value of the 
// supplies transported. 
// 2. Prioritize high-value items while considering weight constraints. 
// 3. Allow partial selection of divisible items (e.g., carrying a fraction of food packets). 
// 4. Ensure that the boat carries the most critical supplies given its weight limit W. 

//Name:Aditya Bhoye
//PRN:123B1F010
//Date:08/08/2025
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
