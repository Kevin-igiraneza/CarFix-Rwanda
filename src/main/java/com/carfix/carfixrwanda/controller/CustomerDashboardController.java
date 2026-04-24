package com.carfix.carfixrwanda.controller;

import com.carfix.carfixrwanda.enums.RequestStatus;
import com.carfix.carfixrwanda.model.CustomerVehicle;
import com.carfix.carfixrwanda.model.Mechanic;
import com.carfix.carfixrwanda.model.ServiceRequest;
import com.carfix.carfixrwanda.model.User;
import com.carfix.carfixrwanda.repository.UserRepository;
import com.carfix.carfixrwanda.service.CustomerVehicleService;
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
import java.util.stream.Collectors;

@Controller
public class CustomerDashboardController {

    private final CustomerVehicleService customerVehicleService;
    private final ServiceRequestService serviceRequestService;
    private final MechanicService mechanicService;
    private final UserRepository userRepository;

    public CustomerDashboardController(CustomerVehicleService customerVehicleService,
                                       ServiceRequestService serviceRequestService,
                                       MechanicService mechanicService,
                                       UserRepository userRepository) {
        this.customerVehicleService = customerVehicleService;
        this.serviceRequestService = serviceRequestService;
        this.mechanicService = mechanicService;
        this.userRepository = userRepository;
    }

    @GetMapping("/customer-dashboard")
    public String customerDashboard(Model model, Authentication authentication) {
        User currentUser = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new IllegalArgumentException("Logged in user not found"));

        List<CustomerVehicle> vehicles = customerVehicleService.getVehiclesByUserId(currentUser.getId());
        List<ServiceRequest> requests = serviceRequestService.getRequestsByUserId(currentUser.getId());
        List<Mechanic> mechanics = mechanicService.getVerifiedMechanics();

        long activeRequestCount = requests.stream()
                .filter(r -> r.getStatus() != RequestStatus.COMPLETED && r.getStatus() != RequestStatus.CANCELLED)
                .count();
        List<ServiceRequest> progressRequests = requests.stream()
                .filter(r -> r.getStatus() != RequestStatus.COMPLETED && r.getStatus() != RequestStatus.CANCELLED)
                .collect(Collectors.toList());

        model.addAttribute("currentUser", currentUser);
        model.addAttribute("vehicles", vehicles);
        model.addAttribute("vehicleCount", vehicles.size());
        model.addAttribute("requests", requests);
        model.addAttribute("requestCount", activeRequestCount);
        model.addAttribute("progressRequests", progressRequests);
        model.addAttribute("mechanics", mechanics);

        return "customer-dashboard";
    }

    @PostMapping("/customer/cancel-service-request")
    public String cancelServiceRequest(@RequestParam("requestId") Long requestId,
                                       @RequestParam("cancellationReason") String cancellationReason,
                                       Authentication authentication,
                                       RedirectAttributes redirectAttributes) {
        User user = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new IllegalArgumentException("Logged in user not found"));
        try {
            serviceRequestService.requestCancellationAsCustomer(
                    requestId,
                    user.getId(),
                    cancellationReason,
                    "CUSTOMER:" + user.getEmail()
            );
            redirectAttributes.addFlashAttribute("flashMessage", "Your cancellation request was sent to the mechanic.");
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("flashError", ex.getMessage());
        }
        return "redirect:/customer-dashboard";
    }

    @PostMapping("/customer/delete-service-request")
    public String deleteServiceRequest(@RequestParam("requestId") Long requestId,
                                       Authentication authentication,
                                       RedirectAttributes redirectAttributes) {
        User user = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new IllegalArgumentException("Logged in user not found"));
        try {
            serviceRequestService.deleteCancelledRequestAsCustomer(requestId, user.getId());
            redirectAttributes.addFlashAttribute("flashMessage", "Cancelled request removed.");
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("flashError", ex.getMessage());
        }
        return "redirect:/customer-dashboard";
    }
}
