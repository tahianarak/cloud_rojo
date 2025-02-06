package com.crypto.repository;

import com.crypto.model.DepotRetraitTemporaire;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DepotRetraitTemporaireRepository extends JpaRepository<DepotRetraitTemporaire,Integer> {
        @Query("SELECT d FROM DepotRetraitTemporaire d WHERE d.depot IS NOT NULL AND d.retrait = 0 ")
        List<DepotRetraitTemporaire> findDepotTemp();
    
        @Query("SELECT d FROM DepotRetraitTemporaire d WHERE d.retrait IS NOT NULL AND d.depot = 0")
        List<DepotRetraitTemporaire> findRetraitTemp();
}
