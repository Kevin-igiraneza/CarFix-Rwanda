package com.carfix.carfixrwanda.repository;

import com.carfix.carfixrwanda.enums.VerificationStatus;
import com.carfix.carfixrwanda.model.Mechanic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MechanicRepository extends JpaRepository<Mechanic, Long> {

    Optional<Mechanic> findByUserId(Long userId);

    List<Mechanic> findByVerificationStatus(VerificationStatus verificationStatus);
}