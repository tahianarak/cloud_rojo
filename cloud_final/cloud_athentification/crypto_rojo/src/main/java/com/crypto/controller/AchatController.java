package com.crypto.controller;

import com.crypto.Request.AcheterCryptoRequest;
import com.crypto.model.crypto.Mapper;
import com.crypto.model.cryptos.Crypto;
import com.crypto.model.Utilisateur;

import com.crypto.service.UserService;
import com.crypto.service.crypto.MyCryptoService;
import com.crypto.service.utilisateur.UtilisateurService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;


import java.io.IOException;
import java.io.PrintWriter;

import java.util.List;

@Controller
public class AchatController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    UtilisateurService utilisateurService;

    @Autowired
    MyCryptoService myCryptoService;

    @Autowired
    UserService userService;

    String url = "http://localhost:7070/api/crypto";
    String urlUser="http://localhost:8000/api/verify/token";

    public String getUrl() {
        return url;
    }

    //get form acheter
    @GetMapping("/crypto/getFormAcheter")
    public String getFormAcheter(HttpServletRequest request)
    {
        ResponseEntity<List<Crypto>> response = new RestTemplate().exchange(
                this.getUrl()+"/liste",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Crypto>>() {}
        );

        if (response.getStatusCode().is2xxSuccessful()) {
            List<Crypto> cryptoList = response.getBody();
            request.setAttribute("cryptoList", cryptoList);
        } else {
            request.setAttribute("error", "Erreur lors de la récupération des cryptos");
        }
        return "AcheterCrypto";
    }

    //acheter crypto
    @PostMapping("/crypto/acheterCrypto")
    public void acheterCrypto(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String redirectString = "/crypto/getFormAcheter";
        try {
            String token=(String) request.getSession().getAttribute("token");
            if (this.userService.verfiyValidityOfToken(token,this.urlUser)==false) {
               throw new Exception("NO TOKEN VALIDE");
            }

            AcheterCryptoRequest acheterCryptoRequest = new AcheterCryptoRequest();

            Utilisateur utilisateur = utilisateurService.getById(request.getSession().getAttribute("idUser").toString());
            Crypto crypto = myCryptoService.getById(request.getParameter("crypto"));
            Double quantities = Double.parseDouble(request.getParameter("quantities"));

            acheterCryptoRequest.setUtilisateur(utilisateur);
            acheterCryptoRequest.setCrypto(crypto);
            acheterCryptoRequest.setQuantities(quantities);
            System.out.println(this.getUrl() + "/acheter");
            ResponseEntity<String> responsePost = new RestTemplate().postForEntity(this.getUrl() + "/acheter", acheterCryptoRequest, String.class);
            PrintWriter writer = response.getWriter();
            writer.println("<script type='text/javascript'>"
                    + "alert('Achat Success !"+responsePost.getBody()+"');"
                    + "window.location.href = '" + redirectString + "';"
                    + "</script>");
        }
        catch (Exception e)
        {
            PrintWriter writer = response.getWriter();
            writer.println("<script type='text/javascript'>"
                    + "alert('Achat refusé:"+e.getMessage()+"');"
                    + "window.location.href = '" + redirectString + "';"
                    + "</script>");
        }
    }

}
