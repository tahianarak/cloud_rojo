package com.crypto.service.crypto;

import com.crypto.model.cryptos.Crypto;
import com.crypto.repository.crypto.MyCryptoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MyCryptoService {

    @Autowired
    MyCryptoRepository myCryptoRepository;

    public List<Crypto> getAll()
    {
        return myCryptoRepository.findAll();
    }
    public Crypto getById(String id)
    {
        return myCryptoRepository.getById(Integer.valueOf(id));
    }
    public void save(Crypto crypto)
    {
        myCryptoRepository.save(crypto);
    }
}
