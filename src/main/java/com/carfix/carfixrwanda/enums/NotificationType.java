package com.carfix.carfixrwanda.enums;

public enum NotificationType {
    NEW_MECHANIC,           // Admin: a mechanic just registered and needs verification
    NO_MECHANIC_REQUEST,    // Admin: customer submitted a request without a mechanic
    REQUEST_ASSIGNED,       // Mechanic: a request was assigned to them (can accept/reject)
    REQUEST_ACCEPTED,       // Customer: mechanic accepted their request
    REQUEST_REJECTED,       // Customer: mechanic rejected their request
    CANCELLATION_REQUESTED, // Mechanic: customer requested cancellation
    CANCELLATION_APPROVED,  // Customer: mechanic approved cancellation
    CANCELLATION_DECLINED   // Customer: mechanic declined cancellation
}
