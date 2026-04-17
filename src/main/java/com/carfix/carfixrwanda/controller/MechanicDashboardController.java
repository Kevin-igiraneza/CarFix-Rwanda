package com.carfix.carfixrwanda.controller;

import com.carfix.carfixrwanda.enums.RequestStatus;
import com.carfix.carfixrwanda.model.ServiceRequest;
import com.carfix.carfixrwanda.service.ServiceRequestService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class MechanicDashboardController {

    private final ServiceRequestService serviceRequestService;

    public MechanicDashboardController(ServiceRequestService serviceRequestService) {
        this.serviceRequestService = serviceRequestService;
    }

    @GetMapping("/real-mechanic-dashboard")
    public String realMechanicDashboard(Model model) {
        List<ServiceRequest> allRequests = serviceRequestService.getAllRequests();

        List<ServiceRequest> assignedRequests = allRequests.stream()
                .filter(r -> r.getPreferredMechanic() != null && !r.getPreferredMechanic().isBlank())
                .collect(Collectors.toList());

        model.addAttribute("assignedRequests", assignedRequests);
        model.addAttribute("requestCount", assignedRequests.size());

        return "mechanic-dashboard";
    }

    @PostMapping("/update-request-status")
    public String updateRequestStatus(
            @RequestParam("requestId") Long requestId,
            @RequestParam("status") RequestStatus status
    ) {
        serviceRequestService.updateRequestStatus(requestId, status);
        return "redirect:/real-mechanic-dashboard";
    }
}