package com.carfix.carfixrwanda.config;

import com.carfix.carfixrwanda.model.User;
import com.carfix.carfixrwanda.repository.UserRepository;
import com.carfix.carfixrwanda.service.NotificationService;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

/**
 * Exposes simple flags for templates so navigation can hide login/register when signed in
 * and show the correct dashboard link per role. Also injects unread notification count.
 */
@ControllerAdvice
public class GlobalNavAdvice {

    private final NotificationService notificationService;
    private final UserRepository userRepository;
    private final com.carfix.carfixrwanda.repository.MechanicRepository mechanicRepository;
    private final com.carfix.carfixrwanda.repository.ServiceRequestRepository serviceRequestRepository;

    public GlobalNavAdvice(NotificationService notificationService, 
                            UserRepository userRepository,
                            com.carfix.carfixrwanda.repository.MechanicRepository mechanicRepository,
                            com.carfix.carfixrwanda.repository.ServiceRequestRepository serviceRequestRepository) {
        this.notificationService = notificationService;
        this.userRepository = userRepository;
        this.mechanicRepository = mechanicRepository;
        this.serviceRequestRepository = serviceRequestRepository;
    }

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
        model.addAttribute("unreadNotificationCount", 0L);
        model.addAttribute("pendingMechanicCount", 0L);
        model.addAttribute("unassignedRequestCount", 0L);

        if (!loggedIn) {
            return;
        }

        boolean customer = hasRole(auth, "ROLE_CUSTOMER");
        boolean mechanic = hasRole(auth, "ROLE_MECHANIC");
        boolean admin    = hasRole(auth, "ROLE_ADMIN");

        model.addAttribute("navIsCustomer", customer);
        model.addAttribute("navIsMechanic", mechanic);
        model.addAttribute("navIsAdmin", admin);

        if (admin) {
            model.addAttribute("navDashboardUrl", "/real-admin-dashboard");
            model.addAttribute("pendingMechanicCount", mechanicRepository.countByVerificationStatus(com.carfix.carfixrwanda.enums.VerificationStatus.PENDING));
            model.addAttribute("unassignedRequestCount", serviceRequestRepository.countByStatus(com.carfix.carfixrwanda.enums.RequestStatus.PENDING));
        } else if (mechanic) {
            model.addAttribute("navDashboardUrl", "/real-mechanic-dashboard");
        } else if (customer) {
            model.addAttribute("navDashboardUrl", "/customer-dashboard");
        }

        // Inject unread notification count for sidebar badge
        try {
            userRepository.findByEmail(auth.getName()).ifPresent(user -> {
                long unread = notificationService.getUnreadCount(user.getId());
                model.addAttribute("unreadNotificationCount", unread);
            });
        } catch (Exception ignored) {
            // Don't break page rendering if notification count fails
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
