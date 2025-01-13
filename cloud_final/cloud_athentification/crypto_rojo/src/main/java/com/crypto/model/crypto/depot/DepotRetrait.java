package com.crypto.model.crypto.depot;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DepotRetrait {

    private int idDepotRetrait;
    private double depot;
    private double retrait;
    private java.sql.Date dateDepotRetrait;  // Utilisation de java.sql.Date
    private int idUtilisateur;

    // Constructeur
    public DepotRetrait(int idDepotRetrait, double depot, double retrait, java.sql.Date dateDepotRetrait, int idUtilisateur) {
        this.idDepotRetrait = idDepotRetrait;
        this.depot = depot;
        this.retrait = retrait;
        this.dateDepotRetrait = dateDepotRetrait;
        this.idUtilisateur = idUtilisateur;
    }

    // Constructeur sans ID (pour la création)
    public DepotRetrait(double depot, double retrait, java.sql.Date dateDepotRetrait, int idUtilisateur) {
        this.depot = depot;
        this.retrait = retrait;
        this.dateDepotRetrait = dateDepotRetrait;
        this.idUtilisateur = idUtilisateur;
    }

    // Getters et Setters
    public int getIdDepotRetrait() {
        return idDepotRetrait;
    }

    public void setIdDepotRetrait(int idDepotRetrait) {
        this.idDepotRetrait = idDepotRetrait;
    }

    public double getDepot() {
        return depot;
    }

    public void setDepot(double depot) {
        this.depot = depot;
    }

    public double getRetrait() {
        return retrait;
    }

    public void setRetrait(double retrait) {
        this.retrait = retrait;
    }

    public java.sql.Date getDateDepotRetrait() {
        return dateDepotRetrait;
    }

    public void setDateDepotRetrait(java.sql.Date dateDepotRetrait) {
        this.dateDepotRetrait = dateDepotRetrait;
    }

    public int getIdUtilisateur() {
        return idUtilisateur;
    }

    public void setIdUtilisateur(int idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }

    // Créer un dépôt/retrait
    public static void create(Connection connection, DepotRetrait depotRetrait) throws SQLException {
        String sql = "INSERT INTO depot_retrait(depot, retrait, date_depot_retrait, id_utilisateur) VALUES (?, ?, CURRENT_DATE, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setDouble(1, depotRetrait.getDepot());
            ps.setDouble(2, depotRetrait.getRetrait());
            ps.setInt(3, depotRetrait.getIdUtilisateur());
            ps.executeUpdate();
        }
    }

    // Lire un dépôt/retrait par son ID
    public static DepotRetrait read(Connection connection, int idDepotRetrait) throws SQLException {
        String sql = "SELECT * FROM depot_retrait WHERE id_depot_retrait = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, idDepotRetrait);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new DepotRetrait(
                        rs.getInt("id_depot_retrait"),
                        rs.getDouble("depot"),
                        rs.getDouble("retrait"),
                        rs.getDate("date_depot_retrait"),  // Récupère la date avec java.sql.Date
                        rs.getInt("id_utilisateur")
                );
            }
        }
        return null; // Retourne null si le dépôt/retrait n'est pas trouvé
    }

    // Mettre à jour un dépôt/retrait
    public static void update(Connection connection, DepotRetrait depotRetrait) throws SQLException {
        String sql = "UPDATE depot_retrait SET depot = ?, retrait = ?, date_depot_retrait = CURRENT_DATE, id_utilisateur = ? WHERE id_depot_retrait = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setDouble(1, depotRetrait.getDepot());
            ps.setDouble(2, depotRetrait.getRetrait());
            ps.setInt(3, depotRetrait.getIdUtilisateur());
            ps.setInt(4, depotRetrait.getIdDepotRetrait());
            ps.executeUpdate();
        }
    }

    // Supprimer un dépôt/retrait par son ID
    public static void delete(Connection connection, int idDepotRetrait) throws SQLException {
        String sql = "DELETE FROM depot_retrait WHERE id_depot_retrait = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, idDepotRetrait);
            ps.executeUpdate();
        }
    }

    // Lire tous les dépôts/retraits
    public static List<DepotRetrait> getAll(Connection connection) throws SQLException {
        List<DepotRetrait> depotRetraits = new ArrayList<>();
        String sql = "SELECT * FROM depot_retrait";
        try (Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                depotRetraits.add(new DepotRetrait(
                        rs.getInt("id_depot_retrait"),
                        rs.getDouble("depot"),
                        rs.getDouble("retrait"),
                        rs.getDate("date_depot_retrait"),  // Utilisation de getDate pour récupérer java.sql.Date
                        rs.getInt("id_utilisateur")
                ));
            }
        }
        return depotRetraits;
    }

    @Override
    public String toString() {
        return "DepotRetrait{" +
                "idDepotRetrait=" + idDepotRetrait +
                ", depot=" + depot +
                ", retrait=" + retrait +
                ", dateDepotRetrait=" + dateDepotRetrait +
                ", idUtilisateur=" + idUtilisateur +
                '}';
    }
}
