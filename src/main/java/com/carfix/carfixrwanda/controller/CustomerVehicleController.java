package com.carfix.carfixrwanda.controller;

import com.carfix.carfixrwanda.model.CustomerVehicle;
import com.carfix.carfixrwanda.model.User;
import com.carfix.carfixrwanda.repository.UserRepository;
import com.carfix.carfixrwanda.service.CustomerVehicleService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.security.core.Authentication;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.Year;

@Controller
public class CustomerVehicleController {

    private final CustomerVehicleService customerVehicleService;
    private final UserRepository userRepository;

    public CustomerVehicleController(CustomerVehicleService customerVehicleService,
                                     UserRepository userRepository) {
        this.customerVehicleService = customerVehicleService;
        this.userRepository = userRepository;
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
            @RequestParam(value = "notes", required = false) String notes,
            Authentication authentication,
            RedirectAttributes redirectAttributes
    ) {
        try {
            User user = userRepository.findByEmail(authentication.getName())
                    .orElseThrow(() -> new IllegalArgumentException("Logged in user not found"));

            int currentYear = Year.now().getValue();
            if (year == null || year < 1950 || year > currentYear + 1) {
                throw new IllegalArgumentException("Enter a valid vehicle year.");
            }

            CustomerVehicle vehicle = new CustomerVehicle();
            vehicle.setVehicleName(requireText(vehicleName, "Vehicle name is required."));
            vehicle.setBrand(requireText(brand, "Vehicle brand is required."));
            vehicle.setModel(requireText(model, "Vehicle model is required."));
            vehicle.setYear(year);
            vehicle.setPlateNumber(requireText(plateNumber, "Plate number is required.").toUpperCase());
            vehicle.setFuelType(requireText(fuelType, "Fuel type is required."));
            vehicle.setTransmission(requireText(transmission, "Transmission is required."));
            vehicle.setNotes(notes == null || notes.trim().isEmpty() ? null : notes.trim());
            vehicle.setUser(user);

            customerVehicleService.saveVehicle(vehicle);
            redirectAttributes.addFlashAttribute("flashMessage", "Vehicle saved successfully.");
            return "redirect:/customer-dashboard";
        } catch (DataIntegrityViolationException ex) {
            redirectAttributes.addFlashAttribute("flashError", "Plate number already exists. Use a unique plate number.");
            return "redirect:/vehicle-registration";
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("flashError", ex.getMessage());
            return "redirect:/vehicle-registration";
        }
    }

    private static String requireText(String value, String message) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(message);
        }
        return value.trim();
    }
}