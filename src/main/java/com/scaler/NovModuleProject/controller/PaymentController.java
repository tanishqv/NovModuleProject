package com.scaler.NovModuleProject.controller;

import com.scaler.NovModuleProject.dto.PaymentRequestDTO;
import com.scaler.NovModuleProject.service.PaymentService;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.exception.StripeException;
import com.stripe.model.Event;
import com.stripe.net.Webhook;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
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

    @PostMapping("/common-webhook")
    public void handleWebhook(@RequestBody String payload, @RequestHeader("Stripe-Signature") String sigHeader) {
        // Based on the event that triggers the payment gateway, this webhook endpoint contains the logic to make use of that change and perform relevant operations
        // We have to register this endpoint for Stripe to forward the changes to it

        String endpointSecret = "****";

        try {
            Event event = Webhook.constructEvent(payload, sigHeader, endpointSecret);
            System.out.println("Webhook triggered, with type: " + event.getType());
        } catch (SignatureVerificationException e) {
            System.out.println("Webhook signature verification failed");
        }
    }
}
