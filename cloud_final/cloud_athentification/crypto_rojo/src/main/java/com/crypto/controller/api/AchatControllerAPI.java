package com.crypto.controller.api;

import com.crypto.Request.AcheterCryptoRequest;
import com.crypto.model.cryptos.Crypto;
import com.crypto.repository.crypto.MyCryptoRepository;
import com.crypto.service.crypto.DepotRetraitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/crypto")
public class AchatControllerAPI {
    @Autowired
    private DepotRetraitService depotRetraitService;

    @Autowired
    private MyCryptoRepository myCryptoRepository;
    @PostMapping("/acheter")
    public ResponseEntity<String> acheterCrypto(
            @RequestBody AcheterCryptoRequest request) {
        //AcheterCryptoRequest est un model presentant le Json envoyé
        try {
            depotRetraitService.acheteCrypto(request.getUtilisateur(), request.getCrypto(), request.getQuantities());
            return ResponseEntity.ok("Cryptos achete success");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/liste")
    public ResponseEntity<List<Crypto>> getListeCrypto() {
        try {
            List<Crypto> cryptos = myCryptoRepository.findAll();
            return ResponseEntity.ok(cryptos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

}
