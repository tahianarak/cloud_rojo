package com.crypto.service.crypto;

import com.crypto.model.cryptos.Commission;
import com.crypto.model.cryptos.Crypto;
import com.crypto.repository.crypto.MyCommissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MyCommissionService {
    @Autowired
    MyCommissionRepository commissionRepository;

    public double commission(Crypto crypto)
    {
        List<Commission> commissions = commissionRepository.getByCrypto(crypto);
        return commissions.get(0).getPourcentage();
    }
}
