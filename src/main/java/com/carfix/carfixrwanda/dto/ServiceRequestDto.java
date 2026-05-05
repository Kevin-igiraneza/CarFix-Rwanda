package com.carfix.carfixrwanda.dto;

import com.carfix.carfixrwanda.enums.RequestStatus;
import java.time.LocalDate;
import java.time.LocalTime;

public class ServiceRequestDto {
    private Long id;
    private String serviceType;
    private String urgency;
    private String problemTitle;
    private String problemDescription;
    private LocalDate preferredDate;
    private LocalTime preferredTime;
    private String locationNotes;
    private RequestStatus status;
    private CustomerVehicleDto customerVehicle;
    private MechanicDto preferredMechanic;

    private boolean cancellationRequested;
    private String cancellationRequestMessage;
    private boolean hasCancellationResponseMessage;
    private String cancellationResponseMessage;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getServiceType() { return serviceType; }
    public void setServiceType(String serviceType) { this.serviceType = serviceType; }

    public String getUrgency() { return urgency; }
    public void setUrgency(String urgency) { this.urgency = urgency; }

    public String getProblemTitle() { return problemTitle; }
    public void setProblemTitle(String problemTitle) { this.problemTitle = problemTitle; }

    public String getProblemDescription() { return problemDescription; }
    public void setProblemDescription(String problemDescription) { this.problemDescription = problemDescription; }

    public LocalDate getPreferredDate() { return preferredDate; }
    public void setPreferredDate(LocalDate preferredDate) { this.preferredDate = preferredDate; }

    public LocalTime getPreferredTime() { return preferredTime; }
    public void setPreferredTime(LocalTime preferredTime) { this.preferredTime = preferredTime; }

    public String getLocationNotes() { return locationNotes; }
    public void setLocationNotes(String locationNotes) { this.locationNotes = locationNotes; }

    public RequestStatus getStatus() { return status; }
    public void setStatus(RequestStatus status) { this.status = status; }

    public CustomerVehicleDto getCustomerVehicle() { return customerVehicle; }
    public void setCustomerVehicle(CustomerVehicleDto customerVehicle) { this.customerVehicle = customerVehicle; }

    public MechanicDto getPreferredMechanic() { return preferredMechanic; }
    public void setPreferredMechanic(MechanicDto preferredMechanic) { this.preferredMechanic = preferredMechanic; }

    public boolean isCancellationRequested() { return cancellationRequested; }
    public void setCancellationRequested(boolean cancellationRequested) { this.cancellationRequested = cancellationRequested; }

    public boolean isHasCancellationResponseMessage() { return hasCancellationResponseMessage; }
    public void setHasCancellationResponseMessage(boolean hasCancellationResponseMessage) { this.hasCancellationResponseMessage = hasCancellationResponseMessage; }
    public boolean hasCancellationResponseMessage() { return hasCancellationResponseMessage; }

    public String getCancellationResponseMessage() { return cancellationResponseMessage; }
    public void setCancellationResponseMessage(String cancellationResponseMessage) { this.cancellationResponseMessage = cancellationResponseMessage; }

    public String getCancellationRequestMessage() { return cancellationRequestMessage; }
    public void setCancellationRequestMessage(String cancellationRequestMessage) { this.cancellationRequestMessage = cancellationRequestMessage; }
    
    public boolean hasCancellationRequestMessage() {
        return cancellationRequestMessage != null && !cancellationRequestMessage.trim().isEmpty();
    }
}
