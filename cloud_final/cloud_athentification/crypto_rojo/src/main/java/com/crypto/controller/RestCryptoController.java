package com.crypto.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.ArrayList;
import java.util.List;
import com.crypto.model.Crypto;
import com.crypto.repository.CryptoRepository;
import com.crypto.repository.DepotRetraitRepository;

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
            cryptoRepository.updateCryptoPrices(100, 1000);
            List<Crypto> cryptoList = cryptoRepository.findAll();
            return ResponseEntity.ok(cryptoList);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }
    }   
}
