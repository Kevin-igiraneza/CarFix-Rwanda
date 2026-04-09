package com.carfix.carfixrwanda.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Mechanic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;
    private String specialization;
    private String supportedVehicleModel;
    private String phoneNumber;
    private String garageLocation;

    public Mechanic() {
    }

    public Long getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getGarageLocation() {
        return garageLocation;
    }

    public void setGarageLocation(String garageLocation) {
        this.garageLocation = garageLocation;
    }
}