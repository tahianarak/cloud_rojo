package com.crypto.service.firebaseSync;

import com.crypto.model.DepotRetraitTemporaire;
import com.crypto.service.DepotRetraitTemporaireService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FirebasePullingServiceDepot {
    @Autowired
    private FirebaseSyncService firebaseSyncService;

    @Autowired
    private DepotRetraitTemporaireService depotRetraitTemporaireService;

    // Planifie l'exécution toutes les 10 secondes
    @Scheduled(fixedRate = 30000) // 10000 ms = 10 secondes
    public void pullDepotRetraits() {
        List<DepotRetraitTemporaire> list = firebaseSyncService.findAll();

        for (DepotRetraitTemporaire depotRetraitTemporaire : list) {
            if (depotRetraitTemporaire.getIn_postgres().equals("0")) {
                depotRetraitTemporaireService.save_only_base(depotRetraitTemporaire);
                firebaseSyncService.updateInPostgres(depotRetraitTemporaire.getKey_firebase());
            }
        }
    }
}
