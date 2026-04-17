package com.carfix.carfixrwanda.model;

import com.carfix.carfixrwanda.enums.RequestStatus;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "service_requests")
public class ServiceRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String serviceType;

    @Column(nullable = false)
    private String urgency;

    private String preferredMechanic;

    @Column(nullable = false)
    private String problemTitle;

    @Column(nullable = false, length = 2000)
    private String problemDescription;

    private LocalDate preferredDate;

    private LocalTime preferredTime;

    private String locationNotes;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RequestStatus status;

    @ManyToOne
    @JoinColumn(name = "vehicle_id", nullable = false)
    private CustomerVehicle customerVehicle;

    public ServiceRequest() {
        this.status = RequestStatus.PENDING;
    }

    public Long getId() {
        return id;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getUrgency() {
        return urgency;
    }

    public void setUrgency(String urgency) {
        this.urgency = urgency;
    }

    public String getPreferredMechanic() {
        return preferredMechanic;
    }

    public void setPreferredMechanic(String preferredMechanic) {
        this.preferredMechanic = preferredMechanic;
    }

    public String getProblemTitle() {
        return problemTitle;
    }

    public void setProblemTitle(String problemTitle) {
        this.problemTitle = problemTitle;
    }

    public String getProblemDescription() {
        return problemDescription;
    }

    public void setProblemDescription(String problemDescription) {
        this.problemDescription = problemDescription;
    }

    public LocalDate getPreferredDate() {
        return preferredDate;
    }

    public void setPreferredDate(LocalDate preferredDate) {
        this.preferredDate = preferredDate;
    }

    public LocalTime getPreferredTime() {
        return preferredTime;
    }

    public void setPreferredTime(LocalTime preferredTime) {
        this.preferredTime = preferredTime;
    }

    public String getLocationNotes() {
        return locationNotes;
    }

    public void setLocationNotes(String locationNotes) {
        this.locationNotes = locationNotes;
    }

    public RequestStatus getStatus() {
        return status;
    }

    public void setStatus(RequestStatus status) {
        this.status = status;
    }

    public CustomerVehicle getCustomerVehicle() {
        return customerVehicle;
    }

    public void setCustomerVehicle(CustomerVehicle customerVehicle) {
        this.customerVehicle = customerVehicle;
    }
}