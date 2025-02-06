package com.crypto.model.crypto;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

public class Mapper {



    public static String sendPostRequest(String url, HashMap<String, String> map, RestTemplate restTemplate) {
        // Préparation des en-têtes pour indiquer que le corps est au format JSON
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Préparation de la requête avec le corps et les en-têtes
        HttpEntity<HashMap<String, String>> request = new HttpEntity<>(map, headers);

        // Envoi de la requête POST et récupération de la réponse
        ResponseEntity<ResponseData> response = restTemplate.exchange(url, HttpMethod.POST, request, ResponseData.class);

        // Récupérer l'objet ResponseData depuis la réponse
        ResponseData responseData = response.getBody();

        // Vérification de l'erreur
        if (responseData != null && responseData.getError() == null) {
            return "La transaction s'est bien passée";
        } else {
            return responseData != null ? responseData.getError() : "Erreur inconnue";
        }
    }
    public  static HashMap<String,Object> resolve(Object data,String exception){
        HashMap<String,Object> ans=new HashMap<>();
        if(exception!=null){
            ans.put("status","error");
        }
        else
        {
            ans.put("status","success");
        }
        ans.put("data",data);
        ans.put("error",exception);
        return  ans;
    }
}
