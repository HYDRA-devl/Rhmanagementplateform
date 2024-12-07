package fr.gestionrh.model;

import java.util.Date;

public class Employee extends User {
    private static final long serialVersionUID = 2L;
    
    private double salaire;
    private int soldeConge;
    private float evaluationPerformance;	
    
    public Employee() {
        super();
        setRole(ROLE_EMPLOYEE);
    }
    
    public Employee(String nom, String email, String motDePasse, double salaire, int soldeConge, float evaluationPerformance) {
        super();
        this.salaire = salaire;
        this.soldeConge = soldeConge;
        this.evaluationPerformance = evaluationPerformance;
        setRole(ROLE_EMPLOYEE);
    }
    
    public Double getSalaire() { return salaire; }
    public void setSalaire(double salaire) { this.salaire = salaire; }
    
    public Integer getSoldeConge() { return soldeConge; }
    public void setSoldeConge(int soldeConge) { this.soldeConge = soldeConge; }
    
    public float getEvaluationPerformance() { return evaluationPerformance; }
    public void setEvaluationPerformance(float evaluationPerformance) { 
        this.evaluationPerformance = evaluationPerformance; 
    }
    
    @Override
    public String toString() {
        return "Employee{" +
               "id=" + getId() +
               ", nom='" + getNom() + '\'' +
               ", email='" + getEmail() + '\'' +
               ", salaire=" + salaire +
               ", soldeConge=" + soldeConge +
               ", evaluationPerformance=" + evaluationPerformance +
               '}';
    }
    
    public static class EmployeeBuilder extends UserBuilder {
        private double salaire;
        private int soldeConge;
        private float evaluationPerformance;
        
        public EmployeeBuilder withSalaire(double salaire) {
            this.salaire = salaire;
            return this;
        }
        
        public EmployeeBuilder withSoldeConge(int soldeConge) {
            this.soldeConge = soldeConge;
            return this;
        }
        
        public EmployeeBuilder withEvaluationPerformance(float evaluationPerformance) {
            this.evaluationPerformance = evaluationPerformance;
            return this;
        }
        
        @Override
        public Employee build() {
            Employee employee = new Employee();
            employee.setNom(super.user.getNom());
            employee.setEmail(super.user.getEmail());
            employee.setMotDePasse(super.user.getMotDePasse());
            employee.setSalaire(this.salaire);
            employee.setSoldeConge(this.soldeConge);
            employee.setEvaluationPerformance(this.evaluationPerformance);
            return employee;
        }
    }
}