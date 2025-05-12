package com.company.main;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import com.company.exception.*;
import com.company.util.DBConnUtil;

public class Main {

    static {
        try {
            // Load the JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("Error loading JDBC driver: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            try {
                displayMenu();
                choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                switch (choice) {
                    case 1:
                        displayPetListings();
                        break;
                    case 2:
                        recordCashDonation();
                        break;
                    case 3:
                        manageAdoptionEvents();
                        break;
                    case 4:
                        addPetToDatabase();
                        break;
                    case 5:
                        adoptPet();
                        break;
                    case 6:
                    	displayAdoptedUsers();  // <== Added
                        break;
                    case 7:
                        
                        System.out.println("Exiting");
                        break;
                    default:
                        System.out.println("Invalid choice. Please choose a valid task.");
                }

            } catch (Exception e) {
                System.err.println("Error: " + e.getMessage());
                choice = 0;  // Keeps the loop running
            }
        } while (choice != 7);
    }

   

	private static void displayMenu() {
        System.out.println("Choose a task:");
        System.out.println("1. Display Pet Listings");
        System.out.println("2. Record Cash Donation");
        System.out.println("3. Manage Adoption Events");
        System.out.println("4. Add Pet to database");
        System.out.println("5. Adopt Pet");
        System.out.println("6. Adpoted Users");
        System.out.println("7. Exit");
    }

    private static void displayPetListings() throws SQLException {
        try (Connection conn = DBConnUtil.getConnection(); 
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM pets WHERE AvailableForAdoption = true");
             ResultSet rs = stmt.executeQuery()) {
            
            System.out.println("Available Pets:");
            while (rs.next()) {
                System.out.println(rs.getString("Name") + " - " + rs.getInt("Age") + " years old, " 
                                   + rs.getString("Breed") + ", Type: " + rs.getString("Type"));
            }
        }
    }

    private static void recordCashDonation() throws SQLException, InsufficientFundsException {
        try (Connection conn = DBConnUtil.getConnection()) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter donor name:");
            String donorName = scanner.nextLine();
            System.out.println("Enter donation amount:");
            double amount = scanner.nextDouble();
            scanner.nextLine();  // Consume newline

            if (amount < 10) {
                throw new InsufficientFundsException(amount);
            }

            System.out.println("Enter donation type:");
            String donationType = scanner.nextLine();
            System.out.println("Enter donation item:");
            String donationItem = scanner.nextLine();

            String query = "INSERT INTO Donations (DonorName, DonationAmount, DonationType, DonationItem, DonationDate) " 
                         + "VALUES (?, ?, ?, ?, NOW())";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, donorName);
                stmt.setDouble(2, amount);
                stmt.setString(3, donationType);
                stmt.setString(4, donationItem);
                stmt.executeUpdate();
                System.out.println("Cash donation recorded successfully");
            }
        }
    }

    private static void manageAdoptionEvents() throws SQLException {
        try (Connection conn = DBConnUtil.getConnection()) {
            // Retrieving upcoming adoption events
            String eventsQuery = "SELECT * FROM AdoptionEvents";
            try (PreparedStatement eventsStmt = conn.prepareStatement(eventsQuery);
                 ResultSet eventsRs = eventsStmt.executeQuery()) {
                
                System.out.println("Upcoming Adoption Events:");
                while (eventsRs.next()) {
                    System.out.println("Event ID: " + eventsRs.getInt("EventID"));
                    System.out.println("Event Name: " + eventsRs.getString("EventName"));
                    System.out.println("Event Date: " + eventsRs.getDate("EventDate"));
                    System.out.println("Event Location: " + eventsRs.getString("Location"));
                }

                // Registering for an event
                Scanner scanner = new Scanner(System.in);
                System.out.println("Enter event ID to register:");
                int eventId = scanner.nextInt();
                scanner.nextLine();  // Consume newline after int input

                System.out.println("Enter your name:");
                String participantName = scanner.nextLine();
                System.out.println("Enter participant type (e.g., adopter, volunteer):");
                String participantType = scanner.nextLine();

                // Register the participant for the selected event
                String registerQuery = "INSERT INTO Participants (EventID, ParticipantName, ParticipantsType) VALUES (?, ?, ?)";
                try (PreparedStatement registerStmt = conn.prepareStatement(registerQuery)) {
                    registerStmt.setInt(1, eventId);
                    registerStmt.setString(2, participantName);
                    registerStmt.setString(3, participantType);
                    registerStmt.executeUpdate();
                    System.out.println("Registration for the event successful");
                }
            }
        }
    }


    private static void addPetToDatabase() throws SQLException, InvalidPetAgeException, NullReferenceException {
        try (Connection conn = DBConnUtil.getConnection()) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter pet name:");
            String petName = scanner.nextLine();
            if (petName == null || petName.trim().isEmpty()) {
                throw new NullReferenceException(petName);
            }

            System.out.println("Enter pet age:");
            int petAge = scanner.nextInt();
            scanner.nextLine();  // Consume newline
            if (petAge <= 0) {
                throw new InvalidPetAgeException(petAge);
            }

            System.out.println("Enter pet breed:");
            String petBreed = scanner.nextLine();
            System.out.println("Enter pet type (e.g., dog, cat, bird):");
            String petType = scanner.nextLine();

            String query = "INSERT INTO pets (Name, Age, Breed, Type, AvailableForAdoption) VALUES (?, ?, ?, ?, true)";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, petName);
                stmt.setInt(2, petAge);
                stmt.setString(3, petBreed);
                stmt.setString(4, petType);
                stmt.executeUpdate();
                System.out.println("Pet added to the database successfully");
            }
        }
    }

    private static void adoptPet() throws SQLException, AdpotionException {
        try (Connection conn = DBConnUtil.getConnection()) {
            Scanner scanner = new Scanner(System.in);

            // Get pet name from user
            System.out.println("Enter pet name:");
            String petName = scanner.nextLine();

            // Check if pet is available
            String petQuery = "SELECT petID, ShelterID FROM pets WHERE Name = ? AND AvailableForAdoption = true";
            int petID = -1;
            int shelterID = -1;

            try (PreparedStatement petStmt = conn.prepareStatement(petQuery)) {
                petStmt.setString(1, petName);
                try (ResultSet rs = petStmt.executeQuery()) {
                    if (rs.next()) {
                        petID = rs.getInt("petID");
                        shelterID = rs.getInt("ShelterID");
                    } else {
                        throw new AdpotionException(petName);
                    }
                }
            }

            // Get adopter details
            System.out.println("Enter your name:");
            String adopterName = scanner.nextLine();
            System.out.println("Enter your contact info (email/phone):");
            String contactInfo = scanner.nextLine();

            // Check if user exists, else insert new
            String userQuery = "SELECT UserID FROM Users WHERE UserName = ?";
            int userID = -1;

            try (PreparedStatement userStmt = conn.prepareStatement(userQuery)) {
                userStmt.setString(1, adopterName);
                try (ResultSet rs = userStmt.executeQuery()) {
                    if (rs.next()) {
                        userID = rs.getInt("UserID");
                    } else {
                        // Insert new user
                        String insertUser = "INSERT INTO Users (UserName, ContactInfo) VALUES (?, ?)";
                        try (PreparedStatement insertStmt = conn.prepareStatement(insertUser, PreparedStatement.RETURN_GENERATED_KEYS)) {
                            insertStmt.setString(1, adopterName);
                            insertStmt.setString(2, contactInfo);
                            insertStmt.executeUpdate();
                            try (ResultSet generatedKeys = insertStmt.getGeneratedKeys()) {
                                if (generatedKeys.next()) {
                                    userID = generatedKeys.getInt(1);
                                }
                            }
                        }
                    }
                }
            }

            // Insert into Adoption table
            String adoptionQuery = "INSERT INTO Adoption (PetID, UserID, AdoptionDate, ShelterID) VALUES (?, ?, NOW(), ?)";
            try (PreparedStatement adoptStmt = conn.prepareStatement(adoptionQuery)) {
                adoptStmt.setInt(1, petID);
                adoptStmt.setInt(2, userID);
                adoptStmt.setInt(3, shelterID);
                adoptStmt.executeUpdate();
                System.out.println("Congratulations! You have successfully adopted " + petName + ".");
            }

            // Update pet availability to false
            String updatePet = "UPDATE pets SET AvailableForAdoption = false WHERE petID = ?";
            try (PreparedStatement updateStmt = conn.prepareStatement(updatePet)) {
                updateStmt.setInt(1, petID);
                updateStmt.executeUpdate();
            }

        } catch (SQLException e) {
            System.err.println("SQL Error: " + e.getMessage());
        }
    }

    private static void displayAdoptedUsers() throws SQLException {
        try (Connection conn = DBConnUtil.getConnection()) {
            String query = "SELECT u.UserName, u.ContactInfo, p.Name AS PetName, a.AdoptionDate " +
                           "FROM Users u " +
                           "JOIN Adoption a ON u.UserID = a.UserID " +
                           "JOIN Pets p ON a.PetID = p.PetID " +
                           "ORDER BY a.AdoptionDate DESC";

            try (PreparedStatement stmt = conn.prepareStatement(query);
                 ResultSet rs = stmt.executeQuery()) {

                System.out.println("Users Who Adopted Pets:");
                System.out.println("------------------------------------");
                while (rs.next()) {
                    String userName = rs.getString("UserName");
                    String contactInfo = rs.getString("ContactInfo");
                    String petName = rs.getString("PetName");
                    String adoptionDate = rs.getString("AdoptionDate");

                    System.out.println("User Name: " + userName);
                    System.out.println("Contact Info: " + contactInfo);
                    System.out.println("Adopted Pet: " + petName);
                    System.out.println("Adoption Date: " + adoptionDate);
                    System.out.println("------------------------------------");
                }
            }
        }
    }

    }

   
    

