package com.carfix.carfixrwanda.controller;

import com.carfix.carfixrwanda.enums.Role;
import com.carfix.carfixrwanda.model.Mechanic;
import com.carfix.carfixrwanda.model.User;
import com.carfix.carfixrwanda.repository.UserRepository;
import com.carfix.carfixrwanda.service.MechanicService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MechanicController {

    private final MechanicService mechanicService;
    private final UserRepository userRepository;

    public MechanicController(MechanicService mechanicService, UserRepository userRepository) {
        this.mechanicService = mechanicService;
        this.userRepository = userRepository;
    }

    @GetMapping("/mechanics")
    public String showMechanics(Model model) {
        model.addAttribute("mechanics", mechanicService.getAllMechanics());
        return "mechanics";
    }

    @GetMapping("/add-mechanic")
    public String showAddMechanicForm() {
        return "add-mechanic";
    }

    @PostMapping("/save-mechanic")
    public String saveMechanic(
            @RequestParam("fullName") String fullName,
            @RequestParam("email") String email,
            @RequestParam("phoneNumber") String phoneNumber,
            @RequestParam("specialization") String specialization,
            @RequestParam("supportedVehicleModel") String supportedVehicleModel,
            @RequestParam("garageLocation") String garageLocation) {

        User user = new User();
        user.setFullName(fullName);
        user.setEmail(email);
        user.setPassword("1234");
        user.setRole(Role.MECHANIC);

        User savedUser = userRepository.save(user);

        Mechanic mechanic = new Mechanic();
        mechanic.setUser(savedUser);
        mechanic.setSpecialization(specialization);
        mechanic.setSupportedVehicleModel(supportedVehicleModel);
        mechanic.setGarageLocation(garageLocation);

        mechanicService.saveMechanic(mechanic);

        return "redirect:/mechanics";
    }
}