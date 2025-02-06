package com.crypto.model;

import com.crypto.model.Utilisateur;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name = "depot_retrait_temporaire")
public class DepotRetraitTemporaire {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_depot_retrait")
    private Long idDepotRetrait;

    @JsonSerialize(using = ToStringSerializer.class)
    @Column(name = "depot", precision = 15, scale = 2)
    private BigDecimal depot;

    @JsonSerialize(using = ToStringSerializer.class)
    @Column(name = "retrait", precision = 15, scale = 2)
    private BigDecimal retrait;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Column(name = "date_depot_retrait")
    private LocalDateTime dateDepotRetrait;

    @ManyToOne
    @JoinColumn(name = "id_utilisateur", nullable = false)
    private Utilisateur utilisateur;

    @JsonSerialize(using = ToStringSerializer.class)
    @Transient
    BigDecimal solde;

    public BigDecimal getSolde() {
        return solde;
    }

    public void setSolde(BigDecimal solde) {
        this.solde = solde;
    }

    // Getters et setters
    public Long getIdDepotRetrait() {
        return idDepotRetrait;
    }

    public void setIdDepotRetrait(Long idDepotRetrait) {
        this.idDepotRetrait = idDepotRetrait;
    }

    public BigDecimal getDepot() {
        return depot;
    }

    public void setDepot(BigDecimal depot) {
        this.depot = depot;
    }

    public BigDecimal getRetrait() {
        return retrait;
    }

    public void setRetrait(BigDecimal retrait) {
        this.retrait = retrait;
    }

    public LocalDateTime getDateDepotRetrait() {
        return dateDepotRetrait;
    }

    public void setDateDepotRetrait(LocalDateTime dateDepotRetrait) {
        this.dateDepotRetrait = dateDepotRetrait;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }
}

