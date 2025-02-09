package com.crypto.service;

import com.crypto.model.SessionUtilisateur;
import com.crypto.repository.SessionUtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SessionUtilisateurService {
    @Autowired
    SessionUtilisateurRepository sessionUtilisateurRepository;
    public void deleteToken(String token)
    {
        SessionUtilisateur sessionUtilisateur = sessionUtilisateurRepository.getByToken(token);
        if (sessionUtilisateur != null){
            sessionUtilisateurRepository.delete(sessionUtilisateur);
        }
    }
}
