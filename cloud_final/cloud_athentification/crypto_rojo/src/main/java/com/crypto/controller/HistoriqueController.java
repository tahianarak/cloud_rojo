package com.crypto.controller;

import com.crypto.model.Utilisateur;
import com.crypto.model.crypto.Cryptos;
import com.crypto.model.cryptos.Transaction;
import com.crypto.service.CryptoService;
import com.crypto.service.crypto.TransactionService;
import com.crypto.service.utilisateur.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class HistoriqueController {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private UtilisateurService utilisateurService;

    @Autowired
    private CryptoService cryptoService;

    @GetMapping("/historique")
    public ModelAndView getHistorique(@RequestParam(required = false) LocalDateTime dateDebut,
                                      @RequestParam(required = false) LocalDateTime dateFin,
                                      @RequestParam(required = false) Integer crypto,
                                      @RequestParam(required = false) Integer utilisateur) throws Exception {

        List<Transaction> transactions = transactionService.getTransactionsWithFilters(dateDebut, dateFin, crypto, utilisateur);
        List<Utilisateur> utilisateurs = utilisateurService.getAllUtilisateurs();
        List<Cryptos> cryptos = cryptoService.getAllCrypto();

        ModelAndView modelAndView = new ModelAndView("historique");
        modelAndView.addObject("transactions", transactions);
        modelAndView.addObject("utilisateurs", utilisateurs);
        modelAndView.addObject("cryptos", cryptos);

        return modelAndView;
    }
}
