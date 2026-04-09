package com.carfix.carfixrwanda.controller;

import com.carfix.carfixrwanda.model.Mechanic;
import com.carfix.carfixrwanda.service.MechanicService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MechanicController {

    private final MechanicService mechanicService;

    public MechanicController(MechanicService mechanicService) {
        this.mechanicService = mechanicService;
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
            @RequestParam("specialization") String specialization,
            @RequestParam("supportedVehicleModel") String supportedVehicleModel,
            @RequestParam("phoneNumber") String phoneNumber,
            @RequestParam("garageLocation") String garageLocation) {

        Mechanic mechanic = new Mechanic();
        mechanic.setFullName(fullName);
        mechanic.setSpecialization(specialization);
        mechanic.setSupportedVehicleModel(supportedVehicleModel);
        mechanic.setPhoneNumber(phoneNumber);
        mechanic.setGarageLocation(garageLocation);

        mechanicService.saveMechanic(mechanic);

        return "redirect:/mechanics";
    }
}