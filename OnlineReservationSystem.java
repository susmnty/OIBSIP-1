// Online Reservation System

import java.util.*;

public class OnlineReservationSystem {                                  
    static class User {
        private String username;
        private String password;

        public User(String username, String password) {
            this.username = username;
            this.password = password;
        }

        public String getUsername() {
            return username;
        }

        public String getPassword() {
            return password;
        }
    }

    static class TrainReservation {
        private int pnr;
        private String trainNumber;
        private String trainName;
        private String classType;
        private String dateOfJourney;
        private String fromPlace;
        private String toPlace;

        public TrainReservation(int pnr, String trainNumber, String trainName, String classType, String dateOfJourney, String fromPlace, String toPlace) {
            this.pnr = pnr;
            this.trainNumber = trainNumber;
            this.trainName = trainName;
            this.classType = classType;
            this.dateOfJourney = dateOfJourney;
            this.fromPlace = fromPlace;
            this.toPlace = toPlace;
        }

        public int getPnr() {
            return pnr;
        }

        @Override
        public String toString() {
            return "PNR: " + pnr + ", Train Number: " + trainNumber + ", Train Name: " + trainName + ", Class Type: " + classType + ", Date of Journey: " + dateOfJourney +  ", From: " + fromPlace + ", To: " + toPlace;
        }
    }

    static class EventReservation {
        private int eventId;
        private String eventName;
        private String date;
        private String venue;

        public EventReservation(int eventId, String eventName, String date, String venue) {
            this.eventId = eventId;
            this.eventName = eventName;
            this.date = date;
            this.venue = venue;
        }

        public int getEventId() {
            return eventId;
        }

        @Override
        public String toString() {
            return "Event ID: " + eventId + ", Event Name: " + eventName + ", Date: " + date + ", Venue: " + venue;
        }
    }

    private static List<User> users = new ArrayList<>();
    private static List<TrainReservation> trainReservations = new ArrayList<>();
    private static List<EventReservation> eventReservations = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        users.add(new User("Sonu", "1234"));

        while (true) {
            System.out.println("1. Login\n2. Exit");
            int choice = scanner.nextInt();

            if (choice == 1) {
                if (login()) {
                    while (true) {
                        System.out.println("1. Make Train Reservation\n2. Cancel Train Reservation");
                        System.out.println("3. Make Event Reservation\n4. Cancel Event Reservation\n5. Logout");
                        int userChoice = scanner.nextInt();

                        if (userChoice == 1) {
                            makeTrainReservation();
                        } else if (userChoice == 2) {
                            cancelTrainReservation();
                        } else if (userChoice == 3) {
                            makeEventReservation();
                        } else if (userChoice == 4) {
                            cancelEventReservation();
                        } else if (userChoice == 5) {
                            break;
                        }
                    }
                }
            } else if (choice == 2) {
                System.out.println("Exiting");
                break;
            }
        }
    }

    private static boolean login() {
        System.out.print("Enter username: ");
        String username = scanner.next();
        System.out.print("Enter password: ");
        String password = scanner.next();

        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                System.out.println("Login successful!");
                return true;
            }
        }
        System.out.println("Invalid username or password.");
        return false;
    }

    private static void makeTrainReservation() {
        System.out.print("Enter PNR: ");
        int pnr = scanner.nextInt();
        System.out.print("Enter Train Number: ");
        String trainNumber = scanner.next();
        System.out.print("Enter Train Name: ");
        String trainName = scanner.next();
        System.out.print("Enter Class Type: ");
        String classType = scanner.next();
        System.out.print("Enter Date of Journey: ");
        String dateOfJourney = scanner.next();
        System.out.print("Enter From (Place): ");
        String fromPlace = scanner.next();
        System.out.print("Enter To (Destination): ");
        String toPlace = scanner.next();

        TrainReservation reservation = new TrainReservation(pnr, trainNumber, trainName, classType, dateOfJourney, fromPlace, toPlace);
        trainReservations.add(reservation);
        System.out.println("Train reservation is made successfully!");
    }

    private static void cancelTrainReservation() {
        System.out.print("Enter PNR: ");
        int pnr = scanner.nextInt();

        TrainReservation reservationToCancel = null;
        for (TrainReservation reservation : trainReservations) {
            if (reservation.getPnr() == pnr) {
                reservationToCancel = reservation;
                break;
            }
        }

        if (reservationToCancel != null) {
            System.out.println("Reservation Details:");
            System.out.println(reservationToCancel);
            System.out.print("Type 'OK' to confirm cancellation: ");
            String confirmation = scanner.next();

            if ("OK".equalsIgnoreCase(confirmation)) {
                trainReservations.remove(reservationToCancel);
                System.out.println("Train reservation cancelled successfully!");
            } else {
                System.out.println("Cancellation aborted.");
            }
        } else {
            System.out.println("No train reservation found with the given PNR.");
        }
    }

    private static void makeEventReservation() {
        System.out.print("Enter Event ID: ");
        int eventId = scanner.nextInt();
        System.out.print("Enter Event Name: ");
        String eventName = scanner.next();
        System.out.print("Enter Date: ");
        String date = scanner.next();
        System.out.print("Enter Venue: ");
        String venue = scanner.next();

        EventReservation reservation = new EventReservation(eventId, eventName, date, venue);
        eventReservations.add(reservation);
        System.out.println("Event reservation made successfully!");
    }

    private static void cancelEventReservation() {
        System.out.print("Enter Event ID: ");
        int eventId = scanner.nextInt();

        EventReservation reservationToCancel = null;
        for (EventReservation reservation : eventReservations) {
            if (reservation.getEventId() == eventId) {
                reservationToCancel = reservation;
                break;
            }
        }

        if (reservationToCancel != null) {
            System.out.println("Reservation Details:");
            System.out.println(reservationToCancel);
            System.out.print("Type 'OK' to confirm cancellation: ");
            String confirmation = scanner.next();

            if ("OK".equalsIgnoreCase(confirmation)) {
                eventReservations.remove(reservationToCancel);
                System.out.println("Event reservation cancelled successfully!");
            } else {
                System.out.println("Cancellation aborted.");
            }
        } else {
            System.out.println("No event reservation found with the given Event ID.");
        }
    }
}
