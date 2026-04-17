package com.carfix.carfixrwanda.controller;

import com.carfix.carfixrwanda.enums.Role;
import com.carfix.carfixrwanda.model.CustomerVehicle;
import com.carfix.carfixrwanda.model.User;
import com.carfix.carfixrwanda.repository.UserRepository;
import com.carfix.carfixrwanda.service.CustomerVehicleService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CustomerVehicleController {

    private final CustomerVehicleService customerVehicleService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public CustomerVehicleController(CustomerVehicleService customerVehicleService,
                                     UserRepository userRepository,
                                     PasswordEncoder passwordEncoder) {
        this.customerVehicleService = customerVehicleService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/vehicle-registration")
    public String showVehicleForm() {
        return "vehicle-registration";
    }

    @PostMapping("/save-vehicle")
    public String saveVehicle(
            @RequestParam("vehicleName") String vehicleName,
            @RequestParam("brand") String brand,
            @RequestParam("model") String model,
            @RequestParam("year") Integer year,
            @RequestParam("plateNumber") String plateNumber,
            @RequestParam("fuelType") String fuelType,
            @RequestParam("transmission") String transmission,
            @RequestParam(value = "notes", required = false) String notes
    ) {

        User user = userRepository.findByEmail("customer@carfix.com").orElseGet(() -> {
            User newUser = new User();
            newUser.setFullName("Default Customer");
            newUser.setEmail("customer@carfix.com");
            newUser.setPassword(passwordEncoder.encode("1234"));
            newUser.setRole(Role.CUSTOMER);
            return userRepository.save(newUser);
        });

        CustomerVehicle vehicle = new CustomerVehicle();
        vehicle.setVehicleName(vehicleName);
        vehicle.setBrand(brand);
        vehicle.setModel(model);
        vehicle.setYear(year);
        vehicle.setPlateNumber(plateNumber);
        vehicle.setFuelType(fuelType);
        vehicle.setTransmission(transmission);
        vehicle.setNotes(notes);
        vehicle.setUser(user);

        customerVehicleService.saveVehicle(vehicle);

        return "redirect:/customer-dashboard";
    }
}