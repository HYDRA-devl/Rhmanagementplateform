package fr.gestionrh.dao;

import fr.gestionrh.model.Employee;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAO {
    private final Connection connection;
    
    public EmployeeDAO(Connection connection) {
        this.connection = connection;
    }
    
    // Créer un nouvel employé
    public Employee create(Employee employee) throws SQLException {
        String sql = "INSERT INTO Employe (nom, salaire, solde_conge, evaluation_performance) VALUES (?, ?, ?, ?)";
        
        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            setEmployeeParameters(statement, employee);
            
            int affectedRows = statement.executeUpdate();
            
            if (affectedRows == 0) {
                throw new SQLException("La création de l'employé a échoué");
            }
            
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    employee.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("La création a échoué, aucun ID obtenu.");
                }
            }
        }
        return employee;
    }
    
    // Trouver un employé par son ID
    public Employee findById(int id) throws SQLException {
        String sql = "SELECT * FROM Employe WHERE id = ?";
        
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return extractEmployeeFromResultSet(rs);
                }
            }
        }
        return null;
    }
    
    // Récupérer tous les employés
    public List<Employee> findAll() throws SQLException {
        List<Employee> employees = new ArrayList<>();
        String sql = "SELECT * FROM Employe ORDER BY nom";
        
        try (Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(sql)) {
            
            while (rs.next()) {
                employees.add(extractEmployeeFromResultSet(rs));
            }
        }
        
        return employees;
    }
    
    // Mettre à jour un employé
    public boolean update(Employee employee) throws SQLException {
        String sql = "UPDATE Employe SET nom = ?, salaire = ?, solde_conge = ?, evaluation_performance = ? WHERE id = ?";
        
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            setEmployeeParameters(statement, employee);
            statement.setInt(5, employee.getId());
            
            int affectedRows = statement.executeUpdate();
            return affectedRows > 0;
        }
    }
    
    // Supprimer un employé
    public boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM Employe WHERE id = ?";
        
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            
            int affectedRows = statement.executeUpdate();
            return affectedRows > 0;
        }
    }
    
    // Rechercher des employés par nom
    public List<Employee> findByName(String name) throws SQLException {
        List<Employee> employees = new ArrayList<>();
        String sql = "SELECT * FROM Employe WHERE nom LIKE ? ORDER BY nom";
        
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, "%" + name + "%");
            
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    employees.add(extractEmployeeFromResultSet(rs));
                }
            }
        }
        
        return employees;
    }
    
    // Trouver les employés par département
    public List<Employee> findByDepartment(int departmentId) throws SQLException {
        List<Employee> employees = new ArrayList<>();
        String sql = "SELECT * FROM Employe WHERE departement_id = ? ORDER BY nom";
        
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, departmentId);
            
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    employees.add(extractEmployeeFromResultSet(rs));
                }
            }
        }
        
        return employees;
    }
    
    // Mettre à jour le salaire
    public boolean updateSalary(int employeeId, double newSalary) throws SQLException {
        String sql = "UPDATE Employe SET salaire = ? WHERE id = ?";
        
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setDouble(1, newSalary);
            statement.setInt(2, employeeId);
            
            int affectedRows = statement.executeUpdate();
            return affectedRows > 0;
        }
    }
    
    // Mettre à jour le solde de congés
    public boolean updateLeaveBalance(int employeeId, int newBalance) throws SQLException {
        String sql = "UPDATE Employe SET solde_conge = ? WHERE id = ?";
        
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, newBalance);
            statement.setInt(2, employeeId);
            
            int affectedRows = statement.executeUpdate();
            return affectedRows > 0;
        }
    }
    
    // Méthode utilitaire pour extraire un employé d'un ResultSet
    private Employee extractEmployeeFromResultSet(ResultSet rs) throws SQLException {
        Employee employee = new Employee();
        employee.setId(rs.getInt("id"));
        employee.setNom(rs.getString("nom"));
        employee.setSalaire(rs.getDouble("salaire"));
        employee.setSoldeConge(rs.getInt("solde_conge"));
        employee.setEvaluationPerformance(rs.getFloat("evaluation_performance"));
        return employee;
    }
    
    // Méthode utilitaire pour définir les paramètres d'un employé dans un PreparedStatement
    private void setEmployeeParameters(PreparedStatement statement, Employee employee) throws SQLException {
        statement.setString(1, employee.getNom());
        statement.setDouble(2, employee.getSalaire());
        statement.setInt(3, employee.getSoldeConge());
        statement.setFloat(4, employee.getEvaluationPerformance());
    }
    
    // Compter le nombre total d'employés
    public int countAll() throws SQLException {
        String sql = "SELECT COUNT(*) FROM Employe";
        
        try (Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(sql)) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return 0;
    }
    
    // Calculer la moyenne des salaires
    public double getAverageSalary() throws SQLException {
        String sql = "SELECT AVG(salaire) FROM Employe";
        
        try (Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(sql)) {
            if (rs.next()) {
                return rs.getDouble(1);
            }
        }
        return 0.0;
    }
}