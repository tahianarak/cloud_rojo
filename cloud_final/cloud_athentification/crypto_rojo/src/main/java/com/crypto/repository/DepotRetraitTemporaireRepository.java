package com.crypto.repository;

import com.crypto.model.DepotRetraitTemporaire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepotRetraitTemporaireRepository extends JpaRepository<DepotRetraitTemporaire,Integer> {
}
