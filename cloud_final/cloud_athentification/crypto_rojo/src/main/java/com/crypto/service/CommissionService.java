package com.crypto.service;



import com.crypto.model.Commission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

@Service
public class CommissionService {

    private final DataSource dataSource;

    @Autowired
    public CommissionService(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    // Créer une commission
    public void createCommission(Commission commission) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            Commission.create(connection, commission);
        }
    }

    // Lire une commission par son ID
    public Commission getCommissionById(int idCommission) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            return Commission.read(connection, idCommission);
        }
    }

    // Mettre à jour une commission
    public void updateCommission(Commission commission) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            Commission.update(connection, commission);
        }
    }

    // Supprimer une commission par son ID
    public void deleteCommission(int idCommission) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            Commission.delete(connection, idCommission);
        }
    }

    // Lire toutes les commissions
    public List<Commission> getAllCommissions() throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            return Commission.getAll(connection);
        }
    }

    // Récupérer les commissions entre deux dates avec type d'analyse
    public Commission getCommissionByCriteria(Timestamp dateMin, Timestamp dateMax, int typeFiltre, int idCrypto) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            return Commission.getAllCommissionTransationByCriteria(connection, dateMin, dateMax, typeFiltre, idCrypto);
        }
    }

    // Récupérer la dernière commission d'une crypto avant une date
    public Commission getCommissionByIdAndDateBefore(int idCrypto, Timestamp dateAvant) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            return Commission.getCommissionByIdAndDateBefore(connection, idCrypto, dateAvant);
        }
    }
}

