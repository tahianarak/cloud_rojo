package com.crypto.repository;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.crypto.model.cryptos.Crypto;
import com.crypto.model.cryptos.Transaction;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


@Repository  
public class TransactionRepository {

    private DatabaseReference database;

    public TransactionRepository(JdbcTemplate jdbcTemplate, FirebaseDatabase  database) {
        this.database = database.getReference("transactions");
    }

    public void syncToFirebase(List<Transaction> transactionList , String email ) {
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    
        for (Transaction transaction : transactionList) {
            String transactionId = String.valueOf(transaction.getIdTransaction());
            String dateDebutStr = transaction.getDateDebut().format(dateFormat);
    
            DatabaseReference transactionRef = database.child("historique").child(transactionId);
    
            // Remplacer les anciennes données par les nouvelles
            transactionRef.child("vente").setValueAsync(transaction.getVente() != null ? transaction.getVente().doubleValue() : 0.0);
            transactionRef.child("achat").setValueAsync(transaction.getAchat() != null ? transaction.getAchat().doubleValue() : 0.0);
            transactionRef.child("dateDebut").setValueAsync(dateDebutStr);
            transactionRef.child("email").setValueAsync(email);
    
            Crypto crypto = transaction.getCrypto();
            if (crypto != null) {
                transactionRef.child("crypto").child("idCrypto").setValueAsync(String.valueOf(crypto.getIdCrypto().intValue()));
                transactionRef.child("crypto").child("libelle").setValueAsync(crypto.getLibelle());
                transactionRef.child("crypto").child("prixActuelle").setValueAsync(crypto.getPrixActuelle().doubleValue());
            }
        }
    }
    
    public void syncMontant( double Montant ) {  
        DatabaseReference databaseReference = database.child("montant");
        databaseReference.child("valeur").setValueAsync(Montant);
    }
}
