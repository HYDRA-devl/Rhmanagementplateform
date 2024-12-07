package fr.gestionrh.model;


import java.util.ArrayList;
import java.util.List;

public class Departement {
    // Attributs
    private int id;
    private String nom;
    private List<Employee> employes;  // Liste des employés du département
    
    // Constructeurs
    public Departement() {
        this.employes = new ArrayList<>();
    }
    
    public Departement(String nom) {
        this.nom = nom;
        this.employes = new ArrayList<>();
    }
    
    public Departement(int id, String nom) {
        this.id = id;
        this.nom = nom;
        this.employes = new ArrayList<>();
    }
    
    // Getters et Setters
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getNom() {
        return nom;
    }
    
    public void setNom(String nom) {
        this.nom = nom;
    }
    
    public List<Employee> getEmployes() {
        return employes;
    }
    
    public void setEmployes(List<Employee> employes) {
        this.employes = employes;
    }
    
    // Méthodes utilitaires pour gérer les employés
    public void addEmployee(Employee employe) {
        if (!employes.contains(employe)) {
            employes.add(employe);
        }
    }
    
    public void removeEmployee(Employee employe) {
        employes.remove(employe);
    }
    
    public int getNumberOfEmployees() {
        return employes.size();
    }
    
    // Méthodes equals et hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        
        Departement that = (Departement) o;
        
        if (id != that.id) return false;
        return nom != null ? nom.equals(that.nom) : that.nom == null;
    }
    
    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (nom != null ? nom.hashCode() : 0);
        return result;
    }
    
    // toString pour l'affichage
    @Override
    public String toString() {
        return "Departement{" +
               "id=" + id +
               ", nom='" + nom + '\'' +
               ", nombreEmployes=" + getNumberOfEmployees() +
               '}';
    }
    
    // Méthode pour cloner un département
    public Departement clone() {
        Departement clone = new Departement();
        clone.setId(this.id);
        clone.setNom(this.nom);
        clone.setEmployes(new ArrayList<>(this.employes));
        return clone;
    }
    
    // Validation du nom du département
    public boolean isValid() {
        return nom != null && !nom.trim().isEmpty();
    }
}