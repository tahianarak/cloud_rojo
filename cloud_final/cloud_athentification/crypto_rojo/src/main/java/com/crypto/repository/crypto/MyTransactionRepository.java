package com.crypto.repository.crypto;

import com.crypto.model.cryptos.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Repository
public interface MyTransactionRepository extends JpaRepository<Transaction, Integer> {
    @Query("SELECT u, " +
            "COALESCE(SUM(CASE WHEN t.dateDebut <= :date THEN t.achat - t.achat*COALESCE(t.commission/100,0) ELSE 0 END), 0) as achat, " +
            "COALESCE(SUM(CASE WHEN t.dateDebut <= :date THEN t.vente - t.achat*COALESCE(t.commission/100,0) ELSE 0 END), 0) as vente " +
            "FROM Utilisateur u " +
            "LEFT JOIN Transaction t ON u = t.utilisateur " +
            "GROUP BY u")
    List<Object[]> moneyDate(@Param("date") LocalDateTime date);

    @Query("SELECT u, " +
            "COALESCE(SUM(CASE WHEN d.dateDepotRetrait <= :date THEN d.depot ELSE 0 END) - SUM(CASE WHEN d.dateDepotRetrait <= :date THEN d.retrait  ELSE 0 END), 0) " +
            "FROM Utilisateur u " +
            "LEFT JOIN DepotRetrait d ON u = d.utilisateur " +
            "GROUP BY u")
    List<Object[]> moneyPortefeuilleDate(@Param("date") LocalDateTime date);

    @Query("SELECT t FROM Transaction t " +
            "JOIN t.utilisateur u " +
            "JOIN t.crypto c " +
            "WHERE (:dateDebut IS NULL OR t.dateDebut >= :dateDebut) " +
            "AND (:dateFin IS NULL OR t.dateDebut <= :dateFin) " +
            "AND (:crypto IS NULL OR c.idCrypto = :crypto) " +
            "AND (:utilisateur IS NULL OR u.idUtilisateur = :utilisateur)")
    List<Transaction> findTransactionsWithFilters(
            @Param("dateDebut") LocalDateTime dateDebut,
            @Param("dateFin") LocalDateTime dateFin,
            @Param("crypto") Integer crypto,
            @Param("utilisateur") Integer utilisateur);


}
