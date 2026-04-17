package com.carfix.carfixrwanda.controller;

import com.carfix.carfixrwanda.enums.Role;
import com.carfix.carfixrwanda.model.User;
import com.carfix.carfixrwanda.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RegistrationController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public RegistrationController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/register")
    public String showRegisterPage() {
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@RequestParam("fullName") String fullName,
                               @RequestParam("email") String email,
                               @RequestParam("password") String password,
                               @RequestParam("role") Role role,
                               Model model) {

        if (userRepository.findByEmail(email).isPresent()) {
            model.addAttribute("errorMessage", "Email already exists. Use another one.");
            return "register";
        }

        User user = new User();
        user.setFullName(fullName);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setPhone("N/A");
        user.setRole(role);

        userRepository.save(user);

        return "redirect:/login";
    }
}