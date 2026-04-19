package com.carfix.carfixrwanda.service;

import com.carfix.carfixrwanda.enums.Role;
import com.carfix.carfixrwanda.model.Mechanic;
import com.carfix.carfixrwanda.model.User;
import com.carfix.carfixrwanda.repository.UserRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AccountRegistrationService {

    private final UserRepository userRepository;
    private final MechanicService mechanicService;
    private final PasswordEncoder passwordEncoder;

    public AccountRegistrationService(UserRepository userRepository,
                                      MechanicService mechanicService,
                                      PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.mechanicService = mechanicService;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Register as customer or mechanic. Mechanics require garage profile fields; verification stays pending until an admin approves.
     */
    @Transactional
    public void registerSafe(String fullName,
                             String email,
                             String password,
                             String phone,
                             Role role,
                             String specialization,
                             String supportedVehicleModel,
                             String garageLocation) {
        if (role != Role.CUSTOMER && role != Role.MECHANIC) {
            throw new IllegalArgumentException("You can only register as a customer or a mechanic.");
        }
        if (userRepository.findByEmail(email.trim()).isPresent()) {
            throw new IllegalStateException("Email already exists. Use another one.");
        }
        if (role == Role.MECHANIC) {
            if (specialization == null || specialization.isBlank()
                    || supportedVehicleModel == null || supportedVehicleModel.isBlank()
                    || garageLocation == null || garageLocation.isBlank()) {
                throw new IllegalStateException("Mechanics must provide specialization, supported vehicle models, and garage location.");
            }
        }

        try {
            User user = new User();
            user.setFullName(fullName.trim());
            user.setEmail(email.trim());
            user.setPassword(passwordEncoder.encode(password));
            user.setPhone(phone.trim());
            user.setRole(role);

            User savedUser = userRepository.save(user);

            if (role == Role.MECHANIC) {
                Mechanic mechanic = new Mechanic();
                mechanic.setUser(savedUser);
                mechanic.setSpecialization(specialization.trim());
                mechanic.setSupportedVehicleModel(supportedVehicleModel.trim());
                mechanic.setGarageLocation(garageLocation.trim());
                mechanicService.saveMechanic(mechanic);
            }
        } catch (DataIntegrityViolationException ex) {
            throw new IllegalStateException("Phone number or email is already in use. Please use different values.");
        }
    }
}
