package com.crypto.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.crypto.model.Component;
import com.crypto.model.DepotRetrait;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.sql.Timestamp;

@Repository 
public class DepotRetraitRepository {

    private final JdbcTemplate jdbcTemplate;
    public DepotRetraitRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    } 

     public void insertDepotRetrait(DepotRetrait depotRetrait) {
        String sql = "INSERT INTO depot_retrait (depot, retrait, date_depot_retrait, id_utilisateur) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                depotRetrait.getDepot(),
                depotRetrait.getRetrait(),
                new Timestamp(System.currentTimeMillis()), // Date actuelle
                depotRetrait.getIdUtilisateur()
        );
    }
    public double getMontantByUtilisateur(int idUtilisateur) {
        String sql = "SELECT COALESCE(SUM(depot) - SUM(retrait), 0) AS montant FROM depot_retrait WHERE id_utilisateur = ?";
        return jdbcTemplate.queryForObject(sql, Double.class, idUtilisateur);
    }
    
} 