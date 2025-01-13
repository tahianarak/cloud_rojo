package com.crypto.service.crypto;

import com.crypto.repository.crypto.MyPortefeuilleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PortefeuilleService {
    @Autowired
    MyPortefeuilleRepository myPortefeuilleRepository;
}
