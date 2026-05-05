package com.carfix.carfixrwanda.repository;

import com.carfix.carfixrwanda.model.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.List;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    Optional<Invoice> findByServiceRequestId(Long serviceRequestId);
    List<Invoice> findByServiceRequest_CustomerVehicle_UserId(Long userId);
    List<Invoice> findByServiceRequest_PreferredMechanic_Id(Long mechanicId);
}
