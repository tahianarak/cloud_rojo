package com.crypto.service.crypto;

import com.crypto.repository.crypto.MyTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {
    @Autowired
    MyTransactionRepository myTransactionRepository;
}
