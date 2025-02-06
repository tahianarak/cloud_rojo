package com.crypto.model.crypto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Portefeuille {

    int idPortefeuille;
    double entree;
    double sortie;
    java.sql.Timestamp dateEns; // Utilisation de java.sql.Timestamp pour inclure l'heure
    int idCrypto;
    int idUtilisateur;

    public static boolean verifyIfNotEmpty(Connection connection, int idUtilisateur, int idCrypto) throws Exception {
        double qtCrypto = Portefeuille.getQtCryptoOf(connection, idUtilisateur, idCrypto);
        return qtCrypto > 0;
    }

    public static double getQtCryptoOf(Connection connection, int idUtilisateur, int idCrypto) throws Exception {
        String sql = "SELECT SUM(entree - sortie) AS qt FROM portefeuille WHERE id_crypto = ? AND id_utilisateur = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, idCrypto);
            ps.setInt(2, idUtilisateur);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getDouble("qt");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return -1;
    }

    // Constructeur
    public Portefeuille(int idPortefeuille, double entree, double sortie, java.sql.Timestamp dateEns, int idCrypto, int idUtilisateur) {
        this.idPortefeuille = idPortefeuille;
        this.entree = entree;
        this.sortie = sortie;
        this.dateEns = dateEns;
        this.idCrypto = idCrypto;
        this.idUtilisateur = idUtilisateur;
    }

    // Constructeur sans ID (pour la création)
    public Portefeuille(double entree, double sortie, java.sql.Timestamp dateEns, int idCrypto, int idUtilisateur) {
        this.entree = entree;
        this.sortie = sortie;
        this.dateEns = dateEns;
        this.idCrypto = idCrypto;
        this.idUtilisateur = idUtilisateur;
    }

    // Getters et Setters
    public int getIdPortefeuille() {
        return idPortefeuille;
    }

    public void setIdPortefeuille(int idPortefeuille) {
        this.idPortefeuille = idPortefeuille;
    }

    public double getEntree() {
        return entree;
    }

    public void setEntree(double entree) {
        this.entree = entree;
    }

    public double getSortie() {
        return sortie;
    }

    public void setSortie(double sortie) {
        this.sortie = sortie;
    }

    public java.sql.Timestamp getDateEns() {
        return dateEns;
    }

    public void setDateEns(java.sql.Timestamp dateEns) {
        this.dateEns = dateEns;
    }

    public int getIdCrypto() {
        return idCrypto;
    }

    public void setIdCrypto(int idCrypto) {
        this.idCrypto = idCrypto;
    }

    public int getIdUtilisateur() {
        return idUtilisateur;
    }

    public void setIdUtilisateur(int idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }

    // Créer un portefeuille
    public static void create(Connection connection, Portefeuille portefeuille) throws SQLException {
        String sql = "INSERT INTO portefeuille(entree, sortie, date_ens, id_crypto, id_utilisateur) VALUES (?, ?, CURRENT_TIMESTAMP, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setDouble(1, portefeuille.getEntree());
            ps.setDouble(2, portefeuille.getSortie());
            ps.setInt(3, portefeuille.getIdCrypto());
            ps.setInt(4, portefeuille.getIdUtilisateur());
            ps.executeUpdate();
        }
    }

    // Lire un portefeuille par son ID
    public static Portefeuille read(Connection connection, int idPortefeuille) throws SQLException {
        String sql = "SELECT * FROM portefeuille WHERE id_porte_feuille = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, idPortefeuille);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Portefeuille(
                        rs.getInt("id_porte_feuille"),
                        rs.getDouble("entree"),
                        rs.getDouble("sortie"),
                        rs.getTimestamp("date_ens"), // Récupère la date avec java.sql.Timestamp
                        rs.getInt("id_crypto"),
                        rs.getInt("id_utilisateur")
                );
            }
        }
        return null; // Retourne null si le portefeuille n'est pas trouvé
    }

    // Mettre à jour un portefeuille
    public static void update(Connection connection, Portefeuille portefeuille) throws SQLException {
        String sql = "UPDATE portefeuille SET entree = ?, sortie = ?, date_ens = CURRENT_TIMESTAMP, id_crypto = ?, id_utilisateur = ? WHERE id_porte_feuille = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setDouble(1, portefeuille.getEntree());
            ps.setDouble(2, portefeuille.getSortie());
            ps.setInt(3, portefeuille.getIdCrypto());
            ps.setInt(4, portefeuille.getIdUtilisateur());
            ps.setInt(5, portefeuille.getIdPortefeuille());
            ps.executeUpdate();
        }
    }

    // Supprimer un portefeuille par son ID
    public static void delete(Connection connection, int idPortefeuille) throws SQLException {
        String sql = "DELETE FROM portefeuille WHERE id_porte_feuille = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, idPortefeuille);
            ps.executeUpdate();
        }
    }

    // Lire tous les portefeuilles
    public static List<Portefeuille> getAll(Connection connection) throws SQLException {
        List<Portefeuille> portefeuilles = new ArrayList<>();
        String sql = "SELECT * FROM portefeuille";
        try (Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                portefeuilles.add(new Portefeuille(
                        rs.getInt("id_porte_feuille"),
                        rs.getDouble("entree"),
                        rs.getDouble("sortie"),
                        rs.getTimestamp("date_ens"), // Utilisation de getTimestamp pour récupérer java.sql.Timestamp
                        rs.getInt("id_crypto"),
                        rs.getInt("id_utilisateur")
                ));
            }
        }
        return portefeuilles;
    }

    @Override
    public String toString() {
        return "Portefeuille{" +
                "idPortefeuille=" + idPortefeuille +
                ", entree=" + entree +
                ", sortie=" + sortie +
                ", dateEns=" + dateEns +
                ", idCrypto=" + idCrypto +
                ", idUtilisateur=" + idUtilisateur +
                '}';
    }
}
