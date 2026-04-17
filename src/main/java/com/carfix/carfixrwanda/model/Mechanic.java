package com.carfix.carfixrwanda.model;

import com.carfix.carfixrwanda.enums.AvailabilityStatus;
import jakarta.persistence.*;
import com.carfix.carfixrwanda.enums.VerificationStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
@Entity
public class Mechanic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @Column(nullable = false)
    private String specialization;

    @Column(nullable = false)
    private String supportedVehicleModel;

    @Column(nullable = false)
    private String garageLocation;

    @Enumerated(EnumType.STRING)
    private VerificationStatus verificationStatus = VerificationStatus.PENDING;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AvailabilityStatus availabilityStatus;

    public Mechanic() {
        this.availabilityStatus = AvailabilityStatus.AVAILABLE;
    }

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public String getSupportedVehicleModel() {
        return supportedVehicleModel;
    }

    public void setSupportedVehicleModel(String supportedVehicleModel) {
        this.supportedVehicleModel = supportedVehicleModel;
    }

    public String getGarageLocation() {
        return garageLocation;
    }

    public void setGarageLocation(String garageLocation) {
        this.garageLocation = garageLocation;
    }

    public VerificationStatus getVerificationStatus() {
        return verificationStatus;
    }

    public void setVerificationStatus(VerificationStatus verificationStatus) {
        this.verificationStatus = verificationStatus;
    }

    public AvailabilityStatus getAvailabilityStatus() {
        return availabilityStatus;
    }

    public void setAvailabilityStatus(AvailabilityStatus availabilityStatus) {
        this.availabilityStatus = availabilityStatus;
    }
}