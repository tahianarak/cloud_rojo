package com.crypto.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class ValeurController {
    @GetMapping("/Valeur")
    public String getValeur(){
        return "Valeur" ;
    }

}
