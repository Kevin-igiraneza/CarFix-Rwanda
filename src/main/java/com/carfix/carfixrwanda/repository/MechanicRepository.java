package com.carfix.carfixrwanda.repository;

import com.carfix.carfixrwanda.model.Mechanic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MechanicRepository extends JpaRepository<Mechanic, Long> {
}