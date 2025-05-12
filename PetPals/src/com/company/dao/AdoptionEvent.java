package com.company.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.company.exception.NullReferenceException;
import com.company.util.DBConnUtil;

public class AdoptionEvent {

    public void hostEvent(int eventId) {
        System.out.println("Hosting adoption event with Event ID: " + eventId);

        // Further logic for hosting the event (e.g., checking if the event exists, etc.)

        System.out.println("Adoption event successfully hosted!");
    }

    public void registerParticipant(int eventId, String participantName, String participantType) throws NullReferenceException {
        try (Connection conn = DBConnUtil.getConnection()) {
            // Check for null references in participant
            if (participantName == null || participantName.trim().isEmpty()) {
                throw new NullReferenceException("Participant name cannot be null or empty.");
            }
            if (participantType == null || participantType.trim().isEmpty()) {
                throw new NullReferenceException("Participant type cannot be null or empty.");
            }

            String query = "INSERT INTO Participants (EventID, ParticipantName, ParticipantsType) VALUES (?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setInt(1, eventId);
                stmt.setString(2, participantName);
                stmt.setString(3, participantType);
                stmt.executeUpdate();
                System.out.println("Participant registered successfully.");
            }
        } catch (SQLException e) {
            System.err.println("Error while registering participant: " + e.getMessage());
        }
    }
}
