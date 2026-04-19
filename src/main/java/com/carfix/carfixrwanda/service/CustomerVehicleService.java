package com.carfix.carfixrwanda.service;

import com.carfix.carfixrwanda.model.CustomerVehicle;
import com.carfix.carfixrwanda.repository.CustomerVehicleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerVehicleService {

    private final CustomerVehicleRepository customerVehicleRepository;

    public CustomerVehicleService(CustomerVehicleRepository customerVehicleRepository) {
        this.customerVehicleRepository = customerVehicleRepository;
    }

    public CustomerVehicle saveVehicle(CustomerVehicle vehicle) {
        return customerVehicleRepository.save(vehicle);
    }

    public List<CustomerVehicle> getAllVehicles() {
        return customerVehicleRepository.findAll();
    }

    public List<CustomerVehicle> getVehiclesByUserId(Long userId) {
        return customerVehicleRepository.findByUserId(userId);
    }

    public Optional<CustomerVehicle> findVehicleOwnedByUser(Long vehicleId, Long userId) {
        return customerVehicleRepository.findByIdAndUserId(vehicleId, userId);
    }
}