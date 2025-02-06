package com.crypto.repository;

import com.crypto.model.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UtilisateurRepository extends JpaRepository<Utilisateur, Integer> {
    @Query("SELECT u FROM Utilisateur u WHERE u.email LIKE :mail")
    public Utilisateur getByMail(@Param("mail") String mail);
}
