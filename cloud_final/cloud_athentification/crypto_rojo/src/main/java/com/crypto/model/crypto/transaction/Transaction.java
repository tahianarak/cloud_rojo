package com.crypto.model.crypto.transaction;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Transaction {

    private int idTransaction;
    private double vente;
    private double achat;
    private java.sql.Date dateDebut;  // Utilisation de java.sql.Date
    private int idCrypto;
    private int idUtilisateur;

    // Constructeur
    public Transaction(int idTransaction, double vente, double achat, java.sql.Date dateDebut, int idCrypto, int idUtilisateur) {
        this.idTransaction = idTransaction;
        this.vente = vente;
        this.achat = achat;
        this.dateDebut = dateDebut;
        this.idCrypto = idCrypto;
        this.idUtilisateur = idUtilisateur;
    }

    // Constructeur sans ID (pour la création)
    public Transaction(double vente, double achat, java.sql.Date dateDebut, int idCrypto, int idUtilisateur) {
        this.vente = vente;
        this.achat = achat;
        this.dateDebut = dateDebut;
        this.idCrypto = idCrypto;
        this.idUtilisateur = idUtilisateur;
    }

    // Getters et Setters
    public int getIdTransaction() {
        return idTransaction;
    }

    public void setIdTransaction(int idTransaction) {
        this.idTransaction = idTransaction;
    }

    public double getVente() {
        return vente;
    }

    public void setVente(double vente) {
        this.vente = vente;
    }

    public double getAchat() {
        return achat;
    }

    public void setAchat(double achat) {
        this.achat = achat;
    }

    public java.sql.Date getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(java.sql.Date dateDebut) {
        this.dateDebut = dateDebut;
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

    // Créer une transaction
    public static void create(Connection connection, Transaction transaction) throws SQLException {
        String sql = "INSERT INTO transaction(vente, achat, date_debut, id_crypto, id_utilisateur) VALUES (?, ?, CURRENT_DATE, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setDouble(1, transaction.getVente());
            ps.setDouble(2, transaction.getAchat());
            ps.setInt(3, transaction.getIdCrypto());
            ps.setInt(4, transaction.getIdUtilisateur());
            ps.executeUpdate();
        }
    }

    // Lire une transaction par son ID
    public static Transaction read(Connection connection, int idTransaction) throws SQLException {
        String sql = "SELECT * FROM transaction WHERE id_transaction = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, idTransaction);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Transaction(
                        rs.getInt("id_transaction"),
                        rs.getDouble("vente"),
                        rs.getDouble("achat"),
                        rs.getDate("date_debut"),  // Récupère la date avec java.sql.Date
                        rs.getInt("id_crypto"),
                        rs.getInt("id_utilisateur")
                );
            }
        }
        return null; // Retourne null si la transaction n'est pas trouvée
    }

    // Mettre à jour une transaction
    public static void update(Connection connection, Transaction transaction) throws SQLException {
        String sql = "UPDATE transaction SET vente = ?, achat = ?, date_debut = CURRENT_DATE, id_crypto = ?, id_utilisateur = ? WHERE id_transaction = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setDouble(1, transaction.getVente());
            ps.setDouble(2, transaction.getAchat());
            ps.setInt(3, transaction.getIdCrypto());
            ps.setInt(4, transaction.getIdUtilisateur());
            ps.setInt(5, transaction.getIdTransaction());
            ps.executeUpdate();
        }
    }

    // Supprimer une transaction par son ID
    public static void delete(Connection connection, int idTransaction) throws SQLException {
        String sql = "DELETE FROM transaction WHERE id_transaction = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, idTransaction);
            ps.executeUpdate();
        }
    }

    // Lire toutes les transactions
    public static List<Transaction> getAll(Connection connection) throws SQLException {
        List<Transaction> transactions = new ArrayList<>();
        String sql = "SELECT * FROM transaction";
        try (Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                transactions.add(new Transaction(
                        rs.getInt("id_transaction"),
                        rs.getDouble("vente"),
                        rs.getDouble("achat"),
                        rs.getDate("date_debut"),  // Utilisation de getDate pour récupérer java.sql.Date
                        rs.getInt("id_crypto"),
                        rs.getInt("id_utilisateur")
                ));
            }
        }
        return transactions;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "idTransaction=" + idTransaction +
                ", vente=" + vente +
                ", achat=" + achat +
                ", dateDebut=" + dateDebut +
                ", idCrypto=" + idCrypto +
                ", idUtilisateur=" + idUtilisateur +
                '}';
    }
}
