package com.scaler.NovModuleProject.controller;

import com.scaler.NovModuleProject.dto.PaymentRequestDTO;
import com.scaler.NovModuleProject.service.PaymentService;
import com.stripe.exception.StripeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PaymentController {
    private PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/payment")
    public ResponseEntity<String> createPaymentLink(@RequestBody PaymentRequestDTO paymentRequestDTO) throws StripeException {
        String paymentLink = paymentService.createPaymentLink(paymentRequestDTO.getOrderId(), paymentRequestDTO.getAmount());
        return new ResponseEntity<>(paymentLink, HttpStatus.OK);
    }
}
