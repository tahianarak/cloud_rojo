package com.crypto.controller.front;

import com.crypto.model.crypto.User;
import com.crypto.service.UserService;
import com.crypto.service.utilisateur.UtilisateurService;
import jakarta.servlet.http.HttpServletRequest;
import jdk.jshell.execution.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.Banner;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


@Controller
@RequestMapping("/auth")
public class AuthentificationController {

    

    @Autowired
    UserService userService; 
    @Value("${link_symfony}")
    String uri ; 

    String  symfonyBaseUrl =  "/api";

    @Autowired
    UtilisateurService utilisateurService;

    @GetMapping("/")
    public String first(HttpServletRequest request)
    {
        request.getSession().invalidate();
        return "redirect:/auth/loginPage";
    }

    @GetMapping("/deconnect")
    public String deconect(HttpServletRequest request)
    {
        request.getSession().invalidate();
        return "redirect:/auth/loginPage";
    }

    @GetMapping("/loginPage")
    public ModelAndView loginPage()
    {
        return new ModelAndView("verifyLogin");
    }

    @GetMapping("/verifyPinPage")
    public ModelAndView verifyPinPage() {

        return new ModelAndView("verifyPin");
    }

    @PostMapping("/login")
    public ModelAndView login(@RequestParam("email") String email,@RequestParam("mdp") String mdp, HttpSession session) {

        String url = this.uri + symfonyBaseUrl + "/verify/login";
   

        Map<String,Object> credentials=new HashMap<>();
        credentials.put("email",email);
        credentials.put("mdp",mdp);
        session.setAttribute("email",email);
        RestTemplate restTemplate=new RestTemplate();
        ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                new HttpEntity<>(credentials),
                new ParameterizedTypeReference<Map<String, Object>>() {},
                Map.class
        );

        if (response.getBody().get("status").equals("success")) {
           ModelAndView mv=new ModelAndView("verifyPin");
            return mv;
        } else {
            ModelAndView mv=new ModelAndView("verifyLogin");
            return mv;
        }
    }

    @PostMapping("/verifyPin")
    public ModelAndView verifyPin(@RequestParam("pin") String pin, HttpSession session) {
        String url = this.uri + symfonyBaseUrl + "/verify/pin";

        Map<String,String> pinData=new HashMap<>();
        pinData.put("email",(String)session.getAttribute("email"));
        pinData.put("pin",pin);
        RestTemplate restTemplate=new RestTemplate();
        ResponseEntity<Map<String, String>> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                new HttpEntity<>(pinData),
                new ParameterizedTypeReference<Map<String, String>>() {},
                Map.class
        );

        if (response.getBody().get("status").equals("success"))
        {
            System.out.println(response.getBody());
            session.setAttribute("token",response.getBody().get("token"));
            session.setAttribute("idUser",response.getBody().get("id_user"));
            session.setAttribute("user",utilisateurService.getById(response.getBody().get("id_user")));
            return  new ModelAndView("home");
        }

        ModelAndView mv= new ModelAndView("error");
        mv.addObject("error",response.getBody().get("error"));
        return mv;
    }

    @GetMapping("/home")
    public String getMethodName() {
        return "home";  
    }
    

}
