package com.carfix.carfixrwanda.controller;

import com.carfix.carfixrwanda.enums.RequestStatus;
import com.carfix.carfixrwanda.enums.VerificationStatus;
import com.carfix.carfixrwanda.model.CustomerVehicle;
import com.carfix.carfixrwanda.model.Mechanic;
import com.carfix.carfixrwanda.model.ServiceRequest;
import com.carfix.carfixrwanda.model.User;
import com.carfix.carfixrwanda.repository.UserRepository;
import com.carfix.carfixrwanda.service.CustomerVehicleService;
import com.carfix.carfixrwanda.service.MechanicService;
import com.carfix.carfixrwanda.service.NotificationService;
import com.carfix.carfixrwanda.service.ServiceRequestService;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.format.DateTimeParseException;
import java.util.List;

@Controller
public class ServiceRequestController {

    private final ServiceRequestService serviceRequestService;
    private final CustomerVehicleService customerVehicleService;
    private final UserRepository userRepository;
    private final MechanicService mechanicService;
    private final NotificationService notificationService;

    public ServiceRequestController(ServiceRequestService serviceRequestService,
                                    CustomerVehicleService customerVehicleService,
                                    UserRepository userRepository,
                                    MechanicService mechanicService,
                                    NotificationService notificationService) {
        this.serviceRequestService = serviceRequestService;
        this.customerVehicleService = customerVehicleService;
        this.userRepository = userRepository;
        this.mechanicService = mechanicService;
        this.notificationService = notificationService;
    }

    @GetMapping("/service-request")
    public String showServiceRequestForm(Model model,
                                         Authentication authentication,
                                         @RequestParam(value = "mechanicId", required = false) Long mechanicId) {
        User user = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new IllegalArgumentException("Logged in user not found"));
        List<CustomerVehicle> vehicles = customerVehicleService.getVehiclesByUserId(user.getId());
        List<Mechanic> mechanics = mechanicService.getVerifiedMechanics();

        model.addAttribute("vehicles", com.carfix.carfixrwanda.dto.DtoMapper.toCustomerVehicleDtoList(vehicles));
        model.addAttribute("mechanics", com.carfix.carfixrwanda.dto.DtoMapper.toMechanicDtoList(mechanics));
        model.addAttribute("preselectedMechanicId", mechanicId);
        return "service-request";
    }

    @PostMapping("/save-service-request")
    public String saveServiceRequest(
            @RequestParam("vehicleId") Long vehicleId,
            @RequestParam("serviceType") String serviceType,
            @RequestParam("urgency") String urgency,
            @RequestParam(value = "preferredMechanicId", required = false) Long preferredMechanicId,
            @RequestParam("problemTitle") String problemTitle,
            @RequestParam("problemDescription") String problemDescription,
            @RequestParam(value = "preferredDate", required = false) String preferredDate,
            @RequestParam(value = "preferredTime", required = false) String preferredTime,
            @RequestParam(value = "locationNotes", required = false) String locationNotes,
            Authentication authentication,
            RedirectAttributes redirectAttributes
    ) {
        try {
            User user = userRepository.findByEmail(authentication.getName())
                    .orElseThrow(() -> new IllegalArgumentException("Logged in user not found"));

            CustomerVehicle vehicle = customerVehicleService.findVehicleOwnedByUser(vehicleId, user.getId())
                    .orElseThrow(() -> new AccessDeniedException("You can only request service for your own vehicles"));

            ServiceRequest request = new ServiceRequest();
            request.setCustomerVehicle(vehicle);
            request.setServiceType(serviceType);
            request.setUrgency(urgency);
            request.setProblemTitle(problemTitle);
            request.setProblemDescription(problemDescription);
            request.setLocationNotes(locationNotes);

            if (preferredMechanicId != null) {
                Mechanic mechanic = mechanicService.findById(preferredMechanicId)
                        .orElseThrow(() -> new IllegalArgumentException("Mechanic not found"));
                if (mechanic.getVerificationStatus() != VerificationStatus.APPROVED) {
                    throw new AccessDeniedException("Only verified mechanics can be selected for a service request");
                }
                request.setPreferredMechanic(mechanic);
                request.setStatus(RequestStatus.ASSIGNED);
            } else {
                request.setStatus(RequestStatus.PENDING);
            }

            if (preferredDate != null && !preferredDate.isBlank()) {
                request.setPreferredDate(java.time.LocalDate.parse(preferredDate));
            }

            if (preferredTime != null && !preferredTime.isBlank()) {
                request.setPreferredTime(java.time.LocalTime.parse(preferredTime));
            }

            ServiceRequest saved = serviceRequestService.saveRequest(request, "CUSTOMER:" + user.getEmail());

            // Notifications
            if (preferredMechanicId == null) {
                // No mechanic chosen — alert admins to assign one
                notificationService.notifyAdminsNoMechanicRequest(saved.getId(), user.getFullName());
            } else {
                // Mechanic selected — notify them of the new assignment
                Mechanic assignedMechanic = saved.getPreferredMechanic();
                if (assignedMechanic != null && assignedMechanic.getUser() != null) {
                    notificationService.notifyMechanicRequestAssigned(
                            assignedMechanic.getUser(),
                            saved.getId(),
                            user.getFullName(),
                            saved.getCustomerVehicle().getVehicleName(),
                            saved.getProblemTitle()
                    );
                }
            }

            redirectAttributes.addFlashAttribute("flashMessage", "Service request created successfully.");
            return "redirect:/customer-dashboard";
        } catch (DateTimeParseException ex) {
            redirectAttributes.addFlashAttribute("flashError", "Preferred date/time format is invalid.");
            return "redirect:/service-request";
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("flashError", ex.getMessage());
            return "redirect:/service-request";
        }
    }
}
