package com.carfix.carfixrwanda.service;

import com.carfix.carfixrwanda.model.ServiceRequest;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    // This is a stub for an email or SMS notification service
    public void sendNotification(String toEmail, String subject, String message) {
        System.out.println("==================================================");
        System.out.println("SIMULATING EMAIL NOTIFICATION TO: " + toEmail);
        System.out.println("SUBJECT: " + subject);
        System.out.println("MESSAGE: " + message);
        System.out.println("==================================================");
    }

    public void notifyStatusChange(ServiceRequest request) {
        String email = request.getCustomerVehicle().getUser().getEmail();
        String subject = "Update on your Service Request #" + request.getId();
        String message = "Your service request status has been updated to: " + request.getStatus();
        sendNotification(email, subject, message);
    }
}
