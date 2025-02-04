package com.crypto.service.utilisateur;

import com.crypto.model.Utilisateur;
import com.crypto.repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
