package com.carfix.carfixrwanda.repository;

import com.carfix.carfixrwanda.model.ServiceRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ServiceRequestRepository extends JpaRepository<ServiceRequest, Long> {
    List<ServiceRequest> findByCustomerVehicleId(Long vehicleId);

    List<ServiceRequest> findByCustomerVehicleUserId(Long userId);

    List<ServiceRequest> findByPreferredMechanicId(Long mechanicId);
}