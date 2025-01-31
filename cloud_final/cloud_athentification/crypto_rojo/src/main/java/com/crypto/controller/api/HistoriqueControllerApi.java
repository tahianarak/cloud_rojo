package com.crypto.controller.api;

import com.crypto.model.Utilisateur;
import com.crypto.model.cryptos.Crypto;
import com.crypto.model.cryptos.Transaction;
import com.crypto.repository.crypto.MyCryptoRepository;
import com.crypto.service.crypto.TransactionService;
import com.crypto.service.utilisateur.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/historique")
public class HistoriqueControllerApi {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private UtilisateurService utilisateurService;

    @Autowired
    private MyCryptoRepository myCryptoRepository;

    @GetMapping
    public ResponseEntity<?> getHistorique(@RequestParam(required = false) LocalDateTime dateDebut,
                                           @RequestParam(required = false) LocalDateTime dateFin,
                                           @RequestParam(required = false) Integer crypto,
                                           @RequestParam(required = false) Integer utilisateur) {
        try {
            List<Transaction> transactions = transactionService.getTransactionsWithFilters(dateDebut, dateFin, crypto, utilisateur);
            return ResponseEntity.ok(transactions);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erreur: " + e.getMessage());
        }
    }

    @GetMapping("/utilisateurs")
    public ResponseEntity<List<Utilisateur>> getUtilisateurs() {
        try {
            List<Utilisateur> utilisateurs = utilisateurService.getAllUtilisateurs();
            return ResponseEntity.ok(utilisateurs);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/cryptos")
    public ResponseEntity<List<Crypto>> getCryptos() {
        try {
            List<Crypto> cryptos = myCryptoRepository.findAll();
            return ResponseEntity.ok(cryptos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
