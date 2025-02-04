package com.crypto.model;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.time.LocalDate;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name = "utilisateur")
public class Utilisateur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_utilisateur")
    private Long idUtilisateur;

    @Column(name = "nom", nullable = false, length = 255)
    private String nom;

    @Column(name = "date_ens", nullable = false)
    private LocalDate dateEns;

    @Column(name = "date_naissance", nullable = false)
    private LocalDate dateNaissance;

    @Column(name = "email", nullable = false, length = 512)
    private String email;

    @Column(name = "mdp", nullable = false, length = 255)
    private String mdp;

    @Column(name = "tentative_restant", nullable = false)
    private Integer tentativeRestant;

    @Column(name = "is_admin")
    private int is_admin;

    // Getters et setters


    public int getIs_admin() {
        return is_admin;
    }

    public void setIs_admin(int is_admin) {
        this.is_admin = is_admin;
    }

    public Long getIdUtilisateur() {
        return idUtilisateur;
    }

    public void setIdUtilisateur(Long idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public LocalDate getDateEns() {
        return dateEns;
    }

    public void setDateEns(LocalDate dateEns) {
        this.dateEns = dateEns;
    }

    public LocalDate getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(LocalDate dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMdp() {
        return mdp;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }

    public Integer getTentativeRestant() {
        return tentativeRestant;
    }

    public void setTentativeRestant(Integer tentativeRestant) {
        this.tentativeRestant = tentativeRestant;
    }
}
