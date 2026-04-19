package com.carfix.carfixrwanda.controller;

import com.carfix.carfixrwanda.enums.Role;
import com.carfix.carfixrwanda.model.Mechanic;
import com.carfix.carfixrwanda.model.User;
import com.carfix.carfixrwanda.repository.UserRepository;
import com.carfix.carfixrwanda.service.MechanicService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Controller
public class MechanicController {

    private final MechanicService mechanicService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public MechanicController(MechanicService mechanicService, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.mechanicService = mechanicService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/mechanics")
    public String showMechanics(
            @RequestParam(value = "specialization", required = false) String specialization,
            @RequestParam(value = "vehicleModel", required = false) String vehicleModel,
            @RequestParam(value = "q", required = false) String keyword,
            Model model) {
        List<Mechanic> mechanics = mechanicService.searchVerifiedMechanics(specialization, vehicleModel, keyword);
        model.addAttribute("mechanics", mechanics);
        model.addAttribute("filterSpecialization", specialization != null ? specialization : "");
        model.addAttribute("filterVehicleModel", vehicleModel != null ? vehicleModel : "");
        model.addAttribute("filterKeyword", keyword != null ? keyword : "");
        return "mechanics";
    }

    @GetMapping("/add-mechanic")
    public String showAddMechanicForm() {
        return "redirect:/register?as=mechanic";
    }

    @PostMapping("/save-mechanic")
    public String saveMechanic(
            @RequestParam("fullName") String fullName,
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            @RequestParam("phoneNumber") String phoneNumber,
            @RequestParam("specialization") String specialization,
            @RequestParam("supportedVehicleModel") String supportedVehicleModel,
            @RequestParam("garageLocation") String garageLocation,
            Model model) {

        try {
            User user = new User();
            user.setFullName(fullName);
            user.setEmail(email);
            user.setPassword(passwordEncoder.encode(password));
            user.setPhone(phoneNumber);
            user.setRole(Role.MECHANIC);

            User savedUser = userRepository.save(user);

            Mechanic mechanic = new Mechanic();
            mechanic.setUser(savedUser);
            mechanic.setSpecialization(specialization);
            mechanic.setSupportedVehicleModel(supportedVehicleModel);
            mechanic.setGarageLocation(garageLocation);

            mechanicService.saveMechanic(mechanic);

            return "redirect:/mechanics";
        } catch (DataIntegrityViolationException ex) {
            model.addAttribute("errorMessage", "Phone number or Email already exists. Please use different ones.");
            return "add-mechanic";
        }
    }
}
