package com.crypto.Request;

import com.crypto.model.cryptos.Crypto;
import com.crypto.model.Utilisateur;

//Voici AcheterCryptoRequest qui represente le Json body dans api AchatControllerAPI
public class AcheterCryptoRequest {
    private Utilisateur utilisateur;
    private Crypto crypto;
    private Double quantities;

    // Getters and setters
    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public Crypto getCrypto() {
        return crypto;
    }

    public void setCrypto(Crypto crypto) {
        this.crypto = crypto;
    }

    public Double getQuantities() {
        return quantities;
    }

    public void setQuantities(Double quantities)throws Exception {
        if (quantities<=0)
        {
            throw new Exception("Quantite Invalide");
        }
        this.quantities = quantities;
    }
}
