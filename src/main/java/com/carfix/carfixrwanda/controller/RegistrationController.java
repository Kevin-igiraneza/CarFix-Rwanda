package com.carfix.carfixrwanda.controller;

import com.carfix.carfixrwanda.enums.Role;
import com.carfix.carfixrwanda.service.AccountRegistrationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RegistrationController {

    private final AccountRegistrationService accountRegistrationService;

    public RegistrationController(AccountRegistrationService accountRegistrationService) {
        this.accountRegistrationService = accountRegistrationService;
    }

    @GetMapping("/register")
    public String showRegisterPage(@RequestParam(value = "as", required = false) String as, Model model) {
        if ("mechanic".equalsIgnoreCase(as)) {
            model.addAttribute("defaultAccountType", "MECHANIC");
        } else {
            model.addAttribute("defaultAccountType", "CUSTOMER");
        }
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@RequestParam("fullName") String fullName,
                               @RequestParam("email") String email,
                               @RequestParam("password") String password,
                               @RequestParam("phone") String phone,
                               @RequestParam(value = "registrationRole", required = false) String registrationRole,
                               @RequestParam(value = "specialization", required = false) String specialization,
                               @RequestParam(value = "supportedVehicleModel", required = false) String supportedVehicleModel,
                               @RequestParam(value = "garageLocation", required = false) String garageLocation,
                               Model model) {

        Role role;
        try {
            if (registrationRole == null || registrationRole.isBlank()) {
                throw new IllegalArgumentException("Select whether you are registering as a customer or a mechanic.");
            }
            role = Role.valueOf(registrationRole.trim().toUpperCase());
        } catch (IllegalArgumentException ex) {
            model.addAttribute("errorMessage", "Select a valid account type: customer or mechanic.");
            model.addAttribute("defaultAccountType", "CUSTOMER");
            return "register";
        }

        try {
            accountRegistrationService.registerSafe(
                    fullName, email, password, phone, role,
                    specialization, supportedVehicleModel, garageLocation
            );
        } catch (IllegalStateException | IllegalArgumentException ex) {
            model.addAttribute("errorMessage", ex.getMessage());
            model.addAttribute("defaultAccountType", role.name());
            return "register";
        }

        return "redirect:/login?registered";
    }
}
