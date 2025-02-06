package com.crypto.model.crypto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Cryptos {

    private int idCrypto;
    private String libelle;
    private double prixActuelle;
    private Timestamp dateUpdate;

    // Constructeur
    public Cryptos(int idCrypto, String libelle, double prixActuelle, Timestamp dateUpdate) {
        this.idCrypto = idCrypto;
        this.libelle = libelle;
        this.prixActuelle = prixActuelle;
        this.dateUpdate = dateUpdate;
    }

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

    // Créer une nouvelle crypto
    public static void create(Connection connection, String libelle, double prixActuelle) throws SQLException {
        String sql = "INSERT INTO crypto(libelle, prix_actuelle, date_update) VALUES (?, ?, NOW())";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, libelle);
            ps.setDouble(2, prixActuelle);
            ps.executeUpdate();
        }
    }

    // Lire une crypto par son ID
    public static Cryptos read(Connection connection, int idCrypto) throws SQLException {
        String sql = "SELECT * FROM crypto WHERE id_crypto = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, idCrypto);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Cryptos(
                        rs.getInt("id_crypto"),
                        rs.getString("libelle"),
                        rs.getDouble("prix_actuelle"),
                        rs.getTimestamp("date_update")
                );
            }
        }
        return null; // Retourne null si la crypto n'est pas trouvée
    }

    // Mettre à jour une crypto
    public static void update(Connection connection, int idCrypto, String libelle, double prixActuelle) throws SQLException {
        String sql = "UPDATE crypto SET libelle = ?, prix_actuelle = ?, date_update = NOW() WHERE id_crypto = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, libelle);
            ps.setDouble(2, prixActuelle);
            ps.setInt(3, idCrypto);
            ps.executeUpdate();
        }
    }

    // Supprimer une crypto par son ID
    public static void delete(Connection connection, int idCrypto) throws SQLException {
        String sql = "DELETE FROM crypto WHERE id_crypto = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, idCrypto);
            ps.executeUpdate();
        }
    }

    // Lire toutes les cryptos
    public static List<Cryptos> getAll(Connection connection) throws SQLException {
        List<Cryptos> cryptos = new ArrayList<>();
        String sql = "SELECT * FROM crypto";
        try (Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                cryptos.add(new Cryptos(
                        rs.getInt("id_crypto"),
                        rs.getString("libelle"),
                        rs.getDouble("prix_actuelle"),
                        rs.getTimestamp("date_update")
                ));
            }
        }
        return cryptos;
    }

    @Override
    public String toString() {
        return "Cryptos{" +
                "idCrypto=" + idCrypto +
                ", libelle='" + libelle + '\'' +
                ", prixActuelle=" + prixActuelle +
                ", dateUpdate=" + dateUpdate +
                '}';
    }
}

