package fr.gestionrh.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import fr.gestionrh.dao.*;
import fr.gestionrh.model.*;

@Service
public class AdminService {
    private final UserDAO userDAO;
    private final TrainingDAO trainingDAO;
    private final Connection connection;

    public AdminService(Connection connection) {
        this.connection = connection;
        this.userDAO = new UserDAO(connection);
        this.trainingDAO = new TrainingDAO();
    }

    public User createUser(User user) throws ServiceException {
       
            
            try {
				userDAO.create(user);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
       
		return user;
    }

    public void updateUser(User user) throws ServiceException {
        try {
            User existingUser = userDAO.findById(user.getId());
            if (existingUser == null) {
                throw new ServiceException("User not found");
            }
            if (!existingUser.getEmail().equals(user.getEmail()) && 
                userDAO.emailExists(user.getEmail())) {
                throw new ServiceException("Email already exists");
            }
            userDAO.update(user);
        } catch (SQLException e) {
            throw new ServiceException("Error updating user", e);
        }
    }

    public void deleteUser(int userId) throws ServiceException {
        try {
            if (!userDAO.delete(userId)) {
                throw new ServiceException("User not found or could not be deleted");
            }
        } catch (SQLException e) {
            throw new ServiceException("Error deleting user", e);
        }
    }

    public List<User> getAllUsers() throws ServiceException {
        try {
            return userDAO.findAll();
        } catch (SQLException e) {
            throw new ServiceException("Error retrieving users", e);
        }
    }

    public void changeUserPassword(int userId, String oldPassword, String newPassword) 
            throws ServiceException {
        try {
            User user = userDAO.findById(userId);
            if (user == null) {
                throw new ServiceException("User not found");
            }
            // In production, verify old password with hashing
            if (!user.getMotDePasse().equals(oldPassword)) {
                throw new ServiceException("Invalid old password");
            }
            userDAO.changePassword(userId, newPassword);
        } catch (SQLException e) {
            throw new ServiceException("Error changing password", e);
        }
    }

    public boolean isUserAdmin(int userId) throws ServiceException {
        try {
            return userDAO.isAdminDepartement(userId);
        } catch (SQLException e) {
            throw new ServiceException("Error checking admin status", e);
        }
    }

    public boolean isUserManager(int userId) throws ServiceException {
        try {
            return userDAO.isManager(userId);
        } catch (SQLException e) {
            throw new ServiceException("Error checking manager status", e);
        }
        
        
        public int getTotalUsers() throws ServiceException {
            try {
                return userDAO.countUsers();
            } catch (SQLException e) {
                throw new ServiceException("Error counting users", e);
            }
        }

        public int getTotalEmployees() throws ServiceException {
            try {
                return userDAO.countEmployees();
            } catch (SQLException e) {
                throw new ServiceException("Error counting employees", e);
            }
        }

        public int getTotalManagers() throws ServiceException {
            try {
                return userDAO.countManagers();
            } catch (SQLException e) {
                throw new ServiceException("Error counting managers", e);
            }
        

    }

  

  
}