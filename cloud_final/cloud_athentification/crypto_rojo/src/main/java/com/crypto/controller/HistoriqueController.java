package com.crypto.controller;

import java.time.LocalDateTime;
import java.util.List;

import com.crypto.model.cryptos.Transaction;
import com.crypto.model.Utilisateur;
import com.crypto.model.cryptos.Crypto;
import com.crypto.repository.crypto.MyCryptoRepository;
import com.crypto.service.crypto.TransactionService;
import com.crypto.service.utilisateur.UtilisateurService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/historique")
public class HistoriqueController {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private UtilisateurService utilisateurService;

    @Autowired
    private MyCryptoRepository myCryptoRepository;

    @GetMapping
    public ModelAndView getHistoriques(
            @RequestParam(required = false) LocalDateTime dateDebut,
            @RequestParam(required = false) LocalDateTime dateFin,
            @RequestParam(required = false) Integer crypto,
            @RequestParam(required = false) Integer utilisateur) {

        List<Transaction> transactions = transactionService.getTransactionsWithFilters(dateDebut, dateFin, crypto, utilisateur);
        List<Utilisateur> utilisateurs = utilisateurService.getAllUtilisateurs();
        List<Crypto> cryptos = myCryptoRepository.findAll();

        ModelAndView modelAndView = new ModelAndView("historique");
        modelAndView.addObject("transactions", transactions);
        modelAndView.addObject("utilisateurs", utilisateurs);
        modelAndView.addObject("cryptos", cryptos);

        return modelAndView;
    }
}
