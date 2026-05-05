package com.carfix.carfixrwanda.controller;

import com.carfix.carfixrwanda.model.Invoice;
import com.carfix.carfixrwanda.model.User;
import com.carfix.carfixrwanda.repository.UserRepository;
import com.carfix.carfixrwanda.service.InvoiceService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;

@Controller
public class InvoiceController {

    private final InvoiceService invoiceService;
    private final UserRepository userRepository;

    public InvoiceController(InvoiceService invoiceService, UserRepository userRepository) {
        this.invoiceService = invoiceService;
        this.userRepository = userRepository;
    }

    @PostMapping("/mechanic/generate-invoice")
    public String generateInvoice(@RequestParam("requestId") Long requestId,
                                  @RequestParam("partsTotal") BigDecimal partsTotal,
                                  @RequestParam("laborTotal") BigDecimal laborTotal,
                                  RedirectAttributes redirectAttributes) {
        try {
            invoiceService.generateInvoice(requestId, partsTotal, laborTotal);
            redirectAttributes.addFlashAttribute("mechanicFlashMessage", "Invoice generated successfully.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mechanicFlashError", e.getMessage());
        }
        return "redirect:/real-mechanic-dashboard";
    }

    @GetMapping("/invoice/{id}")
    public String viewInvoice(@PathVariable Long id, Model model, Authentication authentication) {
        User currentUser = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        
        Invoice invoice = invoiceService.getInvoice(id);
        
        // Basic auth check
        boolean isCustomer = invoice.getServiceRequest().getCustomerVehicle().getUser().getId().equals(currentUser.getId());
        boolean isMechanic = invoice.getServiceRequest().getPreferredMechanic() != null && 
                             invoice.getServiceRequest().getPreferredMechanic().getUser().getId().equals(currentUser.getId());
        
        if (!isCustomer && !isMechanic && !currentUser.getRole().name().equals("ADMIN")) {
            throw new IllegalArgumentException("Access Denied to this invoice");
        }

        model.addAttribute("invoice", invoice);
        model.addAttribute("isCustomer", isCustomer);
        return "invoice";
    }

    @PostMapping("/customer/pay-invoice")
    public String payInvoice(@RequestParam("invoiceId") Long invoiceId,
                             @RequestParam("paymentMethod") String paymentMethod,
                             RedirectAttributes redirectAttributes) {
        try {
            invoiceService.simulatePayment(invoiceId, paymentMethod);
            redirectAttributes.addFlashAttribute("flashMessage", "Payment successful! The invoice has been marked as PAID.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("flashError", e.getMessage());
        }
        return "redirect:/customer-dashboard";
    }
}
