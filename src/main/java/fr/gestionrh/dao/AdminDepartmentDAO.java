package fr.gestionrh.dao;

import fr.gestionrh.model.AdminDepartment;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdminDepartmentDAO {
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/gestionrh", "root", "root");
    }
    
    public void createAdminDepartment(AdminDepartment admin) throws SQLException {
        String sql = "INSERT INTO admin_departments (department_id, department_name, user_id) VALUES (?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, admin.getDepartmentId());
            stmt.setString(2, admin.getDepartmentName());
            stmt.setInt(3, admin.getId());
            stmt.executeUpdate();
        }
    }
    
    public AdminDepartment getAdminDepartment(int id) throws SQLException {
        String sql = "SELECT * FROM admin_departments ad JOIN users u ON ad.user_id = u.id WHERE ad.id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapResultSetToAdminDepartment(rs);
            }
        }
        return null;
    }
    
    public void updateAdminDepartment(AdminDepartment admin) throws SQLException {
        String sql = "UPDATE admin_departments SET department_id = ?, department_name = ? WHERE user_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, admin.getDepartmentId());
            stmt.setString(2, admin.getDepartmentName());
            stmt.setInt(3, admin.getId());
            stmt.executeUpdate();
        }
    }
    
    private AdminDepartment mapResultSetToAdminDepartment(ResultSet rs) throws SQLException {
        AdminDepartment admin = new AdminDepartment();
        admin.setId(rs.getInt("id"));
        admin.setDepartmentId(rs.getString("department_id"));
        admin.setDepartmentName(rs.getString("department_name"));
        admin.setNom(rs.getString("nom"));
        admin.setEmail(rs.getString("email"));
        return admin;
    }
}