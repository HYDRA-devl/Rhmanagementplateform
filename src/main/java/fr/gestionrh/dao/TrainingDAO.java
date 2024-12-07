 
package fr.gestionrh.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.sql.Statement;            // Add this

import fr.gestionrh.model.Training;

public class TrainingDAO {
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/gestionrh", "root", "root");
    }

    public void createTraining(Training training) throws SQLException {
        String sql = "INSERT INTO trainings (titre, description, date_debut, date_fin, duree_heures, " +
                    "formateur, capacite_max, obligatoire, statut) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            setTrainingParameters(stmt, training);
            stmt.executeUpdate();
            
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                training.setId(rs.getInt(1));
            }
        }
    }

    private void setTrainingParameters(PreparedStatement stmt, Training training) throws SQLException {
        stmt.setString(1, training.getTitre());
        stmt.setString(2, training.getDescription());
        stmt.setDate(3, new java.sql.Date(training.getDateDebut().getTime()));
        stmt.setDate(4, new java.sql.Date(training.getDateFin().getTime()));
        stmt.setInt(5, training.getDureeHeures());
        stmt.setString(6, training.getFormateur());
        stmt.setInt(7, training.getCapaciteMax());
        stmt.setBoolean(8, training.isObligatoire());
        stmt.setString(9, training.getStatut());
    }

    public void addParticipant(int trainingId, int userId) throws SQLException {
        String sql = "INSERT INTO training_participants (training_id, user_id) VALUES (?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, trainingId);
            stmt.setInt(2, userId);
            stmt.executeUpdate();
        }
    }

    public void removeParticipant(int trainingId, int userId) throws SQLException {
        String sql = "DELETE FROM training_participants WHERE training_id = ? AND user_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, trainingId);
            stmt.setInt(2, userId);
            stmt.executeUpdate();
        }
    }
}