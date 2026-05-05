package com.carfix.carfixrwanda.controller;

import com.carfix.carfixrwanda.model.ServiceRequest;
import com.carfix.carfixrwanda.model.User;
import com.carfix.carfixrwanda.repository.UserRepository;
import com.carfix.carfixrwanda.service.ReviewService;
import com.carfix.carfixrwanda.service.ServiceRequestService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ReviewController {

    private final ReviewService reviewService;
    private final ServiceRequestService serviceRequestService;
    private final UserRepository userRepository;

    public ReviewController(ReviewService reviewService, ServiceRequestService serviceRequestService, UserRepository userRepository) {
        this.reviewService = reviewService;
        this.serviceRequestService = serviceRequestService;
        this.userRepository = userRepository;
    }

    @GetMapping("/review/new")
    public String showReviewForm(@RequestParam("requestId") Long requestId, Model model, Authentication authentication) {
        User user = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        
        ServiceRequest request = serviceRequestService.getRequestsByUserId(user.getId()).stream()
                .filter(r -> r.getId().equals(requestId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Request not found or access denied"));
        
        model.addAttribute("request", com.carfix.carfixrwanda.dto.DtoMapper.toServiceRequestDto(request));
        return "review-form";
    }

    @PostMapping("/review/submit")
    public String submitReview(@RequestParam("requestId") Long requestId,
                               @RequestParam("rating") Integer rating,
                               @RequestParam("comment") String comment,
                               RedirectAttributes redirectAttributes) {
        try {
            reviewService.addReview(requestId, rating, comment);
            redirectAttributes.addFlashAttribute("flashMessage", "Thank you! Your review has been submitted.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("flashError", e.getMessage());
        }
        return "redirect:/customer-dashboard";
    }
}
