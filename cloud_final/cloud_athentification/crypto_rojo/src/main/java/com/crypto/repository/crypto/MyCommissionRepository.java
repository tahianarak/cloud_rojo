package com.crypto.repository.crypto;

import com.crypto.model.cryptos.Commission;
import com.crypto.model.cryptos.Crypto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface MyCommissionRepository extends JpaRepository<Commission,Integer> {
    @Query(" SELECT c FROM Commission c WHERE c.crypto = :crypto AND c.dateEns <= :dateMax ORDER BY c.dateEns DESC ")
    List<Commission> getByCrypto(@Param("crypto") Crypto crypto, @Param("dateMax") Timestamp dateMax);
}
