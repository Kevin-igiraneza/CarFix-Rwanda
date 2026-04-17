package com.carfix.carfixrwanda.controller;

import com.carfix.carfixrwanda.enums.VerificationStatus;
import com.carfix.carfixrwanda.model.CustomerVehicle;
import com.carfix.carfixrwanda.model.Mechanic;
import com.carfix.carfixrwanda.model.ServiceRequest;
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
    public String realAdminDashboard(Model model) {
        List<Mechanic> mechanics = mechanicService.getAllMechanics();
        List<ServiceRequest> requests = serviceRequestService.getAllRequests();
        List<CustomerVehicle> vehicles = customerVehicleService.getAllVehicles();

        List<User> recentUsers = userRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(User::getId).reversed())
                .limit(6)
                .toList();

        model.addAttribute("mechanics", mechanics);
        model.addAttribute("requests", requests);
        model.addAttribute("vehicles", vehicles);
        model.addAttribute("recentUsers", recentUsers);

        model.addAttribute("mechanicCount", mechanics.size());
        model.addAttribute("requestCount", requests.size());
        model.addAttribute("vehicleCount", vehicles.size());

        return "admin-dashboard";
    }

    @PostMapping("/update-mechanic-verification")
    public String updateMechanicVerification(@RequestParam("mechanicId") Long mechanicId,
                                             @RequestParam("status") VerificationStatus status) {
        mechanicService.updateVerificationStatus(mechanicId, status);
        return "redirect:/real-admin-dashboard";
    }
}