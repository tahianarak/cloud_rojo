package com.crypto.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.sql.Timestamp;
import com.crypto.model.Crypto;
import com.crypto.repository.CryptoRepository;
import com.crypto.repository.DepotRetraitRepository;
import com.google.api.core.ApiFuture;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

@RestController
public class RestCryptoController {

    private final CryptoRepository cryptoRepository;
    
    @Autowired
    public RestCryptoController(CryptoRepository cryptoRepository) {
        this.cryptoRepository = cryptoRepository;
    }

    @GetMapping("/ListCryptoJSON")
    public ResponseEntity<List<Crypto>> listCrypto() {
        try {
            List<Crypto> cryptoList = cryptoRepository.findAll();
            return ResponseEntity.ok(cryptoList);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }
    }

    @Scheduled(fixedRate = 2000)
    public void listCryptoPool() {
        try {
            cryptoRepository.updateCryptoPrices(0, 0);  
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Scheduled(fixedRate = 2000)
    @GetMapping("/syncCrypto")
    public ResponseEntity<String> listCryptoSync() {
        try {
            List<Crypto> cryptoList = cryptoRepository.findAll();
            cryptoRepository.syncToFirebase(cryptoList);
            return ResponseEntity.ok("Liste des cryptos synchronisée avec Firebase !");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Erreur lors de la synchronisation");
        }
    }
    
}
