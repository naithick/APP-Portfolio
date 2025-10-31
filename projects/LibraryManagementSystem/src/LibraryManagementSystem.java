import java.util.List;
import java.util.Scanner;

public class LibraryManagementSystem {
    private static DatabaseManager dbManager;
    private static Scanner scanner;

    public static void main(String[] args) {
        dbManager = new DatabaseManager();
        scanner = new Scanner(System.in);

        System.out.println("*** Library Management System ***");
        System.out.println();

        boolean running = true;
        while (running) {
            displayMenu();
            int choice = getChoice();

            switch (choice) {
                case 1:
                    addBook();
                    break;
                case 2:
                    viewAllBooks();
                    break;
                case 3:
                    issueBook();
                    break;
                case 4:
                    returnBook();
                    break;
                case 5:
                    deleteBook();
                    break;
                case 6:
                    running = false;
                    System.out.println("\nThank you!");
                    break;
                default:
                    System.out.println("\nInvalid choice! Try again.\n");
            }
        }

        dbManager.closeConnection();
        scanner.close();
    }

    private static void displayMenu() {
        System.out.println("===== MAIN MENU =====");
        System.out.println("1. Add New Book");
        System.out.println("2. View All Books");
        System.out.println("3. Issue Book");
        System.out.println("4. Return Book");
        System.out.println("5. Delete Book");
        System.out.println("6. Exit");
        System.out.println("=====================");
        System.out.print("Enter choice: ");
    }

    private static int getChoice() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private static void addBook() {
        System.out.println("\n--- Add New Book ---");
        System.out.print("Enter Book Title: ");
        String title = scanner.nextLine();
        System.out.print("Enter Author Name: ");
        String author = scanner.nextLine();
        System.out.print("Enter ISBN: ");
        String isbn = scanner.nextLine();

        if (title.isEmpty() || author.isEmpty() || isbn.isEmpty()) {
            System.out.println("All fields required!\n");
            return;
        }

        boolean success = dbManager.addBook(title, author, isbn);
        if (success) {
            System.out.println("Book added successfully!\n");
        } else {
            System.out.println("Failed to add book!\n");
        }
    }

    private static void viewAllBooks() {
        System.out.println("\n--- All Books ---");
        List<Book> books = dbManager.getAllBooks();
        
        if (books.isEmpty()) {
            System.out.println("No books found.\n");
            return;
        }

        System.out.println("Total Books: " + books.size());
        for (Book book : books) {
            System.out.println(book);
        }
        System.out.println();
    }

    private static void issueBook() {
        System.out.println("\n--- Issue Book ---");
        System.out.print("Enter Book ID: ");
        try {
            int bookId = Integer.parseInt(scanner.nextLine());
            boolean success = dbManager.issueBook(bookId);
            if (success) {
                System.out.println("Book issued!\n");
            } else {
                System.out.println("Failed! Book may already be issued.\n");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID!\n");
        }
    }

    private static void returnBook() {
        System.out.println("\n--- Return Book ---");
        System.out.print("Enter Book ID: ");
        try {
            int bookId = Integer.parseInt(scanner.nextLine());
            boolean success = dbManager.returnBook(bookId);
            if (success) {
                System.out.println("Book returned!\n");
            } else {
                System.out.println("Failed to return book.\n");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID!\n");
        }
    }

    private static void deleteBook() {
        System.out.println("\n--- Delete Book ---");
        System.out.print("Enter Book ID: ");
        try {
            int bookId = Integer.parseInt(scanner.nextLine());
            System.out.print("Are you sure? (yes/no): ");
            String confirm = scanner.nextLine();
            
            if (confirm.equalsIgnoreCase("yes")) {
                boolean success = dbManager.deleteBook(bookId);
                if (success) {
                    System.out.println("Book deleted!\n");
                } else {
                    System.out.println("Failed to delete.\n");
                }
            } else {
                System.out.println("Cancelled.\n");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID!\n");
        }
    }
}
