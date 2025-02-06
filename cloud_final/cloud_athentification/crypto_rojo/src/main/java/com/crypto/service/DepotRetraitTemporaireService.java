package com.crypto.service;

import com.crypto.model.DepotRetraitTemporaire;
import com.crypto.repository.DepotRetraitRepository;
import com.crypto.repository.DepotRetraitTemporaireRepository;
import com.crypto.service.firebaseSync.FirebaseSyncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepotRetraitTemporaireService {
    @Autowired
    DepotRetraitTemporaireRepository depotRetraitTemporaireRepository;

    @Autowired
    private FirebaseSyncService firebaseSyncService;
    public List<DepotRetraitTemporaire> getAll(){
        return depotRetraitTemporaireRepository.findAll();
    }

    public List<DepotRetraitTemporaire> getDepoTempt() {
        return depotRetraitTemporaireRepository.findDepotTemp();
    }

    public List<DepotRetraitTemporaire> getRetraitTemp() {
        return depotRetraitTemporaireRepository.findRetraitTemp();
    }
    public DepotRetraitTemporaire getById(String id)
    {
        return depotRetraitTemporaireRepository.getById(Integer.valueOf(id));
    }
    public void save(DepotRetraitTemporaire depotRetraitTemporaire)
    {
        depotRetraitTemporaireRepository.save(depotRetraitTemporaire);
        firebaseSyncService.syncDepotRetraitTemporaire(depotRetraitTemporaire);
    }

    public void save_only_base(DepotRetraitTemporaire depotRetraitTemporaire)
    {
        depotRetraitTemporaireRepository.save(depotRetraitTemporaire);
    }

    public void delete(DepotRetraitTemporaire depotRetraitTemporaire)
    {
        depotRetraitTemporaireRepository.delete(depotRetraitTemporaire);
        firebaseSyncService.deleteDepotRetraitTemporaire(depotRetraitTemporaire);
    }
}
