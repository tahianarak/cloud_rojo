package com.crypto.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.ArrayList;
import java.util.List;
import com.crypto.model.Crypto;
import com.crypto.repository.CryptoRepository;
import com.crypto.repository.DepotRetraitRepository;

@Controller
public class CryptoControllers {
    private final CryptoRepository cryptoRepository; 
    @Autowired
    public CryptoControllers(CryptoRepository cryptoRepository) {
        this.cryptoRepository = cryptoRepository;
    } 
    @GetMapping("/ListCrypto")
    public String AllCrypto( Model model ) {
        return "Crypto";
    }  
}
