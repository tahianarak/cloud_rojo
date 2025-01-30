package com.crypto.controller.front;

import com.crypto.service.UserService;
import com.crypto.model.crypto.User;
import com.crypto.service.utilisateur.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/inscription")
public class InscriptionController {

    @Autowired
    UserService userService;

    @Value("${link_spring}")
    String springLink ;

    @Value("${link_symfony}")
    String symfonyLink;
    String symfonyBaseUrl = "/api";

    String app="http://127.0.0.1:7070/inscription/valider";

    @Autowired
    UtilisateurService utilisateurService;

    @PostMapping("/inscrire")
    public ModelAndView validerFormInscription(@RequestParam("email") String email,@RequestParam("nom") String nom,@RequestParam("date_naissance") String date_naissance,@RequestParam("mdp") String mdp, HttpSession session) throws  Exception{
        String url = symfonyLink+symfonyBaseUrl + "/valideInscription";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String,String> userData=new HashMap<>();
        userData.put("email",email);
        userData.put("nom",nom);
        userData.put("date_naissance",date_naissance);
        userData.put("mdp",mdp);
        userData.put("app",this.app);

        RestTemplate restTemplate=new RestTemplate();

        ResponseEntity<Map<String , Object>> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                new HttpEntity<>(userData),
                new ParameterizedTypeReference<Map<String, Object>>() {},
                Map.class
        );

        if (response.getBody().get("status").equals("success"))
        {
            return new ModelAndView("signup");
        }
        throw  new Exception("oups quelque chose s'est mal passée");
    }

    @GetMapping("/valider")
    public ModelAndView validerInscription(@RequestParam("pin") String pin, HttpSession session) {
        String url = UriComponentsBuilder.fromHttpUrl(symfonyLink+symfonyBaseUrl + "/inscriptionEmail")
                .queryParam("pin", pin)
                .toUriString();

        Map<String,String> userData=new HashMap<>();
        userData.put("pin",pin);
        RestTemplate restTemplate=new RestTemplate();
        ResponseEntity<Map<String , Object>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                new HttpEntity<>(userData),
                new ParameterizedTypeReference<Map<String, Object>>() {},
                Map.class
        );
   
        if (((String)response.getBody().get("status")).equals("success"))
        {
            session.setAttribute("token",(String)response.getBody().get("token"));
            session.setAttribute("idUser" , response.getBody().get("id_user").toString());
            session.setAttribute("user",utilisateurService.getById(response.getBody().get("id_user").toString()));
            return  new ModelAndView("home");
        }
        ModelAndView mv= new ModelAndView("error");
        mv.addObject("error",response.getBody().get("error"));
        return mv;

    }

    @GetMapping("/signup")
    public String getSignUp() {
        return "signup";
    }
    

}
