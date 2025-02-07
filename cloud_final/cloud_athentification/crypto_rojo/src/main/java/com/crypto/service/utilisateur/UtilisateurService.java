package com.crypto.service.utilisateur;

import com.crypto.model.Utilisateur;
import com.crypto.repository.UtilisateurRepository;
import com.crypto.service.firebaseSync.FirebaseSyncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UtilisateurService {
    @Autowired
    UtilisateurRepository utilisateurRepository;


    public Utilisateur getById(String id)
    {
        return utilisateurRepository.getById(Integer.valueOf(id));
    }
    public void save (Utilisateur utilisateur)
    {
        utilisateurRepository.save(utilisateur);
    }

    public List<Utilisateur> getAllUtilisateurs() {return utilisateurRepository.findAll(); }

    public Utilisateur getByMail(String mail)
    {
        return utilisateurRepository.getByMail(mail);
    }
}
