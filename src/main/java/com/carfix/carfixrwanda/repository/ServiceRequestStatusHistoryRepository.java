package com.carfix.carfixrwanda.repository;

import com.carfix.carfixrwanda.model.ServiceRequestStatusHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ServiceRequestStatusHistoryRepository extends JpaRepository<ServiceRequestStatusHistory, Long> {

    List<ServiceRequestStatusHistory> findTop20ByOrderByChangedAtDesc();

    void deleteByServiceRequestId(Long serviceRequestId);
}
