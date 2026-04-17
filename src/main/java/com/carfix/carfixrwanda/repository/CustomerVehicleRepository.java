package com.carfix.carfixrwanda.repository;

import com.carfix.carfixrwanda.model.CustomerVehicle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerVehicleRepository extends JpaRepository<CustomerVehicle, Long> {
    List<CustomerVehicle> findByUserId(Long userId);
}