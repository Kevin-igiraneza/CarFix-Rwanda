package com.carfix.carfixrwanda.controller;

import com.carfix.carfixrwanda.enums.RequestStatus;
import com.carfix.carfixrwanda.model.Mechanic;
import com.carfix.carfixrwanda.model.ServiceRequest;
import com.carfix.carfixrwanda.model.User;
import com.carfix.carfixrwanda.repository.UserRepository;
import com.carfix.carfixrwanda.service.MechanicService;
import com.carfix.carfixrwanda.service.ServiceRequestService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class MechanicDashboardController {

    private final ServiceRequestService serviceRequestService;
    private final UserRepository userRepository;
    private final MechanicService mechanicService;

    public MechanicDashboardController(ServiceRequestService serviceRequestService,
                                       UserRepository userRepository,
                                       MechanicService mechanicService) {
        this.serviceRequestService = serviceRequestService;
        this.userRepository = userRepository;
        this.mechanicService = mechanicService;
    }

    @GetMapping("/real-mechanic-dashboard")
    public String realMechanicDashboard(Model model, Authentication authentication) {
        User currentUser = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new IllegalArgumentException("Logged in user not found"));

        Mechanic mechanic = mechanicService.findByUserId(currentUser.getId())
                .orElseThrow(() -> new IllegalStateException("No mechanic profile is linked to this account"));

        List<ServiceRequest> assignedRequests = serviceRequestService.getRequestsForMechanic(mechanic);

        long inProgressCount = assignedRequests.stream()
                .filter(r -> r.getStatus() == RequestStatus.IN_PROGRESS)
                .count();
        long openCount = assignedRequests.stream()
                .filter(r -> r.getStatus() != RequestStatus.COMPLETED
                        && r.getStatus() != RequestStatus.CANCELLED)
                .count();
        long completedCount = assignedRequests.stream()
                .filter(r -> r.getStatus() == RequestStatus.COMPLETED)
                .count();

        model.addAttribute("currentUser", currentUser);
        model.addAttribute("mechanicProfile", mechanic);
        model.addAttribute("assignedRequests", assignedRequests);
        model.addAttribute("requestCount", assignedRequests.size());
        model.addAttribute("inProgressCount", inProgressCount);
        model.addAttribute("openWorkCount", openCount);
        model.addAttribute("completedCount", completedCount);
        model.addAttribute("requestStatuses", RequestStatus.values());

        return "mechanic-dashboard";
    }

    @PostMapping("/update-request-status")
    public String updateRequestStatus(
            @RequestParam("requestId") Long requestId,
            @RequestParam("status") RequestStatus status,
                        Authentication authentication,
                        RedirectAttributes redirectAttributes
    ) {
        User currentUser = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new IllegalArgumentException("Logged in user not found"));
        Mechanic mechanic = mechanicService.findByUserId(currentUser.getId())
                .orElseThrow(() -> new IllegalStateException("No mechanic profile is linked to this account"));

                try {
                        serviceRequestService.updateRequestStatusAsMechanic(
                                        requestId,
                                        status,
                                        mechanic,
                                        "MECHANIC:" + currentUser.getEmail()
                        );
                        redirectAttributes.addFlashAttribute("mechanicFlashMessage", "Request status updated.");
                } catch (Exception ex) {
                        redirectAttributes.addFlashAttribute("mechanicFlashError", ex.getMessage());
                }
        return "redirect:/real-mechanic-dashboard";
    }

    @PostMapping("/mechanic/review-cancellation-request")
    public String reviewCancellationRequest(@RequestParam("requestId") Long requestId,
                                            @RequestParam("decision") String decision,
                                            @RequestParam("responseMessage") String responseMessage,
                                            Authentication authentication,
                                            RedirectAttributes redirectAttributes) {
        User currentUser = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new IllegalArgumentException("Logged in user not found"));
        Mechanic mechanic = mechanicService.findByUserId(currentUser.getId())
                .orElseThrow(() -> new IllegalStateException("No mechanic profile is linked to this account"));

        try {
            boolean approve = "APPROVE".equalsIgnoreCase(decision);
            serviceRequestService.reviewCancellationRequestAsMechanic(
                    requestId,
                    approve,
                    responseMessage,
                    mechanic,
                    "MECHANIC:" + currentUser.getEmail()
            );
            redirectAttributes.addFlashAttribute(
                    "mechanicFlashMessage",
                    approve ? "Cancellation request approved." : "Cancellation request reply sent."
            );
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("mechanicFlashError", ex.getMessage());
        }
        return "redirect:/real-mechanic-dashboard";
    }

    @PostMapping("/mechanic/delete-service-request")
    public String deleteServiceRequest(@RequestParam("requestId") Long requestId,
                                       Authentication authentication,
                                       RedirectAttributes redirectAttributes) {
        User currentUser = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new IllegalArgumentException("Logged in user not found"));
        Mechanic mechanic = mechanicService.findByUserId(currentUser.getId())
                .orElseThrow(() -> new IllegalStateException("No mechanic profile is linked to this account"));

        try {
            serviceRequestService.deleteCancelledRequestAsMechanic(requestId, mechanic.getId());
            redirectAttributes.addFlashAttribute("mechanicFlashMessage", "Cancelled request removed.");
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("mechanicFlashError", ex.getMessage());
        }
        return "redirect:/real-mechanic-dashboard";
    }
}
