package com.crypto.model.cryptos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name = "crypto")
public class Crypto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_crypto")
    private Long idCrypto;

    @Column(name = "libelle", length = 255)
    private String libelle;

    @Column(name = "prix_actuelle", precision = 15, scale = 2)
    private BigDecimal prixActuelle;

    @Column(name = "date_update")
    private LocalDateTime dateUpdate;

    public Long getIdCrypto() {
        return idCrypto;
    }

    public void setIdCrypto(Long idCrypto) {
        this.idCrypto = idCrypto;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public BigDecimal getPrixActuelle() {
        return prixActuelle;
    }

    public void setPrixActuelle(BigDecimal prixActuelle) {
        this.prixActuelle = prixActuelle;
    }

    public LocalDateTime getDateUpdate() {
        return dateUpdate;
    }

    public void setDateUpdate(LocalDateTime dateUpdate) {
        this.dateUpdate = dateUpdate;
    }
}
