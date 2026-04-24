package com.carfix.carfixrwanda.model;

import com.carfix.carfixrwanda.enums.CancellationRequestStatus;
import com.carfix.carfixrwanda.enums.RequestStatus;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
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

    @Column(nullable = false)
    private String problemTitle;

    @Column(nullable = false, length = 2000)
    private String problemDescription;

    private LocalDate preferredDate;

    private LocalTime preferredTime;

    private String locationNotes;

    @Enumerated(EnumType.STRING)
    @Column(name = "cancellation_request_status", length = 20)
    private CancellationRequestStatus cancellationRequestStatus;

    @Column(name = "cancellation_requested_at")
    private LocalDateTime cancellationRequestedAt;

    @Column(name = "cancellation_request_message", length = 500)
    private String cancellationRequestMessage;

    @Column(name = "cancellation_responded_at")
    private LocalDateTime cancellationRespondedAt;

    @Column(name = "cancellation_response_message", length = 500)
    private String cancellationResponseMessage;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RequestStatus status;

    @Column(name = "status_updated_at")
    private LocalDateTime statusUpdatedAt;

    @Column(name = "status_updated_by", length = 120)
    private String statusUpdatedBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "preferred_mechanic_id")
    private Mechanic preferredMechanic;

    @ManyToOne
    @JoinColumn(name = "vehicle_id", nullable = false)
    private CustomerVehicle customerVehicle;

    public ServiceRequest() {
        this.status = RequestStatus.PENDING;
        this.cancellationRequestStatus = CancellationRequestStatus.NONE;
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

    public Mechanic getPreferredMechanic() {
        return preferredMechanic;
    }

    public void setPreferredMechanic(Mechanic preferredMechanic) {
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

    public CancellationRequestStatus getCancellationRequestStatus() {
        return cancellationRequestStatus == null ? CancellationRequestStatus.NONE : cancellationRequestStatus;
    }

    public void setCancellationRequestStatus(CancellationRequestStatus cancellationRequestStatus) {
        this.cancellationRequestStatus = cancellationRequestStatus;
    }

    public LocalDateTime getCancellationRequestedAt() {
        return cancellationRequestedAt;
    }

    public void setCancellationRequestedAt(LocalDateTime cancellationRequestedAt) {
        this.cancellationRequestedAt = cancellationRequestedAt;
    }

    public LocalDateTime getCancellationRespondedAt() {
        return cancellationRespondedAt;
    }

    public void setCancellationRespondedAt(LocalDateTime cancellationRespondedAt) {
        this.cancellationRespondedAt = cancellationRespondedAt;
    }

    public String getCancellationRequestMessage() {
        return cancellationRequestMessage;
    }

    public void setCancellationRequestMessage(String cancellationRequestMessage) {
        this.cancellationRequestMessage = cancellationRequestMessage;
    }

    public String getCancellationResponseMessage() {
        return cancellationResponseMessage;
    }

    public void setCancellationResponseMessage(String cancellationResponseMessage) {
        this.cancellationResponseMessage = cancellationResponseMessage;
    }

    @Transient
    public boolean isCancellationRequested() {
        return getCancellationRequestStatus() == CancellationRequestStatus.PENDING;
    }

    @Transient
    public boolean hasCancellationResponseMessage() {
        return cancellationResponseMessage != null && !cancellationResponseMessage.isBlank();
    }

    @Transient
    public boolean hasCancellationRequestMessage() {
        return cancellationRequestMessage != null && !cancellationRequestMessage.isBlank();
    }

    public RequestStatus getStatus() {
        return status;
    }

    public void setStatus(RequestStatus status) {
        this.status = status;
    }

    public LocalDateTime getStatusUpdatedAt() {
        return statusUpdatedAt;
    }

    public void setStatusUpdatedAt(LocalDateTime statusUpdatedAt) {
        this.statusUpdatedAt = statusUpdatedAt;
    }

    public String getStatusUpdatedBy() {
        return statusUpdatedBy;
    }

    public void setStatusUpdatedBy(String statusUpdatedBy) {
        this.statusUpdatedBy = statusUpdatedBy;
    }

    public CustomerVehicle getCustomerVehicle() {
        return customerVehicle;
    }

    public void setCustomerVehicle(CustomerVehicle customerVehicle) {
        this.customerVehicle = customerVehicle;
    }
}
