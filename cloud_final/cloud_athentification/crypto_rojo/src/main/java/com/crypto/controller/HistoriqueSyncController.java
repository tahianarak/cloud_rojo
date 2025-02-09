package com.crypto.controller;

import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.crypto.model.Crypto;
import com.crypto.model.Utilisateur;
import com.crypto.model.cryptos.Transaction;
import com.crypto.repository.DepotRetraitRepository;
import com.crypto.repository.TransactionRepository;
import com.crypto.repository.UtilisateurRepository;
import com.crypto.service.crypto.TransactionService;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

@RestController
public class HistoriqueSyncController {
    
        private DatabaseReference database;

        @Autowired
        private UtilisateurRepository userRepository; 
        @Autowired
        private TransactionService transactionService;
        @Autowired
        private TransactionRepository transactionRepository ; 
        @Autowired
        private DepotRetraitRepository descRepository ; 

    
        public HistoriqueSyncController( FirebaseDatabase  database ) {
            this.database = database.getReference("email");
        }
        @Scheduled(fixedRate = 2000)
        @GetMapping("/syncEmail")
        public String getEmail() throws InterruptedException {
            CountDownLatch latch = new CountDownLatch(1);
            final StringBuilder email = new StringBuilder();
            database.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        email.append(snapshot.getValue(String.class));
                    }
                    latch.countDown();
                }
                @Override
                public void onCancelled(DatabaseError error) {
                    latch.countDown();
                }
            });
            latch.await(); 
            return email.toString();
        }

        @Scheduled(fixedRate = 2000)
        @GetMapping("/syncHistorique")
        public  ResponseEntity<String> listHistorique() { 
            try {
                String email = getEmail();
                if( email != null ) { 
                     Utilisateur user = userRepository.getByMail(email)  ; 
                     List<Transaction> transactions = transactionService.getTransactionsWithFilters(null, null, null, (int) (long) user.getIdUtilisateur());
                     transactionRepository.syncToFirebase(transactions , email );
                     return ResponseEntity.ok("Liste des transcation synchronisée avec Firebase !");
                }
                return null ; 
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseEntity.status(500).body("Erreur lors de la synchronisation");
            }
        }

        @Scheduled(fixedRate = 2000)
        @GetMapping("/syncMontant")
        public  ResponseEntity<String> getMontantPortefeuil() {
            try {
                String email = getEmail();
                if( email != null ) { 
                     Utilisateur user = userRepository.getByMail(email)  ; 
                     int idUtilisateur = user.getIdUtilisateur().intValue();
                     double portefeuil = descRepository.getMontantByUtilisateur( idUtilisateur ); 
                     transactionRepository.syncMontant(portefeuil);
                     return ResponseEntity.ok("Montant du portefeuille : " + portefeuil);
                }
                return null ; 
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseEntity.status(500).body("Erreur lors de la récupération du montant du portefeuil");
            }
        } 
    }
    