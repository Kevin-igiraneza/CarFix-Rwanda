package com.carfix.carfixrwanda.dto;

import com.carfix.carfixrwanda.model.CustomerVehicle;
import com.carfix.carfixrwanda.model.Mechanic;
import com.carfix.carfixrwanda.model.ServiceRequest;
import com.carfix.carfixrwanda.model.User;

import java.util.List;
import java.util.stream.Collectors;

public class DtoMapper {

    public static UserDto toUserDto(User user) {
        if (user == null) return null;
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setEmail(user.getEmail());
        dto.setFullName(user.getFullName());
        dto.setPhone(user.getPhone());
        dto.setRole(user.getRole());
        dto.setCreatedAt(user.getCreatedAt());
        return dto;
    }

    public static CustomerVehicleDto toCustomerVehicleDto(CustomerVehicle vehicle) {
        if (vehicle == null) return null;
        CustomerVehicleDto dto = new CustomerVehicleDto();
        dto.setId(vehicle.getId());
        dto.setVehicleName(vehicle.getVehicleName());
        dto.setBrand(vehicle.getBrand());
        dto.setModel(vehicle.getModel());
        dto.setYear(vehicle.getYear());
        dto.setPlateNumber(vehicle.getPlateNumber());
        dto.setFuelType(vehicle.getFuelType());
        dto.setTransmission(vehicle.getTransmission());
        if (vehicle.getUser() != null) {
            dto.setUser(toUserDto(vehicle.getUser()));
        }
        return dto;
    }

    public static MechanicDto toMechanicDto(Mechanic mechanic) {
        if (mechanic == null) return null;
        MechanicDto dto = new MechanicDto();
        dto.setId(mechanic.getId());
        dto.setUser(toUserDto(mechanic.getUser()));
        dto.setSpecialization(mechanic.getSpecialization());
        dto.setSupportedVehicleModel(mechanic.getSupportedVehicleModel());
        dto.setGarageLocation(mechanic.getGarageLocation());
        dto.setVerificationStatus(mechanic.getVerificationStatus());
        dto.setAvailabilityStatus(mechanic.getAvailabilityStatus());
        return dto;
    }

    public static ServiceRequestDto toServiceRequestDto(ServiceRequest request) {
        if (request == null) return null;
        ServiceRequestDto dto = new ServiceRequestDto();
        dto.setId(request.getId());
        dto.setServiceType(request.getServiceType());
        dto.setUrgency(request.getUrgency());
        dto.setProblemTitle(request.getProblemTitle());
        dto.setProblemDescription(request.getProblemDescription());
        dto.setPreferredDate(request.getPreferredDate());
        dto.setPreferredTime(request.getPreferredTime());
        dto.setLocationNotes(request.getLocationNotes());
        dto.setStatus(request.getStatus());
        dto.setCustomerVehicle(toCustomerVehicleDto(request.getCustomerVehicle()));
        dto.setPreferredMechanic(toMechanicDto(request.getPreferredMechanic()));
        
        dto.setCancellationRequested(request.isCancellationRequested());
        dto.setCancellationRequestMessage(request.getCancellationRequestMessage());
        dto.setHasCancellationResponseMessage(request.hasCancellationResponseMessage());
        dto.setCancellationResponseMessage(request.getCancellationResponseMessage());
        return dto;
    }

    public static List<ServiceRequestDto> toServiceRequestDtoList(List<ServiceRequest> requests) {
        return requests.stream().map(DtoMapper::toServiceRequestDto).collect(Collectors.toList());
    }

    public static List<CustomerVehicleDto> toCustomerVehicleDtoList(List<CustomerVehicle> vehicles) {
        return vehicles.stream().map(DtoMapper::toCustomerVehicleDto).collect(Collectors.toList());
    }

    public static List<MechanicDto> toMechanicDtoList(List<Mechanic> mechanics) {
        return mechanics.stream().map(DtoMapper::toMechanicDto).collect(Collectors.toList());
    }

    public static List<UserDto> toUserDtoList(List<User> users) {
        return users.stream().map(DtoMapper::toUserDto).collect(Collectors.toList());
    }
}
