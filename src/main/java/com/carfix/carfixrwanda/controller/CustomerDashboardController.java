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
    private final com.carfix.carfixrwanda.service.InvoiceService invoiceService;
    private final com.carfix.carfixrwanda.service.ReviewService reviewService;
    private final com.carfix.carfixrwanda.service.NotificationService notificationService;

    public CustomerDashboardController(CustomerVehicleService customerVehicleService,
                                       ServiceRequestService serviceRequestService,
                                       MechanicService mechanicService,
                                       UserRepository userRepository,
                                       com.carfix.carfixrwanda.service.InvoiceService invoiceService,
                                       com.carfix.carfixrwanda.service.ReviewService reviewService,
                                       com.carfix.carfixrwanda.service.NotificationService notificationService) {
        this.customerVehicleService = customerVehicleService;
        this.serviceRequestService = serviceRequestService;
        this.mechanicService = mechanicService;
        this.userRepository = userRepository;
        this.invoiceService = invoiceService;
        this.reviewService = reviewService;
        this.notificationService = notificationService;
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

        model.addAttribute("currentUser", com.carfix.carfixrwanda.dto.DtoMapper.toUserDto(currentUser));
        model.addAttribute("vehicles", com.carfix.carfixrwanda.dto.DtoMapper.toCustomerVehicleDtoList(vehicles));
        model.addAttribute("vehicleCount", vehicles.size());
        model.addAttribute("requests", com.carfix.carfixrwanda.dto.DtoMapper.toServiceRequestDtoList(requests));
        
        java.util.List<com.carfix.carfixrwanda.model.Invoice> invoices = invoiceService.getCustomerInvoices(currentUser.getId());
        model.addAttribute("invoices", invoices);
        
        // Find all completed requests by this user that have already been reviewed.
        // It's easier to just fetch all reviews from the DB for this user, but for now we can iterate over completed requests and check.
        java.util.List<Long> reviewedRequestIds = new java.util.ArrayList<>();
        for (com.carfix.carfixrwanda.model.ServiceRequest req : requests) {
            if (req.getStatus() == com.carfix.carfixrwanda.enums.RequestStatus.COMPLETED) {
                if (reviewService.hasReviewForServiceRequest(req.getId())) {
                    reviewedRequestIds.add(req.getId());
                }
            }
        }
        model.addAttribute("reviewedRequestIds", reviewedRequestIds);
        model.addAttribute("requestCount", activeRequestCount);
        model.addAttribute("progressRequests", com.carfix.carfixrwanda.dto.DtoMapper.toServiceRequestDtoList(progressRequests));
        model.addAttribute("mechanics", com.carfix.carfixrwanda.dto.DtoMapper.toMechanicDtoList(mechanics));

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

    @GetMapping("/customer/notifications")
    public String showNotifications(Model model, Authentication authentication) {
        User currentUser = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new IllegalArgumentException("Logged in user not found"));
        
        model.addAttribute("currentUser", com.carfix.carfixrwanda.dto.DtoMapper.toUserDto(currentUser));
        model.addAttribute("notifications", notificationService.getNotificationsForUser(currentUser.getId()));
        return "customer/notifications";
    }

    @PostMapping("/customer/notifications/mark-all-read")
    public String markNotificationsRead(Authentication authentication) {
        User currentUser = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new IllegalArgumentException("Logged in user not found"));
        
        notificationService.markAllRead(currentUser.getId());
        return "redirect:/customer/notifications";
    }
}
