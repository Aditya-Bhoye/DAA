import java.util.Arrays;

class Order {
    String id;
    long timestamp; // in milliseconds

    Order(String id, long timestamp) {
        this.id = id;
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return id + " | Timestamp: " + timestamp;
    }
}

public class MergeSortBase {

    public static void merge(Order[] orders, int left, int mid, int right) {
        int n1 = mid - left + 1;
        int n2 = right - mid;

        Order[] L = new Order[n1];
        Order[] R = new Order[n2];

        for (int i = 0; i < n1; i++) L[i] = orders[left + i];
        for (int j = 0; j < n2; j++) R[j] = orders[mid + 1 + j];

        int i = 0, j = 0, k = left;

        while (i < n1 && j < n2) {
            if (L[i].timestamp <= R[j].timestamp) {
                orders[k++] = L[i++];
            } else {
                orders[k++] = R[j++];
            }
        }

        while (i < n1) orders[k++] = L[i++];
        while (j < n2) orders[k++] = R[j++];
    }

    public static void mergeSort(Order[] orders, int left, int right) {
        if (left < right) {
            int mid = left + (right - left) / 2;
            mergeSort(orders, left, mid);
            mergeSort(orders, mid + 1, right);
            merge(orders, left, mid, right);
        }
    }

    public static void main(String[] args) {
        Order[] orders = {
            new Order("O1", 1690000000000L),
            new Order("O2", 1690050000000L),
            new Order("O3", 1689990000000L)
        };

        System.out.println("Before sorting: " + Arrays.toString(orders));
        mergeSort(orders, 0, orders.length - 1);
        System.out.println("After sorting: " + Arrays.toString(orders));
    }
}
