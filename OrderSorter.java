import java.time.Instant;
import java.util.Arrays;

// Represents a single customer order with an ID and a timestamp
class CustomerOrder implements Comparable<CustomerOrder> {
    private final String orderId;
    private final Instant timestamp;

    public CustomerOrder(String orderId, Instant timestamp) {
        this.orderId = orderId;
        this.timestamp = timestamp;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return "Order{id='" + orderId + "', timestamp=" + timestamp + '}';
    }

    // Defines the natural sorting order based on the timestamp
    @Override
    public int compareTo(CustomerOrder other) {
        return this.timestamp.compareTo(other.getTimestamp());
    }
}

public class OrderSorter {

    // Main function that sorts an array of CustomerOrder objects
    public static void mergeSort(CustomerOrder[] arr, int left, int right) {
        if (left < right) {
            int middle = left + (right - left) / 2;
            mergeSort(arr, left, middle);
            mergeSort(arr, middle + 1, right);
            merge(arr, left, middle, right);
        }
    }

    // Merges two sorted subarrays
    private static void merge(CustomerOrder[] arr, int left, int middle, int right) {
        int n1 = middle - left + 1;
        int n2 = right - middle;

        CustomerOrder[] L = new CustomerOrder[n1];
        CustomerOrder[] R = new CustomerOrder[n2];

        System.arraycopy(arr, left, L, 0, n1);
        System.arraycopy(arr, middle + 1, R, 0, n2);

        int i = 0, j = 0;
        int k = left;
        while (i < n1 && j < n2) {
            // The comparison logic uses the compareTo method from CustomerOrder
            if (L[i].compareTo(R[j]) <= 0) {
                arr[k] = L[i];
                i++;
            } else {
                arr[k] = R[j];
                j++;
            }
            k++;
        }

        while (i < n1) {
            arr[k] = L[i];
            i++;
            k++;
        }
        while (j < n2) {
            arr[k] = R[j];
            j++;
            k++;
        }
    }

    public static void main(String[] args) {
        CustomerOrder[] orders = {
            new CustomerOrder("ID003", Instant.parse("2025-09-26T10:15:35Z")),
            new CustomerOrder("ID001", Instant.parse("2025-09-26T10:15:30Z")),
            new CustomerOrder("ID005", Instant.parse("2025-09-26T10:16:00Z")),
            new CustomerOrder("ID002", Instant.parse("2025-09-26T10:14:00Z")),
            new CustomerOrder("ID004", Instant.parse("2025-09-26T10:15:55Z"))
        };

        System.out.println("Unsorted Orders:");
        System.out.println(Arrays.toString(orders));

        mergeSort(orders, 0, orders.length - 1);

        System.out.println("\nSorted Orders by Timestamp:");
        System.out.println(Arrays.toString(orders));
    }
}
