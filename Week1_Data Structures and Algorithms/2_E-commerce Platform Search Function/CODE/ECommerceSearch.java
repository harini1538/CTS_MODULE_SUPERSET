import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

// Product class with productId, productName, and category
class Product {
    int productId;
    String productName;
    String category;

    public Product(int productId, String productName, String category) {
        this.productId = productId;
        this.productName = productName;
        this.category = category;
    }

    @Override
    public String toString() {
        return "Product ID: " + productId +
               ", Name: " + productName +
               ", Category: " + category;
    }
}

// Main class
public class ECommerceSearch {

    // Linear search by product name
    public static Product linearSearch(Product[] products, String name) {
        for (Product p : products) {
            if (p.productName.equalsIgnoreCase(name)) {
                return p;
            }
        }
        return null;
    }

    // Binary search by product name (requires sorted array)
    public static Product binarySearch(Product[] products, String name) {
        int left = 0;
        int right = products.length - 1;

        while (left <= right) {
            int mid = (left + right) / 2;
            int compare = products[mid].productName.compareToIgnoreCase(name);

            if (compare == 0) {
                return products[mid];
            } else if (compare < 0) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return null;
    }

    // Main method to test the search functionality
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        Product[] products = {
            new Product(101, "Laptop", "Electronics"),
            new Product(102, "Shoes", "Footwear"),
            new Product(103, "T-shirt", "Clothing"),
            new Product(104, "Mobile", "Electronics"),
            new Product(105, "Watch", "Accessories")
        };

        System.out.println("--- E-Commerce Product Search ---");
        System.out.print("Enter product name to search (Linear Search): ");
        String name1 = sc.nextLine();

        Product resultLinear = linearSearch(products, name1);
        System.out.println(resultLinear != null ? resultLinear : "Product not found (Linear Search)");

        // Sort products for binary search
        Arrays.sort(products, Comparator.comparing(p -> p.productName.toLowerCase()));

        System.out.print("\nEnter product name to search (Binary Search): ");
        String name2 = sc.nextLine();

        Product resultBinary = binarySearch(products, name2);
        System.out.println(resultBinary != null ? resultBinary : "Product not found (Binary Search)");

        sc.close();
    }
}
