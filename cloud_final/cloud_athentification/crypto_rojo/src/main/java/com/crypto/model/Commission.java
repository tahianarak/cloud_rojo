package com.crypto.model;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Commission {

    public static java.sql.Timestamp parseDatetimeLocal(String datetimeLocalValue) {
        // Le format pour le champ datetime-local est : yyyy-MM-dd'T'HH:mm
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

        // Parse la chaîne datetime-local en un LocalDateTime
        LocalDateTime localDateTime = LocalDateTime.parse(datetimeLocalValue, formatter);

        // Convertir en java.sql.Timestamp
        java.sql.Timestamp sqlTimestamp = java.sql.Timestamp.valueOf(localDateTime);

        return sqlTimestamp;
    }

    private int idCommission;
    private java.sql.Timestamp dateEns;  // Utilisation de java.sql.Timestamp pour la date et l'heure
    private double pourcentage;
    private int idCrypto;
    private String description;

    double valeurCommission;

    Crypto crypto;

    public Crypto getCrypto() {
        return crypto;
    }

    public void setCrypto(Crypto crypto) {
        this.crypto = crypto;
    }

    public double getValeurCommission() {
        return valeurCommission;
    }

    public void setValeurCommission(double valeurCommission) {
        this.valeurCommission = valeurCommission;
    }

    public static Commission getAllCommissionTransationByCriteria(Connection connection, Timestamp dateMin, Timestamp dateMax, int typeFiltre, int idCrypto) {
        String function = "";
        if (typeFiltre == 0) {
            function = "SUM(";
        }
        if (typeFiltre == 1) {
            function = "AVG(";
        }

        String sql = "SELECT " + function + "commission) AS total_commission FROM transaction WHERE 1=1";
        if (idCrypto != -1) {
            sql = sql + " AND id_crypto = " + idCrypto;
        }
        sql = sql + " AND date_debut BETWEEN ? AND ? GROUP BY id_crypto ";
        System.out.println(sql);
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setTimestamp(1, dateMin);
            ps.setTimestamp(2, dateMax);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Commission commission = new Commission();
                commission.setValeurCommission(rs.getDouble("total_commission"));
                return commission;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return null;
    }

    public Commission() {}

    // Constructeur avec ID
    public Commission(int idCommission, java.sql.Timestamp dateEns, double pourcentage, int idCrypto, String description) {
        this.idCommission = idCommission;
        this.dateEns = dateEns;
        this.pourcentage = pourcentage;
        this.idCrypto = idCrypto;
        this.description = description;
    }

    // Constructeur sans ID (pour la création)
    public Commission(java.sql.Timestamp dateEns, double pourcentage, int idCrypto, String description) {
        this.dateEns = dateEns;
        this.pourcentage = pourcentage;
        this.idCrypto = idCrypto;
        this.description = description;
    }

    // Getters et Setters
    public int getIdCommission() {
        return idCommission;
    }

    public void setIdCommission(int idCommission) {
        this.idCommission = idCommission;
    }

    public java.sql.Timestamp getDateEns() {
        return dateEns;
    }

    public void setDateEns(java.sql.Timestamp dateEns) {
        this.dateEns = dateEns;
    }

    public double getPourcentage() {
        return pourcentage;
    }

    public void setPourcentage(double pourcentage) {
        this.pourcentage = pourcentage;
    }

    public int getIdCrypto() {
        return idCrypto;
    }

    public void setIdCrypto(int idCrypto) {
        this.idCrypto = idCrypto;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // Créer une commission
    public static void create(Connection connection, Commission commission) throws SQLException {
        String sql = "INSERT INTO commission(date_ens, pourcentage, id_crypto, description) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setTimestamp(1, commission.getDateEns());  // Utilisation de setTimestamp pour java.sql.Timestamp
            ps.setDouble(2, commission.getPourcentage());
            ps.setInt(3, commission.getIdCrypto());
            ps.setString(4, commission.getDescription());
            ps.executeUpdate();
        }
    }

    // Lire une commission par son ID
    public static Commission read(Connection connection, int idCommission) throws SQLException {
        String sql = "SELECT * FROM commission WHERE id_commission = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, idCommission);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Commission(
                        rs.getInt("id_commission"),
                        rs.getTimestamp("date_ens"),  // Utilisation de getTimestamp pour java.sql.Timestamp
                        rs.getDouble("pourcentage"),
                        rs.getInt("id_crypto"),
                        rs.getString("description")
                );
            }
        }
        return null; // Retourne null si la commission n'est pas trouvée
    }

    // Mettre à jour une commission
    public static void update(Connection connection, Commission commission) throws SQLException {
        String sql = "UPDATE commission SET date_ens = ?, pourcentage = ?, id_crypto = ?, description = ? WHERE id_commission = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setTimestamp(1, commission.getDateEns());  // Utilisation de setTimestamp pour java.sql.Timestamp
            ps.setDouble(2, commission.getPourcentage());
            ps.setInt(3, commission.getIdCrypto());
            ps.setString(4, commission.getDescription());
            ps.setInt(5, commission.getIdCommission());
            ps.executeUpdate();
        }
    }

    // Supprimer une commission par son ID
    public static void delete(Connection connection, int idCommission) throws SQLException {
        String sql = "DELETE FROM commission WHERE id_commission = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, idCommission);
            ps.executeUpdate();
        }
    }

    // Lire toutes les commissions
    public static List<Commission> getAll(Connection connection) throws SQLException {
        List<Commission> commissions = new ArrayList<>();
        String sql = "SELECT * FROM commission";
        try (Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                commissions.add(new Commission(
                        rs.getInt("id_commission"),
                        rs.getTimestamp("date_ens"),  // Utilisation de getTimestamp pour java.sql.Timestamp
                        rs.getDouble("pourcentage"),
                        rs.getInt("id_crypto"),
                        rs.getString("description")
                ));
            }
        }
        return commissions;
    }

    public static Commission getCommissionByIdAndDateBefore(Connection connection, int idCrypto, java.sql.Timestamp dateAvant) throws SQLException {
        String sql = "SELECT * FROM commission WHERE id_crypto = ? AND date_ens < ? ORDER BY date_ens DESC LIMIT 1";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, idCrypto);               // Paramètre pour l'ID de la cryptomonnaie
            ps.setTimestamp(2, dateAvant);         // Paramètre pour la date avant
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Commission(
                        rs.getInt("id_commission"),
                        rs.getTimestamp("date_ens"),           // Utilisation de getTimestamp pour java.sql.Timestamp
                        rs.getDouble("pourcentage"),
                        rs.getInt("id_crypto"),
                        rs.getString("description")
                );
            }
        }
        return null;  // Retourne null si aucune commission n'est trouvée
    }

    @Override
    public String toString() {
        return "Commission{" +
                "idCommission=" + idCommission +
                ", dateEns=" + dateEns +
                ", pourcentage=" + pourcentage +
                ", idCrypto=" + idCrypto +
                ", description='" + description + '\'' +
                '}';
    }
}
