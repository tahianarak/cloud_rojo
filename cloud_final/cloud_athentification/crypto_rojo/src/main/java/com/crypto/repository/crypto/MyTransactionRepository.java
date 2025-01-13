package com.crypto.repository.crypto;

import com.crypto.model.cryptos.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MyTransactionRepository extends JpaRepository<Transaction, Integer> {
}
