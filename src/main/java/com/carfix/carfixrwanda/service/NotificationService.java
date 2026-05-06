package com.carfix.carfixrwanda.service;

import com.carfix.carfixrwanda.enums.NotificationType;
import com.carfix.carfixrwanda.model.Notification;
import com.carfix.carfixrwanda.model.ServiceRequest;
import com.carfix.carfixrwanda.model.User;
import com.carfix.carfixrwanda.repository.NotificationRepository;
import com.carfix.carfixrwanda.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;

    public NotificationService(NotificationRepository notificationRepository,
                               UserRepository userRepository) {
        this.notificationRepository = notificationRepository;
        this.userRepository = userRepository;
    }

    // ── Generic send ──────────────────────────────────────────────────────────
    public void send(User recipient, String title, String message, NotificationType type, String link) {
        Notification n = new Notification(recipient, title, message, type, link);
        notificationRepository.save(n);
    }

    // ── Admin: mechanic registered ────────────────────────────────────────────
    public void notifyAdminsNewMechanic(String mechanicName) {
        List<User> admins = userRepository.findByRole(com.carfix.carfixrwanda.enums.Role.ADMIN);
        for (User admin : admins) {
            send(admin,
                    "New mechanic pending review",
                    mechanicName + " just registered as a mechanic and needs verification.",
                    NotificationType.NEW_MECHANIC,
                    "/admin/mechanic-verification");
        }
    }

    // ── Admin: customer submitted request with no mechanic ────────────────────
    public void notifyAdminsNoMechanicRequest(Long requestId, String customerName) {
        List<User> admins = userRepository.findByRole(com.carfix.carfixrwanda.enums.Role.ADMIN);
        for (User admin : admins) {
            send(admin,
                    "Service request needs mechanic",
                    "Request #" + requestId + " was submitted by " + customerName + " without a mechanic assigned.",
                    NotificationType.NO_MECHANIC_REQUEST,
                    "/admin/service-requests");
        }
    }

    // ── Mechanic: request assigned to them ───────────────────────────────────
    public void notifyMechanicRequestAssigned(User mechanicUser, Long requestId,
                                              String customerName, String vehicleName, String problemTitle) {
        send(mechanicUser,
                "New service request assigned to you",
                "Request #" + requestId + " from " + customerName + " — " + vehicleName + ": " + problemTitle,
                NotificationType.REQUEST_ASSIGNED,
                "/real-mechanic-dashboard?requestId=" + requestId);
    }

    // ── Customer: mechanic accepted ───────────────────────────────────────────
    public void notifyCustomerRequestAccepted(User customerUser, Long requestId, String mechanicName) {
        send(customerUser,
                "Mechanic accepted your job",
                mechanicName + " accepted your request #" + requestId + " and will work on it.",
                NotificationType.REQUEST_ACCEPTED,
                "/customer-dashboard");
    }

    // ── Customer: mechanic rejected ───────────────────────────────────────────
    public void notifyCustomerRequestRejected(User customerUser, Long requestId, String mechanicName) {
        send(customerUser,
                "Mechanic declined your request",
                mechanicName + " has declined request #" + requestId + ". An admin will assign another mechanic.",
                NotificationType.REQUEST_REJECTED,
                "/customer-dashboard");
    }

    // ── Read/unread helpers ──────────────────────────────────────────────────
    public long getUnreadCount(Long userId) {
        return notificationRepository.countByRecipientIdAndReadFalse(userId);
    }

    public List<Notification> getNotificationsForUser(Long userId) {
        return notificationRepository.findByRecipientIdOrderByCreatedAtDesc(userId);
    }

    public void markAllRead(Long userId) {
        notificationRepository.markAllReadForUser(userId);
    }

    // ── Legacy stub kept for backward compat ─────────────────────────────────
    public void notifyStatusChange(ServiceRequest request) {
        // no-op: status changes are reflected in the dashboard directly
    }
}
