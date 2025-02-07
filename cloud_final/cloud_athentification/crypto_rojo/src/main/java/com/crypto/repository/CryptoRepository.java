package com.crypto.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Repository;

import com.crypto.model.Component;
import com.crypto.model.Crypto;
import com.crypto.model.DepotRetrait;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Random;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

@Repository
public class CryptoRepository {


    @Autowired
    private final JdbcTemplate jdbcTemplate;
    private final DatabaseReference database;


    public CryptoRepository(JdbcTemplate jdbcTemplate, FirebaseDatabase  database) {
        this.jdbcTemplate = jdbcTemplate;
        this.database = database.getReference("cryptos");
    }
    
    public List<Crypto> findAll() {
        String sql = "SELECT * FROM crypto";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Crypto crypto = new Crypto();
            crypto.setIdCrypto(rs.getInt("id_crypto"));
            crypto.setLibelle(rs.getString("libelle"));
            crypto.setPrixActuelle(rs.getDouble("prix_actuelle"));
            crypto.setDateUpdate(rs.getTimestamp("date_update"));
            return crypto;
        });
    }
    
    public void updateCryptoPrice(int idCrypto, double newPrice) {
        String sql = "UPDATE crypto SET prix_actuelle = ?, date_update = ? WHERE id_crypto = ?";
        jdbcTemplate.update(sql, 
            newPrice, 
            new Timestamp(System.currentTimeMillis()), 
            idCrypto);
    }

    public void updateCryptoPrices( int min , int max ) {
        Random random = new Random();
        List<Crypto> cryptoList = this.findAll();
        for (Crypto crypto : cryptoList) {
           // double newPrice = min + (max - min) * random.nextDouble();  
            double percentageChange = 0.9 + (1.1 - 0.9) * random.nextDouble(); 
            double newPrice = crypto.getPrixActuelle() * percentageChange;
            this.updateCryptoPrice(crypto.getIdCrypto() , newPrice);
        }
    }
    public void syncToFirebase(List<Crypto> cryptoList) {
        for (Crypto crypto : cryptoList) {
            String dateUpdateStr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(crypto.getDateUpdate());
            this.database.child(String.valueOf(crypto.getIdCrypto()))
                .child("libelle").setValueAsync(crypto.getLibelle());
            this.database.child(String.valueOf(crypto.getIdCrypto()))
                .child("prixActuelle").setValueAsync(crypto.getPrixActuelle());
            this.database.child(String.valueOf(crypto.getIdCrypto()))
                .child("dateUpdate").setValueAsync(dateUpdateStr);          
        }
    }

} 