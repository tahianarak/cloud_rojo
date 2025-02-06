package com.crypto.model;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Crypto {

    int idCrypto;
    String libelle;
    double prixActuelle;
    Timestamp dateUpdate;

    // Constructeur
    public Crypto(int idCrypto, String libelle, double prixActuelle, Timestamp dateUpdate) {
        this.idCrypto = idCrypto;
        this.libelle = libelle;
        this.prixActuelle = prixActuelle;
        this.dateUpdate = dateUpdate;
    }
    public Crypto() {} ; 

    // Getters et Setters
    public int getIdCrypto() {
        return idCrypto;
    }

    public void setIdCrypto(int idCrypto) {
        this.idCrypto = idCrypto;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public double getPrixActuelle() {
        return prixActuelle;
    }

    public void setPrixActuelle(double prixActuelle) {
        this.prixActuelle = prixActuelle;
    }

    public Timestamp getDateUpdate() {
        return dateUpdate;
    }

    public void setDateUpdate(Timestamp dateUpdate) {
        this.dateUpdate = dateUpdate;
    }

  
}

