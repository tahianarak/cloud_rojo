package com.crypto.service.firebaseSync;

import com.crypto.model.DepotRetraitTemporaire;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionalEventListener;
import org.springframework.transaction.event.TransactionPhase;

import java.util.Map;

@Service
public class FirebaseSyncService {

    private final DatabaseReference firebaseRef;
    private final ObjectMapper objectMapper;

    public FirebaseSyncService(FirebaseDatabase firebaseDatabase) {
        this.firebaseRef = firebaseDatabase.getReference("DepotRetraitTemporaire");
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void syncDepotRetraitTemporaire(DepotRetraitTemporaire depotRetraitTemporaire) {
        try {
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
