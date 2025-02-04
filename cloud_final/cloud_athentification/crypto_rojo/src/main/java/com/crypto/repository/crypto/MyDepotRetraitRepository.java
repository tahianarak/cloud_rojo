package com.crypto.repository.crypto;

import com.crypto.model.cryptos.DepotRetrait;
import com.crypto.model.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MyDepotRetraitRepository extends JpaRepository<DepotRetrait, Integer> {
    @Query("SELECT " +
            "COALESCE(SUM(dr.depot), 0) AS depot, " +
            "COALESCE(SUM(dr.retrait), 0) AS retrait, " +
            "COALESCE((SUM(dr.depot) - SUM(dr.retrait)), 0) AS solde, " +
            "u.idUtilisateur " +
            "FROM Utilisateur u " +
            "LEFT JOIN DepotRetrait dr ON u.idUtilisateur = dr.utilisateur.idUtilisateur " +
            "GROUP BY u.idUtilisateur " +
            "HAVING u = :utilisateur")
    List<Object[]> findSoldeByUtilisateur(@Param("utilisateur") Utilisateur utilisateur);

}
