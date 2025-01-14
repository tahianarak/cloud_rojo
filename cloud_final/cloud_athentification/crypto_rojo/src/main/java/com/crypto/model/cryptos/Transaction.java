package com.crypto.model.cryptos;

import com.crypto.model.Utilisateur;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name = "transaction")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_transaction")
    private Long idTransaction;

    @Column(name = "vente", precision = 15, scale = 2)
    private BigDecimal vente;

    @Column(name = "achat", precision = 15, scale = 2)
    private BigDecimal achat;

    @Column(name = "date_debut")
    private LocalDateTime dateDebut;

    @ManyToOne
    @JoinColumn(name = "id_crypto", nullable = false)
    private Crypto crypto;

    @ManyToOne
    @JoinColumn(name = "id_utilisateur", nullable = false)
    private Utilisateur utilisateur;



    @Transient
    private BigDecimal argent;

    @Transient
    private BigDecimal valeurCryptoReste;
    @Column(name = "commission")
    private BigDecimal commission;

    public BigDecimal getValeurCryptoReste() {
        return valeurCryptoReste;
    }

    public void setValeurCryptoReste(BigDecimal valeurCryptoReste) {
        this.valeurCryptoReste = valeurCryptoReste;
    }

    public BigDecimal getCommission() {
        return commission;
    }

    public void setCommission(BigDecimal commission) {
        this.commission = commission;
    }

    public BigDecimal getArgent() {
        return argent;
    }

    public void setArgent(BigDecimal argent) {
        this.argent = argent;
    }
    // Getters et setters
    public Long getIdTransaction() {
        return idTransaction;
    }

    public void setIdTransaction(Long idTransaction) {
        this.idTransaction = idTransaction;
    }

    public BigDecimal getVente() {
        return vente;
    }

    public void setVente(BigDecimal vente) {
        this.vente = vente;
    }

    public BigDecimal getAchat() {
        return achat;
    }

    public void setAchat(BigDecimal achat) {
        this.achat = achat;
    }

    public LocalDateTime getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(LocalDateTime dateDebut) {
        this.dateDebut = dateDebut;
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

