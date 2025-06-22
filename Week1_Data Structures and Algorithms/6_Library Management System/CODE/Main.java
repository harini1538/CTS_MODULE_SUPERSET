import java.util.Arrays;
import java.util.Comparator;

class Book {
    int bookId;
    String title;
    String author;

    public Book(int bookId, String title, String author) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
    }

    @Override
    public String toString() {
        return "ID: " + bookId + ", Title: " + title + ", Author: " + author;
    }
}

public class Main {
    // Linear search by title
    public static Book linearSearch(Book[] books, String title) {
        for (Book book : books) {
            if (book.title.equalsIgnoreCase(title)) {
                return book;
            }
        }
        return null;
    }

    // Binary search by title (array must be sorted)
    public static Book binarySearch(Book[] books, String title) {
        int left = 0, right = books.length - 1;
        while (left <= right) {
            int mid = (left + right) / 2;
            int cmp = books[mid].title.compareToIgnoreCase(title);
            if (cmp == 0) return books[mid];
            else if (cmp < 0) left = mid + 1;
            else right = mid - 1;
        }
        return null;
    }

    public static void main(String[] args) {
        Book[] books = {
            new Book(101, "The Alchemist", "Paulo Coelho"),
            new Book(102, "1984", "George Orwell"),
            new Book(103, "Clean Code", "Robert C. Martin"),
            new Book(104, "To Kill a Mockingbird", "Harper Lee")
        };

        // Sort books by title for binary search
        Arrays.sort(books, Comparator.comparing(book -> book.title.toLowerCase()));

        System.out.println("Searching using Linear Search:");
        Book result1 = linearSearch(books, "1984");
        System.out.println(result1 != null ? result1 : "Book not found.");

        System.out.println("\nSearching using Binary Search:");
        Book result2 = binarySearch(books, "1984");
        System.out.println(result2 != null ? result2 : "Book not found.");
    }
}
