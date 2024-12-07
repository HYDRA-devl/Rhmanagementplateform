package fr.gestionrh.servlet;

import fr.gestionrh.dao.*;
import fr.gestionrh.model.*;
import fr.gestionrh.service.AdminService;
import fr.gestionrh.service.ServiceException;
import fr.gestionrh.util.DBConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
@WebServlet("/admin/createUser")

public class AdminDashboardServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private AdminService adminService;
    
    @Override
    public void init() throws ServletException {
        Connection conn = null;
		try {
			conn = DBConnection.getConnection();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        this.adminService = new AdminService(conn);
    }

    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String path = request.getPathInfo();
        
        try {
            switch (path) {
                   
                case "/accounts":
               	


                	request.getRequestDispatcher("/WEB-INF/admindep/accounts.jsp").forward(request, response);
                    break;
                    
                case "/training":
                	request.getRequestDispatcher("/WEB-INF/admindep/training.jsp").forward(request, response);

                
                
                default:
                     int totalUsers = adminService.getTotalUsers();
                     System.out.println(totalUsers);
                     int totalEmployees = adminService.getTotalEmployees();
                     int totalmanagers=adminService.getTotalManagers();
                     
                     List<User> accounts = adminService.getAllUsers();
                     request.setAttribute("accounts", accounts);
                     System.out.println("Retrieved accounts: " + accounts.size()); // Debug log
                     // Set attributes to be accessed in the JSP
                     request.setAttribute("totalUsers", totalUsers);
                     request.setAttribute("totalEmployees", totalEmployees);
                     request.setAttribute("totalmanagers", totalmanagers);

                     request.getRequestDispatcher("/WEB-INF/admindep/dashboard.jsp").forward(request, response);            }
        } catch (ServiceException e) {
            throw new ServletException("Service error", e);
        }
    }

    @Override

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
    	

    	String action = request.getParameter("action");
        
        switch (action) {
		case "createUser":
		    try {
		        // Create new user
		        User user = new User();
		        
		        // Set required fields
		        user.setNom(request.getParameter("nom"));
		        user.setEmail(request.getParameter("email"));
		        user.setMotDePasse(request.getParameter("motDePasse"));
		        user.setRole(request.getParameter("role"));
		        user.setActif(true);
		        System.out.println("Nom: " + request.getParameter("nom"));
		        System.out.println("Email: " + request.getParameter("email"));
		        System.out.println("MotDePasse: " + request.getParameter("motDePasse"));
		        System.out.println("Role: " + request.getParameter("role"));
		        // Set optional fields
		        String departement = request.getParameter("departement");
		        System.out.println("dep: " + request.getParameter("departement"));

		        if (departement != null && !departement.trim().isEmpty()) {
		            user.setDepartement(departement);
		        }
		        
		        String equipe = request.getParameter("equipe");
		        if (equipe != null && !equipe.trim().isEmpty()) {
		            user.setEquipe(equipe);
		        }
		        
		        // Set current date as creation date
		        user.setDateCreation(new Date());
		        
		        // Handle employee-specific fields if role is EMPLOYEE
		        if (User.ROLE_EMPLOYEE.equals(user.getRole())) {
		            String salaireStr = request.getParameter("salaire");
		            if (salaireStr != null && !salaireStr.trim().isEmpty()) {
		                try {
		                    user.setSalaire(Double.parseDouble(salaireStr));
		                } catch (NumberFormatException e) {
		                    throw new ServiceException("Invalid salary format");
		                }
		            }
		            
		            String soldeCongeStr = request.getParameter("soldeConge");
		            if (soldeCongeStr != null && !soldeCongeStr.trim().isEmpty()) {
		                try {
		                    user.setSoldeConge(Integer.parseInt(soldeCongeStr));
		                } catch (NumberFormatException e) {
		                    throw new ServiceException("Invalid leave balance format");
		                }
		            }
		        }

		        // Validate required fields
		        if (user.getNom() == null || user.getNom().trim().isEmpty()) {
		            throw new ServiceException("Name is required");
		        }
		        if (user.getEmail() == null || user.getEmail().trim().isEmpty()) {
		            throw new ServiceException("Email is required");
		        }
		        if (user.getMotDePasse() == null || user.getMotDePasse().trim().isEmpty()) {
		            throw new ServiceException("Password is required");
		        }
		        if (user.getRole() == null || !(user.getRole().equals(User.ROLE_ADMIN) || 
		                                      user.getRole().equals(User.ROLE_MANAGER) || 
		                                      user.getRole().equals(User.ROLE_EMPLOYEE))) {
		            throw new ServiceException("Invalid role");
		        }

		        // Create user and get the created user with ID
		        User createdUser = adminService.createUser(user);

		        // Send success response
		        response.setContentType("application/json");
		        response.setCharacterEncoding("UTF-8");
		        PrintWriter out = response.getWriter();
		        out.println("{\"success\": true, \"message\": \"User created successfully\", \"userId\": " + createdUser.getId() + "}");

		    } catch (ServiceException e) {
		        response.setContentType("application/json");
		        response.setCharacterEncoding("UTF-8");
		        PrintWriter out = response.getWriter();
		        out.println("{\"success\": false, \"error\": \"" + e.getMessage() + "\"}");
		    }
		    break;
		    case "createTraining":
		        Training training = new Training();
		        training.setTitre(request.getParameter("titre"));
		        training.setDescription(request.getParameter("description"));
		        // Set other training properties
		        break;
		}
		response.sendRedirect("/RHproject/admin/");
    }
}