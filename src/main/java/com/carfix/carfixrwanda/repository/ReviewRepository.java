package com.carfix.carfixrwanda.repository;

import com.carfix.carfixrwanda.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByMechanicId(Long mechanicId);
    Optional<Review> findByServiceRequestId(Long serviceRequestId);
}
