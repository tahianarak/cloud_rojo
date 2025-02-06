package com.crypto.controller.api;


import com.crypto.model.crypto.Mapper;
import com.crypto.model.crypto.transaction.Vente;
import com.crypto.service.UserService;
import com.crypto.service.VenteService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class VenteApi
{

    @Autowired
    VenteService venteService;

    @Autowired
    UserService userService;

    @Value("${link_symfony}")
    String url;
    @PostMapping("/create")
    @ResponseBody
    public HashMap<String,Object> createTransaction(@RequestBody Map<String, Object> request,HttpServletRequest requestServ) throws SQLException {
        String token=(String) request.get("token");
        if (this.userService.verfiyValidityOfToken(token,this.url+"/api/verify/token")==false) {
            return Mapper.resolve(null,"vous n'avez pas de session en cours");
        }
        int idCrypto = Integer.valueOf((String) request.get("idCrypto"));
        int idUtilisateur = Integer.valueOf((String) request.get("idUtilisateur"));
        double quantite = Double.valueOf((String) request.get("quantite"));

        try {
            this.venteService.createVente(idUtilisateur,idCrypto,quantite);
            return Mapper.resolve("transaction effectuée avec succès",null);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return Mapper.resolve(null,e.getMessage());
        }
    }
}

