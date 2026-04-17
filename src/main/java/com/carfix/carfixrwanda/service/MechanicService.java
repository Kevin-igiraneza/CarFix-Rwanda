package com.carfix.carfixrwanda.service;

import com.carfix.carfixrwanda.enums.VerificationStatus;
import com.carfix.carfixrwanda.model.Mechanic;
import com.carfix.carfixrwanda.repository.MechanicRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MechanicService {

    private final MechanicRepository mechanicRepository;

    public MechanicService(MechanicRepository mechanicRepository) {
        this.mechanicRepository = mechanicRepository;
    }

    public List<Mechanic> getAllMechanics() {
        return mechanicRepository.findAll();
    }

    public Mechanic saveMechanic(Mechanic mechanic) {
        return mechanicRepository.save(mechanic);
    }

    public void updateVerificationStatus(Long mechanicId, VerificationStatus status) {
        Mechanic mechanic = mechanicRepository.findById(mechanicId)
                .orElseThrow(() -> new IllegalArgumentException("Mechanic not found"));
        mechanic.setVerificationStatus(status);
        mechanicRepository.save(mechanic);
    }
}