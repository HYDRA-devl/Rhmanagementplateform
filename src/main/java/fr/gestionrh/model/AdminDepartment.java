package fr.gestionrh.model;

import java.util.ArrayList;
import java.util.List;

public class AdminDepartment extends User {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String departmentId;
    private String departmentName;
    private List<User> managedUsers;
    private List<Training> managedTrainings;
    
    public AdminDepartment() {
        super();
        setRole(ROLE_ADMIN);
        this.managedUsers = new ArrayList<>();
        this.managedTrainings = new ArrayList<>();
    }
    
    public String getDepartmentId() { return departmentId; }
    public void setDepartmentId(String departmentId) { this.departmentId = departmentId; }
    
    public String getDepartmentName() { return departmentName; }
    public void setDepartmentName(String departmentName) { this.departmentName = departmentName; }
    
    public List<User> getManagedUsers() { return managedUsers; }
    public void setManagedUsers(List<User> managedUsers) { this.managedUsers = managedUsers; }
    
    public List<Training> getManagedTrainings() { return managedTrainings; }
    public void setManagedTrainings(List<Training> managedTrainings) { this.managedTrainings = managedTrainings; }
    
    @Override
    public String toString() {
        return "AdminDepartment{" +
               "id=" + getId() +
               ", departmentId='" + departmentId + '\'' +
               ", departmentName='" + departmentName + '\'' +
               ", managedUsers=" + managedUsers.size() +
               ", managedTrainings=" + managedTrainings.size() +
               '}';
    }
}