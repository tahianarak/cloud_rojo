package com.crypto.service.firebaseDany;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;
import org.springframework.web.util.UriComponentsBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;

@Service
public class FirebaseServiceIns {

    private final RestTemplate restTemplate;
    private final String firebaseApiKey;

    public FirebaseServiceIns( @Value("${firebase.apiKey}") String firebaseApiKey) {
        this.restTemplate = new RestTemplate();
        this.firebaseApiKey = firebaseApiKey;
    }

    // Méthode pour inscrire un utilisateur
    public String signUp(String email, String password) {
        String uri = UriComponentsBuilder.fromHttpUrl("https://identitytoolkit.googleapis.com/v1/accounts:signUp")
                .queryParam("key", firebaseApiKey)
                .toUriString();



        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HashMap<String,String> ans=new HashMap<>();
        ans.put("email",email);
        ans.put("password",password);

        HttpEntity<HashMap> entity = new HttpEntity<>(ans, headers);
        ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, entity, String.class);

        return response.getBody();
    }

    // Méthode pour connecter un utilisateur
    public String signIn(String email, String password) {
        String uri = UriComponentsBuilder.fromHttpUrl("https://identitytoolkit.googleapis.com/v1/accounts:signInWithPassword")
                .queryParam("key", firebaseApiKey)
                .toUriString();

        SignInRequest signInRequest = new SignInRequest(email, password);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<SignInRequest> entity = new HttpEntity<>(signInRequest, headers);
        ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, entity, String.class);

        return response.getBody();
    }

    // Méthode pour mettre à jour le profil de l'utilisateur
    public String updateProfile(String idToken, String displayName, String photoUrl) {
        String uri = UriComponentsBuilder.fromHttpUrl("https://identitytoolkit.googleapis.com/v1/accounts:update")
                .queryParam("key", firebaseApiKey)
                .toUriString();

        UpdateProfileRequest updateProfileRequest = new UpdateProfileRequest(idToken, displayName, photoUrl);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<UpdateProfileRequest> entity = new HttpEntity<>(updateProfileRequest, headers);
        ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, entity, String.class);

        return response.getBody();
    }

    // Requête pour l'inscription
    public static class SignUpRequest {
        private String email;
        private String password;
        private boolean returnSecureToken = true;

        public SignUpRequest(String email, String password) {
            this.email = email;
            this.password = password;
        }

        // Getters et setters
    }

    // Requête pour la connexion
    public static class SignInRequest {
        private String email;
        private String password;
        private boolean returnSecureToken = true;

        public SignInRequest(String email, String password) {
            this.email = email;
            this.password = password;
        }

        // Getters et setters
    }

    // Requête pour mettre à jour le profil
    public static class UpdateProfileRequest {
        private String idToken;
        private String displayName;
        private String photoUrl;
        private boolean returnSecureToken = true;

        public UpdateProfileRequest(String idToken, String displayName, String photoUrl) {
            this.idToken = idToken;
            this.displayName = displayName;
            this.photoUrl = photoUrl;
        }

        // Getters et setters
    }
}
