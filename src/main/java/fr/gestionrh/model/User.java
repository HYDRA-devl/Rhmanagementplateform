package fr.gestionrh.model;

import java.io.Serializable;
import java.util.Date;

public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    
    // Role Constants
    public static final String ROLE_ADMIN = "ADMIN";
    public static final String ROLE_MANAGER = "MANAGER";
    public static final String ROLE_EMPLOYEE = "EMPLOYEE";
    
    // Required fields
    private int id;
    private String nom;
    private String email;
    private String motDePasse;
    private String role;
    private boolean actif = true;
    
    // Optional fields
    private Date derniereConnexion;
    private Date dateCreation;
    private String departement;
    private String equipe;
    private Double salaire;
    private Integer soldeConge;
    
    // Constructor
    public User() {
        // Initialize only required fields
        this.actif = true;
    }
    
    // Getters and Setters (keep only the validation that's absolutely necessary)
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public String getNom() { return nom; }
    public void setNom(String nom) {
        if (nom != null) {
            this.nom = nom.trim();
        }
    }
    
    public String getEmail() { return email; }
    public void setEmail(String email) {
        if (email != null) {
            this.email = email.toLowerCase().trim();
        }
    }
    
    public String getMotDePasse() { return motDePasse; }
    public void setMotDePasse(String motDePasse) { this.motDePasse = motDePasse; }
    
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
    
    public boolean isActif() { return actif; }
    public void setActif(boolean actif) { this.actif = actif; }
    
    // Optional field getters and setters
    public Date getDerniereConnexion() { return derniereConnexion; }
    public void setDerniereConnexion(Date derniereConnexion) { this.derniereConnexion = derniereConnexion; }
    
    public Date getDateCreation() { return dateCreation; }
    public void setDateCreation(Date dateCreation) { this.dateCreation = dateCreation; }
    
    public String getDepartement() { return departement; }
    public void setDepartement(String departement) { this.departement = departement; }
    
    public String getEquipe() { return equipe; }
    public void setEquipe(String equipe) { this.equipe = equipe; }
    
    public Double getSalaire() { return salaire; }
    public void setSalaire(Double salaire) { this.salaire = salaire; }
    
    public Integer getSoldeConge() { return soldeConge; }
    public void setSoldeConge(Integer soldeConge) { this.soldeConge = soldeConge; }
    
    // Utility methods
    public String getStatusDisplay() {
        return this.actif ? "Actif" : "Inactif";
    }
    
    public String getRoleDisplay() {
        return switch (this.role) {
            case ROLE_ADMIN -> "Administrateur";
            case ROLE_MANAGER -> "Manager";
            case ROLE_EMPLOYEE -> "Employé";
            default -> "Non défini";
        };
    }
}