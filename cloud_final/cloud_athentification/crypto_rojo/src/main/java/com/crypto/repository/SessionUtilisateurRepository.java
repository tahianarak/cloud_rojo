package com.crypto.repository;

import com.crypto.model.SessionUtilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SessionUtilisateurRepository extends JpaRepository<SessionUtilisateur,Integer> {
    @Query("SELECT s FROM SessionUtilisateur s where s.token LIKE :token")
    public SessionUtilisateur getByToken( @Param("token") String token);
}
