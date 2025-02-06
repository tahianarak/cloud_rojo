package com.crypto.service;

import com.crypto.model.crypto.Cryptos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

@Service
public class CryptoService {

    @Autowired
    DataSource dataSource;


    public List<Cryptos> getAllCrypto()throws  Exception
    {
        List<Cryptos> ans=new ArrayList<>();
        try(Connection connection=dataSource.getConnection())
        {
            ans= Cryptos.getAll(connection);
        }
        return ans ;
    }
}
