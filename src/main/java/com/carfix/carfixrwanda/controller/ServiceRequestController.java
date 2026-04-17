package com.carfix.carfixrwanda.controller;

import com.carfix.carfixrwanda.model.CustomerVehicle;
import com.carfix.carfixrwanda.model.ServiceRequest;
import com.carfix.carfixrwanda.service.CustomerVehicleService;
import com.carfix.carfixrwanda.service.ServiceRequestService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ServiceRequestController {

    private final ServiceRequestService serviceRequestService;
    private final CustomerVehicleService customerVehicleService;

    public ServiceRequestController(ServiceRequestService serviceRequestService,
                                    CustomerVehicleService customerVehicleService) {
        this.serviceRequestService = serviceRequestService;
        this.customerVehicleService = customerVehicleService;
    }

    @GetMapping("/service-request")
    public String showServiceRequestForm(Model model) {
        List<CustomerVehicle> vehicles = customerVehicleService.getVehiclesByUserId(3L);
        model.addAttribute("vehicles", vehicles);
        return "service-request";
    }

    @PostMapping("/save-service-request")
    public String saveServiceRequest(
            @RequestParam("vehicleId") Long vehicleId,
            @RequestParam("serviceType") String serviceType,
            @RequestParam("urgency") String urgency,
            @RequestParam(value = "preferredMechanic", required = false) String preferredMechanic,
            @RequestParam("problemTitle") String problemTitle,
            @RequestParam("problemDescription") String problemDescription,
            @RequestParam(value = "preferredDate", required = false) String preferredDate,
            @RequestParam(value = "preferredTime", required = false) String preferredTime,
            @RequestParam(value = "locationNotes", required = false) String locationNotes
    ) {
        CustomerVehicle vehicle = customerVehicleService.getAllVehicles()
                .stream()
                .filter(v -> v.getId().equals(vehicleId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Vehicle not found"));

        ServiceRequest request = new ServiceRequest();
        request.setCustomerVehicle(vehicle);
        request.setServiceType(serviceType);
        request.setUrgency(urgency);
        request.setPreferredMechanic(preferredMechanic);
        request.setProblemTitle(problemTitle);
        request.setProblemDescription(problemDescription);
        request.setLocationNotes(locationNotes);

        if (preferredDate != null && !preferredDate.isBlank()) {
            request.setPreferredDate(java.time.LocalDate.parse(preferredDate));
        }

        if (preferredTime != null && !preferredTime.isBlank()) {
            request.setPreferredTime(java.time.LocalTime.parse(preferredTime));
        }

        serviceRequestService.saveRequest(request);

        return "redirect:/customer-dashboard";
    }
}