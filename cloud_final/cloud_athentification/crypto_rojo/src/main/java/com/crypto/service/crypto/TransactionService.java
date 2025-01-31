package com.crypto.service.crypto;

import com.crypto.model.Utilisateur;
import com.crypto.model.cryptos.Transaction;
import com.crypto.repository.crypto.MyTransactionRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
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
    @PersistenceContext
    private EntityManager entityManager;

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
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Transaction> query = cb.createQuery(Transaction.class);
        Root<Transaction> transaction = query.from(Transaction.class);

        Predicate predicate = cb.conjunction(); // Crée un "WHERE 1=1" pour ajouter dynamiquement des filtres

        if (dateDebut != null) {
            predicate = cb.and(predicate, cb.greaterThanOrEqualTo(transaction.get("dateDebut"), dateDebut));
        }
        if (dateFin != null) {
            predicate = cb.and(predicate, cb.lessThanOrEqualTo(transaction.get("dateDebut"), dateFin));
        }
        if (crypto != null) {
            predicate = cb.and(predicate, cb.equal(transaction.get("crypto").get("idCrypto"), crypto));
        }
        if (utilisateur != null) {
            predicate = cb.and(predicate, cb.equal(transaction.get("utilisateur").get("idUtilisateur"), utilisateur));
        }

        query.select(transaction).where(predicate);
        return entityManager.createQuery(query).getResultList();
    }
}
