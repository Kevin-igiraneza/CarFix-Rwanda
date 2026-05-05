package com.carfix.carfixrwanda.dto;

import com.carfix.carfixrwanda.enums.VerificationStatus;

public class MechanicDto {
    private Long id;
    private UserDto user;
    private String specialization;
    private String supportedVehicleModel;
    private String garageLocation;
    private VerificationStatus verificationStatus;
    private com.carfix.carfixrwanda.enums.AvailabilityStatus availabilityStatus;
    
    private Double averageRating;
    private Integer reviewCount;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public UserDto getUser() { return user; }
    public void setUser(UserDto user) { this.user = user; }

    public String getSpecialization() { return specialization; }
    public void setSpecialization(String specialization) { this.specialization = specialization; }

    public String getSupportedVehicleModel() { return supportedVehicleModel; }
    public void setSupportedVehicleModel(String supportedVehicleModel) { this.supportedVehicleModel = supportedVehicleModel; }

    public String getGarageLocation() { return garageLocation; }
    public void setGarageLocation(String garageLocation) { this.garageLocation = garageLocation; }

    public VerificationStatus getVerificationStatus() { return verificationStatus; }
    public void setVerificationStatus(VerificationStatus verificationStatus) { this.verificationStatus = verificationStatus; }

    public com.carfix.carfixrwanda.enums.AvailabilityStatus getAvailabilityStatus() { return availabilityStatus; }
    public void setAvailabilityStatus(com.carfix.carfixrwanda.enums.AvailabilityStatus availabilityStatus) { this.availabilityStatus = availabilityStatus; }

    public Double getAverageRating() { return averageRating; }
    public void setAverageRating(Double averageRating) { this.averageRating = averageRating; }

    public Integer getReviewCount() { return reviewCount; }
    public void setReviewCount(Integer reviewCount) { this.reviewCount = reviewCount; }
}
