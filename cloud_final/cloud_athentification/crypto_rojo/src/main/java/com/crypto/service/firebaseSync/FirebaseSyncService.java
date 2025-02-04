package com.crypto.service.firebaseSync;

import com.crypto.model.Utilisateur;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Service
public class FirebaseSyncService {

    private final DatabaseReference firebaseRef;

    @Autowired
    public FirebaseSyncService(FirebaseApp firebaseApp) {
        this.firebaseRef = FirebaseDatabase.getInstance(firebaseApp).getReference("Utilisateur");
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void syncUtilisateur(Utilisateur utilisateur) {
        firebaseRef.child(String.valueOf(utilisateur.getIdUtilisateur()))
                .setValueAsync(utilisateur);
    }
}


