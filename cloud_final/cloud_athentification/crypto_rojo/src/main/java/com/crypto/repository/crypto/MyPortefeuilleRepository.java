package com.crypto.repository.crypto;

import com.crypto.model.cryptos.Portefeuille;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface MyPortefeuilleRepository extends JpaRepository<Portefeuille, Integer> {
}
