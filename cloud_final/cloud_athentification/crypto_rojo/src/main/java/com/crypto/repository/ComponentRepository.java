package com.crypto.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.crypto.model.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ComponentRepository {

    private final JdbcTemplate jdbcTemplate;

    public ComponentRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Component> findAll() {
        String sql = "SELECT * FROM component";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Component produit = new Component();
            produit.setId(rs.getInt("idComponent"));
            produit.setNom(rs.getString("nom"));
            produit.setPrix(rs.getDouble("prix"));
            return produit;
        });
    }

    // Insertion d'un composant
    public void insertComponent(Component component) {
        String sql = "INSERT INTO component (nom, prix) VALUES (?, ?)";
        jdbcTemplate.update(sql, component.getNom(), component.getPrix());
    }
} 