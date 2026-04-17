package com.carfix.carfixrwanda.controller;

import com.carfix.carfixrwanda.model.CustomerVehicle;
import com.carfix.carfixrwanda.service.CustomerVehicleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class CustomerDashboardController {

    private final CustomerVehicleService customerVehicleService;

    public CustomerDashboardController(CustomerVehicleService customerVehicleService) {
        this.customerVehicleService = customerVehicleService;
    }

    @GetMapping("/customer-dashboard")
    public String customerDashboard(Model model) {
        List<CustomerVehicle> vehicles = customerVehicleService.getVehiclesByUserId(3L);
        model.addAttribute("vehicles", vehicles);
        model.addAttribute("vehicleCount", vehicles.size());
        return "customer-dashboard";
    }
}