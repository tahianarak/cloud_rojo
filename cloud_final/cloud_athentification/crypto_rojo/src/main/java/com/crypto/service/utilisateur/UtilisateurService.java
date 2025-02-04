package com.crypto.service.utilisateur;

import com.crypto.model.Utilisateur;
import com.crypto.repository.UtilisateurRepository;
import com.crypto.service.firebaseSync.FirebaseSyncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UtilisateurService {
    @Autowired
    UtilisateurRepository utilisateurRepository;

    @Autowired
    private FirebaseSyncService firebaseSyncService;

    @Transactional
    public void syncFirebaseUser(Utilisateur utilisateur)
    {
        firebaseSyncService.syncUtilisateur(utilisateur);
    }
    public Utilisateur getById(String id)
    {
        return utilisateurRepository.getById(Integer.valueOf(id));
    }
    public void save (Utilisateur utilisateur)
    {
        syncFirebaseUser(utilisateur);
        utilisateurRepository.save(utilisateur);
    }
}
