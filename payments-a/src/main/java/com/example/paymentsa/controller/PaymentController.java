package com.example.paymentsa.controller;

import com.example.paymentsa.model.PaymentRequest;
import com.example.paymentsa.model.PaymentResponse;
import com.example.paymentsa.service.PaymentsAService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    private final PaymentsAService paymentsAService;

    public PaymentController(PaymentsAService paymentsAService) {
        this.paymentsAService = paymentsAService;
    }

    @PostMapping
    public ResponseEntity<PaymentResponse> createPayment(@RequestBody PaymentRequest req) {
        PaymentResponse res = paymentsAService.forwardToOrchestration(req);
        return ResponseEntity.status(res.isSuccess() ? 200 : 400).body(res);
    }
}
