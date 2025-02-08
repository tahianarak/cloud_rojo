package com.crypto.service;

import com.crypto.model.SessionUtilisateur;
import com.crypto.repository.SessionUtilisateurRepository;
import org.springframework.stereotype.Service;

@Service
public class SessionUtilisateurService {
    SessionUtilisateurRepository sessionUtilisateurRepository;
    public void deleteToken(String token)
    {
        SessionUtilisateur sessionUtilisateur = sessionUtilisateurRepository.getByToken(token);
        sessionUtilisateurRepository.delete(sessionUtilisateur);
    }
}
