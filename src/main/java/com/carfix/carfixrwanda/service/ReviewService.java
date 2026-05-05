package com.carfix.carfixrwanda.service;

import com.carfix.carfixrwanda.model.Mechanic;
import com.carfix.carfixrwanda.model.Review;
import com.carfix.carfixrwanda.model.ServiceRequest;
import com.carfix.carfixrwanda.repository.ReviewRepository;
import com.carfix.carfixrwanda.repository.ServiceRequestRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ServiceRequestRepository serviceRequestRepository;

    public ReviewService(ReviewRepository reviewRepository, ServiceRequestRepository serviceRequestRepository) {
        this.reviewRepository = reviewRepository;
        this.serviceRequestRepository = serviceRequestRepository;
    }

    public Review addReview(Long serviceRequestId, Integer rating, String comment) {
        ServiceRequest request = serviceRequestRepository.findById(serviceRequestId)
                .orElseThrow(() -> new IllegalArgumentException("Service request not found"));

        if (reviewRepository.findByServiceRequestId(serviceRequestId).isPresent()) {
            throw new IllegalStateException("A review already exists for this service request");
        }
        
        if (request.getStatus() != com.carfix.carfixrwanda.enums.RequestStatus.COMPLETED) {
            throw new IllegalStateException("You can only review completed service requests");
        }

        Mechanic mechanic = request.getPreferredMechanic();
        if (mechanic == null) {
            throw new IllegalStateException("No mechanic was assigned to this request");
        }

        Review review = new Review();
        review.setServiceRequest(request);
        review.setMechanic(mechanic);
        review.setRating(rating);
        review.setComment(comment);
        
        return reviewRepository.save(review);
    }

    public List<Review> getMechanicReviews(Long mechanicId) {
        return reviewRepository.findByMechanicId(mechanicId);
    }
    
    public boolean hasReviewForServiceRequest(Long serviceRequestId) {
        return reviewRepository.findByServiceRequestId(serviceRequestId).isPresent();
    }
}
