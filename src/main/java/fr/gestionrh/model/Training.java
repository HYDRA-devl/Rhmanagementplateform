 
package fr.gestionrh.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Training implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private int id;
    private String titre;
    private String description;
    private Date dateDebut;
    private Date dateFin;
    private int dureeHeures;
    private String formateur;
    private int capaciteMax;
    private boolean obligatoire;
    private String statut; // PLANIFIE, EN_COURS, TERMINE, ANNULE
    private Set<Employee> participants;
    
    public Training() {
        this.participants = new HashSet<>();
    }
    
    public Training(String titre, String description, Date dateDebut, Date dateFin, int dureeHeures, String formateur, int capaciteMax) {
        this();
        this.titre = titre;
        this.description = description;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.dureeHeures = dureeHeures;
        this.formateur = formateur;
        this.capaciteMax = capaciteMax;
        this.statut = "PLANIFIE";
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public String getTitre() { return titre; }
    public void setTitre(String titre) { this.titre = titre; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public Date getDateDebut() { return dateDebut; }
    public void setDateDebut(Date dateDebut) { this.dateDebut = dateDebut; }
    
    public Date getDateFin() { return dateFin; }
    public void setDateFin(Date dateFin) { this.dateFin = dateFin; }
    
    public int getDureeHeures() { return dureeHeures; }
    public void setDureeHeures(int dureeHeures) { this.dureeHeures = dureeHeures; }
    
    public String getFormateur() { return formateur; }
    public void setFormateur(String formateur) { this.formateur = formateur; }
    
    public int getCapaciteMax() { return capaciteMax; }
    public void setCapaciteMax(int capaciteMax) { this.capaciteMax = capaciteMax; }
    
    public boolean isObligatoire() { return obligatoire; }
    public void setObligatoire(boolean obligatoire) { this.obligatoire = obligatoire; }
    
    public String getStatut() { return statut; }
    public void setStatut(String statut) { this.statut = statut; }
    
    public Set<Employee> getParticipants() { return participants; }
    public void setParticipants(Set<Employee> participants) { this.participants = participants; }
    
    // Business methods
    public boolean addParticipant(Employee employee) {
        if (participants.size() < capaciteMax) {
            return participants.add(employee);
        }
        return false;
    }
    
    public boolean removeParticipant(Employee employee) {
        return participants.remove(employee);
    }
    
    public boolean isComplet() {
        return participants.size() >= capaciteMax;
    }
    
    public int getPlacesDisponibles() {
        return capaciteMax - participants.size();
    }
    
    @Override
    public String toString() {
        return "Training{" +
               "id=" + id +
               ", titre='" + titre + '\'' +
               ", dateDebut=" + dateDebut +
               ", dateFin=" + dateFin +
               ", formateur='" + formateur + '\'' +
               ", statut='" + statut + '\'' +
               ", participants=" + participants.size() +
               '}';
    }
}