package com.crypto.controller.firebase;

import com.crypto.model.DepotRetraitTemporaire;
import com.crypto.service.DepotRetraitTemporaireService;
import com.crypto.service.firebaseSync.FirebaseSyncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class FirebaseController {
    @Autowired
    private FirebaseSyncService firebaseSyncService;

    @Autowired
    DepotRetraitTemporaireService depotRetraitTemporaireService;

    // Par exemple, une méthode pour appeler 'findAll()' dans FirebaseSyncService
    @GetMapping("/firebase-depot")
    public String fetchDepotRetraits() {
        List<DepotRetraitTemporaire> list = firebaseSyncService.findAll();
        for (DepotRetraitTemporaire depotRetraitTemporaire : list)
        {
            if (depotRetraitTemporaire.getIn_postgres().equals("0"))
            {
                depotRetraitTemporaireService.save_only_base(depotRetraitTemporaire);
                firebaseSyncService.updateInPostgres(depotRetraitTemporaire.getKey_firebase());
            }
        }
        return "redirect:/";
    }
}
