package com.crypto.model;

import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ModulCrypto {
    public ModulCrypto () { } 

    public void generateRandomEvery10Seconds( int timeRand , int min, int max) {
        if (min > max) {
            throw new IllegalArgumentException("Le minimum ne peut pas être supérieur au maximum");
        }
        Random random = new Random();

        // Créer un ScheduledExecutorService pour exécuter la tâche toutes les 10 secondes
        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
        Runnable task = () -> {
            int randomNumber = random.nextInt(max - min + 1) + min;
            System.out.println("Nombre aléatoire généré : " + randomNumber);
        };
        scheduler.scheduleAtFixedRate(task, 0, timeRand , TimeUnit.SECONDS);
    }
}
