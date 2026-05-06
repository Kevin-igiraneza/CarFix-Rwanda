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
    private final com.carfix.carfixrwanda.service.NotificationService notificationService;

    public AdminDashboardController(MechanicService mechanicService,
                                    ServiceRequestService serviceRequestService,
                                    CustomerVehicleService customerVehicleService,
                                    UserRepository userRepository,
                                    com.carfix.carfixrwanda.service.NotificationService notificationService) {
        this.mechanicService = mechanicService;
        this.serviceRequestService = serviceRequestService;
        this.customerVehicleService = customerVehicleService;
        this.userRepository = userRepository;
        this.notificationService = notificationService;
    }

    @GetMapping("/real-admin-dashboard")
    public String realAdminDashboard(Model model, Authentication authentication) {
        User currentUser = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new IllegalArgumentException("Logged in user not found"));
        List<Mechanic> mechanics = mechanicService.getAllMechanics();
        List<ServiceRequest> requests = serviceRequestService.getAllRequests();
        List<CustomerVehicle> vehicles = customerVehicleService.getAllVehicles();

        List<User> recentUsers = userRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(User::getId).reversed())
                .limit(6)
                .toList();
        
        List<ServiceRequestStatusHistory> recentStatusHistory = serviceRequestService.getRecentStatusHistory();

        model.addAttribute("currentUser", com.carfix.carfixrwanda.dto.DtoMapper.toUserDto(currentUser));
        model.addAttribute("recentUsers", com.carfix.carfixrwanda.dto.DtoMapper.toUserDtoList(recentUsers));
        model.addAttribute("recentStatusHistory", recentStatusHistory);

        model.addAttribute("mechanicCount", mechanics.size());
        long activeRequestCount = requests.stream()
            .filter(r -> r.getStatus() != RequestStatus.COMPLETED && r.getStatus() != RequestStatus.CANCELLED)
            .count();
        model.addAttribute("requestCount", activeRequestCount);
        model.addAttribute("vehicleCount", vehicles.size());

        return "admin-dashboard";
    }

    @GetMapping("/admin/service-requests")
    public String serviceRequests(Model model, Authentication authentication) {
        User currentUser = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new IllegalArgumentException("Logged in user not found"));
        List<ServiceRequest> requests = serviceRequestService.getAllRequests();
        
        model.addAttribute("currentUser", com.carfix.carfixrwanda.dto.DtoMapper.toUserDto(currentUser));
        model.addAttribute("requests", com.carfix.carfixrwanda.dto.DtoMapper.toServiceRequestDtoList(requests));
        model.addAttribute("approvedMechanics", mechanicService.getVerifiedMechanics());
        model.addAttribute("requestStatuses", RequestStatus.values());
        
        return "admin-service-requests";
    }

    @GetMapping("/admin/mechanic-verification")
    public String mechanicVerification(Model model, Authentication authentication) {
        User currentUser = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new IllegalArgumentException("Logged in user not found"));
        List<Mechanic> mechanics = mechanicService.getAllMechanics().stream()
                .filter(m -> m.getVerificationStatus() == VerificationStatus.PENDING)
                .toList();
        
        model.addAttribute("currentUser", com.carfix.carfixrwanda.dto.DtoMapper.toUserDto(currentUser));
        model.addAttribute("mechanics", com.carfix.carfixrwanda.dto.DtoMapper.toMechanicDtoList(mechanics));
        
        return "admin-mechanic-verification";
    }

    @GetMapping("/admin/recent-status-activity")
    public String recentStatusActivity(Model model, Authentication authentication) {
        User currentUser = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new IllegalArgumentException("Logged in user not found"));
        List<ServiceRequestStatusHistory> recentStatusHistory = serviceRequestService.getRecentStatusHistory();
        
        model.addAttribute("currentUser", com.carfix.carfixrwanda.dto.DtoMapper.toUserDto(currentUser));
        model.addAttribute("recentStatusHistory", recentStatusHistory);
        
        return "admin-recent-status-activity";
    }

    @GetMapping("/admin/notifications")
    public String showNotifications(Model model, Authentication authentication) {
        User currentUser = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new IllegalArgumentException("Logged in user not found"));
        
        model.addAttribute("currentUser", com.carfix.carfixrwanda.dto.DtoMapper.toUserDto(currentUser));
        model.addAttribute("notifications", notificationService.getNotificationsForUser(currentUser.getId()));
        return "admin/notifications";
    }

    @PostMapping("/admin/notifications/mark-all-read")
    public String markNotificationsRead(Authentication authentication) {
        User currentUser = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new IllegalArgumentException("Logged in user not found"));
        
        notificationService.markAllRead(currentUser.getId());
        return "redirect:/admin/notifications";
    }

    @PostMapping("/update-mechanic-verification")
    public String updateMechanicVerification(@RequestParam("mechanicId") Long mechanicId,
                                             @RequestParam("status") VerificationStatus status) {
        mechanicService.updateVerificationStatus(mechanicId, status);
        return "redirect:/admin/mechanic-verification";
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
        return "redirect:/admin/service-requests";
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
        return "redirect:/admin/service-requests";
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
        return "redirect:/admin/service-requests";
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
        return "redirect:/admin/service-requests";
    }
}