package com.crypto.model.cryptos;


import jakarta.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "commission")
public class Commission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_commission")
    private int idCommission;

    @Column(name = "date_ens", nullable = false)
    private Timestamp dateEns;

    @Column(name = "pourcentage", nullable = false)
    private double pourcentage;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_crypto", nullable = false)
    private Crypto crypto;

    @Column(name = "description", length = 255)
    private String description;



    // Constructeurs
    public Commission() {}


    public Commission(Timestamp dateEns, double pourcentage, Crypto crypto, String description) {
        this.dateEns = dateEns;
        this.pourcentage = pourcentage;
        this.crypto = crypto;
        this.description = description;
    }

    // Getters et Setters
    public int getIdCommission() {
        return idCommission;
    }

    public void setIdCommission(int idCommission) {
        this.idCommission = idCommission;
    }

    public Timestamp getDateEns() {
        return dateEns;
    }

    public void setDateEns(Timestamp dateEns) {
        this.dateEns = dateEns;
    }

    public double getPourcentage() {
        return pourcentage;
    }

    public void setPourcentage(double pourcentage) {
        this.pourcentage = pourcentage;
    }

    public Crypto getCrypto() {
        return crypto;
    }

    public void setCrypto(Crypto crypto) {
        this.crypto = crypto;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}

