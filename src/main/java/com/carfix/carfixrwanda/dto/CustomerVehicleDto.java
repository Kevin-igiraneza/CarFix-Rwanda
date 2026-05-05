package com.carfix.carfixrwanda.dto;

public class CustomerVehicleDto {
    private Long id;
    private String vehicleName;
    private String brand;
    private String model;
    private Integer year;
    private String plateNumber;
    private String fuelType;
    private String transmission;
    private UserDto user;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getVehicleName() { return vehicleName; }
    public void setVehicleName(String vehicleName) { this.vehicleName = vehicleName; }

    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }

    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }

    public Integer getYear() { return year; }
    public void setYear(Integer year) { this.year = year; }

    public String getPlateNumber() { return plateNumber; }
    public void setPlateNumber(String plateNumber) { this.plateNumber = plateNumber; }

    public String getFuelType() { return fuelType; }
    public void setFuelType(String fuelType) { this.fuelType = fuelType; }

    public String getTransmission() { return transmission; }
    public void setTransmission(String transmission) { this.transmission = transmission; }

    public UserDto getUser() { return user; }
    public void setUser(UserDto user) { this.user = user; }
}
