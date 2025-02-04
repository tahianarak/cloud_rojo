package com.crypto.service.crypto;

import com.crypto.model.cryptos.Crypto;
import com.crypto.model.cryptos.DepotRetrait;
import com.crypto.model.cryptos.Portefeuille;
import com.crypto.model.cryptos.Transaction;
import com.crypto.model.Utilisateur;
import com.crypto.repository.crypto.MyCryptoRepository;
import com.crypto.repository.crypto.MyDepotRetraitRepository;
import com.crypto.repository.crypto.MyPortefeuilleRepository;
import com.crypto.repository.crypto.MyTransactionRepository;
import com.crypto.repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Service
public class DepotRetraitService {
    @Autowired
    MyDepotRetraitRepository myDepotRetraitRepository;

    @Autowired
    UtilisateurRepository utilisateurRepository;

    @Autowired
    MyCryptoRepository myCryptoRepository;

    @Autowired
    MyPortefeuilleRepository myPortefeuilleRepository;

    @Autowired
    MyTransactionRepository myTransactionRepository;
    public DepotRetrait findSoldeByUtilisateur(Utilisateur utilisateur)
    {
        Object[] depotArray = myDepotRetraitRepository.findSoldeByUtilisateur(utilisateur).get(0);
        System.out.println(depotArray[0]);
        System.out.println(depotArray[1]);
        System.out.println(depotArray[2]);
        System.out.println(depotArray[3]);

        DepotRetrait depotRetrait = new DepotRetrait();
        depotRetrait.setDepot((BigDecimal) depotArray[0]);
        depotRetrait.setRetrait((BigDecimal) depotArray[1]);
        depotRetrait.setSolde((BigDecimal) depotArray[2]);
        depotRetrait.setUtilisateur(utilisateurRepository.getById(Integer.valueOf(depotArray[3].toString())));

        return depotRetrait;
    }
    public boolean checkSolde(Utilisateur utilisateur,Double vola)
    {
        DepotRetrait depotRetrait = findSoldeByUtilisateur(utilisateur);
        if (vola.doubleValue() > depotRetrait.getSolde().doubleValue())
        {
            return false;
        }
        return true;
    }

    public void acheteCrypto(Utilisateur utilisateur,Crypto crypto,Double quantities,Double commission)throws Exception
    {
        if (checkSolde(utilisateur,(crypto.getPrixActuelle().doubleValue()*quantities)) == false)
        {
            throw new Exception("Money Insuffisant");
        }

        DepotRetrait depotRetrait = new DepotRetrait();
        depotRetrait.setDateDepotRetrait(LocalDateTime.now());
        depotRetrait.setUtilisateur(utilisateur);
        depotRetrait.setDepot(BigDecimal.valueOf(0.0));
        depotRetrait.setRetrait(BigDecimal.valueOf(crypto.getPrixActuelle().doubleValue()*quantities));

        myDepotRetraitRepository.save(depotRetrait);
        System.out.println("retrait done");

        Portefeuille portefeuille = new Portefeuille();
        portefeuille.setCrypto(crypto);
        portefeuille.setDateEns(LocalDateTime.now());
        portefeuille.setUtilisateur(utilisateur);
        portefeuille.setEntree(BigDecimal.valueOf(quantities));
        portefeuille.setSortie(BigDecimal.valueOf(0));

        myPortefeuilleRepository.save(portefeuille);
        System.out.println("portefeuille done");


        Transaction transaction = new Transaction();
        transaction.setCrypto(crypto);
        transaction.setAchat(BigDecimal.valueOf(crypto.getPrixActuelle().doubleValue()*quantities));
        transaction.setVente(BigDecimal.valueOf(0));
        transaction.setUtilisateur(utilisateur);
        transaction.setDateDebut(LocalDateTime.now());
        transaction.setCommission(BigDecimal.valueOf(commission));

        myTransactionRepository.save(transaction);
        System.out.println("transaction done");

    }
}
