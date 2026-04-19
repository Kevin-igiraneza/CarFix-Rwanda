package com.carfix.carfixrwanda.repository;

import com.carfix.carfixrwanda.model.CustomerVehicle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CustomerVehicleRepository extends JpaRepository<CustomerVehicle, Long> {
    List<CustomerVehicle> findByUserId(Long userId);

    Optional<CustomerVehicle> findByIdAndUserId(Long id, Long userId);
}