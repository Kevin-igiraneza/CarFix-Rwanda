package com.carfix.carfixrwanda.service;

import com.carfix.carfixrwanda.enums.CancellationRequestStatus;
import com.carfix.carfixrwanda.enums.RequestStatus;
import com.carfix.carfixrwanda.enums.VerificationStatus;
import com.carfix.carfixrwanda.model.Mechanic;
import com.carfix.carfixrwanda.model.ServiceRequest;
import com.carfix.carfixrwanda.model.ServiceRequestStatusHistory;
import com.carfix.carfixrwanda.repository.ServiceRequestRepository;
import com.carfix.carfixrwanda.repository.ServiceRequestStatusHistoryRepository;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

@Service
public class ServiceRequestService {

    private static final Set<RequestStatus> TERMINAL_STATUSES =
            EnumSet.of(RequestStatus.COMPLETED, RequestStatus.CANCELLED);

    private final ServiceRequestRepository serviceRequestRepository;
    private final ServiceRequestStatusHistoryRepository statusHistoryRepository;
    private final MechanicService mechanicService;

    public ServiceRequestService(ServiceRequestRepository serviceRequestRepository,
                                 ServiceRequestStatusHistoryRepository statusHistoryRepository,
                                 MechanicService mechanicService) {
        this.serviceRequestRepository = serviceRequestRepository;
        this.statusHistoryRepository = statusHistoryRepository;
        this.mechanicService = mechanicService;
    }

    public ServiceRequest saveRequest(ServiceRequest serviceRequest, String actor) {
        normalizeAndValidateNewRequest(serviceRequest);
        RequestStatus initialStatus = serviceRequest.getStatus();
        auditStatusChange(serviceRequest, actor);
        ServiceRequest saved = serviceRequestRepository.save(serviceRequest);
        appendHistory(saved, null, initialStatus, actor, "REQUEST_CREATED", "Customer created a new service request.");
        return saved;
    }

    public List<ServiceRequest> getAllRequests() {
        return serviceRequestRepository.findAll();
    }

    public List<ServiceRequest> getRequestsByVehicleId(Long vehicleId) {
        return serviceRequestRepository.findByCustomerVehicleId(vehicleId);
    }

    public List<ServiceRequest> getRequestsByUserId(Long userId) {
        return serviceRequestRepository.findByCustomerVehicleUserId(userId);
    }

    public List<ServiceRequest> getRequestsForMechanic(Mechanic mechanic) {
        return serviceRequestRepository.findByPreferredMechanicId(mechanic.getId());
    }

    public List<ServiceRequestStatusHistory> getRecentStatusHistory() {
        return statusHistoryRepository.findTop20ByOrderByChangedAtDesc();
    }

    public void updateRequestStatusAsMechanic(Long requestId, RequestStatus status, Mechanic mechanic, String actor) {
        ServiceRequest request = serviceRequestRepository.findById(requestId)
                .orElseThrow(() -> new IllegalArgumentException("Request not found"));
        Mechanic assigned = request.getPreferredMechanic();
        if (assigned == null || !assigned.getId().equals(mechanic.getId())) {
            throw new AccessDeniedException("This service request is not assigned to you");
        }
        if (request.isCancellationRequested()) {
            throw new IllegalStateException("Respond to the customer's cancellation request before changing the job status.");
        }

        RequestStatus current = request.getStatus();
        if (status == current) {
            return;
        }
        if (current == RequestStatus.ASSIGNED && status == RequestStatus.IN_PROGRESS) {
            request.setStatus(status);
            auditStatusChange(request, actor);
            ServiceRequest saved = serviceRequestRepository.save(request);
            appendHistory(saved, current, status, actor, "MECHANIC_STATUS_UPDATED", null);
            return;
        }
        if (current == RequestStatus.IN_PROGRESS && status == RequestStatus.COMPLETED) {
            request.setStatus(status);
            auditStatusChange(request, actor);
            ServiceRequest saved = serviceRequestRepository.save(request);
            appendHistory(saved, current, status, actor, "MECHANIC_STATUS_UPDATED", null);
            return;
        }
        throw new IllegalStateException("Mechanic can only move status from ASSIGNED to IN_PROGRESS, or IN_PROGRESS to COMPLETED.");
    }

    public void updateRequestStatusAsAdmin(Long requestId, RequestStatus status, String actor) {
        ServiceRequest request = serviceRequestRepository.findById(requestId)
                .orElseThrow(() -> new IllegalArgumentException("Request not found"));

        RequestStatus current = request.getStatus();
        if (status == current) {
            return;
        }
        if (TERMINAL_STATUSES.contains(current)) {
            throw new IllegalStateException("Closed requests cannot be reopened or changed.");
        }

        if (status == RequestStatus.ASSIGNED || status == RequestStatus.IN_PROGRESS) {
            if (request.getPreferredMechanic() == null) {
                throw new IllegalStateException("Assign a mechanic before setting status to ASSIGNED or IN_PROGRESS.");
            }
        }

        if (status == RequestStatus.PENDING) {
            request.setPreferredMechanic(null);
        }

        if (current == RequestStatus.PENDING
                && status != RequestStatus.ASSIGNED
                && status != RequestStatus.CANCELLED) {
            throw new IllegalStateException("From PENDING, admin can only move to ASSIGNED or CANCELLED.");
        }

        if (current == RequestStatus.ASSIGNED
                && status != RequestStatus.IN_PROGRESS
                && status != RequestStatus.PENDING
                && status != RequestStatus.CANCELLED) {
            throw new IllegalStateException("From ASSIGNED, admin can move to IN_PROGRESS, PENDING, or CANCELLED.");
        }

        if (current == RequestStatus.IN_PROGRESS
                && status != RequestStatus.COMPLETED
                && status != RequestStatus.ASSIGNED
                && status != RequestStatus.CANCELLED) {
            throw new IllegalStateException("From IN_PROGRESS, admin can move to COMPLETED, ASSIGNED, or CANCELLED.");
        }

        request.setStatus(status);
        auditStatusChange(request, actor);
        ServiceRequest saved = serviceRequestRepository.save(request);
        appendHistory(saved, current, status, actor, "ADMIN_STATUS_UPDATED", null);
    }

    public void requestCancellationAsCustomer(Long requestId, Long customerUserId, String cancellationReason, String actor) {
        ServiceRequest request = serviceRequestRepository.findById(requestId)
                .orElseThrow(() -> new IllegalArgumentException("Request not found"));
        if (!request.getCustomerVehicle().getUser().getId().equals(customerUserId)) {
            throw new AccessDeniedException("You can only manage your own service requests");
        }
        RequestStatus current = request.getStatus();
        if (TERMINAL_STATUSES.contains(current)) {
            throw new IllegalStateException("This request is already closed.");
        }
        if (request.getPreferredMechanic() == null) {
            throw new IllegalStateException("This request is not assigned to a mechanic yet. Please contact an administrator.");
        }
        if (request.isCancellationRequested()) {
            throw new IllegalStateException("A cancellation request is already waiting for the mechanic's response.");
        }
        String reason = requireText(cancellationReason, "Tell the mechanic why you want to cancel this request.");
        request.setCancellationRequestStatus(CancellationRequestStatus.PENDING);
        request.setCancellationRequestedAt(LocalDateTime.now());
        request.setCancellationRequestMessage(reason);
        request.setCancellationRespondedAt(null);
        request.setCancellationResponseMessage(null);
        ServiceRequest saved = serviceRequestRepository.save(request);
        appendHistory(saved, current, current, actor, "CUSTOMER_CANCELLATION_REQUESTED",
                reason);
    }

    public void reviewCancellationRequestAsMechanic(Long requestId,
                                                    boolean approve,
                                                    String responseMessage,
                                                    Mechanic mechanic,
                                                    String actor) {
        ServiceRequest request = serviceRequestRepository.findById(requestId)
                .orElseThrow(() -> new IllegalArgumentException("Request not found"));
        Mechanic assigned = request.getPreferredMechanic();
        if (assigned == null || !assigned.getId().equals(mechanic.getId())) {
            throw new AccessDeniedException("This service request is not assigned to you");
        }
        if (!request.isCancellationRequested()) {
            throw new IllegalStateException("There is no pending cancellation request for this job.");
        }

        String mechanicMessage = requireText(responseMessage, "Enter a message for the customer.");
        RequestStatus current = request.getStatus();
        request.setCancellationRequestedAt(request.getCancellationRequestedAt() == null ? LocalDateTime.now() : request.getCancellationRequestedAt());
        request.setCancellationRespondedAt(LocalDateTime.now());
        request.setCancellationResponseMessage(mechanicMessage);

        if (approve) {
            request.setCancellationRequestStatus(CancellationRequestStatus.APPROVED);
            request.setStatus(RequestStatus.CANCELLED);
            auditStatusChange(request, actor);
            ServiceRequest saved = serviceRequestRepository.save(request);
            appendHistory(saved, current, RequestStatus.CANCELLED, actor, "MECHANIC_CANCELLATION_APPROVED", mechanicMessage);
            return;
        }

        request.setCancellationRequestStatus(CancellationRequestStatus.DECLINED);
        ServiceRequest saved = serviceRequestRepository.save(request);
        appendHistory(saved, current, current, actor, "MECHANIC_CANCELLATION_DECLINED", mechanicMessage);
    }

    /**
     * Admin assigns or changes the preferred mechanic (must be verified). Updates status when appropriate.
     */
    public void assignMechanicAsAdmin(Long requestId, Long mechanicId, String actor) {
        ServiceRequest request = serviceRequestRepository.findById(requestId)
                .orElseThrow(() -> new IllegalArgumentException("Request not found"));
        RequestStatus before = request.getStatus();
        if (request.getStatus() == RequestStatus.IN_PROGRESS) {
            throw new IllegalStateException("Cannot reassign a request that is already in progress.");
        }
        if (TERMINAL_STATUSES.contains(request.getStatus())) {
            throw new IllegalStateException("Cannot assign a mechanic to a closed request.");
        }
        Mechanic mechanic = mechanicService.findById(mechanicId)
                .orElseThrow(() -> new IllegalArgumentException("Mechanic not found"));
        if (mechanic.getVerificationStatus() != VerificationStatus.APPROVED) {
            throw new IllegalStateException("Only verified mechanics can be assigned");
        }
        request.setPreferredMechanic(mechanic);
        if (request.getStatus() == RequestStatus.PENDING) {
            request.setStatus(RequestStatus.ASSIGNED);
        }
        auditStatusChange(request, actor);
        ServiceRequest saved = serviceRequestRepository.save(request);
        appendHistory(
                saved,
            before,
                saved.getStatus(),
                actor,
                "ASSIGNMENT_CHANGED",
                "Admin assigned or changed the preferred mechanic."
        );
    }

    public void clearMechanicAssignmentAsAdmin(Long requestId, String actor) {
        ServiceRequest request = serviceRequestRepository.findById(requestId)
                .orElseThrow(() -> new IllegalArgumentException("Request not found"));
        RequestStatus before = request.getStatus();
        if (request.getStatus() == RequestStatus.CANCELLED || request.getStatus() == RequestStatus.COMPLETED) {
            throw new IllegalStateException("Cannot clear assignment on a closed request.");
        }
        if (request.getStatus() == RequestStatus.IN_PROGRESS) {
            throw new IllegalStateException("Mark the request as ASSIGNED or PENDING before unassigning a mechanic.");
        }
        request.setPreferredMechanic(null);
        if (request.getStatus() == RequestStatus.ASSIGNED) {
            request.setStatus(RequestStatus.PENDING);
        }
        auditStatusChange(request, actor);
        ServiceRequest saved = serviceRequestRepository.save(request);
        appendHistory(saved, before, saved.getStatus(), actor, "ASSIGNMENT_CLEARED", "Admin cleared the preferred mechanic assignment.");
    }

    @Transactional
    public void deleteRequestAsAdmin(Long requestId, String actor) {
        ServiceRequest request = serviceRequestRepository.findById(requestId)
                .orElseThrow(() -> new IllegalArgumentException("Request not found"));

        if (!TERMINAL_STATUSES.contains(request.getStatus())) {
            throw new IllegalStateException("Only completed or cancelled requests can be deleted.");
        }

        appendHistory(
                request,
                request.getStatus(),
                request.getStatus(),
                actor,
                "REQUEST_DELETED",
                "Admin deleted a closed request."
        );

        deleteRequestWithHistory(request);
    }

    @Transactional
    public void deleteCancelledRequestAsCustomer(Long requestId, Long customerUserId) {
        ServiceRequest request = serviceRequestRepository.findById(requestId)
                .orElseThrow(() -> new IllegalArgumentException("Request not found"));
        if (!request.getCustomerVehicle().getUser().getId().equals(customerUserId)) {
            throw new AccessDeniedException("You can only delete your own cancelled requests");
        }
        if (request.getStatus() != RequestStatus.CANCELLED) {
            throw new IllegalStateException("Only cancelled requests can be deleted from the dashboard.");
        }
        deleteRequestWithHistory(request);
    }

    @Transactional
    public void deleteCancelledRequestAsMechanic(Long requestId, Long mechanicId) {
        ServiceRequest request = serviceRequestRepository.findById(requestId)
                .orElseThrow(() -> new IllegalArgumentException("Request not found"));
        Mechanic assigned = request.getPreferredMechanic();
        if (assigned == null || !assigned.getId().equals(mechanicId)) {
            throw new AccessDeniedException("You can only delete cancelled requests assigned to you");
        }
        if (request.getStatus() != RequestStatus.CANCELLED) {
            throw new IllegalStateException("Only cancelled requests can be deleted from the dashboard.");
        }
        deleteRequestWithHistory(request);
    }

    private void normalizeAndValidateNewRequest(ServiceRequest request) {
        if (request.getCustomerVehicle() == null) {
            throw new IllegalArgumentException("Select a valid vehicle before submitting the request.");
        }
        request.setServiceType(requireText(request.getServiceType(), "Service type is required."));
        request.setUrgency(requireText(request.getUrgency(), "Urgency is required."));
        request.setProblemTitle(requireText(request.getProblemTitle(), "Problem title is required."));
        request.setProblemDescription(requireText(request.getProblemDescription(), "Problem description is required."));
        if (request.getLocationNotes() != null) {
            String notes = request.getLocationNotes().trim();
            request.setLocationNotes(notes.isEmpty() ? null : notes);
        }
    }

    private static String requireText(String value, String message) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(message);
        }
        return value.trim();
    }

    private void auditStatusChange(ServiceRequest request, String actor) {
        request.setStatusUpdatedAt(LocalDateTime.now());
        request.setStatusUpdatedBy(actor == null || actor.isBlank() ? "SYSTEM" : actor.trim());
    }

    private void deleteRequestWithHistory(ServiceRequest request) {
        statusHistoryRepository.deleteByServiceRequestId(request.getId());
        serviceRequestRepository.delete(request);
    }

    private void appendHistory(ServiceRequest request,
                               RequestStatus previousStatus,
                               RequestStatus newStatus,
                               String actor,
                               String actionType,
                               String note) {
        ServiceRequestStatusHistory history = new ServiceRequestStatusHistory();
        history.setServiceRequest(request);
        history.setPreviousStatus(previousStatus);
        history.setNewStatus(newStatus);
        history.setChangedBy(actor == null || actor.isBlank() ? "SYSTEM" : actor.trim());
        history.setChangedAt(LocalDateTime.now());
        history.setActionType(actionType);
        history.setNote(note);
        statusHistoryRepository.save(history);
    }

}
