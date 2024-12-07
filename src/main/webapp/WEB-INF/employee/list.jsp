<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Gestion des Employés</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="container mt-4">
        <h1>Gestion des Employés</h1>
        <div class="mb-3">
            <a href="${pageContext.request.contextPath}/employee/new" class="btn btn-primary">Nouvel Employé</a>
        </div>
        
        <table class="table table-striped">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Nom</th>
                    <th>Salaire</th>
                    <th>Solde Congé</th>
                    <th>Évaluation</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="employee" items="${listEmployee}">
                    <tr>
                        <td>${employee.id}</td>
                        <td>${employee.nom}</td>
                        <td>${employee.salaire}</td>
                        <td>${employee.soldeConge}</td>
                        <td>${employee.evaluationPerformance}</td>
                        <td>
                            <a href="${pageContext.request.contextPath}/employee/edit?id=${employee.id}" 
                               class="btn btn-sm btn-warning">Modifier</a>
                            <a href="${pageContext.request.contextPath}/employee/delete?id=${employee.id}" 
                               class="btn btn-sm btn-danger" 
                               onclick="return confirm('Êtes-vous sûr de vouloir supprimer cet employé ?')">
                                Supprimer
                            </a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</body>
</html>