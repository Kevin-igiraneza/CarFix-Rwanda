package com.carfix.carfixrwanda.repository;

import com.carfix.carfixrwanda.enums.VerificationStatus;
import com.carfix.carfixrwanda.model.Mechanic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MechanicRepository extends JpaRepository<Mechanic, Long> {

    @Query("""
            select m
            from Mechanic m
            join fetch m.user u
            where u.id = :userId
            """)
    Optional<Mechanic> findByUserIdWithExistingUser(@Param("userId") Long userId);

    @Query("""
            select m
            from Mechanic m
            join fetch m.user u
            where m.verificationStatus = :verificationStatus
            order by m.id desc
            """)
    List<Mechanic> findByVerificationStatusWithExistingUser(@Param("verificationStatus") VerificationStatus verificationStatus);

    @Query("""
            select m
            from Mechanic m
            join fetch m.user u
            where m.id = :id
            """)
    Optional<Mechanic> findByIdWithExistingUser(@Param("id") Long id);

    @Query("""
            select m
            from Mechanic m
            join fetch m.user u
            order by m.id desc
            """)
    List<Mechanic> findAllWithExistingUser();
}
