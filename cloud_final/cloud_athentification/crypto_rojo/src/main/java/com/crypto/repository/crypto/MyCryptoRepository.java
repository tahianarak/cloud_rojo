package com.crypto.repository.crypto;

import com.crypto.model.cryptos.Crypto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MyCryptoRepository extends JpaRepository<Crypto, Integer> {
}
