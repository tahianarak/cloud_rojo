package com.crypto.model.cryptos;

import com.crypto.model.Utilisateur;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name = "portefeuille")
public class Portefeuille {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_porte_feuille")
    private Long idPorteFeuille;

    @Column(name = "entree", precision = 15, scale = 2)
    private BigDecimal entree;

    @Column(name = "sortie", precision = 15, scale = 2)
    private BigDecimal sortie;

    @Column(name = "date_ens")
    private LocalDateTime dateEns;

    @ManyToOne
    @JoinColumn(name = "id_crypto", nullable = false)
    private Crypto crypto;

    @ManyToOne
    @JoinColumn(name = "id_utilisateur", nullable = false)
    private Utilisateur utilisateur;

    // Getters et setters
    public Long getIdPorteFeuille() {
        return idPorteFeuille;
    }

    public void setIdPorteFeuille(Long idPorteFeuille) {
        this.idPorteFeuille = idPorteFeuille;
    }

    public BigDecimal getEntree() {
        return entree;
    }

    public void setEntree(BigDecimal entree) {
        this.entree = entree;
    }

    public BigDecimal getSortie() {
        return sortie;
    }

    public void setSortie(BigDecimal sortie) {
        this.sortie = sortie;
    }

    public LocalDateTime getDateEns() {
        return dateEns;
    }

    public void setDateEns(LocalDateTime dateEns) {
        this.dateEns = dateEns;
    }

    public Crypto getCrypto() {
        return crypto;
    }

    public void setCrypto(Crypto crypto) {
        this.crypto = crypto;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }
}
