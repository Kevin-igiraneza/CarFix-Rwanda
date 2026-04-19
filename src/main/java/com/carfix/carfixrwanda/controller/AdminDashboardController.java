package com.carfix.carfixrwanda.controller;

import com.carfix.carfixrwanda.enums.RequestStatus;
import com.carfix.carfixrwanda.enums.VerificationStatus;
import com.carfix.carfixrwanda.model.CustomerVehicle;
import com.carfix.carfixrwanda.model.Mechanic;
import com.carfix.carfixrwanda.model.ServiceRequest;
import com.carfix.carfixrwanda.model.ServiceRequestStatusHistory;
import com.carfix.carfixrwanda.model.User;
import com.carfix.carfixrwanda.repository.UserRepository;
import com.carfix.carfixrwanda.service.CustomerVehicleService;
import com.carfix.carfixrwanda.service.MechanicService;
import com.carfix.carfixrwanda.service.ServiceRequestService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.security.core.Authentication;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Comparator;
import java.util.List;

@Controller
public class AdminDashboardController {

    private final MechanicService mechanicService;
    private final ServiceRequestService serviceRequestService;
    private final CustomerVehicleService customerVehicleService;
    private final UserRepository userRepository;

    public AdminDashboardController(MechanicService mechanicService,
                                    ServiceRequestService serviceRequestService,
                                    CustomerVehicleService customerVehicleService,
                                    UserRepository userRepository) {
        this.mechanicService = mechanicService;
        this.serviceRequestService = serviceRequestService;
        this.customerVehicleService = customerVehicleService;
        this.userRepository = userRepository;
    }

    @GetMapping("/real-admin-dashboard")
    public String realAdminDashboard(Model model, Authentication authentication) {
        User currentUser = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new IllegalArgumentException("Logged in user not found"));
        List<Mechanic> mechanics = mechanicService.getAllMechanics();
        List<ServiceRequest> requests = serviceRequestService.getAllRequests();
        List<ServiceRequestStatusHistory> recentStatusHistory = serviceRequestService.getRecentStatusHistory();
        List<CustomerVehicle> vehicles = customerVehicleService.getAllVehicles();

        List<User> recentUsers = userRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(User::getId).reversed())
                .limit(6)
                .toList();

        model.addAttribute("currentUser", currentUser);
        model.addAttribute("mechanics", mechanics);
        model.addAttribute("requests", requests);
        model.addAttribute("recentStatusHistory", recentStatusHistory);
        model.addAttribute("vehicles", vehicles);
        model.addAttribute("recentUsers", recentUsers);

        model.addAttribute("mechanicCount", mechanics.size());
        long activeRequestCount = requests.stream()
            .filter(r -> r.getStatus() != RequestStatus.COMPLETED && r.getStatus() != RequestStatus.CANCELLED)
            .count();
        model.addAttribute("requestCount", activeRequestCount);
        model.addAttribute("totalRequestCount", requests.size());
        model.addAttribute("vehicleCount", vehicles.size());
        model.addAttribute("requestStatuses", RequestStatus.values());
        model.addAttribute("approvedMechanics", mechanicService.getVerifiedMechanics());

        return "admin-dashboard";
    }

    @PostMapping("/update-mechanic-verification")
    public String updateMechanicVerification(@RequestParam("mechanicId") Long mechanicId,
                                             @RequestParam("status") VerificationStatus status) {
        mechanicService.updateVerificationStatus(mechanicId, status);
        return "redirect:/real-admin-dashboard";
    }

    @PostMapping("/admin/update-request-status")
    public String adminUpdateRequestStatus(@RequestParam("requestId") Long requestId,
                                           @RequestParam("status") RequestStatus status,
                                           Authentication authentication,
                                           RedirectAttributes redirectAttributes) {
        try {
            serviceRequestService.updateRequestStatusAsAdmin(requestId, status, "ADMIN:" + authentication.getName());
            redirectAttributes.addFlashAttribute("adminFlashMessage", "Request status updated.");
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("adminFlashError", ex.getMessage());
        }
        return "redirect:/real-admin-dashboard#requests";
    }

    @PostMapping("/admin/assign-mechanic")
    public String adminAssignMechanic(@RequestParam("requestId") Long requestId,
                                      @RequestParam("mechanicId") Long mechanicId,
                                      Authentication authentication,
                                      RedirectAttributes redirectAttributes) {
        try {
            serviceRequestService.assignMechanicAsAdmin(requestId, mechanicId, "ADMIN:" + authentication.getName());
            redirectAttributes.addFlashAttribute("adminFlashMessage", "Mechanic assigned to the request.");
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("adminFlashError", ex.getMessage());
        }
        return "redirect:/real-admin-dashboard#requests";
    }

    @PostMapping("/admin/clear-mechanic-assignment")
    public String adminClearMechanic(@RequestParam("requestId") Long requestId,
                                     Authentication authentication,
                                     RedirectAttributes redirectAttributes) {
        try {
            serviceRequestService.clearMechanicAssignmentAsAdmin(requestId, "ADMIN:" + authentication.getName());
            redirectAttributes.addFlashAttribute("adminFlashMessage", "Mechanic unassigned; request set back to pending where applicable.");
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("adminFlashError", ex.getMessage());
        }
        return "redirect:/real-admin-dashboard#requests";
    }

    @PostMapping("/admin/delete-request")
    public String adminDeleteRequest(@RequestParam("requestId") Long requestId,
                                     Authentication authentication,
                                     RedirectAttributes redirectAttributes) {
        try {
            serviceRequestService.deleteRequestAsAdmin(requestId, "ADMIN:" + authentication.getName());
            redirectAttributes.addFlashAttribute("adminFlashMessage", "Request deleted.");
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("adminFlashError", ex.getMessage());
        }
        return "redirect:/real-admin-dashboard#requests";
    }
}