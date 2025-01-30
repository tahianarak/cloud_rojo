package com.crypto.model;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import org.springframework.jdbc.core.JdbcTemplate;

import com.crypto.repository.DepotRetraitRepository;
public class DepotRetrait {
    private int idDepotRetrait;
    private double depot;
    private double retrait;
    private LocalDateTime dateDepotRetrait;
    private int idUtilisateur;
    private DepotRetraitRepository depotRetraitRepository; 

    // Constructeurs
    public DepotRetrait() {
    }

    public DepotRetrait(double depot, double retrait, int idUtilisateur) {
        this.depot = depot;
        this.retrait = retrait;
       // this.dateDepotRetrait = new Timestamp(System.currentTimeMillis()); // Date actuelle
        this.idUtilisateur = idUtilisateur;
    }
    public DepotRetraitRepository getDepotRetraitRepository() {
        return depotRetraitRepository;
    }
    public void setDepotRetraitRepository(DepotRetraitRepository depotRetraitRepository) {
        this.depotRetraitRepository = depotRetraitRepository;
    }
    // Getters et Setters
    public int getIdDepotRetrait() {
        return idDepotRetrait;
    }

    public void setIdDepotRetrait(int idDepotRetrait) {
        this.idDepotRetrait = idDepotRetrait;
    }

    public double getDepot() {
        return depot;
    }

    public void setDepot(double depot) throws Exception { 
        if (depot < 0) {
            throw new IllegalArgumentException("La valeur du dépôt doit être correcte");
        } else {
            this.depot = depot;
        } 
    }
    public double getRetrait() {
        return retrait;
    }
    public void setRetrait(double retrait) throws Exception { 
        System.out.println( this.idUtilisateur + "\n");
       // System.out.println("montant : " +depotRetraitRepository.getMontantByUtilisateur( this.idUtilisateur ) ) ; 
        if (retrait < 0) {
            throw new IllegalArgumentException("La valeur du retrait doit être correcte");
        } 
        if( retrait > depotRetraitRepository.getMontantByUtilisateur( this.idUtilisateur )){ 
            throw new Exception("Le montant du retrait est supérieur au solde du compte"); 
        } 
        else { this.retrait = retrait; } 
    }
    public LocalDateTime getDateDepotRetrait() {
        return dateDepotRetrait;
    }

    public void setDateDepotRetrait(LocalDateTime dateDepotRetrait) {
        this.dateDepotRetrait = dateDepotRetrait;
    }

    public int getIdUtilisateur() {
        return idUtilisateur;
    }

    public void setIdUtilisateur(int idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }
}
