package com.crypto.service;

import com.crypto.model.DepotRetraitTemporaire;
import com.crypto.repository.DepotRetraitRepository;
import com.crypto.repository.DepotRetraitTemporaireRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepotRetraitTemporaireService {
    @Autowired
    DepotRetraitTemporaireRepository depotRetraitTemporaireRepository;
    public List<DepotRetraitTemporaire> getAll(){
        return depotRetraitTemporaireRepository.findAll();
    }

    public DepotRetraitTemporaire getById(String id)
    {
        return depotRetraitTemporaireRepository.getById(Integer.valueOf(id));
    }
    public void save(DepotRetraitTemporaire depotRetraitTemporaire)
    {
        depotRetraitTemporaireRepository.save(depotRetraitTemporaire);
    }

    public void delete(DepotRetraitTemporaire depotRetraitTemporaire)
    {
        depotRetraitTemporaireRepository.delete(depotRetraitTemporaire);
    }

}
