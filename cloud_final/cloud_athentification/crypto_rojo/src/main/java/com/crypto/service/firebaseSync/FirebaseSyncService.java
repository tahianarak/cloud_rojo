package com.crypto.service.firebaseSync;

import com.crypto.model.DepotRetraitTemporaire;
import com.crypto.service.utilisateur.UtilisateurService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionalEventListener;
import org.springframework.transaction.event.TransactionPhase;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@Service
public class FirebaseSyncService {

    private final DatabaseReference firebaseRef;
    private final ObjectMapper objectMapper;


    @Autowired
    UtilisateurService utilisateurService;

    public FirebaseSyncService(FirebaseDatabase firebaseDatabase) {
        this.firebaseRef = firebaseDatabase.getReference("DepotRetraitTemporaire");
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void syncDepotRetraitTemporaire(DepotRetraitTemporaire depotRetraitTemporaire) {
        try {
            Map<String, Object> data = objectMapper.convertValue(depotRetraitTemporaire, Map.class);
            data.put("mail",depotRetraitTemporaire.getUtilisateur().getEmail());
            data.put("in_postgres",1);
            data.remove("idDepotRetrait");
            data.remove("utilisateur");

            firebaseRef.child(String.valueOf(depotRetraitTemporaire.getIdDepotRetrait()))
                    .setValueAsync(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void deleteDepotRetraitTemporaire(DepotRetraitTemporaire depotRetraitTemporaire) {
        try {
            firebaseRef.child(String.valueOf(depotRetraitTemporaire.getIdDepotRetrait()))
                    .removeValueAsync();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void updateInPostgres(String key) {
        DatabaseReference childRef = firebaseRef.child(key);
        Map<String, Object> updates = new HashMap<>();
        updates.put("in_postgres", 1);
        childRef.updateChildrenAsync(updates);
    }

    // Méthode pour trouver tous les éléments
    public List<DepotRetraitTemporaire> findAll() {
        CountDownLatch latch = new CountDownLatch(1);
        List<DepotRetraitTemporaire> depotRetraitList = new ArrayList<>();

        firebaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    String key = child.getKey();
                    Object value = child.getValue();
                    try {
                        if (value instanceof Map) {
                            Map<String, Object> data = (Map<String, Object>) value;
                            String mail = (String) data.get("email");
                            String depot = String.valueOf(data.get("depot"));
                            String retrait = String.valueOf(data.get("retrait"));
                            String date = (String) data.get("dateDepotRetrait");
                            String in_postgres = String.valueOf(data.get("in_postgres"));

                            DepotRetraitTemporaire depotRetraitTemporaire = new DepotRetraitTemporaire();
                            depotRetraitTemporaire.setKey_firebase(key);
                            depotRetraitTemporaire.setDepot(BigDecimal.valueOf(Double.valueOf(depot)));
                            depotRetraitTemporaire.setRetrait(BigDecimal.valueOf(Double.valueOf(retrait)));
                            depotRetraitTemporaire.setUtilisateur(utilisateurService.getByMail(mail));
                            depotRetraitTemporaire.setDateDepotRetrait(DateUtil.getDateString(date));
                            depotRetraitTemporaire.setIn_postgres(in_postgres);

                            depotRetraitList.add(depotRetraitTemporaire);
                        }
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                        e.printStackTrace();
                    }
                }
                latch.countDown();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("error : " + databaseError.getMessage());
                latch.countDown();  // To ensure latch.countDown() is called even on error
            }
        });

        try {
            latch.await();  // Wait until data is fetched
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Return the list once data is fetched
        return depotRetraitList;
    }
}

