package com.crypto.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType; 
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.crypto.model.ApiResponse;
import com.crypto.repository.DepotRetraitRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpSession;

import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.http.HttpHeaders;


@Controller
public class DepotRetraitController {

    private final DepotRetraitRepository depotRetraitRepo ;

    @Value("${link_spring}")
    String apiUrl;

    @Autowired
    public DepotRetraitController(DepotRetraitRepository depotRetraitRepo) {
        this.depotRetraitRepo = depotRetraitRepo;
    }

   @PostMapping("/ValiderDepot")
    public String handleDepot(@RequestParam("montant") double money, Model model , HttpSession session) {
       RestTemplate restTemplate = new RestTemplate();
        try {
            MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
            requestBody.add("money", String.valueOf(money));

            requestBody.add("token" , (String) session.getAttribute("token"));
            requestBody.add("idUser" , session.getAttribute("idUser").toString());
            
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(requestBody, headers);
            ResponseEntity<String> responseEntity = restTemplate.postForEntity(apiUrl+"/DepotApi", requestEntity, String.class);
            ObjectMapper objectMapper = new ObjectMapper();
            ApiResponse apiResponse = objectMapper.readValue(responseEntity.getBody(), ApiResponse.class);
            model.addAttribute("message", apiResponse.getData());
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("message", e.getMessage());
        }
        return "Depot" ; 
    }

    @GetMapping("/Depot")
    public String Depot() {
        return "Depot" ; 
    }

   @GetMapping("/Retrait")
    public String retraitMoney(Model model, @ModelAttribute("message") String message) {
        double montant = depotRetraitRepo.getMontantByUtilisateur(1);
        model.addAttribute("montant", montant);
        if (message != null && !message.isEmpty()) {
            model.addAttribute("message", message);
        }
        return "Retrait";
    }

  @PostMapping("/ValiderRetrait")
    public String handleRetraot(@RequestParam("montant") double money, RedirectAttributes redirectAttributes , HttpSession session) {
    RestTemplate restTemplate = new RestTemplate();
    String apiUrl = this.apiUrl+"/RetraitApi";
    try {
        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("money", String.valueOf(money));
        requestBody.add("token" , (String) session.getAttribute("token")); 
        requestBody.add("idUser" , (String)session.getAttribute("idUser"));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(requestBody, headers);
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(apiUrl, requestEntity, String.class);
        
        ObjectMapper objectMapper = new ObjectMapper();
        ApiResponse apiResponse = objectMapper.readValue(responseEntity.getBody(), ApiResponse.class);
        
        redirectAttributes.addFlashAttribute("message", apiResponse.getData());
    } catch (Exception e) {
        redirectAttributes.addFlashAttribute("message", e.getMessage());
        e.printStackTrace();
    }

    // Rediriger vers /Retrait
    return "redirect:/Retrait";
}

}