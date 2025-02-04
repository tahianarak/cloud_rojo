package com.crypto.service.firebaseSync;

import com.crypto.model.Utilisateur;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Service
public class FirebaseSyncService {

    private final DatabaseReference firebaseRef;

    public FirebaseSyncService() {
        this.firebaseRef = FirebaseDatabase.getInstance().getReference("Utilisateur");
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void syncUtilisateur(Utilisateur utilisateur) {
        firebaseRef.child(String.valueOf(utilisateur.getIdUtilisateur()))
                .setValueAsync(utilisateur);
    }
}

