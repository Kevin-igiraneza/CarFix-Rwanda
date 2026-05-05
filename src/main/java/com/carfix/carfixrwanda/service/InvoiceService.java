package com.carfix.carfixrwanda.service;

import com.carfix.carfixrwanda.model.Invoice;
import com.carfix.carfixrwanda.model.Payment;
import com.carfix.carfixrwanda.model.ServiceRequest;
import com.carfix.carfixrwanda.repository.InvoiceRepository;
import com.carfix.carfixrwanda.repository.PaymentRepository;
import com.carfix.carfixrwanda.repository.ServiceRequestRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
public class InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final PaymentRepository paymentRepository;
    private final ServiceRequestRepository serviceRequestRepository;

    public InvoiceService(InvoiceRepository invoiceRepository, PaymentRepository paymentRepository, ServiceRequestRepository serviceRequestRepository) {
        this.invoiceRepository = invoiceRepository;
        this.paymentRepository = paymentRepository;
        this.serviceRequestRepository = serviceRequestRepository;
    }

    @Transactional
    public Invoice generateInvoice(Long serviceRequestId, BigDecimal partsTotal, BigDecimal laborTotal) {
        ServiceRequest request = serviceRequestRepository.findById(serviceRequestId)
                .orElseThrow(() -> new IllegalArgumentException("Service Request not found"));

        if (invoiceRepository.findByServiceRequestId(serviceRequestId).isPresent()) {
            throw new IllegalStateException("Invoice already exists for this service request");
        }

        Invoice invoice = new Invoice();
        invoice.setServiceRequest(request);
        invoice.setPartsTotal(partsTotal);
        invoice.setLaborTotal(laborTotal);
        
        // Example: 18% tax
        BigDecimal subTotal = partsTotal.add(laborTotal);
        BigDecimal tax = subTotal.multiply(new BigDecimal("0.18"));
        invoice.setTaxTotal(tax);
        invoice.setGrandTotal(subTotal.add(tax));
        
        return invoiceRepository.save(invoice);
    }

    public List<Invoice> getCustomerInvoices(Long customerId) {
        return invoiceRepository.findByServiceRequest_CustomerVehicle_UserId(customerId);
    }

    public List<Invoice> getMechanicInvoices(Long mechanicId) {
        return invoiceRepository.findByServiceRequest_PreferredMechanic_Id(mechanicId);
    }

    public Invoice getInvoice(Long invoiceId) {
        return invoiceRepository.findById(invoiceId)
                .orElseThrow(() -> new IllegalArgumentException("Invoice not found"));
    }

    @Transactional
    public Payment simulatePayment(Long invoiceId, String paymentMethod) {
        Invoice invoice = getInvoice(invoiceId);
        
        if (invoice.getStatus() == Invoice.InvoiceStatus.PAID) {
            throw new IllegalStateException("Invoice is already paid");
        }

        Payment payment = new Payment();
        payment.setInvoice(invoice);
        payment.setAmountPaid(invoice.getGrandTotal());
        payment.setPaymentMethod(paymentMethod);
        payment.setTransactionId(UUID.randomUUID().toString());
        paymentRepository.save(payment);

        invoice.setStatus(Invoice.InvoiceStatus.PAID);
        invoiceRepository.save(invoice);

        return payment;
    }
}
