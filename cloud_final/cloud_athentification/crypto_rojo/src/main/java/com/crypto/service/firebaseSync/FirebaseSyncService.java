package com.crypto.service.firebaseSync;

import com.crypto.model.DepotRetraitTemporaire;
import com.crypto.model.Utilisateur;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.Map;

@Service
public class FirebaseSyncService {

    private final DatabaseReference firebaseRef;

    @Autowired
    public FirebaseSyncService(FirebaseApp firebaseApp) {
        this.firebaseRef = FirebaseDatabase.getInstance(firebaseApp).getReference("DepotRetraitTemporaire");
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void syncDepotRetraitTemporaire(DepotRetraitTemporaire depotRetraitTemporaire) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Object> data = objectMapper.convertValue(depotRetraitTemporaire, Map.class);
            data.put("idUtilisateur", depotRetraitTemporaire.getUtilisateur().getIdUtilisateur());
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
}


