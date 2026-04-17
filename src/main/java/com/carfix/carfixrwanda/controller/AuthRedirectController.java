package com.carfix.carfixrwanda.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthRedirectController {

    @GetMapping("/")
    public String homeRedirect(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return "index";
        }

        for (GrantedAuthority authority : authentication.getAuthorities()) {
            String role = authority.getAuthority();

            if ("ROLE_ADMIN".equals(role)) {
                return "redirect:/real-admin-dashboard";
            }
            if ("ROLE_MECHANIC".equals(role)) {
                return "redirect:/real-mechanic-dashboard";
            }
            if ("ROLE_CUSTOMER".equals(role)) {
                return "redirect:/customer-dashboard";
            }
        }

        return "index";
    }
}