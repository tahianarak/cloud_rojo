package com.crypto.service;

import com.crypto.model.crypto.transaction.Vente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;

@Service
public class VenteService
{
    @Autowired
    DataSource dataSource;

    public void createVente(int idUtilisateur,int idCrypto,double quantite)throws  Exception
    {
        try(Connection connect=dataSource.getConnection())
        {
            Vente vnt= new Vente(idUtilisateur,idCrypto,quantite,connect);
            vnt.createObject(connect);
        }
    }
}
