// Digital Library Management     admin, adminpass

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

// User class
class User {
    private String userId;
    private String password;
    private String role; 
    private String email;

    public User(String userId, String password, String role, String email) {
        this.userId = userId;
        this.password = password;
        this.role = role;
        this.email = email;
    }

    public String getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    public String getEmail() {
        return email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

class Book {
    private String bookId;
    private String title;
    private String author;
    private String category;
    private boolean isIssued;

    public Book(String bookId, String title, String author, String category) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.category = category;
        this.isIssued = false;
    }

    public String getBookId() {
        return bookId;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getCategory() {
        return category;
    }

    public boolean isIssued() {
        return isIssued;
    }

    public void setIssued(boolean issued) {
        isIssued = issued;
    }
}

class Library {
    private Map<String, User> users;
    private Map<String, Book> books;

    public Library() {
        users = new HashMap<>();
        books = new HashMap<>();
    }

    public void addUser(User user) {
        users.put(user.getUserId(), user);
    }

    public void addBook(Book book) {
        books.put(book.getBookId(), book);
    }

    public User authenticateUser(String userId, String password) {
        User user = users.get(userId);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }

    public void issueBook(String bookId, User user) {
        Book book = books.get(bookId);
        if (book != null && !book.isIssued()) {
            book.setIssued(true);
            System.out.println("Book issued successfully.");
        } else {
            System.out.println("Book is not available.");
        }
    }

    public void returnBook(String bookId) {
        Book book = books.get(bookId);
        if (book != null && book.isIssued()) {
            book.setIssued(false);
            System.out.println("Book returned successfully.");
        } else {
            System.out.println("Book is not issued.");
        }
    }

    public void viewBooks() {
        for (Book book : books.values()) {
            System.out.println(book.getTitle() + " by " + book.getAuthor() + " [" + (book.isIssued() ? "Issued" : "Available") + "]");
        }
    }
}

public class LibraryManagementSystem {
    public static void main(String[] args) {
        Library library = new Library();
        Scanner scanner = new Scanner(System.in);

        library.addUser(new User("admin", "adminpass", "admin", "admin@example.com"));
        library.addUser(new User("user1", "userpass1", "user", "user1@example.com"));

        library.addBook(new Book("1", "The Great Gatsby", "F. Scott Fitzgerald", "Fiction"));
        library.addBook(new Book("2", "1984", "George Orwell", "Dystopian"));
        library.addBook(new Book("3", "To Kill a Mockingbird", "Harper Lee", "Fiction"));

        boolean exit = false;

        while (!exit) {
            System.out.println("1. Login\n2. Exit");
            System.out.print("Choose an option: ");
            int option = scanner.nextInt();

            switch (option) {
                case 1:
                    System.out.print("Enter user ID: ");
                    String userId = scanner.next();
                    System.out.print("Enter password: ");
                    String password = scanner.next();

                    User user = library.authenticateUser(userId, password);
                    if (user != null) {
                        System.out.println("Login successful!");
                        if (user.getRole().equals("admin")) {
                            handleAdminActions(library, scanner);
                        } else {
                            handleUserActions(library, user, scanner);
                        }
                    } else {
                        System.out.println("Invalid user ID or password.");
                    }
                    break;
                case 2:
                    exit = true;
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }

        scanner.close();
    }

    private static void handleAdminActions(Library library, Scanner scanner) {
        boolean adminExit = false;

        while (!adminExit) {
            System.out.println("1. Add Book\n2. Update Book\n3. Delete Book\n4. View Books\n5. Logout");
            System.out.print("Choose an option: ");
            int option = scanner.nextInt();

            switch (option) {
                case 1:
                    System.out.print("Enter book ID: ");
                    String bookId = scanner.next();
                    System.out.print("Enter title: ");
                    scanner.nextLine(); 
                    String title = scanner.nextLine();
                    System.out.print("Enter author: ");
                    String author = scanner.nextLine();
                    System.out.print("Enter category: ");
                    String category = scanner.nextLine();

                    library.addBook(new Book(bookId, title, author, category));
                    System.out.println("Book added successfully.");
                    break;
                case 2:
                    System.out.println("Update book feature is not implemented yet.");
                    break;
                case 3:
                    System.out.println("Delete book feature is not implemented yet.");
                    break;
                case 4:
                    library.viewBooks();
                    break;
                case 5:
                    adminExit = true;
                    System.out.println("Logged out.");
                    break;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }

    private static void handleUserActions(Library library, User user, Scanner scanner) {
        boolean userExit = false;

        while (!userExit) {
            System.out.println("1. View Books\n2. Issue Book\n3. Return Book\n4. Logout");
            System.out.print("Choose an option: ");
            int option = scanner.nextInt();

            switch (option) {
                case 1:
                    library.viewBooks();
                    break;
                case 2:
                    System.out.print("Enter book ID to issue: ");
                    String bookId = scanner.next();
                    library.issueBook(bookId, user);
                    break;
                case 3:
                    System.out.print("Enter book ID to return: ");
                    String returnBookId = scanner.next();
                    library.returnBook(returnBookId);
                    break;
                case 4:
                    userExit = true;
                    System.out.println("Logged out.");
                    break;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }
}
