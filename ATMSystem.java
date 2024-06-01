// ATM Interface       user1, 1234

import java.util.ArrayList;
import java.util.Scanner;

class User {
    private String userId;
    private String pin;
    private Account account;

    public User(String userId, String pin, double initialBalance) {
        this.userId = userId;
        this.pin = pin;
        this.account = new Account(initialBalance);
    }

    public String getUserId() {
        return userId;
    }

    public String getPin() {
        return pin;
    }

    public Account getAccount() {
        return account;
    }
}

class Account {
    private double balance;
    private ArrayList<Transaction> transactions;

    public Account(double initialBalance) {
        this.balance = initialBalance;
        this.transactions = new ArrayList<>();
    }

    public double getBalance() {
        return balance;
    }

    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
        balance += transaction.getAmount();
    }

    public ArrayList<Transaction> getTransactions() {
        return transactions;
    }
}

class Transaction {
    private String type;
    private double amount;

    public Transaction(String type, double amount) {
        this.type = type;
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return type + ": " + (type.equals("Deposit") ? "+" : "") + amount;
    }
}

class ATM {
    private User user;

    public ATM(User user) {
        this.user = user;
    }

    public void showTransactionHistory() {
        System.out.println("Transaction History:");
        for (Transaction transaction : user.getAccount().getTransactions()) {
            System.out.println(transaction);
        }
    }

    public void withdraw(double amount) {
        if (amount > 0 && amount <= user.getAccount().getBalance()) {
            user.getAccount().addTransaction(new Transaction("Withdraw", -amount));
            System.out.println("Withdrawn: " + amount);
        } else {
            System.out.println("Insufficient balance or invalid amount.");
        }
    }

    public void deposit(double amount) {
        if (amount > 0) {
            user.getAccount().addTransaction(new Transaction("Deposit", amount));
            System.out.println("Deposited: " + amount);
        } else {
            System.out.println("Invalid amount.");
        }
    }

    public void transfer(User recipient, double amount) {
        if (amount > 0 && amount <= user.getAccount().getBalance()) {
            user.getAccount().addTransaction(new Transaction("Transfer Out", -amount));
            recipient.getAccount().addTransaction(new Transaction("Transfer In", amount));
            System.out.println("Transferred: " + amount + " to " + recipient.getUserId());
        } else {
            System.out.println("Insufficient balance or invalid amount.");
        }
    }
}

public class ATMSystem {
    private static ArrayList<User> users = new ArrayList<>();

    public static void main(String[] args) {
        users.add(new User("user1", "1234", 1000));
        users.add(new User("user2", "5678", 1500));

        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter User ID: ");
        String userId = scanner.next();

        System.out.print("Enter PIN: ");
        String pin = scanner.next();

        User currentUser = authenticate(userId, pin);

        if (currentUser != null) {
            ATM atm = new ATM(currentUser);
            boolean quit = false;

            while (!quit) {
                System.out.println("1. Transaction History\n2. Withdraw\n3. Deposit\n4. Transfer\n5. Quit");
                System.out.print("Choose an option: ");
                int choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        atm.showTransactionHistory();
                        break;
                    case 2:
                        System.out.print("Enter amount to withdraw: ");
                        double withdrawAmount = scanner.nextDouble();
                        atm.withdraw(withdrawAmount);
                        break;
                    case 3:
                        System.out.print("Enter amount to deposit: ");
                        double depositAmount = scanner.nextDouble();
                        atm.deposit(depositAmount);
                        break;
                    case 4:
                        System.out.print("Enter recipient User ID: ");
                        String recipientId = scanner.next();
                        System.out.print("Enter amount to transfer: ");
                        double transferAmount = scanner.nextDouble();
                        User recipient = findUserById(recipientId);
                        if (recipient != null) {
                            atm.transfer(recipient, transferAmount);
                        } else {
                            System.out.println("Recipient not found.");
                        }
                        break;
                    case 5:
                        quit = true;
                        System.out.println("Quitting...");
                        break;
                    default:
                        System.out.println("Invalid option. Try again.");
                }
            }
        } else {
            System.out.println("Authentication failed.");
        }

        scanner.close();
    }

    private static User authenticate(String userId, String pin) {
        for (User user : users) {
            if (user.getUserId().equals(userId) && user.getPin().equals(pin)) {
                return user;
            }
        }
        return null;
    }

    private static User findUserById(String userId) {
        for (User user : users) {
            if (user.getUserId().equals(userId)) {
                return user;
            }
        }
        return null;
    }
}
