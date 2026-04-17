package com.carfix.carfixrwanda.service;

import com.carfix.carfixrwanda.enums.RequestStatus;
import com.carfix.carfixrwanda.model.ServiceRequest;
import com.carfix.carfixrwanda.repository.ServiceRequestRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceRequestService {

    private final ServiceRequestRepository serviceRequestRepository;

    public ServiceRequestService(ServiceRequestRepository serviceRequestRepository) {
        this.serviceRequestRepository = serviceRequestRepository;
    }

    public ServiceRequest saveRequest(ServiceRequest serviceRequest) {
        return serviceRequestRepository.save(serviceRequest);
    }

    public List<ServiceRequest> getAllRequests() {
        return serviceRequestRepository.findAll();
    }

    public List<ServiceRequest> getRequestsByVehicleId(Long vehicleId) {
        return serviceRequestRepository.findByCustomerVehicleId(vehicleId);
    }

    public List<ServiceRequest> getRequestsByUserId(Long userId) {
        return serviceRequestRepository.findByCustomerVehicleUserId(userId);
    }

    public void updateRequestStatus(Long requestId, RequestStatus status) {
        ServiceRequest request = serviceRequestRepository.findById(requestId)
                .orElseThrow(() -> new IllegalArgumentException("Request not found"));
        request.setStatus(status);
        serviceRequestRepository.save(request);
    }
}