package com.crypto.service.crypto;

import com.crypto.model.cryptos.Commission;
import com.crypto.model.cryptos.Crypto;
import com.crypto.repository.crypto.MyCommissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Service
public class MyCommissionService {
    @Autowired
    MyCommissionRepository commissionRepository;

    public double commission(Crypto crypto)
    {
        System.out.println("CRYPTO : "+crypto.getLibelle());
        System.out.println("DATE : "+new Timestamp(new Date().getTime()));
        List<Commission> commissions = commissionRepository.getByCrypto(crypto,new Timestamp(new Date().getTime()));
        return commissions.get(0).getPourcentage();
    }

    public void save(Commission commission)
    {
        commissionRepository.save(commission);
    }
}
