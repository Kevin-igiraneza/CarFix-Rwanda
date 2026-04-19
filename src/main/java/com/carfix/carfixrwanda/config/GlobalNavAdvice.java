package com.carfix.carfixrwanda.config;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

/**
 * Exposes simple flags for templates so navigation can hide login/register when signed in
 * and show the correct dashboard link per role.
 */
@ControllerAdvice
public class GlobalNavAdvice {

    @ModelAttribute
    public void globalNavAttributes(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        boolean loggedIn = auth != null
                && auth.isAuthenticated()
                && !(auth instanceof AnonymousAuthenticationToken);

        model.addAttribute("navLoggedIn", loggedIn);
        model.addAttribute("navIsCustomer", false);
        model.addAttribute("navIsMechanic", false);
        model.addAttribute("navIsAdmin", false);
        model.addAttribute("navDashboardUrl", "/");

        if (!loggedIn) {
            return;
        }

        boolean customer = hasRole(auth, "ROLE_CUSTOMER");
        boolean mechanic = hasRole(auth, "ROLE_MECHANIC");
        boolean admin = hasRole(auth, "ROLE_ADMIN");

        model.addAttribute("navIsCustomer", customer);
        model.addAttribute("navIsMechanic", mechanic);
        model.addAttribute("navIsAdmin", admin);

        if (admin) {
            model.addAttribute("navDashboardUrl", "/real-admin-dashboard");
        } else if (mechanic) {
            model.addAttribute("navDashboardUrl", "/real-mechanic-dashboard");
        } else if (customer) {
            model.addAttribute("navDashboardUrl", "/customer-dashboard");
        }
    }

    private static boolean hasRole(Authentication auth, String role) {
        for (GrantedAuthority a : auth.getAuthorities()) {
            if (role.equals(a.getAuthority())) {
                return true;
            }
        }
        return false;
    }
}
