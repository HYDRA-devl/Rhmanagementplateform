<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Employé Form</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="container mt-4">
        <h1>${employee != null ? "Modifier" : "Nouvel"} Employé</h1>
        
        <form action="${pageContext.request.contextPath}/employee/${employee != null ? 'update' : 'insert'}" 
              method="post">
            
            <c:if test="${employee != null}">
                <input type="hidden" name="id" value="${employee.id}" />
            </c:if>
            
            <div class="mb-3">
                <label for="nom" class="form-label">Nom:</label>
                <input type="text" class="form-control" id="nom" name="nom" 
                       value="${employee != null ? employee.nom : ''}" required>
            </div>
            
            <div class="mb-3">
                <label for="salaire" class="form-label">Salaire:</label>
                <input type="number" class="form-control" id="salaire" name="salaire" 
                       value="${employee != null ? employee.salaire : ''}" required>
            </div>
            
            <div class="mb-3">
                <label for="soldeConge" class="form-label">Solde Congé:</label>
                <input type="number" class="form-control" id="soldeConge" name="soldeConge" 
                       value="${employee != null ? employee.soldeConge : ''}" required>
            </div>
            
            <div class="mb-3">
                <label for="evaluationPerformance" class="form-label">Évaluation Performance:</label>
                <input type="number" step="0.1" class="form-control" id="evaluationPerformance" 
                       name="evaluationPerformance" 
                       value="${employee != null ? employee.evaluationPerformance : ''}" required>
            </div>
            
            <button type="submit" class="btn btn-primary">Enregistrer</button>
            <a href="${pageContext.request.contextPath}/employee/list" class="btn btn-secondary">Annuler</a>
        </form>
    </div>
</body>
</html>