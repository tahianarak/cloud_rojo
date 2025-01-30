package com.crypto.service.crypto;

import com.crypto.model.Utilisateur;
import com.crypto.model.cryptos.Transaction;
import com.crypto.repository.crypto.MyTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
public class TransactionService {
    @Autowired
    MyTransactionRepository myTransactionRepository;

    public HashMap<Utilisateur,BigDecimal> moneyPortefeuilleDate(LocalDateTime date)
    {
        List<Object[]> filtre = myTransactionRepository.moneyPortefeuilleDate(date);
        HashMap<Utilisateur,BigDecimal> toReturn = new HashMap<Utilisateur,BigDecimal>();
        for (Object[] data : filtre)
        {
            toReturn.put((Utilisateur) data[0], (BigDecimal) data[1]);
        }
        return toReturn;
    }

    public List<Transaction> moneyDate(LocalDateTime date)
    {
        HashMap<Utilisateur,BigDecimal> moneyPertefeuille = moneyPortefeuilleDate(date);
        List<Object[]> filtre = myTransactionRepository.moneyDate(date);
        List<Transaction> toReturn = new ArrayList<Transaction>();
        for (Object[] data : filtre)
        {
            Transaction t = new Transaction();
            t.setUtilisateur((Utilisateur) data[0]);
            t.setAchat((BigDecimal) data[1]);
            t.setVente((BigDecimal) data[2]);
            t.setArgent(moneyPertefeuille.get((Utilisateur) data[0]));
            t.setValeurCryptoReste(BigDecimal.valueOf(t.getAchat().doubleValue() - t.getVente().doubleValue()));
            toReturn.add(t);
        }
        return toReturn;
    }

    public List<Transaction> getTransactionsWithFilters(LocalDateTime dateDebut, LocalDateTime dateFin, Integer crypto, Integer utilisateur) {
        return myTransactionRepository.findTransactionsWithFilters(dateDebut, dateFin, crypto, utilisateur);
    }
}
