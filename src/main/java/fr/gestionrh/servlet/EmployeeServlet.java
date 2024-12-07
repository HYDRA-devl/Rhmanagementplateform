 
package fr.gestionrh.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import fr.gestionrh.dao.EmployeeDAO;
import fr.gestionrh.model.Employee;
import fr.gestionrh.util.DBConnection;

@WebServlet("/employee/*")
public class EmployeeServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private EmployeeDAO employeeDAO;
    
    public void init() throws ServletException {
        try {
            Connection conn = DBConnection.getConnection();
            employeeDAO = new EmployeeDAO(conn);
        } catch (Exception e) {
            throw new ServletException("Erreur d'initialisation de EmployeeDAO", e);
        }
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String action = request.getPathInfo();
        if (action == null) {
            action = "/list";
        }

        try {
            switch (action) {
                case "/list":
                    viewEmployeeInfo(request, response);
                    break;
                case "/new":
                    showNewForm(request, response);
                    break;
                case "/edit":
                    showEditForm(request, response);
                    break;
                case "/delete":
                    deleteEmployee(request, response);
                    break;
                default:
                    viewEmployeeInfo(request, response);
                    break;
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String action = request.getPathInfo();
        if (action == null) {
            action = "/list";
        }

        try {
            switch (action) {
                case "/insert":
                    insertEmployee(request, response);
                    break;
                case "/update":
                    updateEmployee(request, response);
                    break;
                default:
                    viewEmployeeInfo(request, response);
                    break;
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }

    private void viewEmployeeInfo(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        // Assume the employee's ID is stored in the session after login
//   int employeeId = (int) request.getSession().getAttribute("employeeId");

        Employee employee = employeeDAO.findById(1);
        if (employee != null) {
            request.setAttribute("employee", employee);
            request.getRequestDispatcher("/WEB-INF/employee/dashboard.jsp").forward(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Employee not found");
        }
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/employee/form.jsp").forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response) 
            throws SQLException, ServletException, IOException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            Employee existingEmployee = employeeDAO.findById(id);
            request.setAttribute("employee", existingEmployee);
            request.getRequestDispatcher("/WEB-INF/employee/form.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/employee/list");
        }
    }

    private void insertEmployee(HttpServletRequest request, HttpServletResponse response) 
            throws SQLException, IOException {
        String nom = request.getParameter("nom");
        double salaire = Double.parseDouble(request.getParameter("salaire"));
        int soldeConge = Integer.parseInt(request.getParameter("soldeConge"));
        float evaluationPerformance = Float.parseFloat(request.getParameter("evaluationPerformance"));

        Employee newEmployee = new Employee(nom, salaire, soldeConge, evaluationPerformance);
        employeeDAO.create(newEmployee);
        response.sendRedirect(request.getContextPath() + "/employee/list");
    }

    private void updateEmployee(HttpServletRequest request, HttpServletResponse response) 
            throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String nom = request.getParameter("nom");
        double salaire = Double.parseDouble(request.getParameter("salaire"));
        int soldeConge = Integer.parseInt(request.getParameter("soldeConge"));
        float evaluationPerformance = Float.parseFloat(request.getParameter("evaluationPerformance"));

        Employee employee = new Employee(nom, salaire, soldeConge, evaluationPerformance);
        employee.setId(id);
        employeeDAO.update(employee);
        response.sendRedirect(request.getContextPath() + "/employee/list");
    }

    private void deleteEmployee(HttpServletRequest request, HttpServletResponse response) 
            throws SQLException, IOException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            employeeDAO.delete(id);
        } catch (NumberFormatException e) {
            // Log l'erreur si nécessaire
        }
        response.sendRedirect(request.getContextPath() + "/employee/list");
    }
}