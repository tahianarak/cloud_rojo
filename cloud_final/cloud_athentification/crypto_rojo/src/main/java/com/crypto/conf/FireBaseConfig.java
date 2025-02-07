package com.crypto.conf;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import jakarta.annotation.PostConstruct;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.FileInputStream;
import java.io.IOException;

@Configuration
public class FireBaseConfig {

    @Bean
    public FirebaseApp initializeFirebase() throws IOException {
        try { 
            // Charger le fichier de configuration
            FileInputStream serviceAccount = new FileInputStream("src/main/resources/google-services.json");

            // Configurer Firebase
            FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setDatabaseUrl("https://firstproject-8b46d-default-rtdb.asia-southeast1.firebasedatabase.app/")  
                .build();

            // Vérifier si Firebase est déjà initialisé
            if (FirebaseApp.getApps().isEmpty()) {
                // Initialiser Firebase et retourner l'instance
                return FirebaseApp.initializeApp(options);
            }
            
        return FirebaseApp.getInstance();  // Retourne l'instance existante si déjà initialisée
        }catch (IOException e) {
            e.printStackTrace();
            System.out.println(e.getMessage()) ; 
        }
        return null; 
    }

    @Bean
    public FirebaseDatabase firebaseDatabase(FirebaseApp firebaseApp) {
        return FirebaseDatabase.getInstance(firebaseApp);
    }
}
