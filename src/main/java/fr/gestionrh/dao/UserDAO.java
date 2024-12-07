package fr.gestionrh.dao;

import fr.gestionrh.model.User;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    private final Connection connection;
    
    public UserDAO(Connection connection) {
        this.connection = connection;
    }
    
    // Créer un nouvel utilisateur
    public User create(User user) throws SQLException {
        // Start transaction
        connection.setAutoCommit(false);
        
        try {
            // 1. Insert into Utilisateur table first
            String userSql = "INSERT INTO utilisateur (nom, email, mot_de_passe) VALUES (?, ?, ?)";
            
            try (PreparedStatement statement = connection.prepareStatement(userSql, Statement.RETURN_GENERATED_KEYS)) {
                statement.setString(1, user.getNom());
                statement.setString(2, user.getEmail());
                statement.setString(3, user.getMotDePasse());
                System.out.println("echoue de kdkdkdkldl");

                int affectedRows = statement.executeUpdate();
                
                if (affectedRows == 0) {
                    System.out.println("echoue de creation");

                	throw new SQLException("La création de l'utilisateur a échoué");
                }
                
                // Get the generated user ID
                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        user.setId(generatedKeys.getInt(1));
                        System.out.println(user.getId());
                        System.out.println(user.getId());
                        System.out.println(user.getId());


                    } else {
                        throw new SQLException("La création a échoué, aucun ID obtenu.");
                    }
                }
            }
            
            // 2. Based on role, insert into Employee or Manager table
            System.out.println("sucessssssssssssssssssssss");

            switch (user.getRole()) {
                case User.ROLE_EMPLOYEE -> createEmployee(user);
                case User.ROLE_MANAGER -> createManager(user);
                case User.ROLE_ADMIN -> { /* No additional table needed for admin */ }
                default -> throw new SQLException("Role invalide");
            }
            
            // If everything is OK, commit the transaction
            connection.commit();
            
            return user;
            
        } catch (SQLException e) {
            // If anything goes wrong, rollback the transaction
            connection.rollback();
            throw e;
        } finally {
            // Reset auto-commit to true
            connection.setAutoCommit(true);
        }
    }

    private void createEmployee(User user) throws SQLException {
    	   String sql = "INSERT INTO Employe (nom, utilisateur_id, salaire, solde_conge, departement_id, evaluation_performance) VALUES (?, ?, ?, ?, ?, 5.0)";

    	   Integer departementId = null;
    	   if (user.getDepartement() != null && !user.getDepartement().trim().isEmpty()) {
    	       departementId = getDepartementId(user.getDepartement());
    	   }

    	   try (PreparedStatement stmt = connection.prepareStatement(sql)) {
    	       stmt.setString(1, user.getNom());
    	       stmt.setInt(2, user.getId());
    	       stmt.setDouble(3, user.getSalaire() != null ? user.getSalaire() : 0);
    	       stmt.setInt(4, user.getSoldeConge() != null ? user.getSoldeConge() : 0);
    	       
    	       if (departementId != null) {
    	           stmt.setInt(5, departementId);
    	       } else {
    	           stmt.setNull(5, java.sql.Types.INTEGER);
    	       }

    	       if (stmt.executeUpdate() == 0) {
    	           throw new SQLException("La création de l'employé a échoué");
    	       }
    	   }
    	}
    private void createManager(User user) throws SQLException {
        String sql = "INSERT INTO Manager (utilisateur_id, departement_id) VALUES (?, ?)";
        
        // First, get department ID if department is specified
        Integer departementId = null;
        if (user.getDepartement() != null && !user.getDepartement().trim().isEmpty()) {
            departementId = getDepartementId(user.getDepartement());
            System.out.println(departementId+"dep");

        }
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, user.getId());
            
            if (departementId != null) {
                stmt.setInt(2, departementId);
            } else {
                stmt.setNull(2, java.sql.Types.INTEGER);
            }
            
            if (stmt.executeUpdate() == 0) {
                throw new SQLException("La création du manager a échoué");
            }
        }
    }

    private Integer getDepartementId(String departementNom) throws SQLException {
        String sql = "SELECT id FROM Departement WHERE nom = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, departementNom);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id");
                }
                // If department doesn't exist, create it
                return createDepartement(departementNom);
            }
        }
    }

    private Integer createDepartement(String nom) throws SQLException {
        String sql = "INSERT INTO Departement (nom) VALUES (?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, nom);
            
            if (stmt.executeUpdate() == 0) {
                throw new SQLException("La création du département a échoué");
            }
            
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    throw new SQLException("La création du département a échoué, aucun ID obtenu");
                }
            }
        }
    }
    
    // Authentifier un utilisateur
    public User authenticate(String email, String motDePasse) throws SQLException {
        String sql = "SELECT * FROM Utilisateur WHERE email = ? AND mot_de_passe = ?";
        
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, email);
            statement.setString(2, motDePasse);  // En production, comparer avec hash
            
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return extractUserFromResultSet(rs);
                }
            }
        }
        return null;
    }
    
    // Trouver un utilisateur par son ID
    public User findById(int id) throws SQLException {
        String sql = "SELECT * FROM Utilisateur WHERE id = ?";
        
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return extractUserFromResultSet(rs);
                }
            }
        }
        return null;
    }
    
    
    
    public int countUsers() throws SQLException {
        String sql = "SELECT COUNT(*) FROM utilisateur";
        System.out.println("Executing query: " + sql);  // Debug log
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            System.out.println("Query executed successfully");  // Debug log

            if (rs.next()) {
                int count = rs.getInt(1);
                System.out.println("Total users count: " + count);  // Debug log
                return count;
            }
            return 0;
        }
    }

    
    public int countManagers() throws SQLException {
        String sql = "SELECT COUNT(*) FROM manager";
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
            return 0;
        }
    }
    
    public int countEmployees() throws SQLException {
        String sql = "SELECT COUNT(*) FROM employe";
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
            	 System.out.println(rs.getInt(1));
            	return rs.getInt(1);
               
            }
            return 0;
        }
    }
    
    
    // Trouver un utilisateur par email
    public User findByEmail(String email) throws SQLException {
        String sql = "SELECT * FROM Utilisateur WHERE email = ?";
        
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, email);
            
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return extractUserFromResultSet(rs);
                }
            }
        }
        return null;
    }
    
    // Récupérer tous les utilisateurs
    public List<User> findAll() throws SQLException {
        List<User> users = new ArrayList<>();
        String sql = """
            SELECT u.*,
                   d.nom as departement_nom,
                   e.id as employe_id,
                   e.salaire,
                   e.solde_conge,
                   m.id as manager_id
            FROM Utilisateur u
            LEFT JOIN Employe e ON u.id = e.utilisateur_id
            LEFT JOIN Manager m ON u.id = m.utilisateur_id
            LEFT JOIN Departement d ON e.departement_id = d.id
            ORDER BY u.nom
             LIMIT 10

        """;

        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {
            
            while (rs.next()) {
                User user = new User();
                
                // Basic user information
                user.setId(rs.getInt("id"));
                user.setNom(rs.getString("nom"));
                user.setEmail(rs.getString("email"));
                user.setMotDePasse(rs.getString("mot_de_passe"));
                user.setActif(true); // Default to active
                
                // Determine role and set additional information
                if (rs.getInt("manager_id") > 0) {
                    user.setRole(User.ROLE_MANAGER);
                } else if (rs.getInt("employe_id") > 0) {
                    user.setRole(User.ROLE_EMPLOYEE);
                    // Set employee specific information
                    Double salaire = rs.getDouble("salaire");
                    if (!rs.wasNull()) {
                        user.setSalaire(salaire);
                    }
                    user.setSoldeConge(rs.getInt("solde_conge"));
                } else {
                    user.setRole(User.ROLE_ADMIN);
                }

                // Set department if exists
                String departement = rs.getString("departement_nom");
                if (departement != null) {
                    user.setDepartement(departement);
                }

                users.add(user);
            }
        }
        return users;
    }

    // Rest of your UserDAO methods remain the same...
    
    // Helper method to safely get date
    private Date getDateSafely(ResultSet rs, String columnName) throws SQLException {
        java.sql.Date date = rs.getDate(columnName);
        return date != null ? new Date(date.getTime()) : null;
    }

    
    // Mettre à jour un utilisateur
    public boolean update(User user) throws SQLException {
        String sql = "UPDATE Utilisateur SET nom = ?, email = ?, mot_de_passe = ? WHERE id = ?";
        
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, user.getNom());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getMotDePasse());
            statement.setInt(4, user.getId());
            
            int affectedRows = statement.executeUpdate();
            return affectedRows > 0;
        }
    }
    
    // Supprimer un utilisateur
    public boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM Utilisateur WHERE id = ?";
        
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            
            int affectedRows = statement.executeUpdate();
            return affectedRows > 0;
        }
    }
    
    // Vérifier si l'email existe déjà
    public boolean emailExists(String email) throws SQLException {
        String sql = "SELECT COUNT(*) FROM Utilisateur WHERE email = ?";
        
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, email);
            
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }
    
    // Changer le mot de passe
    public boolean changePassword(int userId, String newPassword) throws SQLException {
        String sql = "UPDATE Utilisateur SET mot_de_passe = ? WHERE id = ?";
        
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, newPassword);  // En production, hasher le mot de passe
            statement.setInt(2, userId);
            
            int affectedRows = statement.executeUpdate();
            return affectedRows > 0;
        }
    }
    
    // Vérifier si l'utilisateur est un manager
    public boolean isManager(int userId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM Manager WHERE utilisateur_id = ?";
        
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, userId);
            
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }
    
    // Vérifier si l'utilisateur est un admin de département
    public boolean isAdminDepartement(int userId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM AdminDepartement WHERE utilisateur_id = ?";
        
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, userId);
            
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }
    
    // Méthode utilitaire pour extraire un utilisateur d'un ResultSet
    private User extractUserFromResultSet(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getInt("id"));
        user.setNom(rs.getString("nom"));
        user.setEmail(rs.getString("email"));
        user.setMotDePasse(rs.getString("mot_de_passe"));
        return user;
    }
} 
