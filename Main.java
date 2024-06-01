// Online Examination                   user1, password1

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

class User {
    private String userId;
    private String password;
    private String profileInfo;
    private HashMap<Integer, String> answers;

    public User(String userId, String password, String profileInfo) {
        this.userId = userId;
        this.password = password;
        this.profileInfo = profileInfo;
        this.answers = new HashMap<>();
    }

    public String getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getProfileInfo() {
        return profileInfo;
    }

    public void setProfileInfo(String profileInfo) {
        this.profileInfo = profileInfo;
    }

    public void setAnswer(int questionId, String answer) {
        answers.put(questionId, answer);
    }

    public String getAnswer(int questionId) {
        return answers.getOrDefault(questionId, "");
    }
}

class MCQ {
    private int id;
    private String question;
    private List<String> options;
    private String correctAnswer;

    public MCQ(int id, String question, List<String> options, String correctAnswer) {
        this.id = id;
        this.question = question;
        this.options = options;
        this.correctAnswer = correctAnswer;
    }

    public int getId() {
        return id;
    }

    public String getQuestion() {
        return question;
    }

    public List<String> getOptions() {
        return options;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Q").append(id).append(": ").append(question).append("\n");
        for (int i = 0; i < options.size(); i++) {
            sb.append((char) ('A' + i)).append(". ").append(options.get(i)).append("\n");
        }
        return sb.toString();
    }
}

class Quiz {
    private List<MCQ> questions;
    private User user;
    private Timer timer;
    private boolean timeUp;

    public Quiz(List<MCQ> questions, User user) {
        this.questions = questions;
        this.user = user;
        this.timeUp = false;
    }

    public void start(int timeLimitInSeconds) {
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                timeUp = true;
                System.out.println("Time's up! The quiz will be auto-submitted.");
                displayResults();
                System.exit(0);
            }
        }, timeLimitInSeconds * 1000);

        Scanner scanner = new Scanner(System.in);
        for (MCQ question : questions) {
            if (timeUp) break;
            System.out.println(question);
            System.out.print("Your answer: ");
            String answer = scanner.next();
            user.setAnswer(question.getId(), answer.toUpperCase());
        }

        if (!timeUp) {
            timer.cancel();
            displayResults();
        }
    }

    private void displayResults() {
        int score = 0;
        System.out.println("\nResults:");
        for (MCQ question : questions) {
            String userAnswer = user.getAnswer(question.getId());
            System.out.println(question);
            System.out.println("Your answer: " + userAnswer);
            System.out.println("Correct answer: " + question.getCorrectAnswer());
            if (userAnswer.equals(question.getCorrectAnswer())) {
                score++;
            }
        }
        System.out.println("Your score: " + score + "/" + questions.size());
    }
}

class Session {
    private HashMap<String, User> users;
    private User loggedInUser;

    public Session() {
        users = new HashMap<>();
        users.put("Sonu", new User("Sonu", "1234", "User One Profile"));
        users.put("Monu", new User("Monu", "2345", "User Two Profile"));
    }

    public boolean login(String userId, String password) {
        if (users.containsKey(userId) && users.get(userId).getPassword().equals(password)) {
            loggedInUser = users.get(userId);
            System.out.println("Login successful!");
            return true;
        }
        System.out.println("Invalid user ID or password.");
        return false;
    }

    public void logout() {
        loggedInUser = null;
        System.out.println("Logged out successfully.");
    }

    public User getLoggedInUser() {
        return loggedInUser;
    }

    public void updateProfile(String newProfileInfo) {
        if (loggedInUser != null) {
            loggedInUser.setProfileInfo(newProfileInfo);
            System.out.println("Profile updated successfully.");
        } else {
            System.out.println("No user is logged in.");
        }
    }

    public void updatePassword(String newPassword) {
        if (loggedInUser != null) {
            loggedInUser.setPassword(newPassword);
            System.out.println("Password updated successfully.");
        } else {
            System.out.println("No user is logged in.");
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Session session = new Session();
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            System.out.println("1. Login\n2. Update Profile\n3. Update Password\n4. Take Quiz\n5. Logout\n6. Quit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter User ID: ");
                    String userId = scanner.next();
                    System.out.print("Enter Password: ");
                    String password = scanner.next();
                    session.login(userId, password);
                    break;
                case 2:
                    System.out.print("Enter new profile information: ");
                    scanner.nextLine(); 
                    String profileInfo = scanner.nextLine();
                    session.updateProfile(profileInfo);
                    break;
                case 3:
                    System.out.print("Enter new password: ");
                    String newPassword = scanner.next();
                    session.updatePassword(newPassword);
                    break;
                case 4:
                    if (session.getLoggedInUser() != null) {
                        ArrayList<MCQ> questions = new ArrayList<>();
                        questions.add(new MCQ(1, "What is the capital of France?", new ArrayList<>(List.of("Berlin", "Madrid", "Paris", "Rome")), "C"));
                        questions.add(new MCQ(2, "What is 2 + 2?", new ArrayList<>(List.of("3", "4", "5", "6")), "B"));
                        questions.add(new MCQ(3, "What is the color of the sky?", new ArrayList<>(List.of("Blue", "Green", "Yellow", "Red")), "A"));
                        questions.add(new MCQ(4, "What is the capital of India?", new ArrayList<>(List.of("Delhi", "Tamil Nadu", "Goa", "Hyderabad")), "A"));
                        questions.add(new MCQ(5, "What is the game played by the most ?", new ArrayList<>(List.of("Pubg", "Freefire", "COD", "Fortnite")), "A"));

                        Quiz quiz = new Quiz(questions, session.getLoggedInUser());
                        quiz.start(30); 
                    } else {
                        System.out.println("You need to login first.");
                    }
                    break;
                case 5:
                    session.logout();
                    break;
                case 6:
                    exit = true;
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }

        scanner.close();
    }
}
