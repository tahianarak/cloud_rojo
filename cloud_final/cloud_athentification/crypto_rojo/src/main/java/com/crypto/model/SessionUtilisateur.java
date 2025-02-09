package com.crypto.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name = "session_utilisateur")
public class SessionUtilisateur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_session")
    private Long idSession;

    @Column(name = "token", nullable = false, unique = true)
    private String token;

    @Column(name = "temps_restant", nullable = false)
    private LocalDateTime tempsRestant;

    @ManyToOne
    @JoinColumn(name = "id_utilisateur", nullable = false)
    private Utilisateur utilisateur;

    // Constructeurs
    public SessionUtilisateur() {}

    public SessionUtilisateur(String token, LocalDateTime tempsRestant, Utilisateur utilisateur) {
        this.token = token;
        this.tempsRestant = tempsRestant;
        this.utilisateur = utilisateur;
    }

    // Getters et Setters
    public Long getIdSession() {
        return idSession;
    }

    public void setIdSession(Long idSession) {
        this.idSession = idSession;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public LocalDateTime getTempsRestant() {
        return tempsRestant;
    }

    public void setTempsRestant(LocalDateTime tempsRestant) {
        this.tempsRestant = tempsRestant;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }
}
