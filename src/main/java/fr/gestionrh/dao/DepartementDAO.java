 
package fr.gestionrh.dao;

import fr.gestionrh.model.Departement;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DepartementDAO {
    private final Connection connection;
    
    public DepartementDAO(Connection connection) {
        this.connection = connection;
    }
    
    // Créer un nouveau département
    public Departement create(Departement departement) throws SQLException {
        String sql = "INSERT INTO Departement (nom) VALUES (?)";
        
        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, departement.getNom());
            
            int affectedRows = statement.executeUpdate();
            
            if (affectedRows == 0) {
                throw new SQLException("La création du département a échoué");
            }
            
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    departement.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("La création a échoué, aucun ID obtenu.");
                }
            }
        }
        return departement;
    }
    
    // Trouver un département par son ID
    public Departement findById(int id) throws SQLException {
        String sql = "SELECT * FROM Departement WHERE id = ?";
        
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return extractDepartementFromResultSet(rs);
                }
            }
        }
        return null;
    }
    
    // Récupérer tous les départements
    public List<Departement> findAll() throws SQLException {
        List<Departement> departements = new ArrayList<>();
        String sql = "SELECT * FROM Departement ORDER BY nom";
        
        try (Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(sql)) {
            
            while (rs.next()) {
                departements.add(extractDepartementFromResultSet(rs));
            }
        }
        
        return departements;
    }
    
    // Mettre à jour un département
    public boolean update(Departement departement) throws SQLException {
        String sql = "UPDATE Departement SET nom = ? WHERE id = ?";
        
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, departement.getNom());
            statement.setInt(2, departement.getId());
            
            int affectedRows = statement.executeUpdate();
            return affectedRows > 0;
        }
    }
    
    // Supprimer un département
    public boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM Departement WHERE id = ?";
        
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            
            int affectedRows = statement.executeUpdate();
            return affectedRows > 0;
        }
    }
    
    // Trouver un département par son nom
    public Departement findByName(String nom) throws SQLException {
        String sql = "SELECT * FROM Departement WHERE nom = ?";
        
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, nom);
            
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return extractDepartementFromResultSet(rs);
                }
            }
        }
        return null;
    }
    
    // Obtenir le nombre d'employés dans un département
    public int getEmployeeCount(int departementId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM Employe WHERE departement_id = ?";
        
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, departementId);
            
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        return 0;
    }
    
    // Obtenir la masse salariale d'un département
    public double getTotalSalary(int departementId) throws SQLException {
        String sql = "SELECT SUM(salaire) FROM Employe WHERE departement_id = ?";
        
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, departementId);
            
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble(1);
                }
            }
        }
        return 0.0;
    }
    
    // Vérifier si un département existe
    public boolean exists(int id) throws SQLException {
        String sql = "SELECT COUNT(*) FROM Departement WHERE id = ?";
        
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }
    
    // Méthode utilitaire pour extraire un département d'un ResultSet
    private Departement extractDepartementFromResultSet(ResultSet rs) throws SQLException {
        Departement departement = new Departement();
        departement.setId(rs.getInt("id"));
        departement.setNom(rs.getString("nom"));
        return departement;
    }
    
    // Obtenir les statistiques du département
    public DepartementStats getStats(int departementId) throws SQLException {
        String sql = "SELECT COUNT(*) as employee_count, " +
                    "AVG(salaire) as avg_salary, " +
                    "MIN(salaire) as min_salary, " +
                    "MAX(salaire) as max_salary, " +
                    "AVG(evaluation_performance) as avg_performance " +
                    "FROM Employe WHERE departement_id = ?";
        
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, departementId);
            
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    DepartementStats stats = new DepartementStats();
                    stats.setEmployeeCount(rs.getInt("employee_count"));
                    stats.setAverageSalary(rs.getDouble("avg_salary"));
                    stats.setMinSalary(rs.getDouble("min_salary"));
                    stats.setMaxSalary(rs.getDouble("max_salary"));
                    stats.setAveragePerformance(rs.getFloat("avg_performance"));
                    return stats;
                }
            }
        }
        return null;
    }
}

// Classe interne pour les statistiques du département
class DepartementStats {
    private int employeeCount;
    private double averageSalary;
    private double minSalary;
    private double maxSalary;
    private float averagePerformance;
    
    // Getters et Setters
    public int getEmployeeCount() { return employeeCount; }
    public void setEmployeeCount(int employeeCount) { this.employeeCount = employeeCount; }
    
    public double getAverageSalary() { return averageSalary; }
    public void setAverageSalary(double averageSalary) { this.averageSalary = averageSalary; }
    
    public double getMinSalary() { return minSalary; }
    public void setMinSalary(double minSalary) { this.minSalary = minSalary; }
    
    public double getMaxSalary() { return maxSalary; }
    public void setMaxSalary(double maxSalary) { this.maxSalary = maxSalary; }
    
    public float getAveragePerformance() { return averagePerformance; }
    public void setAveragePerformance(float averagePerformance) { 
        this.averagePerformance = averagePerformance; 
    }
}